/**
 * @author Instructor Provided, slightly modified
 * @version Unknown, Ryan Gau
 */
public class StationData {
	public String id;
	public float latitude;
	public float longitude;
	public float elevation;
	public String state;
	public String name;
	
	@Override
	public String toString() {
		return "id=" + id + " latitude=" + latitude + " longitude=" + longitude + " elevation=" + elevation + " state="
				+ state + " name=" + name;
	}
}
