package Service.Model.DBModel;

import Data.Service.BaseTable;

/**
 * @author rgau1
 */
public class Airport extends BaseTable
{
	// public int Id;
	public String Code; // unique
	public String City;
	public int StateId;
	public int Elevation;
	public boolean TowerOnField;
	public String Description;
}
