package Event;

import java.util.EventObject;

/**
 * Maximum speed reached event
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class SpeedMaxEvent extends EventObject {
	private static final long serialVersionUID = 2930145719766314157L;
	
	public SpeedMaxEvent(Object source) {
		super(source);
	}
}
