package PLinq;

import static org.junit.Assert.fail;

import org.junit.Test;

import System.Collections.Generic.List;

public class SelectTest
{
	@Test
	public void SelectPass()
	{
		// Setup
		List<Integer> expected = new List<>();
		for (int i = 0; i < 10000000; i++)
			expected.Add(i * 2);
		
		// Act
		List<Integer> actual = new List<>();
		for (int i = 0; i < 10000000; i++)
			actual.Add(i);
		actual = actual.AsParallel().Select(f -> f * 2).OrderBy(f -> f).ToList();
		
		// Check
		if (!expected.SequenceEqual(actual))
			fail("Sequences are not equal");
	}

	@Test
	public void SelectPassSequential()
	{
		// Setup
		List<Integer> expected = new List<>();
		for (int i = 0; i < 10000000; i++)
			expected.Add(i * 2);
		
		// Act
		List<Integer> actual = new List<>();
		for (int i = 0; i < 10000000; i++)
			actual.Add(i);
		actual = actual.Select(f -> f * 2).OrderBy(f -> f).ToList();
		
		// Check
		if (!expected.SequenceEqual(actual))
			fail("Sequences are not equal");
	}
}
