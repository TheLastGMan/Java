package Linq;

import static org.junit.Assert.fail;

import org.junit.Test;

import System.TupleT2;

public class ThenByDescendingTest
{
	@Test
	public void ThenByDescendingPass()
	{
		// Setup
		// Integer[] expected = { 3, 2, 2, 1, 7, 26, 25, 25, 24 };
		// String[] expected = { c, d, b, a, g, z, y, r, x };
		String[] expected = { "a", "d", "b", "c", "g", "x", "y", "r", "z" };
		
		// Act
		@SuppressWarnings("unchecked")
		TupleT2<Integer, String>[] actual = BaseCollections.AlphabetNumbers().OrderBy(f -> f.Item1).ThenByDescending(f -> f.Item2).ToArray(TupleT2.class);
		
		// Check
		if (expected.length != actual.length)
			fail("Counts do not match");
		
		for (int i = 0; i < expected.length; i++)
			if (actual[i].Item2 != expected[i])
				fail("Elements not sorted");
	}

	@Test
	public void ThenByDescendingConditionPass()
	{
		// Setup
		// Integer[] expected = { 3, 2, 2, 1, 7, 26, 25, 25, 24 };
		// String[] expected = { c, d, b, a, g, z, y, r, x };
		String[] expected = { "a", "b", "d", "c", "g", "x", "r", "y", "z" };
		
		// Act
		@SuppressWarnings("unchecked")
		TupleT2<Integer, String>[] actual = BaseCollections.AlphabetNumbers().OrderBy(f -> f.Item1).ThenByDescending(f -> f.Item2, (j, k) -> k.compareTo(j)).ToArray(TupleT2.class);
		
		// Check
		if (expected.length != actual.length)
			fail("Counts do not match");
		
		for (int i = 0; i < expected.length; i++)
			if (actual[i].Item2 != expected[i])
				fail("Elements not sorted");
	}
}
