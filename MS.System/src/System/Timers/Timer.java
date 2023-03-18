package System.Timers;

import System.Threading.ThreadTimer;
import System.Threading.TimerCallback;

/**
 * Generates an event after a set interval,
 * with an option to generate recurring events.
 * Based on Microsoft's System.Timers.Timer
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class Timer
{
	private int interval = 100;
	private boolean autoReset = true;
	private boolean canRaiseEvents = true;
	private boolean enabled = true;
	public final ElapsedEventHandler Elapsed = new ElapsedEventHandler();
	private ThreadTimer timerThread = new ThreadTimer(timerCallback());

	/**
	 * Initializes a new instance of the Timer class, and sets all the
	 * properties to their initial values.
	 */
	public Timer()
	{
	}

	/**
	 * Initializes a new instance of the Timer class, and sets the Interval
	 * property to the specified number of milliseconds.
	 *
	 * @param interval
	 *            The time, in milliseconds, between events. The value must be
	 *            greater than zero and less than or equal to Int32.MaxValue.
	 */
	public Timer(int interval)
	{
		setInterval(interval);
	}

	// public int Interval { get; set; }
	public int getInterval()
	{
		return interval;
	}

	public void setInterval(int value)
	{
		// validate
		if (value <= 0)
		{
			value = 1;
		}

		// assign
		interval = value;
	}

	// public bool AutoReset { get; set; }
	public boolean getAutoReset()
	{
		return autoReset;
	}

	public void setAutoReset(boolean value)
	{
		autoReset = value;
	}

	// public bool CanRaiseEvents { get; }
	public boolean canRaiseEvents()
	{
		return canRaiseEvents;
	}

	// public bool Enabled { get; set; }
	public boolean getEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean value)
	{
		// start/stop timer if we want it enabled
		if (enabled)
		{
			timerThread.change(getInterval(), getInterval());
		}
		else
		{
			timerThread.change(-1, -1);
		}

		// assign new value
		enabled = value;
	}

	/** Start the timer, if not already running */
	public void start()
	{
		setEnabled(true);
	}

	/** Stop the timer */
	public void stop()
	{
		setEnabled(false);
	}

	private TimerCallback timerCallback()
	{
		return (state) ->
			{
				// check if we should continue raising future events
				if (!getAutoReset())
				{
					setEnabled(false);
				}

				// raise elapsed event
				Elapsed.raiseEvent(new ElapsedEventArgs(this));
			};
	}
}
