package Generic;

public class BSTNode<T extends Comparable<T>>
{
	BSTNode(T value)
	{
		Value = value;
	}
	
	// #region Properties
	private BSTNode<T> _rightLeaf;
	
	public BSTNode<T> RightLeaf()
	{
		return _rightLeaf;
	}
	
	protected void setRightLeaf(BSTNode<T> item)
	{
		_rightLeaf = item;
	}
	
	private BSTNode<T> _leftLeaf;
	
	public BSTNode<T> LeftLeaf()
	{
		return _leftLeaf;
	}
	
	protected void setLeftLeaf(BSTNode<T> item)
	{
		_leftLeaf = item;
	}
	
	public T Value;
	
	public int Depth()
	{
		return LeftLeafDepth() + RightLeafDepth();
	}
	
	public int LeftLeafDepth()
	{
		return HasLeftLeaf() ? (1 + LeftLeaf().Depth()) : 0;
	}
	
	public int RightLeafDepth()
	{
		return HasRightLeaf() ? (1 + RightLeaf().Depth()) : 0;
	}
	
	// #endregion
	
	// #region Properties
	
	public boolean HasRightLeaf()
	{
		return (RightLeaf() != null);
	}
	
	public boolean HasLeftLeaf()
	{
		return (LeftLeaf() != null);
	}
	
	// #endregion
}
