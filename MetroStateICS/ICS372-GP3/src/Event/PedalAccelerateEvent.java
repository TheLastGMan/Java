package Event;

import java.util.EventObject;

/**
 * Accelerate pedal depressed event
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class PedalAccelerateEvent extends EventObject {
	private static final long serialVersionUID = 2930145719766314155L;
	
	public PedalAccelerateEvent(Object source) {
		super(source);
	}
}
