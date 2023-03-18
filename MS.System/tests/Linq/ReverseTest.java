package Linq;

import static org.junit.Assert.fail;

import org.junit.Test;

import System.TupleT2;

public class ReverseTest
{
	@Test
	public void ReversePass()
	{
		// Setup
		// Integer[] expected = { 3, 2, 2, 1, 7, 26, 25, 25, 24 };
		Integer[] expected = { 24, 25, 25, 26, 7, 1, 2, 2, 3 };
		
		// Act
		@SuppressWarnings("unchecked")
		TupleT2<Integer, String>[] actual = BaseCollections.AlphabetNumbers().Reverse().ToArray(TupleT2.class);
		
		// Check
		if (expected.length != actual.length)
			fail("Sequence lengths do not match.");
		
		for (int i = 0; i < expected.length; i++)
			if (expected[i] != actual[i].Item1)
				fail("Sequence does not match expected.");
	}
}
