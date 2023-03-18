package Application.Controls.Flight;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class FlightEdit extends BaseFlight
{
	private static final long serialVersionUID = -203023836911883053L;
	private static final Service.Repository.FlightPlan _FlightPlanService = new Service.Repository.FlightPlan();
	private Service.Model.FlightPlan _FlightPlan;

	/**
	 * Create the panel.
	 */
	public FlightEdit(int flightPlanId)
	{
		super("Update Flight Plan");

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				cancelEdit();
			}
		});
		panelFooter.add(btnCancel);

		JButton btnCreate = new JButton("Update");
		btnCreate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				updateFlightPlan();
			}
		});
		panelFooter.add(btnCreate);

		// load base airport
		loadData(flightPlanId);
	}

	private void cancelEdit()
	{
		// check if we are an administrator
		if (Application.Events.FPSEventService.CurrentUser().IsAdmin)
			Application.Events.FPSEventService.ShowPanelOnMaster(new FlightListAdmin());
		else
			Application.Events.FPSEventService.ShowPanelOnMaster(new FlightListUser(Application.Events.FPSEventService.CurrentUser().Id));
	}

	private void loadData(int planId)
	{
		_FlightPlan = _FlightPlanService.Get(planId);
		if (_FlightPlan == null)
		{
			// invalid AirportId, show message and redirect
			JOptionPane.showMessageDialog(null, "Error", "Invalid Flight Plan Id: " + Integer.toString(planId) + System.lineSeparator() + "Redirecting to Flight Plan list",
					JOptionPane.ERROR_MESSAGE | JOptionPane.OK_OPTION);
			cancelEdit();
		}
		else
		{
			// base user info
			setBaseFlightPlan(_FlightPlan);
			return;
		}
	}

	private void updateFlightPlan()
	{
		// map over to current user
		Service.Model.FlightPlan plan = getBaseFlightPlan();
		plan.Id = _FlightPlan.Id;
		plan.UserId = _FlightPlan.UserId;

		// check if we have a valid user
		List<String> airportErrors = plan.IsValid();
		if (airportErrors.size() > 0)
			// validation errors
			JOptionPane.showMessageDialog(null, "Errors Reported: " + System.lineSeparator() + String.join(System.lineSeparator(), airportErrors), "Error",
					JOptionPane.WARNING_MESSAGE | JOptionPane.OK_OPTION);
		else if (!_FlightPlanService.Update(plan))
			// error creating user
			JOptionPane.showMessageDialog(null, "Error upading Flight Plan's information, please try again later", "Error", JOptionPane.ERROR_MESSAGE | JOptionPane.OK_OPTION);
		else
		{
			// show success message
			JOptionPane.showMessageDialog(null, "Flight Plan's information has been updated", "Success", JOptionPane.INFORMATION_MESSAGE | JOptionPane.OK_OPTION);

			// go to flight plans
			cancelEdit();
		}
	}
}
