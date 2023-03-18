package Generic;

import java.io.InvalidObjectException;
import java.util.*;

public class Queue<T>
{
	private ArrayList<T> _list = new ArrayList<T>();
	
	// #region Properties
	
	public int Count()
	{
		return _list.size();
	}
	
	// #endregion
	
	// #region Methods
	
	public void Clear()
	{
		_list.clear();
	}
	
	public boolean Contains(T item) throws InvalidObjectException
	{
		if (Count() == 0)
			throw new InvalidObjectException("The Queue is empty");
		
		return _list.contains(item);
	}
	
	public T Dequeue() throws InvalidObjectException
	{
		if (Count() == 0)
			throw new InvalidObjectException("The Queue is empty");
		
		T item = _list.get(0);
		_list.remove(0);
		return item;
	}
	
	public void Enqueue(T item)
	{
		_list.add(item);
	}
	
	public T Peek() throws InvalidObjectException
	{
		if (Count() == 0)
			throw new InvalidObjectException("The Queue is empty");
		
		return _list.get(0);
	}
	
	// #endregion
}
