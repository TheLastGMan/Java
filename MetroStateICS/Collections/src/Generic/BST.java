package Generic;

/**
 * Generic Binary Search Tree
 * 
 * @author rgau1
 * @param <T>
 * @param <U>
 */
public class BST<T extends IBSTNode<T>>
{
	private BSTNode<T> _root;			// base node
	private String _separator;
	
	private final int _rebalanceFreq = 10;	// balance bst if array is divisible by this
	private final int _diffTrigger = 2; 	// determines at what value tree should balance
	
	public BST(String separator)
	{
		_separator = separator;
	}
	
	// #region Properties
	
	private int _count;
	
	public int Count()
	{
		return _count;
	}
	
	public boolean AutoBalance = true;
	
	// #endregion
	
	// #region Methods
	
	public void Insert(T item)
	{
		// do not insert null values
		if (item == null)
			return;
		
		// insert
		_count++;
		BSTNode<T> newNode = new BSTNode<T>(item);
		if (_root == null)
			_root = newNode;
		else
			bstInsert(_root, newNode);
		
		// balance every n items if enabled
		if (_count % _rebalanceFreq == 0 && AutoBalance)
			Balance();
	}
	
	public void Balance()
	{
		_root = rebalanceBST(_root);
	}
	
	public boolean Contains(T item)
	{
		if (_root == null || item == null)
			return false;
		
		return bstFind(_root, item) != null;
	}
	
	public T Find(T value)
	{
		return bstFind(_root, value).Value;
	}
	
	public void Delete(T item)
	{
		// no null values
		if (_root == null || item == null)
			return;
		
		bstDelete(null, _root, item);
	}
	
	@Override
	public String toString()
	{
		// empty if null
		if (_root == null)
			return "";
		
		return nodeText(_root);
	}
	
	// #endregion
	
	private BSTNode<T> rebalanceBST(BSTNode<T> node)
	{
		// load diff
		int diff = node.RightLeafDepth() - node.LeftLeafDepth();
		int aDiff = Math.abs(diff);
		
		if (aDiff >= _diffTrigger)
		{
			if (diff < 0)
			{
				// detach left leaf and rotate towards right leaf
				BSTNode<T> left = node.LeftLeaf();
				node.setLeftLeaf(null);
				bstInsert(left, node);
				
				// run again until balanced
				return rebalanceBST(left);
			}
			else
			{
				// detach right leaf and rotate towards left leaf
				BSTNode<T> right = node.RightLeaf();
				node.setRightLeaf(null);
				bstInsert(right, node);
				
				// run again until balanced
				return rebalanceBST(right);
			}
		}
		
		// balance left leaf
		if (node.LeftLeafDepth() > _diffTrigger)
			node.setLeftLeaf(rebalanceBST(node.LeftLeaf()));
		
		// balance right leaf
		if (node.RightLeafDepth() > _diffTrigger)
			node.setRightLeaf(rebalanceBST(node.RightLeaf()));
		
		// give balanced node
		return node;
	}
	
	private void bstInsert(BSTNode<T> node, BSTNode<T> item)
	{
		// do nothing for null values as we can't compare them
		if (item == null)
			return;
		
		int cVal = item.Value.compareTo(node.Value);
		if (cVal == 0)
		{
			/*
			 * No Duplicate Entries, causes issues when balancing
			 * if(node.HasLeftLeaf() && _comparator.compare(node.LeftLeaf().Value, item.Value) == 0)
			 * //continue on left node
			 * bstInsert(node.LeftLeaf(), item);
			 * else
			 * {
			 * //finally different, add as left node
			 * bstInsert(item, node.LeftLeaf());
			 * node.setLeftLeaf(item);
			 * }
			 */
			node.Value.DuplicateDetected();
			return;
		}
		else if (cVal < 0)
		{
			// less than, check if left node available
			if (node.HasLeftLeaf())
				// traverse left node
				bstInsert(node.LeftLeaf(), item);
			else
			{
				// add to left node
				node.setLeftLeaf(item);
				_count++;
			}
		}
		else
		{
			// greater than, check if right node available
			if (node.HasRightLeaf())
				// traverse right node
				bstInsert(node.RightLeaf(), item);
			else
			{
				// add to right node
				node.setRightLeaf(item);
				_count++;
			}
		}
	}
	
	public BSTNode<T> bstFind(BSTNode<T> node, T item)
	{
		if (node == null)
			return null;
		
		int cVal = item.compareTo(node.Value);
		if (cVal == 0)
			return node;
		else if (cVal < 0)
			return bstFind(node.LeftLeaf(), item);
		else
			return bstFind(node.RightLeaf(), item);
	}
	
	private void bstDelete(BSTNode<T> parent, BSTNode<T> node, T item)
	{
		// if node is null, reached end of the line, no deletions found
		if (node == null)
			return;
		
		// compare and run action
		int cVal = item.compareTo(node.Value);
		if (cVal == 0)
		{
			if (parent.LeftLeaf() == node)
				// update parents left leaf
				parent.setLeftLeaf(node.LeftLeaf());
			else
				// update parents right leaf
				parent.setRightLeaf(node.LeftLeaf());
			
			bstInsert(parent, node.RightLeaf());
			_count--;
		}
		else if (cVal < 0)
		{
			// continue via left leaf
			bstDelete(node, node.LeftLeaf(), item);
		}
		else
		{
			// continue via right leaf
			bstDelete(node, node.RightLeaf(), item);
		}
	}
	
	public String PostOrder()
	{
		// empty if null
		if (_root == null)
			return "";
		
		return nodePostOrderText(_root);
	}
	
	// left, right, root
	private String nodePostOrderText(BSTNode<T> node)
	{
		if (node == null)
			return null;
		
		String result = "";
		
		// left
		if (node.HasLeftLeaf())
			result += nodePostOrderText(node.LeftLeaf()) + _separator;
		
		// right
		if (node.HasRightLeaf())
			result += nodePostOrderText(node.RightLeaf()) + _separator;
		
		// node
		result += node.Value.toString();
		return result;
	}
	
	public String PreOrder()
	{
		// empty if null
		if (_root == null)
			return "";
		
		return nodePreOrderText(_root);
	}
	
	// Node, left, right
	private String nodePreOrderText(BSTNode<T> node)
	{
		if (node == null)
			return null;
		
		// node
		String result = node.Value.toString();
		
		// left
		if (node.HasLeftLeaf())
			result += _separator + nodePreOrderText(node.LeftLeaf());
		
		// right
		if (node.HasRightLeaf())
			result += _separator + nodePreOrderText(node.RightLeaf());
		
		return result;
	}
	
	private String nodeText(BSTNode<T> node)
	{
		// safety net, return empty if null
		if (node == null)
			return "";
		
		String result = "";
		// continue to navigate if left node
		if (node.HasLeftLeaf())
			result += nodeText(node.LeftLeaf()) + _separator;
		
		// output current node
		result += node.Value.toString();
		
		// add right hand side if available
		if (node.HasRightLeaf())
			result += _separator + nodeText(node.RightLeaf());
		
		return result;
	}
}
