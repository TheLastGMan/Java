/**
 * Input arguments from command line
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class InputArguments {
	public short StartYearInclusive;
	public short EndYearInclusive;
	public byte StartMonthInclusive;
	public byte EndMonthInclusive;
	public String WeatherElementFilter;
	
	public boolean SortTempASC() {
		return WeatherElementFilter.toLowerCase().equals("tmin");
	}
}
