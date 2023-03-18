package Listener;

import java.util.EventListener;

import Event.PedalAccelerateEvent;

/**
 * Accelerate Pedal Event Listener Specification
 *
 * @author Ryan Gau
 * @version 1.0
 */
public interface PedalAccelerateListener extends EventListener {
	/**
	 * Acceleration Pedal depressed Event Delegate
	 *
	 * @param event
	 *            Acceleration Pedal depressed Event
	 */
	void pedalAccelerate(PedalAccelerateEvent event);
}
