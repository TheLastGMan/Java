package UserInterface;

import java.awt.Color;
import java.util.Observable;

/**
 * MVC Style model, last minute idea, because, why not?
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class GUIModel extends Observable {
	
	private String TitleLabel = "";
	private String IgnitionLabel = "";
	private String GearLabel = "";
	private String PedalLabel = "";
	private String SpeedLabel = "";
	private int SpeedValue = 0;
	private Color IgnitionIndicator = Color.RED;
	private Color GearIndicator = Color.RED;
	private Color PedalIndicator = Color.RED;
	
	/** Notify listeners the model has changed */
	private void update() {
		super.setChanged();
		super.notifyObservers(this);
	}
	
	/** Gets the Title Text */
	public String getTitleLabel() {
		return TitleLabel;
	}
	
	/** Sets the Title Text */
	public void setTitleLabel(String value) {
		TitleLabel = value;
		update();
	}
	
	/** Gets the Ignition Text */
	public String getIgnitionLabel() {
		return IgnitionLabel;
	}
	
	/** Sets the Ignition Text */
	public void setIgnitionLabel(String value) {
		IgnitionLabel = value;
		update();
	}
	
	/** Gets the Gear Text */
	public String getGearLabel() {
		return GearLabel;
	}
	
	/** Sets the Gear Text */
	public void setGearLabel(String value) {
		GearLabel = value;
		update();
	}
	
	/** Gets the Pedal Text */
	public String getPedalLabel() {
		return PedalLabel;
	}
	
	/** Sets the Pedal Text */
	public void setPedalLabel(String value) {
		PedalLabel = value;
		update();
	}
	
	/** Gets the Speed Text */
	public String getSpeedLabel() {
		return SpeedLabel;
	}
	
	/** Sets the Speed Text */
	public void setSpeedLabel(String value) {
		SpeedLabel = value;
		update();
	}
	
	/** Gets the Speed Value */
	public int getSpeed() {
		return SpeedValue;
	}
	
	/** Sets the Speed Value */
	public void setSpeed(int value) {
		SpeedValue = value;
		update();
	}
	
	/** Gets the Ignition Indicator Color */
	public Color getIgnitionIndicator() {
		return IgnitionIndicator;
	}
	
	/** Sets the Ignition Indicator Color */
	public void setIgnitionIndicator(Color value) {
		IgnitionIndicator = value;
		update();
	}
	
	/** Gets the Gear Indicator Color */
	public Color getGearIndicator() {
		return GearIndicator;
	}
	
	/** Sets the Gear Indicator Color */
	public void setGearIndicator(Color value) {
		GearIndicator = value;
		update();
	}
	
	/** Gets the Pedal Indicator Color */
	public Color getPedalIndicator() {
		return PedalIndicator;
	}
	
	/** Sets the Pedal Indicator Color */
	public void setPedalIndicator(Color value) {
		PedalIndicator = value;
		update();
	}
}
