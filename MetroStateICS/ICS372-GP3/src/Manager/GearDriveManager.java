package Manager;

import Event.GearDriveEvent;
import Listener.GearDriveListener;

/**
 * Manager to handle Gear Drive Events
 *
 * @author Tenzin
 * @version 1.0
 */
public class GearDriveManager extends ManagerListener<GearDriveListener, GearDriveEvent> {
	private static GearDriveManager instance;
	
	private GearDriveManager() {
	}
	
	/**
	 * For the singleton pattern
	 *
	 * @return the instance
	 */
	public static GearDriveManager instance() {
		if (instance == null) {
			return instance = new GearDriveManager();
		}
		return instance;
	}
	
	@Override
	void process(GearDriveListener listener, GearDriveEvent event) {
		listener.gearDrive(event);
	}
}
