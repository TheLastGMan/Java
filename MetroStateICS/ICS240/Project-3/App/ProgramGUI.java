package App;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;

import Core.*;
import Core.Info.*;

public class ProgramGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField SimFileTextBox;
	private JTextField SimSettingExportTxt;
	private JTextField CsvOutputTxt;
	private JSpinner ArrivalRate;
	private JSpinner DepartureRate;
	private JSpinner TimeToLand;
	private JSpinner TimeToTakeoff;
	private JSpinner TimeToReroute;
	private JSpinner Runways;
	private JSpinner SimulationTime;
	private JComboBox<AutoSimMode> SimAutoModeComboBox;
	private JSpinner SimAutoIncrement;
	private JSpinner SimAutoSimulations;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProgramGUI frame = new ProgramGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProgramGUI() {
		setResizable(false);
		setTitle("ICS-240: Project 3");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{424, 0};
		gbl_contentPane.rowHeights = new int[] {0, 225, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, 1.0};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel LoadSimFilePanel = new JPanel();
		LoadSimFilePanel.setVisible(false);
		GridBagConstraints gbc_LoadSimFilePanel = new GridBagConstraints();
		gbc_LoadSimFilePanel.anchor = GridBagConstraints.NORTH;
		gbc_LoadSimFilePanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_LoadSimFilePanel.insets = new Insets(0, 0, 5, 0);
		gbc_LoadSimFilePanel.gridx = 0;
		gbc_LoadSimFilePanel.gridy = 0;
		contentPane.add(LoadSimFilePanel, gbc_LoadSimFilePanel);
		LoadSimFilePanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_8 = new JLabel("Sim Setting Import");
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 14));
		LoadSimFilePanel.add(lblNewLabel_8, BorderLayout.NORTH);
		
		JPanel NorthBrowserPanel = new JPanel();
		LoadSimFilePanel.add(NorthBrowserPanel, BorderLayout.SOUTH);
		NorthBrowserPanel.setLayout(new BorderLayout(0, 0));
		
		SimFileTextBox = new JTextField();
		SimFileTextBox.setBackground(Color.LIGHT_GRAY);
		SimFileTextBox.setEditable(false);
		NorthBrowserPanel.add(SimFileTextBox, BorderLayout.CENTER);
		SimFileTextBox.setColumns(10);
		
		JButton btnNewButton = new JButton("browse");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jfc.setMultiSelectionEnabled(false);
				jfc.setFileFilter(new SimFilter());
				if(jfc.showOpenDialog(btnNewButton) == JFileChooser.APPROVE_OPTION)
					SimFileTextBox.setText(jfc.getSelectedFile().getAbsolutePath());
				else
					SimFileTextBox.setText("");
			}
		});
		btnNewButton.setMnemonic('b');
		NorthBrowserPanel.add(btnNewButton, BorderLayout.EAST);
		
		JPanel panel_1 = new JPanel();
		NorthBrowserPanel.add(panel_1, BorderLayout.SOUTH);
		
		JButton btnNewButton_1 = new JButton("Load");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(SimFileTextBox.getText().length() == 0)
				{
					JOptionPane.showMessageDialog(null, "Sim input output must be selected");
					return;
				}
			}
		});
		btnNewButton_1.setMnemonic('L');
		panel_1.add(btnNewButton_1);
		
		JPanel SimSettingsPanel = new JPanel();
		GridBagConstraints gbc_SimSettingsPanel = new GridBagConstraints();
		gbc_SimSettingsPanel.fill = GridBagConstraints.BOTH;
		gbc_SimSettingsPanel.insets = new Insets(0, 0, 5, 0);
		gbc_SimSettingsPanel.gridx = 0;
		gbc_SimSettingsPanel.gridy = 1;
		contentPane.add(SimSettingsPanel, gbc_SimSettingsPanel);
		SimSettingsPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_7 = new JLabel("Simulation Settings");
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 14));
		SimSettingsPanel.add(lblNewLabel_7, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		SimSettingsPanel.add(panel);
		panel.setLayout(new GridLayout(7, 2, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Arrival Rate %: ");
		panel.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		
		ArrivalRate = new JSpinner();
		panel.add(ArrivalRate);
		ArrivalRate.setModel(new SpinnerNumberModel(new Byte((byte) 45), new Byte((byte) 0), new Byte((byte) 100), new Byte((byte) 1)));
		
		JLabel lblNewLabel_1 = new JLabel("Departure Rate %: ");
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		
		DepartureRate = new JSpinner();
		panel.add(DepartureRate);
		DepartureRate.setModel(new SpinnerNumberModel(new Byte((byte) 20), new Byte((byte) 0), new Byte((byte) 100), new Byte((byte) 1)));
		
		JLabel lblNewLabel_2 = new JLabel("Time to Land (min): ");
		panel.add(lblNewLabel_2);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.TRAILING);
		
		TimeToLand = new JSpinner();
		panel.add(TimeToLand);
		TimeToLand.setModel(new SpinnerNumberModel(new Byte((byte) 2), new Byte((byte) 0), new Byte((byte) 120), new Byte((byte) 1)));
		
		JLabel lblNewLabel_3 = new JLabel("Time to Takeoff (min): ");
		panel.add(lblNewLabel_3);
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.TRAILING);
		
		TimeToTakeoff = new JSpinner();
		panel.add(TimeToTakeoff);
		TimeToTakeoff.setModel(new SpinnerNumberModel(new Byte((byte) 3), new Byte((byte) 0), new Byte((byte) 120), new Byte((byte) 1)));
		
		JLabel lblNewLabel_4 = new JLabel("Time Until Reroute (min): ");
		panel.add(lblNewLabel_4);
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.TRAILING);
		
		TimeToReroute = new JSpinner();
		panel.add(TimeToReroute);
		TimeToReroute.setModel(new SpinnerNumberModel(new Byte((byte) 10), new Byte((byte) 0), new Byte((byte) 120), new Byte((byte) 1)));
		
		JLabel lblNewLabel_5 = new JLabel("Runways: ");
		panel.add(lblNewLabel_5);
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.TRAILING);
		
		Runways = new JSpinner();
		panel.add(Runways);
		Runways.setModel(new SpinnerNumberModel(new Byte((byte) 1), new Byte((byte) 0), new Byte((byte) 16), new Byte((byte) 1)));
		
		JLabel lblNewLabel_6 = new JLabel("Simulation Time (min): ");
		panel.add(lblNewLabel_6);
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.TRAILING);
		
		SimulationTime = new JSpinner();
		panel.add(SimulationTime);
		SimulationTime.setModel(new SpinnerNumberModel(new Short((short) 600), new Short((short) 1), new Short((short) 10080), new Short((short) 1)));
		
		JPanel SimAutoSettingsPanel = new JPanel();
		GridBagConstraints gbc_SimAutoSettingsPanel = new GridBagConstraints();
		gbc_SimAutoSettingsPanel.insets = new Insets(0, 0, 5, 0);
		gbc_SimAutoSettingsPanel.anchor = GridBagConstraints.NORTH;
		gbc_SimAutoSettingsPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_SimAutoSettingsPanel.gridx = 0;
		gbc_SimAutoSettingsPanel.gridy = 2;
		contentPane.add(SimAutoSettingsPanel, gbc_SimAutoSettingsPanel);
		SimAutoSettingsPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblAutoSimulationSettings = new JLabel("Auto Simulation Settings");
		lblAutoSimulationSettings.setHorizontalAlignment(SwingConstants.CENTER);
		lblAutoSimulationSettings.setFont(new Font("Tahoma", Font.BOLD, 14));
		SimAutoSettingsPanel.add(lblAutoSimulationSettings, BorderLayout.NORTH);
		
		JPanel panel_2 = new JPanel();
		SimAutoSettingsPanel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new GridLayout(2, 3, 0, 0));
		
		JLabel lblNewLabel_9 = new JLabel("Variable");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_9.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("Increment");
		lblNewLabel_10.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_10.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("Simulations");
		lblNewLabel_11.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblNewLabel_11);
		
		SimAutoModeComboBox = new JComboBox<AutoSimMode>();
		SimAutoModeComboBox.setBackground(Color.GRAY);
		SimAutoModeComboBox.setModel(new DefaultComboBoxModel<AutoSimMode>(AutoSimMode.values()));
		panel_2.add(SimAutoModeComboBox);
		
		SimAutoIncrement = new JSpinner();
		SimAutoIncrement.setModel(new SpinnerNumberModel(new Byte((byte) 1), new Byte((byte) 0), new Byte((byte) 100), new Byte((byte) 1)));
		panel_2.add(SimAutoIncrement);
		
		SimAutoSimulations = new JSpinner();
		SimAutoSimulations.setModel(new SpinnerNumberModel(1, 1, 30000, 1));
		panel_2.add(SimAutoSimulations);
		
		JPanel SimSettingExportPanel = new JPanel();
		SimSettingExportPanel.setVisible(false);
		GridBagConstraints gbc_SimSettingExportPanel = new GridBagConstraints();
		gbc_SimSettingExportPanel.insets = new Insets(0, 0, 5, 0);
		gbc_SimSettingExportPanel.fill = GridBagConstraints.BOTH;
		gbc_SimSettingExportPanel.gridx = 0;
		gbc_SimSettingExportPanel.gridy = 3;
		contentPane.add(SimSettingExportPanel, gbc_SimSettingExportPanel);
		SimSettingExportPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblSimSettingExport = new JLabel("Sim Setting Export");
		lblSimSettingExport.setHorizontalAlignment(SwingConstants.CENTER);
		lblSimSettingExport.setFont(new Font("Tahoma", Font.BOLD, 14));
		SimSettingExportPanel.add(lblSimSettingExport, BorderLayout.NORTH);
		
		SimSettingExportTxt = new JTextField();
		SimSettingExportTxt.setBackground(Color.LIGHT_GRAY);
		SimSettingExportTxt.setEditable(false);
		SimSettingExportTxt.setColumns(10);
		SimSettingExportPanel.add(SimSettingExportTxt, BorderLayout.CENTER);
		
		JButton SettingExportBrowseBtn = new JButton("browse");
		SettingExportBrowseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jfc.setMultiSelectionEnabled(false);
				jfc.setFileFilter(new SimFilter());
				if(jfc.showOpenDialog(SettingExportBrowseBtn) == JFileChooser.APPROVE_OPTION)
					SimSettingExportTxt.setText(jfc.getSelectedFile().getName());
				else
					SimSettingExportTxt.setText("");
			}
		});
		SimSettingExportPanel.add(SettingExportBrowseBtn, BorderLayout.EAST);
		
		JPanel panel_6 = new JPanel();
		SimSettingExportPanel.add(panel_6, BorderLayout.SOUTH);
		
		JButton OutputSettingLoadBtn = new JButton("Export");
		OutputSettingLoadBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(SimSettingExportTxt.getText().length() == 0)
				{
					JOptionPane.showMessageDialog(null, "Sim output file must be selected.");
					return;
				}
			}
		});
		OutputSettingLoadBtn.setMnemonic('E');
		panel_6.add(OutputSettingLoadBtn);
		
		JPanel OutputFilePanel = new JPanel();
		GridBagConstraints gbc_OutputFilePanel = new GridBagConstraints();
		gbc_OutputFilePanel.insets = new Insets(0, 0, 5, 0);
		gbc_OutputFilePanel.fill = GridBagConstraints.BOTH;
		gbc_OutputFilePanel.gridx = 0;
		gbc_OutputFilePanel.gridy = 4;
		contentPane.add(OutputFilePanel, gbc_OutputFilePanel);
		OutputFilePanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblOutputFile = new JLabel("Output File");
		lblOutputFile.setHorizontalAlignment(SwingConstants.CENTER);
		lblOutputFile.setFont(new Font("Tahoma", Font.BOLD, 14));
		OutputFilePanel.add(lblOutputFile, BorderLayout.NORTH);
		
		CsvOutputTxt = new JTextField();
		CsvOutputTxt.setBackground(Color.LIGHT_GRAY);
		CsvOutputTxt.setEditable(false);
		CsvOutputTxt.setColumns(10);
		OutputFilePanel.add(CsvOutputTxt, BorderLayout.CENTER);
		
		JButton OutputBrowseBtn = new JButton("browse");
		OutputBrowseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jfc.setMultiSelectionEnabled(false);
				jfc.setFileFilter(new CsvFilter());
				if(jfc.showSaveDialog(OutputBrowseBtn) == JFileChooser.APPROVE_OPTION)
					CsvOutputTxt.setText(jfc.getSelectedFile().getAbsolutePath());
				else
					CsvOutputTxt.setText("");
			}
		});
		OutputFilePanel.add(OutputBrowseBtn, BorderLayout.EAST);
		
		JPanel RunPanel = new JPanel();
		GridBagConstraints gbc_RunPanel = new GridBagConstraints();
		gbc_RunPanel.fill = GridBagConstraints.BOTH;
		gbc_RunPanel.gridx = 0;
		gbc_RunPanel.gridy = 5;
		contentPane.add(RunPanel, gbc_RunPanel);
		RunPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblRunSimulation = new JLabel("Run Simulation");
		lblRunSimulation.setHorizontalAlignment(SwingConstants.CENTER);
		lblRunSimulation.setFont(new Font("Tahoma", Font.BOLD, 14));
		RunPanel.add(lblRunSimulation, BorderLayout.NORTH);
		
		JPanel panel_8 = new JPanel();
		RunPanel.add(panel_8, BorderLayout.SOUTH);
		
		JButton RunSim = new JButton("Simulate");
		panel_8.add(RunSim);
		RunSim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//validate
				if(CsvOutputTxt.getText().length() == 0)
				{
					JOptionPane.showMessageDialog(null, "Output must be selected");
					return;
				}
				
				
				//setup
				SimInfo info = simInfo();
				AutoSim autoInfo = autoSim();
				
				//run
				Simulator sim = new Simulator();
				ArrayList<SimMultiResult> simResults = sim.SimulateMultiple(info,  autoInfo);
				
				//save
				if(!new Exporter().SaveToFile(simResults, CsvOutputTxt.getText()))
					JOptionPane.showMessageDialog(null, "Error exporting results.");
				else
					JOptionPane.showMessageDialog(null, "Simulation Complete");
			}
		});
		RunSim.setMnemonic('S');
	}
	
	private SimInfo simInfo()
	{
		SimInfo info = new SimInfo();
		info.setArrivalRatePercent((byte)ArrivalRate.getValue());
		info.setDepartureRatePercent((byte)DepartureRate.getValue());
		info.setMinsTimeRemaining((byte)TimeToReroute.getValue());
		info.setMinsToLand((byte)TimeToLand.getValue());
		info.setMinsToTakeoff((byte)TimeToTakeoff.getValue());
		info.setRunwaysAvailable((byte)Runways.getValue());
		info.setSimulationTime((short)SimulationTime.getValue());
		return info;
	}
	
	private AutoSim autoSim()
	{
		AutoSim autoInfo = new AutoSim();
		autoInfo.Mode = (AutoSimMode)SimAutoModeComboBox.getSelectedItem();
		autoInfo.Increment = (byte)SimAutoIncrement.getValue();
		autoInfo.Simulations = (int)SimAutoSimulations.getValue();
		return autoInfo;
	}
}
