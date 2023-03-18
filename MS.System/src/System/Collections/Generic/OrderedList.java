/**
 *
 */
package System.Collections.Generic;

import System.Collections.IComparer;

/**
 * Represents a collection of key/value pairs that are sorted by the keys and are accessible by key and by index.
 * Based on Microsoft's System.Collections.Generic.SortedList
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class OrderedList<T> extends List<T>
// IReadOnlyDictionary<TKey,TValue>
{
	private static final long serialVersionUID = 2518115637332507016L;
	private final IComparer<T> _comparer;

	public OrderedList(IComparer<T> comparer)
	{
		this(0, comparer);
	}
	
	public OrderedList(int capacity, IComparer<T> comparer)
	{
		super();
		if (capacity < 0)
			capacity = 0;
		
		_comparer = comparer;
	}
	
	@Override
	public void Add(T value)
	{
		for (int i = 0; i < super.Count(); i++)
			if (_comparer.compare(value, super.Get(i)) >= 0)
			{
				Insert(i, value);
				return;
			}

		// add to end
		super.Add(value);
	}
	
	@Override
	public void AddRange(Iterable<T> collection)
	{
		for (T value : collection)
			Add(value);
	}
}
