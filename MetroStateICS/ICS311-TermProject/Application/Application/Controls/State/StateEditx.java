package Application.Controls.State;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import Application.Controls.Airport.AirportList;

public class StateEditx extends BaseState
{
	private static final long serialVersionUID = 2361479604241190460L;
	private static final Service.Repository.State _StateRepository = new Service.Repository.State();
	private Service.Model.State _State = null;
	
	/**
	 * Create the panel.
	 */
	public StateEditx(int stateId)
	{
		super("Edit State");
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				// show state list
				Application.Events.FPSEventService.ShowPanelOnMaster(new StateList());
			}
		});
		panelFooter.add(btnCancel);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				updateState();
			}
		});
		panelFooter.add(btnUpdate);
		
		// load state
		loadStateData(stateId);
	}
	
	private void loadStateData(int stateId)
	{
		_State = _StateRepository.Get(stateId);
		if (_State == null)
		{
			// invalid userId, show message and redirect
			JOptionPane.showMessageDialog(null, "Error", "Invalid State Id: " + Integer.toString(stateId) + System.lineSeparator() + "Redirecting to airport list",
					JOptionPane.ERROR_MESSAGE | JOptionPane.OK_OPTION);
			Application.Events.FPSEventService.ShowPanelOnMaster(new AirportList());
		}
		else
		{
			// base user info
			setBaseState(_State);
			return;
		}
	}
	
	private void updateState()
	{
		// map over to current user
		Service.Model.State state = getBaseState();
		_State.Abbreviation = state.Abbreviation;
		_State.Name = state.Name;
		
		// check if we have a valid user
		List<String> airportErrors = _State.IsValid();
		if (airportErrors.size() > 0)
			// validation errors
			JOptionPane.showMessageDialog(null, "Errors Reported: " + System.lineSeparator() + String.join(System.lineSeparator(), airportErrors), "Error",
					JOptionPane.WARNING_MESSAGE | JOptionPane.OK_OPTION);
		else if (_StateRepository.AbbreviationCount(_State.Abbreviation) > 1)
			// abbreviation already exists
			JOptionPane.showMessageDialog(null, "State Abbreviation (" + _State.Abbreviation + ") already exists.", "Warning", JOptionPane.WARNING_MESSAGE | JOptionPane.OK_OPTION);
		else if (!_StateRepository.Update(_State))
			// error creating user
			JOptionPane.showMessageDialog(null, "Error upading State's information, please try again later", "Error", JOptionPane.ERROR_MESSAGE | JOptionPane.OK_OPTION);
		else
		{
			// show success message
			JOptionPane.showMessageDialog(null, "State's information has been updated", "Success", JOptionPane.INFORMATION_MESSAGE | JOptionPane.OK_OPTION);
			
			// go to airports screen
			Application.Events.FPSEventService.ShowPanelOnMaster(new AirportList());
		}
	}
}
