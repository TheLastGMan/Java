package Service.Model;

import java.util.ArrayList;
import java.util.List;

import System.Linq.QList;

public class State extends Service.Model.DBModel.State
{
	/* Repository */

	private static final Service.Repository.Airport _AirportRepository = new Service.Repository.Airport();
	private static final Service.Repository.User _UserRepository = new Service.Repository.User();

	/* Navigation */

	public QList<Service.Model.Airport> Airports()
	{
		return _AirportRepository.byState(Id);
	}

	public QList<Service.Model.User> Users()
	{
		return _UserRepository.byState(Id);
	}

	/* Methods */

	public List<String> IsValid()
	{
		ArrayList<String> errors = new ArrayList<>();

		// Abbreviation (3)
		if (Abbreviation.isEmpty())
			errors.add("Abbreviation is required.");
		else if (Abbreviation.length() > 3)
			errors.add("Abbreviation must be less than 4 characters");
		
		// Name (32)
		if (Name.isEmpty())
			errors.add("Name is required");
		else if (Name.length() > 32)
			errors.add("Name must be less than 33 characters");
		
		return errors;
	}
}
