package Event;

import java.util.EventObject;

/**
 * Timer elapsed event
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class TimerElapsedEvent extends EventObject {
	private static final long serialVersionUID = 2930145719766314159L;
	
	public TimerElapsedEvent(Object source) {
		super(source);
	}
}
