package System.Timers;

import java.util.*;

/**
 * Provides data for the Timer.Elapsed event.
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class ElapsedEventArgs extends EventObject
{
	private static final long serialVersionUID = 71230332065063327L;
	private final Date signalTime;
	
	/**
	 * @param source
	 *            The source of the event.
	 */
	public ElapsedEventArgs(Object source)
	{
		super(source);
		signalTime = new Date();
	}
	
	/**
	 * Gets the date/time when the Timer.Elapsed event was raised.
	 *
	 * @return The time the Elapsed event was raised.
	 */
	public Date getSignalTime()
	{
		return signalTime;
	}
}
