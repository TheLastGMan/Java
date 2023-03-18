package Generic;

public class LinkedListNodeSingle<T>
{
	LinkedListNodeSingle()
	{}
	
	LinkedListNodeSingle(T item)
	{
		Value = item;
	}
	
	// #region Properties
	
	public T Value;
	
	private LinkedListNodeSingle<T> _next;
	
	public LinkedListNodeSingle<T> Next()
	{
		return _next;
	}
	
	public void setNext(LinkedListNodeSingle<T> value)
	{
		_next = value;
	}
	
	public boolean HasNext()
	{
		return Next() != null;
	}
	
	// #endregion
}
