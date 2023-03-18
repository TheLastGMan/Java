/**
 *
 */
package System.Collections.Generic;

import java.io.Serializable;

/**
 * Defines a key/value pair that can be set or retrieved.
 * Based on Microsoft's System.Collections.Generic.KeyValuePair
 *
 * @author Ryan Gau
 * @version 1.0
 * @param TKey
 *            The type of the key
 * @param TValue
 *            The type of the value
 */
public class KeyValuePair<TKey, TValue> implements Serializable
{
	private static final long serialVersionUID = -2935571175134350365L;
	private TKey key;
	private TValue value;

	/**
	 * Initializes a new instance of the KeyValuePair<TKey, TValue> structure with the specified key and value.
	 *
	 * @param key
	 *            The object defined in each key/value pair.
	 * @param value
	 *            The definition associated with key.
	 */
	public KeyValuePair(TKey key, TValue value)
	{
		this.key = key;
		this.value = value;
	}

	/**
	 * Gets the key in the key/value pair
	 *
	 * @return A TKey that is the key of the KeyValuepair(Of TKey, TValue)
	 */
	public TKey Key()
	{
		return key;
	}

	/**
	 * Gets the value in the key/value pair.
	 *
	 * @return A TValue that is the value of the KeyValuePair(Of TKey, TValue).
	 */
	public TValue Value()
	{
		return value;
	}

	/**
	 * Returns a string representation of the KeyValuePair(Of TKey, TValue), using the string representations of the key
	 * and value.
	 * 
	 * @return A string representation of the KeyValuePair(Of TKey, TValue), which includes the string representations
	 *         of the key and value.
	 */
	@Override
	public String toString()
	{
		String output = "[";
		if (key != null)
			output += key.toString();
		output += ", ";
		if (value != null)
			output += value.toString();
		output += "]";
		return output;
	}
}
