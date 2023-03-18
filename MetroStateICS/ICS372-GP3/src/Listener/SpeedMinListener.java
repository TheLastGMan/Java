package Listener;

import java.util.EventListener;

import Event.SpeedMinEvent;

/**
 * Minimum Speed Reached Event Listener Specification
 *
 * @author Ryan Gau
 * @version 1.0
 */
public interface SpeedMinListener extends EventListener {
	/**
	 * Minimum Speed Reached Event Delegate
	 *
	 * @param event
	 *            Minimum speed reached Event
	 */
	void speedMin(SpeedMinEvent event);
}
