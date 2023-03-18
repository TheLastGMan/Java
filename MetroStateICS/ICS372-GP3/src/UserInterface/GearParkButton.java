package UserInterface;

import Device.VehicleDisplay;
import Event.GearParkEvent;
import Manager.ManagerRepository;

/**
 * Represents the gear park button
 * 
 * @author Ilnaz D
 * @version 1.0
 */
public class GearParkButton extends GUIButton {
	private static final long serialVersionUID = -8239987900757953950L;
	
	/**
	 * Creates the button with the required label
	 *
	 * @param string
	 *            the label
	 */
	public GearParkButton(String string) {
		super(string);
	}
	
	/**
	 * Tell the manager to send it to the right listeners
	 */
	@Override
	public void inform(VehicleDisplay source) {
		ManagerRepository.gearParkManager.raiseEvent(new GearParkEvent(source));
	}
}
