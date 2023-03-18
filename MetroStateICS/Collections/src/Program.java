import Generic.*;

public class Program
{
	public static void main(String args[])
	{
		// setup
		int[] values = { 5, 10, 15, 20, 25, 30, 35, 40, 45 };
		int[] bstValues = { 10, 20, 30, 15, 12, 18, 14, 0, 25, 7, 3, 42, 42, 42, 59, 26, 5, 47, 35, 24 };
		
		// Display
		System.out.println("Collections");
		System.out.println("");
		
		// -------------------------------------------------
		// simulate queue
		System.out.println("Queue");
		Queue<Integer> queue = new Queue<Integer>();
		try
		{
			// -enqueue
			for (int v : values)
				queue.Enqueue(v);
			
			// -dequeue display
			while (queue.Count() > 0)
				System.out.println(queue.Dequeue());
		}
		catch (Exception ex)
		{
			System.out.println("Queue Error: " + ex.getMessage());
		}
		System.out.println("");
		
		// -------------------------------------------------
		// simulate stack
		System.out.println("Stack");
		Stack<Integer> stack = new Stack<Integer>();
		try
		{
			// -push
			for (int v : values)
				stack.Push(v);
			
			// -pop display
			while (stack.Count() > 0)
				System.out.println(stack.Pop());
		}
		catch (Exception ex)
		{
			System.out.println("Stack Error: " + ex.getMessage());
		}
		System.out.println("");
		
		// -------------------------------------------------
		// simulate linked list
		System.out.println("LinkedList");
		LinkedList<Integer> linked = new LinkedList<Integer>();
		try
		{
			// -add
			for (int v : values)
				linked.AddLast(v);
			
			// -iterate and display
			LinkedListNode<Integer> node = linked.First();
			while (node != null)
			{
				System.out.println(node.Value.toString());
				node = node.Next();
			}
		}
		catch (Exception ex)
		{
			System.out.println("LinkedList Error: " + ex.getMessage());
		}
		System.out.println("");
		
		// -------------------------------------------------
		// simulate linked list
		System.out.println("List");
		List<Integer> list = new List<Integer>(values[0]);
		try
		{
			// -add
			for (int v : values)
				list.Add(v);
			
			// -iterate and display
			for (int i = 0; i < list.Count(); i++)
				System.out.println(list.getItem(i));
		}
		catch (Exception ex)
		{
			System.out.println("List Error: " + ex.getMessage());
		}
		System.out.println("");
		
		// -------------------------------------------------
		// simulate Binary Searach Tree (BST)
		System.out.println("Binary Search Tree (BST)");
		BST<BSTComparatorInt> bst = new BST<BSTComparatorInt>(",");
		try
		{
			// -add
			for (int v : bstValues)
				bst.Insert(new BSTComparatorInt(v));
			
			// -delete
			bst.Delete(new BSTComparatorInt(bstValues[5]));
			bst.Delete(new BSTComparatorInt(bstValues[5]));
			bst.Delete(new BSTComparatorInt(bstValues[6]));
			
			// -display
			System.out.println("Pre  Order: " + bst.PreOrder());
			System.out.println("In   Order: " + bst.toString());
			System.out.println("Post Order: " + bst.PostOrder());
		}
		catch (Exception ex)
		{
			System.out.println("BST Error: " + ex.getMessage());
		}
		System.out.println("");
		System.out.println("Program Complete");
	}
}
