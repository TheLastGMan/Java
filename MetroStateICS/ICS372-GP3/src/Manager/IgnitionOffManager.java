package Manager;

import Event.IgnitionOffEvent;
import Listener.IgnitionOffListener;

/**
 * Manager to handle Ignition Off Events
 *
 * @author Tenzin
 * @version 1.0
 */
public class IgnitionOffManager extends ManagerListener<IgnitionOffListener, IgnitionOffEvent> {
	private static IgnitionOffManager instance;
	
	private IgnitionOffManager() {
	}
	
	/**
	 * For the singleton pattern
	 *
	 * @return the instance
	 */
	public static IgnitionOffManager instance() {
		if (instance == null) {
			return instance = new IgnitionOffManager();
		}
		return instance;
	}
	
	@Override
	void process(IgnitionOffListener listener, IgnitionOffEvent event) {
		listener.ignitionOff(event);
	}
}
