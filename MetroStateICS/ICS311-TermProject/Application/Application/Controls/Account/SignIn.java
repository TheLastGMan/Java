package Application.Controls.Account;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Application.Controls.BaseView;
import Application.Events.FPSEventService;

public class SignIn extends BaseView
{
	private static final long serialVersionUID = 5650482481185969924L;
	private static final Service.Repository.User _UserRepository = new Service.Repository.User();
	private JPasswordField userPassword;
	private JTextField userName;
	private JLabel lblInfo;
	
	/**
	 * Create the panel.
	 */
	public SignIn()
	{
		super("Log In");
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 225, 225 };
		gbl_panel.rowHeights = new int[] { 30, 30, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0 };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0 };
		panel.setLayout(gbl_panel);
		
		JLabel lblUserName = new JLabel("Username:");
		GridBagConstraints gbc_lblUserName = new GridBagConstraints();
		gbc_lblUserName.anchor = GridBagConstraints.EAST;
		gbc_lblUserName.gridy = 0;
		gbc_lblUserName.gridx = 0;
		panel.add(lblUserName, gbc_lblUserName);
		lblUserName.setHorizontalAlignment(SwingConstants.RIGHT);
		
		userName = new JTextField();
		GridBagConstraints gbc_userName = new GridBagConstraints();
		gbc_userName.anchor = GridBagConstraints.WEST;
		gbc_userName.insets = new Insets(0, 0, 5, 0);
		gbc_userName.fill = GridBagConstraints.HORIZONTAL;
		gbc_userName.gridx = 1;
		gbc_userName.gridy = 0;
		panel.add(userName, gbc_userName);
		
		userName.setColumns(16);
		JLabel lblPassword = new JLabel("Password:");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.gridy = 1;
		gbc_lblPassword.gridx = 0;
		panel.add(lblPassword, gbc_lblPassword);
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		
		userPassword = new JPasswordField();
		userPassword.setColumns(32);
		GridBagConstraints gbc_userPassword = new GridBagConstraints();
		gbc_userPassword.anchor = GridBagConstraints.WEST;
		gbc_userPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_userPassword.gridx = 1;
		gbc_userPassword.gridy = 1;
		panel.add(userPassword, gbc_userPassword);
		
		JPanel panelX = new JPanel();
		GridBagConstraints gbc_panelX = new GridBagConstraints();
		gbc_panelX.gridwidth = 2;
		gbc_panelX.gridy = 2;
		gbc_panelX.gridx = 0;
		panel.add(panelX, gbc_panelX);
		GridBagLayout gbl_panelX = new GridBagLayout();
		gbl_panelX.columnWidths = new int[] { 300 };
		gbl_panelX.rowHeights = new int[] { 30, 30, 30, 30 };
		gbl_panelX.columnWeights = new double[] { 0.0 };
		gbl_panelX.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
		panelX.setLayout(gbl_panelX);
		
		lblInfo = new JLabel("Username and Password are required.");
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblInfo = new GridBagConstraints();
		gbc_lblInfo.insets = new Insets(0, 0, 5, 0);
		gbc_lblInfo.fill = GridBagConstraints.VERTICAL;
		gbc_lblInfo.gridx = 0;
		gbc_lblInfo.gridy = 0;
		panelX.add(lblInfo, gbc_lblInfo);
		
		JButton btnSubmit = new JButton("Sign In");
		btnSubmit.addActionListener(new ActionListener()
		{
			@Override
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0)
			{
				// Validate input
				ArrayList<String> errors = new ArrayList<String>();
				if (userName.getText().trim().isEmpty())
					errors.add("Username");
				String password = userPassword.getText().trim();
				if (password.isEmpty())
					errors.add("Password");
				else if (password.length() <= 5)
					errors.add("Password: must be longer than 5 characters");
				
				// if errors, report to screen and exit
				if (!errors.isEmpty())
				{
					lblInfo.setText("Missing required fields: {" + String.join(", ", errors) + "}");
					return;
				}
				// check for valid username/password combination
				int isValidCombination = _UserRepository.validLoginState(userName.getText().trim(), userPassword.getText().trim());
				if (isValidCombination == 3)
					// connection issue
					lblInfo.setText("Unable to process requests at this time, please try again later.");
				else if (isValidCombination == 1)
					// validation successful, call login with users id as we
					// know they exist
					FPSEventService.LoginSuccessful(_UserRepository.byUsername(userName.getText()));
				else
					// error, report to screen
					lblInfo.setText("Invalid Username and/or Password, Please try again.");
			}
		});
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.insets = new Insets(0, 0, 5, 0);
		gbc_btnSubmit.gridx = 0;
		gbc_btnSubmit.gridy = 1;
		panelX.add(btnSubmit, gbc_btnSubmit);
		
		JButton btnForgotPassword = new JButton("Forgot Password");
		btnForgotPassword.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				FPSEventService.ShowPanelOnMaster(new ForgotPassword());
			}
		});
		GridBagConstraints gbc_btnForgotPassword = new GridBagConstraints();
		gbc_btnForgotPassword.insets = new Insets(0, 0, 5, 0);
		gbc_btnForgotPassword.gridx = 0;
		gbc_btnForgotPassword.gridy = 2;
		panelX.add(btnForgotPassword, gbc_btnForgotPassword);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				FPSEventService.ShowPanelOnMaster(new UserRegister());
			}
		});
		GridBagConstraints gbc_btnRegister = new GridBagConstraints();
		gbc_btnRegister.gridx = 0;
		gbc_btnRegister.gridy = 3;
		panelX.add(btnRegister, gbc_btnRegister);
	}
}
