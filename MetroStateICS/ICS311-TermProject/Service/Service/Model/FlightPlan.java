package Service.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightPlan extends Service.Model.DBModel.FlightPlan
{
	/* Repository */

	private static final Service.Repository.Airport _AirportRepository = new Service.Repository.Airport();
	private static final Service.Repository.User _UserRepository = new Service.Repository.User();

	/* Navigation */

	public Airport getDepartureAirport()
	{
		return _AirportRepository.Get(DepartureAirportId);
	}

	public Airport getDestinationAirport()
	{
		return _AirportRepository.Get(DestinationAirportId);
	}

	public Airport getAlternateAirport()
	{
		return _AirportRepository.Get(AlternateAirportId);
	}

	public User getUser()
	{
		return _UserRepository.Get(UserId);
	}

	/* Methods */

	public List<String> IsValid()
	{
		ArrayList<String> errors = new ArrayList<>();

		// UserId(int)
		if (UserId < 1)
			errors.add("Invalid User Filing Plan");
		
		// FlightTypeIdent(byte)
		if (FlightTypeIdent < 0 || FlightTypeIdent > 2)
			errors.add("Invalid Flight Type, must be: {VFR, IFR}");
		
		// AircraftIdentification(6)
		if (AircraftIdentification.isEmpty())
			errors.add("Identification is Required");
		else if (AircraftIdentification.length() > 12)
			errors.add("Identification must be less than 13 characters");
		
		// AircraftType(6)
		if (AircraftType.isEmpty())
			errors.add("Aircraft Type is Required");
		else if (AircraftType.length() > 6)
			errors.add("Aircraft Type must be less than 7 characters");
		
		// TrueAirspeed(short)
		if (TrueAirspeed < 0 || TrueAirspeed > 1000)
			errors.add("Airspeed must be between 0 and 1000, how fast are you flying again?");
		
		// DepartureAirportId(int)
		if (DepartureAirportId < 1)
			errors.add("Invalid Departure Point");
		
		// DepartureTimeUTC(DateTime)
		if (DepartureTimeUTC.toLocalDateTime().compareTo(LocalDateTime.now()) < 0)
			errors.add("Departure time must be in the future");
		
		// CrusingAltitude(short)
		if (CruisingAltitude < 0 || CruisingAltitude > 1000)
			errors.add("Cruising altitude must be between 0 and 1000, how high are you flying again?");
		
		// RouteOfFlight(1024)
		if (RouteOfFlight.isEmpty())
			errors.add("Route of Flight is Required");
		else if (RouteOfFlight.length() > 1024)
			errors.add("Route of flight must be less than 1025 characters");
		
		// DestinationAirportId(int)
		if (DestinationAirportId < 1)
			errors.add("Invalid Destination Airport");
		// EstTimeEnrouteHours(byte)
		if (EstTimeEnrouteHours < 0 || EstTimeEnrouteHours > 23)
			errors.add("Time Enroute Hours must be between 0 and 23");
		
		// EstTimeEnrouteMinutes(byte)
		if (EstTimeEnrouteMinutes < 0 || EstTimeEnrouteMinutes > 59)
			errors.add("Time Enroute Mins must be between 0 and 59");
		
		// Remarks(1024)
		if (Remarks.length() > 1024)
			errors.add("Remarks must be less than 1025 characters");
		
		// FuelOnBoardHours(byte)
		if (FuelOnBoardHours < 0 || FuelOnBoardHours > 23)
			errors.add("Fuel on Board Hours must be between 0 and 23");
		
		// FuelOnBoardMinutes(byte)
		if (FuelOnBoardMinutes < 0 || FuelOnBoardMinutes > 23)
			errors.add("Fuel on Board Mins must be between 0 and 23");
		
		// AlternateAirportId(int)
		if (AlternateAirportId < 1)
			errors.add("Invalid Alternate Airport");
		
		// PilotInfo(128)
		if (PilotInfo.isEmpty())
			errors.add("Pilot Info is Required");
		else if (PilotInfo.length() > 128)
			errors.add("Pilot Info must be less than 129 characters");
		
		// NumberAboard(short)
		if (NumberAboard < 1 || NumberAboard > 1000)
			errors.add("Number Aboard must be between 1 and 1000");
		
		// ColorOfAircraft(16)
		if (ColorOfAircraft.isEmpty())
			errors.add("Color of Aircraft is Required");
		else if (ColorOfAircraft.length() > 16)
			errors.add("Color of Aircraft must be less than 17 characters");
		
		// DestinationContactInfo(128)
		if (DestinationContactInfo.isEmpty())
			errors.add("Destination Contact is Required");
		else if (DestinationContactInfo.length() > 128)
			errors.add("Destination Contact must be less than 129 characters");
		
		// CreatedUTC

		return errors;
	}
}
