import java.io.Serializable;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.*;

/**
 *
 */

/**
 * Concurrent Queue
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class Queue<U> implements java.util.Queue<U>, Serializable {
	private static final long serialVersionUID = 8002619207086257691L;
	private final Lock syncLock = new ReentrantLock();
	private final LinkedList<U> backing;
	
	// #Region CTOR

	public Queue() {
		this(new ArrayList<>());
	}

	public Queue(Collection<? extends U> collection) {
		backing = new LinkedList<>(collection);
	}

	// #EndRegion
	
	// #Region Thread Safe Wrappers
	
	/**
	 * Encapsulates a returnable method into a thread safe version
	 *
	 * @param query
	 *            Query to run
	 * @return Value from query
	 * @throws Exception
	 */
	private <V> V syncReturn(Callable<V> query) {
		V result = null;
		try {
			syncLock.lock();
			result = query.call(); // method could fail
									 // wrap in try catch to release lock
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			syncLock.unlock();
		}
		return result;
	}
	
	/**
	 * Encapsulates a method into a thread safe version
	 *
	 * @param query
	 *            Query to run
	 */
	private void syncMethod(Runnable query) {
		syncReturn(() -> {
			query.run();
			return null; // work around so this method doesn't have
						 // to duplicate locking code
		});
	}

	// #EndRegion
	
	// #Region Non-Standard Java Queue Methods
	
	public boolean enqueue(U e) {
		return add(e);
	}
	
	public U dequeue() {
		return poll();
	}
	
	// #EndRegion
	
	// #Region Queue Methods

	@Override
	public int size() {
		return syncReturn(() -> backing.size());
	}

	@Override
	public boolean isEmpty() {
		return syncReturn(() -> backing.isEmpty());
	}
	
	@Override
	public boolean contains(Object o) {
		return syncReturn(() -> backing.contains(o));
	}

	/**
	 * Iterator of collection
	 * !!! NOT THREAD SAFE !!!
	 *
	 * @return Iterator
	 */
	@Override
	public Iterator<U> iterator() {
		// TODO Make thread safe
		return syncReturn(() -> backing.iterator());
	}

	@Override
	public Object[] toArray() {
		return syncReturn(() -> backing.toArray());
	}
	
	@Override
	public <T> T[] toArray(T[] a) {
		return syncReturn(() -> backing.toArray(a));
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
		return syncReturn(() -> backing.containsAll(c));
	}
	
	@Override
	public boolean addAll(Collection<? extends U> c) {
		return syncReturn(() -> backing.addAll(c));
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		return syncReturn(() -> backing.removeAll(c));
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		return syncReturn(() -> backing.retainAll(c));
	}

	@Override
	public void clear() {
		syncMethod(() -> backing.clear());
	}

	@Override
	public boolean add(U e) {
		return syncReturn(() -> backing.add(e));
	}
	
	@Override
	public boolean offer(U e) {
		return syncReturn(() -> backing.offer(e));
	}

	@Override
	public U poll() {
		return syncReturn(() -> backing.poll());
	}

	@Override
	public U peek() {
		return syncReturn(() -> backing.peek());
	}
	
	@Override
	public U element() {
		return syncReturn(() -> backing.element());
	}
	
	@Override
	public boolean remove(Object o) {
		return syncReturn(() -> backing.remove(o));
	}
	
	@Override
	public U remove() {
		return syncReturn(() -> backing.remove());
	}
	
	// #EndRegion
}
