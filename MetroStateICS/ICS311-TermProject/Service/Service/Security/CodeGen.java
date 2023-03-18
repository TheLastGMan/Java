package Service.Security;

import java.time.LocalDateTime;
import java.util.Random;

public class CodeGen
{
	/**
	 * Generate a rudimentary response code
	 *
	 * @param number
	 *            Generate code for
	 * @return Coded number
	 */
	public static int GenerateCode()
	{
		// XOR with another number, as this is not negative, not need to turn it into a positive
		return (LocalDateTime.now().getNano() ^ (new Random()).nextInt(1000000000));
	}
}
