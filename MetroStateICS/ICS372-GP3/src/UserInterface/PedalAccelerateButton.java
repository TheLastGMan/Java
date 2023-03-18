package UserInterface;

import Device.VehicleDisplay;
import Event.PedalAccelerateEvent;
import Manager.ManagerRepository;

/**
 * Represents the pedal accelerate button
 * 
 * @author Ilnaz D
 * @version 1.0
 */
public class PedalAccelerateButton extends GUIButton {
	private static final long serialVersionUID = 3663012348625291311L;
	
	/**
	 * Creates the button with the required label
	 *
	 * @param string
	 *            the label
	 */
	public PedalAccelerateButton(String string) {
		super(string);
	}
	
	/**
	 * Tell the manager to send it to the right listeners
	 */
	@Override
	public void inform(VehicleDisplay source) {
		ManagerRepository.pedalAccelerateManager.raiseEvent(new PedalAccelerateEvent(source));
	}
}
