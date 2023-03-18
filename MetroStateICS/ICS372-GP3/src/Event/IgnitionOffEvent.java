package Event;

import java.util.EventObject;

/**
 * Ignition turned Off Event
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class IgnitionOffEvent extends EventObject {
	private static final long serialVersionUID = 2930145719766314151L;
	
	public IgnitionOffEvent(Object source) {
		super(source);
	}
}
