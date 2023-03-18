package Data.Events;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class EventListener<T>
{
	private static ArrayList<Object> _EventListeners = new ArrayList<>();
	
	public static <T> boolean AddListener(T eventHandler)
	{
		if (eventHandler == null)
			return false;
		
		return _EventListeners.add(eventHandler);
	}
	
	public static <T> boolean RemoveListener(T eventHandler)
	{
		if (eventHandler == null)
			return false;
		
		return _EventListeners.remove(eventHandler);
	}
	
	public static void Clear()
	{
		_EventListeners.clear();
	}
	
	@SuppressWarnings("unchecked")
	public static <T, U> U GetFirstEvent(Function<T, U> action)
	{
		if (action != null && _EventListeners.size() > 0)
			return action.apply((T)_EventListeners.get(0));
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> void RaiseEvent(Consumer<T> action)
	{
		if (action != null)
			for (Object event : _EventListeners)
				action.accept((T)event);
	}
}
