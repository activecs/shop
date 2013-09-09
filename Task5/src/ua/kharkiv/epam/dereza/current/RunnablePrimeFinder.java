package ua.kharkiv.epam.dereza.current;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class for search prime numbers via common approach
 * 
 * @author Eduard_Dereza
 *
 */
public class RunnablePrimeFinder {
	
	public static void main(String[] args) throws InterruptedException {
		RunnablePrimeFinder finder = new RunnablePrimeFinder();
		List<Integer> externalList = new ArrayList<Integer>();
		
		//start time
		long startTime = new Date().getTime();
		
		int startValue = 1;
		int finishValue = 1_000_000;
		int threadCount = 10;
		Thread[] threads = new Thread[threadCount];
		
		// distribution range for processing
		int forProcessing = (finishValue - startValue) + 1;
		int forOneThread = forProcessing/threadCount;
		for(int i = 1; i<=threadCount; i++){
			int start = startValue + (i - 1) * forOneThread;
			int finish = startValue + ((i * forOneThread) - 1);
			if(i == threadCount)
				finish = finishValue;
			threads[i -1] = new Thread(finder.new PrimeFinder(start, finish, externalList));
			threads[i-1].start();
		}
		
		// waits for threads
		for(Thread thr : threads)
			thr.join();
		
		//finish time
		long finishTime = new Date().getTime();
		System.out.println("time:" + (finishTime - startTime));
		
		System.out.println(externalList.size());
	}
	
	/**
	 * Core of search
	 * 
	 * @author Eduard_Dereza
	 *
	 */
	private class PrimeFinder implements Runnable {

		private int startValue;
		private int finishValue;
		private List<Integer> externalList;
		private List<Integer> internalList;

		public PrimeFinder(int startValue, int finishValue,
				List<Integer> externalList) {
			this.startValue = startValue;
			this.finishValue = finishValue;
			this.externalList = externalList;
			internalList = new ArrayList<Integer>();
		}

		public boolean isPrimeNumber(int number) {
			if (number < 2)
				return false;
			for (int i = 2; i * i <= number; i++)
				if (number % i == 0)
					return false;
			return true;
		}

		@Override
		public void run() {
			for (int i = startValue; i <= finishValue; i++) {
				if (isPrimeNumber(i))
					internalList.add(i);
			}
			synchronized (externalList) {
				externalList.addAll(internalList);
			}

		}
	}
}
