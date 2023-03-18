import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit Test for Date Implementation
 * Method names should be self explanatory
 * 
 * @author Ryan Gau
 * @version 1.0
 */
public class DateImplTest {
	private DateImpl dateAccess;
	
	@Before
	public void initialize() {
		dateAccess = new DateImpl();
	}
	
	// #region Year
	
	@Test
	public void GetYearTest() {
		// act
		int year = dateAccess.getYear();
		
		// assert
		assertTrue("invalid year retreived", year >= 1);
	}
	
	@Test
	public void SetYearTest() {
		// act
		if (!dateAccess.setYear(2000)) {
			fail("could not set an expected valid year");
		}
		int year = dateAccess.getYear();
		
		// assert
		assertTrue("year does not match expected", year == 2000);
	}
	
	@Test
	public void SetYearInvalidTest() {
		// act
		if (dateAccess.setYear(-1)) {
			fail("invalid year passed");
		}
	}

	// #endregion
	// #region Month
	
	@Test
	public void GetMonthTest() {
		// act
		int month = dateAccess.getMonth();

		// assert
		assertTrue("invalid month retrieved", month >= 0 && month <= 11);
	}
	
	public void SetMonthTest() {
		// act
		if (!dateAccess.setMonth(3)) {
			fail("could not set expected valid month");
		}
		int month = dateAccess.getMonth();
		
		// assert
		assertTrue("month does not match expected", month == 3);
	}
	
	@Test
	public void SetMonthInvalidLBTest() {
		if (dateAccess.setMonth(-1)) {
			fail("invalid month was allowed to be set");
		}
	}
	
	@Test
	public void SetMonthInvalidUBTest() {
		if (dateAccess.setMonth(12)) {
			fail("invalid month was allowed to be set");
		}
	}
	
	// #endregion
	// #region Day
	
	@Test
	public void GetDayTest() {
		// act
		int day = dateAccess.getDayOfMonth();

		// assert
		assertTrue("invalid day", day >= 1 && day <= 31);
	}

	@Test
	public void SetDayTest() {
		// act
		if (!dateAccess.setDayOfMonth(18)) {
			fail("could not set expected valid day");
		}
	}

	@Test
	public void SetDayInvalidLBTest() {
		// act
		if (dateAccess.setDayOfMonth(-1)) {
			fail("invalid day was allowed to be set");
		}
	}

	@Test
	public void SetDayInvalidUBTest() {
		if (dateAccess.setDayOfMonth(32)) {
			fail("invalid day was allowed to be set");
		}
	}

	// #endregion
	// #region Hour
	
	@Test
	public void GetHourTest() {
		// act
		int hour = dateAccess.getHours();

		// assert
		assertTrue("invalid hour", hour >= 0 && hour <= 23);
	}

	@Test
	public void SetHourTest() {
		// act (use 18 as we should allow military time
		if (!dateAccess.setHours(18)) {
			fail("could not set expected valid hours");
		}
	}

	@Test
	public void SetHourInvalidLBTest() {
		// act
		if (dateAccess.setHours(-1)) {
			fail("invalid hours was allowed to be set");
		}
	}

	@Test
	public void SetHourInvalidUBTest() {
		if (dateAccess.setHours(25)) {
			fail("invalid hours was allowed to be set");
		}
	}

	// #endregion
	// #region Minute

	@Test
	public void GetMinuteTest() {
		// act
		int min = dateAccess.getMinutes();
		
		// assert
		assertTrue("invalid minute", min >= 0 && min <= 59);
	}
	
	@Test
	public void SetMinuteTest() {
		// act
		if (!dateAccess.setMinutes(31)) {
			fail("could not set expected valid minute");
		}
	}
	
	@Test
	public void SetMinuteInvalidLBTest() {
		// act
		if (dateAccess.setMinutes(-1)) {
			fail("invalid minute was allowed to be set");
		}
	}
	
	@Test
	public void SetMinuteInvalidUBTest() {
		// act
		if (dateAccess.setMinutes(60)) {
			fail("invalid minute was allowed to be set");
		}
	}
	
	// #endregion
	// #region Second
	
	@Test
	public void GetSecondTest() {
		// act
		int second = dateAccess.getSeconds();

		// assert
		assertTrue("invalid seconds", second >= 0 && second <= 59);
	}

	@Test
	public void SetSecondTest() {
		// act
		if (!dateAccess.setSeconds(45)) {
			fail("could not set expected valid seconds");
		}
	}

	@Test
	public void SetSecondInvalidLBTest() {
		// act
		if (dateAccess.setSeconds(-1)) {
			fail("invalid seconds was allowed to be set");
		}
	}

	@Test
	public void SetSecondInvalidUBTest() {
		// act
		if (dateAccess.setSeconds(60)) {
			fail("invalid seconds was allowed to be set");
		}
	}

	// #endRegion
	// #region LeapYear
	
	@Test
	public void LeapYearValidTest() {
		// act
		dateAccess.setYear(2004);
		boolean isLeapYear = dateAccess.isLeapYear();
		
		// assert
		assertTrue("year was not found as a valid leap year", isLeapYear);
	}
	
	@Test
	public void LeapYearInvalidTest() {
		// act
		dateAccess.setYear(2003);
		boolean isLeapYear = dateAccess.isLeapYear();
		
		// assert
		assertFalse("invalid yeap year was detected as valid", isLeapYear);
	}
	
	// #endregion

	// #region Date Change Cascades

	@Test
	public void MonthChangeToInvalidDaysTest() {
		// act (31 days in month 0)
		dateAccess.setDayOfMonth(1);
		dateAccess.setMonth(0);
		dateAccess.setDayOfMonth(31);

		// assert we can't change to month 1 that only has 28/29 days
		if (dateAccess.setMonth(1)) {
			fail("month change allowed with invalid days in new month");
		}
	}

	@Test
	public void YearChangeFebInvalidDays() {
		// act (29 days in a leap year)
		dateAccess.setDayOfMonth(1);
		dateAccess.setMonth(1);
		dateAccess.setYear(2004);
		dateAccess.setDayOfMonth(29);
		
		// assert we can't change to a non leap year with our invalid day of the
		// month
		if (dateAccess.setYear(2003)) {
			fail("year change allowed to one with fewer days in month");
		}
	}
	
	// #endregion
}
