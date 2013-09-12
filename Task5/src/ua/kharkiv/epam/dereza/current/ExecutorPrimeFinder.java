package ua.kharkiv.epam.dereza.current;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Class for search prime numbers via Executor
 * 
 * @author Eduard_Dereza
 *
 */
public class ExecutorPrimeFinder {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorPrimeFinder primeExecutor = new ExecutorPrimeFinder();
		int startValue = 1;
		int finishValue = 1_000_000;
		int threadCount = 10;
		
		//start time
		long startTime = new Date().getTime();
		//list of tasks
		List<PrimeFinderCallable> primeFinderList = new ArrayList<PrimeFinderCallable>();
		ExecutorService exService = Executors.newFixedThreadPool(threadCount);
		
		// distribution range for processing
		int forProcessing = (finishValue - startValue) + 1;
		int forOneThread = forProcessing/threadCount;
		for(int i = 1; i<=threadCount; i++){
			int start = startValue + (i - 1) * forOneThread;
			int finish = startValue + ((i * forOneThread) - 1);
			if(i == threadCount)
				finish = finishValue;
			
			primeFinderList.add(primeExecutor.new PrimeFinderCallable(start, finish));
		}
		// waits while executor finish all tasks
		List<Future<List<Integer>>> futureList = exService.invokeAll(primeFinderList);
		//keeps result
		List<Integer> listOfPrime = new ArrayList<Integer>();
		
		for(Future<List<Integer>> tempFuture : futureList){
			listOfPrime.addAll(tempFuture.get());
		}
		
		//finish time
		long finishTime = new Date().getTime();
		System.out.println("time:" + (finishTime - startTime));
		
		System.out.println("list size:" + listOfPrime.size());
		//it isn't necessary
		exService.shutdown();
	}
	
	/**
	 * Core of search
	 * 
	 * @author Eduard_Dereza
	 *
	 */
	private class PrimeFinderCallable implements Callable<List<Integer>>{
		
		private int startValue;
		private int finishValue;
		private List<Integer> internalList;
		
		public PrimeFinderCallable(int startValue, int finishValue) {
			this.startValue = startValue;
			this.finishValue = finishValue;
			internalList = new ArrayList<Integer>();
		}
		
		/**
		 * 
		 * @param number
		 * @return true if number is prime
		 */
		public boolean isPrimeNumber(int number) {
			if (number < 2) return false;
		    for (int i = 2; i*i <= number; i++)
		        if (number % i == 0) return false;
		    return true;
		}
		
		@Override
		public List<Integer> call() throws Exception {
			for (int i=startValue; i<=finishValue; i++) {
				if (isPrimeNumber(i)) 
					internalList.add(i);
				}
			return internalList;
		}
		
	}
}
