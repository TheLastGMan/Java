package System;

import java.io.Serializable;

/**
 * Represents a time interval.
 * Based on Microsoft's System.TimeSpan
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class TimeSpan implements Comparable<TimeSpan>, Serializable
{
	private static final long serialVersionUID = 2863742554746742796L;
	private long _ticks;

	/* Ticks Per Time Unit */
	private static final long _ticksPerMillisecond = 1;
	private static final long _ticksPerSecond = (_ticksPerMillisecond * 1000);
	private static final long _ticksPerMinute = (_ticksPerSecond * 60);
	private static final long _ticksPerHour = (_ticksPerMinute * 60);
	private static final long _ticksPerDay = (_ticksPerHour * 24);

	/* Time Units Per Tick */
	private static final double _millisecondsPerTick = 1.0 / _ticksPerMillisecond;
	private static final double _secondsPerTick = 1.0 / _ticksPerSecond;
	private static final double _minutesPerTick = 1.0 / _ticksPerMinute;
	private static final double _hoursPerTick = 1.0 / _ticksPerHour;
	private static final double _daysPerTick = 1.0 / _ticksPerDay;
	
	/* Millisecond Info */
	private static final int _millisPerSecond = 1000;
	private static final int _millisPerMinute = _millisPerSecond * 60;
	private static final int _millisPerHour = _millisPerMinute * 60;
	private static final int _millisPerDay = _millisPerHour * 24;
	private static final long _maxMilliseconds = Long.MAX_VALUE / _ticksPerMillisecond;
	private static final long _minMilliseconds = Long.MIN_VALUE / _ticksPerMillisecond;

	/* Global States */
	public static final TimeSpan Zero = new TimeSpan(0);
	public static final TimeSpan MaxValue = new TimeSpan(Long.MAX_VALUE);
	public static final TimeSpan MinValue = new TimeSpan(Long.MIN_VALUE);

	/**
	 * Initializes a new instance of the TimeSpan structure to a specified number of hours, minutes, and seconds.
	 *
	 * @param hours
	 * @param minutes
	 * @param seconds
	 */
	public TimeSpan(int hours, int minutes, int seconds)
	{
		this(0, hours, minutes, seconds);
	}
	
	/**
	 * Initializes a new instance of the TimeSpan structure to a specified number of days, hours, minutes, and seconds.
	 *
	 * @param days
	 * @param hours
	 * @param minutes
	 * @param seconds
	 */
	public TimeSpan(int days, int hours, int minutes, int seconds)
	{
		this(days, hours, minutes, seconds, 0);
	}
	
	/**
	 * Initializes a new instance of the TimeSpan structure to a specified number of days, hours, minutes, seconds, and
	 * milliseconds.
	 *
	 * @param days
	 * @param hours
	 * @param minutes
	 * @param seconds
	 * @param milliseconds
	 */
	public TimeSpan(int days, int hours, int minutes, int seconds, int milliseconds)
	{
		long totalTicks = (days * _ticksPerDay) + (hours * _ticksPerHour) + (seconds * _ticksPerSecond) + (milliseconds * _ticksPerMillisecond);
		_ticks = totalTicks * _ticksPerMillisecond;
	}

	/**
	 * Initializes a new instance of the TimeSpan structure to the specified number of ticks.
	 *
	 * @param ticks
	 */
	public TimeSpan(long ticks)
	{
		_ticks = ticks;
	}

	/** Gets the days component of the time interval represented by the current TimeSpan structure. */
	public int days()
	{
		return (int)(ticks() / _ticksPerDay);
	}

	/** Gets the hours component of the time interval represented by the current TimeSpan structure. */
	public int hours()
	{
		return (int)((ticks() / _ticksPerHour) % 24);
	}

	/** Gets the minutes component of the time interval represented by the current TimeSpan structure. */
	public int minutes()
	{
		return (int)((ticks() / _ticksPerMinute) % 60);
	}

	/** Gets the seconds component of the time interval represented by the current TimeSpan structure. */
	public int seconds()
	{
		return (int)((ticks() / _ticksPerSecond) % 60);
	}

	/** Gets the milliseconds component of the time interval represented by the current TimeSpan structure. */
	public int milliseconds()
	{
		return (int)((ticks() / _ticksPerMillisecond) % 1000);
	}

	/** Gets the number of ticks that represent the value of the current TimeSpan structure. */
	public long ticks()
	{
		return _ticks;
	}
	
	/** Gets the value of the current TimeSpan structure expressed in whole and fractional days. */
	public double totalDays()
	{
		return ticks() * _daysPerTick;
	}
	
	/** Gets the value of the current TimeSpan structure expressed in whole and fractional hours. */
	public double totalHours()
	{
		return ticks() * _hoursPerTick;
	}

	/** Gets the value of the current TimeSpan structure expressed in whole and fractional minutes. */
	public double totalMinutes()
	{
		return ticks() * _minutesPerTick;
	}

	/** Gets the value of the current TimeSpan structure expressed in whole and fractional seconds. */
	public double totalSeconds()
	{
		return ticks() * _secondsPerTick;
	}

	/** Gets the value of the current TimeSpan structure expressed in whole and fractional milliseconds. */
	public double totalMilliseconds()
	{
		double tmp = ticks() * _millisecondsPerTick;

		// maximum value check
		if (tmp > _maxMilliseconds)
			return _maxMilliseconds;
		
		// minimum value check
		if (tmp < _minMilliseconds)
			return _minMilliseconds;
		
		return tmp;
	}

	/** Returns a new TimeSpan object whose value is the sum of the specified TimeSpan object and this instance. */
	public TimeSpan add(TimeSpan ts)
	{
		return new TimeSpan(ticks() + ts.ticks());
	}
	
	/**
	 * Returns a new TimeSpan object whose value is the difference between the specified TimeSpan object and this
	 * instance.
	 */
	public TimeSpan subtract(TimeSpan ts)
	{
		return new TimeSpan(ticks() - ts.ticks());
	}
	
	private static TimeSpan interval(double value, int scale)
	{
		double tmp = value * scale;
		double millis = tmp + (value >= 0 ? 0.5 : -0.5);
		return new TimeSpan((long)millis * _ticksPerMillisecond);
	}

	/**
	 * Returns a TimeSpan that represents a specified number of days, where the specification is accurate to the nearest
	 * millisecond.
	 */
	public static TimeSpan fromDays(double value)
	{
		return interval(value, _millisPerDay);
	}
	
	/**
	 * Returns a TimeSpan that represents a specified number of hours, where the specification is accurate to the
	 * nearest millisecond.
	 */
	public static TimeSpan fromHours(double value)
	{
		return interval(value, _millisPerHour);
	}
	
	/**
	 * Returns a TimeSpan that represents a specified number of minutes, where the specification is accurate to the
	 * nearest millisecond.
	 */
	public static TimeSpan fromMinutes(double value)
	{
		return interval(value, _millisPerMinute);
	}
	
	/**
	 * Returns a TimeSpan that represents a specified number of seconds, where the specification is accurate to the
	 * nearest millisecond.
	 */
	public static TimeSpan fromSeconds(double value)
	{
		return interval(value, _millisPerSecond);
	}

	/** Returns a TimeSpan that represents a specified number of milliseconds. */
	public static TimeSpan fromMilliseconds(double value)
	{
		return interval(value, 1);
	}

	/** Returns a TimeSpan that represents a specified time, where the specification is in units of ticks. */
	public static TimeSpan fromTicks(long value)
	{
		return new TimeSpan(value);
	}
	
	/**
	 * Compares this instance to a specified TimeSpan object and returns an integer that indicates whether this instance
	 * is shorter than, equal to, or longer than the TimeSpan object.
	 */
	@Override
	public int compareTo(TimeSpan o)
	{
		if (o.ticks() > ticks())
			return 1;
		else if (o.ticks() < ticks())
			return -1;
		return 0;
	}
	
	/** Returns a value indicating whether this instance is equal to a specified object (Overrides Object.equals) */
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof TimeSpan)
			return ((TimeSpan)o).compareTo(this) == 0;

		return false;
	}
	
	/** Returns a hash code for this instance (Overrides Object.hashCode) */
	@Override
	public int hashCode()
	{
		return (int)ticks() ^ (int)(ticks() >> 32);
	}
}
