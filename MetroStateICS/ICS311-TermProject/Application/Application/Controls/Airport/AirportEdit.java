package Application.Controls.Airport;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AirportEdit extends BaseAirport
{
	private static final long serialVersionUID = -735003030011296063L;
	private static final Service.Repository.Airport _AirportRepository = new Service.Repository.Airport();
	private static Service.Model.Airport _Airport = null;

	/**
	 * Create the panel.
	 */
	public AirportEdit(int airportId)
	{
		super("Edit Airport");

		JPanel panel = new JPanel();
		panelFooter.add(panel);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				updateAirport();
			}
		});

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Application.Events.FPSEventService.ShowPanelOnMaster(new AirportList());
			}
		});
		panel.add(btnCancel);
		panel.add(btnUpdate);

		// load base airport
		loadData(airportId);
	}

	private void loadData(int airportId)
	{
		_Airport = _AirportRepository.Get(airportId);
		if (_Airport == null)
		{
			// invalid AirportId, show message and redirect
			JOptionPane.showMessageDialog(null, "Error", "Invalid Airport Id: " + Integer.toString(airportId) + System.lineSeparator() + "Redirecting to airport list",
					JOptionPane.ERROR_MESSAGE | JOptionPane.OK_OPTION);
			Application.Events.FPSEventService.ShowPanelOnMaster(new AirportList());
		}
		else
		{
			// base user info
			setBaseAirportData(_Airport);
			return;
		}
	}

	private void updateAirport()
	{
		// map over to current user
		Service.Model.Airport airport = getBaseAirportData();
		airport.Code = _Airport.Code;
		airport.Id = _Airport.Id;

		// check if we have a valid user
		List<String> airportErrors = airport.IsValid();
		if (airportErrors.size() > 0)
			// validation errors
			JOptionPane.showMessageDialog(null, "Errors Reported: " + System.lineSeparator() + String.join(System.lineSeparator(), airportErrors), "Error",
					JOptionPane.WARNING_MESSAGE | JOptionPane.OK_OPTION);
		else if (!_AirportRepository.Update(airport))
			// error creating user
			JOptionPane.showMessageDialog(null, "Error upading Airport's information, please try again later", "Error", JOptionPane.ERROR_MESSAGE | JOptionPane.OK_OPTION);
		else
		{
			// show success message
			JOptionPane.showMessageDialog(null, "Airport's information has been updated", "Success", JOptionPane.INFORMATION_MESSAGE | JOptionPane.OK_OPTION);

			// go to airports screen
			Application.Events.FPSEventService.ShowPanelOnMaster(new AirportList());
		}
	}
}
