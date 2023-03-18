package UserInterface;

import Device.VehicleDisplay;
import Event.IgnitionOffEvent;
import Manager.ManagerRepository;

/**
 * Represents the ignition off button
 * 
 * @author Ilnaz D
 * @version 1.0
 */
public class IgnitionOffButton extends GUIButton {
	private static final long serialVersionUID = 1874959967434170315L;
	
	/**
	 * Creates the button with the required label
	 *
	 * @param string
	 *            the label
	 */
	public IgnitionOffButton(String string) {
		super(string);
	}
	
	/**
	 * Tell the manager to send it to the right listeners
	 */
	@Override
	public void inform(VehicleDisplay source) {
		ManagerRepository.ignitionOffManager.raiseEvent(new IgnitionOffEvent(source));
	}
}
