package UserInterface;

import Device.VehicleDisplay;
import Event.IgnitionOnEvent;
import Manager.ManagerRepository;

/**
 * Represents the ignition on button
 * 
 * @author Ilnaz D
 * @version 1.0
 */
public class IgnitionOnButton extends GUIButton {
	private static final long serialVersionUID = -3064738522636039596L;
	
	/**
	 * Creates the button with the required label
	 *
	 * @param string
	 *            the label
	 */
	public IgnitionOnButton(String string) {
		super(string);
	}
	
	/**
	 * Tell the manager to send it to the right listeners
	 */
	@Override
	public void inform(VehicleDisplay source) {
		ManagerRepository.ignitionOnManager.raiseEvent(new IgnitionOnEvent(source));
	}
}
