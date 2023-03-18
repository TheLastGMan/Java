package Manager;

import Event.*;
import Listener.*;

/**
 * Event Listener Manager Repository
 * Used as a separation of concern layer so each of us could code
 * without needing each others classes to do our part
 *
 * @author Ryan Gau, Tenzin
 * @version 1.0
 */
public class ManagerRepository {
	//@formatter:off
	/** Ignition On Listener Manager */
	public static final BaseListener<IgnitionOnListener, IgnitionOnEvent> ignitionOnManager = IgnitionOnManager.instance();

	/** Ignition Off Listener Manager */
	public static final BaseListener<IgnitionOffListener, IgnitionOffEvent> ignitionOffManager = IgnitionOffManager.instance();

	/** Gear Park Listener Manager */
	public static final BaseListener<GearParkListener, GearParkEvent> gearParkManager = GearParkManager.instance();

	/** Gear Drive Listener Manager */
	public static final BaseListener<GearDriveListener, GearDriveEvent> gearDriveManager = GearDriveManager.instance();

	/** Accelerate Pedal Listener Manager */
	public static final BaseListener<PedalAccelerateListener, PedalAccelerateEvent> pedalAccelerateManager = PedalAccelerateManager.instance();

	/** Brake Pedal Listener Manager */
	public static final BaseListener<PedalBrakeListener, PedalBrakeEvent> pedalBrakeManager = PedalBrakeManager.instance();

	/** Minimum Speed Reached Listener Manager */
	public static final BaseListener<SpeedMinListener, SpeedMinEvent> speedMinManager = SpeedMinManager.instance();

	/** Maximum Speed Reached Listener Manager */
	public static final BaseListener<SpeedMaxListener, SpeedMaxEvent> speedMaxManager = SpeedMaxManager.instance();

	/** Timer Elapsed Listener Manager */
	public static final BaseListener<TimerElapsedListener, TimerElapsedEvent> timerElapsedManager = TimerElapsedManager.instance();
	//@formatter:on
}
