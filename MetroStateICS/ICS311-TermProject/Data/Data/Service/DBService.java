package Data.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBService
{
	private static String _Server = "";
	private static String _Port = "";
	private static String _Database = "";
	private static String _Username = "";
	private static String _Password = "";

	/**
	 * Set up database required information
	 *
	 * @param server
	 * @param port
	 * @param database
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	public static void Setup(String server, int port, String database, String username, String password) throws Exception
	{
		// Validate
		ArrayList<String> errors = new ArrayList<String>();
		if (server.isEmpty())
			errors.add("server");
		if (port == 0)
			errors.add("invalid port");
		if (database.isEmpty())
			errors.add("database");
		if (username.isEmpty())
			errors.add("username");
		if (!errors.isEmpty())
			throw new Exception("Parameters required: {" + String.join(", ", errors) + "}");
		
		// Set properties
		_Server = server;
		_Port = Integer.toString(port);
		_Database = database;
		_Username = username;
		_Password = password;
	}

	/**
	 * Create a ThreadSafe connection instance
	 *
	 * @return Connection
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException
	{
		Properties connectionProps = new Properties();
		connectionProps.put("user", _Username);			// connection username
		connectionProps.put("password", _Password);		// connection password
		// connectionProps.put("connectTimeout", "30"); // timeout in 30s
		connectionProps.put("useCompression", "true"); // enable compression
		connectionProps.put("useSSL", "false"); // disable ssl, don't need it in this environment
		connectionProps.put("verifyServerCertificate", "false");// don't verify
		connectionProps.put("paranoid", "true"); // prevent exposure of sensitive data in error messages
		Connection conn = DriverManager.getConnection("jdbc:mysql://" + _Server + ":" + _Port + "/" + _Database, connectionProps);
		return conn;
	}

	/**
	 * Execute a query that returns rows
	 *
	 * @param sql
	 * @param parameters
	 * @return List of Object arrays equal to the columns selected in query
	 *         Software is responsible to know what are in each column and type
	 * @throws SQLException
	 */
	public static ArrayList<Object[]> RunQuery(String sql, List<Object> parameters) throws SQLException
	{
		// check for null parameters
		if (parameters == null)
			parameters = new ArrayList<>();
		
		// check for ending statement
		if (!sql.endsWith(";"))
			sql += ";";
		
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		try (Connection conn = getConnection())
		{
			try (PreparedStatement stmt = conn.prepareStatement(sql))
			{
				// add parameters
				for (int i = 0; i < parameters.size(); i++)
					stmt.setObject(i + 1, parameters.get(i));
				
				// execute query
				ResultSet resultSet = stmt.executeQuery();
				if (resultSet != null)
				{
					// load result information
					ResultSetMetaData md = resultSet.getMetaData();
					int totalColumn = md.getColumnCount();

					// map ResultSet over to an DataTable of object arrays
					// since we lose the ResultSet when the statement closes
					while (resultSet.next())
					{
						Object[] row = new Object[totalColumn];
						for (int i = 0; i < totalColumn; i++)
							row[i] = resultSet.getObject(i + 1);
						result.add(row);
					}
					
					// close resultSet
					if (!resultSet.isClosed())
						resultSet.close();
				}
			}
		}
		catch (Exception ex)
		{
			Data.Events.DBEventService.RaiseError(ex.getMessage());
			throw ex;
		}
		
		// return result
		return result;
	}

	/**
	 * Execute a query that returns a single value
	 *
	 * @param sql
	 * @param parameters
	 * @return Nullable singular value, software is responsible for knowing what type it is
	 * @throws SQLException
	 */
	public static Object RunScalar(String sql, List<Object> parameters, boolean returnIdentity) throws SQLException
	{
		// check for null parameters
		if (parameters == null)
			parameters = new ArrayList<>();
		
		// check for ending statement
		if (!sql.endsWith(";"))
			sql += ";";
		
		// if we are returning an identity, we actually want an update command
		if (returnIdentity)
			return RunUpdate(sql, parameters, returnIdentity);
		
		Object result = null;
		try (Connection con = getConnection())
		{
			try (PreparedStatement stmt = con.prepareStatement(sql))
			{
				// add parameters
				for (int i = 0; i < parameters.size(); i++)
					stmt.setObject(i + 1, parameters.get(i));
				
				// execute query
				ResultSet resultSet = stmt.executeQuery();
				if (resultSet != null && resultSet.next())
					result = resultSet.getObject(1);
			}
		}
		catch (Exception ex)
		{
			Data.Events.DBEventService.RaiseError(ex.getMessage());
			throw ex;
		}

		// return result
		return result;
	}

	/**
	 * Execute a query that returns numbers of records effected
	 *
	 * @param sql
	 * @param parameters
	 * @return Count of effected records
	 * @throws SQLException
	 */
	public static int RunUpdate(String sql, List<Object> parameters, boolean returnIdentity) throws SQLException
	{
		// check for null parameters
		if (parameters == null)
			parameters = new ArrayList<>();
		
		// check for ending statement
		if (!sql.endsWith(";"))
			sql += ";";
		
		int result = -1;
		try (Connection conn = getConnection())
		{
			try (PreparedStatement stmt = (returnIdentity ? conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) : conn.prepareStatement(sql)))
			{
				// add parameters
				for (int i = 0; i < parameters.size(); i++)
					stmt.setObject(i + 1, parameters.get(i));
				
				// execute query
				int rowsChanged = stmt.executeUpdate();
				if (!returnIdentity)
					return rowsChanged;
				
				// want to return the identity, load generated key
				if (rowsChanged == 0)
					throw new SQLException("No rows changed");
				
				try (ResultSet genKeys = stmt.getGeneratedKeys())
				{
					if (genKeys.next())
						result = genKeys.getInt(1);
					else
						throw new SQLException("No Identity created");
				}
			}
		}
		catch (Exception ex)
		{
			Data.Events.DBEventService.RaiseError(ex.getMessage());
			throw ex;
		}

		// return result
		return result;
	}
}
