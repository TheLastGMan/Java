package Event;

import java.util.EventObject;

/**
 * Ignition turned On Event
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class IgnitionOnEvent extends EventObject {
	private static final long serialVersionUID = 2930145719766314150L;
	
	public IgnitionOnEvent(Object source) {
		super(source);
	}
}
