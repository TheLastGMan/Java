/**
 *
 */
package System.Collections.Generic;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

import System.Collections.IList;

/**
 * Represents a strongly typed list of objects that can be accessed by index. Provides methods to search, sort, and
 * manipulate lists.
 * Based on Microsoft's System.Collections.Generic.List
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class List<T> extends IList<T> implements IReadOnlyCollection<T>, Serializable
{
	private static final long serialVersionUID = 6375453409339867463L;
	private final ArrayList<T> _backing;

	public List()
	{
		this(16);
	}

	public List(int capacity)
	{
		super();
		_backing = new ArrayList<>(capacity);
	}

	public List(Iterable<T> collection)
	{
		this();
		
		// add collection
		for (T item : collection)
			_backing.add(item);
	}

	/*
	 * (non-Javadoc)
	 * @see System.Collections.ICollection#Count()
	 */
	@Override
	public int Count()
	{
		return _backing.size();
	}

	public <TOutput> List<TOutput> ConvertAll(Function<T, TOutput> converter)
	{
		return Select(converter).ToList();
	}
	
	/*
	 * (non-Javadoc)
	 * @see System.Collections.ICollection#Add(java.lang.Object)
	 */
	public void Add(T item)
	{
		_backing.add(item);
	}
	
	public void Clear()
	{
		_backing.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see System.Collections.ICollection#Contains(java.lang.Object)
	 */
	@Override
	public boolean Contains(T item)
	{
		return _backing.contains(item);
	}

	public boolean Remove(T item)
	{
		return _backing.remove(item);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<T> iterator()
	{
		return _backing.iterator();
	}

	/*
	 * (non-Javadoc)
	 * @see System.Collections.IList#get(int)
	 */

	@Override
	public T Get(int index)
	{
		return _backing.get(index);
	}

	/*
	 * (non-Javadoc)
	 * @see System.Collections.IList#set(int, java.lang.Object)
	 */
	@Override
	public void Set(int index, T value)
	{
		_backing.set(index, value);
	}

	/*
	 * (non-Javadoc)
	 * @see System.Collections.IList#IndexOf(java.lang.Object)
	 */
	@Override
	public int IndexOf(T item)
	{
		return _backing.indexOf(item);
	}

	/*
	 * (non-Javadoc)
	 * @see System.Collections.IList#Insert(int, java.lang.Object)
	 */
	@Override
	public void Insert(int index, T item)
	{
		_backing.add(index, item);
	}

	/*
	 * (non-Javadoc)
	 * @see System.Collections.IList#RemoveAt(int)
	 */
	@Override
	public void RemoveAt(int index)
	{
		_backing.remove(index);
	}

	// Adds the elements of the given collection to the end of this list. If
	// required, the capacity of the list is increased to twice the previous
	// capacity or the new size, whichever is larger.
	public void AddRange(Iterable<T> collection)
	{
		InsertRange(_backing.size(), collection);
	}
	
	// Inserts the elements of the given collection at a given index. If
	// required, the capacity of the list is increased to twice the previous
	// capacity or the new size, whichever is larger. Ranges may be added
	// to the end of the list by setting index to the List's size.
	public void InsertRange(int index, Iterable<T> collection)
	{
		for (T item : collection)
			Insert(index++, item);
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
}
