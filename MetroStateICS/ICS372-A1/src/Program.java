import System.Linq.QList;

/**
 * A driver that does the following:
 * > It creates 10 different Latitude objects and 10 different Longitude objects. Half of the latitudes must be north of
 * the equator and the other half must be south of the equator. Half of the longitudes must be west of the Prime
 * Meridian and the other half must be east of the prime meridian.
 * > It creates 10 weather records for 10 locations on earth. All of the latitudes and longitudes created above must be
 * used in one of the records.
 * > The maximum temperature must be more than the minimum temperature at every location.
 * Approximately half of the minimum temperatures must be negative.
 * > It prints the string representation of all weather records. (See the sample output below.)
 * > It changes the maximum temperature and minimum temperature of one of the locations and prints the new information.
 *
 * @author Ryan Gau
 */
public class Program
{
	/**
	 * Main entry point
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String args[])
	{
		// generate weather reports
		// QList => http://apps.rpgcor.com/java
		//@formatter:off
		QList<WeatherInformation> reports =
				QList.Range(1, 10)
				.Select(ix -> generateRandomWeather((ix % 2) == 1));
		//@formatter:on

		// write output to the console
		reports.ForEach(w -> System.out.println(w.toString()));

		// change one of the weather information min/max temp
		// in this case, let's just use the first one
		WeatherInformation wi = reports.FirstOrDefault();
		wi.setMinTemperature(Math.random() * -100);
		wi.setMaxTemperature(Math.random() * 100);

		// add divider line in console
		System.out.println("--------------------------------------------------");

		// write output to the console again
		reports.ForEach(w ->
			{
				System.out.println(w.toString());
			});
	}
	
	/**
	 * Generate a random weather report
	 *
	 * @param useOddLogic
	 *            use odd logic state in randomization
	 * @return Weather Information
	 */
	private static WeatherInformation generateRandomWeather(boolean useOddLogic)
	{
		// Latitude
		short latDeg = (short)(Math.random() * 90);
		short latMin = (short)(Math.random() * 60);
		Latitude lat = new Latitude(latDeg, latMin, (useOddLogic ? "N" : "S"));

		// Longitude
		short lonDeg = (short)(Math.random() * 180);
		short lonMin = (short)(Math.random() * 60);
		Longitude lon = new Longitude(lonDeg, lonMin, (useOddLogic ? "E" : "W"));
		
		// Temperatures
		double tempMin = Math.random() * 100 * (useOddLogic ? -1 : 1); // ([-100,0]|[0,100])
		double tempMax = Math.random() * 100 + 101; // [0, 100] => [101, 201]

		// lat, lon, min, max
		WeatherInformation wi = new WeatherInformation(lat, lon, tempMin, tempMax);
		return wi;
	}
}
