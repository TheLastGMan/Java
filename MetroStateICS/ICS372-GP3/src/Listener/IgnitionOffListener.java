package Listener;

import java.util.EventListener;

import Event.IgnitionOffEvent;

/**
 * Ignition Off Event ListenerSpecification
 *
 * @author Ryan Gau
 * @version 1.0
 */
public interface IgnitionOffListener extends EventListener {
	/**
	 * Ignition Off Event Delegate
	 *
	 * @param event
	 *            Ignition turned Off Event
	 */
	void ignitionOff(IgnitionOffEvent event);
}
