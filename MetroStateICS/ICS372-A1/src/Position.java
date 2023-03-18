/**
 * An abstract class named Position, which stores the degree and minute of either a longitude or a latitude.
 *
 * @author Ryan Gau
 */
public abstract class Position
{
	private short minute;
	protected short degree;
	protected String direction;

	/**
	 * Get Degree
	 *
	 * @return degree
	 */
	public short getDegree()
	{
		return degree;
	}

	/**
	 * Set Degree
	 *
	 * @param value
	 *            degree
	 */
	public abstract void setDegree(int value);

	/**
	 * Get Minute
	 *
	 * @return minute
	 */
	public short getMinute()
	{
		return minute;
	}

	/**
	 * Set Minute
	 *
	 * @param value
	 *            minute
	 */
	public void setMinute(int value)
	{
		// validate
		if (value > 59)
			value = 59;
		else if (value < 0)
			value = 0;

		minute = (short)value;
	}
	
	/**
	 * Set Direction
	 *
	 * @param direction
	 *            direction
	 */
	protected void setDirection(String direction)
	{
		// validate
		if (direction == null)
			direction = "";

		// apply
		this.direction = direction;
	}

	/**
	 * Get Direction
	 *
	 * @return direction
	 */
	protected String getDirection()
	{
		return direction;
	}
}
