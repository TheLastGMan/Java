package System.Linq;

import java.util.Comparator;
import java.util.function.Function;

import System.Collections.Generic.*;

/**
 * Represents a sorted sequence.
 *
 * @author Ryan Gau
 * @param <TElement>
 *            The type of the elements of the sequence.
 */
public final class OrderedEnumerable<TElement> extends List<TElement>
{
	private static final long serialVersionUID = -2842425478971825763L;
	private Function<?, ?> _previousKeySelector = null;
	private Comparator<?> _previousComparer = null;

	/**
	 * @param <TKey>
	 *            The type of the key returned by previousSelector.
	 * @param base
	 *            Previous ordered collection
	 * @param previousSelector
	 *            Previous KeySelector used to extract the key from the element
	 * @param previousComparer
	 *            Previous KeyComparer used to compare keys
	 */
	public <TKey> OrderedEnumerable(IEnumerable<TElement> base, Function<TElement, TKey> previousSelector, Comparator<TKey> previousComparer)
	{
		super(base);
		_previousKeySelector = previousSelector;
		_previousComparer = previousComparer;
	}

	/**
	 * Performs a subsequent ordering of the elements in a sequence in ascending order according to a key.
	 *
	 * @param <TKey>
	 *            The type of the key returned by keySelector.
	 * @param keySelector
	 *            A function to extract a key from each element.
	 * @return An OrderedQList(Of TElement) whose elements are sorted according to a key.
	 */
	@SuppressWarnings("unchecked")
	private <TKey> OrderedEnumerable<TElement> sort(Function<TElement, TKey> keySelector, Comparator<TKey> comparer, boolean sortASC)
	{
		// argument check
		if (keySelector == null || comparer == null)
			return this;

		// linear search by previous info, order groups
		List<TElement> newOrdered = new List<>();
		List<TElement> subGroup = new List<>();
		TKey lastKey = null;
		for (TElement item : this)
		{
			TKey currentKey = ((Function<TElement, TKey>)_previousKeySelector).apply(item);

			if (lastKey == null)
				// first item, just assign
				lastKey = currentKey;
			else if (((Comparator<TKey>)_previousComparer).compare(currentKey, lastKey) != 0)
			{
				// new group
				IEnumerable<TElement> sorted = MergeSort.Sort(subGroup, keySelector, comparer);
				newOrdered.AddRange(sortASC ? sorted : new List<>(sorted).Reverse().ToList());
				subGroup = new List<>();
				lastKey = currentKey;
			}

			// add to current group
			subGroup.Add(item);
		}

		// last sorting
		IEnumerable<TElement> lastSorted = MergeSort.Sort(subGroup, keySelector, comparer);
		newOrdered.AddRange((sortASC) ? lastSorted : new List<>(lastSorted).Reverse().ToList());

		// return updated sort
		return new OrderedEnumerable<>(newOrdered, keySelector, comparer);
	}

	/**
	 * Performs a subsequent ordering of the elements in a sequence in ascending order according to a key.
	 *
	 * @param <TKey>
	 *            The type of the key returned by keySelector.
	 * @param keySelector
	 *            A function to extract a key from each element.
	 * @return An OrderedQList(Of TElement) whose elements are sorted according to a key.
	 */
	public <TKey extends Comparable<TKey>> OrderedEnumerable<TElement> ThenBy(Function<TElement, TKey> keySelector)
	{
		return ThenBy(keySelector, (first, second) -> first.compareTo(second));
	}

	/**
	 * Performs a subsequent ordering of the elements in a sequence in ascending order by using a specified comparer.
	 *
	 * @param <TKey>
	 *            The type of the key returned by keySelector.
	 * @param keySelector
	 *            A function to extract a key from each element.
	 * @param comparer
	 *            An Comparator(Of T) to compare keys.
	 * @return An OrderedQList(Of TElement) whose elements are sorted according to a key.
	 */

	public <TKey> OrderedEnumerable<TElement> ThenBy(Function<TElement, TKey> keySelector, Comparator<TKey> comparer)
	{
		return sort(keySelector, comparer, true);
	}

	/**
	 * Performs a subsequent ordering of the elements in a sequence in descending order, according to a key.
	 *
	 * @param <TKey>
	 *            The type of the key returned by keySelector.
	 * @param keySelector
	 *            A function to extract a key from each element.
	 * @return An OrderedQList(Of TElement) whose elements are sorted in descending order according to a key.
	 */
	public <TKey extends Comparable<TKey>> OrderedEnumerable<TElement> ThenByDescending(Function<TElement, TKey> keySelector)
	{
		return ThenByDescending(keySelector, (l, r) -> l.compareTo(r));
	}

	/**
	 * Performs a subsequent ordering of the elements in a sequence in descending order by using a specified comparer.
	 *
	 * @param <TKey>
	 *            The type of the key returned by keySelector.
	 * @param keySelector
	 *            A function to extract a key from each element.
	 * @param comparer
	 *            An Comparator(Of T) to compare keys.
	 * @return An OrderedQList(Of TElement) whose elements are sorted in descending order according to a key.
	 */
	public <TKey> OrderedEnumerable<TElement> ThenByDescending(Function<TElement, TKey> keySelector, Comparator<TKey> comparer)
	{
		return sort(keySelector, comparer, false);
	}
}
