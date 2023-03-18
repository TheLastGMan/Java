package System.Linq;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import System.Collections.MergeSort;

/**
 * Represents a sorted sequence.
 *
 * @author Ryan Gau
 * @param <TElement>
 *            The type of the elements of the sequence.
 */
public final class OrderedQList<TElement> extends QList<TElement>
{
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
	public <TKey> OrderedQList(Collection<TElement> base, Function<TElement, TKey> previousSelector, Comparator<TKey> previousComparer)
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
	private <TKey> OrderedQList<TElement> sort(Function<TElement, TKey> keySelector, Comparator<TKey> comparer, boolean sortASC)
	{
		// argument check
		if (keySelector == null || comparer == null)
		{
			return this;
		}
		
		// linear search by previous info, order groups
		List<TElement> newOrdered = new ArrayList<>();
		List<TElement> subGroup = new ArrayList<>();
		TKey lastKey = null;
		for (TElement item : this)
		{
			TKey currentKey = ((Function<TElement, TKey>)_previousKeySelector).apply(item);
			
			if (lastKey == null)
			{
				// first item, just assign
				lastKey = currentKey;
			}
			else if (((Comparator<TKey>)_previousComparer).compare(currentKey, lastKey) != 0)
			{
				// new group
				Collection<TElement> sorted = MergeSort.Sort(subGroup, keySelector, comparer);
				newOrdered.addAll(sortASC ? sorted : new QList<>(sorted).Reverse().ToList());
				subGroup = new ArrayList<>();
				lastKey = currentKey;
			}
			
			// add to current group
			subGroup.add(item);
		}
		
		// last sorting
		Collection<TElement> lastSorted = MergeSort.Sort(subGroup, keySelector, comparer);
		newOrdered.addAll((sortASC) ? lastSorted : new QList<>(lastSorted).Reverse().ToList());
		
		// return updated sort
		return new OrderedQList<>(newOrdered, keySelector, comparer);
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
	public <TKey extends Comparable<TKey>> OrderedQList<TElement> ThenBy(Function<TElement, TKey> keySelector)
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
	
	public <TKey> OrderedQList<TElement> ThenBy(Function<TElement, TKey> keySelector, Comparator<TKey> comparer)
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
	public <TKey extends Comparable<TKey>> OrderedQList<TElement> ThenByDescending(Function<TElement, TKey> keySelector)
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
	public <TKey> OrderedQList<TElement> ThenByDescending(Function<TElement, TKey> keySelector, Comparator<TKey> comparer)
	{
		return sort(keySelector, comparer, false);
	}
}
