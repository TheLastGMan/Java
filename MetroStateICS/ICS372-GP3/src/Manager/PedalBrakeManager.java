package Manager;

import Event.PedalBrakeEvent;
import Listener.PedalBrakeListener;

/**
 * Manager to handle Pedal Brake Events
 *
 * @author Tenzin
 * @version 1.0
 */
public class PedalBrakeManager extends ManagerListener<PedalBrakeListener, PedalBrakeEvent> {
	private static PedalBrakeManager instance;
	
	private PedalBrakeManager() {
	}
	
	/**
	 * For the singleton pattern
	 *
	 * @return the instance
	 */
	public static PedalBrakeManager instance() {
		if (instance == null) {
			return instance = new PedalBrakeManager();
		}
		return instance;
	}
	
	@Override
	void process(PedalBrakeListener listener, PedalBrakeEvent event) {
		listener.pedalBrake(event);
	}
}
