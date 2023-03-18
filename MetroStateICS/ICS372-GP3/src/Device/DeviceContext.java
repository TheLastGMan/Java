package Device;

import State.*;

/**
 * @author Anthony Freitag
 * @version 1.0
 */
public class DeviceContext implements Context {
	private static DeviceContext instance;
	private DeviceState currentState;
	
	/**
	 * singleton constructor
	 */
	private DeviceContext() {
	}
	
	/**
	 * Return the instance
	 *
	 * @return the object
	 */
	public static DeviceContext instance() {
		if (instance == null) {
			instance = new DeviceContext();
		}
		return instance;
	}
	
	private void startState(DeviceState state) {
		currentState = state;
		currentState.run();
		DeviceRepository.display.setMovement(currentState.getStateId().toString());
	}
	
	@Override
	public void start() {
		currentState = ParkedState.instance();
		startState(currentState);
	}
	
	@Override
	public void changeState(DeviceState next) {
		currentState.leave();
		startState(next);
	}
}
