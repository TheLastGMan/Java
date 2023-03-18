package Service.Repository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import Data.Service.BaseRepository;
import System.Linq.QList;

public class FlightPlan extends BaseRepository<Service.Model.FlightPlan>
{
	
	@Override
	public Service.Model.FlightPlan MapRecordToModel(Object[] r) throws SQLException
	{
		Service.Model.FlightPlan model = new Service.Model.FlightPlan();
		model.Id = (Integer)r[0];
		model.UserId = (Integer)r[1];
		model.FlightTypeIdent = Byte.parseByte(((Integer)r[2]).toString());
		model.AircraftIdentification = (String)r[3];
		model.AircraftType = (String)r[4];
		model.TrueAirspeed = Short.parseShort(((Integer)r[5]).toString());
		model.DepartureAirportId = (Integer)r[6];
		model.DepartureTimeUTC = (Timestamp)r[7];
		model.CruisingAltitude = Short.parseShort(((Integer)r[8]).toString());
		model.RouteOfFlight = (String)r[9];
		model.DestinationAirportId = (Integer)r[10];
		model.EstTimeEnrouteHours = Byte.parseByte(((Integer)r[11]).toString());
		model.EstTimeEnrouteMinutes = Byte.parseByte(((Integer)r[12]).toString());
		model.Remarks = (String)r[13];
		model.FuelOnBoardHours = Short.parseShort(((Integer)r[14]).toString());
		model.FuelOnBoardMinutes = Short.parseShort(((Integer)r[15]).toString());
		model.AlternateAirportId = (Integer)r[16];
		model.PilotInfo = (String)r[17];
		model.NumberAboard = Short.parseShort(((Integer)r[18]).toString());
		model.ColorOfAircraft = (String)r[19];
		model.DestinationContactInfo = (String)r[20];
		model.CreatedUTC = (Timestamp)r[21];
		return model;
	}
	
	@Override
	public String TableName()
	{
		return "`FlightPlan`".toLowerCase();
	}
	
	@Override
	public boolean Update(Service.Model.FlightPlan f)
	{
		//@formatter:off
		String sql = "UPDATE " + TableName() + " SET " +
					"`UserId` = ?," +
					"`FlightTypeIdent` = ?," +
					"`AircraftIdentification` = ?," +
					"`AircraftType` = ?," +
					"`TrueAirspeed` = ?," +
					"`DepartureAirportId` = ?," +
					"`DepartureTimeUTC` = ?," +
					"`CruisingAltitude` = ?," +
					"`RouteOfFlight` = ?," +
					"`DestinationAirportId` = ?," +
					"`EstTimeEnrouteHours` = ?," +
					"`EstTimeEnrouteMinutes` = ?," +
					"`Remarks` = ?," +
					"`FuelOnBoardHours` = ?," +
					"`FuelOnBoardMinutes` = ?," +
					"`AlternateAirportId` = ?," +
					"`PilotInfo` = ?," +
					"`NumberAboard` = ?," +
					"`ColorOfAircraft` = ?," +
					"`DestinationContactInfo` = ?" +
					" WHERE Id = ?";

		//@formatter:on
		return super.RunQuery(sql,
				new ArrayList<>(Arrays.asList(f.UserId, f.FlightTypeIdent, f.AircraftIdentification, f.AircraftType, f.TrueAirspeed, f.DepartureAirportId, f.DepartureTimeUTC,
						f.CruisingAltitude, f.RouteOfFlight, f.DestinationAirportId, f.EstTimeEnrouteHours, f.EstTimeEnrouteMinutes, f.Remarks, f.FuelOnBoardHours,
						f.FuelOnBoardMinutes, f.AlternateAirportId, f.PilotInfo, f.NumberAboard, f.ColorOfAircraft, f.DestinationContactInfo, f.Id)));
	}
	
	@Override
	public boolean Create(Service.Model.FlightPlan f)
	{
		//@formatter:off
		String sql = "INSERT INTO " + TableName() + " (`UserId`,`FlightTypeIdent`,`AircraftIdentification`," +
					"`AircraftType`,`TrueAirspeed`,`DepartureAirportId`,`DepartureTimeUTC`,"+
					"`CruisingAltitude`,`RouteOfFlight`,`DestinationAirportId`," +
					"`EstTimeEnrouteHours`,`EstTimeEnrouteMinutes`," +
					"`Remarks`," +
					"`FuelOnBoardHours`,`FuelOnBoardMinutes`,`AlternateAirportId`," +
					"`PilotInfo`,`NumberAboard`," +
					"`ColorOfAircraft`,`DestinationContactInfo`,`CreatedUTC`)" +
					" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, UTC_TIMESTAMP());";
		//@formatter:on
		
		// run scalar and update with generated id
		Object result = RunScalar(sql, true,
				Arrays.asList(f.UserId, f.FlightTypeIdent, f.AircraftIdentification, f.AircraftType, f.TrueAirspeed, f.DepartureAirportId, f.DepartureTimeUTC, f.CruisingAltitude,
						f.RouteOfFlight, f.DestinationAirportId, f.EstTimeEnrouteHours, f.EstTimeEnrouteMinutes, f.Remarks, f.FuelOnBoardHours, f.FuelOnBoardMinutes,
						f.AlternateAirportId, f.PilotInfo, f.NumberAboard, f.ColorOfAircraft, f.DestinationContactInfo));
		if (result != null)
		{
			f.Id = (Integer)result;
			return true;
		}
		return false;
	}
	
