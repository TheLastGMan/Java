import java.io.File;
import java.util.*;

/**
 * Main entry of Programming Assignment 2
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class Program {
	private static final int _NumberOfTemperatures = 5;
	
	public static void main(String[] args) {
		try {
			// write info
			int numThreads = ParallelEnumerable.AvailableCores() * 3;
			System.out.println("ICS440 - A2 - Ryan Gau");
			System.out.println("Est. Memory Usage: " + (600 + numThreads * 100) + " MB");
			System.out.println();

			// parse input arguments
			InputArguments cmdArgs = getInputArguments();
			
			// read station file (small enough we can keep in memory)
			// App Spec: assume file is in the same directory as the program
			List<StationData> stationData = loadStationData(new File("ghcnd-stations.txt"));

			// find weather data using min/max temperatures, based on arguments
			File folder = new File("ghcnd_hcn");
			List<File> weatherFiles = new ArrayList<>();
			for (File f : folder.listFiles()) {
				weatherFiles.add(f);
			}
			
			// pick the top weather data;
			List<WeatherData> weatherData = new ParallelEnumerable<>(weatherFiles).WithDegreeOfParallelism(numThreads)
					.SelectMany(f -> {
						try {
							List<WeatherData> wData = parseWeatherData(f, cmdArgs);
							wData = pickNWeatherData(wData, cmdArgs, _NumberOfTemperatures);
							return wData;
						}
						catch (Exception ex) {
							System.out.println("Error during find min/max weather data: " + ex.getMessage());
							return new ArrayList<>();
						}
					}).ToList();
			
			// filter all results to final 5 and map
			weatherData = pickNWeatherData(weatherData, cmdArgs, _NumberOfTemperatures);
			List<WeatherStationResult> weatherResults = mapWeatherDataWithStation(weatherData, stationData);
			
			// output results
			outputWeatherResult(weatherResults);
		}
		catch (Exception ex) {
			System.out.println("Appliction Error: " + ex.getMessage());
		}
	}
	
	/**
	 * Write station and weather data to the console
	 *
	 * @param results
	 */
	private static void outputWeatherResult(List<WeatherStationResult> results) {
		System.out.println();
		for (WeatherStationResult result : results) {
			System.out.println(result.Weather.toString());
			System.out.println(result.Station.toString());
		}
	}

	/**
	 * Find the specified station id in the collection of stations
	 *
	 * @param stations
	 * @param stationId
	 * @return
	 * @throws Exception
	 */
	private static StationData findStation(List<StationData> stations, String stationId) throws Exception {
		StationData sd = new ParallelEnumerable<>(stations).FirstOrDefault(f -> f.id.equals(stationId));
		if (sd == null) {
			throw new Exception("Station id not found: " + stationId);
		}
		return sd;
	}

	/**
	 * Map the weather data with its station
	 *
	 * @param weatherInfo
	 * @param stations
	 * @return
	 * @throws Exception
	 */
	private static List<WeatherStationResult> mapWeatherDataWithStation(List<WeatherData> weatherInfo,
			List<StationData> stations) throws Exception {
		List<WeatherStationResult> results = new ArrayList<>();
		for (WeatherData wd : weatherInfo) {
			// find station
			StationData station = findStation(stations, wd.StationId);
			WeatherStationResult wsr = new WeatherStationResult(station, wd);
			results.add(wsr);
		}
		return results;
	}
	
	/***
	 * Pick N number of weather data based on the input arguments
	 *
	 * @param weatherData
	 * @param cmdArgs
	 * @param recordCount
	 * @return
	 * @throws Exception
	 */
	private static List<WeatherData> pickNWeatherData(List<WeatherData> weatherData, InputArguments cmdArgs,
			int recordCount) throws Exception {
		// order by ASC/DESC based on Command Arguments
		List<WeatherData> data = MergeSort.Sort(weatherData, f -> f.value);
		ParallelEnumerable<WeatherData> orderedData = new ParallelEnumerable<>(data);
		if (!cmdArgs.SortTempASC()) {
			orderedData = new ParallelEnumerable<>(orderedData.ToList()).Reverse();
		}

		return orderedData.Take(recordCount).ToList();
	}
	
	/**
	 * Parse the weather data file
	 *
	 * @param weatherFile
	 *            Weather Data File
	 * @param elementFilter
	 *            Only use the data for the provided element
	 * @return Colection(Of WeatherData)
	 * @throws Exception
	 *             Error
	 */
	private static List<WeatherData> parseWeatherData(File weatherFile, InputArguments elementInfo) throws Exception {
		try (TextReader tr = new TextReader(weatherFile)) {
			List<WeatherData> weatherRecords = new ArrayList<>();
			String line = "";
			while ((line = tr.readLine()) != null) {
				// load station id from filename
				String stationId = weatherFile.getName();
				stationId = stationId.substring(0, stationId.indexOf('.'));
				
				// parse weather file
				List<WeatherData> wd = DataParser.parseWeatherData(line, stationId, elementInfo);
				weatherRecords.addAll(wd);
			}
			return weatherRecords;
		}
		catch (Exception ex) {
			throw new Exception("Error reading Weather File: " + ex.getMessage(), ex);
		}
	}
	
	/***
	 * Read station data file
	 *
	 * @param stationFile
	 *            Station File
	 * @return Collection of StationData
	 * @throws Exception
	 *             Error while reading file
	 */
	private static List<StationData> loadStationData(File stationFile) throws Exception {
		try (TextReader tr = new TextReader(stationFile)) {
			List<StationData> stationRecords = new LinkedList<>();
			String line = "";
			while ((line = tr.readLine()) != null) {
				StationData station = DataParser.parseStationData(line);
				stationRecords.add(station);
			}
			return stationRecords;
		}
		catch (Exception ex) {
			System.out.println("Error reading station data: " + ex.getMessage());
			throw ex;
		}
	}
	
	/**
	 * Read input arguments
	 *
	 * @return InputArguments
	 */
	private static InputArguments getInputArguments() {
		InputArguments args = new InputArguments();

		// Start and end year
		while (true) {
			args.StartYearInclusive = (short)ConsoleReader.readInt("Starting Year (Inclusive)", 0, 9999);
			args.StartMonthInclusive = (byte)ConsoleReader.readInt("Starting Month (Inclusive) [1, 12]", 1, 12);
			args.EndYearInclusive = (short)ConsoleReader.readInt("Ending Year (Inclusive)", 0, 9999);
			args.EndMonthInclusive = (byte)ConsoleReader.readInt("Ending Month (Inclusive) [1, 12]", 1, 12);

			if (args.EndYearInclusive > args.StartYearInclusive) {
				break;
			}
			else if (args.EndYearInclusive == args.StartYearInclusive) {
				// check month
				if (args.EndMonthInclusive >= args.StartMonthInclusive) {
					break;
				}
				else {
					System.out.println();
					System.out.println(
							"Validation Error: Ending Month/Year (Inclusive) must be at or after the Starting Month/Year (Inclusive)");
					System.out.println();
				}
			}
			else {
				System.out.println();
				System.out.println(
						"Validation Error: Ending Year (Inclusive) must be at or after the Ending Year (Inclusive)");
				System.out.println();
			}
		}

		// element selection
		while (true) {
			args.WeatherElementFilter = ConsoleReader.readLine("Temperature Selection [TMAX | TMIN]");
			if (args.WeatherElementFilter.toLowerCase().equals("tmin")
					|| args.WeatherElementFilter.toLowerCase().equals("tmax")) {
				break;
			}
			else {
				System.out.println();
				System.out.println("Validation Error: Temperature Selection must be TMAX or TMIN");
				System.out.println();
			}
		}
		return args;
	}
}
