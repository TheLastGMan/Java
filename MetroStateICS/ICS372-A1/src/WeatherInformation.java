/**
 * class named WeatherInformation to store the maximum and minimum temperature of a point on the earth. It should have
 * attributes to store the latitude, longitude, minimum temperature ever recorded, and the maximum temperature ever
 * recorded at that position.
 * Weather Record of Min/Max temps
 *
 * @author Ryan Gau
 */
public class WeatherInformation implements WeatherRecord
{
	private double maxTemperature;
	private double minTemperature;
	private Latitude latitude;
	private Longitude longitude;

	/**
	 * Create new instance of MyWeatherRecord with specified temperatures
	 *
	 * @param latitude
	 *            Latitude
	 * @param longitude
	 *            Longitude
	 * @param minTemperature
	 *            Minimum temperature
	 * @param maxTemperature
	 *            Maximum temperature
	 */
	WeatherInformation(Latitude latitude, Longitude longitude, double minTemperature, double maxTemperature)
	{
		double min = Math.min(minTemperature, maxTemperature);
		double max = Math.max(minTemperature, maxTemperature);
		setMinTemperature(min);
		setMaxTemperature(max);
		
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * Get Max Temperature
	 *
	 * @return temperature
	 */
	public double getMaxTemperature()
	{
		return maxTemperature;
	}

	/**
	 * Set Max Temperature
	 */
	@Override
	public void setMaxTemperature(double temperature)
	{
		maxTemperature = validateTemperature(temperature);
	}

	/**
	 * Get Min Temperature
	 *
	 * @return temperature
	 */
	public double getMinTemperature()
	{
		return minTemperature;
	}

	/**
	 * Set Min Temperature
	 */
	@Override
	public void setMinTemperature(double temperature)
	{
		minTemperature = validateTemperature(temperature);
	}
	
	private double validateTemperature(double temp)
	{
		if (temp < -500)
			return -500;
		else if (temp > 5000)
			return 5000;
		else
			return temp;
	}

	/**
	 * Get Latitude
	 *
	 * @return latitude
	 */
	public Latitude getLatitude()
	{
		return latitude;
	}

	/**
	 * Get Longitude
	 *
	 * @return longitude
	 */
	public Longitude getLongitude()
	{
		return longitude;
	}

	/**
	 * Weather Information in an easy to read line of text
	 * WeatherInformation [latitude=Latitude: [degree=10, minute=0 NorthOrSouth=N],
	 * longitude=Longitude: [degree=10, minute=0 eastOrWest=E],
	 * maxTemperature=72.16207711612515, minTemperature=30.50698550659581]
	 *
	 * @return weather info
	 */
	@Override
	public String toString()
	{
		String result = "WeatherInformation [" + getLatitude().toString() + ", " + getLongitude().toString() + ", MinTemperature=" + round(getMinTemperature())
				+ ", MaxTemperature=" + round(getMaxTemperature()) + "]";
		return result;
	}

	private double round(double value)
	{
		return Math.round(value * 100) / 100.0;
	}
}
