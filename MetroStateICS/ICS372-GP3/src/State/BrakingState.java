/**
 * BrakingState
 *
 * @author Anthony Freitag
 * @version 1.0
 */
package State;

import Device.DeviceRepository;
import Event.*;
import Listener.*;
import Manager.ManagerRepository;

public class BrakingState implements DeviceState, PedalAccelerateListener, SpeedMinListener, TimerElapsedListener {
	private static BrakingState instance;
	
	/**
	 * Private for the singleton pattern
	 */
	private BrakingState() {
	}
	
	/**
	 * singleton implementation
	 *
	 * @return the object
	 */
	public static BrakingState instance() {
		if (instance == null) {
			instance = new BrakingState();
		}
		return instance;
	}
	
	/**
	 * Enter this state - process requested change to BrakingState
	 * Initializes resources
	 * overrides super
	 */
	@Override
	public void run() {
		// set gear to drive
		DeviceRepository.display.gearPositionDrive();
		// set pedal to accelerate
		DeviceRepository.display.brake();
		// ASSUMES TIMER IS CONSTANTLY RUNNING - uses TimerElapsedEvent in
		// incrementing speed
		
		// add PedalAccelerateListener
		ManagerRepository.pedalAccelerateManager.addListener(this);
		// add SpeedMinListener
		ManagerRepository.speedMinManager.addListener(this);
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
		// remove PedalAccelerateListener
		ManagerRepository.pedalAccelerateManager.removeListener(this);
		// remove SpeedMinListener
		ManagerRepository.speedMinManager.removeListener(this);
		// remove TimerElapsedListener
		ManagerRepository.timerElapsedManager.removeListener(this);
	}
	
	/**
	 * Give the associated state type with this
	 *
	 * @return StateType
	 *         overrides super
	 */
	@Override
	public StateType getStateId() {
		return StateType.BRAKE;
	}
	
	/**
	 * handle TimerElapsedEvent
	 * request change of state
	 * overrides super
	 */
	@Override
	public void timerElapsed(TimerElapsedEvent event) {
		// decrease speed (begin deceleration per timer tick)
		// get speed to decrease and update display
		DeviceRepository.display.displaySpeed(((DeviceRepository.display.getSpeed()) - 5));
	}
	
	/**
	 * handle SpeedMinEvent
	 * request change of state
	 * overrides super
	 */
	@Override
	public void speedMin(SpeedMinEvent event) {
		// set state to StoppedState
		DeviceRepository.context.changeState(StoppedState.instance());
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
}
