import Device.DeviceRepository;
import UserInterface.GUIDisplay;

/**
 * Main Entry Point that launches the application
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class Program {
	public static void main(String args[]) {
		try {
			// tell device what display to use and start it
			DeviceRepository.display = new GUIDisplay();
			DeviceRepository.context.start();
		}
		catch (Exception ex) {
			// report error
			System.err.println("Program Error");
			System.err.println(ex.getMessage());
			System.err.println(ex.getStackTrace());
		}
	}
}
