package Manager;

import Event.GearParkEvent;
import Listener.GearParkListener;

/**
 * Manager to handle Gear Park Events
 *
 * @author Tenzin
 * @version 1.0
 */
public class GearParkManager extends ManagerListener<GearParkListener, GearParkEvent> {
	private static GearParkManager instance;
	
	private GearParkManager() {
	}
	
	/**
	 * For the singleton pattern
	 *
	 * @return the instance
	 */
	public static GearParkManager instance() {
		if (instance == null) {
			return instance = new GearParkManager();
		}
		return instance;
	}
	
	@Override
	void process(GearParkListener listener, GearParkEvent event) {
		listener.gearPark(event);
	}
}
