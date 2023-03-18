package Service.Util;

public class Common
{
	public static boolean IsDEBUG()
	{
		String debug = GetEnvVar("DEBUG");
		if (debug.equals(""))
			return false;
		
		boolean result = debug.equals("1") ? true : false;
		return result;
	}
	
	public static String GetEnvVar(String variable)
	{
		try
		{
			return System.getenv(variable);
		}
		catch (Exception ex)
		{
			return "";
		}
	}
}
