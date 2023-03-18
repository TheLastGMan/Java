package System.Threading;

/**
 * Limits the number of threads that can access a resource or pool of resources concurrently.
 * Based on Microsoft's System.Threading.Semaphore
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class Semaphore extends WaitHandle
{
	String name = "";

	/**
	 * Initializes a new instance of the Semaphore class, specifying the initial number of entries and the maximum
	 * number of concurrent entries.
	 *
	 * @param initialCount
	 *            The initial number of requests for the semaphore that can be granted concurrently.
	 * @param maximumCount
	 *            The maximum number of requests for the semaphore that can be granted concurrently.
	 */
	public Semaphore(int initialCount, int maximumCount)
	{
		this(initialCount, maximumCount, "");
	}

	/**
	 * Initializes a new instance of the Semaphore class, specifying the initial number of entries and the maximum
	 * number of concurrent entries, and optionally specifying the name of a system semaphore object.
	 *
	 * @param initialCount
	 *            The initial number of requests for the semaphore that can be granted concurrently.
	 * @param maximumCount
	 *            The maximum number of requests for the semaphore that can be granted concurrently.
	 * @param name
	 *            The name of a named system semaphore object.
	 */
	public Semaphore(int initialCount, int maximumCount, String name)
	{
		// validate
		if (initialCount < 0)
		{
			initialCount = 0;
		}
		if (maximumCount < 1)
		{
			maximumCount = 1;
		}
		if (initialCount > maximumCount)
		{
			initialCount = maximumCount;
		}

		// set name
		this.name = name;

		// enable fairness so no threads can barge in front of another
		super.lock = new java.util.concurrent.Semaphore(maximumCount);
	}

	// properties
	public String getName()
	{
		return name;
	}
	
	// methods
	public int release()
	{
		return release(1);
	}

	public int release(int count)
	{
		if (count < 1)
		{
			count = 1;
		}
		
		int previousCount = lock.drainPermits();
		lock.release(count);
		return previousCount;
	}
}
