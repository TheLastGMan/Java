package Manager;

import java.util.*;

import Listener.BaseListener;

/**
 * Manager methods to handle events and save copy paste into each manager class
 *
 * @author Tenzin, Ryan Gau
 * @param <T>
 *            EventListener
 * @param <E>
 *            EventObject
 */
public abstract class ManagerListener<T extends EventListener, E extends EventObject> implements BaseListener<T, E> {
	
	private List<T> listeners = new ArrayList<>();
	
	@Override
	public boolean addListener(T listener) {
		return listeners.add(listener);
	}
	
	@Override
	public boolean removeListener(T listener) {
		return listeners.remove(listener);
	}
	
	@Override
	public void raiseEvent(E event) {
		// ryan: used new arraylist of listeners since we don't know
		// what the handlers of this event will do.
		// Prevents errors if they try to remove themselves from this listener
		// as this resource is already in use
		for (T listener : new ArrayList<>(listeners)) {
			process(listener, event);
		}
	}
	
	/**
	 * How many listeners do we have
	 *
	 * @return number of listeners
	 */
	public int count() {
		return listeners.size();
	}
	
	/**
	 * Pass the actual event handling off to the sub class
	 * since, at this level, we don't know how to handle it
	 * or what to do with it
	 *
	 * @param listener
	 *            EventListener
	 * @param event
	 *            EventObject
	 */
	abstract void process(T listener, E event);
}
