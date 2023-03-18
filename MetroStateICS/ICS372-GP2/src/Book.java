
/**
 * @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010
 *            Redistribution and use with or without
 *            modification, are permitted provided that the following conditions
 *            are met:
 *            - the use is for academic purpose only
 *            - Redistributions of source code must retain the above copyright
 *            notice, this list of conditions and the following disclaimer.
 *            - Neither the name of Brahma Dathan or Sarnath Ramnath
 *            may be used to endorse or promote products derived
 *            from this software without specific prior written permission.
 *            The authors do not make any claims regarding the correctness of
 *            the code in this module
 *            and are not responsible for any loss or damage resulting from its
 *            use.
 */
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Represents a single book
 *
 * @author Brahma Dathan and Sarnath Ramnath
 */
public class Book extends LoanableItem implements Serializable, Matchable<String> {
	private static final long serialVersionUID = 1L;
	private String author;
	
	/**
	 * Creates a book with the given id, title, and author name
	 *
	 * @param title
	 *            book title
	 * @param author
	 *            author name
	 * @param id
	 *            book id
	 */
	public Book(String title, String author, String id) {
		super(title, id);
		this.author = author;
	}
	
	/**
	 * will calculate the fine
	 *
	 * @return the fine calculated
	 */
	@Override
	public double getFineAmount() {
		// for a normal book, give the standard formula
		if (!getIsInReservedSection()) {
			return super.getFineAmount();
		}
		
		// custom book formula $1 per ceiling hour
		// date difference in hours
		Calendar today = new GregorianCalendar();
		today.add(Calendar.MINUTE, 30); // add 30 minutes to round up hours
		long hoursLate = (today.getTimeInMillis() - getDueDate().getTimeInMillis()) / (1000 * 60 * 60);
		
		// late fee formula
		// double fine = 1.0 * hoursLate;
		return hoursLate;
	}
	
	/**
	 * Marks the book as issued to a member
	 *
	 * @param member
	 *            the borrower
	 * @return true iff the book could be issued. True currently
	 */
	@Override
	public boolean issue(Member member) {
		if (super.issue(member)) {
			// reserved section item has 2 hour limit
			if (super.getIsInReservedSection()) {
				dueDate.add(Calendar.HOUR, 2);
			}
			// standard 1 month borrow
			else {
				dueDate.add(Calendar.MONTH, 1);
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Getter for author
	 *
	 * @return author name
	 */
	public String getAuthor() {
		return author;
	}
	
	/**
	 * String form of the book
	 */
	@Override
	public String toString() {
		return super.toString() + " author " + author + " borrowed by " + borrowedBy;
	}
	
	/**
	 * Implements the accept method of the Visitor pattern.
	 *
	 * @param visitor
	 *            the Visitor that will process the Book object
	 */
	@Override
	public void accept(LoanableItemVisitor visitor) {
		visitor.visit(this);
	}
}
