package Event;

import java.util.EventObject;

/**
 * Gear in Park Event
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class GearParkEvent extends EventObject {
	private static final long serialVersionUID = 2930145719766314152L;
	
	public GearParkEvent(Object source) {
		super(source);
	}
}
