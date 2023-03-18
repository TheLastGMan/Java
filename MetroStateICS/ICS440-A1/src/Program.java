import java.util.*;

/**
 * Main Entry Point
 * Specs seem to indicate this won't be used, just the implementation of
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class Program {
	public static void main(String[] args) {
		// app spec, must be of integers
		Queue<Integer> collection = new Queue<>();
		for (int index = 0; index < 10000; index++) {
			int candidate = (int)(Math.random() * 5);
			collection.enqueue(candidate);
		}

		// set up thread processors
		int threadCount = 5;
		RequestProcessor processor = new RequestProcessor(collection);
		List<Thread> threads = new ArrayList<>();

		// populate threads with processors
		for (int index = 0; index < threadCount; index++) {
			// set up thread
			Thread t = new Thread(processor);
			threads.add(t);
			t.start();
		}

		try {
			// wait for all threads to finish processing
			for (Thread thread : threads) {
				thread.join();
			}
		}
		catch (InterruptedException ie) {
			ie.printStackTrace();
		}

		// show statistics
		ThreadStatisticsSetup.print();
	}
}
