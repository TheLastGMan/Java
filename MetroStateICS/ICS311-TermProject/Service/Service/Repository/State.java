package Service.Repository;

import java.sql.SQLException;
import java.util.Arrays;

import Data.Service.BaseRepository;

public class State extends BaseRepository<Service.Model.State>
{
	
	@Override
	public Service.Model.State MapRecordToModel(Object[] r) throws SQLException
	{
		Service.Model.State state = new Service.Model.State();
		state.Id = (Integer)r[0];
		state.Abbreviation = (String)r[1];
		state.Name = (String)r[2];
		return state;
	}

	@Override
	public String TableName()
	{
		return "`State`".toLowerCase();
	}

	@Override
	public boolean Update(Service.Model.State s)
	{
		try
		{
			// do not update Username, it is unique per user
			// do not update PasswordHash, needs special handling
			//@formatter:off
		    String sql = "UPDATE " + TableName() +
			    	" SET `Abbreviation` = ?, `Name` = ?" +
			    	" WHERE `Id` = ?";
		    //@formatter:on

			// run update with parameters
			return RunQuery(sql, Arrays.asList(s.Abbreviation, s.Name, s.Id));
		}
		catch (Exception e)
		{
			Data.Events.DBEventService.RaiseError(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean Create(Service.Model.State s)
	{
		//@formatter:off
		String sql = "INSERT INTO " + TableName() + " (`Abbreviation`, `Name`) " +
					"VALUES (?, ?);";
		//@formatter:on

		// run scalar and update with generated id
		Object result = RunScalar(sql, true, Arrays.asList(s.Abbreviation, s.Name));
		if (result != null)
		{
			s.Id = (Integer)result;
			return true;
		}
		return false;
	}

	/* Methods */

	public long AbbreviationCount(String abbreviation)
	{
		String sql = "SELECT COUNT(Id) FROM " + TableName() + " WHERE `Abbreviation` = ?;";
		long result = (long)RunScalar(sql, false, Arrays.asList(abbreviation));
		return result;
	}
}
