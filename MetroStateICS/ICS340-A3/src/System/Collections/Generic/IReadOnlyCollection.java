/**
 *
 */
package System.Collections.Generic;

/**
 * Readonly collection
 * Based on Microsoft's System.Collections.Generic.IReadOnlyCollection
 * 
 * @author Ryan Gau
 * @version 1.0
 */
public interface IReadOnlyCollection<T>
{
	public T Get(int index);
}
