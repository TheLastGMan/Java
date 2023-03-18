package UserInterface;

import javax.swing.JButton;

import Device.VehicleDisplay;

/**
 * The abstract GUI JButton object. Helps to get rid of conditionals
 *
 * @author Ilnaz D
 * @version 1.0
 */
public abstract class GUIButton extends JButton {
	private static final long serialVersionUID = 3788649459253783202L;
	
	/**
	 * Create the button with the proper text
	 *
	 * @param string
	 *            the text
	 */
	public GUIButton(String string) {
		super(string);
	}
	
	/**
	 * Tell the context that the button has been clicked.
	 *
	 * @param context
	 *            the Vehicle context
	 * @param display
	 *            the GUI
	 */
	public abstract void inform(VehicleDisplay display);
}
