import java.util.*;
import java.util.function.Function;

/**
 * Sort a collection using MergeSort<br>
 * ---------------------------------<br>
 * Time Complexity:<br>
 * Best: O(n log(n))<br>
 * Average: O(n log(n))<br>
 * Worst: O(n log(n))<br>
 * ---------------------------------<br>
 * Space Complexity:<br>
 * Worst: O(n)<br>
 *
 * @author Ryan Gau
 * @version 1.0-ICS440A2
 */
public class MergeSort {
	/**
	 * @param items
	 *            Collection to sort using the default comparer
	 * @param <TSource>
	 *            Type of the element in the collection
	 * @return ordered collection in ascending order
	 */
	public static <TSource extends Comparable<TSource>> Collection<TSource> Sort(List<TSource> items) {
		return Sort(items, f -> f);
	}

	/**
	 * Sort the collection by the selected key using the default comparer
	 *
	 * @param <TSource>
	 *            Type of input selection
	 * @param <TKey>
	 *            Type of key to order by
	 * @param items
	 *            Collection to sort
	 * @param selector
	 *            Function to select the element to sort by
	 * @return ordered collection in ascending order
	 */
	public static <TSource, TKey extends Comparable<TKey>> List<TSource> Sort(List<TSource> items,
			Function<TSource, TKey> selector) {
		return Sort(items, selector, (first, second) -> first.compareTo(second));
	}

	/**
	 * Sort the collection by the selected key using the specified comparer
	 *
	 * @param <TSource>
	 *            Type of input selection
	 * @param <TKey>
	 *            Type of key to order by
	 * @param items
	 *            Collection to sort
	 * @param selector
	 *            Function to select the element to sort by
	 * @param comparer
	 *            A Comparator to compare selection.
	 * @return ordered collection according to the comparer
	 */
	public static <TSource, TKey> List<TSource> Sort(List<TSource> items, Function<TSource, TKey> selector,
			Comparator<TKey> comparer) {
		// parameter check
		if (items == null || selector == null || comparer == null || !items.iterator().hasNext()) {
			return items;
		}

		// sort
		List<TSource> values = splitMergeArray(items, selector, comparer);
		return values;
	}

	/**
	 * Split array into a left and right hand at its midpoint and merge back
	 * together
	 *
	 * @param <TSource>
	 *            Type of input selection
	 * @param <TKey>
	 *            Type of key to order by
	 * @param values
	 *            value to split and merge
	 * @param selector
	 *            access to underlying comparable to sort by
	 * @param comparer
	 *            A Comparator to compare selection.
	 * @return ordered array
	 */
	private static <TSource, TKey> List<TSource> splitMergeArray(List<TSource> values, Function<TSource, TKey> selector,
			Comparator<TKey> comparer) {
		@SuppressWarnings("unchecked")
		TSource[] output = (TSource[])(new Object[values.size()]);
		splitMergeArrayIndex(values, 0, values.size() - 1, output, selector, comparer);
		return values;
	}

	private static <TSource, TKey> void splitMergeArrayIndex(List<TSource> values, int lowerBound, int upperBound,
			TSource[] valuesSorted, Function<TSource, TKey> selector, Comparator<TKey> comparer) {
		// check for one item, no need to split any further
		if (upperBound - lowerBound <= 0) {
			return;
		}

		int middleBound = (lowerBound + upperBound) / 2;
		splitMergeArrayIndex(values, lowerBound, middleBound, valuesSorted, selector, comparer);
		splitMergeArrayIndex(values, middleBound + 1, upperBound, valuesSorted, selector, comparer);
		mergeArrayIndex(values, lowerBound, middleBound, middleBound + 1, upperBound, valuesSorted, selector, comparer);
		copyArray(valuesSorted, lowerBound, upperBound, values);
	}

	private static <TSource, TKey> void mergeArrayIndex(List<TSource> values, int lowerBoundLeft, int upperBoundLeft,
			int lowerBoundRight, int upperBoundRight, TSource[] result, Function<TSource, TKey> selector,
			Comparator<TKey> comparer) {
		for (int index = lowerBoundLeft; index <= upperBoundRight; index++) {
			if (lowerBoundLeft <= upperBoundLeft && lowerBoundRight <= upperBoundRight) {
				// compare the two
				if (comparer.compare(selector.apply(values.get(lowerBoundLeft)),
						selector.apply(values.get(lowerBoundRight))) <= 0) {
					result[index] = values.get(lowerBoundLeft);
					lowerBoundLeft += 1;
				}
				else {
					result[index] = values.get(lowerBoundRight);
					lowerBoundRight += 1;
				}
			}
			else if (lowerBoundLeft <= upperBoundLeft) {
				// take left hand side
				result[index] = values.get(lowerBoundLeft);
				lowerBoundLeft += 1;
			}
			else {
				// take right hand side
				result[index] = values.get(lowerBoundRight);
				lowerBoundRight += 1;
			}
		}
	}

	private static <TSource> void copyArray(TSource[] source, int lowerBound, int upperBound,
			List<TSource> destination) {
		while (lowerBound <= upperBound) {
			destination.set(lowerBound, source[lowerBound]);
			lowerBound += 1;
		}
	}
}
