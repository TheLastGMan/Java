package Service.Model.DBModel;

import java.sql.Timestamp;

import Data.Service.BaseTable;

/**
 * @author rgau1
 *         User DB Model
 */
public class User extends BaseTable
{
	// public int Id;
	public String Username;		// unique
	public String FirstName;
	public String LastName;
	public String Address;
	public String City;
	public int StateId;
	public int Zip;
	public String EMail;
	public String PasswordHash;
	public byte FailedLoginAttempts;
	public boolean IsAdmin;
	public Timestamp CreatedUTC;
}
