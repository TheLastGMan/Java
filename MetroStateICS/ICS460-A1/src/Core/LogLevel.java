package Core;

/**
 * @author Ryan Gau
 * @version 1.0
 */
public class LogLevel {
	public static int EMERGENCY = 0;			// system is unusable
	public static int EMERG = 0;				// system is unusable
	public static int ALERT = 1;				// action must be taken
												// immediately
	public static int CRITICAL = 2;
	public static int CRIT = 2;
	public static int ERROR = 3;
	public static int ERR = 3;
	public static int WARNING = 4;
	public static int NOTICE = 5;				// normal but significant
													// conditions
													// not errors, but may
													// required
													// special handling
	public static int INFORMATIONAL = 6;
	public static int INFO = 6;
	public static int DEBUG = 7;				// useful information when
												// debugging
}
