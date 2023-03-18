package Event;

import java.util.EventObject;

/**
 * Brake pedal depressed event
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class PedalBrakeEvent extends EventObject {
	private static final long serialVersionUID = 2930145719766314156L;
	
	public PedalBrakeEvent(Object source) {
		super(source);
	}
}
