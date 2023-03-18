/**
 *
 */
package System.Collections.Generic;

import java.io.Serializable;
import java.util.*;

import System.Collections.ICollection;
import System.Linq.IEnumerable;

/**
 * Represents a first-in, first-out collection of objects.
 * Based on Microsoft's System.Collections.Generic.Queue
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class Queue<T> extends ICollection<T> implements Serializable, IReadOnlyCollection<T>
{
	private static final long serialVersionUID = -1902394791086738084L;
	private final ArrayList<T> _backing;

	public Queue()
	{
		_backing = new ArrayList<>();
	}

	public Queue(int capacity)
	{
		if (capacity < 0)
			capacity = 0;
		_backing = new ArrayList<>(capacity);
	}

	public Queue(IEnumerable<T> collection)
	{
		this();

		// validate
		if (collection == null)
			return;

		// add collection
		for (T item : collection)
			Enqueue(item);
	}

	public void Clear()
	{
		_backing.clear();
	}

	@Override
	public boolean Contains(T item)
	{
		return _backing.contains(item);
	}

	@Override
	public int Count()
	{
		return _backing.size();
	}

	public T Dequeue()
	{
		return _backing.remove(0);
	}

	public void Enqueue(T item)
	{
		_backing.add(item);
	}

	public T Peek()
	{
		return _backing.get(0);
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
