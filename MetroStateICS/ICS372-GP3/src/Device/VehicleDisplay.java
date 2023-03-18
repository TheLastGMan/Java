package Device;

/**
 * Specifies what the display system should do.
 */
public interface VehicleDisplay {
	/**
	 * Indicate that the ignition switch is on
	 */
	void turnIgnitionSwitchOn();
	
	/**
	 * Indicate that the ignition switch off
	 */
	void turnIgnitionSwitchOff();
	
	/**
	 * Indicate that the gear is in parked position
	 */
	void gearPositionPark();
	
	/**
	 * Indicate that the gear is in drive position
	 */
	void gearPositionDrive();
	
	/**
	 * Indicate that vehicle is accelerating
	 */
	void accelerate();
	
	/**
	 * Indicate that vehicle is braking
	 */
	void brake();
	
	/**
	 * display the speed of vehicle
	 *
	 * @param speed
	 *            the current speed
	 */
	void displaySpeed(int speed);
	
	/**
	 * get the current speed of the vehicle
	 *
	 * @return current speed of vehicle
	 */
	int getSpeed();
	
	/**
	 * update the title of the display
	 *
	 * @param value
	 *            Title
	 */
	void setMovement(String value);
}
