package Core;

/**
 * @author Ryan Gau
 * @version 1.2
 */
public class Logging {
	private static int logLevel = 0;
	private static int defLogLevel = LogLevel.INFO;
	
	/**
	 * Parse the environment variable "MIN_LOG_LEVEL" to determine what level to
	 * log at
	 * if unable to parse, the default log level will be used
	 *
	 * @return
	 */
	private static int getLogLevel() {
		if (logLevel == 0) {
			String env = System.getenv("MIN_LOG_LEVEL");
			if (env == null) {
				logLevel = defLogLevel;
			}
			else {
				env = env.trim().toUpperCase();
				try {
					logLevel = Integer.getInteger(env);
					if (logLevel > LogLevel.DEBUG || logLevel < LogLevel.EMERGENCY) {
						throw new Exception("pass off to defaulting section");
					}
				}
				catch (Exception ex) {
					// error, parse as string
					switch (env) {
						case "EMERGENCY":
						case "EMERG":
							logLevel = LogLevel.EMERGENCY;
							break;
						case "ALERT":
							logLevel = LogLevel.ALERT;
							break;
						case "CRITICAL":
						case "CRIT":
							logLevel = LogLevel.CRITICAL;
							break;
						case "ERROR":
						case "ERR":
							logLevel = LogLevel.ERROR;
							break;
						case "WARNING":
							logLevel = LogLevel.WARNING;
							break;
						case "NOTICE":
							logLevel = LogLevel.NOTICE;
							break;
						case "INFORMATIONAL":
						case "INFO":
							logLevel = LogLevel.INFO;
							break;
						case "DEBUG":
							logLevel = LogLevel.DEBUG;
							break;
						default:
							write("XXX: unknown log level: " + env, true);
							logLevel = defLogLevel;
					}
				}
			}
		}

		return logLevel;
	}

	public static void debug(String message) {
		if (LogLevel.DEBUG <= getLogLevel()) {
			write("DEBUG: " + message, false);
		}
	}

	public static void info(String message) {
		if (LogLevel.INFO <= getLogLevel()) {
			write("INFO: " + message, false);
		}
	}
	
	public static void warning(String message) {
		if (LogLevel.WARNING <= getLogLevel()) {
			write("WARN: " + message, false);
		}
	}

	public static void error(String message) {
		if (LogLevel.ERROR <= getLogLevel()) {
			write(message, true);
		}
	}

	private static void write(String message, boolean isError) {
		if (isError) {
			System.err.println(message);
		}
		else {
			System.out.println(message);
		}
	}
}
