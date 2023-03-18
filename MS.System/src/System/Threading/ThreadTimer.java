package System.Threading;

import System.TimeSpan;

/**
 * Provides a mechanism for executing a method on a thread pool thread at specified intervals. This class cannot be
 * inherited.
 * Based on Microsoft's System.Threading.Timer
 *
 * @author Ryan Gau
 * @version 1.0
 */
public final class ThreadTimer
{
	private Thread thread = new Thread(new TimerRunner());
	private TimerCallback callback;
	private Object state;
	private int dueTime;
	private int period;
	
	/**
	 * Initializes a new instance of the Timer class with an infinite period and
	 * an infinite due time, using the newly created Timer object as the
	 * state object.
	 *
	 * @param callback
	 *            A TimerCallback delegate representing a method to be executed.
	 */
	public ThreadTimer(TimerCallback callback)
	{
		timerSetup(callback, this, -1, -1);
	}
	
	/**
	 * Initializes a new instance of the Timer class, using a 32-bit signed
	 * integer to specify the time interval.
	 *
	 * @param callback
	 *            A TimerCallback delegate representing a method to be executed.
	 * @param state
	 *            An object containing information to be used by the callback
	 *            method, or null.
	 * @param dueTime
	 *            The amount of time to delay before callback is invoked, in
	 *            milliseconds. Specify Timeout.Infinite to prevent the timer
	 *            from starting. Specify zero (0) to start the timer
	 *            immediately.
	 * @param period
	 *            The time interval between invocations of callback, in
	 *            milliseconds. Specify Timeout.Infinite to disable periodic
	 *            signaling.
	 */
	public ThreadTimer(TimerCallback callback, Object state, int dueTime, int period)
	{
		timerSetup(callback, state, dueTime, period);
	}
	
	/**
	 * Initializes a new instance of the Timer class, using TimeSpan values to measure time intervals.
	 *
	 * @param callback
	 *            A TimerCallback delegate representing a method to be executed.
	 * @param state
	 *            An object containing information to be used by the callback
	 *            method, or null.
	 * @param dueTime
	 *            The amount of time to delay before the callback parameter invokes its methods. Specify negative one
	 *            (-1) milliseconds to prevent the timer from starting. Specify zero (0) to start the timer immediately.
	 * @param period
	 *            The time interval between invocations of the methods referenced by callback. Specify negative one (-1)
	 *            milliseconds to disable periodic signaling.
	 */
	public ThreadTimer(TimerCallback callback, Object state, TimeSpan dueTime, TimeSpan period)
	{
		timerSetup(callback, state, (int)dueTime.totalMilliseconds(), (int)period.totalMilliseconds());
	}
	
	private void timerSetup(TimerCallback callback, Object state, int dueTime, int period)
	{
		this.callback = callback;
		this.state = state;
		change(dueTime, period);
	}
	
	/**
	 * Changes the start time and the interval between method invocations for a timer, using 32-bit signed integers to
	 * measure time intervals.
	 *
	 * @param dueTime
	 *            The amount of time to delay before the invoking the callback method specified when the Timer was
	 *            constructed, in milliseconds. Specify Timeout.Infinite to prevent the timer from restarting. Specify
	 *            zero (0) to restart the timer immediately.
	 * @param period
	 *            The time interval between invocations of the callback method specified when the Timer was constructed,
	 *            in milliseconds. Specify Timeout.Infinite to disable periodic signaling.
	 * @return T/F if the timer was successfully updated.
	 */
	public boolean change(int dueTime, int period)
	{
		// assign values
		this.dueTime = dueTime;
		this.period = period;
		
		// stop timer during update
		thread.interrupt();
		
		// start timer if we have valid time
		if (dueTime >= 0)
		{
			thread.start();
		}
		
		return true;
	}
	
	/**
	 * Changes the start time and the interval between method invocations for a timer, using TimeSpan values to measure
	 * time intervals.
	 *
	 * @param dueTime
	 *            A TimeSpan representing the amount of time to delay before invoking the callback method specified when
	 *            the Timer was constructed. Specify negative one (-1) milliseconds to prevent the timer from
	 *            restarting. Specify zero (0) to restart the timer immediately.
	 * @param period
	 *            The time interval between invocations of the callback method specified when the Timer was constructed.
	 *            Specify negative one (-1) milliseconds to disable periodic signaling.
	 * @return T/F if the timer was successfully updated.
	 */
	public boolean change(TimeSpan dueTime, TimeSpan period)
	{
		return change((int)dueTime.totalMilliseconds(), (int)period.totalMilliseconds());
	}

	private class TimerRunner implements Runnable
	{
		@Override
		public void run()
		{
			try
			{
				while (dueTime >= 0)
				{
					// sleep
					Thread.sleep(dueTime);
					
					// if we were interrupted, exit
					if (Thread.interrupted())
					{
						break;
					}
					
					// raise update
					callback.accept(state);
					
					// change due time to period
					if (period == -1)
					{
						break;
					}
					else
					{
						dueTime = period;
					}
				}
			}
			catch (Exception ex)
			{
				
			}
		}
	}
}
