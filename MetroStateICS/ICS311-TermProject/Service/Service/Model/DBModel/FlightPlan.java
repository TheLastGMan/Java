package Service.Model.DBModel;

import java.sql.Timestamp;

import Data.Service.BaseTable;

/**
 * @author rgau1
 *         FlightPlan DB Model
 */
public class FlightPlan extends BaseTable
{
	// public int Id;
	public int UserId;
	public byte FlightTypeIdent;
	public String AircraftIdentification;
	public String AircraftType;
	public short TrueAirspeed;
	public int DepartureAirportId;
	public Timestamp DepartureTimeUTC;
	public short CruisingAltitude;
	public String RouteOfFlight;
	public int DestinationAirportId;
	public byte EstTimeEnrouteHours;
	public byte EstTimeEnrouteMinutes;
	public String Remarks;
	public short FuelOnBoardHours;
	public short FuelOnBoardMinutes;
	public int AlternateAirportId;
	public String PilotInfo;
	public short NumberAboard;
	public String ColorOfAircraft;
	public String DestinationContactInfo;
	public Timestamp CreatedUTC;
}
