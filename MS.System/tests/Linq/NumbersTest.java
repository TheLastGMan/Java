package Linq;

import static org.junit.Assert.fail;

import org.junit.Test;

import System.Linq.Enumerable;

public class NumbersTest
{
	@Test
	public void MinTest()
	{
		// Setup
		// Integer[] expected = { 3, 2, 2, 1, 7, 26, 25, 25, 24 };
		int expected = 1;
		
		// Act
		int actual = BaseCollections.AlphabetNumbers().Min(f -> f.Item1);
		
		// Assert
		if (expected != actual)
			fail("Actual does not match Expected.");
	}

	@Test
	public void MaxTest()
	{
		// Setup
		// Integer[] expected = { 3, 2, 2, 1, 7, 26, 25, 25, 24 };
		int expected = 26;

		// Act
		int actual = BaseCollections.AlphabetNumbers().Max(f -> f.Item1);

		// Assert
		if (expected != actual)
			fail("Actual does not match Expected.");
	}

	@Test
	public void AverageTest()
	{
		// Setup
		// Integer[] expected = { 3, 2, 2, 1, 7, 26, 25, 25, 24 };
		double expected = (3 + 2 + 2 + 1 + 7 + 26 + 25 + 25 + 24) / 9.0D;

		// Act
		double actual = BaseCollections.AlphabetNumbers().Average(f -> f.Item1);

		// Assert
		if (expected != actual)
			fail("Actual does not match Expected.");
	}

	@Test
	public void SumTest()
	{
		// Setup
		// Integer[] expected = { 3, 2, 2, 1, 7, 26, 25, 25, 24 };
		int expected = (3 + 2 + 2 + 1 + 7 + 26 + 25 + 25 + 24);

		// Act
		int actual = -1;
		try
		{
			actual = (int)BaseCollections.AlphabetNumbers().Sum(f -> f.Item1);
		}
		catch (Exception ex)
		{
			fail("Sum Error: " + ex.getMessage());
		}

		// Assert
		if (expected != actual)
			fail("Actual does not match Expected.");
	}

	@Test
	public void RangeTest()
	{
		// Setup
		int[] expected = { 1, 2, 3, 4, 5 };

		// Act
		Integer[] actual = Enumerable.Range(1, 5).ToArray(Integer.class);

		// Assert
		if (expected.length != actual.length)
			fail("Sequence lengths do not match.");
		
		for (int i = 0; i < expected.length; i++)
			if (expected[i] != actual[i])
				fail("Range not as expected.");
	}
	
	@Test
	public void RepeatTest()
	{
		// Setup
		int[] expected = { 1, 1, 1, 1, 1 };

		// Act
		Integer[] actual = Enumerable.Repeat(1, 5).ToArray(Integer.class);

		// Assert
		if (expected.length != actual.length)
			fail("Sequence lengths do not match.");
		
		for (int i = 0; i < expected.length; i++)
			if (expected[i] != actual[i])
				fail("Repeat not as expected.");
	}
}
