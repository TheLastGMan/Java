/**
 *
 */
package System.PLinq;

import java.util.Iterator;
import java.util.concurrent.locks.*;

/**
 * Synchronous iteration of an iterator
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class SyncIterator<T>
{
	private final Iterator<T> _iterator;
	private final Lock itrLock = new ReentrantLock();
	private int activeIndex;
	
	public SyncIterator(Iterator<T> iterator)
	{
		_iterator = iterator;
	}
	
	public SyncIteratorResult<T> Next()
	{
		// result
		SyncIteratorResult<T> result;
		
		// lock and check if we have a next element
		// create result based on this input
		itrLock.lock();
		if (_iterator.hasNext())
		{
			result = new SyncIteratorResult<>(_iterator.next(), activeIndex, true);
			activeIndex += 1;
		}
		else
			result = new SyncIteratorResult<>(null, -1, false);
		
		// unlock and give result
		itrLock.unlock();
		return result;
	}
}
