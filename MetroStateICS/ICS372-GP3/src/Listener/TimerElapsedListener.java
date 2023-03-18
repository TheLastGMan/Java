package Listener;

import java.util.EventListener;

import Event.TimerElapsedEvent;

/**
 * Timer Elapsed Event Listener Specification
 *
 * @author Ryan Gau
 * @version 1.0
 */
public interface TimerElapsedListener extends EventListener {
	/**
	 * Timer Elapsed Event Delegate
	 *
	 * @param event
	 *            Timer Elapsed Event
	 */
	void timerElapsed(TimerElapsedEvent event);
}
