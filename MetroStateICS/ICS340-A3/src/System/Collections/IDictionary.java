/**
 *
 */
package System.Collections;

import System.Collections.Generic.KeyValuePair;

/**
 * @author Ryan Gau
 * @version 1.0
 */
public abstract class IDictionary<TKey, TValue> extends ICollection<KeyValuePair<TKey, TValue>>
{
	// Interfaces are not serializable
	// The Item property provides methods to read and edit entries
	// in the Dictionary.
	public abstract TValue Get(TKey key) throws Exception;

	public abstract void Set(TKey key, TValue value) throws Exception;

	// Returns a collections of the keys in this dictionary.
	public abstract ICollection<TKey> Keys();

	// Returns a collections of the values in this dictionary.
	public abstract ICollection<TValue> Values();

	// Returns whether this dictionary contains a particular key.
	public abstract boolean ContainsKey(TKey key);

	// Adds a key-value pair to the dictionary.
	public abstract void Add(TKey key, TValue value);

	// Removes a particular key from the dictionary.
	public abstract boolean Remove(TKey key);

	public abstract TValue TryGetValue(TKey key);
}
