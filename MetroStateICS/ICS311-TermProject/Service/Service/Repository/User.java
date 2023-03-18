package Service.Repository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import Data.Service.BaseRepository;
import Service.Model.UserSummary;
import Service.Security.ICryptoEngine;
import Service.Security.SHA256;
import System.Linq.QList;

public class User extends BaseRepository<Service.Model.User>
{
	
	@Override
	public Service.Model.User MapRecordToModel(Object[] r) throws SQLException
	{
		Service.Model.User model = new Service.Model.User();
		model.Id = (Integer)r[0];
		model.Username = (String)r[1];
		model.FirstName = (String)r[2];
		model.LastName = (String)r[3];
		model.Address = (String)r[4];
		model.City = (String)r[5];
		model.StateId = (Integer)r[6];
		model.Zip = (Integer)r[7];
		model.EMail = (String)r[8];
		model.PasswordHash = (String)r[9];
		model.FailedLoginAttempts = Byte.parseByte(((Integer)r[10]).toString());
		model.IsAdmin = (Boolean)r[11];
		model.CreatedUTC = (Timestamp)r[12];
		return model;
	}
	
	@Override
	public String TableName()
	{
		return "`User`".toLowerCase();
	}
	
	@Override
	public boolean Update(Service.Model.User u)
	{
		try
		{
			// do not update Username, it is unique per user
			// do not update PasswordHash, needs special handling
			//@formatter:off
		    String sql = "UPDATE " + TableName() +
			    	" SET `FirstName` = ?, `LastName` = ?, `Address` = ?, `City` = ?, `StateId` = ?, `Zip` = ?, `EMail` = ?, `FailedLoginAttempts` = ?, `IsAdmin` = ?" +
			    	" WHERE `Id` = ?";
		    //@formatter:on
			
			// run update with parameters
			return RunQuery(sql, Arrays.asList(u.FirstName, u.LastName, u.Address, u.City, u.StateId, u.Zip, u.EMail, u.FailedLoginAttempts, u.IsAdmin, u.Id));
		}
		catch (Exception e)
		{
			Data.Events.DBEventService.RaiseError(e.getMessage());
			return false;
		}
	}
	
	/**
	 * Create a new user
	 * assumes password has already been hashed
	 *
	 * @param u
	 *            User to create
	 * @return T/F if created
	 */
	@Override
	public boolean Create(Service.Model.User u)
	{
		//@formatter:off
		String sql = "INSERT INTO " + TableName() + " (`Username`,`FirstName`,`LastName`,`Address`,`City`,`StateId`,`Zip`,`EMail`,`PasswordHash`,`FailedLoginAttempts`,`IsAdmin`, `CreatedUTC`) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, UTC_TIMESTAMP());";
		//@formatter:on
		
		// run scalar and update with generated id
		Object result = RunScalar(sql, true,
				Arrays.asList(u.Username, u.FirstName, u.LastName, u.Address, u.City, u.StateId, u.Zip, u.EMail, u.PasswordHash, u.FailedLoginAttempts, u.IsAdmin));
		if (result != null)
		{
			u.Id = (Integer)result;
			return true;
		}
		return false;
	}
	
	// #Region "User Repository"
	
	private static final ICryptoEngine _SecurityService = new SHA256();
	
	/**
	 * User summary view
	 *
	 * @return List of User Summaries
	 */
	public ArrayList<UserSummary> UserSummary()
	{
		String sql = "SELECT * FROM " + TableName() + " ORDER BY `FirstName`, `LastName`";
		QList<Service.Model.User> result = RunMappedQuery(sql);
		ArrayList<UserSummary> userSummary = result.Select(user ->
			{
				UserSummary summary = new UserSummary();
				summary.PilotId = user.Id;
				summary.FirstName = user.FirstName;
				summary.LastName = user.LastName;
				summary.Username = user.Username;
				return summary;
			}).ToList();
		return userSummary;
	}
	
	/**
	 * Create a new user with the specified un-encrypted password
	 *
	 * @param user
	 *            User to create
	 * @param password
	 *            un-encrypted password for user
	 * @return T/F if created
	 */
	public boolean Create(Service.Model.User user, String password)
	{
		user.PasswordHash = hashPassword(user.Username, password);
		return Create(user);
	}
	
	/**
	 * Generate the user's encrypted password
	 *
	 * @param username
	 *            user's unique login
	 * @param password
	 *            un-encrypted password
	 * @return hashed password
	 */
	private String hashPassword(String username, String password)
	{
		return Base64.getEncoder().encodeToString(_SecurityService.Encrypt(username + "|" + password));
	}
	
	/**
	 * Checks if the user is locked out after so many invalid tries
	 *
	 * @param invalidTries
	 *            invalid login tries
	 * @return T/F if locked out
	 */
	private boolean isLockedOut(int invalidTries)
	{
		return invalidTries >= 3;
	}
	
	/**
	 * Update a user's password based on their unique login
	 *
	 * @param user
	 *            user's unique login
	 * @param password
	 *            new un-encrypted password
	 * @return T/F if update was successful
	 */
	public boolean UpdatePassword(Service.Model.User user, String password)
	{
		String sql = "UPDATE " + TableName() + " SET PasswordHash = ? WHERE Id = ?";
		String newPassword = hashPassword(user.Username, password);
		return RunQuery(sql, Arrays.asList(newPassword, user.Id));
	}
	
	/**
	 * Checks for a valid login combination
	 *
	 * @param username
	 *            user's unique login
	 * @param password
	 *            account password
	 * @return 0 = failed, 1 = success, 2 = not found, 3 = error
	 */
	public int validLoginState(String username, String password)
	{
		// find user by username
		Service.Model.User user = byUsername(username);
		
		// exit if not found
		if (user == null)
			return 0;
		
		// check invalid logins for lock out conditions
		if (isLockedOut(user.FailedLoginAttempts))
			return 3;
		
		// found, check password and act
		String passHash = hashPassword(username, password);
		if (passHash.equals(user.PasswordHash))
		{
			// valid user, update failed login attempts
			user.FailedLoginAttempts = 0;
			Update(user);
			return 1;
		}
		
		// invalid, update failed login attempts
		user.FailedLoginAttempts += 1;
		Update(user);
		return 0;
	}
	
	/**
	 * Find user by their username
	 *
	 * @param username
	 *            unique login
	 * @return User if found, otherwise null
	 */
	public Service.Model.User byUsername(String username)
	{
		String sql = "SELECT * FROM " + TableName() + " WHERE `Username` = ?;";
		
		// run query with parameters
		QList<Service.Model.User> result = RunMappedQuery(sql, Arrays.asList(username));
		return result.FirstOrDefault();
	}
	
	public QList<Service.Model.User> byState(int stateId)
	{
		String sql = "SELECT * FROM " + TableName() + " WHERE `StateId` = ?";
		QList<Service.Model.User> result = RunMappedQuery(sql, Arrays.asList(stateId));
		return result;
	}
	
	// #EndRegion
}
