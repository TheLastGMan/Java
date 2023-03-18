/**
 *
 */
package System.Collections.Generic;

import java.io.Serializable;
import java.util.*;

import System.Collections.ICollection;
import System.Linq.IEnumerable;

/**
 * Represents a variable size last-in-first-out (LIFO) collection of instances of the same specified type.
 * Based on Microsoft's System.Collections.Generic.Stack
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class Stack<T> extends ICollection<T> implements Serializable, IReadOnlyCollection<T>
{
	private static final long serialVersionUID = -7394512169656101399L;
	private final ArrayList<T> _backing;

	public Stack()
	{
		_backing = new ArrayList<>();
	}

	public Stack(int capacity)
	{
		if (capacity < 0)
			capacity = 0;
		_backing = new ArrayList<>(capacity);
	}

	public Stack(IEnumerable<T> collection)
	{
		this();

		// validate
		if (collection == null)
			return;

		// add collection
		for (T item : collection)
			Push(item);
	}

	@Override
	public int Count()
	{
		return _backing.size();
	}

	@Override
	public boolean Contains(T item)
	{
		return _backing.contains(item);
	}

	public T Peek()
	{
		return _backing.get(0);
	}

	public T Pop()
	{
		return _backing.remove(0);
	}

	public void Push(T item)
	{
		_backing.add(0, item);
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
	public Iterator<T> iterator()
	{
		return _backing.iterator();
	}
	
	/*
	 * (non-Javadoc)
	 * @see System.Collections.Generic.IReadOnlyCollection#Get(int)
	 */
	@Override
	public T Get(int index)
	{
		return _backing.get(index);
	}
}
