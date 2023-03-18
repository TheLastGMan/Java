package Event;

import java.util.EventObject;

/**
 * Minimum speed reached event
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class SpeedMinEvent extends EventObject {
	private static final long serialVersionUID = 2930145719766314158L;
	
	public SpeedMinEvent(Object source) {
		super(source);
	}
}
