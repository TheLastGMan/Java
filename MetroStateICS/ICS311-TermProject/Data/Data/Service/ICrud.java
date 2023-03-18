package Data.Service;

import System.Linq.QList;

public interface ICrud<T extends BaseTable>
{
	/**
	 * Get the tables name
	 *
	 * @return Database table name
	 */
	String TableName();

	/**
	 * Get all entities from the table
	 *
	 * @return non null list of all entities
	 */
	QList<T> Table();

	/**
	 * Get entity by its Id
	 *
	 * @param id
	 *            unique identifier of entity
	 * @return Entity or null if not found
	 */
	T Get(int id);

	/**
	 * Delete entity by its Id
	 *
	 * @param id
	 *            unique identifier of entity
	 * @return T/F if successful
	 */
	boolean Delete(int id);

	/**
	 * Delete entity
	 *
	 * @param record
	 *            Entity
	 * @return T/F if successful
	 */
	boolean Delete(T record);

	/**
	 * Update entity
	 *
	 * @param record
	 *            Entity
	 * @return T/F if successful
	 */
	boolean Update(T record);

	/**
	 * Create entity
	 *
	 * @param record
	 *            Entity
	 * @return T/F if successful
	 */
	boolean Create(T record);
}
