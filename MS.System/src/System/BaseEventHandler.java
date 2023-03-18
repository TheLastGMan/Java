package System;

import java.util.*;
import java.util.function.Consumer;

/**
 * Event Handler class that allows more generic specification
 * over what comes natively with Java
 *
 * @author Ryan Gau
 * @param <T>
 *            Event Class of EventObject
 */
public class BaseEventHandler<T extends EventObject>
{
	private List<Consumer<T>> _handlers = new LinkedList<>();

	/**
	 * Add the specified handler
	 *
	 * @param handler
	 *            Event Handler
	 * @return T/F if added
	 */
	public boolean addHandler(Consumer<T> handler)
	{
		return _handlers.add(handler);
	}

	/**
	 * Remove the specified handler
	 *
	 * @param handler
	 *            Event Handler
	 * @return T/F if removed
	 */
	public boolean removeHandler(Consumer<T> handler)
	{
		return _handlers.remove(handler);
	}

	/**
	 * Raise the specified event to the handlers of it
	 * (Internally Visible)
	 *
	 * @param event
	 *            Event to raise
	 */
	public void raiseEvent(T event)
	{
		for (Consumer<T> c : _handlers)
			c.accept(event);
	}
}
