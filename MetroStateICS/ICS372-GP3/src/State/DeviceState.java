package State;

/**
 * Common state methods
 * 
 * @author Ryan Gau
 * @version 1.0
 */
public interface DeviceState {
	/**
	 * Enter this state
	 * Initializes resources
	 */
	void run();
	
	/**
	 * Leave this state
	 * Cleans up any used resources
	 */
	void leave();
	
	/**
	 * Give the associated state type with this
	 * 
	 * @return StateType
	 */
	StateType getStateId();
}
