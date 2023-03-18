package Application.Controls.Flight;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import Application.Controls.BaseView;
import Application.Controls.Modules.AirportComboBox;
import Application.Controls.Modules.DayTimeHrMinPanel;
import Application.Controls.Modules.TimeHrMinPanel;

public class BaseFlight extends BaseView
{
	private static final long serialVersionUID = 1403055375468069954L;
	private JRadioButton rdbtnVfr;
	private JRadioButton rdbtnIfr;
	private JTextField textIdentification;
	private JTextField textAircraftType;
	private JSpinner spinnerCruisingAltitude;
	private JSpinner spinnerTrueAirspeed;
	private JTextArea textAreaRouteOfFlight;
	private JTextArea textAreaRemarks;
	private JTextField textPilotInfo;
	private JTextField textDestinationContact;
	private JSpinner spinnerNumberAboard;
	private DayTimeHrMinPanel panelDepartureTime;
	private TimeHrMinPanel panelTimeEnroute;
	private TimeHrMinPanel panelFuelOnBoard;
	private AirportComboBox panelDestinationPoint;
	private AirportComboBox panelAlternatePoint;
	private JTextField textColorOfAircraft;
	private AirportComboBox panelDeparturePoint;
	
	/**
	 * Create the panel.
	 */
	public BaseFlight(String title)
	{
		super(title);
		
		JPanel panelFlightPlan = new JPanel();
		panelWrapper.add(panelFlightPlan, BorderLayout.CENTER);
		panelFlightPlan.setLayout(new GridLayout(5, 0, 0, 0));
		
		JPanel tblRow1 = new JPanel();
		panelFlightPlan.add(tblRow1);
		GridBagLayout gbl_tblRow1 = new GridBagLayout();
		gbl_tblRow1.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_tblRow1.rowHeights = new int[] { 0 };
		gbl_tblRow1.columnWeights = new double[] { 4.0, 1.0, 1.0, 12.0, 2.0, 1.0, 1.0 };
		gbl_tblRow1.rowWeights = new double[] { 1.0 };
		tblRow1.setLayout(gbl_tblRow1);
		
		JPanel tr1c1 = new JPanel();
		tr1c1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Type", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_tr1c1 = new GridBagConstraints();
		gbc_tr1c1.fill = GridBagConstraints.BOTH;
		gbc_tr1c1.gridx = 0;
		gbc_tr1c1.gridy = 0;
		tblRow1.add(tr1c1, gbc_tr1c1);
		
		rdbtnVfr = new JRadioButton("VFR");
		rdbtnVfr.setSelected(true);
		rdbtnIfr = new JRadioButton("IFR");
		tr1c1.setLayout(new BoxLayout(tr1c1, BoxLayout.Y_AXIS));
		tr1c1.add(rdbtnVfr);
		tr1c1.add(rdbtnIfr);
		
		JPanel tr1c2 = new JPanel();
		tr1c2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Identification", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_tr1c2 = new GridBagConstraints();
		gbc_tr1c2.fill = GridBagConstraints.BOTH;
		gbc_tr1c2.gridx = 1;
		gbc_tr1c2.gridy = 0;
		tblRow1.add(tr1c2, gbc_tr1c2);
		GridBagLayout gbl_tr1c2 = new GridBagLayout();
		gbl_tr1c2.columnWidths = new int[] { 0 };
		gbl_tr1c2.rowHeights = new int[] { 0 };
		gbl_tr1c2.columnWeights = new double[] { 1.0 };
		gbl_tr1c2.rowWeights = new double[] { 0.0 };
		tr1c2.setLayout(gbl_tr1c2);
		
		textIdentification = new JTextField();
		textIdentification.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_textIdentification = new GridBagConstraints();
		gbc_textIdentification.insets = new Insets(0, 5, 0, 5);
		gbc_textIdentification.fill = GridBagConstraints.BOTH;
		gbc_textIdentification.gridx = 0;
		gbc_textIdentification.gridy = 0;
		tr1c2.add(textIdentification, gbc_textIdentification);
		textIdentification.setColumns(10);
		
		JPanel tr1c3 = new JPanel();
		tr1c3.setBorder(new TitledBorder(null, "Aircraft Type", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_tr1c3 = new GridBagConstraints();
		gbc_tr1c3.fill = GridBagConstraints.BOTH;
		gbc_tr1c3.gridx = 2;
		gbc_tr1c3.gridy = 0;
		tblRow1.add(tr1c3, gbc_tr1c3);
		GridBagLayout gbl_tr1c3 = new GridBagLayout();
		gbl_tr1c3.columnWidths = new int[] { 0 };
		gbl_tr1c3.rowHeights = new int[] { 0 };
		gbl_tr1c3.columnWeights = new double[] { 1.0 };
		gbl_tr1c3.rowWeights = new double[] { 0.0 };
		tr1c3.setLayout(gbl_tr1c3);
		
		textAircraftType = new JTextField();
		GridBagConstraints gbc_textAircraftType = new GridBagConstraints();
		gbc_textAircraftType.insets = new Insets(0, 5, 0, 5);
		gbc_textAircraftType.fill = GridBagConstraints.HORIZONTAL;
		gbc_textAircraftType.gridx = 0;
		gbc_textAircraftType.gridy = 0;
		tr1c3.add(textAircraftType, gbc_textAircraftType);
		textAircraftType.setColumns(10);
		
		JPanel tr1c4 = new JPanel();
		tr1c4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Speed (KTS)", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_tr1c4 = new GridBagConstraints();
		gbc_tr1c4.fill = GridBagConstraints.BOTH;
		gbc_tr1c4.gridx = 3;
		gbc_tr1c4.gridy = 0;
		tblRow1.add(tr1c4, gbc_tr1c4);
		GridBagLayout gbl_tr1c4 = new GridBagLayout();
		gbl_tr1c4.columnWidths = new int[] { 0 };
		gbl_tr1c4.rowHeights = new int[] { 0 };
		gbl_tr1c4.columnWeights = new double[] { 0.0 };
		gbl_tr1c4.rowWeights = new double[] { 0.0 };
		tr1c4.setLayout(gbl_tr1c4);
		
		spinnerTrueAirspeed = new JSpinner();
		spinnerTrueAirspeed.setModel(new SpinnerNumberModel(new Short((short)100), new Short((short)1), new Short((short)999), new Short((short)1)));
		GridBagConstraints gbc_spinnerTrueAirspeed = new GridBagConstraints();
		gbc_spinnerTrueAirspeed.insets = new Insets(0, 5, 0, 5);
		gbc_spinnerTrueAirspeed.gridx = 0;
		gbc_spinnerTrueAirspeed.gridy = 0;
		tr1c4.add(spinnerTrueAirspeed, gbc_spinnerTrueAirspeed);
		
		panelDeparturePoint = new AirportComboBox();
		panelDeparturePoint.setBorder(new TitledBorder(null, "Departure Point", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelDeparturePoint = new GridBagConstraints();
		gbc_panelDeparturePoint.fill = GridBagConstraints.BOTH;
		gbc_panelDeparturePoint.gridx = 4;
		gbc_panelDeparturePoint.gridy = 0;
		tblRow1.add(panelDeparturePoint, gbc_panelDeparturePoint);
		GridBagLayout gbl_panelDeparturePoint = new GridBagLayout();
		gbl_panelDeparturePoint.columnWidths = new int[] { 0 };
		gbl_panelDeparturePoint.rowHeights = new int[] { 0 };
		gbl_panelDeparturePoint.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_panelDeparturePoint.rowWeights = new double[] { Double.MIN_VALUE };
		panelDeparturePoint.setLayout(gbl_panelDeparturePoint);
		
		panelDepartureTime = new DayTimeHrMinPanel();
		panelDepartureTime.setBorder(new TitledBorder(null, "Departure Time", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelDepartureTime = new GridBagConstraints();
		gbc_panelDepartureTime.fill = GridBagConstraints.BOTH;
		gbc_panelDepartureTime.gridx = 5;
		gbc_panelDepartureTime.gridy = 0;
		tblRow1.add(panelDepartureTime, gbc_panelDepartureTime);
		
		JPanel tr1c7 = new JPanel();
		tr1c7.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Cruise", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_tr1c7 = new GridBagConstraints();
		gbc_tr1c7.fill = GridBagConstraints.BOTH;
		gbc_tr1c7.gridx = 6;
		gbc_tr1c7.gridy = 0;
		tblRow1.add(tr1c7, gbc_tr1c7);
		GridBagLayout gbl_tr1c7 = new GridBagLayout();
		gbl_tr1c7.columnWidths = new int[] { 0 };
		gbl_tr1c7.rowHeights = new int[] { 0 };
		gbl_tr1c7.columnWeights = new double[] { 0.0 };
		gbl_tr1c7.rowWeights = new double[] { 0.0 };
		tr1c7.setLayout(gbl_tr1c7);
		
		spinnerCruisingAltitude = new JSpinner();
		spinnerCruisingAltitude.setModel(new SpinnerNumberModel(new Short((short)20), new Short((short)1), new Short((short)999), new Short((short)1)));
		GridBagConstraints gbc_spinnerCruisingAltitude = new GridBagConstraints();
		gbc_spinnerCruisingAltitude.insets = new Insets(0, 5, 0, 5);
		gbc_spinnerCruisingAltitude.gridx = 0;
		gbc_spinnerCruisingAltitude.gridy = 0;
		tr1c7.add(spinnerCruisingAltitude, gbc_spinnerCruisingAltitude);
		
		JPanel tblRow2 = new JPanel();
		tblRow2.setBorder(new TitledBorder(null, "Route of Flight", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelFlightPlan.add(tblRow2);
		GridBagLayout gbl_tblRow2 = new GridBagLayout();
		gbl_tblRow2.columnWidths = new int[] { 0, 0 };
		gbl_tblRow2.rowHeights = new int[] { 0, 0 };
		gbl_tblRow2.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_tblRow2.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		tblRow2.setLayout(gbl_tblRow2);
		
		textAreaRouteOfFlight = new JTextArea();
		textAreaRouteOfFlight.setRows(4);
		textAreaRouteOfFlight.setTabSize(4);
		textAreaRouteOfFlight.setLineWrap(true);
		GridBagConstraints gbc_textAreaRouteOfFlight = new GridBagConstraints();
		gbc_textAreaRouteOfFlight.fill = GridBagConstraints.BOTH;
		gbc_textAreaRouteOfFlight.gridx = 0;
		gbc_textAreaRouteOfFlight.gridy = 0;
		tblRow2.add(textAreaRouteOfFlight, gbc_textAreaRouteOfFlight);
		
		JPanel tblRow3 = new JPanel();
		panelFlightPlan.add(tblRow3);
		GridBagLayout gbl_tblRow3 = new GridBagLayout();
		gbl_tblRow3.columnWidths = new int[] { 0, 0, 0 };
		gbl_tblRow3.rowHeights = new int[] { 0 };
		gbl_tblRow3.columnWeights = new double[] { 1.0, 1.0, 18.0 };
		gbl_tblRow3.rowWeights = new double[] { 1.0 };
		tblRow3.setLayout(gbl_tblRow3);
		
		panelDestinationPoint = new AirportComboBox();
		panelDestinationPoint.setBorder(new TitledBorder(null, "Destination Point", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelDestinationPoint = new GridBagConstraints();
		gbc_panelDestinationPoint.fill = GridBagConstraints.BOTH;
		gbc_panelDestinationPoint.gridx = 0;
		gbc_panelDestinationPoint.gridy = 0;
		tblRow3.add(panelDestinationPoint, gbc_panelDestinationPoint);
		GridBagLayout gbl_panelDestinationPoint = new GridBagLayout();
		gbl_panelDestinationPoint.columnWidths = new int[] { 0 };
		gbl_panelDestinationPoint.rowHeights = new int[] { 0 };
		gbl_panelDestinationPoint.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_panelDestinationPoint.rowWeights = new double[] { Double.MIN_VALUE };
		panelDestinationPoint.setLayout(gbl_panelDestinationPoint);
		
		panelTimeEnroute = new TimeHrMinPanel();
		panelTimeEnroute.setBorder(new TitledBorder(null, "Time Enroute", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelTimeEnroute = new GridBagConstraints();
		gbc_panelTimeEnroute.fill = GridBagConstraints.BOTH;
		gbc_panelTimeEnroute.gridx = 1;
		gbc_panelTimeEnroute.gridy = 0;
		tblRow3.add(panelTimeEnroute, gbc_panelTimeEnroute);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "Remarks", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 2;
		gbc_panel_4.gridy = 0;
		tblRow3.add(panel_4, gbc_panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));
		
		textAreaRemarks = new JTextArea();
		textAreaRemarks.setRows(4);
		textAreaRemarks.setTabSize(4);
		textAreaRemarks.setLineWrap(true);
		panel_4.add(textAreaRemarks);
		
		JPanel tblRow4 = new JPanel();
		panelFlightPlan.add(tblRow4);
		GridBagLayout gbl_tblRow4 = new GridBagLayout();
		gbl_tblRow4.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_tblRow4.rowHeights = new int[] { 0 };
		gbl_tblRow4.columnWeights = new double[] { 1.0, 1.0, 8.0, 2.0 };
		gbl_tblRow4.rowWeights = new double[] { 1.0 };
		tblRow4.setLayout(gbl_tblRow4);
		
		panelFuelOnBoard = new TimeHrMinPanel();
		panelFuelOnBoard.setBorder(new TitledBorder(null, "Fuel on Board", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelFuelOnBoard = new GridBagConstraints();
		gbc_panelFuelOnBoard.fill = GridBagConstraints.BOTH;
		gbc_panelFuelOnBoard.gridx = 0;
		gbc_panelFuelOnBoard.gridy = 0;
		tblRow4.add(panelFuelOnBoard, gbc_panelFuelOnBoard);
		
		panelAlternatePoint = new AirportComboBox();
		panelAlternatePoint.setBorder(new TitledBorder(null, "Alternate Airport", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelAlternatePoint = new GridBagConstraints();
		gbc_panelAlternatePoint.fill = GridBagConstraints.BOTH;
		gbc_panelAlternatePoint.gridx = 1;
		gbc_panelAlternatePoint.gridy = 0;
		tblRow4.add(panelAlternatePoint, gbc_panelAlternatePoint);
		GridBagLayout gbl_panelAlternatePoint = new GridBagLayout();
		gbl_panelAlternatePoint.columnWidths = new int[] { 0 };
		gbl_panelAlternatePoint.rowHeights = new int[] { 0 };
		gbl_panelAlternatePoint.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_panelAlternatePoint.rowWeights = new double[] { Double.MIN_VALUE };
		panelAlternatePoint.setLayout(gbl_panelAlternatePoint);
		
		JPanel panel_9 = new JPanel();
		GridBagConstraints gbc_panel_9 = new GridBagConstraints();
		gbc_panel_9.fill = GridBagConstraints.BOTH;
		gbc_panel_9.gridx = 2;
		gbc_panel_9.gridy = 0;
		tblRow4.add(panel_9, gbc_panel_9);
		GridBagLayout gbl_panel_9 = new GridBagLayout();
		gbl_panel_9.columnWidths = new int[] { 0, 0 };
		gbl_panel_9.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_9.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_9.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		panel_9.setLayout(gbl_panel_9);
		
		JPanel panel_11 = new JPanel();
		panel_11.setBorder(new TitledBorder(null, "Pilot's Name, Address, Telephone, Aircraft Home Base", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_11 = new GridBagConstraints();
		gbc_panel_11.fill = GridBagConstraints.BOTH;
		gbc_panel_11.gridx = 0;
		gbc_panel_11.gridy = 0;
		panel_9.add(panel_11, gbc_panel_11);
		panel_11.setLayout(new BoxLayout(panel_11, BoxLayout.X_AXIS));
		
		textPilotInfo = new JTextField();
		panel_11.add(textPilotInfo);
		textPilotInfo.setColumns(10);
		
		JPanel panel_12 = new JPanel();
		panel_12.setBorder(new TitledBorder(null, "Destination Contact / Telephone", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_12 = new GridBagConstraints();
		gbc_panel_12.fill = GridBagConstraints.BOTH;
		gbc_panel_12.gridx = 0;
		gbc_panel_12.gridy = 1;
		panel_9.add(panel_12, gbc_panel_12);
		panel_12.setLayout(new BoxLayout(panel_12, BoxLayout.X_AXIS));
		
		textDestinationContact = new JTextField();
		panel_12.add(textDestinationContact);
		textDestinationContact.setColumns(10);
		
		JPanel panel_10 = new JPanel();
		panel_10.setBorder(new TitledBorder(null, "Number Aboard", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_10 = new GridBagConstraints();
		gbc_panel_10.fill = GridBagConstraints.BOTH;
		gbc_panel_10.gridx = 3;
		gbc_panel_10.gridy = 0;
		tblRow4.add(panel_10, gbc_panel_10);
		GridBagLayout gbl_panel_10 = new GridBagLayout();
		gbl_panel_10.columnWidths = new int[] { 0 };
		gbl_panel_10.rowHeights = new int[] { 0 };
		gbl_panel_10.columnWeights = new double[] { 0.0 };
		gbl_panel_10.rowWeights = new double[] { 0.0 };
		panel_10.setLayout(gbl_panel_10);
		
		spinnerNumberAboard = new JSpinner();
		spinnerNumberAboard.setModel(new SpinnerNumberModel(new Short((short)1), new Short((short)1), new Short((short)999), new Short((short)1)));
		GridBagConstraints gbc_spinnerNumberAboard = new GridBagConstraints();
		gbc_spinnerNumberAboard.gridx = 0;
		gbc_spinnerNumberAboard.gridy = 0;
		panel_10.add(spinnerNumberAboard, gbc_spinnerNumberAboard);
		
		JPanel tblRow5 = new JPanel();
		panelFlightPlan.add(tblRow5);
		GridBagLayout gbl_tblRow5 = new GridBagLayout();
		gbl_tblRow5.columnWidths = new int[] { 0, 0 };
		gbl_tblRow5.rowHeights = new int[] { 0 };
		gbl_tblRow5.columnWeights = new double[] { 1.0, 6.0 };
		gbl_tblRow5.rowWeights = new double[] { 1.0 };
		tblRow5.setLayout(gbl_tblRow5);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Color Of Aircraft", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		tblRow5.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0 };
		gbl_panel.rowHeights = new int[] { 0 };
		gbl_panel.columnWeights = new double[] { 1.0 };
		gbl_panel.rowWeights = new double[] { 0.0 };
		panel.setLayout(gbl_panel);
		
		textColorOfAircraft = new JTextField();
		GridBagConstraints gbc_textColorOfAircraft = new GridBagConstraints();
		gbc_textColorOfAircraft.fill = GridBagConstraints.HORIZONTAL;
		gbc_textColorOfAircraft.gridx = 0;
		gbc_textColorOfAircraft.gridy = 0;
		panel.add(textColorOfAircraft, gbc_textColorOfAircraft);
		textColorOfAircraft.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 0;
		tblRow5.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JTextArea txtrCivilAircraftPilots = new JTextArea();
		txtrCivilAircraftPilots.setBackground(SystemColor.menu);
		txtrCivilAircraftPilots.setWrapStyleWord(true);
		txtrCivilAircraftPilots.setLineWrap(true);
		txtrCivilAircraftPilots.setFont(new Font("Monospaced", Font.PLAIN, 10));
		txtrCivilAircraftPilots.setText(
				"CIVIL AIRCRAFT PILOTS. FAR Part 91 requires you to file an IFR flight plan to operate under Instrument Flight Rules in controlled airspace. Failure to file  coould result in a civil penalty not to exceed $1,000 for each violation (Section 901 of the Federal Aviation Act of 1958, as amended). Filing of a VFR flight plan is recommended as a good operating practice. See also Part 99 for requirements concerning DVFR flight plans.");
		txtrCivilAircraftPilots.setRows(4);
		txtrCivilAircraftPilots.setEditable(false);
		panel_1.add(txtrCivilAircraftPilots);
		
		// apply other items
		applyGroups();
	}
	
	private void applyGroups()
	{
		ButtonGroup flightTypeGroup = new ButtonGroup();
		flightTypeGroup.add(rdbtnVfr);
		flightTypeGroup.add(rdbtnIfr);
	}
	
	protected Service.Model.FlightPlan getBaseFlightPlan()
	{
		Service.Model.FlightPlan plan = new Service.Model.FlightPlan();
		plan.FlightTypeIdent = (byte)(rdbtnIfr.isSelected() ? 1 : 0);
		plan.AircraftIdentification = textIdentification.getText();
		plan.AircraftType = textAircraftType.getText();
		plan.TrueAirspeed = (short)spinnerTrueAirspeed.getValue();
		plan.DepartureAirportId = panelDeparturePoint.getAirportId();
		
		// Departure check, if before today, make it next month
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime takeOff = LocalDateTime.of(now.getYear(), now.getMonth(), panelDepartureTime.getDay(), panelDepartureTime.getHours(), panelDepartureTime.getMin(), 0);
		if (takeOff.compareTo(now) < 0)
			takeOff = takeOff.plusMonths(1);
		plan.DepartureTimeUTC = Timestamp.valueOf(takeOff);
		
		plan.CruisingAltitude = (short)spinnerCruisingAltitude.getValue();
		plan.RouteOfFlight = textAreaRouteOfFlight.getText();
		plan.DestinationAirportId = panelDestinationPoint.getAirportId();
		plan.EstTimeEnrouteHours = panelTimeEnroute.getHours();
		plan.EstTimeEnrouteMinutes = panelTimeEnroute.getMin();
		plan.Remarks = textAreaRemarks.getText();
		plan.FuelOnBoardHours = panelFuelOnBoard.getHours();
		plan.FuelOnBoardMinutes = panelFuelOnBoard.getMin();
		plan.AlternateAirportId = panelAlternatePoint.getAirportId();
		plan.PilotInfo = textPilotInfo.getText();
		plan.DestinationContactInfo = textDestinationContact.getText();
		plan.NumberAboard = (short)spinnerNumberAboard.getValue();
		plan.ColorOfAircraft = textColorOfAircraft.getText();
		
		return plan;
	}
	
	protected void setBaseFlightPlan(Service.Model.FlightPlan plan)
	{
		rdbtnIfr.setSelected(plan.FlightTypeIdent == 1);
		textIdentification.setText(plan.AircraftIdentification);
		textAircraftType.setText(plan.AircraftType);
		spinnerTrueAirspeed.setValue(plan.TrueAirspeed);
		panelDeparturePoint.setAirportId(plan.DepartureAirportId);
		
		// departure time
		LocalDateTime departure = plan.DepartureTimeUTC.toLocalDateTime();
		panelDepartureTime.setDay(departure.getDayOfMonth());
		panelDepartureTime.setHours((byte)departure.getHour());
		panelDepartureTime.setMin((byte)departure.getMinute());
		
		spinnerCruisingAltitude.setValue(plan.CruisingAltitude);
		textAreaRouteOfFlight.setText(plan.RouteOfFlight);
		panelDestinationPoint.setAirportId(plan.DestinationAirportId);
		panelTimeEnroute.setHours(plan.EstTimeEnrouteHours);
		panelTimeEnroute.setMin(plan.EstTimeEnrouteMinutes);
		textAreaRemarks.setText(plan.Remarks);
		panelFuelOnBoard.setHours((byte)plan.FuelOnBoardHours);
		panelFuelOnBoard.setMin((byte)plan.FuelOnBoardMinutes);
		panelAlternatePoint.setAirportId(plan.AlternateAirportId);
		textPilotInfo.setText(plan.PilotInfo);
		textDestinationContact.setText(plan.DestinationContactInfo);
		spinnerNumberAboard.setValue(plan.NumberAboard);
		textColorOfAircraft.setText(plan.ColorOfAircraft);
	}
}
