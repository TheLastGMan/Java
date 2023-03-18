package Linq;

import static org.junit.Assert.fail;

import org.junit.Test;

import System.TupleT2;

public class OrderByTest
{
	@Test
	public void OrderByPass()
	{
		// Setup
		// Integer[] expected = { 3, 2, 2, 1, 7, 26, 25, 25, 24 };
		Integer[] expected = { 1, 2, 2, 3, 7, 24, 25, 25, 26 };

		// Act
		@SuppressWarnings("unchecked")
		TupleT2<Integer, String>[] actual = BaseCollections.AlphabetNumbers().OrderBy(f -> f.Item1).ToArray(TupleT2.class);

		// Check
		if (expected.length != actual.length)
			fail("Counts do not match");
		
		for (int i = 0; i < expected.length; i++)
			if (actual[i].Item1 != expected[i])
				fail("Elements not sorted");
	}
	
	@Test
	public void CountConditionPass()
	{
		// Setup
		// Integer[] expected = { 3, 2, 2, 1, 7, 26, 25, 25, 24 };
		Integer[] expected = { 26, 25, 25, 24, 7, 3, 2, 2, 1 };

		// Act
		@SuppressWarnings("unchecked")
		TupleT2<Integer, String>[] actual = BaseCollections.AlphabetNumbers().OrderBy(f -> f.Item1, (s, n) -> n.compareTo(s)).ToArray(TupleT2.class);

		// Check
		if (expected.length != actual.length)
			fail("Counts do not match");
		
		for (int i = 0; i < expected.length; i++)
			if (actual[i].Item1 != expected[i])
				fail("Elements not sorted");
	}
}
