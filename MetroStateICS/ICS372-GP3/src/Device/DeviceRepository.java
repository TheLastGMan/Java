package Device;

/**
 * Common repository for device
 * Used as a separation of concern layer so each of us could code
 * without needing each others classes to do our part
 *
 * @author Ryan Gau, Anthony Freitag
 * @version 1.0
 */
public class DeviceRepository {
	/** Main Device Context - set by program */
	public static final Context context = DeviceContext.instance();
	
	/** Device Display, set by Program */
	public static VehicleDisplay display = null;
}
