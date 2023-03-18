package System.Threading;

import java.util.concurrent.TimeUnit;

import System.TimeSpan;

/**
 * Encapsulates operating system–specific objects that wait for exclusive access to shared resources.
 *
 * @author Ryan Gau
 * @version 1.0
 */
public abstract class WaitHandle
{
	protected java.util.concurrent.Semaphore lock = new java.util.concurrent.Semaphore(1, true);
	
	protected void setLocks(int locks)
	{
		lock = new java.util.concurrent.Semaphore(locks, true);
	}
	
	/**
	 * Blocks the current thread until the current WaitHandle receives a signal.
	 *
	 * @return true if the current instance receives a signal; otherwise, false.
	 */
	public boolean WaitOne()
	{
		return lock.tryAcquire();
	}

	/**
	 * Blocks the current thread until the current WaitHandle receives a signal, using a 32-bit signed integer to
	 * specify the time interval in milliseconds.
	 *
	 * @param millisecondsTimeout
	 *            The number of milliseconds to wait, or Timeout.Infinite (-1) to wait indefinitely.
	 * @return true if the current instance receives a signal; otherwise, false.
	 * @throws InterruptedException
	 */
	public boolean WaitOne(long millisecondsTimeout) throws InterruptedException
	{
		if (millisecondsTimeout == -1)
		{
			return WaitOne();
		}
		
		return lock.tryAcquire(millisecondsTimeout, TimeUnit.MILLISECONDS);
	}

	/**
	 * @param timeout
	 *            A TimeSpan that represents the number of milliseconds to wait, or a TimeSpan that represents -1
	 *            milliseconds to wait indefinitely.
	 * @return true if the current instance receives a signal; otherwise, false.
	 * @throws InterruptedException
	 */
	public boolean WaitOne(TimeSpan timeout) throws InterruptedException
	{
		return WaitOne((long)timeout.totalMilliseconds());
	}
}
