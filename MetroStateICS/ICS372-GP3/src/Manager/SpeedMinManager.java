package Manager;

import Event.SpeedMinEvent;
import Listener.SpeedMinListener;

/**
 * Manager to handle Speed Min Events
 *
 * @author Tenzin
 * @version 1.0
 */
public class SpeedMinManager extends ManagerListener<SpeedMinListener, SpeedMinEvent> {
	private static SpeedMinManager instance;
	
	private SpeedMinManager() {
	}
	
	/**
	 * For the singleton pattern
	 *
	 * @return the instance
	 */
	public static SpeedMinManager instance() {
		if (instance == null) {
			return instance = new SpeedMinManager();
		}
		return instance;
	}
	
	@Override
	void process(SpeedMinListener listener, SpeedMinEvent event) {
		listener.speedMin(event);
	}
}
