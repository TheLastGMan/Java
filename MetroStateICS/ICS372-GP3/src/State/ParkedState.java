/**
 * ParkedState
 *
 * @author Anthony Freitag
 * @version 1.0
 */
package State;

import Device.DeviceRepository;
import Event.IgnitionOnEvent;
import Listener.IgnitionOnListener;
import Manager.ManagerRepository;

public class ParkedState implements DeviceState, IgnitionOnListener {
	private static ParkedState instance;
	
	/**
	 * Private for the singleton pattern
	 */
	private ParkedState() {
	}
	
	/**
	 * singleton implementation
	 *
	 * @return the object
	 */
	public static ParkedState instance() {
		if (instance == null) {
			instance = new ParkedState();
		}
		return instance;
	}
	
	/**
	 * Enter this state - process requested change to ParkedState
	 * Initializes resources
	 * overrides super
	 */
	@Override
	public void run() {
		// set ignition status
		DeviceRepository.display.turnIgnitionSwitchOff();
		// set gear to park
		DeviceRepository.display.gearPositionPark();
		// pedal displays not moving - no change of state
		// set speed to 0
		DeviceRepository.display.displaySpeed(0);
		
		// timer is stopped
		
		// add IgnitionOnListener
		ManagerRepository.ignitionOnManager.addListener(this);
	}
	
	/**
	 * Leave this state
	 * Cleans up any used resources
	 * overrides super
	 */
	@Override
	public void leave() {
		// remove IgnitionOnListener
		ManagerRepository.ignitionOnManager.removeListener(this);
	}
	
	/**
	 * Give the associated state type with this
	 *
	 * @return StateType
	 *         overrides super
	 */
	@Override
	public StateType getStateId() {
		return StateType.PARKED;
	}
	
	/**
	 * handle IgnitionOnEvent
	 * request change of state
	 * overrides super
	 */
	@Override
	public void ignitionOn(IgnitionOnEvent event) {
		// change state to IdleState
		DeviceRepository.context.changeState(IdleState.instance());
	}
}
