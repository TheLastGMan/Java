package Data.Service;

import java.sql.SQLException;

public interface IMap<T>
{
	T MapRecord(Object[] record) throws SQLException;
}
