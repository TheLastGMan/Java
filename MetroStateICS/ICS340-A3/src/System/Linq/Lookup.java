package System.Linq;

import java.util.*;

import System.Collections.IList;
import System.Collections.Generic.List;

/**
 * Represents a collection of keys each mapped to one or more values.
 *
 * @author Ryan Gau
 * @param <TKey>
 *            The type of the keys in the Lookup(Of TKey, TElement).
 * @param <TElement>
 *            The type of the elements of each IEnumerable(Of T) value in the Lookup(Of TKey, TElement).
 */
public final class Lookup<TKey, TElement> extends List<Grouping<TKey, TElement>>
{
	private static final long serialVersionUID = -2212317350432576206L;

	public Lookup(IEnumerable<Grouping<TKey, TElement>> base)
	{
		super(base);
	}
	
	/**
	 * Gets the collection of values indexed by the specified key.
	 *
	 * @param key
	 *            The key of the desired collection of values.
	 * @return The collection of values indexed by the specified key.
	 *         if it is not found, returns empty set
	 */
	public IList<TElement> Item(TKey key)
	{
		// find grouping
		for (Grouping<TKey, TElement> e : this)
			if (e.Key == key)
				return e;
			
		// not found, return empty set
		return new List<>(new ArrayList<>());
	}
	
	/**
	 * Gets the collection of values indexed by the specified key using the specified comparer.
	 *
	 * @param key
	 *            The key of the desired collection of values.
	 * @param comparer
	 *            A Comparer(Of T) to hash and compare keys.
	 * @return The collection of values indexed by the specified key.
	 *         if it is not found, returns empty set
	 */
	public IEnumerable<TElement> Item(TKey key, Comparator<TKey> comparer)
	{
		// find grouping
		for (Grouping<TKey, TElement> e : this)
			if (comparer.compare(e.Key, key) == 0)
				return e;
			
		// not found, return empty set
		return new List<>(new ArrayList<>());
	}
}
