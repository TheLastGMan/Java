package Application.Controls.Flight;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class FlightCreate extends BaseFlight
{
	private static final long serialVersionUID = 1352723944483880269L;
	private static final Service.Repository.FlightPlan _FlightPlanRepository = new Service.Repository.FlightPlan();
	
	/**
	 * Create the panel.
	 */
	public FlightCreate()
	{
		super("Create Flight Plan");
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				cancelCreate();
			}
		});
		panelFooter.add(btnCancel);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				createFlightPlan();
			}
		});
		panelFooter.add(btnCreate);
	}
	
	private void cancelCreate()
	{
		// check if we are an administrator
		if (Application.Events.FPSEventService.CurrentUser().IsAdmin)
			Application.Events.FPSEventService.ShowPanelOnMaster(new FlightListAdmin());
		else
			Application.Events.FPSEventService.ShowPanelOnMaster(new FlightListUser(Application.Events.FPSEventService.CurrentUser().Id));
	}
	
	private void createFlightPlan()
	{
		Service.Model.FlightPlan plan = getBaseFlightPlan();
		
		// add in extra features
		plan.UserId = Application.Events.FPSEventService.CurrentUser().Id;
		
		// validation check
		// check if we have a valid airport
		List<String> errors = plan.IsValid();
		if (errors.size() > 0)
			// validation errors
			JOptionPane.showMessageDialog(null, "Errors Reported: " + System.lineSeparator() + String.join(System.lineSeparator(), errors), "Error",
					JOptionPane.ERROR_MESSAGE | JOptionPane.OK_OPTION);
		else if (!_FlightPlanRepository.Create(plan))
			// error creating user
			JOptionPane.showMessageDialog(null, "Error creating flight plan, please try again later", "Error", JOptionPane.ERROR_MESSAGE | JOptionPane.OK_OPTION);
		else
		{
			// show success message
			JOptionPane.showMessageDialog(null, "Flight Plan Created", "Success", JOptionPane.OK_OPTION | JOptionPane.INFORMATION_MESSAGE | JOptionPane.OK_OPTION);
			
			// fight play created
			cancelCreate();
		}
	}
}
