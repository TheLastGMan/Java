package System.Timers;

import System.TimeSpan;

/**
 * Based on Microsoft's System.Diagnostics.Stopwatch
 * 
 * @author Ryan Gau
 * @version 1.0
 */
public class Stopwatch
{
	private long _elapsed;
	private long _startTimeStamp;
	private boolean _isRunning;
	private static final long _ticksPerMillisecond = 1;

	/** Initializes a new instance of the Stopwatch class. */
	public Stopwatch()
	{
		reset();
	}

	/**
	 * Gets the total elapsed time measured by the current instance.
	 *
	 * @return A read-only TimeSpan representing the total elapsed time measured by the current instance.
	 */
	public TimeSpan elapsed()
	{
		return new TimeSpan(getElapsedDateTimeTicks());
	}

	/**
	 * Gets the total elapsed time measured by the current instance, in milliseconds.
	 *
	 * @return
	 */
	public long elapsedMilliseconds()
	{
		return getElapsedDateTimeTicks();
	}

	/**
	 * Gets the total elapsed time measured by the current instance, in timer ticks.
	 *
	 * @return
	 */
	public long elapsedTicks()
	{
		return getRawElapsedTicks();
	}

	/**
	 * Gets a value indicating whether the Stopwatch timer is running.
	 *
	 * @return
	 */
	public boolean isRunning()
	{
		return _isRunning;
	}
	
	/** Starts, or resumes, measuring elapsed time for an interval. */
	public void start()
	{
		if (!_isRunning)
		{
			_startTimeStamp = getTimestamp();
			_isRunning = true;
		}
	}

	/**
	 * Initializes a new Stopwatch instance, sets the elapsed time property to zero, and starts measuring elapsed time.
	 *
	 * @return A Stopwatch that has just begun measuring elapsed time.
	 */
	public static Stopwatch startNew()
	{
		Stopwatch s = new Stopwatch();
		s.start();
		return s;
	}

	/** Stops measuring elapsed time for an interval. */
	public void stop()
	{
		if (_isRunning)
		{
			long endTimeStamp = getTimestamp();
			long elapsedThisPeriod = endTimeStamp - _startTimeStamp;
			_elapsed += elapsedThisPeriod;
			_isRunning = false;

			// in case of hardware abnormalities, ensure no negative values
			if (_elapsed < 0)
			{
				_elapsed = 0;
			}
		}
	}

	/** Stops time interval measurement and resets the elapsed time to zero. */
	public void reset()
	{
		_elapsed = 0;
		_isRunning = false;
		_startTimeStamp = 0;
	}

	/** Stops time interval measurement, resets the elapsed time to zero, and starts measuring elapsed time. */
	public void restart()
	{
		_elapsed = 0;
		_startTimeStamp = getTimestamp();
		_isRunning = true;
	}

	/** Gets the current number of ticks in the timer mechanism. */
	public static long getTimestamp()
	{
		return System.currentTimeMillis();
	}

	public long getRawElapsedTicks()
	{
		long timeElapsed = _elapsed;
		if (_isRunning)
		{
			// if it is running, add elapsed time since we start it the last time
			long currentTimestamp = getTimestamp();
			long elapsedUntilNow = currentTimestamp - _startTimeStamp;
			timeElapsed += elapsedUntilNow;
		}
		return timeElapsed;
	}

	public long getElapsedDateTimeTicks()
	{
		// convert to milliseconds
		long rawMilliseconds = getRawElapsedTicks() / _ticksPerMillisecond;
		return rawMilliseconds;
	}
}
