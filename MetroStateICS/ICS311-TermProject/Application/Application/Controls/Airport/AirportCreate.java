package Application.Controls.Airport;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class AirportCreate extends BaseAirport
{
	private static final long serialVersionUID = -6214871254503294145L;
	private static final Service.Repository.Airport _AirportRepository = new Service.Repository.Airport();
	private JTextField textIdentifier;
	
	/**
	 * Create the panel.
	 */
	public AirportCreate()
	{
		super("New Airport");
		panelContent.setBorder(new TitledBorder(null, "Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "NFDC Code", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelBody.add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 225, 225, 0 };
		gbl_panel.rowHeights = new int[] { 30, 30, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);
		
		JLabel lblNewLabel = new JLabel("Airport Identifier:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		textIdentifier = new JTextField();
		textIdentifier.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyTyped(KeyEvent arg0)
			{
				enterInfoMode(false);
			}
		});
		GridBagConstraints gbc_textIdentifier = new GridBagConstraints();
		gbc_textIdentifier.fill = GridBagConstraints.HORIZONTAL;
		gbc_textIdentifier.insets = new Insets(0, 0, 0, 5);
		gbc_textIdentifier.gridx = 1;
		gbc_textIdentifier.gridy = 0;
		panel.add(textIdentifier, gbc_textIdentifier);
		textIdentifier.setColumns(10);
		
		JButton btnVerify = new JButton("Check Identifier");
		btnVerify.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				verifyCode();
			}
		});
		GridBagConstraints gbc_btnVerify = new GridBagConstraints();
		gbc_btnVerify.gridwidth = 2;
		gbc_btnVerify.gridx = 0;
		gbc_btnVerify.gridy = 1;
		panel.add(btnVerify, gbc_btnVerify);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0 };
		panelFooter.setLayout(gridBagLayout);
		
		JPanel panelRegister = new JPanel();
		GridBagConstraints gbc_panelRegister = new GridBagConstraints();
		gbc_panelRegister.insets = new Insets(0, 0, 5, 0);
		gbc_panelRegister.fill = GridBagConstraints.BOTH;
		gbc_panelRegister.gridx = 0;
		gbc_panelRegister.gridy = 0;
		panelFooter.add(panelRegister, gbc_panelRegister);
		
		JButton btnCreate = new JButton("Add");
		btnCreate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				addAirport();
			}
		});
		panelRegister.add(btnCreate);
		
		JButton btnCancel = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 5, 0);
		gbc_btnCancel.gridx = 0;
		gbc_btnCancel.gridy = 1;
		panelFooter.add(btnCancel, gbc_btnCancel);
		btnCancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Application.Events.FPSEventService.ShowPanelOnMaster(new AirportList());
			}
		});
		
		// set mode
		enterInfoMode(false);
	}
	
	private void enterInfoMode(boolean info)
	{
		textIdentifier.setEditable(!info);
		panelContent.setVisible(info);
		panelFooter.setVisible(info);
	}
	
	private void verifyCode()
	{
		Service.Model.Airport airport = _AirportRepository.byCode(textIdentifier.getText());
		if (airport == null)
			// enter info mode
			enterInfoMode(true);
		else
			// airport code already exists
			JOptionPane.showMessageDialog(null, "Airport Identifier is already taken", "Warning", JOptionPane.INFORMATION_MESSAGE | JOptionPane.OK_OPTION);
	}
	
	private void addAirport()
	{
		Service.Model.Airport airport = getBaseAirportData();
		
		// set custom fields
		airport.Code = textIdentifier.getText();
		
		// check if we have a valid airport
		List<String> errors = airport.IsValid();
		if (errors.size() > 0)
			// validation errors
			JOptionPane.showMessageDialog(null, "Errors Reported: " + System.lineSeparator() + String.join(System.lineSeparator(), errors), "Error",
					JOptionPane.ERROR_MESSAGE | JOptionPane.OK_OPTION);
		else if (!_AirportRepository.Create(airport))
			// error creating user
			JOptionPane.showMessageDialog(null, "Error creating airport, please try again later", "Error", JOptionPane.ERROR_MESSAGE | JOptionPane.OK_OPTION);
		else
		{
			// show success message
			JOptionPane.showMessageDialog(null, "Airport Created", "Success", JOptionPane.OK_OPTION | JOptionPane.INFORMATION_MESSAGE | JOptionPane.OK_OPTION);
			
			// airport created, login
			Application.Events.FPSEventService.ShowPanelOnMaster(new AirportList());
		}
	}
}
