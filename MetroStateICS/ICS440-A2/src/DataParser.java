import java.util.*;

/**
 * Collection of, slightly modified, Instructor provided implementation
 *
 * @author Unknown, Ryan Gau
 * @version 1.0
 */
public class DataParser {
	/***
	 * Parse a NOAA station data string to a class structure
	 *
	 * @param thisLine
	 *            line to parse
	 * @return StationData
	 */
	public static StationData parseStationData(String thisLine) {
		StationData sd = new StationData();
		sd.id = thisLine.substring(0, 11);
		sd.latitude = Float.valueOf(thisLine.substring(12, 20).trim());
		sd.longitude = Float.valueOf(thisLine.substring(21, 30).trim());
		sd.elevation = Float.valueOf(thisLine.substring(31, 37).trim());
		sd.state = thisLine.substring(38, 40);
		sd.name = thisLine.substring(41, 71);
		return sd;
	}

	/***
	 * Parse a NOAA weather data string to a collection of it's data
	 *
	 * @param thisLine
	 *            line to parse
	 * @return Collection(Of WeatherData)
	 */
	public static List<WeatherData> parseWeatherData(String thisLine, String stationId, InputArguments filterInfo) {
		// basic information
		String id = thisLine.substring(0, 11);
		int year = Integer.valueOf(thisLine.substring(11, 15).trim());
		int month = Integer.valueOf(thisLine.substring(15, 17).trim());
		String element = thisLine.substring(17, 21);

		// calculate the number of days in the line
		int days = (thisLine.length() - 21) / 8;
		List<WeatherData> weatherRecords = new ArrayList<>(days);
		for (int i = 0; i < days; i++) {
			// Process each day in the line.
			WeatherData wd = new WeatherData();
			int value = Integer.valueOf(thisLine.substring(21 + 8 * i, 26 + 8 * i).trim());
			wd.StationId = stationId;
			wd.day = i + 1;
			wd.id = id;
			wd.year = year;
			wd.month = month;
			wd.element = element;
			wd.value = value / 10.0;
			wd.qflag = thisLine.substring(27 + 8 * i, 28 + 8 * i);

			// add if data matches filter
			if (weatherDataValid(wd, filterInfo)) {
				weatherRecords.add(wd);
			}
		}
		return weatherRecords;
	}
	
	private static boolean weatherDataValid(WeatherData wd, InputArguments filterInfo) {
		return (wd.qflag.trim().equals("") && wd.value > -999.9
				&& wd.element.toLowerCase().equals(filterInfo.WeatherElementFilter.toLowerCase())
				&& DataParser.isWeatherDataAfterDate(wd, filterInfo.StartYearInclusive, filterInfo.StartMonthInclusive)
				&& DataParser.isWeatherDataBeforeDate(wd, filterInfo.EndYearInclusive, filterInfo.EndMonthInclusive));
	}

	/**
	 * Check if weather data is, at least, before the given date
	 *
	 * @param wd
	 * @param year
	 * @param month
	 * @return T/F
	 */
	public static Boolean isWeatherDataBeforeDate(WeatherData wd, int year, int month) {
		return compareWeatherDataToDate(wd, year, month) < 0;
	}
	
	/**
	 * Check if weather data is, at least, after the given date
	 *
	 * @param wd
	 * @param year
	 * @param month
	 * @return T/F
	 */
	public static Boolean isWeatherDataAfterDate(WeatherData wd, int year, int month) {
		return compareWeatherDataToDate(wd, year, month) > 0;
	}

	/**
	 * Compare weather data to the given date
	 *
	 * @param wd
	 * @param year
	 * @param month
	 * @param day
	 * @return -1 if before, 0 if same date, 1 if after
	 */
	public static int compareWeatherDataToDate(WeatherData wd, int year, int month) {
		// year
		if (wd.year < year) {
			return -1;
		}
		else if (wd.year > year) {
			return 1;
		}
		else {
			// month
			if (wd.month < month) {
				return -1;
			}
			else if (wd.month > month) {
				return 1;
			}
			else {
				return 0;
			}
		}
	}
}
