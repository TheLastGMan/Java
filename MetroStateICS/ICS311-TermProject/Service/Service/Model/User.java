package Service.Model;

import java.util.ArrayList;
import java.util.List;

import System.Linq.QList;

public class User extends Service.Model.DBModel.User
{
	/* Repository */

	private static final Service.Repository.FlightPlan _FlightPlanRepository = new Service.Repository.FlightPlan();
	private static final Service.Repository.State _StateRepository = new Service.Repository.State();

	/* Navigation */

	public Service.Model.State getState()
	{
		return _StateRepository.Get(StateId);
	}

	public QList<FlightPlan> getFlightPlans()
	{
		return _FlightPlanRepository.byUserId(Id);
	}

	/* Methods */

	public List<String> IsValid()
	{
		ArrayList<String> errors = new ArrayList<>();

		// Username(32)
		if (Username.isEmpty())
			errors.add("Username is required");
		else if (Username.length() > 32)
			errors.add("Username must be shorter than 33 characters");
		
		// FirstName(64)
		if (FirstName.isEmpty())
			errors.add("First Name is required");
		else if (FirstName.length() > 64)
			errors.add("First Name must be shorter than 65 characters");
		
		// LastName(64)
		if (LastName.isEmpty())
			errors.add("Last Name is required");
		else if (LastName.length() > 64)
			errors.add("Last Name must be shorter than 65 characters");
		
		// Address(128)
		if (Address.isEmpty())
			errors.add("Address is required");
		else if (Address.length() > 128)
			errors.add("Address must be shorter than 129 characters");
		
		// City(64)
		if (City.isEmpty())
			errors.add("City is required");
		else if (City.length() > 64)
			errors.add("City must be shorter than 129 characters");
		
		// StateId(int), check for valid id
		if (StateId < 1)
			errors.add("State is invalid");
		
		// Zip(int), no need to validate, set up spinner for integers [0, 99999]

		// EMail(128)
		if (EMail.isEmpty())
			errors.add("E-Mail is required");
		else if (EMail.length() > 128)
			errors.add("E-Mail must be shorter than 129 characters");
		else if (!(EMail.contains("@") || EMail.contains(".")))
			errors.add("E-Mail format is invalid");
		
		return errors;
	}

}
