package Linq;

import static org.junit.Assert.fail;

import org.junit.Test;

public class AllTest
{
	@Test
	public void AllPass()
	{
		// setup
		boolean expected = true;

		// act
		boolean actual = BaseCollections.AlphabetNumbers().All(i -> i.Item1 > 0);
		
		// assert
		if (actual != expected)
			fail("All elements did not match condition.");
	}
}
