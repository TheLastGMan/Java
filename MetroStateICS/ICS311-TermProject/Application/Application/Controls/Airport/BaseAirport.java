package Application.Controls.Airport;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import Application.Controls.BaseView;
import Application.Controls.Modules.StateComboBox;

public abstract class BaseAirport extends BaseView
{
	private static final long serialVersionUID = -3679732646772240654L;
	protected JPanel panelBody;
	private JTextField textCity;
	private JSpinner spinnerElevation;
	private JCheckBox checkboxTowerOnField;
	private JTextField textDescription;
	protected JPanel panelContent;
	private StateComboBox panelState;
	
	/**
	 * Create the panel.
	 */
	public BaseAirport(String title)
	{
		super(title);
		
		panelBody = new JPanel();
		add(panelBody, BorderLayout.CENTER);
		panelBody.setLayout(new BorderLayout(0, 0));
		
		panelContent = new JPanel();
		panelBody.add(panelContent, BorderLayout.CENTER);
		GridBagLayout gbl_panelContent = new GridBagLayout();
		gbl_panelContent.columnWidths = new int[] { 225, 255, 0 };
		gbl_panelContent.rowHeights = new int[] { 0, 30, 30, 30, 30, 30, 0 };
		gbl_panelContent.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panelContent.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0 };
		panelContent.setLayout(gbl_panelContent);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 0;
		panelContent.add(panel_2, gbc_panel_2);
		
		JLabel lblNewLabel_1 = new JLabel("City:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panelContent.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		textCity = new JTextField();
		GridBagConstraints gbc_textCity = new GridBagConstraints();
		gbc_textCity.insets = new Insets(0, 0, 5, 0);
		gbc_textCity.fill = GridBagConstraints.HORIZONTAL;
		gbc_textCity.gridx = 1;
		gbc_textCity.gridy = 1;
		panelContent.add(textCity, gbc_textCity);
		textCity.setColumns(10);
		
		JLabel lblState = new JLabel("State:");
		GridBagConstraints gbc_lblState = new GridBagConstraints();
		gbc_lblState.anchor = GridBagConstraints.EAST;
		gbc_lblState.insets = new Insets(0, 0, 5, 5);
		gbc_lblState.gridx = 0;
		gbc_lblState.gridy = 2;
		panelContent.add(lblState, gbc_lblState);
		
		panelState = new StateComboBox();
		GridBagConstraints gbc_comboBoxState = new GridBagConstraints();
		gbc_comboBoxState.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxState.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxState.gridx = 1;
		gbc_comboBoxState.gridy = 2;
		panelContent.add(panelState, gbc_comboBoxState);
		
		JLabel lblElevation = new JLabel("Elevation:");
		GridBagConstraints gbc_lblElevation = new GridBagConstraints();
		gbc_lblElevation.anchor = GridBagConstraints.EAST;
		gbc_lblElevation.insets = new Insets(0, 0, 5, 5);
		gbc_lblElevation.gridx = 0;
		gbc_lblElevation.gridy = 3;
		panelContent.add(lblElevation, gbc_lblElevation);
		
		spinnerElevation = new JSpinner();
		spinnerElevation.setModel(new SpinnerNumberModel(933, -1000, 10000, 1));
		GridBagConstraints gbc_spinnerElevation = new GridBagConstraints();
		gbc_spinnerElevation.anchor = GridBagConstraints.WEST;
		gbc_spinnerElevation.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerElevation.gridx = 1;
		gbc_spinnerElevation.gridy = 3;
		panelContent.add(spinnerElevation, gbc_spinnerElevation);
		
		JLabel lblTowerOnField = new JLabel("Tower at airport:");
		GridBagConstraints gbc_lblTowerOnField = new GridBagConstraints();
		gbc_lblTowerOnField.anchor = GridBagConstraints.EAST;
		gbc_lblTowerOnField.insets = new Insets(0, 0, 5, 5);
		gbc_lblTowerOnField.gridx = 0;
		gbc_lblTowerOnField.gridy = 4;
		panelContent.add(lblTowerOnField, gbc_lblTowerOnField);
		
		checkboxTowerOnField = new JCheckBox("");
		GridBagConstraints gbc_checkboxTowerOnField = new GridBagConstraints();
		gbc_checkboxTowerOnField.insets = new Insets(0, 0, 5, 0);
		gbc_checkboxTowerOnField.anchor = GridBagConstraints.WEST;
		gbc_checkboxTowerOnField.gridx = 1;
		gbc_checkboxTowerOnField.gridy = 4;
		panelContent.add(checkboxTowerOnField, gbc_checkboxTowerOnField);
		
		JLabel lblNewLabel_2 = new JLabel("Description:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 5;
		panelContent.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		textDescription = new JTextField();
		GridBagConstraints gbc_textDescription = new GridBagConstraints();
		gbc_textDescription.insets = new Insets(0, 0, 5, 0);
		gbc_textDescription.fill = GridBagConstraints.HORIZONTAL;
		gbc_textDescription.gridx = 1;
		gbc_textDescription.gridy = 5;
		panelContent.add(textDescription, gbc_textDescription);
		textDescription.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 0, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 6;
		panelContent.add(panel_3, gbc_panel_3);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.EAST);
	}

	protected Service.Model.Airport getBaseAirportData()
	{
		Service.Model.Airport airport = new Service.Model.Airport();
		airport.City = textCity.getText();
		airport.StateId = panelState.getStateId();
		airport.Elevation = (int)spinnerElevation.getValue();
		airport.TowerOnField = checkboxTowerOnField.isSelected();
		airport.Description = textDescription.getText();
		return airport;
	}
	
	protected void setBaseAirportData(Service.Model.Airport airport)
	{
		textCity.setText(airport.City);
		panelState.setStateId(airport.StateId);
		spinnerElevation.setValue(airport.Elevation);
		checkboxTowerOnField.setSelected(airport.TowerOnField);
		textDescription.setText(airport.Description);
	}
}
