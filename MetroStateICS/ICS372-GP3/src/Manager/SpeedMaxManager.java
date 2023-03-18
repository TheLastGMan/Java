package Manager;

import Event.SpeedMaxEvent;
import Listener.SpeedMaxListener;

/**
 * Manager to handle Speed Max Events
 *
 * @author Tenzin
 * @version 1.0
 */
public class SpeedMaxManager extends ManagerListener<SpeedMaxListener, SpeedMaxEvent> {
	private static SpeedMaxManager instance;
	
	private SpeedMaxManager() {
	}
	
	/**
	 * For the singleton pattern
	 *
	 * @return the instance
	 */
	public static SpeedMaxManager instance() {
		if (instance == null) {
			return instance = new SpeedMaxManager();
		}
		return instance;
	}
	
	@Override
	void process(SpeedMaxListener listener, SpeedMaxEvent event) {
		listener.speedMax(event);
	}
}
