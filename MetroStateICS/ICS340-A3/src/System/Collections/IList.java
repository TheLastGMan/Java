/**
 *
 */
package System.Collections;

/**
 * Based on Microsoft's System.Collections.Generic.IList
 *
 * @author Ryan Gau
 * @version 1.0
 */
public abstract class IList<T> extends ICollection<T>
{
	// The Item property provides methods to read and edit entries in the List.
	public abstract T Get(int index);
	
	public abstract void Set(int index, T value);
	
	// Returns the index of a particular item, if it is in the list.
	// Returns -1 if the item isn't in the list.
	public abstract int IndexOf(T item);
	
	// Inserts value into the list at position index.
	// index must be non-negative and less than or equal to the
	// number of elements in the list. If index equals the number
	// of items in the list, then value is appended to the end.
	public abstract void Insert(int index, T item);
	
	// Removes the item at position index.
	public abstract void RemoveAt(int index);
}
