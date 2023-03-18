/**
 * StoppedState
 *
 * @author Anthony Freitag
 * @version 1.0
 */
package State;

import Device.DeviceRepository;
import Event.*;
import Listener.*;
import Manager.ManagerRepository;

public class StoppedState implements DeviceState, GearParkListener, PedalAccelerateListener {
	private static StoppedState instance;
	
	/**
	 * Private for the singleton pattern
	 */
	private StoppedState() {
	}
	
	/**
	 * singleton implementation
	 *
	 * @return the object
	 */
	public static StoppedState instance() {
		if (instance == null) {
			instance = new StoppedState();
		}
		return instance;
	}
	
	/**
	 * Enter this state - process requested change to StoppedState
	 * Initializes resources
	 * overrides super
	 */
	@Override
	public void run() {
		// set gear to drive
		DeviceRepository.display.gearPositionDrive();
		// pedal displays not moving - no change of state
		// set speed to 0
		DeviceRepository.display.displaySpeed(0);
		
		// timer is stopped
		
		// add IgnitionOffListener
		ManagerRepository.pedalAccelerateManager.addListener(this);
		// add GearParkListener
		ManagerRepository.gearParkManager.addListener(this);
	}
	
	/**
	 * Leave this state
	 * Cleans up any used resources
	 * overrides super
	 */
	@Override
	public void leave() {
		// remove IgnitionOffListener
		ManagerRepository.pedalAccelerateManager.removeListener(this);
		// remove GearParkListener
		ManagerRepository.gearParkManager.removeListener(this);
	}
	
	/**
	 * Give the associated state type with this
	 *
	 * @return StateType
	 *         overrides super
	 */
	@Override
	public StateType getStateId() {
		return StateType.STOPPED;
	}
	
	/**
	 * handle PedalAccelerateEvent
	 * request change of state
	 * overrides super
	 */
	@Override
	public void pedalAccelerate(PedalAccelerateEvent event) {
		// set state to AcceleratingState
		DeviceRepository.context.changeState(AcceleratingState.instance());
	}
	
	/**
	 * handle GearParkEvent
	 * request change of state
	 * overrides super
	 */
	@Override
	public void gearPark(GearParkEvent event) {
		// set state to IdleState
		DeviceRepository.context.changeState(IdleState.instance());
	}
}
