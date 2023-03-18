package Generic;

import java.lang.reflect.Array;

/**
 * List using dynamic sized array
 * 
 * @author rgau1
 * @param <T>
 */
public class List<T>
{
	@SuppressWarnings("rawtypes")
	private Class _baseClass;
	private int _initCapacity = 5;
	private T[] _array;
	
	public List(T typeExample)
	{
		@SuppressWarnings("unchecked")
		final T[] ary = (T[])Array.newInstance(typeExample.getClass(), _initCapacity);
		_array = ary;
		_baseClass = typeExample.getClass();
	}
	
	public List(T typeExample, int capacity)
	{
		@SuppressWarnings("unchecked")
		final T[] ary = (T[])Array.newInstance(typeExample.getClass(), capacity);
		_array = ary;
		_capacity = capacity;
		_baseClass = typeExample.getClass();
	}
	
	// #region Properties
	
	private int _capacity = _initCapacity;
	
	public int Capacity()
	{
		return _capacity;
	}
	
	private int _count;
	
	public int Count()
	{
		return _count;
	}
	
	public T getItem(int index) throws Exception
	{
		if (index < 0 || index >= Count())
			throw new Exception("Index out of range.");
		
		return _array[index];
	}
	
	public void setItem(T item, int index) throws Exception
	{
		if (index < 0 || index >= Count())
			throw new Exception("Index out of range.");
		
		_array[index] = item;
	}
	
	// #endregion
	
	// #region Methods
	
	private void resize()
	{
		T[] _tmp = _array.clone();
		_capacity *= 2;
		
		@SuppressWarnings("unchecked")
		final T[] ary = (T[])Array.newInstance(_baseClass, _capacity);
		_array = ary;
		
		for (int i = 0; i < (Count() - 1); i++)
			_array[i] = _tmp[i];
	}
	
	public void Add(T item)
	{
		_count++;
		if (_count > Capacity())
			resize();
		
		_array[Count() - 1] = item;
	}
	
	public void Clear()
	{
		_count = 0;
	}
	
	public boolean Contains(T item)
	{
		for (int i = 0; i < Count(); i++)
			if (_array[i] == item)
				return true;
		
		return false;
	}
	
	/**
	 * Find the index of the given element
	 * 
	 * @param item
	 *            to find
	 * @return index position or -1 if not found
	 */
	public int IndexOf(T item)
	{
		for (int i = 0; i < Count(); i++)
		{
			if (_array[i] == item)
				return i;
		}
		
		return -1;
	}
	
	// #endregion
	
}
