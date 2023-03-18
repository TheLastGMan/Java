package Event;

import java.util.EventObject;

/**
 * Gear in Drive Event
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class GearDriveEvent extends EventObject {
	private static final long serialVersionUID = 2930145719766314154L;
	
	public GearDriveEvent(Object source) {
		super(source);
	}
}
