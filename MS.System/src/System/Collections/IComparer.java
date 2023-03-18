/**
 *
 */
package System.Collections;

import java.util.Comparator;

/**
 * The generic IComparer interface implements a method that compares
 * two objects. It is used in conjunction with the Sort and
 * BinarySearch methods on the Array, List, and SortedList classes.
 * Based on Microsoft's System.Collections.Generic.IComparer
 * 
 * @author Ryan Gau
 * @version 1.0
 */
public interface IComparer<T> extends Comparator<T>
{
}
