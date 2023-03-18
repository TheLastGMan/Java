/**
 *
 */
package System.Collections.Generic;

import java.io.Serializable;
import java.util.*;

import System.Collections.*;
import System.Linq.Enumerable;

/**
 * Represents a collection of key/value pairs that are sorted by the keys and are accessible by key and by index.
 * Based on Microsoft's System.Collections.Generic.SortedList
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class SortedList<TKey, TValue> extends IDictionary<TKey, TValue> implements Serializable
// IReadOnlyDictionary<TKey,TValue>
{
	private static final long serialVersionUID = 2518115637332507015L;
	private final IComparer<TKey> _comparer;
	private final ArrayList<TKey> _keyBacking;
	private final ArrayList<TValue> _valueBacking;
	
	public SortedList(IComparer<TKey> comparer)
	{
		this(0, comparer);
	}

	public SortedList(int capacity, IComparer<TKey> comparer)
	{
		if (capacity < 0)
			capacity = 0;

		_comparer = comparer;
		_keyBacking = new ArrayList<>();
		_valueBacking = new ArrayList<>();
	}

	/*
	 * (non-Javadoc)
	 * @see System.Collections.Generic.IDictionary#Get(java.lang.Object)
	 */
	@Override
	public TValue Get(TKey key) throws Exception
	{
		int index = IndexOfKey(key);
		return index >= 0 ? _valueBacking.get(index) : null;
	}

	/*
	 * (non-Javadoc)
	 * @see System.Collections.Generic.IDictionary#Set(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void Set(TKey key, TValue value) throws Exception
	{
		Add(key, value);
	}

	public TValue Get(int index)
	{
		return _valueBacking.get(index);
	}

	public void Set(int index, TValue value)
	{
		_valueBacking.set(index, value);
	}

	/*
	 * (non-Javadoc)
	 * @see System.Collections.Generic.IDictionary#Keys()
	 */
	@Override
	public ICollection<TKey> Keys()
	{
		return new Enumerable<>(_keyBacking).ToList();
	}

	/*
	 * (non-Javadoc)
	 * @see System.Collections.Generic.IDictionary#Values()
	 */
	@Override
	public ICollection<TValue> Values()
	{
		return new Enumerable<>(_valueBacking).ToList();
	}

	/*
	 * (non-Javadoc)
	 * @see System.Collections.Generic.IDictionary#ContainsKey(java.lang.Object)
	 */
	@Override
	public boolean ContainsKey(TKey key)
	{
		return _keyBacking.contains(key);
	}

	/*
	 * (non-Javadoc)
	 * @see System.Collections.Generic.IDictionary#Add(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void Add(TKey key, TValue value)
	{
		for (int i = 0; i < _keyBacking.size(); i++)
			if (_comparer.compare(_keyBacking.get(i), key) >= 0)
			{
				Insert(i, key, value);
				return;
			}

		// add to end
		Insert(_keyBacking.size(), key, value);
	}

	public void Insert(int index, TKey key, TValue value)
	{
		_keyBacking.add(index, key);
		_valueBacking.add(index, value);
	}
	
	public int IndexOfKey(TKey key)
	{
		return _keyBacking.indexOf(key);
	}
	
	public int IndexOfValue(TValue value)
	{
		return _valueBacking.indexOf(value);
	}

	/*
	 * (non-Javadoc)
	 * @see System.Collections.Generic.IDictionary#Remove(java.lang.Object)
	 */
	@Override
	public boolean Remove(TKey key)
	{
		int index = _keyBacking.indexOf(key);
		if (index >= 0)
		{
			_keyBacking.remove(index);
			_valueBacking.remove(index);
			return true;
		}
		return false;
	}

	public void RemoveAt(int index)
	{
		_keyBacking.remove(index);
		_valueBacking.remove(index);
	}

	/*
	 * (non-Javadoc)
	 * @see System.Collections.Generic.IDictionary#TryGetValue(java.lang.Object)
	 */
	@Override
	public TValue TryGetValue(TKey key)
	{
		int index = _keyBacking.indexOf(key);
		return index >= 0 ? _valueBacking.get(index) : null;
	}

	/*
	 * (non-Javadoc)
	 * @see System.Collections.Generic.ICollection#Count()
	 */
	@Override
	public int Count()
	{
		return _keyBacking.size();
	}

	/*
	 * (non-Javadoc)
	 * @see System.Collections.Generic.ICollection#SyncRoot()
	 */
	@Override
	public Object SyncRoot()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see System.Collections.Generic.ICollection#IsSynchronized()
	 */
	@Override
	public boolean IsSynchronized()
	{
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see System.Linq.IEnumerable#iterator()
	 */
	@Override
	public Iterator<KeyValuePair<TKey, TValue>> iterator()
	{
		return new SortedListEnumerator(this);
	}
	
	private final class SortedListEnumerator implements Iterator<KeyValuePair<TKey, TValue>>
	{
		private SortedList<TKey, TValue> _sortedList;
		private int index = 0;

		public SortedListEnumerator(SortedList<TKey, TValue> sorted)
		{
			_sortedList = sorted;
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext()
		{
			return _sortedList.Get(index) != null;
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		@Override
		public KeyValuePair<TKey, TValue> next()
		{
			return new KeyValuePair<>(_sortedList.Keys().ElementAtOrDefault(index), _sortedList.Values().ElementAtOrDefault(index++));
		}
	}
}
