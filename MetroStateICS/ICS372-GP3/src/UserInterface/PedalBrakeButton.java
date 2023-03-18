package UserInterface;

import Device.VehicleDisplay;
import Event.PedalBrakeEvent;
import Manager.ManagerRepository;

/**
 * Represents the pedal brake button
 * 
 * @author Ilnaz D
 * @version 1.0
 */
public class PedalBrakeButton extends GUIButton {
	private static final long serialVersionUID = 5501790962741164911L;
	
	/**
	 * Creates the button with the required label
	 *
	 * @param string
	 *            the label
	 */
	public PedalBrakeButton(String string) {
		super(string);
	}
	
	/**
	 * Tell the manager to send it to the right listeners
	 */
	@Override
	public void inform(VehicleDisplay source) {
		ManagerRepository.pedalBrakeManager.raiseEvent(new PedalBrakeEvent(source));
	}
}
