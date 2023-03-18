package System.Linq;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.*;

import System.TupleT2;
import System.Collections.MergeSort;

/**
 * Simulates limited methods of Microsoft's Language INtegrated Queries (LINQ)
 *
 * @author Ryan Gau
 * @version 1.0
 * @param <TSource>
 *            Type of the element in the collection
 */
public class QList<TSource> implements Iterable<TSource> {
	private Collection<TSource> _qlist;

	/**
	 * Initialize collection
	 *
	 * @param base
	 *            Underlying collection, zero length set if parameter is null
	 */
	public QList(Collection<TSource> base) {
		if (base == null) {
			_qlist = new ArrayList<>();
		}
		else {
			_qlist = new ArrayList<>(base);
		}
	}

	/**
	 * Update base collection with updated reference
	 *
	 * @param <TSource>
	 *            The type of the elements of source.
	 * @param newBase
	 *            updated collection
	 * @return updated QList
	 */
	private QList<TSource> assign(Collection<TSource> newBase) {
		return new QList<>(newBase);
	}

	// #Region "Simulated MS LINQ Methods"

	/**
	 * Generates a sequence of integral numbers within a specified range.
	 *
	 * @param start
	 *            The value of the first integer in the sequence.
	 * @param count
	 *            The number of sequential integers to generate.
	 * @return A QList that contains a range of sequential integral numbers.
	 */
	public static QList<Integer> Range(int start, int count) {
		// setup and add range
		ArrayList<Integer> items = new ArrayList<>();
		while (count-- > 0) {
			items.add(start++);
		}

		// return range list
		return new QList<>(items);
	}

	/**
	 * Generates a sequence that contains one repeated value.
	 *
	 * @param <TSource>
	 *            The type of the elements of source.
	 * @param element
	 *            The value to be repeated.
	 * @param count
	 *            The number of times to repeat the value in the generated
	 *            sequence.
	 * @return A QList that contains a repeated value
	 */
	public static <TSource> QList<TSource> Repeat(TSource element, int count) {
		// setup and add elements to the array
		ArrayList<TSource> items = new ArrayList<>();
		while (count-- > 0) {
			items.add(element);
		}

		// return repeated list
		return new QList<>(items);
	}

	/**
	 * Applies an accumulator function over a sequence.
	 *
	 * @param selector
	 *            An accumulator function to be invoked on each element
	 *            (TSouce-Old, TSource-Current),
	 *            output TSource-New.
	 * @return The final accumulator value.
	 *         if parameter is not set, returns first or default of collection
	 */
	public TSource Aggregate(BiFunction<TSource, TSource, TSource> selector) {
		// parameter check
		if (selector == null || this.Count() <= 1) {
			return this.FirstOrDefault();
		}

		// aggregate
		TSource finalValue = null;
		for (TSource item : this) {
			if (finalValue == null) {
				finalValue = item;
			}
			else {
				finalValue = selector.apply(finalValue, item);
			}
		}
		
		// output result
		return finalValue;
	}

	/**
	 * Applies an accumulator function over a sequence. The specified seed value
	 * is used as the initial accumulator
	 * value.
	 *
	 * @param <TAccumulate>
	 *            The type of the accumulator value.
	 * @param seed
	 *            The initial accumulator value.
	 * @param selector
	 *            An accumulator function to be invoked on each element.
	 * @return The final accumulator value, if any parameters are null, returns
	 *         seed
	 *         if any parameters are not set, returns null
	 */
	public <TAccumulate> TAccumulate Aggregate(TAccumulate seed,
			BiFunction<TAccumulate, TSource, TAccumulate> selector) {
		// parameter check
		if (seed == null || selector == null) {
			return null;
		}

		// aggregate
		TAccumulate finalValue = seed;
		for (TSource item : this) {
			finalValue = selector.apply(finalValue, item);
		}

		// output result
		return finalValue;
	}

	/**
	 * Applies an accumulator function over a sequence. The specified seed value
	 * is used as the initial accumulator
	 * value, and the specified function is used to select the result value.
	 *
	 * @param <TAccumulate>
	 *            The type of the accumulator value.
	 * @param <TResult>
	 *            The type of the resulting value.
	 * @param seed
	 *            The initial accumulator value.
	 * @param selector
	 *            An accumulator function to be invoked on each element.
	 * @param resultSelector
	 *            A function to transform the final accumulator value into the
	 *            result value.
	 * @return The transformed final accumulator value.
	 *         if any parameters are not set, returns null
	 */
	public <TResult, TAccumulate> TResult Aggregate(TAccumulate seed,
			BiFunction<TAccumulate, TSource, TAccumulate> selector, Function<TAccumulate, TResult> resultSelector) {
		// parameter check
		if (seed == null || selector == null || resultSelector == null) {
			return null;
		}

		// map results
		TAccumulate agg = Aggregate(seed, selector);
		TResult result = resultSelector.apply(agg);
		return result;
	}

	/**
	 * Determines whether all elements of a sequence satisfy a condition.
	 *
	 * @param p
	 *            A function to test each element for a condition.
	 * @return true if every element of the source sequence passes the test in
	 *         the specified predicate,
	 *         or if the sequence is empty; otherwise, false.
	 */
	public boolean All(Predicate<TSource> p) {
		// check for null
		if (p == null) {
			return true;
		}

		// if all match condition, return false on first failure
		for (TSource item : this) {
			if (!p.test(item)) {
				return false;
			}
		}
		
		return true;
	}

	/**
	 * Determines whether a sequence contains any elements.
	 *
	 * @return true if source sequence contains any elements; otherwise, false.
	 */
	public boolean Any() {
		return this.Count() > 0;
	}

