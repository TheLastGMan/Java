
package UserInterface;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import Device.VehicleDisplay;
import Event.*;
import Manager.ManagerRepository;

/**
 * GUI to implement the VehicleDisplay interface
 *
 * @author Ilnaz D, Anthony Freitag, Ryan Gau
 * @version 1.0
 */
public class GUIDisplay extends JFrame implements VehicleDisplay, Observer, ActionListener {
	private static final long serialVersionUID = -7499608579323123753L;
	
	// Model
	private GUIModel model = new GUIModel();
	
	// Common data
	private final Color colorOn = Color.GREEN;
	private final Color colorOff = Color.RED;
	private final int minimumSpeed = 0;
	private final int maximumSpeed = 50;
	
	/**
	 * Makes it a singleton
	 */
	public GUIDisplay() {
		// add model listener
		model.addObserver(this);
		
		// setup display
		initialize();
		setTitle("Vehicle HUD");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 300);
		pack();
		setVisible(true);
	}
	
	// Buttons
	private IgnitionOnButton ignitionOnButton = new IgnitionOnButton("Ignition ON");
	private IgnitionOffButton ignitionOffButton = new IgnitionOffButton("Ignition OFF");
	private GearParkButton parkButton = new GearParkButton("Park");
	private GearDriveButton driveButton = new GearDriveButton("Drive");
	private PedalAccelerateButton accelerateButton = new PedalAccelerateButton("Accelerate");
	private PedalBrakeButton brakeButton = new PedalBrakeButton("Brake");
	
	// Labels
	private JLabel titleLabel = new JLabel("Vehicle State: LOADING...", JLabel.CENTER);
	private JLabel ignitionSwitchLabel = new JLabel("Ignition Switch", JLabel.CENTER);
	private JLabel gearPositionLabel = new JLabel("Gear Position", JLabel.CENTER);
	private JLabel pedalsLabel = new JLabel("Pedals", JLabel.CENTER);
	private JLabel onOrOffStatusLabel = new JLabel("Vehicle is Off", JLabel.CENTER);
	private JLabel parkOrDriveStatusLabel = new JLabel("Vehicle is in Park", JLabel.CENTER);
	private JLabel accelerateOrBrakeStatusLabel = new JLabel("Vehicle is Braking", JLabel.CENTER);
	private JLabel speedLabel = new JLabel("Speed: 0 MPH", JLabel.CENTER);
	
	// Color indicators
	private JLabel colorIndicatorIgnitionLabel = new JLabel("•", JLabel.CENTER);
	private JLabel colorIndicatorGearLabel = new JLabel("•", JLabel.CENTER);
	private JLabel colorIndicatorPedalsLabel = new JLabel("•", JLabel.CENTER);
	
	// Speed indicator bar
	private JProgressBar progressBar = new JProgressBar(minimumSpeed, maximumSpeed);
	
	/**
	 * Sets up the contents of the frame
	 */
	private void initialize() {
		/* MAIN PANEL - holds ignitionPanel, gearPanel, and pedalPanel */
		JPanel mainPanel = new JPanel(new GridLayout(1, 3));
		mainPanel.setBackground(Color.white);
		add(mainPanel);
		
		/* IGNITION PANEL */
		JPanel ignitionPanel = new JPanel(new GridLayout(7, 1));
		ignitionPanel.setBackground(Color.white);
		ignitionPanel.add(new JLabel());
		ignitionPanel.add(new JLabel());
		ignitionPanel.add(new JLabel());
		ignitionPanel.add(ignitionSwitchLabel);
		ignitionPanel.add(colorIndicatorIgnitionLabel);
		ignitionPanel.add(onOrOffStatusLabel);
		colorIndicatorIgnitionLabel.setFont(colorIndicatorIgnitionLabel.getFont().deriveFont(48.0f));
		colorIndicatorIgnitionLabel.setForeground(Color.RED);
		JPanel ignitionButtonsPanel = new JPanel(new FlowLayout());
		ignitionButtonsPanel.setBackground(Color.white);
		ignitionOnButton.addActionListener(GUIDisplay.this);
		ignitionOffButton.addActionListener(GUIDisplay.this);
		ignitionButtonsPanel.add(ignitionOffButton);
		ignitionButtonsPanel.add(ignitionOnButton);
		ignitionPanel.add(ignitionButtonsPanel);
		mainPanel.add(ignitionPanel);
		
		/* GEAR PANEL */
		JPanel gearPanel = new JPanel(new GridLayout(7, 1));
		gearPanel.setBackground(Color.white);
		gearPanel.add(titleLabel);
		gearPanel.add(speedLabel);
		gearPanel.add(progressBar);
		gearPanel.add(gearPositionLabel);
		gearPanel.add(colorIndicatorGearLabel);
		gearPanel.add(parkOrDriveStatusLabel);
		colorIndicatorGearLabel.setFont(colorIndicatorGearLabel.getFont().deriveFont(48.0f));
		colorIndicatorGearLabel.setForeground(Color.RED);
		JPanel gearButtonsPanel = new JPanel(new FlowLayout());
		gearButtonsPanel.setBackground(Color.white);
		parkButton.addActionListener(GUIDisplay.this);
		driveButton.addActionListener(GUIDisplay.this);
		gearButtonsPanel.add(parkButton);
		gearButtonsPanel.add(driveButton);
		gearPanel.add(gearButtonsPanel);
		mainPanel.add(gearPanel);
		
		/* PEDALS PANEL */
		JPanel pedalsPanel = new JPanel(new GridLayout(7, 1));
		pedalsPanel.setBackground(Color.white);
		pedalsPanel.add(new JLabel());
		pedalsPanel.add(new JLabel());
		pedalsPanel.add(new JLabel());
		pedalsPanel.add(pedalsLabel);
		pedalsPanel.add(colorIndicatorPedalsLabel);
		pedalsPanel.add(accelerateOrBrakeStatusLabel);
		colorIndicatorPedalsLabel.setFont(colorIndicatorPedalsLabel.getFont().deriveFont(48.0f));
		colorIndicatorPedalsLabel.setForeground(Color.RED);
		JPanel pedalsButtonsPanel = new JPanel(new FlowLayout());
		pedalsButtonsPanel.setBackground(Color.white);
		accelerateButton.addActionListener(GUIDisplay.this);
		brakeButton.addActionListener(GUIDisplay.this);
		pedalsButtonsPanel.add(brakeButton);
		pedalsButtonsPanel.add(accelerateButton);
		pedalsPanel.add(pedalsButtonsPanel);
		mainPanel.add(pedalsPanel);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// arg is an object of any type being observed
		// check instance backing
		if (arg instanceof GUIModel) {
			// update GUI
			model = (GUIModel)arg;
			titleLabel.setText(model.getTitleLabel());
			onOrOffStatusLabel.setText(model.getIgnitionLabel());
			colorIndicatorIgnitionLabel.setForeground(model.getIgnitionIndicator());
			parkOrDriveStatusLabel.setText(model.getGearLabel());
			colorIndicatorGearLabel.setForeground(model.getGearIndicator());
			accelerateOrBrakeStatusLabel.setText(model.getPedalLabel());
			colorIndicatorPedalsLabel.setForeground(model.getPedalIndicator());
			speedLabel.setText(model.getSpeedLabel());
			progressBar.setValue(model.getSpeed());
		}
	}
	
	/**
	 * Handles the clicks
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		// validate event is instance of GUIButton before direct casting event
		if (event.getSource() instanceof GUIButton) {
			((GUIButton)event.getSource()).inform(this);
		}
	}
	
	/**
	 * Indicate that the ignition switch is on
	 */
	@Override
	public void turnIgnitionSwitchOn() {
		model.setIgnitionIndicator(colorOn);
		model.setIgnitionLabel("Vehicle is ON");
	}
	
	/**
	 * Indicate that the ignition switch off
	 */
	@Override
	public void turnIgnitionSwitchOff() {
		model.setIgnitionIndicator(colorOff);
		model.setIgnitionLabel("Vehicle is OFF");
	}
	
	/**
	 * Indicate that the gear is in parked position
	 */
	@Override
	public void gearPositionPark() {
		model.setGearIndicator(colorOff);
		model.setGearLabel("Vehicle is in Park");
	}
	
	/**
	 * Indicate that the gear is in drive position
	 */
	@Override
	public void gearPositionDrive() {
		model.setGearIndicator(colorOn);
		model.setGearLabel("Vehicle is in Drive");
	}
	
	/**
	 * Indicate that vehicle is accelerating
	 */
	@Override
	public void accelerate() {
		model.setPedalIndicator(colorOn);
		model.setPedalLabel("Vehicle is Accelerating");
	}
	
	/**
	 * Indicate that vehicle is braking
	 */
	@Override
	public void brake() {
		model.setPedalIndicator(colorOff);
		model.setPedalLabel("Vehicle is Braking");
	}
	
	/**
	 * display the speed of vehicle
	 *
	 * @param speed
	 *            the current speed
	 */
	@Override
	public void displaySpeed(int speed) {
		// validate range
		if (speed >= maximumSpeed) {
			// maximum speed, set upper bound
			// and raise event for maximum reached speed
			speed = maximumSpeed;
			ManagerRepository.speedMaxManager.raiseEvent(new SpeedMaxEvent(this));
		}
		else if (speed <= minimumSpeed) {
			// minimum speed, set lower bound
			// and raise event for minimum reached speed
			speed = minimumSpeed;
			ManagerRepository.speedMinManager.raiseEvent(new SpeedMinEvent(this));
		}
		
		// update display
		model.setSpeedLabel("Speed: " + speed + " MPH");
		model.setSpeed(speed);
	}
	
	/**
	 * get the current speed of the vehicle
	 *
	 * @return current speed of vehicle
	 */
	@Override
	public int getSpeed() {
		return model.getSpeed();
	}
	
	/**
	 * update the title of the display
	 *
	 * @param value
	 *            Title
	 */
	@Override
	public void setMovement(String value) {
		model.setTitleLabel("Vehicle State: " + value);
	}
}
