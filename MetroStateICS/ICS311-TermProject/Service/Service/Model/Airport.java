package Service.Model;

import java.util.ArrayList;
import java.util.List;

import System.Linq.QList;

public class Airport extends Service.Model.DBModel.Airport
{
	/* Repository */

	private static final Service.Repository.FlightPlan _FlightPlanRepository = new Service.Repository.FlightPlan();
	private static final Service.Repository.State _StateRepository = new Service.Repository.State();

	/* Navigation */

	public Service.Model.State getState()
	{
		return _StateRepository.Get(StateId);
	}

	public ArrayList<FlightPlan> getFlightPlans()
	{
		ArrayList<FlightPlan> fps = new ArrayList<FlightPlan>();
		for (FlightPlan fp : _FlightPlanRepository.Table())
			if (fp.DepartureAirportId == Id || fp.DestinationAirportId == Id || fp.AlternateAirportId == Id)
				fps.add(fp);
		return fps;
	}

	public QList<FlightPlan> getFlightDepartures()
	{
		return _FlightPlanRepository.byDepartureAirport(Id);
	}

	public QList<FlightPlan> getFlightPlanDestinations()
	{
		return _FlightPlanRepository.byDepartureAirport(Id);
	}

	public QList<FlightPlan> getFlightPlanAlternates()
	{
		return _FlightPlanRepository.byAlternateAirport(Id);
	}

	/* Computed Fields */

	public String airportHeader()
	{
		return Code + " (" + Description + ")";
	}

	/* Methods */

	public List<String> IsValid()
	{
		ArrayList<String> errors = new ArrayList<>();

		// Code(4)
		if (Code.isEmpty())
			errors.add("Code is required");
		else if (Code.length() > 5)
			errors.add("Code must be shorter than 5 characters");
		
		// City(32)
		if (City.isEmpty())
			errors.add("City is required");
		else if (City.length() > 32)
			errors.add("City must be shorter than 33 characters");
		
		// State(int), check for valid index
		if (StateId < 1)
			errors.add("State is invalid");
		
		// Elevation [-1000, 10000]
		if (Elevation < -1000 || Elevation > 10000)
			errors.add("Elevation must be between -1000 and 10,000");
		
		// Description(128)
		if (Description.isEmpty())
			errors.add("Description is required");
		else if (Description.length() > 128)
			errors.add("Description must be shorter than 129 characters");
		
		return errors;
	}
}
