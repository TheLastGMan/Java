package Application.Controls.Modules;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

public class DayTimeHrMinPanel extends TimeHrMinPanel
{
	private static final long serialVersionUID = 5560243694101937348L;
	private JSpinner spinnerDay;
	
	/**
	 * Create the panel.
	 */
	public DayTimeHrMinPanel()
	{
		GridBagLayout gridBagLayout = (GridBagLayout)getLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowWeights = new double[] { 1.0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0 };
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Day", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 2;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0 };
		gbl_panel.rowHeights = new int[] { 0 };
		gbl_panel.columnWeights = new double[] { 0.0 };
		gbl_panel.rowWeights = new double[] { 0.0 };
		panel.setLayout(gbl_panel);
		
		spinnerDay = new JSpinner();
		spinnerDay.setModel(new SpinnerNumberModel(1, 1, 31, 1));
		GridBagConstraints gbc_spinnerDay = new GridBagConstraints();
		gbc_spinnerDay.anchor = GridBagConstraints.NORTHWEST;
		gbc_spinnerDay.gridx = 0;
		gbc_spinnerDay.gridy = 0;
		panel.add(spinnerDay, gbc_spinnerDay);
	}
	
	public int getDay()
	{
		return (int)spinnerDay.getValue();
	}
	
	public void setDay(int value)
	{
		spinnerDay.setValue(value);
	}
}
