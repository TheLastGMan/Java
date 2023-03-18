package Application.Events;

import javax.swing.JPanel;

public interface IFPSEvents
{
	void LoginSuccessful(Service.Model.User user);
	
	void Logout();
	
	Service.Model.User CurrentUser();
	
	void ShowPanelOnMaster(JPanel panel);
}
