package Application.Controls.Account;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Application.Controls.User.BaseUser;

public class UserRegister extends BaseUser
{
	private static final long serialVersionUID = -3105726839248295726L;
	private static final Service.Repository.User _UserRepository = new Service.Repository.User();
	private JTextField textUsername;
	private JPasswordField passwordEntry;
	private JPasswordField passwordConfirm;
	private JPanel panelUsername;
	private JPanel panelPassword;
	private JPanel panelRegister;
	
	/**
	 * Create the panel.
	 */
	public UserRegister()
	{
		super("Register");
		
		panelUsername = new JPanel();
		panelContainer.add(panelUsername, BorderLayout.NORTH);
		GridBagLayout gbl_panelUsername = new GridBagLayout();
		gbl_panelUsername.columnWidths = new int[] { 225, 225, 0 };
		gbl_panelUsername.rowHeights = new int[] { 15, 30, 30, 15 };
		gbl_panelUsername.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panelUsername.rowWeights = new double[] { 1.0, 0.0, 0.0 };
		panelUsername.setLayout(gbl_panelUsername);
		
		JPanel panelSpacer1 = new JPanel();
		GridBagConstraints gbc_panelSpacer1 = new GridBagConstraints();
		gbc_panelSpacer1.gridwidth = 2;
		gbc_panelSpacer1.insets = new Insets(0, 0, 5, 5);
		gbc_panelSpacer1.fill = GridBagConstraints.BOTH;
		gbc_panelSpacer1.gridx = 0;
		gbc_panelSpacer1.gridy = 0;
		panelUsername.add(panelSpacer1, gbc_panelSpacer1);
		
		JPanel panelSpacer2 = new JPanel();
		GridBagConstraints gbc_panelSpacer2 = new GridBagConstraints();
		gbc_panelSpacer2.gridwidth = 2;
		gbc_panelSpacer2.gridy = 3;
		gbc_panelSpacer2.gridx = 0;
		gbc_panelSpacer1.gridwidth = 2;
		gbc_panelSpacer1.insets = new Insets(0, 0, 5, 5);
		gbc_panelSpacer1.fill = GridBagConstraints.BOTH;
		gbc_panelSpacer1.gridx = 0;
		gbc_panelSpacer1.gridy = 3;
		panelUsername.add(panelSpacer2, gbc_panelSpacer2);
		
		JLabel lblUsername = new JLabel("Username:");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.EAST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 0;
		gbc_lblUsername.gridy = 1;
		panelUsername.add(lblUsername, gbc_lblUsername);
		
		textUsername = new JTextField();
		textUsername.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyTyped(KeyEvent arg0)
			{
				// key change, force a verify action
				enterUserInfoMode(false);
			}
		});
		GridBagConstraints gbc_textUsername = new GridBagConstraints();
		gbc_textUsername.insets = new Insets(0, 0, 5, 0);
		gbc_textUsername.fill = GridBagConstraints.HORIZONTAL;
		gbc_textUsername.gridx = 1;
		gbc_textUsername.gridy = 1;
		panelUsername.add(textUsername, gbc_textUsername);
		textUsername.setColumns(10);
		
		JButton btnCheck = new JButton("Check Availability");
		btnCheck.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				checkUsernameAvailability();
			}
		});
		GridBagConstraints gbc_btnCheck = new GridBagConstraints();
		gbc_btnCheck.gridwidth = 2;
		gbc_btnCheck.gridx = 0;
		gbc_btnCheck.gridy = 2;
		panelUsername.add(btnCheck, gbc_btnCheck);
		
		panelPassword = new JPanel();
		panelContainer.add(panelPassword, BorderLayout.SOUTH);
		GridBagLayout gbl_panelPassword = new GridBagLayout();
		gbl_panelPassword.columnWidths = new int[] { 225, 1, 0 };
		gbl_panelPassword.rowHeights = new int[] { 0, 0, 0 };
		gbl_panelPassword.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panelPassword.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panelPassword.setLayout(gbl_panelPassword);
		
		JLabel lblPassword = new JLabel("Password:");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 0;
		panelPassword.add(lblPassword, gbc_lblPassword);
		
		passwordEntry = new JPasswordField();
		GridBagConstraints gbc_passwordEntry = new GridBagConstraints();
		gbc_passwordEntry.insets = new Insets(0, 0, 5, 0);
		gbc_passwordEntry.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordEntry.gridx = 1;
		gbc_passwordEntry.gridy = 0;
		panelPassword.add(passwordEntry, gbc_passwordEntry);
		
		JLabel lblPasswordVerify = new JLabel("Verify:");
		GridBagConstraints gbc_lblPasswordVerify = new GridBagConstraints();
		gbc_lblPasswordVerify.anchor = GridBagConstraints.EAST;
		gbc_lblPasswordVerify.insets = new Insets(0, 0, 0, 5);
		gbc_lblPasswordVerify.gridx = 0;
		gbc_lblPasswordVerify.gridy = 1;
		panelPassword.add(lblPasswordVerify, gbc_lblPasswordVerify);
		
		passwordConfirm = new JPasswordField();
		GridBagConstraints gbc_passwordConfirm = new GridBagConstraints();
		gbc_passwordConfirm.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordConfirm.gridx = 1;
		gbc_passwordConfirm.gridy = 1;
		panelPassword.add(passwordConfirm, gbc_passwordConfirm);
		
		panelRegister = new JPanel();
		panelFooter.add(panelRegister);
		panelRegister.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				registerUser();
			}
		});
		panelRegister.add(btnRegister);
		
		// hide body and register button
		enterUserInfoMode(false);
	}
	
	private void enterUserInfoMode(boolean status)
	{
		textUsername.setEditable(!status);
		panelBody.setVisible(status);
		panelPassword.setVisible(status);
		panelRegister.setVisible(status);
	}
	
	private void checkUsernameAvailability()
	{
		if (_UserRepository.byUsername(textUsername.getText()) == null)
			// available, allow more info
			enterUserInfoMode(true);
		else
			// Username in use
			JOptionPane.showMessageDialog(null, "Username is already taken", "Warning", JOptionPane.WARNING_MESSAGE | JOptionPane.OK_OPTION);
	}
	
	@SuppressWarnings("deprecation")
	private void registerUser()
	{
		// check password
		if (!passwordEntry.getText().equals(passwordConfirm.getText()))
		{
			// show error
			JOptionPane.showMessageDialog(null, "Passwords do not match", "Warning", JOptionPane.WARNING_MESSAGE | JOptionPane.OK_OPTION);
			return;
		}
		else if (passwordEntry.getText().length() < 6)
		{
			// show error
			JOptionPane.showMessageDialog(null, "Password must be longer than 5 characters", "Warning", JOptionPane.WARNING_MESSAGE | JOptionPane.OK_OPTION);
			return;
		}
		
		// load user data
		Service.Model.User user = getBaseUser();
		
		// set custom fields
		user.Username = textUsername.getText();
		
		// check if we have a valid user
		List<String> userErrors = user.IsValid();
		if (userErrors.size() > 0)
			// validation errors
			JOptionPane.showMessageDialog(null, "Errors Reported: " + System.lineSeparator() + System.lineSeparator() + String.join(System.lineSeparator(), userErrors), "Error",
					JOptionPane.ERROR_MESSAGE | JOptionPane.OK_OPTION);
		else if (!_UserRepository.Create(user, passwordEntry.getText()))
			// error creating user
			JOptionPane.showMessageDialog(null, "Error filing your registration, please try again later", "Error", JOptionPane.ERROR_MESSAGE | JOptionPane.OK_OPTION);
		else
		{
			// show success message
			JOptionPane.showMessageDialog(null,
					"Thank you, your Pilot Id is: " + Integer.toString(user.Id) + System.lineSeparator() + "An email has been sent welcoming you into our ranks", "Success",
					JOptionPane.OK_OPTION | JOptionPane.INFORMATION_MESSAGE);
			
			// user created, login
			Application.Events.FPSEventService.ShowPanelOnMaster(new SignIn());
			;
		}
	}
}
