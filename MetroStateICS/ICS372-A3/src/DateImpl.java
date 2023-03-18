import java.time.YearMonth;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
* Date Calendar implementation adaptor, valid inputs are listed for each method
*
* @author Ryan Gau
* @version 1.0
*/
public class DateImpl implements DateInterface {
	// Calendar backing initialized to the current date
	private final GregorianCalendar calendar = new GregorianCalendar();
	
	/**
	 * Gives how many days are in the month for the given year
	 *
	 * @param year
	 *            Year [0..]
	 * @param month
	 *            Month [0, 11]
	 * @return days in a given month of a year
	 */
	private int daysInMonth(int year, int month) {
		// validate
		if (year <= 0 || month < 0 || month > 11) {
			return -1;
		}
		
		// figure out how many days are in a given month (map calendar 0-11 over
		// to 1-12)
		YearMonth yearMonth = YearMonth.of(year, month + 1);
		return yearMonth.lengthOfMonth();
	}
	
	/**
	 * Checks if the given year is a leap year
	 *
	 * @param year
	 *            Year
	 * @return T/F if the specified year is a leap year
	 */
	private boolean isYearALeapYear(int year) {
		// validate
		if (year <= 0) {
			return false;
		}
		
		// check for a leap year
		return YearMonth.of(year, 1).isLeapYear();
	}

	/**
	 * Returns the year
	 *
	 * @return the year stored by the object
	 */
	@Override
	public int getYear() {
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * Sets the year
	 *
	 * @param year
	 * @return true
	 */
	@Override
	public boolean setYear(int year) {
		// validate
		if (year <= 0) {
			return false;
		}

		// check for the selected year that the current month and day exist
		int daysInSelectedMonthYear = daysInMonth(year, getMonth());
		if (getDayOfMonth() > daysInSelectedMonthYear) {
			return false;
		}

		// apply
		calendar.set(Calendar.YEAR, year);
		return true;
	}
	
	/**
	 * Returns the month: 0 for January, 1 for February, etc., and 11 for
	 * December.
	 *
	 * @return integer corresponding to the month
	 */
	@Override
	public int getMonth() {
		return calendar.get(Calendar.MONTH);
	}
	
	/**
	 * Sets the month, provided it is between 0 and 11, otherwise no changes
	 * are made.
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
	 *
	 * @param month
	 *            should be between 0 and 11
	 * @return returns true if month is valid and a change could be made
	 */
	@Override
	public boolean setMonth(int month) {
		// validate
		if (month < 0 || month > 11) {
			return false;
		}
		
		// check number of days exists in new month
		int daysInWantedMonth = daysInMonth(getYear(), month);
		if (getDayOfMonth() > daysInWantedMonth) {
			return false;
		}

		// apply new month
		calendar.set(Calendar.MONTH, month);
		return true;
	}
	
	/**
	 * Returns the day
	 *
	 * @return day of the month
	 */
	@Override
	public int getDayOfMonth() {
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
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
	@Override
	public boolean setDayOfMonth(int date) {
		// validate
		if (date <= 0) {
			return false;
		}
		
		// check maximum for current month
		int daysInMonth = daysInMonth(getYear(), getMonth());
		if (date > daysInMonth) {
			return false;
		}

		// apply new day
		calendar.set(Calendar.DAY_OF_MONTH, date);
		return true;
	}
	
	/**
	 * Returns the hour between 0 and 23
	 *
	 * @return the hour in military time
	 */
	@Override
	public int getHours() {
		// hour of day is already military
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * Sets the hour. Should be between 0 and 23 or no action is taken
	 *
	 * @param hours
	 *            between 0 and 23
	 * @return true only if hours is valid and a change could be made
	 */
	@Override
	public boolean setHours(int hours) {
		// validate
		if (hours < 0 || hours > 23) {
			return false;
		}

		// apply military time
		calendar.set(Calendar.HOUR_OF_DAY, hours);
		return true;
	}
	
	/**
	 * Returns the number of minutes past the hour
	 *
	 * @return number of minutes past the hour
	 */
	@Override
	public int getMinutes() {
		return calendar.get(Calendar.MINUTE);
	}
	
	/**
	 * Sets the minutes of this object{} should be between 0 and 59 or no
	 * changes are made
	 *
	 * @param minutes
	 *            0-59
	 * @return true only if hours is valid and a change could be made
	 */
	@Override
	public boolean setMinutes(int minutes) {
		// validate
		if (minutes < 0 || minutes > 59) {
			return false;
		}
		
		// apply
		calendar.set(Calendar.MINUTE, minutes);
		return true;
	}
	
	/**
	 * Returns the number of seconds past the minute
	 *
	 * @return the number of seconds past the minute
	 */
	@Override
	public int getSeconds() {
		return calendar.get(Calendar.SECOND);
	}
	
	/**
	 * Sets the seconds of this object{} should be between 0 and 59 or no
	 * changes are made
	 *
	 * @param seconds
	 *            0-59
	 * @return true only if seconds is valid and a change could be made
	 */
	@Override
	public boolean setSeconds(int seconds) {
		// validate
		if (seconds < 0 || seconds > 59) {
			return false;
		}
		
		// pass up the chain
		calendar.set(Calendar.SECOND, seconds);
		return true;
	}
	
	/**
	 * Returns true if and only if the year is a leap year
	 *
	 * @return true if and only if the year is a leap year
	 */
	@Override
	public boolean isLeapYear() {
		return isYearALeapYear(getYear());
	}
}
