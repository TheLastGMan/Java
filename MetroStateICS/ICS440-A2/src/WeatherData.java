/**
 * @author Instructor Provided, slightly altered
 * @version Unknown, Ryan Gau
 */
public class WeatherData {
	public String id;
	public int year;
	public int month;
	public int day;
	public String element;
	public double value;
	public String qflag;
	
	public String StationId;

	@Override
	public String toString() {
		return "id=" + id + " year=" + year + " month=" + month + " day=" + day + " element=" + element + " value="
				+ value + "C qflag=" + qflag;
	}
}
