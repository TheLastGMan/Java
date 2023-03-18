package System.Linq;

import System.Collections.Generic.List;

/**
 * Represents a collection of objects that have a common key.
 *
 * @author Ryan Gau
 * @param <TKey>
 *            The type of the key of the Grouping(Of TKey, TElement)
 * @param <TElement>
 *            The type of the values in the Grouping(Of TKey, TElement)
 */
public final class Grouping<TKey, TElement> extends List<TElement>
{
	private static final long serialVersionUID = -3133117572765977842L;
	
	/**
	 * The key of the Grouping(Of TKey, TElement)
	 */
	public final TKey Key;
	
	public Grouping(TKey key, IEnumerable<TElement> base)
	{
		super(base);
		Key = key;
	}
}
