package Application.Controls.State;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class StateCreate extends BaseState
{
	private static final long serialVersionUID = -6544433557764985797L;
	private static final Service.Repository.State _StateRepository = new Service.Repository.State();
	
	/**
	 * Create the panel.
	 */
	public StateCreate()
	{
		super("Create State");
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Application.Events.FPSEventService.ShowPanelOnMaster(new StateList());
			}
		});
		panelFooter.add(btnCancel);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				addState();
			}
		});
		panelFooter.add(btnAdd);
	}
	
	private void addState()
	{
		Service.Model.State state = getBaseState();
		
		// check if we have a valid user
		List<String> errors = state.IsValid();
		if (errors.size() > 0)
			// validation errors
			JOptionPane.showMessageDialog(null, "Errors Reported: " + System.lineSeparator() + String.join(System.lineSeparator(), errors), "Error",
					JOptionPane.ERROR_MESSAGE | JOptionPane.OK_OPTION);
		else if (_StateRepository.AbbreviationCount(state.Abbreviation) > 0)
			// abbreviation already exists
			JOptionPane.showMessageDialog(null, "State Abbreviation (" + state.Abbreviation + ") already exists.", "Warning", JOptionPane.WARNING_MESSAGE | JOptionPane.OK_OPTION);
		else if (!_StateRepository.Create(state))
			// error creating user
			JOptionPane.showMessageDialog(null, "Error creating State, please try again later", "Error", JOptionPane.ERROR_MESSAGE | JOptionPane.OK_OPTION);
		else
		{
			// show success message
			JOptionPane.showMessageDialog(null, "State Created", "Success", JOptionPane.OK_OPTION | JOptionPane.INFORMATION_MESSAGE);
			
			// airport created, login
			Application.Events.FPSEventService.ShowPanelOnMaster(new StateList());
		}
	}
}