	/**
	 * Determines whether any element of a sequence satisfies a condition.
	 *
	 * @param p
	 *            A function to test each element for a condition.
	 * @return true if any elements in the source sequence pass the test in the
	 *         specified predicate or param isn't set;
	 *         otherwise, false.
	 */
	public boolean Any(Predicate<TSource> p) {
		// if nothing specified and enumerable has elements, return true
		if (p == null) {
			return Any();
		}

		// pass on first true response
		for (TSource item : this) {
			if (p.test(item)) {
				return true;
			}
		}
		
		return false;
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
	public <TResult extends Number> double Average(Function<TSource, TResult> selector) {
		QList<TResult> items = this.Select(selector);
		double sum = items.Sum(g -> g.doubleValue());
		double ave = (sum / items.Count());
		return ave;
	}

	/**
	 * Concatenates two sequences.
	 *
	 * @param second
	 *            The sequence to concatenate to the first sequence.
	 * @return A QList that contains the concatenated elements of the two input
	 *         sequences.
	 */
	public QList<TSource> Concat(QList<TSource> second) {
		// parameter check
		if (second == null || second.Count() == 0) {
			return this;
		}

		// apply action
		ArrayList<TSource> qCopy = this.ToList();
		qCopy.addAll(second.ToList());
		return assign(qCopy);
	}

	/**
	 * Returns the number of elements in a sequence.
	 *
	 * @return The number of elements in the input sequence.
	 */
	public int Count() {
		return _qlist.size();
	}

	/**
	 * Returns a number that represents how many elements in the specified
	 * sequence satisfy a condition.
	 *
	 * @param p
	 *            A function to test each element for a condition.
	 * @return A number that represents how many elements in the sequence
	 *         satisfy the condition in the predicate
	 *         function. collection count if parameter is not set
	 */
	public int Count(Predicate<TSource> p) {
		// check nulls
		if (p == null) {
			return Count();
		}

		// count the number of elements that match the conditions
		int count = 0;
		for (TSource item : this) {
			if (p.test(item)) {
				count += 1;
			}
		}
		
		return count;
	}

	/**
	 * Returns distinct elements from a sequence by using the default equality
	 * comparer to compare values.
	 *
	 * @param <TResult>
	 *            The type of the resulting value.
	 * @param selector
	 *            Function to retrieve element to determine distinctness by
	 * @return A QList that contains distinct elements from the source sequence.
	 */
	public <TResult extends Comparable<TResult>> QList<TSource> Distinct(Function<TSource, TResult> selector) {
		return Distinct(selector, (first, second) -> first.compareTo(second));
	}

	/**
	 * Returns distinct elements from a sequence by using the default equality
	 * comparer to compare values.
	 *
	 * @param <TResult>
	 *            The type of the resulting value.
	 * @param selector
	 *            A function to extract the object to compare uniqueness
	 * @param comparer
	 *            A Comparer used to compare values for equality
	 * @return A QList that contains distinct elements from the source sequence.
	 *         if any parameters are null, returns a zero length set
	 */
	public <TResult> QList<TSource> Distinct(Function<TSource, TResult> selector, Comparator<TResult> comparer) {
		// parameter check
		if (selector == null || comparer == null) {
			return new QList<>(new ArrayList<TSource>());
		}

		// check for zero or one element
		if (this.Count() <= 1) {
			return this;
		}
		
		//@formatter:off
		ArrayList<TSource> distinctList = this.Select((s, i) -> new TupleT2<>(i, s))
											.GroupBy(t -> selector.apply(t.Item2), comparer)
											.OrderBy(f -> f.Min(g -> g.Item1))
											.Select(f -> f.FirstOrDefault().Item2)
											.ToList();
		//@formatter:on

		return assign(distinctList);
	}

	/**
	 * Returns the element at a specified index in a sequence or a default value
	 * if the index is out of range.
	 *
	 * @param index
	 *            The zero-based index of the element to retrieve.
	 * @return null if the index is outside the bounds of the source sequence;
	 *         otherwise, the element at the specified
	 *         position in the source sequence.
	 */
	public TSource ElementAtOrDefault(int index) {
		List<TSource> al = this.ToList();

		// check for out of bounds condition
		if (index < 0 || index >= al.size()) {
			return null;
		}

		// return index
		return al.get(index);
	}

	/**
	 * Produces the set difference of two sequences by using the default
	 * equality comparer to compare values.
	 *
	 * @param <TResult>
	 *            The type of the elements of the output sequences.
	 * @param second
	 *            A QList(Of T) whose elements that also occur in the first
	 *            sequence will cause those elements to be
	 *            removed from the returned sequence.
	 * @param selector
	 *            A function to extract the element from the source list
	 * @return A sequence that contains the set difference of the elements of
	 *         two sequences.
	 *         if any parameters are null, returns current QList
	 */
	public <TResult extends Comparable<TResult>> QList<TSource> Except(QList<TSource> second,
			Function<TSource, TResult> selector) {
		return Except(second, (first, next) -> selector.apply(first).compareTo(selector.apply(next)));
	}

	/**
	 * Produces the set difference of two sequences by using the specified
	 * Comparator(Of T) to compare values.
	 *
	 * @param second
	 *            A QList(Of T) whose elements that also occur in the first
	 *            sequence will cause those elements to be
	 *            removed from the returned sequence.
	 * @param comparer
	 *            A Comparator(Of T) to compare values.
	 * @return A sequence that contains the set difference of the elements of
	 *         two sequences.
	 *         if any parameters are null, returns current QList
	 */
	public QList<TSource> Except(QList<TSource> second, Comparator<TSource> comparer) {
		// parameter check
		if (second == null || comparer == null) {
			return this;
		}

		// return elements that not in either collection
		QList<TSource> secondDistinct = second.Distinct(f -> f, comparer);
		QList<TSource> thisResult = this.Distinct(f -> f, comparer);

		//@formatter:off
		thisResult = thisResult.Concat(secondDistinct)
								.GroupBy(f -> f, comparer)
								.Where(f -> f.Count() == 1)
								.Select(f -> f.Key);
		//@formatter:on

		return thisResult;
	}

	/**
	 * Returns the first element of a sequence, or a default value if the
	 * sequence contains no elements.
	 *
	 * @return default(TSource) if source is empty; otherwise, the first element
	 *         in source.
	 */
	public TSource FirstOrDefault() {
		if (_qlist.size() == 0) {
			return null;
		}

		return _qlist.iterator().next();
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
			return FirstOrDefault();
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

	/**
	 * Performs the specified action on each element of the QList.
	 *
	 * @param action
	 *            The Action delegate to perform on each element of the QList.
	 */
	public void ForAll(Consumer<TSource> action) {
		// check if base is null
		if (action == null) {
			return;
		}

		// apply action
		for (TSource item : this) {
			action.accept(item);
		}
	}

	/**
	 * Performs the specified action on each element of the QList.
	 *
	 * @param action
	 *            The Action delegate to perform on each element of the QList.
	 */
	public void ForEach(Consumer<TSource> action) {
		ForAll(action);
	}

	/**
	 * Groups the elements of a sequence according to a specified key selector
	 * function.
	 *
	 * @param <TKey>
	 *            The type of the key returned by keySelector.
	 * @param keySelector
	 *            A function to extract the key for each element.
	 * @return A QList(Of Grouping(Of TKey, TSource)) where each Grouping(Of
	 *         TKey, TElement) object contains a sequence
	 *         of objects and a key.
	 *         if any parameters are null, return empty set
	 */
	public <TKey extends Comparable<TKey>> QList<Grouping<TKey, TSource>> GroupBy(Function<TSource, TKey> keySelector) {
		return GroupBy(keySelector, (element) -> element);
	}

	/**
	 * Groups the elements of a sequence according to a specified key selector
	 * function and compares the keys by using a
	 * specified comparer.
	 *
	 * @param <TKey>
	 *            The type of the key returned by keySelector.
	 * @param keySelector
	 *            A function to extract the key for each element.
	 * @param comparer
	 *            A Comparator(Of T) to compare keys.
	 * @return A QList(Of Grouping(Of TKey, TSource)) where each Grouping(Of
	 *         TKey, TElement) object contains a
	 *         collection of objects and a key.
	 *         if any parameters are not set, returns empty set
	 */
	public <TKey> QList<Grouping<TKey, TSource>> GroupBy(Function<TSource, TKey> keySelector,
			Comparator<TKey> comparer) {
		return GroupBy(keySelector, (element) -> element, comparer);
	}

	/**
	 * Groups the elements of a sequence according to a specified key selector
	 * function and projects the elements for
	 * each group by using a specified function.
	 *
	 * @param <TKey>
	 *            The type of the key returned by keySelector.
	 * @param <TElement>
	 *            The type of the elements in the Grouping(Of TKey, TElement).
	 * @param keySelector
	 *            A function to extract the key for each element.
	 * @param elementSelector
	 *            A function to map each source element to an element in the
	 *            Grouping(Of TKey, TElement).
	 * @return An QList(Of IGrouping(Of TKey, TElement)) where each Grouping(Of
	 *         TKey, TElement) object contains a
	 *         collection of objects of type TElement and a key.
	 */
	public <TKey extends Comparable<TKey>, TElement> QList<Grouping<TKey, TElement>> GroupBy(
			Function<TSource, TKey> keySelector, Function<TSource, TElement> elementSelector) {
		// return GroupBy(keySelector, elementSelector, (first, second) ->
		// first.compareTo(second));
		return GroupBy(keySelector,
				(key, qListOfTSource) -> new Grouping<>(key,
						qListOfTSource.Select(ts -> elementSelector.apply(ts)).ToList()),
				(first, second) -> first.compareTo(second)); // this line
															 // doesn't cause
															 // java to detect
															 // an ambiguous
															 // call
	}

	/**
	 * Groups the elements of a sequence according to a key selector function.
	 * The keys are compared by using a comparer
	 * and each group's elements are projected by using a specified function.
	 *
	 * @param <TKey>
	 *            The type of the key returned by keySelector.
	 * @param <TElement>
	 *            The type of the elements in the Grouping(Of TKey, TElement).
	 * @param keySelector
	 *            A function to extract the key for each element.
	 * @param elementSelector
	 *            A function to map each source element to an element in the
	 *            Grouping(Of TKey, TElement).
	 * @param comparer
	 *            An Comparator(Of T) to compare keys with.
	 * @return A QList(Of Grouping(Of TKey, TElement)) where each Grouping(Of
	 *         TKey, TElement) object contains a
	 *         collection of objects of type TElement and a key.
	 *         if any parameters are not set, returns empty set
	 */
	public <TKey, TElement> QList<Grouping<TKey, TElement>> GroupBy(Function<TSource, TKey> keySelector,
			Function<TSource, TElement> elementSelector, Comparator<TKey> comparer) {
		return GroupBy(keySelector, (key, qListOfTSource) -> new Grouping<>(key,
				qListOfTSource.Select(ts -> elementSelector.apply(ts)).ToList()), comparer);
	}

	/**
	 * Groups the elements of a sequence according to a specified key selector
	 * function and creates a result value from
	 * each group and its key.
	 *
	 * @param <TKey>
	 *            The type of the key returned by keySelector.
	 * @param <TResult>
	 *            The type of the result value returned by resultSelector.
	 * @param keySelector
	 *            A function to extract the key for each element.
	 * @param resultSelector
	 *            A function to create a result value from each group.
	 * @return A QList(Of TResult) where each element represents a projection
	 *         over a group and its key.
	 *         if any parameters are not set, returns empty set
	 */
	public <TKey extends Comparable<TKey>, TResult> QList<TResult> GroupBy(Function<TSource, TKey> keySelector,
			BiFunction<TKey, QList<TSource>, TResult> resultSelector) {
		return GroupBy(keySelector, (element) -> element, resultSelector);
	}

	/**
	 * Groups the elements of a sequence according to a specified key selector
	 * function and creates a result value from
	 * each group and its key. The keys are compared by using a specified
	 * comparer.
	 *
	 * @param <TKey>
	 *            The type of the key returned by keySelector.
	 * @param <TResult>
	 *            The type of the result value returned by resultSelector.
	 * @param keySelector
	 *            A function to extract the key for each element.
	 * @param resultSelector
	 *            A function to create a result value from each group.
	 * @param comparer
	 *            An Comparator(Of T) to compare keys with.
	 * @return A QList(Of TResult) where each element represents a projection
	 *         over a group and its key.
	 *         if any parameters are not set, returns empty set
	 */
	public <TKey, TResult> QList<TResult> GroupBy(Function<TSource, TKey> keySelector,
			BiFunction<TKey, QList<TSource>, TResult> resultSelector, Comparator<TKey> comparer) {
		return GroupBy(keySelector, (element) -> element, resultSelector, comparer);
	}

	/**
	 * Groups the elements of a sequence according to a specified key selector
	 * function and creates a result value from
	 * each group and its key. The elements of each group are projected by using
	 * a specified function.
	 *
	 * @param <TKey>
	 *            The type of the key returned by keySelector.
	 * @param <TElement>
	 *            The type of the elements in each Grouping(Of TKey, TElement).
	 * @param <TResult>
	 *            The type of the result value returned by resultSelector.
	 * @param keySelector
	 *            A function to extract the key for each element.
	 * @param elementSelector
	 *            A function to map each source element to an element in a
	 *            Grouping(Of TKey, TElement).
	 * @param resultSelector
	 *            A function to create a result value from each group.
	 * @return A QList(Of TResult) where each element represents a projection
	 *         over a group and its key.
	 *         if any parameters are not set, returns an empty set
	 */
	public <TKey extends Comparable<TKey>, TElement, TResult> QList<TResult> GroupBy(
			Function<TSource, TKey> keySelector, Function<TSource, TElement> elementSelector,
			BiFunction<TKey, QList<TElement>, TResult> resultSelector) {
		return GroupBy(keySelector, elementSelector, resultSelector, (first, second) -> first.compareTo(second));
	}

	/**
	 * Groups the elements of a sequence according to a specified key selector
	 * function and creates a result value from
	 * each group and its key. Key values are compared by using a specified
	 * comparer, and the elements of each group are
	 * projected by using a specified function.
	 *
	 * @param <TKey>
	 *            The type of the key returned by keySelector.
	 * @param <TElement>
	 *            The type of the elements in each Grouping(Of TKey, TElement).
	 * @param <TResult>
	 *            The type of the result value returned by resultSelector.
	 * @param keySelector
	 *            A function to extract the key for each element.
	 * @param elementSelector
	 *            A function to map each source element to an element in a
	 *            Grouping(Of TKey, TElement).
	 * @param resultSelector
	 *            A function to create a result value from each group.
	 * @param comparer
	 *            An Comparator(Of T) to compare keys with.
	 * @return A QList(Of TResult) where each element represents a projection
	 *         over a group and its key.
	 *         if any parameters are null, return empty set
	 */
	public <TKey, TElement, TResult> QList<TResult> GroupBy(Function<TSource, TKey> keySelector,
			Function<TSource, TElement> elementSelector, BiFunction<TKey, QList<TElement>, TResult> resultSelector,
			Comparator<TKey> comparer) {
		// default
		QList<TResult> result = new QList<>(new ArrayList<>());

		// parameter check
		if (keySelector == null || elementSelector == null || resultSelector == null || comparer == null) {
			return result;
		}

		// setup output of mapping
		ArrayList<TupleT2<TKey, TResult>> resultSet = new ArrayList<>();

		// setup mapping variables
		TKey lastKey = null;
		ArrayList<TElement> subElements = new ArrayList<>();

		// map values and output result
		QList<TupleT2<Integer, TSource>> orderedSourceElements = this.Select((src, i) -> new TupleT2<>(i, src))
				.OrderBy(t -> keySelector.apply(t.Item2), comparer);
		for (TupleT2<Integer, TSource> tuple : orderedSourceElements) {
			// base information
			TSource item = tuple.Item2;
			TKey localKey = keySelector.apply(item);
			TElement element = elementSelector.apply(item);

			// action based on condition
			if (lastKey == null) {
				// first grouping
				lastKey = localKey;
			}
			else if (comparer.compare(lastKey, localKey) != 0) {
				// new grouping with result add
				resultSet.add(new TupleT2<>(lastKey, resultSelector.apply(lastKey, new QList<>(subElements))));
				subElements = new ArrayList<>();
				lastKey = localKey;
			}

			// add element to set
			subElements.add(element);
		}

		// add last element calculation
		resultSet.add(new TupleT2<>(lastKey, resultSelector.apply(lastKey, new QList<>(subElements))));

		// return result
		result = new QList<>(resultSet).OrderBy(t -> t.Item1, comparer).Select(t -> t.Item2);
		return result;
	}

	/**
	 * Correlates the elements of two sequences based on equality of keys and
	 * groups the results.
	 * (LEFT JOIN inner ON innerKeySelector == outerKeySelector) using the
	 * default comparer.
	 *
	 * @param <TInner>
	 *            The type of the elements of the second sequence.
	 * @param <TKey>
	 *            The type of the keys returned by the key selector functions.
	 * @param <TResult>
	 *            The type of the result elements.
	 * @param inner
	 *            The sequence to join to the first sequence.
	 * @param outerKeySelector
	 *            A function to extract the join key from each element of the
	 *            first sequence.
	 * @param innerKeySelector
	 *            A function to extract the join key from each element of the
	 *            second sequence.
	 * @param resultSelector
	 *            A function to create a result element from an element from the
	 *            first sequence and a collection of
	 *            matching elements from the second sequence.
	 * @return A QList(Of T) that contains elements of type TResult that are
	 *         obtained by performing a grouped join on
	 *         two sequences.
	 */
	public <TInner, TKey extends Comparable<TKey>, TResult> QList<TResult> GroupJoin(QList<TInner> inner,
			Function<TSource, TKey> outerKeySelector, Function<TInner, TKey> innerKeySelector,
			BiFunction<TSource, QList<TInner>, TResult> resultSelector) {
		return GroupJoin(inner, outerKeySelector, innerKeySelector, resultSelector, (f, n) -> f.compareTo(n));
	}

	/**
	 * Correlates the elements of two sequences based on equality of keys and
	 * groups the results.
	 * (LEFT JOIN inner ON innerKeySelector == outerKeySelector) using the
	 * specified comparer.
	 *
	 * @param <TInner>
	 *            The type of the elements of the second sequence.
	 * @param <TKey>
	 *            The type of the keys returned by the key selector functions.
	 * @param <TResult>
	 *            The type of the result elements.
	 * @param inner
	 *            The sequence to join to the first sequence.
	 * @param outerKeySelector
	 *            A function to extract the join key from each element of the
	 *            first sequence.
	 * @param innerKeySelector
	 *            A function to extract the join key from each element of the
	 *            second sequence.
	 * @param resultSelector
	 *            A function to create a result element from an element from the
	 *            first sequence and a collection of
	 *            matching elements from the second sequence.
	 * @param comparer
	 *            A Comparer(Of T) to hash and compare keys.
	 * @return A QList(Of T) that contains elements of type TResult that are
	 *         obtained by performing a grouped join on
	 *         two sequences.
	 */
	public <TInner, TKey, TResult> QList<TResult> GroupJoin(QList<TInner> inner,
			Function<TSource, TKey> outerKeySelector, Function<TInner, TKey> innerKeySelector,
			BiFunction<TSource, QList<TInner>, TResult> resultSelector, Comparator<TKey> comparer) {
		Lookup<TKey, TInner> innerGroup = inner.ToLookup(innerKeySelector, comparer);
		QList<TResult> result = this
				.Select(s -> resultSelector.apply(s, innerGroup.Item(outerKeySelector.apply(s), comparer)));
		return result;
	}

	/**
	 * Produces the set intersection of two sequences by using the default
	 * equality comparer to compare values.
	 *
	 * @param <TResult>
	 *            The type of the resulting value.
	 * @param second
	 *            A QList whose distinct elements that also appear in the first
	 *            sequence will be returned.
	 * @param selector
	 *            Function grab the element to compare on
	 * @return A sequence that contains the elements that form the set
	 *         intersection of two sequences.
	 */
	public <TResult extends Comparable<TResult>> QList<TSource> Intersect(QList<TSource> second,
			Function<TSource, TResult> selector) {
		return Intersect(second, (first, next) -> selector.apply(first).compareTo(selector.apply(next)));
	}

	/**
	 * Produces the set intersection of two sequences by using the specified
	 * Comparator to compare values.
	 *
	 * @param second
	 *            A QList whose distinct elements that also appear in the first
	 *            sequence will be returned.
	 * @param comparer
	 *            A Comparator to compare values.
	 * @return A sequence that contains the elements that form the set
	 *         intersection of two sequences.
	 *         if any arguments are null, returns a zero length set
	 */
	public QList<TSource> Intersect(QList<TSource> second, Comparator<TSource> comparer) {
		// parameter check
		if (second == null || comparer == null) {
			return this;
		}

		// return elements that exist in both collections
		QList<TSource> secondDistinct = second.Distinct(f -> f, comparer);
		return this.Distinct(f -> f, comparer).Where(f -> secondDistinct.Contains(f));
	}

	/**
	 * Determines whether a sequence contains a specified element by using the
	 * default equality comparer.
	 *
	 * @param value
	 *            The value to locate in the sequence.
	 * @return true if the source sequence contains an element that has the
	 *         specified value; otherwise, false.
	 */
	public boolean Contains(TSource value) {
		return this.Any(f -> f.equals(value));
	}

	/**
	 * Determines whether a sequence contains a specified element by using a
	 * specified Comparator
	 *
	 * @param value
	 *            The value to locate in the sequence.
	 * @param comparer
	 *            An equality comparer to compare values.
	 * @return true if the source sequence contains an element that has the
	 *         specified value; otherwise, false.
	 */
	public boolean Contains(TSource value, Comparator<TSource> comparer) {
		return this.Any(f -> comparer.compare(f, value) == 0);
	}

	/**
	 * Returns the last element of a sequence, or a default value if the
	 * sequence contains no elements.
	 *
	 * @return last element or null
	 */
	public TSource LastOrDefault() {
		return LastOrDefault(r -> true);
	}

	/**
	 * Returns the last element of a sequence that satisfies a condition or a
	 * default value if no such element is found.
	 *
	 * @param p
	 *            A function to test each element for a condition.
	 * @return null if the sequence is empty or if no elements pass the test in
	 *         the predicate function; otherwise, the
	 *         last element that passes the test in the predicate function.
	 */
	public TSource LastOrDefault(Predicate<TSource> p) {
		// find last matching element
		return this.Reverse().FirstOrDefault(p);
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
	@SuppressWarnings("unchecked")
	public <TResult extends Number> TResult Max(Function<TSource, TResult> selector) {
		// find the first default value
		TResult value = this.Select(selector).OrderBy(g -> g.doubleValue()).LastOrDefault();

		// convert output value to base type
		return (TResult)Number.class.cast((value == null) ? 0 : value);
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
	@SuppressWarnings("unchecked")
	public <TResult extends Number> TResult Min(Function<TSource, TResult> selector) {
		// find the first default value
		TResult value = this.Select(selector).OrderBy(g -> g.doubleValue()).FirstOrDefault();

		// convert output value to base type
		return (TResult)Number.class.cast((value == null) ? 0 : value);
	}

	/**
	 * Sorts the elements of a sequence in ascending order according to a key.
	 *
	 * @param <TResult>
	 *            The type of the resulting value.
	 * @param keySelector
	 *            A function to extract a key from an element.
	 * @return A QList whose elements are sorted according to a key.
	 */
	public <TResult extends Comparable<TResult>> OrderedQList<TSource> OrderBy(Function<TSource, TResult> keySelector) {
		return OrderBy(keySelector, (first, second) -> first.compareTo(second));
	}

	/**
	 * Sorts the elements of a sequence in ascending order by using a specified
	 * comparer.
	 *
	 * @param <TResult>
	 *            The type of the resulting value.
	 * @param selector
	 *            A function to extract a key from an element.
	 * @param comparer
	 *            The Comparator to compare values.
	 * @return ordered collection, if any parameters are null it returns the
	 *         current collection
	 */
	public <TResult> OrderedQList<TSource> OrderBy(Function<TSource, TResult> selector, Comparator<TResult> comparer) {
		Collection<TSource> items = new ArrayList<>();

		// argument check
		if (selector != null && comparer != null) {
			// sort in ascending
			items = MergeSort.Sort(this.ToList(), selector, comparer);
		}
		
		// O(n log(n))
		return new OrderedQList<>(items, selector, comparer);
	}

	/**
	 * Sorts the elements of a sequence in descending order according to a key.
	 *
	 * @param <TResult>
	 *            The type of the resulting value.
	 * @param selector
	 *            A function to extract a key from an element.
	 * @return A QList whose elements are sorted in descending order according
	 *         to a key.
	 */
	public <TResult extends Comparable<TResult>> OrderedQList<TSource> OrderByDescending(
			Function<TSource, TResult> selector) {
		// OrderBy + O(n/2)
		return OrderByDescending(selector, (first, second) -> first.compareTo(second));
	}

	/**
	 * Sorts the elements of a sequence in descending order by using a specified
	 * comparer.
	 *
	 * @param <TResult>
	 *            The type of the resulting value.
	 * @param selector
	 *            A function to extract a key from an element.
	 * @param comparer
	 *            A Comparator to compare selection.
	 * @return A QList whose elements are sorted in descending order according
	 *         to a key.
	 */
	public <TResult> OrderedQList<TSource> OrderByDescending(Function<TSource, TResult> selector,
			Comparator<TResult> comparer) {
		List<TSource> result = OrderBy(selector, comparer).Reverse().ToList();
		return new OrderedQList<>(result, selector, comparer);
	}

	/**
	 * Inverts the order of the elements in a sequence.
	 *
	 * @return A sequence whose elements correspond to those of the input
	 *         sequence in reverse order.
	 */
	public QList<TSource> Reverse() {
		ArrayList<TSource> items = this.ToList();

		int len = items.size();
		int halfLen = len / 2;
		for (int i = 0; i < halfLen; i++) {
			int upperBound = len - 1 - i;
			TSource temp = items.get(i);
			items.set(i, items.get(upperBound));
			items.set(upperBound, temp);
		}

		return assign(items);
	}

	/**
	 * Projects each element of a sequence into a new form.
	 *
	 * @param <TResult>
	 *            The type of the resulting value.
	 * @param selector
	 *            A transform function to apply to each element.
	 * @return A QList whose elements are the result of invoking the transform
	 *         function on each element of
	 *         source.
	 */
	public <TResult> QList<TResult> Select(Function<TSource, TResult> selector) {
		return Select((e, i) -> selector.apply(e));
	}

	/**
	 * Projects each element of a sequence into a new form by incorporating the
	 * element's index.
	 *
	 * @param <TResult>
	 *            The type of the resulting value.
	 * @param selector
	 *            A transform function to apply to each source element; the
	 *            second parameter of the function represents
	 *            the index of the source element.
	 * @return A QList whose elements are the result of invoking the transform
	 *         function on each element of
	 *         source.
	 */
	public <TResult> QList<TResult> Select(BiFunction<TSource, Integer, TResult> selector) {
		// default
		List<TResult> result = new ArrayList<>();

		// parameter check
		if (selector != null) {
			// map
			int count = 0;
			for (TSource item : this) {
				result.add(selector.apply(item, count++));
			}
		}

		// return result
		return new QList<>(result);
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
	public <TResult> QList<TResult> SelectMany(Function<TSource, List<TResult>> selector) {
		return SelectMany((e, i) -> selector.apply(e));
	}

	/**
	 * Projects each element of a sequence to an IEnumerable, and flattens the
	 * resulting sequences into one sequence.
	 * The index of each source element is used in the projected form of that
	 * element.
	 *
	 * @param <TResult>
	 *            The type of the resulting value.
	 * @param selector
	 *            A transform function to apply to each source element; the
	 *            second parameter of the function represents
	 *            the index of the source element.
	 * @return A QList whose elements are the result of invoking the one-to-many
	 *         transform function on each
	 *         element
	 *         of an input sequence.
	 */
	public <TResult> QList<TResult> SelectMany(BiFunction<TSource, Integer, List<TResult>> selector) {
		return SelectMany((e, i) -> selector.apply(e, i), (s, c) -> c);
	}

	/**
	 * Projects each element of a sequence to an IEnumerable, flattens the
	 * resulting sequences into one sequence, and
	 * invokes a result selector function on each element therein.
	 *
	 * @param <TCollection>
	 *            The type of the intermediate elements collected by
	 *            collectionSelector.
	 * @param <TResult>
	 *            The type of the resulting value.
	 * @param collectionSelector
	 *            A transform function to apply to each element of the input
	 *            sequence.
	 * @param resultSelector
	 *            A transform function to apply to each element of the
	 *            intermediate sequence.
	 * @return A QList whose elements are the result of invoking the one-to-many
	 *         transform function
	 *         collectionSelector on each element of source and then mapping
	 *         each of those sequence elements and their
	 *         corresponding source element to a result element.
	 */
	public <TResult, TCollection> QList<TResult> SelectMany(Function<TSource, List<TCollection>> collectionSelector,
			BiFunction<TSource, TCollection, TResult> resultSelector) {
		return SelectMany((e, i) -> collectionSelector.apply(e), resultSelector);
	}

	/**
	 * Projects each element of a sequence to an IEnumerable, flattens the
	 * resulting sequences into one sequence, and
	 * invokes a result selector function on each element therein. The index of
	 * each source element is used in the
	 * intermediate projected form of that element.
	 *
	 * @param <TCollection>
	 *            The type of the intermediate elements collected by
	 *            collectionSelector.
	 * @param <TResult>
	 *            The type of the resulting value.
	 * @param collectionSelector
	 *            A transform function to apply to each source element; the
	 *            second parameter of the function represents
	 *            the index of the source element.
	 * @param resultSelector
	 *            A transform function to apply to each element of the
	 *            intermediate sequence.
	 * @return A QList whose elements are the result of invoking the one-to-many
	 *         transform function
	 *         collectionSelector on each element of source and then mapping
	 *         each of those sequence elements and their
	 *         corresponding source element to a result element.
	 */
	public <TResult, TCollection> QList<TResult> SelectMany(
			BiFunction<TSource, Integer, Collection<TCollection>> collectionSelector,
			BiFunction<TSource, TCollection, TResult> resultSelector) {
		// default
		QList<TResult> result = new QList<>(new ArrayList<>());

		// parameter check
		if (collectionSelector == null || resultSelector == null) {
			return result;
		}

		// map one to many to one
		//@formatter:off
		this.Select((src, i) ->
						new QList<>(collectionSelector.apply(src, i))
							.Select(tc -> resultSelector.apply(src, tc)))
					.Aggregate((old, current) -> old.Concat(current));
		//@formatter:off

		// return result
		return result;
	}

	/**
	 * Bypasses a specified number of elements in a sequence and then returns the remaining elements.
	 *
	 * @param count
	 *            The number of elements to skip before returning the remaining elements.
	 * @return A QList that contains the elements that occur after the specified index in the input sequence.
	 */
	public QList<TSource> Skip(int count)
	{
		ArrayList<TSource> items = new ArrayList<>();

		// check bounds
		if (count < 0 || count > this.Count()) {
			return this;
		}

		// skip elements
		for (TSource item : this) {
			if (count-- > 0) {
				continue;
			}
			else {
				items.add(item);
			}
		}

		return assign(items);
	}

	/**
	 * Bypasses elements in a sequence as long as a specified condition is true and then returns the remaining elements.
	 *
	 * @param p
	 *            A function to test each element for a condition.
	 * @return A QList that contains the elements from the input sequence starting at the first element in the
	 *         linear series
	 */
	public QList<TSource> SkipWhile(Predicate<TSource> p)
	{
		return SkipWhile((e, i) -> p.test(e));
	}

	/**
	 * Bypasses elements in a sequence as long as a specified condition is true and then returns the remaining elements.
	 * The element's index is used in the logic of the predicate function.
	 *
	 * @param p
	 *            A function to test each source element for a condition; the second parameter of the function
	 *            represents the index of the source element.
	 * @return A QList that contains the elements from the input sequence starting at the first element in the linear
	 *         series that does not pass the test specified by predicate.
	 *         if parameter is null, returns current collection
	 */
	public QList<TSource> SkipWhile(BiPredicate<TSource, Integer> p)
	{
		// parameter check
		if (p == null) {
			return this;
		}

		ArrayList<TSource> items = new ArrayList<>();
		// skip elements
		int count = 0;
		boolean takeRest = false;
		for (TSource item : this) {
			if (takeRest) {
				items.add(item);
			}
			else if (!p.test(item, count++))
			{
				items.add(item);
				takeRest = true;
			}
		}

		// return rest of collection after skipping
		return assign(items);
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
	public <TResult extends Number> double Sum(Function<TSource, TResult> selector)
	{
		double sum = 0;
		ArrayList<TResult> ary = this.Select(selector).ToList();
		for (TResult itm : ary) {
			sum += itm.doubleValue();
		}
		return sum;
	}

	/**
	 * Returns a specified number of contiguous elements from the start of a sequence.
	 *
	 * @param count
	 *            The number of elements to return.
	 * @return A QList that contains the specified number of elements from the start of the input sequence.
	 */
	public QList<TSource> Take(int count)
	{
		// check bounds
		if (count < 0) {
			return assign(new ArrayList<TSource>());
		}
		if (count > this.Count()) {
			return this;
		}

		// take the count
		ArrayList<TSource> items = new ArrayList<>();
		for (TSource item : this) {
			if (count-- > 0) {
				items.add(item);
			}
			else {
				break;
			}
		}

		return assign(items);
	}

	/**
	 * Returns elements from a sequence as long as a specified condition is true.
	 *
	 * @param p
	 *            A function to test each element for a condition
	 * @return A QList that contains the elements from the input sequence that occur before the element at which the
	 *         test no longer passes.
	 */
	public QList<TSource> TakeWhile(Predicate<TSource> p)
	{
		return TakeWhile((e, i) -> p.test(e));
	}

	/**
	 * Returns elements from a sequence as long as a specified condition is true. The element's index is used in the
	 * logic of the predicate function.
	 *
	 * @param p
	 *            A function to test each source element for a condition; the second parameter of the function
	 *            represents the index of the source element.
	 * @return A QList that contains elements from the input sequence that occur before the element at which the test
	 *         no longer passes.
	 */
	public QList<TSource> TakeWhile(BiPredicate<TSource, Integer> p)
	{
		// check nulls
		if (p == null) {
			return this;
		}

		// take while condition is true
		ArrayList<TSource> items = new ArrayList<>();
		int count = 0;
		for (TSource item : this) {
			if (p.test(item, count++)) {
				items.add(item);
			}
			else {
				break;
			}
		}

		return assign(items);
	}

	/**
	 * Create and array of U from the QList
	 *
	 * @param <TResult>
	 *            The type of the resulting value.
	 * @param type
	 *            type of array to convert to
	 * @return array of TResult
	 */
	@SuppressWarnings("unchecked")
	public <TResult> TResult[] ToArray(Class<TResult> type)
	{
		TResult[] ary = (TResult[])Array.newInstance(type, _qlist.size());
		int counter = 0;
		for (TSource itm : this) {
			ary[counter++] = (TResult)itm;
		}

		return ary;
	}

	/**
	 * Create an ArrayList from a QList
	 *
	 * @return ArrayList of TSource
	 */
	public ArrayList<TSource> ToList()
	{
		return new ArrayList<>(_qlist);
	}

	/**
	 * Creates a Lookup(Of TKey, TElement) from a QList(Of T) according to a specified key selector function.
	 * @param <TKey> The type of the key returned by keySelector.
	 * @param keySelector A function to extract a key from each element.
	 * @return A Lookup(Of TKey, TElement) that contains keys and values.
	 */
	public <TKey extends Comparable<TKey>> Lookup<TKey, TSource> ToLookup(Function<TSource, TKey> keySelector)
	{
		return ToLookup(keySelector, e -> e);
	}

	/**
	 * Creates a Lookup(Of TKey, TElement) from a QList(Of T) according to a specified key selector function and key comparer.
	 * @param <TKey> The type of the key returned by keySelector.
	 * @param keySelector A function to extract a key from each element.
	 * @param comparer An Comparer(of T) to compare keys.
	 * @return A Lookup(Of TKey, TElement) that contains keys and values.
	 */
	public <TKey> Lookup<TKey, TSource> ToLookup(Function<TSource, TKey> keySelector, Comparator<TKey> comparer)
	{
		return ToLookup(keySelector, e -> e, comparer);
	}

	/**
	 * Creates a Lookup(Of TKey, TElement) from a QList(Of T) according to specified key selector and element selector functions.
	 * @param <TKey> The type of the key returned by keySelector.
	 * @param <TElement> The type of the value returned by elementSelector.
	 * @param keySelector A function to extract a key from each element.
	 * @param elementSelector A transform function to produce a result element value from each element.
	 * @return A Lookup(Of TKey, TElement) that contains values of type TElement selected from the input sequence.
	 */
	public <TKey extends Comparable<TKey>, TElement> Lookup<TKey, TElement> ToLookup(Function<TSource, TKey> keySelector, Function<TSource, TElement> elementSelector)
	{
		return ToLookup(keySelector, elementSelector, (first, second) -> first.compareTo(second));
	}

	/**
	 * Creates a Lookup(Of TKey, TElement) from a QList(Of T) according to specified key selector and element selector functions.
	 * @param <TKey> The type of the key returned by keySelector.
	 * @param <TElement> The type of the value returned by elementSelector.
	 * @param keySelector A function to extract a key from each element.
	 * @param elementSelector A transform function to produce a result element value from each element.
	 * @param comparer An Comparer(of T) to compare keys.
	 * @return A Lookup(Of TKey, TElement) that contains values of type TElement selected from the input sequence.
	 */
	public <TKey, TElement> Lookup<TKey, TElement> ToLookup(Function<TSource, TKey> keySelector, Function<TSource, TElement> elementSelector, Comparator<TKey> comparer)
	{
		QList<Grouping<TKey, TElement>> result = this.GroupBy(keySelector, elementSelector, comparer);
		return new Lookup<>(result.ToList());
	}

	/**
	 * Produces the set union of two sequences by using the default comparer.
	 *
	 * @param <TSecond>
	 *            The type of the elements of the second input sequence.
	 * @param second
	 *            A QList whose distinct elements form the second set for the union.
	 * @param selector
	 *            Function to retrieve the union value from the collection
	 * @return A QList that contains the elements from both input sequences, excluding duplicates.
	 */
	public <TSecond extends Comparable<TSecond>> QList<TSource> Union(QList<TSource> second, Function<TSource, TSecond> selector)
	{
		return Union(second, selector, (first, next) -> first.compareTo(next));
	}

	/**
	 * Produces the set union of two sequences by using a specified Comparator.
	 *
	 * @param <TSecond>
	 *            The type of the elements of the second input sequence.
	 * @param second
	 *            A QList whose distinct elements form the second set for the union.
	 * @param selector
	 *            Function to retrieve the union value from the collection
	 * @param comparer
	 *            The Comparator to compare values.
	 * @return A QList that contains the elements from both input sequences, excluding duplicates.
	 */
	public <TSecond> QList<TSource> Union(QList<TSource> second, Function<TSource, TSecond> selector, Comparator<TSecond> comparer)
	{
		// parameter check
		if (second == null || selector == null || comparer == null) {
			return this;
		}

		// apply action
		return this.Concat(second).Distinct(selector, comparer);
	}

	/**
	 * Filters a sequence of values based on a predicate.
	 *
	 * @param p
	 *            A function to test each element for a condition.
	 * @return A QList that contains elements from the input sequence that satisfy the condition.
	 */
	public QList<TSource> Where(Predicate<TSource> p)
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
	public QList<TSource> Where(BiPredicate<TSource, Integer> p)
	{
		//default
		ArrayList<TSource> items = new ArrayList<>();

		//parameter check
		if(p == null) {
			return new QList<>(items);
		}

		// find matching items against predicate to add to new list
		int count = 0;
		for (TSource item : this) {
			if (p.test(item, count++)) {
				items.add(item);
			}
		}

		return assign(items);
	}

	/**
	 * Applies a specified function to the corresponding elements of two sequences, producing a sequence of the results.
	 *
	 * @param <TSecond>
	 *            The type of the elements of the second input sequence.
	 * @param <TResult>
	 *            The type of the resulting value.
	 * @param second
	 *            The second input sequence.
	 * @param selector
	 *            A function that specifies how to combine the corresponding elements of the two sequences.
	 * @return A QList that contains elements of the two input sequences, combined by selector,
	 *         if any arguments are null, a zero length set
	 */
	public <TSecond, TResult> QList<TResult> Zip(QList<TSecond> second, BiFunction<TSource, TSecond, TResult> selector)
	{
		// null check
		if (second == null || selector == null) {
			return new QList<>(new ArrayList<TResult>());
		}

		// setup and select minimum count between the two collections
		ArrayList<TResult> result = new ArrayList<>();
		int min = Math.min(this.Count(), second.Count());

		// map the zip selector function on the two collections up to the minimum count of both
		for (int i = 0; i < min; i++) {
			result.add(selector.apply(this.ElementAtOrDefault(i), second.ElementAtOrDefault(i)));
		}

		return new QList<>(result);
	}

	// #EndRegion
	// #Region "Iterator

	@Override
	public Iterator<TSource> iterator()
	{
		return _qlist.iterator();
	}

	// #EndRegion
}
