package Application.Controls.User;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.TitledBorder;

import Application.Controls.Account.UserRegister;

public class UserEditAdmin extends BaseUser
{
	private static final long serialVersionUID = -988468430627886280L;
	private JPasswordField passwordEntry;
	private JPasswordField passwordConfirm;
	private static Service.Repository.User _UserRepository = new Service.Repository.User();
	private Service.Model.User _User = null;
	private JCheckBox chckboxIsAdministrator;
	private JCheckBox chckboxUnlockAccount;
	private JLabel lblPilotId;
	
	/**
	 * Create the panel.
	 */
	public UserEditAdmin(int userId)
	{
		super("Edit User");
		panelBody.setBorder(new TitledBorder(null, "Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel lblPilotidText = new JLabel("Pilot Id:");
		GridBagConstraints gbc_lblPilotidText = new GridBagConstraints();
		gbc_lblPilotidText.anchor = GridBagConstraints.EAST;
		gbc_lblPilotidText.insets = new Insets(0, 0, 0, 5);
		gbc_lblPilotidText.gridx = 0;
		gbc_lblPilotidText.gridy = 7;
		panelBody.add(lblPilotidText, gbc_lblPilotidText);
		
		lblPilotId = new JLabel("New label");
		GridBagConstraints gbc_lblPilotId = new GridBagConstraints();
		gbc_lblPilotId.anchor = GridBagConstraints.WEST;
		gbc_lblPilotId.gridx = 1;
		gbc_lblPilotId.gridy = 7;
		panelBody.add(lblPilotId, gbc_lblPilotId);
		
		JPanel panelPassword = new JPanel();
		panelPassword.setBorder(new TitledBorder(null, "Password", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelContainer.add(panelPassword, BorderLayout.NORTH);
		GridBagLayout gbl_panelPassword = new GridBagLayout();
		gbl_panelPassword.columnWidths = new int[] { 225, 225, 0 };
		gbl_panelPassword.rowHeights = new int[] { 5, 30, 30, 30, 5 };
		gbl_panelPassword.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panelPassword.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelPassword.setLayout(gbl_panelPassword);
		
		JLabel lblPWD = new JLabel("New Password:");
		GridBagConstraints gbc_lblPWD = new GridBagConstraints();
		gbc_lblPWD.anchor = GridBagConstraints.EAST;
		gbc_lblPWD.insets = new Insets(0, 0, 5, 5);
		gbc_lblPWD.gridx = 0;
		gbc_lblPWD.gridy = 1;
		panelPassword.add(lblPWD, gbc_lblPWD);
		
		passwordEntry = new JPasswordField();
		GridBagConstraints gbc_passwordEntry = new GridBagConstraints();
		gbc_passwordEntry.insets = new Insets(0, 0, 5, 0);
		gbc_passwordEntry.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordEntry.gridx = 1;
		gbc_passwordEntry.gridy = 1;
		panelPassword.add(passwordEntry, gbc_passwordEntry);
		
		JLabel lblPWDConfirm = new JLabel("Confirm Password:");
		GridBagConstraints gbc_lblPWDConfirm = new GridBagConstraints();
		gbc_lblPWDConfirm.anchor = GridBagConstraints.EAST;
		gbc_lblPWDConfirm.insets = new Insets(0, 0, 5, 5);
		gbc_lblPWDConfirm.gridx = 0;
		gbc_lblPWDConfirm.gridy = 2;
		panelPassword.add(lblPWDConfirm, gbc_lblPWDConfirm);
		
		passwordConfirm = new JPasswordField();
		GridBagConstraints gbc_passwordConfirm = new GridBagConstraints();
		gbc_passwordConfirm.insets = new Insets(0, 0, 5, 0);
		gbc_passwordConfirm.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordConfirm.gridx = 1;
		gbc_passwordConfirm.gridy = 2;
		panelPassword.add(passwordConfirm, gbc_passwordConfirm);
		
		JButton btnPasswordUpdate = new JButton("Update Password");
		btnPasswordUpdate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				passwordUpdate();
			}
		});
		GridBagConstraints gbc_btnPasswordUpdate = new GridBagConstraints();
		gbc_btnPasswordUpdate.gridwidth = 2;
		gbc_btnPasswordUpdate.insets = new Insets(0, 0, 0, 5);
		gbc_btnPasswordUpdate.gridx = 0;
		gbc_btnPasswordUpdate.gridy = 3;
		panelPassword.add(btnPasswordUpdate, gbc_btnPasswordUpdate);
		
		JButton btnNewButton = new JButton("Update");
		btnNewButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				updateInformation();
			}
		});
		panelFooter.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Application.Events.FPSEventService.ShowPanelOnMaster(new UserList());
			}
		});
		panelFooter.add(btnCancel);
		panelFooter.add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Administration Use Only", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelContainer.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		
		chckboxUnlockAccount = new JCheckBox("Unlock Account");
		panel_1.add(chckboxUnlockAccount);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		
		chckboxIsAdministrator = new JCheckBox("Is Administrator");
		panel_2.add(chckboxIsAdministrator);
		
		// load user information
		loadData(userId);
	}
	
	private void loadData(int userId)
	{
		_User = _UserRepository.Get(userId);
		if (_User == null)
		{
			// invalid userId, show message and redirect
			JOptionPane.showMessageDialog(null, "Error", "Invalid Pilot Id: " + Integer.toString(userId) + System.lineSeparator() + "Redirecting to register a user",
					JOptionPane.ERROR_MESSAGE | JOptionPane.OK_OPTION);
			Application.Events.FPSEventService.ShowPanelOnMaster(new UserRegister());
		}
		else
		{
			// base user info
			setBaseUser(_User);
			chckboxIsAdministrator.setSelected(_User.IsAdmin);
			lblPilotId.setText(Integer.toString(_User.Id));
		}
	}
	
	@SuppressWarnings("deprecation")
	private void passwordUpdate()
	{
		// check password
		if (!passwordEntry.getText().equals(passwordConfirm.getText()))
			// show error
			JOptionPane.showMessageDialog(null, "Passwords do not match", "Warning", JOptionPane.WARNING_MESSAGE | JOptionPane.OK_OPTION);
		else // update password
		if (_UserRepository.UpdatePassword(_User, passwordEntry.getText()))
		{
			// success
			JOptionPane.showMessageDialog(null, "Password Updated", "Success", JOptionPane.INFORMATION_MESSAGE | JOptionPane.OK_OPTION);
			passwordEntry.setText("");
			passwordConfirm.setText("");
		}
		else
			// failure
			JOptionPane.showMessageDialog(null, "Error while updating your password, please try again later", "Error", JOptionPane.ERROR_MESSAGE | JOptionPane.OK_OPTION);
	}
	
	private void updateInformation()
	{
		// map over to current user
		Service.Model.User user = getBaseUser();
		_User.FirstName = user.FirstName;
		_User.LastName = user.LastName;
		_User.Address = user.Address;
		_User.City = user.City;
		_User.StateId = user.StateId;
		_User.Zip = user.Zip;
		_User.EMail = user.EMail;
		
		// map in administrator fields
		_User.IsAdmin = chckboxIsAdministrator.isSelected();
		_User.FailedLoginAttempts = chckboxUnlockAccount.isSelected() ? 0 : _User.FailedLoginAttempts;
		
		// check if we have a valid user
		List<String> userErrors = _User.IsValid();
		if (userErrors.size() > 0)
			// validation errors
			JOptionPane.showMessageDialog(null, "Errors Reported: " + System.lineSeparator() + String.join(System.lineSeparator(), userErrors), "Error",
					JOptionPane.ERROR_MESSAGE | JOptionPane.OK_OPTION);
		else if (!_UserRepository.Update(_User))
			// error creating user
			JOptionPane.showMessageDialog(null, "Error upading user's information, please try again later", "Error", JOptionPane.ERROR_MESSAGE | JOptionPane.OK_OPTION);
		else
		{
			// show success message
			JOptionPane.showMessageDialog(null, "User's information has been updated", "Success", JOptionPane.INFORMATION_MESSAGE | JOptionPane.OK_OPTION);
			
			// go to users screen
			Application.Events.FPSEventService.ShowPanelOnMaster(new UserList());
		}
	}
}
