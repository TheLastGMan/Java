package Generic;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Represents a doubly linked list
 * 
 * @author Ryan Gau
 * @param <T>
 *            Type
 */
public class LinkedList<T> implements Iterable<T>
{
	// #region Properties
	
	/**
	 * Gets the number of nodes actually contained in the LinkedList
	 * 
	 * @return
	 */
	public int Count()
	{
		return _count;
	}
	
	private int _count;
	
	/**
	 * Gets the first node of the LinkedList
	 * 
	 * @return
	 */
	public LinkedListNode<T> First()
	{
		return _first;
	}
	
	private LinkedListNode<T> _first;
	
	/**
	 * Gets the last node of the LinkedList
	 * 
	 * @return
	 */
	public LinkedListNode<T> Last()
	{
		return _last;
	}
	
	private LinkedListNode<T> _last;
	
	// #endregion
	
	// #region Methods
	
	/**
	 * Adds a new node containing the specified value after the specified existing node in the LinkedList
	 * 
	 * @param node
	 * @param newNode
	 */
	public void AddAfter(LinkedListNode<T> node, LinkedListNode<T> newNode)
	{
		newNode.setPrevious(node);
		node.setNext(newNode);
		
		if (node.HasNext())
			node.Next().setPrevious(newNode);
		else
			_last = newNode;
		
		node.setNext(newNode);
		_count++;
	}
	
	/**
	 * Adds a new node containing the specified value before the specified existing node in the LinkedList
	 * 
	 * @param node
	 * @param newNode
	 */
	public void AddBefore(LinkedListNode<T> node, LinkedListNode<T> newNode)
	{
		newNode.setPrevious(node.Previous());
		newNode.setNext(node);
		
		if (node.HasPrevious())
			node.Previous().setNext(newNode);
		else
			_first = newNode;
		
		node.setNext(newNode);
		_count++;
	}
	
	/**
	 * Adds a new node containing the specified value at the start of the LinkedList
	 * 
	 * @param item
	 */
	public void AddFirst(T item)
	{
		LinkedListNode<T> node = new LinkedListNode<T>(item);
		
		if (First() == null)
		{
			_first = node;
			_last = node;
			node.setPrevious(null);
			node.setNext(null);
		}
		else
			AddBefore(First(), node);
	}
	
	/**
	 * Adds a new node containing the specified value at the end of the LinkedList
	 * 
	 * @param item
	 */
	public void AddLast(T item)
	{
		if (Last() != null)
			AddFirst(item);
		else
			AddAfter(Last(), new LinkedListNode<T>(item));
	}
	
	private void removeNode(LinkedListNode<T> node)
	{
		// logic if this is the first node
		if (node.HasPrevious())
			node.Previous().setNext(node.Next());
		else
			_first = node.Next();
		
		// logic if this is the last node
		if (node.HasNext())
			node.Next().setPrevious(node.Previous());
		else
			_last = node.Previous();
		
		_count--;
	}
	
	/**
	 * @param item
	 */
	public void Remove(T item)
	{
		// find the valid item
		LinkedListNode<T> node = Find(item);
		if (node == null)
			return;
		
		removeNode(node);
	}
	
	/**
	 * Removes the node at the start of the LinkedList
	 */
	public void RemoveFirst()
	{
		if (_first != null)
			removeNode(First());
	}
	
	/**
	 * Removes the node at the end of the LinkedList
	 */
	public void RemoveLast()
	{
		if (_last != null)
			removeNode(Last());
	}
	
	/**
	 * Determines whether a value is in the LinkedList
	 * 
	 * @param item
	 * @return
	 */
	public boolean Contains(T item)
	{
		return Find(item) != null;
	}
	
	/**
	 * Finds the first node that contains the specified value.
	 * 
	 * @param item
	 * @return
	 */
	public LinkedListNode<T> Find(T item)
	{
		LinkedListNode<T> _cursor = First();
		if (_cursor == null)
			return null;
		
		do
		{
			if (_cursor.Value == item)
				return _cursor;
			
			_cursor = _cursor.Next();
		} while (_cursor != null);
		
		return null;
	}
	
	/**
	 * Removes all nodes from the LinkedList
	 */
	public void Clear()
	{
		_count = 0;
		_first = null;
		_last = null;
	}
	
	// #endregion
	
	// #region Iterator
	
	@Override
	public Iterator<T> iterator()
	{
		return new linkedListIterator();
	}
	
	private class linkedListIterator implements Iterator<T>
	{
		private LinkedListNode<T> _cursor;
		
		linkedListIterator()
		{
			_cursor = First();
		}
		
		@Override
		public boolean hasNext()
		{
			return _cursor != null;
		}
		
		@Override
		public T next()
		{
			// error check
			if (!hasNext())
				throw new NoSuchElementException();
			
			// increment iterator
			_cursor = _cursor.Next();
			return _cursor.Value;
			
		}
	}
	
	// #endregion
}
