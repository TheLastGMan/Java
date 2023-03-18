package Listener;

import java.util.*;

/**
 * Base definition for classes to add/remove and raise events to listeners
 *
 * @author Ryan Gau
 * @param <T>
 *            EventListener of Type DeviceEventListener(Of E)
 * @param <E>
 *            EventObject
 */
public interface BaseListener<T extends EventListener, E extends EventObject> {
	/**
	 * Adds the specified Event Listener
	 *
	 * @param listener
	 *            Event Listener
	 * @return T/F if added
	 */
	public boolean addListener(T listener);
	
	/**
	 * Removes the specified Event Listener
	 *
	 * @param listener
	 *            Event Listener
	 * @return T/F if removed
	 */
	boolean removeListener(T listener);
	
	/**
	 * Raises the specified event to the Event Listeners
	 *
	 * @param event
	 *            Event Listener
	 */
	void raiseEvent(E event);
}
