package Application.Controls.Account;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import Application.Controls.BaseView;
import Application.Events.FPSEventService;
import Service.Model.User;
import Service.Security.CodeGen;
import Service.Util.Common;

public class ForgotPassword extends BaseView
{
	private static final long serialVersionUID = -4307871511804173444L;
	private JLabel lblResponseCode;
	private int _DebugResponseCode = 0;
	private int _RequestedPilotId = 0;
	private JPanel panelResponse;
	
	/**
	 * Create the panel.
	 */
	public ForgotPassword()
	{
		super("Forgot Password");
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 225, 225 };
		gbl_panel.rowHeights = new int[] { 0, 40, 40, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0 };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
		panel.setLayout(gbl_panel);
		
		JLabel lblEnterYourPilot = new JLabel("Enter your Pilot Id to request a password reset.");
		GridBagConstraints gbc_lblEnterYourPilot = new GridBagConstraints();
		gbc_lblEnterYourPilot.gridwidth = 2;
		gbc_lblEnterYourPilot.insets = new Insets(0, 0, 5, 0);
		gbc_lblEnterYourPilot.gridx = 0;
		gbc_lblEnterYourPilot.gridy = 0;
		panel.add(lblEnterYourPilot, gbc_lblEnterYourPilot);
		
		JLabel lblPilotId = new JLabel("Pilot Id:");
		lblPilotId.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblPilotId = new GridBagConstraints();
		gbc_lblPilotId.fill = GridBagConstraints.BOTH;
		gbc_lblPilotId.insets = new Insets(0, 0, 5, 5);
		gbc_lblPilotId.gridx = 0;
		gbc_lblPilotId.gridy = 1;
		panel.add(lblPilotId, gbc_lblPilotId);
		
		JSpinner spinnerPilotId = new JSpinner();
		spinnerPilotId.setModel(new SpinnerNumberModel(1, 1, 2147483647, 1));
		GridBagConstraints gbc_spinnerPilotId = new GridBagConstraints();
		gbc_spinnerPilotId.anchor = GridBagConstraints.WEST;
		gbc_spinnerPilotId.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerPilotId.gridx = 1;
		gbc_spinnerPilotId.gridy = 1;
		panel.add(spinnerPilotId, gbc_spinnerPilotId);
		
		JButton btnNewButton = new JButton("Request");
		btnNewButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// generate confirmation code
				int code = CodeGen.GenerateCode();
				lblResponseCode.setText("");
				
				// determine what to do based on our build
				if (Common.IsDEBUG())
				{
					// save code to check later
					_DebugResponseCode = code;
					_RequestedPilotId = (int)spinnerPilotId.getValue();
					lblResponseCode.setText("<html>DEBUG: Confirmation Code: " + code + "<br>");
				}
				else
				{
					// send confirmation code in email: {TBD}
				}
				lblResponseCode.setText(lblResponseCode.getText() + "A confirmation code has been sent to the e-mail on file.");
				panelResponse.setVisible(true);
			}
		});
		
		JButton btnSignIn = new JButton("Log In");
		btnSignIn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				FPSEventService.ShowPanelOnMaster(new SignIn());
			}
		});
		GridBagConstraints gbc_btnSignIn = new GridBagConstraints();
		gbc_btnSignIn.insets = new Insets(0, 0, 5, 5);
		gbc_btnSignIn.gridx = 0;
		gbc_btnSignIn.gridy = 2;
		panel.add(btnSignIn, gbc_btnSignIn);
		btnNewButton.setMnemonic('r');
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 2;
		panel.add(btnNewButton, gbc_btnNewButton);
		
		panelResponse = new JPanel();
		panelResponse.setVisible(false);
		GridBagConstraints gbc_panelResponse = new GridBagConstraints();
		gbc_panelResponse.gridwidth = 2;
		gbc_panelResponse.fill = GridBagConstraints.BOTH;
		gbc_panelResponse.gridx = 0;
		gbc_panelResponse.gridy = 3;
		panel.add(panelResponse, gbc_panelResponse);
		GridBagLayout gbl_panelResponse = new GridBagLayout();
		gbl_panelResponse.columnWidths = new int[] { 225, 225 };
		gbl_panelResponse.rowHeights = new int[] { 50, 30, 30 };
		gbl_panelResponse.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panelResponse.rowWeights = new double[] { Double.MIN_VALUE, 0.0 };
		panelResponse.setLayout(gbl_panelResponse);
		
		lblResponseCode = new JLabel("{code}");
		GridBagConstraints gbc_lblResponseCode = new GridBagConstraints();
		gbc_lblResponseCode.gridwidth = 2;
		gbc_lblResponseCode.insets = new Insets(0, 0, 5, 0);
		gbc_lblResponseCode.gridx = 0;
		gbc_lblResponseCode.gridy = 0;
		panelResponse.add(lblResponseCode, gbc_lblResponseCode);
		
		JLabel lblResponseCodeInput = new JLabel("Response Code:");
		lblResponseCodeInput.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblResponseCodeInput = new GridBagConstraints();
		gbc_lblResponseCodeInput.anchor = GridBagConstraints.EAST;
		gbc_lblResponseCodeInput.insets = new Insets(0, 0, 0, 5);
		gbc_lblResponseCodeInput.gridx = 0;
		gbc_lblResponseCodeInput.gridy = 1;
		panelResponse.add(lblResponseCodeInput, gbc_lblResponseCodeInput);
		
		JSpinner spinnerResponseCode = new JSpinner();
		spinnerResponseCode.setModel(new SpinnerNumberModel(0, 0, 2147483647, 1));
		GridBagConstraints gbc_spinnerResponseCode = new GridBagConstraints();
		gbc_spinnerResponseCode.anchor = GridBagConstraints.WEST;
		gbc_spinnerResponseCode.gridx = 1;
		gbc_spinnerResponseCode.gridy = 1;
		panelResponse.add(spinnerResponseCode, gbc_spinnerResponseCode);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int checkCode = (int)spinnerResponseCode.getValue();
				if (Common.IsDEBUG())
				{
					// check against stored value
					if (checkCode == _DebugResponseCode)
					{
						User user = new Service.Repository.User().Get(_RequestedPilotId);
						if (user != null)
							FPSEventService.LoginSuccessful(user);
						else
							lblResponseCode.setText("User not found");
					}
					else
						// invalid response code
						lblResponseCode.setText("Invalid response code, please try again.");
				}
				else
				{
					// check lookup table in database
				}
			}
		});
		btnConfirm.setMnemonic('c');
		GridBagConstraints gbc_btnConfirm = new GridBagConstraints();
		gbc_btnConfirm.gridwidth = 2;
		gbc_btnConfirm.insets = new Insets(0, 0, 5, 5);
		gbc_btnConfirm.gridx = 0;
		gbc_btnConfirm.gridy = 2;
		panelResponse.add(btnConfirm, gbc_btnConfirm);
	}
}
