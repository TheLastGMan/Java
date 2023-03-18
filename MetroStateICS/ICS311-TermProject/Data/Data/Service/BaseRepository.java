package Data.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import System.Linq.QList;

public abstract class BaseRepository<T extends Data.Service.BaseTable> extends DataMapper<T> implements ICrud<T>
{
	
	public abstract T MapRecordToModel(Object[] r) throws SQLException;

	@Override
	public abstract String TableName();

	@Override
	public QList<T> Table()
	{
		return MapTableToModel(TableName(), "Id", (Object[] record) -> MapRecordToModel(record));
	}

	@Override
	public T Get(int id)
	{
		String sql = "SELECT * FROM " + TableName() + " WHERE Id = ?";
		QList<T> result = RunMappedQuery(sql, new ArrayList<Object>(Arrays.asList(id)));
		return result.FirstOrDefault();
	}

	@Override
	public boolean Delete(int id)
	{
		String sql = "DELETE FROM " + TableName() + " WHERE Id = ?";
		return RunQuery(sql, new ArrayList<Object>(Arrays.asList(id)));
	}

	@Override
	public boolean Delete(T record)
	{
		return Delete(record.Id);
	}

	@Override
	public abstract boolean Update(T record);

	@Override
	public abstract boolean Create(T record);

	// #Region "Table Mapping"

	/**
	 * Map a query's records over to an object using the default mapping
	 *
	 * @param sql
	 *            command to run
	 * @return List of mapped records
	 */
	public QList<T> RunMappedQuery(String sql)
	{
		return RunMappedQuery(sql, new ArrayList<Object>());
	}

	/**
	 * Map a query's records over to an object using the default mapping
	 *
	 * @param sql
	 *            command to run
	 * @param parameters
	 *            sql safe variabled inputs
	 * @return List of mapped records
	 */
	public QList<T> RunMappedQuery(String sql, List<Object> parameters)
	{
		return MapQueryToModel(sql, parameters, (Object[] record) -> MapRecordToModel(record));
	}

	/**
	 * Run a query that returns a singular value
	 *
	 * @param sql
	 *            command to run
	 * @return Singular Object value of the first row and column of the query
	 */
	public Object RunScalar(String sql)
	{
		return RunScalar(sql, false, null);
	}

	/**
	 * Run a query that returns a singular value
	 *
	 * @param sql
	 *            command to run
	 * @return Singular Object value of the first row and column of the query
	 */
	public Object RunScalar(String sql, boolean returnIdentity)
	{
		return RunScalar(sql, returnIdentity, null);
	}

	/**
	 * Run a query that returns a singular value
	 *
	 * @param sql
	 *            command to run
	 * @param parameters
	 *            sql safe variabled inputs
	 * @return Singular Object value of the first row and column of the query
	 */
	public Object RunScalar(String sql, boolean returnIdentity, List<Object> parameters)
	{
		try
		{
			return Data.Service.DBService.RunScalar(sql, parameters, returnIdentity);
		}
		catch (SQLException e)
		{
			Data.Events.DBEventService.RaiseError(e.getMessage());
			return null;
		}
	}

	/**
	 * Run a query that return number of records effected
	 *
	 * @param sql
	 *            command to run
	 * @return number of records effected
	 */
	public boolean RunQuery(String sql)
	{
		return RunQuery(sql, null);
	}

	/**
	 * Run a query that return number of records effected using parameterized inputs
	 *
	 * @param sql
	 *            command to run
	 * @param parameters
	 *            sql safe variabled inputs
	 * @return number of records effected
	 */
	public boolean RunQuery(String sql, List<Object> parameters)
	{
		try
		{
			Data.Service.DBService.RunUpdate(sql, parameters, false);
			return true;
		}
		catch (SQLException e)
		{
			Data.Events.DBEventService.RaiseError(e.getMessage());
			return false;
		}
	}

	// #EndRegion
	// #Region "Custom Mapping"

	/**
	 * Custom mapping of database rows
	 *
	 * @param sql
	 *            command to run
	 * @param map
	 *            custom map of records
	 * @return List of mapped records
	 */
	public <U> QList<U> RunCustomMappedQuery(String sql, IMap<U> map)
	{
		return RunCustomMappedQuery(sql, null, map);
	}

	/**
	 * Custom mapping of database rows with parameterized inputs
	 *
	 * @param sql
	 *            command to run
	 * @param parameters
	 *            sql safe variabled inputs
	 * @param map
	 *            custom map of records
	 * @return List of mapped records
	 */
	public <U> QList<U> RunCustomMappedQuery(String sql, List<Object> parameters, IMap<U> map)
	{
		return new DataMapper<U>().MapQueryToModel(sql, parameters, (Object[] record) -> map.MapRecord(record));
	}

	// #EndRegion
}
