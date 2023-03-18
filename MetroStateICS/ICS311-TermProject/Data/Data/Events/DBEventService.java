package Data.Events;

public class DBEventService extends EventListener<IDBEvent>
{
	public static void RaiseError(String message)
	{
		RaiseEvent((IDBEvent e) -> e.Exception(message));
	}
}
