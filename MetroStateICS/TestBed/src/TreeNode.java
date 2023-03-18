/**
 *
 */

/**
 * @author rgau1
 * @version 1.0
 */
public class TreeNode {
	
	private TreeNode right;
	private TreeNode left;
	private int nodeValue;
	
	public static void main(String[] args) {
		TreeNode root = new TreeNode(8);
		root.setLeft(new TreeNode(4));
		root.left.setLeft(new TreeNode(2));
		root.left.left.setLeft(new TreeNode(1));
		root.left.left.setRight(new TreeNode(3));
		root.left.setRight(new TreeNode(6));
		root.left.right.setLeft(new TreeNode(5));
		root.left.right.setRight(new TreeNode(7));
		root.setRight(new TreeNode(12));
		root.right.setLeft(new TreeNode(10));
		root.right.left.setLeft(new TreeNode(9));
		root.right.left.setRight(new TreeNode(11));
		root.right.setRight(new TreeNode(14));
		root.right.right.setLeft(new TreeNode(13));
		root.right.right.setRight(new TreeNode(15));

		System.out.println("----- PREORDER -----");
		preOrder(root);
		System.out.println("----- POSTORDER -----");
		postOrder(root);
	}
	
	public TreeNode(int i) {
		setValue(i);
		left = null;
		right = null;
	}
	
	public TreeNode leftNode() {
		return left;
	}
	
	public TreeNode rightNode() {
		return right;
	}
	
	public int getNodeValue() {
		return nodeValue;
	}
	
	public void setLeft(TreeNode t) {
		left = t;
	}
	
	public void setRight(TreeNode t) {
		right = t;
	}
	
	public void setValue(int i) {
		nodeValue = i;
	}
	
	public static void preOrder(TreeNode t) {
		if (t != null) {
			System.out.println(t.getNodeValue());
			postOrder(t.left);
			postOrder(t.right);
		}
	}
	
	public static void postOrder(TreeNode t) {
		if (t != null) {
			preOrder(t.left);
			preOrder(t.right);
			System.out.println(t.getNodeValue());
		}
	}
}
