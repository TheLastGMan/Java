package Application.Controls.User;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import Application.Controls.BaseView;
import Application.Controls.Modules.StateComboBox;

public abstract class BaseUser extends BaseView
{
	private static final long serialVersionUID = 4207466504717708647L;
	private JTextField textFirstName;
	private JTextField textLastName;
	private JTextField textAddress;
	private JTextField textCity;
	private JTextField textEMail;
	private JSpinner spinnerZip;
	protected JPanel panelContainer;
	protected JPanel panelBody;
	private StateComboBox panelState;
	private JLabel lblNewLabel_7;
	private JLabel lblUsername;
	
	/**
	 * Create the panel.
	 */
	public BaseUser(String title)
	{
		super(title);
		
		panelContainer = new JPanel();
		panelContainer.setLayout(new BorderLayout(0, 0));
		add(panelContainer, BorderLayout.CENTER);
		
		panelBody = new JPanel();
		panelContainer.add(panelBody, BorderLayout.CENTER);
		GridBagLayout gbl_panelBody = new GridBagLayout();
		gbl_panelBody.columnWidths = new int[] { 225, 225, 0 };
		gbl_panelBody.rowHeights = new int[] { 30, 30, 30, 30, 30, 30, 30, 30 };
		gbl_panelBody.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panelBody.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		panelBody.setLayout(gbl_panelBody);

		lblNewLabel_7 = new JLabel("Username:");
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7.gridx = 0;
		gbc_lblNewLabel_7.gridy = 0;
		panelBody.add(lblNewLabel_7, gbc_lblNewLabel_7);

		lblUsername = new JLabel("<dynamic>");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.WEST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 0);
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 0;
		panelBody.add(lblUsername, gbc_lblUsername);
		
		JLabel lblNewLabel = new JLabel("First Name:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		panelBody.add(lblNewLabel, gbc_lblNewLabel);
		
		textFirstName = new JTextField();
		GridBagConstraints gbc_textFirstName = new GridBagConstraints();
		gbc_textFirstName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFirstName.insets = new Insets(0, 0, 5, 0);
		gbc_textFirstName.gridx = 1;
		gbc_textFirstName.gridy = 1;
		panelBody.add(textFirstName, gbc_textFirstName);
		textFirstName.setColumns(64);
		
		JLabel lblNewLabel_1 = new JLabel("Last Name:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		panelBody.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		textLastName = new JTextField();
		GridBagConstraints gbc_textLastName = new GridBagConstraints();
		gbc_textLastName.insets = new Insets(0, 0, 5, 0);
		gbc_textLastName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textLastName.gridx = 1;
		gbc_textLastName.gridy = 2;
		panelBody.add(textLastName, gbc_textLastName);
		textLastName.setColumns(64);
		
		JLabel lblNewLabel_3 = new JLabel("Address:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 3;
		panelBody.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		textAddress = new JTextField();
		GridBagConstraints gbc_textAddress = new GridBagConstraints();
		gbc_textAddress.insets = new Insets(0, 0, 5, 0);
		gbc_textAddress.fill = GridBagConstraints.HORIZONTAL;
		gbc_textAddress.gridx = 1;
		gbc_textAddress.gridy = 3;
		panelBody.add(textAddress, gbc_textAddress);
		textAddress.setColumns(128);
		
		JLabel lblNewLabel_2 = new JLabel("City:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 4;
		panelBody.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		textCity = new JTextField();
		GridBagConstraints gbc_textCity = new GridBagConstraints();
		gbc_textCity.insets = new Insets(0, 0, 5, 0);
		gbc_textCity.fill = GridBagConstraints.HORIZONTAL;
		gbc_textCity.gridx = 1;
		gbc_textCity.gridy = 4;
		panelBody.add(textCity, gbc_textCity);
		textCity.setColumns(64);
		
		JLabel lblNewLabel_4 = new JLabel("State:");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 5;
		panelBody.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		panelState = new StateComboBox();
		GridBagConstraints gbc_comboBoxState = new GridBagConstraints();
		gbc_comboBoxState.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxState.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxState.gridx = 1;
		gbc_comboBoxState.gridy = 5;
		panelBody.add(panelState, gbc_comboBoxState);
		
		JLabel lblNewLabel_5 = new JLabel("Zip:");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 0;
		gbc_lblNewLabel_5.gridy = 6;
		panelBody.add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		spinnerZip = new JSpinner();
		spinnerZip.setModel(new SpinnerNumberModel(1, 1, 99999, 1));
		GridBagConstraints gbc_spinnerZip = new GridBagConstraints();
		gbc_spinnerZip.anchor = GridBagConstraints.WEST;
		gbc_spinnerZip.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerZip.gridx = 1;
		gbc_spinnerZip.gridy = 6;
		panelBody.add(spinnerZip, gbc_spinnerZip);
		
		JLabel lblNewLabel_6 = new JLabel("E-Mail:");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_6.gridx = 0;
		gbc_lblNewLabel_6.gridy = 7;
		panelBody.add(lblNewLabel_6, gbc_lblNewLabel_6);
		
		textEMail = new JTextField();
		GridBagConstraints gbc_textEMail = new GridBagConstraints();
		gbc_textEMail.fill = GridBagConstraints.HORIZONTAL;
		gbc_textEMail.gridx = 1;
		gbc_textEMail.gridy = 7;
		panelBody.add(textEMail, gbc_textEMail);
		textEMail.setColumns(64);
		
		JPanel panelRight = new JPanel();
		panelContainer.add(panelRight, BorderLayout.EAST);
	}
	
	protected Service.Model.User getBaseUser()
	{
		Service.Model.User user = new Service.Model.User();
		user.FirstName = textFirstName.getText();
		user.LastName = textLastName.getText();
		user.Address = textAddress.getText();
		user.City = textCity.getText();
		user.StateId = panelState.getStateId();
		user.Zip = (int)spinnerZip.getValue();
		user.EMail = textEMail.getText();
		return user;
	}
	
	protected void setBaseUser(Service.Model.User user)
	{
		lblUsername.setText(user.Username);
		textFirstName.setText(user.FirstName);
		textLastName.setText(user.LastName);
		textAddress.setText(user.Address);
		textCity.setText(user.City);
		panelState.setStateId(user.StateId);
		spinnerZip.setValue(user.Zip);
		textEMail.setText(user.EMail);
	}
}
