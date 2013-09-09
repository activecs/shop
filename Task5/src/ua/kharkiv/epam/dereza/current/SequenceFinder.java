package ua.kharkiv.epam.dereza.current;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Sequence finder with wait/notify
 * 
 * @author Eduard_Dereza
 *
 */
public class SequenceFinder {

	private StringBuilder path = new StringBuilder();
	private int minSeqLngth = 2;

	public static void main(String[] args) throws IOException,	InterruptedException {
		// initialize thread and starts it
		SequenceFinder finder = new SequenceFinder();
		SequenceFinder.SequenceSearch search = finder.new SequenceSearch();
		Thread searchThread = new Thread(search);
		searchThread.setDaemon(true);
		searchThread.start();

		boolean flag = true;
		Scanner scanner = new Scanner(System.in);
		while (flag) {
			System.out.println("Please input path to file or -1 to exit");
			System.out.println("(for win it looks like - C:/Windows/.., for linux - /data/app/..)");
			System.out.print(">");
			String path = scanner.next();
			if (!new File(path.toString()).exists())
				throw new IllegalArgumentException();
			if (path.equals("-1"))
				flag = false;
			else {
				synchronized (finder.path) {
					finder.path.append(path);
					finder.path.notifyAll();
					
					while(!(finder.path.length() == 0))
						finder.path.wait();
				}
			}
		}
		scanner.close();
	}

	private class SequenceSearch implements Runnable {
		FileInputStream fis = null;

		@Override
		public void run() {
			while (true) {
				synchronized (path) {
					try {
						while (path.length() == 0)
							path.wait();
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
					File file = new File(path.toString());
					path.delete(0, path.length());

					System.out.println("File length:" + file.length());

					// container for all sequences
					List<List<Byte>> sequences = new ArrayList<List<Byte>>();
					List<Byte> tempSequence = new ArrayList<Byte>();

					try {
						fis = new FileInputStream(file);
						byte[] read = new byte[10];
						while (fis.read(read) != -1) {
							for (Byte currentByte : read) {
								// it is first element of sequence
								if (tempSequence.isEmpty())
									tempSequence.add(currentByte);
								// next element for sequence
								if (currentByte > tempSequence.get(tempSequence.size() - 1))
									tempSequence.add(currentByte);
								// saves sequence if it has length greater or
								// equal
								// minimal sequence length
								if (currentByte < tempSequence.get(tempSequence.size() - 1)) {
									if (tempSequence.size() >= minSeqLngth)
										sequences.add(tempSequence);
									// prepares containre for next sequence
									tempSequence = new ArrayList<Byte>();
								}
							}
						}
						fis.close();
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					}

					// sorts sequences by length
					Collections.sort(sequences, new Comparator<List<Byte>>() {
						@Override
						public int compare(List<Byte> list1, List<Byte> list2) {
							if (list1.size() > list2.size())
								return 1;
							if (list1.size() < list2.size())
								return -1;
							return 0;
						}
					});
					
					// looking for the greatest repeated sequence
					List<Byte> greatestSeq = new ArrayList<Byte>();
					for (int i = 0; i < sequences.size(); i++) {
						for(int j = (i + 1); j < sequences.size(); j++){
							if(containEntry(sequences.get(j), sequences.get(i))){
								if (greatestSeq.size() < sequences.get(i).size()) {
									greatestSeq = sequences.get(i);
								}
							}
						}
					}
					
					// looking for indexes
					List<Integer> indexes = new ArrayList<Integer>();
					for (int i = 0; i < sequences.size(); i++) {
						if(containEntry(sequences.get(i), greatestSeq)){
							indexes.add(i);
						}
					}
					
					System.out.println("The greatest repeated sequence:" + greatestSeq);
					System.out.println("indexes:" + indexes);

					path.notifyAll();
				}
			}
		}
		
		/**
		 * Checks whether list1 contains list2
		 * 
		 * @param list1
		 * @param list2
		 * @return
		 */
		private <T> boolean containEntry(List<T> list1, List<T> list2){
			StringBuilder list1Str = new StringBuilder();
			StringBuilder list2Str = new StringBuilder();
			for(T tmp : list1)
				list1Str.append("." + tmp);
			for(T tmp : list2)
				list2Str.append("." + tmp);
			Pattern pattern = Pattern.compile(list2Str.toString());
			Matcher matcher = pattern.matcher(list1Str.toString());
			
			return matcher.find();
		}
	}
}
