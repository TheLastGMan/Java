package Linq;

import static org.junit.Assert.fail;

import org.junit.Test;

public class AnyTest
{
	@Test
	public void AnyPass()
	{
		// setup
		boolean expected = true;

		// act
		boolean actual = BaseCollections.AlphabetNumbers().Any();

		if (expected != actual)
			fail("Any elements did not pass");
	}
	
	@Test
	public void AnyConditionPass()
	{
		// setup
		boolean expected = true;
		
		// act
		boolean actual = BaseCollections.AlphabetNumbers().Any(i -> i.Item1 > 0);
		
		if (expected != actual)
			fail("Any elements of condition did not pass");
	}
}
