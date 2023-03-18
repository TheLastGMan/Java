/**
 * FullSpeedState
 *
 * @author Anthony Freitag
 * @version 1.0
 */
package State;

import Device.DeviceRepository;
import Event.PedalBrakeEvent;
import Listener.PedalBrakeListener;
import Manager.ManagerRepository;

public class FullSpeedState implements DeviceState, PedalBrakeListener {
	private static FullSpeedState instance = null;
	
	/**
	 * Private for the singleton pattern
	 */
	private FullSpeedState() {
	}
	
	/**
	 * singleton implementation
	 *
	 * @return the object
	 */
	public static FullSpeedState instance() {
		if (instance == null) {
			instance = new FullSpeedState();
		}
		return instance;
	}
	
	/**
	 * Enter this state - process requested change to FullSpeedState
	 * Initializes resources
	 * overrides super
	 */
	@Override
	public void run() {
		// set gear to drive
		DeviceRepository.display.gearPositionDrive();
		// set pedal to accelerate
		DeviceRepository.display.accelerate();
		// speed is set to max - no change necessary; already at max value
		// pedal - no change of state, displays message
		// timer is stopped (off - not running)
		// add PedalBrakeListener
		ManagerRepository.pedalBrakeManager.addListener(this);
	}
	
	/**
	 * Leave this state
	 * Cleans up any used resources
	 * overrides super
	 */
	@Override
	public void leave() {
		// remove PedalBrakeListener
		ManagerRepository.pedalBrakeManager.removeListener(this);
	}
	
	/**
	 * Give the associated state type with this
	 *
	 * @return StateType
	 *         overrides super
	 */
	@Override
	public StateType getStateId() {
		return StateType.FULL_SPEED;
	}
	
	/**
	 * handle PedalBrakeEvent
	 * request change of state
	 * overrides super
	 */
	@Override
	public void pedalBrake(PedalBrakeEvent event) {
		// set state to BrakingState
		DeviceRepository.context.changeState(BrakingState.instance());
	}
}
