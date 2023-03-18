package System.Threading;

/**
 * A synchronization primitive that can also be used for interprocess synchronization.
 * Based on Microsoft's System.Threading.Mutex
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class Mutex extends WaitHandle
{
	private String name = "";
	
	public Mutex()
	{
		this("");
	}
	
	public Mutex(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
}
