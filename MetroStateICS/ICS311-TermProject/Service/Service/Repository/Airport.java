package Service.Repository;

import java.sql.SQLException;
import java.util.Arrays;

import Data.Service.BaseRepository;
import System.Linq.QList;

public class Airport extends BaseRepository<Service.Model.Airport>
{
	
	@Override
	public Service.Model.Airport MapRecordToModel(Object[] r) throws SQLException
	{
		Service.Model.Airport model = new Service.Model.Airport();
		model.Id = (Integer)r[0];
		model.Code = (String)r[1];
		model.City = (String)r[2];
		model.StateId = (Integer)r[3];
		model.Elevation = (Integer)r[4];
		model.TowerOnField = (Boolean)r[5];
		model.Description = (String)r[6];
		return model;
	}

	@Override
	public String TableName()
	{
		return "`Airport`".toLowerCase();
	}

	@Override
	public boolean Update(Service.Model.Airport a)
	{
		//@formatter:off
		String sql = "UPDATE " + TableName() +
					" SET `City` = ?, `StateId` = ?, " +
					"`Elevation` = ?, `TowerOnField` = ?, `Description` = ?" +
					" WHERE `Id` = ?";
		//@formatter:on
		return RunQuery(sql, Arrays.asList(a.City, a.StateId, a.Elevation, a.TowerOnField, a.Description, a.Id));
	}

	@Override
	public boolean Create(Service.Model.Airport a)
	{
		//@formatter:off
		String sql = "INSERT INTO " + TableName() + " (`Code`, `City`, `StateId`, `Elevation`, `TowerOnField`, `Description`)" +
					" VALUES (?, ?, ?, ?, ?, ?);";
		//@formatter:on

		// update model with created id
		Object result = RunScalar(sql, true, Arrays.asList(a.Code, a.City, a.StateId, a.Elevation, a.TowerOnField, a.Description));
		if (result != null)
		{
			a.Id = (Integer)result;
			return true;
		}
		return false;
	}

	// #Region "Airport Repository"

	/**
	 * Locate an airport by its unique code
	 *
	 * @param code
	 *            NFDC airport identifier (ex. K21D)
	 * @return Airport if found, otherwise null
	 */
	public Service.Model.Airport byCode(String code)
	{
		String sql = "SELECT * FROM " + TableName() + " WHERE `Code` = ?";
		QList<Service.Model.Airport> result = RunMappedQuery(sql, Arrays.asList(code.toUpperCase()));
		return result.FirstOrDefault();
	}

	/**
	 * Find airports associated with a given state
	 *
	 * @param stateId
	 *            State Id
	 * @return Collection of airports
	 */
	public QList<Service.Model.Airport> byState(int stateId)
	{
		String sql = "SELECT * FROM " + TableName() + " WHERE `StateId` = ?";
		QList<Service.Model.Airport> result = RunMappedQuery(sql, Arrays.asList(stateId));
		return result;
	}

	public QList<Service.Model.AirportSummary> SummaryView()
	{
		String sql = "SELECT * FROM " + "`AirportSummaryView`".toLowerCase();
		QList<Service.Model.AirportSummary> result = RunCustomMappedQuery(sql, (v) ->
			{
				Service.Model.AirportSummary model = new Service.Model.AirportSummary();
				model.Id = (Integer)v[0];
				model.Code = (String)v[1];
				model.City = (String)v[2];
				model.StateAbbreviation = (String)v[3];
				return model;
			});
		return result;
	}

	// #EndRegion
}
