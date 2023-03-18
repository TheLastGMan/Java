package Application.Events;

import javax.swing.JPanel;

public class FPSEventService extends Data.Events.EventListener<IFPSEvents>
{
	// #Region "Master Event Handlers"
	
	public static void LoginSuccessful(Service.Model.User user)
	{
		RaiseEvent((IFPSEvents e) -> e.LoginSuccessful(user));
	}
	
	public static void Logout()
	{
		RaiseEvent((IFPSEvents e) -> e.Logout());
	}
	
	public static Service.Model.User CurrentUser()
	{
		return GetFirstEvent((IFPSEvents f) -> f.CurrentUser());
	}
	
	public static void ShowPanelOnMaster(JPanel panel)
	{
		RaiseEvent((IFPSEvents e) -> e.ShowPanelOnMaster(panel));
	}
	
	// #EndRegion
}
