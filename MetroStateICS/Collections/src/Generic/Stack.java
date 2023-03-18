package Generic;

import java.io.InvalidObjectException;
import java.util.*;

public class Stack<T>
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
			throw new InvalidObjectException("The Stack is empty");
		
		return _list.contains(item);
	}
	
	public T Pop() throws InvalidObjectException
	{
		if (Count() == 0)
			throw new InvalidObjectException("The Stack is empty");
		
		T item = _list.get(Count() - 1);
		_list.remove(Count() - 1);
		return item;
	}
	
	public void Push(T item)
	{
		_list.add(item);
	}
	
	public T Peek() throws InvalidObjectException
	{
		if (Count() == 0)
			throw new InvalidObjectException("The Stack is empty");
		
		return _list.get(Count() - 1);
	}
	
	// #endregion
}
