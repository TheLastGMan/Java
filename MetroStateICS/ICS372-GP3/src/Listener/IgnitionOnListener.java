package Listener;

import java.util.EventListener;

import Event.IgnitionOnEvent;

/**
 * Ignition On Event Listener Specification
 *
 * @author Ryan Gau
 * @version 1.0
 */
public interface IgnitionOnListener extends EventListener {
	/**
	 * Ignition On Event Delegate
	 *
	 * @param event
	 *            Ignition turned On Event
	 */
	void ignitionOn(IgnitionOnEvent event);
}
