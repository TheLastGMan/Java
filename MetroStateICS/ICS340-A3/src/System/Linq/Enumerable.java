/**
 *
 */
package System.Linq;

import java.io.Serializable;
import java.util.Iterator;

/**
 * @author Ryan Gau
 * @version 1.1
 */
public class Enumerable<T> extends IEnumerable<T> implements Serializable
{
	private static final long serialVersionUID = 3518346558019742918L;
	private final Iterator<T> _Iterator;
	
	// backwards compatibility
	public Enumerable(Iterable<T> collection)
	{
		this(collection.iterator());
	}

	public Enumerable(Iterator<T> iterator)
	{
		_Iterator = iterator;
	}

	/*
	 * (non-Javadoc)
	 * @see System.Linq.IEnumerable#iterator()
	 */
	@Override
	public Iterator<T> iterator()
	{
		return _Iterator;
	}
}
