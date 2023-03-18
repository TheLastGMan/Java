import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Laptop extends LoanableItem implements Serializable, Matchable<String> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a laptop with the given id and BrandModel
	 * 
	 * @param BrandModel
	 *            laptop brand and model
	 * @param id
	 *            laptop id
	 */
	public Laptop(String BrandModel, String id) {
		super(BrandModel, id);
	}
	
	/**
	 * Marks the laptop as issued to a member
	 * 
	 * @param member
	 *            the borrower
	 * @return true iff the laptop could be issued. True currently
	 */
	@Override
	public boolean issue(Member member) {
		if (super.issue(member)) {
			dueDate.add(Calendar.HOUR_OF_DAY, 3);
			return true;
		}
		return false;
	}
	
	/**
	 * Marks the laptop as returned
	 * 
	 * @return The member who had borrowed the laptop
	 */
	@Override
	public Member returnItem() {
		if (borrowedBy == null) {
			return null;
		}
		else {
			Member borrower = borrowedBy;
			borrowedBy = null;
			return borrower;
		}
	}
	
	/**
	 * Implements the Matchable interface
	 * 
	 * @param key
	 *            the id of the digital camera
	 */
	@Override
	public boolean matches(String key) {
		return getId().equals(key);
	}
	
	/**
	 * Implements the accept method of the Visitor pattern.
	 * 
	 * @param visitor
	 *            the Visitor that will process the laptop object
	 */
	@Override
	public void accept(LoanableItemVisitor visitor) {
		visitor.visit(this);
	}
	
	/**
	 * Calculates the fine for laptop based on hours
	 * late
	 * 
	 * @return the amount of fine due
	 */
	@Override
	public double getFineAmount() {
		
		Calendar today = new GregorianCalendar();
		long hoursLate = (today.getTimeInMillis() - getDueDate().getTimeInMillis()) / (1000 * 60 * 60);
		System.out.println(hoursLate + " hours late");
		
		double fine = LoanableItem.lateFeeSubsequentDays * hoursLate
				+ (LoanableItem.lateFeeFirstDay - LoanableItem.lateFeeSubsequentDays);
		return fine;
	}
	
}
