/**
 *
 */
package UI;

/**
 * Product Information
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class Product {
	private int _Id; // custom field not in file, used to quickly compare
					 // instances
	private final String _Name;
	private final long _UnitValue;
	private final int _UnitWeight;

	// public int Quantity;
	// this was specified in the file, we won't use it so
	// we don't have to track quantities through the iterations
	
	public Product(int id, String name, long unitValue, int unitWeight) throws Exception {
		// validate
		if (name == null) {
			throw new Exception("name must not be null");
		}
		if (unitWeight <= 0) {
			throw new Exception("unitWeight must be greater than zero");
		}

		// soft validate value
		if (unitValue < 0) {
			unitValue = 0;
		}
		
		// assign values
		_Id = id;
		_Name = name;
		_UnitValue = unitValue;
		_UnitWeight = unitWeight;
	}

	/**
	 * Id of product
	 *
	 * @return Int
	 */
	public int Id() {
		return _Id;
	}
	
	/**
	 * Name of product
	 *
	 * @return String
	 */
	public String Name() {
		return _Name;
	}
	
	/**
	 * Unit Value of product
	 *
	 * @return Long
	 */
	public long UnitValue() {
		return _UnitValue;
	}
	
	/**
	 * Unit Weight of product
	 *
	 * @return Long
	 */
	public int UnitWeight() {
		return _UnitWeight;
	}
	
	/**
	 * check if another product equals this one
	 *
	 * @param other
	 * @return
	 */
	public boolean Equals(Product other) {
		return Id() == other.Id();
	}
}
