package Data.Service;

import java.util.ArrayList;
import java.util.List;

import System.Linq.QList;

public class DataMapper<T>
{
	public QList<T> MapTableToModel(String table, String identifier, IMap<T> map)
	{
		String sql = "SELECT * FROM " + table + " ORDER BY " + identifier + " DESC;";
		return MapQueryToModel(sql, map);
	}

	public QList<T> MapQueryToModel(String sql, IMap<T> map)
	{
		return MapQueryToModel(sql, null, map);
	}

	public QList<T> MapQueryToModel(String sql, List<Object> parameters, IMap<T> map)
	{
		QList<T> result = new QList<>(new ArrayList<>());
		try
		{
			// run query and map
			ArrayList<Object[]> rs = Data.Service.DBService.RunQuery(sql, parameters);

			// map objects
			ArrayList<T> rsmap = new ArrayList<T>();
			for (Object[] o : rs)
				rsmap.add(map.MapRecord(o));
			
			// return mapped result
			result = new QList<T>(rsmap);
		}
		catch (Exception e)
		{
			Data.Events.DBEventService.RaiseError(e.getMessage());
		}
		return result;
	}
}
