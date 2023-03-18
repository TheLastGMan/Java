package Generic;

public class LinkedListNode<T>
{
	LinkedListNode()
	{}
	
	LinkedListNode(T item)
	{
		Value = item;
	}
	
	// #region properties
	
	public T Value;
	
	public LinkedListNode<T> Previous()
	{
		return _previous;
	}
	
	public void setPrevious(LinkedListNode<T> value)
	{
		_previous = value;
	}
	
	private LinkedListNode<T> _previous;
	
	public boolean HasPrevious()
	{
		return Previous() != null;
	}
	
	public LinkedListNode<T> Next()
	{
		return _next;
	}
	
	public void setNext(LinkedListNode<T> value)
	{
		_next = value;
	}
	
	private LinkedListNode<T> _next;
	
	public boolean HasNext()
	{
		return Previous() != null;
	}
	
	// #endregion
}
