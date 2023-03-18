package Manager;

import java.util.*;

import Event.TimerElapsedEvent;
import Listener.TimerElapsedListener;

/**
 * Manager to process TimerElapsed Events
 *
 * @author Tenzin, Ryan Gau
 * @version 1.0
 */
public class TimerElapsedManager extends ManagerListener<TimerElapsedListener, TimerElapsedEvent> implements Observer {
	private Timer timer = new Timer(1000);
	private static TimerElapsedManager instance;
	
	private TimerElapsedManager() {
		timer.addObserver(this);
	}
	
	/**
	 * For the singleton pattern
	 *
	 * @return the instance
	 */
	public static TimerElapsedManager instance() {
		if (instance == null) {
			return instance = new TimerElapsedManager();
		}
		return instance;
	}
	
	/**
	 * Custom implementation to add a listener.
	 * Start the timer with the first handler of this event.
	 * This way, we get full second events raised
	 * starting with the first handler
	 * -----
	 * Also, prevents unknown states if the user
	 * toggles between Accelerate/Brake within the same interval
	 * multiple times
	 */
	@Override
	public boolean addListener(TimerElapsedListener listener) {
		// check if we could add the listener
		if (super.addListener(listener)) {
			// on the first listener, start the timer
			if (super.count() == 1) {
				timer.start();
			}
			return true;
		}
		
		// couldn't add listener
		return false;
	}
	
	/**
	 * Custom implementation to remove a listener.
	 * Stop the timer if there are no handlers for the event.
	 * This way, we get full second events raised when
	 * the next listener is added.
	 * -----
	 * Also, prevents unknown states if the user
	 * toggles between Accelerate/Brake within the same interval
	 * multiple times
	 */
	@Override
	public boolean removeListener(TimerElapsedListener listener) {
		// check if we could remove the listener
		if (super.removeListener(listener)) {
			// on no more active listeners, stop the timer
			if (super.count() == 0) {
				timer.stop();
			}
			return true;
		}
		
		// couldn't remove listener
		return false;
	}
	
	/** timer tells us something happened, raise an event */
	@Override
	public void update(Observable observable, Object arg1) {
		super.raiseEvent(new TimerElapsedEvent(this));
	}
	
	@Override
	void process(TimerElapsedListener listener, TimerElapsedEvent event) {
		listener.timerElapsed(event);
	}
}
