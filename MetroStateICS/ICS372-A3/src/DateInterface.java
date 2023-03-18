/**
 * DateInterface contains a set of methods to manipulate dates. Similar to some
 * of
 * the methods in the class java.util.Date.
 */
public interface DateInterface {
	/**
	 * Returns the year
	 *
	 * @return the year stored by the object
	 */
	int getYear();
	
	/**
	 * Sets the year
	 *
	 * @param year
	 * @return true
	 */
	boolean setYear(int year);
	
	/**
	 * Returns the month: 0 for January, 1 for February, etc., and 11 for
	 * December.
	 *
	 * @return integer corresponding to the month
	 */
	int getMonth();
	
	/**
	 * Sets the month, provided it is between 0 and 11; otherwise no changes are
	 * made.
	 * month must be consistent with the year and day of month. For example, if
	 * month is
	 * 1 (Februray), but year is 2016 and day of month is 30 as stored in the
	 * DateInterface
	 * object, the month stored in the DateInterface object must not be changed.
	 * The client
	 * must do some extra work to effect the change: for example, change the day
	 * to 1 and then change
	 * month.
	 * Anther example is changing month to 3 (April) when day of month is 31.
	 * 2
	 *
	 * @param month
	 *            should be between 0 and 11
	 * @return returns true if month is valid and a change could be made
	 */
	boolean setMonth(int month);
	
	/**
	 * Returns the day
	 *
	 * @return day of the month
	 */
	int getDayOfMonth();
	
	/**
	 * Sets the day of the month. Should be a valid day or no action is taken.
	 * For example, if you set 29 for February and the year is 1900 as stored in
	 * the
	 * DateInterface object, the change will not be made. Essentially, the
	 * change in dayOfMonth
	 * must not potentially be an inconsistent date. The client
	 * must do some extra work to effect the change: for example, change the
	 * month to 11 and then change
	 * month.
	 *
	 * @param date
	 *            a valid date for the month and year
	 * @return true only if the day is valid and a change could be made
	 */
	boolean setDayOfMonth(int date);
	
	/**
	 * Returns the hour between 0 and 23
	 *
	 * @return the hour in military time
	 */
	int getHours();
	
	/**
	 * Sets the hour. Should be between 0 and 23 or no action is taken
	 *
	 * @param hours
	 *            between 0 and 23
	 * @return true only if hours is valid and a change could be made
	 */
	boolean setHours(int hours);
	
	/**
	 * Returns the number of minutes past the hour
	 *
	 * @return number of minutes past the hour
	 */
	int getMinutes();
	
	/**
	 * Sets the minutes of this object; should be between 0 and 59 or no changes
	 * are made
	 *
	 * @param minutes
	 *            0-59
	 * @return true only if hours is valid and a change could be made
	 */
	boolean setMinutes(int minutes);
	
	/**
	 * Returns the number of seconds past the minute
	 *
	 * @return the number of seconds past the minute
	 */
	int getSeconds();
	
	/**
	 * Sets the seconds of this object; should be between 0 and 59 or no changes
	 * are made
	 *
	 * @param seconds
	 *            0-59
	 * @return true only if seconds is valid and a change could be made
	 *         3
	 */
	boolean setSeconds(int seconds);
	
	/**
	 * Returns true if and only if the year is a leap year
	 *
	 * @return true if and only if the year is a leap year
	 */
	boolean isLeapYear();
}
