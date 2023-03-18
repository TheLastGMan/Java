package Manager;

import Event.IgnitionOnEvent;
import Listener.IgnitionOnListener;

/**
 * Manager to handle Ignition On Events
 *
 * @author Tenzin
 * @version 1.0
 */
public class IgnitionOnManager extends ManagerListener<IgnitionOnListener, IgnitionOnEvent> {
	
	private static IgnitionOnManager instance;
	
	private IgnitionOnManager() {
	}
	
	/**
	 * For the singleton pattern
	 *
	 * @return the instance
	 */
	public static IgnitionOnManager instance() {
		if (instance == null) {
			return instance = new IgnitionOnManager();
		}
		return instance;
	}
	
	@Override
	void process(IgnitionOnListener listener, IgnitionOnEvent event) {
		listener.ignitionOn(event);
	}
}
