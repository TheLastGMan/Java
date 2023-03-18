/**
 *
 */
package System.Collections;

import java.io.Serializable;
import java.util.*;

import System.Linq.QList;

/**
 * Represents a collection of keys and values.
 * Based on Microsoft's System.Collections.Generic.Dictionary
 *
 * @author Ryan Gau
 * @version 1.0
 * @param TKey
 *            The type of the keys in the dictionary.
 * @param TValue
 *            The type of the values in the dictionary.
 */
public class Dictionary<TKey extends Comparable<TKey>, TValue>
		implements Iterable<KeyValuePair<TKey, TValue>>, Serializable {
	private static final long serialVersionUID = -5511211048713190342L;
	private HashMap<TKey, TValue> _hashtable = null;
	
	public Dictionary() {
		this(0);
	}
	
	public Dictionary(int capacity) {
		// validate
		if (capacity < 0) {
			capacity = 0;
		}
		
		// setup
		_hashtable = new HashMap<>(capacity);
	}
	
	/**
	 * Gets the number of key/value pairs contained in the Dictionary(Of TKey,
	 * TValue).
	 *
	 * @return The number of key/value pairs contained in the Dictionary(Of
	 *         TKey, TValue).
	 */
	public int Count() {
		return _hashtable.size();
	}
	
	/**
	 * Gets a collection containing the keys in the Dictionary(Of TKey, TValue).
	 *
	 * @return collection containing the keys in the Dictionary(Of TKey,
	 *         TValue).
	 */
	public QList<TKey> Keys() {
		return new QList<>(_hashtable.keySet());
	}
	
	/**
	 * Gets a collection containing the values in the Dictionary(Of TKey,
	 * TValue).
	 *
	 * @return collection containing the values in the Dictionary(Of TKey,
	 *         TValue).
	 */
	public QList<TValue> Values() {
		return new QList<>(_hashtable.values());
	}
	
	/**
	 * Gets the value associated with the specified key.
	 *
	 * @param key
	 *            The key of the value to get
	 * @return value associated with the specified key. if the key is not found,
	 *         and an exception is thrown
	 * @throws Exception
	 *             Error
	 */
	public TValue Get(TKey key) throws Exception {
		TValue value = _hashtable.get(key);
		if (value == null) {
			throw new Exception("Key not found");
		}
		
		return _hashtable.get(key);
	}
	
	/**
	 * Adds the specified key and value to the dictionary.
	 *
	 * @param key
	 *            The key of the element to add.
	 * @param value
	 *            The value of the element to add. The value can be null for
	 *            reference types.
	 * @throws Exception
	 */
	public void Add(TKey key, TValue value) throws Exception {
		if (key == null) {
			throw new Exception("Key must not be null");
		}
		if (ContainsKey(key)) {
			throw new Exception("Duplicate key");
		}
		
		// add to hash
		_hashtable.put(key, value);
	}
	
	/**
	 * Removes all keys and values from the Dictionary(Of TKey, TValue).
	 */
	public void Clear() {
		_hashtable.clear();
	}
	
	/**
	 * Determines whether the Dictionary(Of TKey, TValue) contains the specified
	 * key.
	 *
	 * @param key
	 *            The key to locate
	 * @return T/F if the dictionary contains the specified key
	 */
	public boolean ContainsKey(TKey key) {
		return _hashtable.containsKey(key);
	}
	
	/**
	 * Determines whether the Dictionary(Of TKey, TValue) contains a specific
	 * value.
	 *
	 * @param value
	 *            The value to locate, can be null for reference types
	 * @return T/F if the dictionary contains an element with the specified
	 *         value
	 */
	public boolean ContainsValue(TValue value) {
		return _hashtable.containsValue(value);
	}
	
	/**
	 * @return Creates a shallow copy of the current Object
	 * @throws Exception
	 *             Error
	 */
	@SuppressWarnings("unchecked")
	public Dictionary<TKey, TValue> MemberwiseClone() throws Exception {
		return (Dictionary<TKey, TValue>)super.clone();
	}
	
	/**
	 * Removes the value with the specified key from the Dictionary(Of TKey,
	 * TValue).
	 *
	 * @param key
	 *            The key of the element to remove.
	 */
	public void Remove(TKey key) {
		_hashtable.remove(key);
	}
	
	/**
	 * Gets the value associated with the specified key.
	 *
	 * @param key
	 *            The key of the value to get.
	 * @return returns the value for the key, if the key is found;
	 *         otherwise, returns null
	 */
	public TValue TryGetValue(TKey key) {
		return _hashtable.get(key);
	}
	
	/**
	 * Creates an enumerable out of this hash collection
	 *
	 * @return QList(Of KeyValuePair(Of TKey, TValue))
	 */
	public QList<KeyValuePair<TKey, TValue>> Enumerable() {
		return new QList<>(_hashtable.entrySet()).Select(f -> new KeyValuePair<>(f.getKey(), f.getValue()));
	}
	
	/**
	 * Returns an iterator that can go through the Dictionary(Of TKey, TValue)
	 */
	@Override
	public Iterator<KeyValuePair<TKey, TValue>> iterator() {
		return new DictionaryIterator();
	}
	
	private class DictionaryIterator implements Iterator<KeyValuePair<TKey, TValue>> {
		private Iterator<TKey> baseIterator = _hashtable.keySet().iterator();
		
		/*
		 * (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			return baseIterator.hasNext();
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		@Override
		public KeyValuePair<TKey, TValue> next() {
			TKey current = baseIterator.next();
			return new KeyValuePair<>(current, TryGetValue(current));
		}
	}
}
