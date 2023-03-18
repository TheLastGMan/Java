/**
 *
 */
package System.Collections;

import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

import System.Collections.Generic.*;

public class SortedListTest
{
	@Test
	public void OrderedListPass()
	{
		// setup
		List<Integer> expected = new List<>(Arrays.asList(1, 3, 5, 7, 9));

		// act
		SortedList<Integer, Integer> actual = new SortedList<>((f, s) ->
			{
				if (f < s)
					return -1;
				else if (f == s)
					return 0;
				else
					return 1;
			});
		actual.Add(5, 5);
		actual.Add(1, 1);
		actual.Add(9, 9);
		actual.Add(3, 3);
		actual.Add(7, 7);

		// assert
		if (!expected.SequenceEqual(actual.Keys()))
			fail("All elements did not match condition.");
	}
}
