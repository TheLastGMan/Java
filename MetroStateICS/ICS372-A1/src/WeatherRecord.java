/**
 * The class WeatherInformation must implement the following interface.
 * Weather Record Interface to set min and max temps
 *
 * @author Ryan Gau
 */
public interface WeatherRecord
{
	/**
	 * Sets the maximum temperature
	 *
	 * @param temperature
	 *            the new maximum temperature
	 */
	void setMaxTemperature(double temperature);

	/**
	 * Sets the minimum temperature
	 *
	 * @param temperature
	 *            the new minimum temperature
	 */
	void setMinTemperature(double temperature);
}
