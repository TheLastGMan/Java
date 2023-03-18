package Device;

import State.DeviceState;

/**
 * Device context interface
 *
 * @author Ryan Gau, Anthony Freitag
 * @version 1.0
 */
public interface Context {
	/**
	 * Request a state change using the specified event
	 *
	 * @param next
	 *            EventType raised that should change
	 */
	void changeState(DeviceState next);
	
	/**
	 * Start the context
	 */
	void start();
}
