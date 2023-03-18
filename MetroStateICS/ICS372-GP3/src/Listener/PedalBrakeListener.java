package Listener;

import java.util.EventListener;

import Event.PedalBrakeEvent;

/**
 * Brake Pedal Event Listener Specification
 *
 * @author Ryan Gau
 * @version 1.0
 */
public interface PedalBrakeListener extends EventListener {
	/**
	 * Brake Pedal depressed Event Delegate
	 *
	 * @param event
	 *            Brake Pedal depressed Event
	 */
	void pedalBrake(PedalBrakeEvent event);
}
