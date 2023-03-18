/**
 *
 */
package System.PLinq;

import java.util.Iterator;
import java.util.concurrent.*;
import java.util.function.*;

import System.TupleT2;
import System.Collections.Generic.List;
import System.Linq.IEnumerable;

/**
 * Parallel Methods for IEnumerable(Of TSource)
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class ParallelEnumerable<TSource> extends IEnumerable<TSource>
{
	private final Iterator<TSource> _backing;
	private int _degreeOfParallelism = AvailableCores() * 4;
	private boolean _asOrdered = false;

	// CTOR
	public ParallelEnumerable(Iterator<TSource> source)
	{
		_backing = source;
	}

	public ParallelEnumerable(Iterable<TSource> source)
	{
		this(source.iterator());
	}

	private ParallelEnumerable(IEnumerable<TSource> source, int degreeOfParallelism, boolean asOrdered)
	{
		this(source);
		_asOrdered = asOrdered;
		
		// validate degree
		if (degreeOfParallelism < 1)
			degreeOfParallelism = 1;
		if (degreeOfParallelism > 128)
			degreeOfParallelism = 128;
		_degreeOfParallelism = degreeOfParallelism;
	}
	
	public static int AvailableCores()
	{
		return Runtime.getRuntime().availableProcessors();
	}

	// --
	// #Region Parallel Specific

	public ParallelEnumerable<TSource> WithDegreeOfParallelism(int threads)
	{
		// validate
		if (threads < 1)
			threads = 1;
		if (threads > 128)
			threads = 128;

		_degreeOfParallelism = threads;
		return this;
	}

	public IEnumerable<TSource> AsSequential()
	{
		// take advantage of inheritance to make this happen
		return this;
	}

	public ParallelEnumerable<TSource> AsOrdered()
	{
		_asOrdered = true;
		return this;
	}
	
	public ParallelEnumerable<TSource> AsUnordered()
	{
		_asOrdered = false;
		return this;
	}
	
	private <TOutput> List<TOutput> parallelIterate(Function<SyncIterator<TSource>, TOutput> action)
	{
		// create thread pool and synchronous access to the iterator
		ExecutorService pool = Executors.newFixedThreadPool(_degreeOfParallelism);
		SyncIterator<TSource> itr = new SyncIterator<>(iterator());

		// spawn threads as callable tasks
		List<Future<TOutput>> tasks = new List<>();
		for (int i = 0; i < _degreeOfParallelism; i++)
			tasks.Add(pool.submit((Callable<TOutput>)() ->
				{
					return action.apply(itr);
				}));

		// wait for task completion
		try
		{
			pool.wait();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		// combine output and give result
		try
		{
			List<TOutput> result = new List<>();
			for (Future<TOutput> value : tasks)
				result.Add(value.get());
			return result;
		}
		catch (Exception ex)
		{
			return new List<>();
		}
		finally
		{
			// close thread pool
			pool.shutdown();
		}
	}

	// #EndRegion
	// #Region Iterator

	@Override
	public Iterator<TSource> iterator()
	{
		return _backing;
	}
	
	// #EndRegion

	// #Region Parallel Versions of IEnumerable
	
	/**
	 * Determines whether all elements of a sequence satisfy a condition.
	 *
	 * @param p
	 *            A function to test each element for a condition.
	 * @return true if every element of the source sequence passes the test in the specified predicate,
	 *         or if the sequence is empty; otherwise, false.
	 */
	@Override
	public boolean All(Predicate<TSource> p)
	{
		// check for null
		if (p == null)
			return true;
		
		List<Boolean> results = parallelIterate(itrLock ->
			{
				SyncIteratorResult<TSource> item = null;
				while ((item = itrLock.Next()).HasValue)
					// check for fail, mark and exit
					if (!p.test(item.Value))
					{
						// take the lock and just go through the remaining
						// items so other threads don't process them
						while (itrLock.Next().HasValue)
						{
						}
						return false;
					}
				return true;
			});
		
		// check all results are true
		return results.All(f -> f);
	}
	
	/**
	 * Determines whether any element of a sequence satisfies a condition.
	 *
	 * @param p
	 *            A function to test each element for a condition.
	 * @return true if any elements in the source sequence pass the test in the specified predicate or param isn't set;
	 *         otherwise, false.
	 */
	@Override
	public boolean Any(Predicate<TSource> p)
	{
		// if nothing specified and enumerable has elements, return true
		if (p == null)
			return Any();
		
		// pass on first true response
		List<Boolean> result = parallelIterate(itrLock ->
			{
				SyncIteratorResult<TSource> item = null;
				while ((item = itrLock.Next()).HasValue)
					// test if condition matches
					if (p.test(item.Value))
					{
						// take the lock and just go through the remaining
						// items so other threads don't process them
						while (itrLock.Next().HasValue)
						{
						}
						return true;
					}
				
				return false;
			});
		
		// check if any are true
		return result.Any(f -> f);
	}

	/**
	 * Computes the average of a sequence of Number values.
	 *
	 * @param <TResult>
	 *            The type of the resulting value.
	 * @param selector
	 *            A function to extract a number from an element
	 * @return The average of the sequence of values.
	 * @exception IllegalArgumentException
	 *                if selector is not set
	 */
	@Override
	public <TResult extends Number> double Average(Function<TSource, TResult> selector)
	{
		List<TupleT2<Integer, Double>> results = parallelIterate(itrLock ->
			{
				int localCount = 0;
				double localSum = 0;
				SyncIteratorResult<TSource> item = null;
				while ((item = itrLock.Next()).HasValue)
				{
					// process
					localCount += 1;
					localSum += selector.apply(item.Value).doubleValue();
				}
				return new TupleT2<>(localCount, localSum);
			});
		
		// merge thread results
		int count = 0;
		double sum = 0;
		for (TupleT2<Integer, Double> t : results)
		{
			count += t.Item1;
			sum += t.Item2;
		}
		double avg = sum / count;
		return avg;
	}
	
	/**
	 * Returns a number that represents how many elements in the specified sequence satisfy a condition.
	 *
	 * @param p
	 *            A function to test each element for a condition.
	 * @return A number that represents how many elements in the sequence satisfy the condition in the predicate
	 *         function. collection count if parameter is not set
	 */
	@Override
	public int Count(Predicate<TSource> p)
	{
		// check nulls
		if (p == null)
			return Count();

		List<Integer> result = parallelIterate(itrLock ->
			{
				int totalCount = 0;
				SyncIteratorResult<TSource> item = null;
				while ((item = itrLock.Next()).HasValue)
					// add to the count if it's true
					totalCount += p.test(item.Value) ? 1 : 0;
				return totalCount;
			});

		// combine threads to give total count
		int count = 0;
		for (int i : result)
			count += i;

		return count;
	}

	/**
	 * Performs the specified action on each element of the QList.
	 *
	 * @param action
	 *            The Action delegate to perform on each element of the QList.
	 */
	public void ForAll(Consumer<TSource> action)
	{
		// check if base is null
		if (action == null)
			return;
		
		parallelIterate(itrLock ->
			{
				SyncIteratorResult<TSource> item = null;
				while ((item = itrLock.Next()).HasValue)
					action.accept(item.Value);

				// must have some value
				return 0;
			});
	}

	/**
	 * Returns the maximum value in a sequence of Number values.
	 *
	 * @param <TResult>
	 *            The type of the resulting value.
	 * @param selector
	 *            A function to extract a number from an element
	 * @return The maximum value in the sequence, 0 if there are no elements.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <TResult extends Number> TResult Max(Function<TSource, TResult> selector)
	{
		List<Double> maximums = parallelIterate(itrLock ->
			{
				// setup
				Double max = null;
				boolean isFirst = true;

				SyncIteratorResult<TSource> item = null;
				while ((item = itrLock.Next()).HasValue)
				{
					// check if it is the max for this set
					double value = selector.apply(item.Value).doubleValue();
					if (isFirst)
					{
						isFirst = false;
						max = value;
					}
					else if (value > max)
						max = value;
				}

				// give the local maximum
				return max;
			}).Where(f -> f != null).ToList();
		
		// convert output value to base type
		return (TResult)Number.class.cast(maximums.Any() ? maximums.Max(f -> f) : 0);
	}

	/**
	 * Returns the minimum value in a sequence of Number values.
	 *
	 * @param <TResult>
	 *            The type of the resulting value.
	 * @param selector
	 *            A function to extract a number from an element
	 * @return The minimum value in the sequence.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <TResult extends Number> TResult Min(Function<TSource, TResult> selector)
	{
		List<Double> minimums = parallelIterate(itrLock ->
			{
				// setup
				Double min = null;
				boolean isFirst = true;

				while (true)
				{
					// load next element
					SyncIteratorResult<TSource> item = itrLock.Next();
					if (!item.HasValue)
						break;

					// check if it is the max for this set
					double value = selector.apply(item.Value).doubleValue();
					if (isFirst)
					{
						isFirst = false;
						min = value;
					}
					else if (value < min)
						min = value;
				}

				// give the local maximum
				return min;
			}).Where(f -> f != null).ToList();

		// convert output value to base type
		return (TResult)Number.class.cast(minimums.Any() ? minimums.Min(f -> f) : 0);
	}

	/**
	 * Projects each element of a sequence into a new form.
	 *
	 * @param <TResult>
	 *            The type of the resulting value.
	 * @param selector
	 *            A transform function to apply to each element.
	 * @return A QList whose elements are the result of invoking the transform function on each element of
	 *         source.
	 */
	@Override
	public <TResult> ParallelEnumerable<TResult> Select(Function<TSource, TResult> selector)
	{
		return Select((e, i) -> selector.apply(e));
	}

	/**
	 * Projects each element of a sequence into a new form by incorporating the element's index.
	 *
	 * @param <TResult>
	 *            The type of the resulting value.
	 * @param selector
	 *            A transform function to apply to each source element; the second parameter of the function represents
	 *            the index of the source element.
	 * @return A QList whose elements are the result of invoking the transform function on each element of
	 *         source.
	 */
	@Override
	public <TResult> ParallelEnumerable<TResult> Select(BiFunction<TSource, Integer, TResult> selector)
	{
		// validate
		if (selector == null)
			return new ParallelEnumerable<>(new List<>(), _degreeOfParallelism, _asOrdered);

		// default
		List<TupleT2<Integer, TResult>> result = new List<>();
		
		// parallel select
		List<List<TupleT2<Integer, TResult>>> query = parallelIterate(itrLock ->
			{
				List<TupleT2<Integer, TResult>> results = new List<>();
				while (true)
				{
					// next item
					SyncIteratorResult<TSource> item = itrLock.Next();
					if (!item.HasValue)
						break;

					// apply action and add to result
					TResult output = selector.apply(item.Value, item.Index);
					results.Add(new TupleT2<>(item.Index, output));
				}

				return results;
			});

		// combine output
		for (List<TupleT2<Integer, TResult>> val : query)
			result.AddRange(val);

		// check ordering
		if (_asOrdered)
			result = result.OrderBy(f -> f.Item1);

		// return result
		return new ParallelEnumerable<>(result.Select(f -> f.Item2), _degreeOfParallelism, _asOrdered);
	}

	/**
	 * Projects each element of a sequence to an IEnumerable and flattens the resulting sequences into one
	 * sequence.
	 *
	 * @param <TResult>
	 *            The type of the resulting value.
	 * @param selector
	 *            A transform function to apply to each element.
	 * @return A QList whose elements are the result of invoking the one-to-many transform function on each
	 *         element
	 *         of the input sequence.
	 */
	@Override
	public <TResult> ParallelEnumerable<TResult> SelectMany(Function<TSource, IEnumerable<TResult>> selector)
	{
		return SelectMany((e, i) -> selector.apply(e));
	}

	/**
	 * Projects each element of a sequence to an IEnumerable, and flattens the resulting sequences into one sequence.
	 * The index of each source element is used in the projected form of that element.
	 *
	 * @param <TResult>
	 *            The type of the resulting value.
	 * @param selector
	 *            A transform function to apply to each source element; the second parameter of the function represents
	 *            the index of the source element.
	 * @return A QList whose elements are the result of invoking the one-to-many transform function on each
	 *         element
	 *         of an input sequence.
	 */
	@Override
	public <TResult> ParallelEnumerable<TResult> SelectMany(BiFunction<TSource, Integer, IEnumerable<TResult>> selector)
	{
		return SelectMany((e, i) -> selector.apply(e, i), (s, c) -> c);
	}

	/**
	 * Projects each element of a sequence to an IEnumerable, flattens the resulting sequences into one sequence, and
	 * invokes a result selector function on each element therein.
	 *
	 * @param <TCollection>
	 *            The type of the intermediate elements collected by collectionSelector.
	 * @param <TResult>
	 *            The type of the resulting value.
	 * @param collectionSelector
	 *            A transform function to apply to each element of the input sequence.
	 * @param resultSelector
	 *            A transform function to apply to each element of the intermediate sequence.
	 * @return A QList whose elements are the result of invoking the one-to-many transform function
	 *         collectionSelector on each element of source and then mapping each of those sequence elements and their
	 *         corresponding source element to a result element.
	 */
	@Override
	public <TResult, TCollection> ParallelEnumerable<TResult> SelectMany(Function<TSource, IEnumerable<TCollection>> collectionSelector,
			BiFunction<TSource, TCollection, TResult> resultSelector)
	{
		return SelectMany((e, i) -> collectionSelector.apply(e), resultSelector);
	}

	/**
	 * Projects each element of a sequence to an IEnumerable, flattens the resulting sequences into one sequence, and
	 * invokes a result selector function on each element therein. The index of each source element is used in the
	 * intermediate projected form of that element.
	 *
	 * @param <TCollection>
	 *            The type of the intermediate elements collected by collectionSelector.
	 * @param <TResult>
	 *            The type of the resulting value.
	 * @param collectionSelector
	 *            A transform function to apply to each source element; the second parameter of the function represents
	 *            the index of the source element.
	 * @param resultSelector
	 *            A transform function to apply to each element of the intermediate sequence.
	 * @return A QList whose elements are the result of invoking the one-to-many transform function
	 *         collectionSelector on each element of source and then mapping each of those sequence elements and their
	 *         corresponding source element to a result element.
	 */
	@Override
	public <TResult, TCollection> ParallelEnumerable<TResult> SelectMany(BiFunction<TSource, Integer, IEnumerable<TCollection>> collectionSelector,
			BiFunction<TSource, TCollection, TResult> resultSelector)
	{
		// parameter check
		if (collectionSelector == null || resultSelector == null)
			return new ParallelEnumerable<>(new List<>(), _degreeOfParallelism, _asOrdered);

		// default
		List<TupleT2<Integer, IEnumerable<TResult>>> result = new List<>();

		// iterate
		List<List<TupleT2<Integer, IEnumerable<TResult>>>> threadResults = parallelIterate(itrLock ->
			{
				List<TupleT2<Integer, IEnumerable<TResult>>> localResults = new List<>();
				while (true)
				{
					// load next item
					SyncIteratorResult<TSource> item = itrLock.Next();
					if (!item.HasValue)
						break;

					IEnumerable<TResult> localValues = collectionSelector.apply(item.Value, item.Index).Select(f -> resultSelector.apply(item.Value, f));
					localResults.Add(new TupleT2<>(item.Index, localValues));
				}
				return localResults;
			});

		// merge and give results
		for (List<TupleT2<Integer, IEnumerable<TResult>>> threadResult : threadResults)
			result.AddRange(threadResult);

		// check ordering
		if (_asOrdered)
			result = result.OrderBy(f -> f.Item1);

		// return result
		return new ParallelEnumerable<>(result.SelectMany(f -> f.Item2), _degreeOfParallelism, _asOrdered);
	}
	
	/**
	 * Computes the sum of a sequence of Number values.
	 *
	 * @param <TResult>
	 *            The type of the resulting value.
	 * @param selector
	 *            A function to extract a number from an element
	 * @return The sum of the values in the sequence.
	 */
	@Override
	public <TResult extends Number> double Sum(Function<TSource, TResult> selector)
	{
		// sum individual segments and then sum all threads
		return parallelIterate(itrLock ->
			{
				double sum = 0;
				while (true)
				{
					SyncIteratorResult<TSource> item = itrLock.Next();
					if (!item.HasValue)
						break;
					sum += selector.apply(item.Value).doubleValue();
				}
				return sum;
			}).Sum(f -> f);
	}
	
	/**
	 * Filters a sequence of values based on a predicate.
	 *
	 * @param p
	 *            A function to test each element for a condition.
	 * @return A QList that contains elements from the input sequence that satisfy the condition.
	 */
	@Override
	public ParallelEnumerable<TSource> Where(Predicate<TSource> p)
	{
		return Where((e, i) -> p.test(e));
	}
	
	/**
	 * Filters a sequence of values based on a predicate. Each element's index is used in the logic of the predicate
	 * function.
	 *
	 * @param p
	 *            A function to test each source element for a condition; the second parameter of the function
	 *            represents the index of the source element.
	 * @return A QList that contains elements from the input sequence that satisfy the condition.
	 */
	@Override
	public ParallelEnumerable<TSource> Where(BiPredicate<TSource, Integer> p)
	{
		// parameter check
		if (p == null)
			return new ParallelEnumerable<>(new List<>(), _degreeOfParallelism, _asOrdered);
		
		// default
		List<TupleT2<Integer, TSource>> items = new List<>();
		
		// find matching items against predicate to add to new list
		List<List<TupleT2<Integer, TSource>>> result = parallelIterate(itrLock ->
			{
				List<TupleT2<Integer, TSource>> values = new List<>();
				SyncIteratorResult<TSource> item = null;
				while ((item = itrLock.Next()).HasValue)
					if (p.test(item.Value, item.Index))
						values.Add(new TupleT2<>(item.Index, item.Value));
				return values;
			});
		
		// combine results and give result
		for (List<TupleT2<Integer, TSource>> val : result)
			items.AddRange(val);
		
		// check ordering
		if (_asOrdered)
			items = items.OrderBy(f -> f.Item1);
		
		return new ParallelEnumerable<>(items.Select(f -> f.Item2), _degreeOfParallelism, _asOrdered);
	}
	
	// #EndRegion
}
