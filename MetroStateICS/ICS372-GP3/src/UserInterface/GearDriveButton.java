package UserInterface;

import Device.VehicleDisplay;
import Event.GearDriveEvent;
import Manager.ManagerRepository;

/**
 * Represents the gear drive button
 * 
 * @author Ilnaz D
 * @version 1.0
 */
public class GearDriveButton extends GUIButton {
	private static final long serialVersionUID = 2539228616380198703L;
	
	/**
	 * Creates the button with the required label
	 *
	 * @param string
	 *            the label
	 */
	public GearDriveButton(String string) {
		super(string);
	}
	
	/**
	 * Tell the manager to send it to the right listeners
	 */
	@Override
	public void inform(VehicleDisplay source) {
		ManagerRepository.gearDriveManager.raiseEvent(new GearDriveEvent(source));
	}
}
