package ua.kharkiv.epam.dereza.current;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Sequence finder with ReentrantLock
 * 
 * @author Eduard_Dereza
 * 
 */
public class SequenceFinder {
	// keeps path to file for searching
	private StringBuffer path = new StringBuffer();
	// keeps messages from daemon to main thread
	private StringBuffer messages = new StringBuffer();
	// minimal allowable length of sequence

	private volatile boolean searchDone = true;
	private Lock lock = new ReentrantLock();
	private Condition newTask = lock.newCondition();
	private Condition newMessage = lock.newCondition();

	public static void main(String[] args) throws Exception {
		// initialize thread and starts it
		SequenceFinder finder = new SequenceFinder();
		Thread searchThread = new Thread(finder.new SequenceSearch());
		searchThread.setDaemon(true);
		searchThread.start();

		// console menu
		boolean escMenu = true;
		Scanner scanner = new Scanner(System.in);
		while (escMenu) {
			System.out.println("Please input path to file or -1 to exit");
			System.out.println("(for win it looks like - C:/Windows/.., for linux - /data/app/..)");
			System.out.print(">");

			String path = scanner.next();
			if (!new File(path.toString()).exists())
				throw new IllegalArgumentException();

			if (path.equals("-1"))
				escMenu = false;
			else {
				try {
					finder.lock.lock();
					// sets path
					finder.path.delete(0, path.length());
					finder.path.append(path);
					finder.searchDone = false;
					finder.newTask.signalAll();
					while (!finder.searchDone) {
						// thread sleeps until daemon doesn't set a message
						finder.newMessage.await();
						System.out.print(finder.messages);
						finder.messages.delete(0, finder.messages.length());
					}
				} finally {
					finder.lock.unlock();
				}
			}
		}
		scanner.close();
	}

	/**
	 * Sequences finder
	 * 
	 * @author Eduard_Dereza
	 * 
	 */
	private class SequenceSearch implements Runnable {

		private FileInputStream fis;
		private byte[] readArr;
		private Integer[] suffixIndexes;
		private Integer length =-1;
		private Integer firstOccurrence =-1;
		private Integer secondOccurrence =-1;

		@Override
		public void run() {
			// thread sleeps until searchDone == true
			// this condition can be changed in menu
			while (true) {
				try {
					lock.lock();
					while (searchDone)
						newTask.await();
				} catch (InterruptedException e) {
					throw new RuntimeException("Thread was interrupted");
				}finally{
					lock.unlock();
				}
				File file = new File(path.toString());
				// saves message for main thread
				sendMessage("File length:" + file.length());

				readFile(file);

				// Start indexes of suffixes
				suffixIndexes = new Integer[readArr.length];

				// Unsorted array of suffixes
				sendMessage("\nInitialization...");
				for (int i = 0; i < suffixIndexes.length; i++)
					suffixIndexes[i] = i;

				// sorts indexes
				sendMessage("\nSorts indexes....");
				sortSuffixArrays();
				sendMessage("\nSorts indexes finished.");

				// Searching
				sendMessage("\nSequence searching....");
				for (int i = 0; i < suffixIndexes.length - 1; i++) {
					Integer same = countSameBytesInSuffixes(i, i + 1);

					if(i % 75000 == 0)
						sendMessage("Processed " + i + " from " + suffixIndexes.length + " indexes\n");

					if (same > length) {
						length = same;
						firstOccurrence = suffixIndexes[i];
						secondOccurrence = suffixIndexes[i + 1];
					}

				}

				sendMessage("\nThe longest repeated sequence has " + length + " elements.");
				sendMessage(" indexes:" + firstOccurrence + ", " + secondOccurrence + "\n");
				searchDone = true;

				try{
					lock.lock();
					newMessage.signalAll();
				}finally{
					lock.unlock();
				}
			}
		}

		/**
		 * Sorts suffixIndexes according to  read byte array
		 */
		private void sortSuffixArrays() {
			Arrays.sort(suffixIndexes, new Comparator<Integer>() {
				@Override
				public int compare(Integer first, Integer second) {
					int result = Byte.compare(readArr[first], readArr[second]);
					return result;
				}
			});
		}

		/**
		 * Allows to found longest repeated sequence.
		 * Count same bytes in different suffixes.
		 * 
		 * @param firstIndex
		 * @param secondIndex
		 * @return
		 */
		private Integer countSameBytesInSuffixes(int firstIndex, int secondIndex) {
			Integer totalCount = 0;
			int iteraion = Math.max(suffixIndexes[firstIndex],	suffixIndexes[secondIndex]);
			int count1 = suffixIndexes[firstIndex];
			int count2 = suffixIndexes[secondIndex];

			while (iteraion++ < readArr.length) {
				if (readArr[count1++] == readArr[count2++])
					totalCount++;
				else
					break;
			}
			return totalCount;
		}

		/**
		 * Saves message for main thread
		 * 
		 * @param message
		 */
		private void sendMessage(String message){
			try{
				messages.append(message);
				lock.lock();
				newMessage.signalAll();
			}finally{
				lock.unlock();
			}
		}

		/**
		 * Reads file and saves it in byte array.
		 * 
		 * @param file
		 */
		private void readFile(File file){
			try {
				fis = new FileInputStream(file);
				readArr = new byte[(int) file.length()];
				// reads file to array
				fis.read(readArr);
				fis.close();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}
	}
}
