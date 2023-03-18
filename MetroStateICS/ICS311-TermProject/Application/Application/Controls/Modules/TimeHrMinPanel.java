package Application.Controls.Modules;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

public class TimeHrMinPanel extends JPanel
{
	private static final long serialVersionUID = -2687912436482707488L;
	private JSpinner spinnerHr;
	private JSpinner spinnerMin;
	
	/**
	 * Create the panel.
	 */
	public TimeHrMinPanel()
	{
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0 };
		gridBagLayout.rowWeights = new double[] { 1.0 };
		setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Hr", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0 };
		gbl_panel.rowHeights = new int[] { 0 };
		gbl_panel.columnWeights = new double[] { 0.0 };
		gbl_panel.rowWeights = new double[] { 0.0 };
		panel.setLayout(gbl_panel);
		
		spinnerHr = new JSpinner();
		spinnerHr.setModel(new SpinnerNumberModel(new Byte((byte)0), new Byte((byte)0), new Byte((byte)23), new Byte((byte)1)));
		GridBagConstraints gbc_spinnerHr = new GridBagConstraints();
		gbc_spinnerHr.anchor = GridBagConstraints.NORTHWEST;
		gbc_spinnerHr.gridx = 0;
		gbc_spinnerHr.gridy = 0;
		panel.add(spinnerHr, gbc_spinnerHr);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Min", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 0;
		add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0 };
		gbl_panel_1.rowHeights = new int[] { 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0 };
		gbl_panel_1.rowWeights = new double[] { 0.0 };
		panel_1.setLayout(gbl_panel_1);
		
		spinnerMin = new JSpinner();
		spinnerMin.setModel(new SpinnerNumberModel(new Byte((byte)0), new Byte((byte)0), new Byte((byte)59), new Byte((byte)1)));
		GridBagConstraints gbc_spinnerMin = new GridBagConstraints();
		gbc_spinnerMin.anchor = GridBagConstraints.NORTHWEST;
		gbc_spinnerMin.gridx = 0;
		gbc_spinnerMin.gridy = 0;
		panel_1.add(spinnerMin, gbc_spinnerMin);
		
	}
	
	public byte getHours()
	{
		return (byte)spinnerHr.getValue();
	}
	
	public void setHours(byte value)
	{
		spinnerHr.setValue(value);
	}
	
	public byte getMin()
	{
		return (byte)spinnerMin.getValue();
	}
	
	public void setMin(byte value)
	{
		spinnerMin.setValue(value);
	}
}
