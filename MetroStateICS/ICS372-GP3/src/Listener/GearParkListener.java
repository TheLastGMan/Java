package Listener;

import java.util.EventListener;

import Event.GearParkEvent;

/**
 * Gear Park Event Listener Specification
 *
 * @author Ryan Gau
 * @version 1.0
 */
public interface GearParkListener extends EventListener {
	/**
	 * Gear Park Event Delegate
	 *
	 * @param event
	 *            Gear in Park Event
	 */
	void gearPark(GearParkEvent event);
}
