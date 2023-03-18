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
/**
 * Prints all LoanableItem objects formatted.
 * 
 * @author Brahma Dathan
 */
public class PrintFormat implements LoanableItemVisitor {
	private static PrintFormat visitor;
	
	/**
	 * The constructor is for the singleton pattern
	 */
	private PrintFormat() {
	}
	
	/**
	 * Returns the only instance of the class
	 * 
	 * @return the instance of the class
	 */
	public static PrintFormat instance() {
		if (visitor == null) {
			visitor = new PrintFormat();
		}
		return visitor;
	}
	
	/**
	 * This is not a fully implemented method. It essentially prints the
	 * object's string representation.
	 */
	@Override
	public void visit(LoanableItem item) {
		System.out.println("Print " + item + " formatted");
	}
	
	/**
	 * This is not a fully implemented method. It essentially prints the
	 * object's string representation.
	 */
	@Override
	public void visit(Book book) {
		System.out.println("Print " + book + " formatted");
	}
	
	/**
	 * This is not a fully implemented method. It essentially prints the
	 * object's string representation.
	 */
	@Override
	public void visit(Periodical periodical) {
		System.out.println("Print " + periodical + " formatted");
	}
	
	/**
	 * This is not a fully implemented method. It essentially prints the
	 * object's string representation.
	 */
	@Override
	public void visit(Laptop laptop) {
		System.out.println("Print " + laptop + " formatted");
		
	}
	
	/**
	 * This is not a fully implemented method. It essentially prints the
	 * object's string representation.
	 */
	@Override
	public void visit(DigitalCamera camera) {
		System.out.println("Print " + camera + " formatted");
		
	}
	
	/**
	 * This is not a fully implemented method. It essentially prints the
	 * object's string representation.
	 */
	@Override
	public void visit(DVD dvd) {
		System.out.println("Print " + dvd + " formatted");
		
	}
	
}
