package Linq;

import static org.junit.Assert.fail;

import org.junit.Test;

import System.TupleT2;

public class TakeTest
{
	@Test
	public void TakeTestPass()
	{
		// Setup
		// Integer[] expected = { 3, 2, 2, 1, 7, 26, 25, 25, 24 };
		Integer[] expected = { 3, 2, 2, 1, 7 };

		// act
		@SuppressWarnings("unchecked")
		TupleT2<Integer, String>[] actual = BaseCollections.AlphabetNumbers().Take(5).ToArray(TupleT2.class);

		for (int i = 0; i < actual.length; i++)
			if (expected[i] != actual[i].Item1)
				fail("Sequences do not match");
	}
}
