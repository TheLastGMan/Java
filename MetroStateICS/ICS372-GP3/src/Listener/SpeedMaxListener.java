package Listener;

import java.util.EventListener;

import Event.SpeedMaxEvent;

/**
 * Maximum Speed Reached Event Listener Specification
 *
 * @author Ryan Gau
 * @version 1.0
 */
public interface SpeedMaxListener extends EventListener {
	/**
	 * Maximum Speed Reached Event Delegate
	 *
	 * @param event
	 *            Maximum speed reached Event
	 */
	void speedMax(SpeedMaxEvent event);
}
