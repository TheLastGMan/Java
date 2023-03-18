package System.Linq;

import java.util.Collection;

/**
 * Represents a collection of objects that have a common key.
 *
 * @author Ryan Gau
 * @param <TKey>
 *            The type of the key of the Grouping(Of TKey, TElement)
 * @param <TElement>
 *            The type of the values in the Grouping(Of TKey, TElement)
 */
public final class Grouping<TKey, TElement> extends QList<TElement>
{
	/**
	 * The key of the Grouping(Of TKey, TElement)
	 */
	public final TKey Key;
	
	public Grouping(TKey key, Collection<TElement> base)
	{
		super(base);
		Key = key;
	}
}
