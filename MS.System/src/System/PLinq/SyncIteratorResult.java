/**
 *
 */
package System.PLinq;

/**
 * @author Ryan Gau
 * @version 1.0
 */
public class SyncIteratorResult<T>
{
	public final T Value;
	public final int Index;
	public final boolean HasValue;

	public SyncIteratorResult(T value, int index, boolean hasValue)
	{
		Value = value;
		Index = index;
		HasValue = hasValue;
	}
}
