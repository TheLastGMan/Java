package Linq;

import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

import System.Collections.Generic.List;

public class SequenceEqualTest
{
	@Test
	public void SequenceEqualPass()
	{
		// setup
		List<Integer> expected = new List<>(Arrays.asList(1, 5, 10, 20, 15, 2));

		// act
		List<Integer> actual = new List<>(Arrays.asList(1, 5, 10, 20, 15, 2));
		
		// assert
		if (!expected.SequenceEqual(actual))
			fail("Sequences do not match");
	}

	@Test
	public void SequenceEqualFail()
	{
		// setup
		List<Integer> expected = new List<>(Arrays.asList(1, 5, 10, 20, 15, 2));
		
		// act
		List<Integer> actual = new List<>(Arrays.asList(1, 5, 30, 20, 15, 2));

		// assert
		if (expected.SequenceEqual(actual))
			fail("Sequences match when expected not to.");
	}
}
