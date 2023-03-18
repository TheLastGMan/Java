package Application.Controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

public abstract class BaseView extends JPanel
{
	private static final long serialVersionUID = -6669816513981143943L;
	protected JPanel panelFooter;
	protected JPanel panelWrapper;
	private JPanel panel;
	
	/**
	 * Create the panel.
	 */
	public BaseView(String title)
	{
		setLayout(new BorderLayout(0, 0));
		Border paddingBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		
		panelWrapper = new JPanel();
		add(panelWrapper, BorderLayout.CENTER);
		panelWrapper.setLayout(new BorderLayout(0, 0));
		
		panelFooter = new JPanel();
		add(panelFooter, BorderLayout.SOUTH);
		
		panel = new JPanel();
		panel.setBorder(new MatteBorder(0, 0, 1, 0, new Color(0, 0, 0)));
		add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 100, 0 };
		gbl_panel.rowHeights = new int[] { 31, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);
		
		JLabel lblHeader = new JLabel(title);
		GridBagConstraints gbc_lblHeader = new GridBagConstraints();
		gbc_lblHeader.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblHeader.anchor = GridBagConstraints.NORTH;
		gbc_lblHeader.gridx = 0;
		gbc_lblHeader.gridy = 0;
		panel.add(lblHeader, gbc_lblHeader);
		lblHeader.setFont(new Font("Georgia", lblHeader.getFont().getStyle(), 18));
		lblHeader.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeader.setBorder(paddingBorder);
	}
}
