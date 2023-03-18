package Application.Controls.State;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Application.Controls.BaseView;

public abstract class BaseState extends BaseView
{
	private static final long serialVersionUID = -8560473824597021686L;
	protected JPanel panelContainer;
	protected JPanel panelBody;
	private JTextField textAbbreviation;
	private JTextField textName;
	
	/**
	 * Create the panel.
	 */
	public BaseState(String title)
	{
		super(title);
		
		panelContainer = new JPanel();
		add(panelContainer, BorderLayout.CENTER);
		panelContainer.setLayout(new BorderLayout(0, 0));
		
		panelBody = new JPanel();
		panelContainer.add(panelBody, BorderLayout.CENTER);
		GridBagLayout gbl_panelBody = new GridBagLayout();
		gbl_panelBody.columnWidths = new int[] { 225, 225, 0 };
		gbl_panelBody.rowHeights = new int[] { 30, 30 };
		gbl_panelBody.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panelBody.rowWeights = new double[] { 0.0, 0.0 };
		panelBody.setLayout(gbl_panelBody);
		
		JLabel lblNewLabel = new JLabel("Abbreviation:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panelBody.add(lblNewLabel, gbc_lblNewLabel);
		
		textAbbreviation = new JTextField();
		GridBagConstraints gbc_textAbbreviation = new GridBagConstraints();
		gbc_textAbbreviation.insets = new Insets(0, 0, 5, 0);
		gbc_textAbbreviation.fill = GridBagConstraints.HORIZONTAL;
		gbc_textAbbreviation.gridx = 1;
		gbc_textAbbreviation.gridy = 0;
		panelBody.add(textAbbreviation, gbc_textAbbreviation);
		textAbbreviation.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Name:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panelBody.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		textName = new JTextField();
		GridBagConstraints gbc_textName = new GridBagConstraints();
		gbc_textName.insets = new Insets(0, 0, 5, 0);
		gbc_textName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textName.gridx = 1;
		gbc_textName.gridy = 1;
		panelBody.add(textName, gbc_textName);
		textName.setColumns(10);
		
		JPanel panelRight = new JPanel();
		panelContainer.add(panelRight, BorderLayout.EAST);
	}
	
	protected Service.Model.State getBaseState()
	{
		Service.Model.State state = new Service.Model.State();
		state.Abbreviation = textAbbreviation.getText();
		state.Name = textName.getText();
		return state;
	}
	
	protected void setBaseState(Service.Model.State state)
	{
		textAbbreviation.setText(state.Abbreviation);
		textName.setText(state.Name);
	}
}
