public class Program
{
	public static void main(String[] args)
	{
		try
		{
			new Program().run();
		}
		catch (Exception ex)
		{
			System.out.println("ERROR: " + ex.getMessage());
		}
	}

	public void run() throws InterruptedException
	{
	}
}
