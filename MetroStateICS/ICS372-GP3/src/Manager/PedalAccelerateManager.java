package Manager;

import Event.PedalAccelerateEvent;
import Listener.PedalAccelerateListener;

/**
 * Manager to handle Pedal Accelerate Events
 *
 * @author Tenzin
 * @version 1.0
 */
public class PedalAccelerateManager extends ManagerListener<PedalAccelerateListener, PedalAccelerateEvent> {
	private static PedalAccelerateManager instance;
	
	private PedalAccelerateManager() {
	}
	
	/**
	 * For the singleton pattern
	 *
	 * @return the instance
	 */
	public static PedalAccelerateManager instance() {
		if (instance == null) {
			return instance = new PedalAccelerateManager();
		}
		return instance;
	}
	
	@Override
	void process(PedalAccelerateListener listener, PedalAccelerateEvent event) {
		listener.pedalAccelerate(event);
	}
}
