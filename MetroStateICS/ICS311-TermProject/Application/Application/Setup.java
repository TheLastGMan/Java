package Application;

public class Setup
{
	public static void DBSetup() throws Exception
	{
		//@formatter:off
		// set up database
		Data.Service.DBService.Setup("localhost", 3306, "RyanGauICS311TermProjectFPS", "Kuodi Master", "A+ Would Be Appreciated");
		//Data.Service.DBService.Setup("METROSTFR5613CZ.db.8515393.hostedresource.com", 3306, "METROSTFR5613CZ", "METROSTFR5613CZ", "MSUn!v3rs!ty");
		//@formatter:on
	}
}
