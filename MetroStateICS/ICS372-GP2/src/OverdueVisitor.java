
public class OverdueVisitor implements LoanableItemVisitor {
	
	private static OverdueVisitor visitor;
	
	/**
	 * The constructor is for the singleton pattern
	 */
	private OverdueVisitor() {
	}
	
	/**
	 * Returns the only instance of the class
	 * 
	 * @return the instance of the class
	 */
	public static OverdueVisitor instance() {
		if (visitor == null) {
			visitor = new OverdueVisitor();
		}
		return visitor;
	}
	
	private int count;
	
	/**
	 * This method will check if item is over due, if so it will print
	 * out item information and add one to count.
	 */
	@Override
	public void visit(LoanableItem item) {
		if (item.isOverdue()) {
			System.out.println(item + "\n");
			count++;
		}
	}
	
	/**
	 * This method will check if book is over due, if so it will print
	 * out book information and add one to count.
	 */
	@Override
	public void visit(Book book) {
		if (book.isOverdue()) {
			System.out.println(book + "\n");
			count++;
		}
	}
	
	/**
	 * This method will check if periodical is over due, if so it will print
	 * out periodical information and add one to count.
	 */
	@Override
	public void visit(Periodical periodical) {
		if (periodical.isOverdue()) {
			System.out.println(periodical + "\n");
			count++;
		}
	}
	
	/**
	 * This method will check if laptop is over due, if so it will print
	 * out laptop information and add one to count.
	 */
	@Override
	public void visit(Laptop laptop) {
		if (laptop.isOverdue()) {
			System.out.println(laptop + "\n");
			count++;
		}
	}
	
	/**
	 * This method will check if camera is over due, if so it will print
	 * out camera information and add one to count.
	 */
	@Override
	public void visit(DigitalCamera camera) {
		if (camera.isOverdue()) {
			System.out.println(camera + "\n");
			count++;
		}
	}
	
	/**
	 * This method will check if dvd is over due, if so it will print
	 * out dvd information and add one to count.
	 */
	@Override
	public void visit(DVD dvd) {
		if (dvd.isOverdue()) {
			System.out.println(dvd + "\n");
			count++;
		}
	}
	
	/**
	 * This method will return count
	 * @ return int count
	 */
	public int getCount() {
		return count;
	}
	
}// end class