	// #EndRegion
	// #Region "Flight Plan Repository"
	
	/**
	 * Create flight view records based on a custom query
	 *
	 * @param sql
	 *            command to run
	 * @return List of flightview records
	 */
	private QList<Service.Model.FlightView> flightViewMap(QList<Service.Model.FlightPlan> records)
	{
		// load flight plans based on query and map to FlightView
		QList<Service.Model.FlightView> fvs = records.Select(fp ->
			{
				// map model over to the FlightView model
				Service.Model.FlightView fv = new Service.Model.FlightView();
				
				// Id
				fv.setId(fp.Id);
				
				// aircraft info
				fv.setIdent(fp.AircraftIdentification);
				fv.setType(fp.AircraftType);
				
				// airports
				fv.setSource(fp.getDepartureAirport().Code);
				fv.setDestination(fp.getDestinationAirport().Code);
				
				// times
				DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd HH:mm");
				LocalDateTime departTime = fp.DepartureTimeUTC.toLocalDateTime();
				fv.setDepart(departTime.format(timeFormat));
				fv.setArrive(departTime.plusHours(fp.EstTimeEnrouteHours).plusMinutes(fp.EstTimeEnrouteMinutes).format(timeFormat));
				
				return fv;
			});
		
		// return result
		return fvs;
	}
	
	public QList<Service.Model.FlightView> loadFlightViewAdmin(int limit)
	{
		String sql = "SELECT * FROM " + TableName() + " ORDER BY `DepartureTimeUTC` DESC LIMIT ?";
		return flightViewMap(RunMappedQuery(sql, Arrays.asList(limit == 0 ? 1000000 : limit)));
	}
	
	/**
	 * All current flights
	 *
	 * @param limit
	 *            number of current flights to load
	 * @return List of FlightViews
	 */
	public QList<Service.Model.FlightView> loadFlightView(int limit)
	{
		// query and model map
		String currentTimestamp = LocalDateTime.now(ZoneId.of("UTC")).plusHours(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		String sql = "SELECT * FROM " + TableName() + " WHERE ADDTIME(`DepartureTimeUTC`, CONCAT_WS(':',`EstTimeEnrouteHours`,`EstTimeEnrouteMinutes`)) >= ?"
				+ " ORDER BY `DepartureTimeUTC`, ADDTIME(`DepartureTimeUTC`, CONCAT_WS(':',`EstTimeEnrouteHours`,`EstTimeEnrouteMinutes`)) LIMIT ?";
		return flightViewMap(RunMappedQuery(sql, Arrays.asList(currentTimestamp, limit == 0 ? 1000000 : limit)));
	}
	
	/**
	 * Filed flights for a given user
	 *
	 * @param limit
	 *            number of current flights to load
	 * @param userId
	 *            user to load flights for
	 * @return List of FlightViews
	 */
	public QList<Service.Model.FlightView> loadFlightViewUser(int limit, int userId)
	{
		// query and model map
		String sql = "SELECT * FROM " + TableName() + " WHERE `UserId` = ?" + " ORDER BY `DepartureTimeUTC` DESC LIMIT ?";
		return flightViewMap(RunMappedQuery(sql, Arrays.asList(userId, limit == 0 ? 1000000 : limit)));
	}
	
	/**
	 * FlightPlans with specified departure airports
	 *
	 * @param departureAirportId
	 *            Airport generated Id
	 * @return FlightPlans found
	 */
	public QList<Service.Model.FlightPlan> byDepartureAirport(int departureAirportId)
	{
		String sql = "SELECT * FROM " + TableName() + " WHERE DepartureAirportId = ?";
		return RunMappedQuery(sql, Arrays.asList(departureAirportId));
	}
	
	/**
	 * FlightPlans with specified destination airport
	 *
	 * @param destinationAirportId
	 *            Airport generated Id
	 * @return FlightPlans found
	 */
	public QList<Service.Model.FlightPlan> byDestinationAirport(int destinationAirportId)
	{
		String sql = "SELECT * FROM " + TableName() + " WHERE DestinationAirportId = ?";
		return RunMappedQuery(sql, Arrays.asList(destinationAirportId));
	}
	
	/**
	 * FlightPlans with specified alternate airport
	 *
	 * @param alternateAirportId
	 *            Airport generated Id
	 * @return FlightPlans found
	 */
	public QList<Service.Model.FlightPlan> byAlternateAirport(int alternateAirportId)
	{
		String sql = "SELECT * FROM " + TableName() + " WHERE AlternateAirportId = ?";
		return RunMappedQuery(sql, Arrays.asList(alternateAirportId));
	}
	
	/**
	 * FlightPlans with specified user
	 *
	 * @param userId
	 *            user's generated Id
	 * @return FlightPlans found
	 */
	public QList<Service.Model.FlightPlan> byUserId(int userId)
	{
		String sql = "SELECT * FROM " + TableName() + " WHERE UserId = ?";
		return RunMappedQuery(sql, Arrays.asList(userId));
	}
	
	// #EndRegion
}
