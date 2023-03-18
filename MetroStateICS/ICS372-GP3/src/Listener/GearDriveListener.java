package Listener;

import java.util.EventListener;

import Event.GearDriveEvent;

/**
 * Gear Drive Event Listener Specification
 *
 * @author Ryan Gau
 * @version 1.0
 */
public interface GearDriveListener extends EventListener {
	/**
	 * Gear Drive Event Delegate
	 *
	 * @param event
	 *            Gear in Drive Event
	 */
	void gearDrive(GearDriveEvent event);
}
