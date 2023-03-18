/**
 *
 */

/**
 * Container for station and weather info
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class WeatherStationResult {
	public final StationData Station;
	public final WeatherData Weather;
	
	public WeatherStationResult(StationData station, WeatherData weather) {
		Station = station;
		Weather = weather;
	}
}
