/**
 * AcceleratingState
 *
 * @author Anthony Freitag
 * @version 1.0
 */
package State;

import Device.DeviceRepository;
import Event.*;
import Listener.*;
import Manager.ManagerRepository;

public class AcceleratingState implements DeviceState, PedalBrakeListener, SpeedMaxListener, TimerElapsedListener {
	private static AcceleratingState instance;
	
	/**
	 * Private for the singleton pattern
	 */
	private AcceleratingState() {
	}
	
	/**
	 * singleton implementation
	 *
	 * @return the object
	 */
	public static AcceleratingState instance() {
		if (instance == null) {
			instance = new AcceleratingState();
		}
		return instance;
	}
	
	/**
	 * Enter this state - process requested change to AcceleratingState
	 * Initializes resources
	 * overrides super
	 */
	@Override
	public void run() {
		// set gear to drive
		DeviceRepository.display.gearPositionDrive();
		// set pedal to accelerate
		DeviceRepository.display.accelerate();
		// ASSUMES TIMER IS CONSTANTLY RUNNING - uses TimerElapsedEvent in
		// incrementing speed
		// add PedalBrakeListener
		ManagerRepository.pedalBrakeManager.addListener(this);
		// add SpeedMaxListener
		ManagerRepository.speedMaxManager.addListener(this);
		// add TimerElapsedListener
		ManagerRepository.timerElapsedManager.addListener(this);
	}
	
	/**
	 * Leave this state
	 * Cleans up any used resources
	 * overrides super
	 */
	@Override
	public void leave() {
		// remove TimerElapsedListener
		ManagerRepository.timerElapsedManager.removeListener(this);
		// remove PedalBrakeListener
		ManagerRepository.pedalBrakeManager.removeListener(this);
		// remove SpeedMaxListener
		ManagerRepository.speedMaxManager.removeListener(this);
		
	}
	
	/**
	 * Give the associated state type with this
	 *
	 * @return StateType
	 *         overrides super
	 */
	@Override
	public StateType getStateId() {
		return StateType.ACCELERATE;
	}
	
	/**
	 * handle TimerElapsedEvent
	 * request change of state
	 * overrides super
	 */
	@Override
	public void timerElapsed(TimerElapsedEvent event) {
		// increase speed (begin accumulating per timer tick)
		// get speed to increment and update with new value
		DeviceRepository.display.displaySpeed(DeviceRepository.display.getSpeed() + 5);
	}
	
	/**
	 * handle SpeedMaxEvent
	 * request change of state
	 * overrides super
	 */
	@Override
	public void speedMax(SpeedMaxEvent event) {
		// set state to FullSpeedState
		DeviceRepository.context.changeState(FullSpeedState.instance());
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
