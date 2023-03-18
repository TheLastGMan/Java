import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

/**
 * Parallel Implementation
 * AppSpec Your program must use futures, callables and thread pools to process
 * each thread
 *
 * @author Ryan Gau
 * @version 1.0-ICS440A2
 */
public class ParallelEnumerable<TSource> implements Iterable<TSource> {
	private final ConcurrentLinkedQueue<TSource> _backing;
	private int _degreeOfParallelism = AvailableCores();

	// CTOR
	public ParallelEnumerable(Collection<TSource> source) {
		_backing = new ConcurrentLinkedQueue<>(source);
	}

	private ParallelEnumerable(Collection<TSource> source, int degreeOfParallelism) {
		this(source);
		
		// validate degree
		if (degreeOfParallelism < 1) {
			degreeOfParallelism = 1;
		}
		if (degreeOfParallelism > 128) {
			degreeOfParallelism = 128;
		}
		_degreeOfParallelism = degreeOfParallelism;
	}
	
	public static int AvailableCores() {
		return Runtime.getRuntime().availableProcessors();
	}

	public ParallelEnumerable<TSource> WithDegreeOfParallelism(int threads) {
		// validate
		if (threads < 1) {
			threads = 1;
		}
		if (threads > 128) {
			threads = 128;
		}
		
		_degreeOfParallelism = threads;
		return this;
	}

	/**
	 * Iterate a collection in parallel
	 *
	 * @param action
	 *            Action to apply to collection
	 * @return Collection(Of TOutput)
	 */
	private <TOutput> List<TOutput> parallelIterate(Function<ConcurrentLinkedQueue<TSource>, TOutput> action) {
		// create thread pool and synchronous access to the iterator
		ExecutorService pool = Executors.newFixedThreadPool(_degreeOfParallelism);

		// spawn threads as call-able tasks
		List<Future<TOutput>> tasks = new ArrayList<>();
		for (int i = 0; i < _degreeOfParallelism; i++) {
			tasks.add(pool.submit((Callable<TOutput>)() -> {
				return action.apply(_backing);
			}));
		}

		// wait for all threads to complete
		while (true) {
			boolean isDone = true;
			for (Future<TOutput> task : tasks) {
				if (!task.isDone()) {
					isDone = false;
					break;
				}
			}
			if (isDone) {
				break;
			}
			
			// delay until next check
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException e) {
			}
		}

		// combine output and give result
		try {
			List<TOutput> result = new ArrayList<>();
			for (Future<TOutput> value : tasks) {
				result.add(value.get());
			}
			return result;
		}
		catch (Exception ex) {
			return new ArrayList<>();
		}
		finally {
			// close thread pool
			pool.shutdown();
		}
	}

	/**
	 * Projects each element of a sequence to an IEnumerable and flattens the
	 * resulting sequences into one
	 * sequence.
	 *
	 * @param <TResult>
	 *            The type of the resulting value.
	 * @param selector
	 *            A transform function to apply to each element.
	 * @return A QList whose elements are the result of invoking the one-to-many
	 *         transform function on each
	 *         element
	 *         of the input sequence.
	 */
	public <TResult> ParallelEnumerable<TResult> SelectMany(Function<TSource, List<TResult>> selector) {
		// parameter check
		if (selector == null) {
			return new ParallelEnumerable<>(new ArrayList<>(), _degreeOfParallelism);
		}

		// default
		List<List<TResult>> results = new ArrayList<>();

		// iterate
		List<List<List<TResult>>> manyResults = parallelIterate(itrLock -> {
			List<List<TResult>> localResults = new ArrayList<>();
			TSource item;
			while ((item = itrLock.poll()) != null) {
				List<TResult> localValues = selector.apply(item);
				localResults.add(localValues);
			}
			return localResults;
		});

		// merge and give results
		for (List<List<TResult>> result : manyResults) {
			results.addAll(result);
		}

		// map down one more time
		List<TResult> finalResults = new ArrayList<>();
		for (List<TResult> result : results) {
			finalResults.addAll(result);
		}

		// return result
		return new ParallelEnumerable<>(finalResults, _degreeOfParallelism);
	}
	
	/**
	 * Inverts the order of the elements in a sequence.
	 *
	 * @return A sequence whose elements correspond to those of the input
	 *         sequence in reverse order.
	 */
	public ParallelEnumerable<TSource> Reverse() {
		List<TSource> items = this.ToList();
		
		int len = items.size();
		int halfLen = len / 2;
		for (int i = 0; i < halfLen; i++) {
			int upperBound = len - 1 - i;
			TSource temp = items.get(i);
			items.set(i, items.get(upperBound));
			items.set(upperBound, temp);
		}
		
		return new ParallelEnumerable<>(items, _degreeOfParallelism);
	}

	/**
	 * Create an ArrayList from a Enumerable
	 *
	 * @return ArrayList of TSource
	 */
	public List<TSource> ToList() {
		// create a list from this iterator
		List<TSource> values = new ArrayList<>();
		for (TSource item : this) {
			values.add(item);
		}
		return values;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<TSource> iterator() {
		return _backing.iterator();
	}

	/**
	 * Returns a specified number of contiguous elements from the start of a
	 * sequence.
	 *
	 * @param count
	 *            The number of elements to return.
	 * @return A Enumerable that contains the specified number of elements from
	 *         the start of the input sequence.
	 */
	public ParallelEnumerable<TSource> Take(int count) {
		// check bounds
		if (count < 0) {
			return new ParallelEnumerable<>(_backing, _degreeOfParallelism);
		}
		
		// take the count
		List<TSource> items = new ArrayList<>();
		for (TSource item : this) {
			if (count-- > 0) {
				items.add(item);
			}
			else {
				break;
			}
		}
		
		return new ParallelEnumerable<>(items, _degreeOfParallelism);
	}
	
	/**
	 * Returns the first element of the sequence that satisfies a condition or a
	 * default value if no such element is
	 * found.
	 *
	 * @param p
	 *            A function to test each element for a condition.
	 * @return default(TSource) if source is empty or if no element passes the
	 *         test specified by predicate; otherwise,
	 *         the first element in source that passes the test specified by
	 *         predicate.
	 */
	public TSource FirstOrDefault(Predicate<TSource> p) {
		// determine action based on predicate
		if (p == null) {
			if (!iterator().hasNext()) {
				return null;
			}
			
			return iterator().next();
		}

		// find first element that is true
		for (TSource item : this) {
			if (p.test(item)) {
				return item;
			}
		}

		// default response
		return null;
	}
}
