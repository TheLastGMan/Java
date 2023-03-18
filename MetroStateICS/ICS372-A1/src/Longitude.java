/**
 * A subclass of Position named Longitude, which represents the longitude of a position. It should have the extra
 * attribute (of type String) to store either “E” or “W”.
 * Latitude of Position ranging between 0 and 180, indicated by East or West
 *
 * @author Ryan Gau
 */
public class Longitude extends Position
{
	public Longitude(int degree, int minute, String eastOrWest)
	{
		setDegree(degree);
		setMinute(minute);
		setDirection(eastOrWest);
	}
	
	/**
	 * Set Direction
	 */
	@Override
	public void setDirection(String value)
	{
		// validate
		if (value.equals("E") || value.equals("e") || value.equals("W") || value.equals("w"))
			super.setDirection(value.toUpperCase());
		else
			super.setDirection("E");
	}

	/**
	 * Get Direction
	 *
	 * @return Direction (E|W)
	 */
	@Override
	public String getDirection()
	{
		return super.getDirection();
	}

	/**
	 * Set Degree
	 *
	 * @param value
	 *            degree
	 */
	@Override
	public void setDegree(int value)
	{
		// validate
		if (value < 0)
			value = 0;
		else if (value > 180)
			value = 180;

		// apply
		super.degree = (short)value;
	}
	
	/**
	 * [degree=10, minute=0, EastOrWest=E]
	 */
	@Override
	public String toString()
	{
		String result = "Longitude=Longitude: [Degree=" + getDegree() + ", Minute=" + getMinute() + ", EastOrWest=" + getDirection() + "]";
		return result;
	}
}
