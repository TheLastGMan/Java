/**
 * IdleState
 *
 * @author Anthony Freitag
 * @version 1.0
 */
package State;

import Device.DeviceRepository;
import Event.*;
import Listener.*;
import Manager.ManagerRepository;

public class IdleState implements DeviceState, IgnitionOffListener, GearDriveListener {
	private static IdleState instance;
	
	/**
	 * Private for the singleton pattern
	 */
	private IdleState() {
	}
	
	/**
	 * singleton implementation
	 *
	 * @return the object
	 */
	public static IdleState instance() {
		if (instance == null) {
			instance = new IdleState();
		}
		return instance;
	}
	
	/**
	 * Enter this state - process requested change to IdleState
	 * Initializes resources
	 * overrides super
	 */
	@Override
	public void run() {
		// set ignition switch to on
		DeviceRepository.display.turnIgnitionSwitchOn();
		// gear selection set to park
		DeviceRepository.display.gearPositionPark();
		// pedal displays not moving - no change of state
		// set speed to 0
		DeviceRepository.display.displaySpeed(0);
		
		// timer is stopped
		
		// add IgnitionOffListener
		ManagerRepository.ignitionOffManager.addListener(this);
		// add GearDriveListener
		ManagerRepository.gearDriveManager.addListener(this);
	}
	
	/**
	 * Leave this state
	 * Cleans up any used resources
	 * overrides super
	 */
	@Override
	public void leave() {
		// remove IgnitionOffListener
		ManagerRepository.ignitionOffManager.removeListener(this);
		// remove GearDriveListener
		ManagerRepository.gearDriveManager.removeListener(this);
	}
	
	/**
	 * Give the associated state type with this
	 *
	 * @return StateType
	 *         overrides super
	 */
	@Override
	public StateType getStateId() {
		return StateType.IDLE;
	}
	
	/**
	 * handle GearDriveEvent
	 * request change of state
	 * overrides super
	 */
	@Override
	public void gearDrive(GearDriveEvent event) {
		// change state to StoppedState
		DeviceRepository.context.changeState(StoppedState.instance());
	}
	
	/**
	 * handle IgnitionOffEvent
	 * request change of state
	 * overrides super
	 */
	@Override
	public void ignitionOff(IgnitionOffEvent event) {
		// set state to ParkedState
		DeviceRepository.context.changeState(ParkedState.instance());
	}
}
