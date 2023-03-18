package Linq;

import static org.junit.Assert.fail;

import org.junit.Test;

import System.TupleT2;

public class SelectTest
{
	@Test
	public void SelectPass()
	{
		// Setup
		Integer[] expected = { 3, 2, 2, 1, 7, 26, 25, 25, 24 };

		// Act
		Integer[] actual = BaseCollections.AlphabetNumbers().Select(f -> f.Item1).ToArray(Integer.class);

		// Check
		if (expected.length != actual.length)
			fail("Sequence lengths do not match.");
		
		for (int i = 0; i < expected.length; i++)
			if (expected[i] != actual[i])
				fail("Sequence does not match expected.");
	}

	@Test
	public void SelectWithIdPass()
	{
		// Setup
		Integer[] expected = { 3, 2, 2, 1, 7, 26, 25, 25, 24 };

		// Act
		@SuppressWarnings("unchecked")
		TupleT2<Integer, Integer>[] actual = BaseCollections.AlphabetNumbers().Select((f, index) -> new TupleT2<Integer, Integer>(index, f.Item1)).ToArray(TupleT2.class);

		// Check
		if (expected.length != actual.length)
			fail("Sequence lengths do not match.");
		
		for (int i = 0; i < expected.length; i++)
			if (i > 0 && actual[i].Item1 <= actual[i - 1].Item1)
				fail("Sequence index is not incrementing.");
			else if (expected[i] != actual[i].Item2)
				fail("Sequence does not match expected.");
	}
}
