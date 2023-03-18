/**
 * A subclass of Position named Latitude, which represents the latitude of a position. It should have the extra
 * attribute (of type String) to store either “N” or “S”.
 * Latitude of Position ranging between 0 and 90, indicated by North or South
 *
 * @author Ryan Gau
 */
public class Latitude extends Position
{
	/**
	 * Create instance of Latitude
	 *
	 * @param degree
	 *            Degree
	 * @param minute
	 *            Minute
	 * @param northOrSouth
	 *            N or S
	 */
	public Latitude(int degree, int minute, String northOrSouth)
	{
		setDegree(degree);
		setMinute(minute);
		setDirection(northOrSouth);
	}

	/**
	 * Set Direction
	 */
	@Override
	public void setDirection(String value)
	{
		// validate
		if (value.equals("N") || value.equals("n") || value.equals("S") || value.equals("s"))
			super.setDirection(value.toUpperCase());
		else
			super.setDirection("N");
	}
	
	/**
	 * Get Direction
	 *
	 * @return Direction (N|S)
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
		else if (value > 90)
			value = 90;

		// apply
		super.degree = (short)value;
	}
	
	/**
	 * [degree=10, minute=0, NorthOrSouth=N]
	 */
	@Override
	public String toString()
	{
		String result = "Latitude=Latitude: [Degree=" + getDegree() + ", Minute=" + getMinute() + ", NorthOrSouth=" + getDirection() + "]";
		return result;
	}
}
