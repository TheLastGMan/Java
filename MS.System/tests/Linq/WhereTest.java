package Linq;

import static org.junit.Assert.fail;

import org.junit.Test;

import System.TupleT2;

public class WhereTest
{
	@Test
	public void WherePass()
	{
		// Setup
		// Integer[] expected = { 3, 2, 2, 1, 7, 26, 25, 25, 24 };
		Integer[] expected = { 26, 25, 25, 24 };

		// Act
		@SuppressWarnings("unchecked")
		TupleT2<Integer, String>[] actual = BaseCollections.AlphabetNumbers().Where(f -> f.Item1 > 8).ToArray(TupleT2.class);

		// Check
		if (expected.length != actual.length)
			fail("Sequence lengths do not match.");
		
		for (int i = 0; i < expected.length; i++)
			if (expected[i] != actual[i].Item1)
				fail("Sequence does not match expected.");
	}

	@Test
	public void WhereWithIdPass()
	{
		// Setup
		// Integer[] expectes = { 3, 2, 2, 1, 7, 26, 25, 25, 24 };
		// Integer[] expectei = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
		Integer[] expected = { 3, 2, 7, 26, 25, 25, 24 };

		// Act
		@SuppressWarnings("unchecked")
		TupleT2<Integer, String>[] actual = BaseCollections.AlphabetNumbers().Where((f, index) -> f.Item1 > index).ToArray(TupleT2.class);

		// Check
		if (expected.length != actual.length)
			fail("Sequence lengths do not match.");
		
		for (int i = 0; i < expected.length; i++)
			if (expected[i] != actual[i].Item1)
				fail("Sequence does not match expected.");
	}
}
