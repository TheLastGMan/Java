package Linq;

import static org.junit.Assert.fail;

import org.junit.Test;

public class CountTest
{
	@Test
	public void CountPass()
	{
		// Setup
		// Integer[] expected = { 3, 2, 2, 1, 7, 26, 25, 25, 24 };
		double expected = 9;
		
		// Act
		double actual = BaseCollections.AlphabetNumbers().Count();
		
		// Check
		if (expected != actual)
			fail("Counts do not match");
	}

	@Test
	public void CountConditionPass()
	{
		// Setup
		// Integer[] expected = { 3, 2, 2, 1, 7, 26, 25, 25, 24 };
		double expected = 4;
		
		// Act
		double actual = BaseCollections.AlphabetNumbers().Count(f -> f.Item1 > 8);
		
		// Check
		if (expected != actual)
			fail("Counts from a condition do not match");
	}
}
