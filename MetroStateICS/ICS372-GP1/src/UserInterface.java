
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
 *            THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS"AS
 *            IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *            LIMITED TO,
 *            THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *            PARTICULAR
 *            PURPOSE ARE DISCLAIMED.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * This class implements the user interface for the Library project.
 * The commands are encoded as integers using a number of
 * static final variables. A number of utility methods exist to
 * make it easier to parse the input.
 */
public class UserInterface {
	private static UserInterface userInterface;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Library library;
	private static final int EXIT = 0;
	private static final int ADD_MEMBER = 1;
	private static final int ADD_BOOKS = 2;
	private static final int ISSUE_BOOKS = 3;
	private static final int RETURN_BOOKS = 4;
	private static final int RENEW_BOOKS = 5;
	private static final int REMOVE_BOOKS = 6;
	private static final int PLACE_HOLD = 7;
	private static final int REMOVE_HOLD = 8;
	private static final int PROCESS_HOLD = 9;
	private static final int GET_TRANSACTIONS = 10;
	private static final int SAVE = 11;
	private static final int RETRIEVE = 12;
	private static final int HELP = 13;
	
	/**
	 * Made private for singleton pattern.
	 * Conditionally looks for any saved data. Otherwise, it gets
	 * a singleton Library object.
	 */
	private UserInterface() {
		if (yesOrNo("Look for saved data and  use it?")) {
			retrieve();
		}
		else {
			library = Library.instance();
		}
	}
	
	/**
	 * Supports the singleton pattern
	 *
	 * @return the singleton object
	 */
	public static UserInterface instance() {
		if (userInterface == null) {
			return userInterface = new UserInterface();
		}
		else {
			return userInterface;
		}
	}
	
	/**
	 * Gets a token after prompting
	 *
	 * @param prompt
	 *            - whatever the user wants as prompt
	 * @return - the token from the keyboard
	 */
	public String getToken(String prompt) {
		do {
			try {
				System.out.println(prompt);
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line, "\n\r\f");
				if (tokenizer.hasMoreTokens()) {
					return tokenizer.nextToken();
				}
			}
			catch (IOException ioe) {
				System.exit(0);
			}
		} while (true);
	}
	
	/**
	 * Queries for a yes or no and returns true for yes and false for no
	 *
	 * @param prompt
	 *            The string to be prepended to the yes/no prompt
	 * @return true for yes and false for no
	 */
	private boolean yesOrNo(String prompt) {
		String more = getToken(prompt + " (Y|y)[es] or anything else for no");
		if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
			return false;
		}
		return true;
	}
	
	/**
	 * Converts the string to a number
	 *
	 * @param prompt
	 *            the string for prompting
	 * @return the integer corresponding to the string
	 */
	public int getNumber(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				Integer number = Integer.valueOf(item);
				return number.intValue();
			}
			catch (NumberFormatException nfe) {
				System.out.println("Please input a number ");
			}
		} while (true);
	}
	
	/**
	 * Prompts for a date and gets a date object
	 *
	 * @param prompt
	 *            the prompt
	 * @return the data as a Calendar object
	 */
	public Calendar getDate(String prompt) {
		do {
			try {
				Calendar date = new GregorianCalendar();
				String item = getToken(prompt);
				DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
				date.setTime(dateFormat.parse(item));
				return date;
			}
			catch (Exception fe) {
				System.out.println("Please input a date as mm/dd/yy");
			}
		} while (true);
	}
	
	/**
	 * Prompts for a command from the keyboard
	 *
	 * @return a valid command
	 */
	public int getCommand() {
		do {
			try {
				int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
				if (value >= EXIT && value <= HELP) {
					return value;
				}
			}
			catch (NumberFormatException nfe) {
				System.out.println("Enter a number");
			}
		} while (true);
	}
	
	/**
	 * Displays the help screen
	 */
	public void help() {
		System.out.println("Enter a number between 0 and 12 as explained below:");
		System.out.println(EXIT + " to Exit\n");
		System.out.println(ADD_MEMBER + " to add a member");
		System.out.println(ADD_BOOKS + " to  add books");
		System.out.println(ISSUE_BOOKS + " to  issue books to a  member");
		System.out.println(RETURN_BOOKS + " to  return books ");
		System.out.println(RENEW_BOOKS + " to  renew books ");
		System.out.println(REMOVE_BOOKS + " to  remove books");
		System.out.println(PLACE_HOLD + " to  place a hold on a book");
		System.out.println(REMOVE_HOLD + " to  remove a hold on a book");
		System.out.println(PROCESS_HOLD + " to  process holds");
		System.out.println(GET_TRANSACTIONS + " to  print transactions");
		System.out.println(SAVE + " to  save data");
		System.out.println(RETRIEVE + " to  retrieve");
		System.out.println(HELP + " for help");
	}
	
	/**
	 * Method to be called for adding a member.
	 * Prompts the user for the appropriate values and
	 * uses the appropriate Library method for adding the member.
	 */
	public void addMember() {
		String name = getToken("Enter member name");
		String address = getToken("Enter address");
		String phone = getToken("Enter phone");
		Member result;
		result = library.addMember(name, address, phone);
		if (result == null) {
			System.out.println("Could not add member");
		}
		System.out.println(result);
	}
	
	/**
	 * Method to be called for adding a book.
	 * Prompts the user for the appropriate values and
	 * uses the appropriate Library method for adding the book.
	 */
	public void addBooks() {
		Book result;
		do {
			String title = getToken("Enter  title");
			String bookID = getToken("Enter id");
			String author = getToken("Enter author");
			result = library.addBook(title, author, bookID);
			if (result != null) {
				System.out.println(result);
			}
			else {
				System.out.println("Book could not be added");
			}
			if (!yesOrNo("Add more books?")) {
				break;
			}
		} while (true);
	}
	
	/**
	 * Method to be called for issuing books.
	 * Prompts the user for the appropriate values and
	 * uses the appropriate Library method for issuing books.
	 */
	public void issueBooks() {
		Book result;
		String memberId;
		
		library.showMemberList();
		String memberNumber = getToken("Enter line number of desired member: ");
		
		int number = checkMemberNumber(memberNumber);
		
		if (number == -1) {
			return;
		}
		
		memberId = library.getMemberID(memberNumber);
		
		do {
			String bookId = getToken("Enter book id");
			result = library.issueBook(memberId, bookId);
			if (result != null) {
				System.out.println(result.getTitle() + "   " + result.getDueDate());
			}
			else {
				System.out.println("Book could not be issued");
			}
			if (!yesOrNo("Issue more books?")) {
				break;
			}
		} while (true);
	}
	
	/**
	 * Method to be called for renewing books.
	 * Prompts the user for the appropriate values and
	 * uses the appropriate Library method for renewing books.
	 */
	public void renewBooks() {
		Book result;
		String memberId;
		String bookId;
		List<Book> arrayOfBooks;
		
		library.showMemberList();
		String memberNumber = getToken("Enter line number of desired member: ");
		
		int number = checkMemberNumber(memberNumber);
		
		if (number == -1) {
			return;
		}
		
		memberId = library.getMemberID(memberNumber);
		
		do {
			arrayOfBooks = library.showCheckedOutBooks(memberId);
			
			if (arrayOfBooks.isEmpty()) {
				System.out.println("No books to be renewed for this member");
				break;
			}
			else {
				String bookNumber = getToken("Enter line number of desired book: ");
				
				boolean validNumber = checkBookNumber(bookNumber, arrayOfBooks);
				if (validNumber) {
					bookId = library.getBookID(bookNumber, arrayOfBooks);
					result = library.renewBook(bookId, memberId);
				}
				else {
					return;
				}
				if (result != null) {
					System.out.println(result.getTitle() + "   " + result.getDueDate());
				}
				else {
					System.out.println("Book is not renewable");
				}
				if (!yesOrNo("Are there more books to renew?")) {
					break;
				}
			}
		} while (true);
	}
	
	/**
	 * Method to be called for returning books.
	 * Prompts the user for the appropriate values and
	 * uses the appropriate Library method for returning books.
	 */
	public void returnBooks() {
		int result;
		do {
			String bookID = getToken("Enter book id");
			result = library.returnBook(bookID);
			switch (result) {
				case Library.BOOK_NOT_FOUND:
					System.out.println("No such Book in Library");
					break;
				case Library.BOOK_NOT_ISSUED:
					System.out.println(" Book  was not checked out");
					break;
				case Library.BOOK_HAS_HOLD:
					System.out.println("Book has a hold");
					break;
				case Library.OPERATION_FAILED:
					System.out.println("Book could not be returned");
					break;
				case Library.OPERATION_COMPLETED:
					System.out.println(" Book has been returned");
					break;
				default:
					System.out.println("An error has occurred");
			}
			if (!yesOrNo("Return more books?")) {
				break;
			}
		} while (true);
	}
	
	/**
	 * Method to be called for removing books.
	 * Prompts the user for the appropriate values and
	 * uses the appropriate Library method for removing books.
	 */
	public void removeBooks() {
		int result;
		String bookId;
		List<Book> arrayOfBooks;
		
		do {
			arrayOfBooks = library.getPotentialRemovals();
			String bookNumber = getToken("Enter line number of desired book: ");
			
			boolean validNumber = checkBookNumber(bookNumber, arrayOfBooks);
			if (validNumber) {
				bookId = library.getBookID(bookNumber, arrayOfBooks);
				result = library.removeBook(bookId);
			}
			else {
				return;
			}
			
			switch (result) {
				case Library.BOOK_NOT_FOUND:
					System.out.println("No such Book in Library");
					break;
				case Library.BOOK_ISSUED:
					System.out.println(" Book is currently checked out");
					break;
				case Library.BOOK_HAS_HOLD:
					System.out.println("Book has a hold");
					break;
				case Library.OPERATION_FAILED:
					System.out.println("Book could not be removed");
					break;
				case Library.OPERATION_COMPLETED:
					System.out.println(" Book has been removed");
					break;
				default:
					System.out.println("An error has occurred");
			}
			if (!yesOrNo("Remove more books?")) {
				break;
			}
		} while (true);
	}
	
	/**
	 * Method to be called for placing a hold.
	 * Prompts the user for the appropriate values and
	 * uses the appropriate Library method for placing a hold.
	 */
	public void placeHold() {
		library.showMemberList();
		String bookId;
		int result;
		List<Member> members = MemberList.instance().getMembers();
		int max = members.size();
		String memberNumber = getToken("Enter line number of desired member: ");
		int number = Integer.parseInt(memberNumber);
		if (number == -1) {
			return;
		}
		else if (number < 0 || number >= max) {
			System.out.println("Please enter a number which is in the range of sequence numbers");
			return;
		}
		String memberId = library.getMemberID(memberNumber);
		List<Book> arrayOfBooks = library.showBooksCheckedOut();
		
		String bookNumber = getToken("Enter line number of desired book: ");
		
		boolean validNumber = checkBookNumber(bookNumber, arrayOfBooks);
		if (validNumber) {
			bookId = library.getBookID(bookNumber, arrayOfBooks);
			int duration = getNumber("Enter duration of hold: ");
			result = library.placeHold(memberId, bookId, duration);
		}
		else {
			return;
		}
		switch (result) {
			case Library.BOOK_NOT_FOUND:
				System.out.println("No such Book in Library");
				break;
			case Library.BOOK_NOT_ISSUED:
				System.out.println(" Book is not checked out");
				break;
			case Library.NO_SUCH_MEMBER:
				System.out.println("Not a valid member ID");
				break;
			case Library.HOLD_PLACED:
				System.out.println("A hold has been placed");
				break;
			default:
				System.out.println("An error has occurred");
		}
	}
	
	/**
	 * Method to be called for removing a holds.
	 * Prompts the user for the appropriate values and
	 * uses the appropriate Library method for removing a hold.
	 */
	public void removeHold() {
		String memberID = getMemberId();
		
		// load held book for member
		int result = Library.NO_SUCH_MEMBER;
		if (memberID != null) {
			List<Book> books = library.getMemberHolds(memberID);
			if (books.size() == 0) {
				System.out.println("No books on hold");
				return;
			}
			String bookID = getBookId(books);
			
			// if no book selected, exit
			if (bookID == null) {
				return;
			}
			
			// remove hold
			result = library.removeHold(memberID, bookID);
		}
		
		// show result of hold removal
		switch (result) {
			case Library.BOOK_NOT_FOUND:
				System.out.println("No such Book in Library");
				break;
			case Library.NO_SUCH_MEMBER:
				System.out.println("Not a valid member ID");
				break;
			case Library.OPERATION_COMPLETED:
				System.out.println("The hold has been removed");
				break;
			default:
				System.out.println("An error has occurred");
		}
	}
	
	/**
	 * Retrieves the member's id from the selection
	 *
	 * @return Member's Id or null if not found
	 */
	private String getMemberId() {
		// poll for input
		while (true) {
			// receive input
			library.showMemberList();
			String seqNumber = getToken("Member Number: ");
			
			try {
				// validate selection and map to id
				int index = Integer.parseInt(seqNumber);
				if (index == -1) {
					return null;
				}
				
				// check range
				if (index < 0 || index >= MemberList.instance().getMembers().size()) {
					throw new IndexOutOfBoundsException();
				}
				
				// load members id
				return library.getMemberID(Integer.toString(index));
			}
			catch (Exception ex) {
				// invalid selection
				System.out.println("Invalid sequence number.");
			}
		}
	}
	
	/**
	 * Retrieves the held book's id for the specified member
	 *
	 * @return Book's Id or null if not found
	 */
	private String getBookId(List<Book> books) {
		// poll for input
		while (true) {
			// receive input
			String seqNumber = getToken("Book Number: ");
			
			try {
				// validate selection and map to id
				int index = Integer.parseInt(seqNumber);
				if (index == -1) {
					return null;
				}
				
				// check range
				if (index < 0 || index >= books.size()) {
					throw new IndexOutOfBoundsException();
				}
				
				// load members id
				return library.getBookID(Integer.toString(index), books);
			}
			catch (Exception ex) {
				// invalid selection
				System.out.println("Invalid sequence number.");
			}
		}
	}
	
	/**
	 * Method to be called for processing books.
	 * Prompts the user for the appropriate values and
	 * uses the appropriate Library method for processing books.
	 */
	public void processHolds() {
		do {
			List<Book> books = library.getAllHolds();
			if (books.size() == 0) {
				System.out.println("No Books on hold");
				return;
			}
			String bookID = getBookId(books);
			
			// if not book was selected, exit
			if (bookID == null) {
				return;
			}
			
			// process hold on book
			Member result = library.processHold(bookID);
			if (result != null) {
				System.out.println(result);
			}
			else {
				System.out.println("No valid holds left");
			}
			if (!yesOrNo("Process more books?")) {
				break;
			}
		} while (true);
	}
	
	/**
	 * Method to be called for displaying transactions.
	 * Prompts the user for the appropriate values and
	 * uses the appropriate Library method for displaying transactions.
	 */
	public void getTransactions() {
		Iterator<Transaction> result;
		library.showMemberList();
		String memberNumber = getToken("Enter line number of desired member: ");
		
		int number = checkMemberNumber(memberNumber);
		
		if (number == -1) {
			return;
		}
		
		String memberId = library.getMemberID(memberNumber);
		Calendar date = getDate("Please enter the date for which you want records as mm/dd/yy");
		result = library.getTransactions(memberId, date);
		if (result == null) {
			System.out.println("Invalid Member ID");
		}
		else {
			while (result.hasNext()) {
				Transaction transaction = result.next();
				System.out.println(transaction.getType() + "   " + transaction.getTitle() + "\n");
			}
			System.out.println("\n  There are no more transactions \n");
		}
	}
	
	/**
	 * Method to be called for saving the Library object.
	 * Uses the appropriate Library method for saving.
	 */
	private void save() {
		if (Library.save()) {
			System.out.println(" The library has been successfully saved in the file LibraryData \n");
		}
		else {
			System.out.println(" There has been an error in saving \n");
		}
	}
	
	/**
	 * Method to be called for retrieving saved data.
	 * Uses the appropriate Library method for retrieval.
	 */
	private void retrieve() {
		try {
			Library tempLibrary = Library.retrieve();
			if (tempLibrary != null) {
				System.out.println(" The library has been successfully retrieved from the file LibraryData \n");
				library = tempLibrary;
			}
			else {
				System.out.println("File doesnt exist; creating new library");
				library = Library.instance();
			}
		}
		catch (Exception cnfe) {
			cnfe.printStackTrace();
		}
	}
	
	/**
	 * Orchestrates the whole process.
	 * Calls the appropriate method for the different functionalties.
	 */
	public void process() {
		int command;
		help();
		while ((command = getCommand()) != EXIT) {
			switch (command) {
				case ADD_MEMBER:
					addMember();
					break;
				case ADD_BOOKS:
					addBooks();
					break;
				case ISSUE_BOOKS:
					issueBooks();
					break;
				case RETURN_BOOKS:
					returnBooks();
					break;
				case REMOVE_BOOKS:
					removeBooks();
					break;
				case RENEW_BOOKS:
					renewBooks();
					break;
				case PLACE_HOLD:
					placeHold();
					break;
				case REMOVE_HOLD:
					removeHold();
					break;
				case PROCESS_HOLD:
					processHolds();
					break;
				case GET_TRANSACTIONS:
					getTransactions();
					break;
				case SAVE:
					save();
					break;
				case RETRIEVE:
					retrieve();
					break;
				case HELP:
					help();
					break;
			}
		}
	}
	
	/**
	 * The method to start the application. Simply calls process().
	 *
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {
		UserInterface.instance().process();
	}
	
	// helper methods
	private int checkMemberNumber(String memberNumber) {
		
		int result = Integer.parseInt(memberNumber);
		
		List<Member> members = MemberList.instance().getMembers();
		int max = members.size();
		
		try {
			int number = result;
			if (number < -1 || number >= max) {
				System.out.println("Please enter a number which is in the range of sequence numbers");
				return result = -1;
			}
		}
		catch (NumberFormatException e) {
			System.out.println("Invalid sequence number.");
			return result = -1;
		}
		return result;
	}
	
	private boolean checkBookNumber(String bookNumber, List<Book> list) {
		
		boolean result = true;
		
		List<Book> arrayOfBooks = list;
		try {
			
			int lineNumber = Integer.parseInt(bookNumber);
			if (lineNumber < 0 || lineNumber >= arrayOfBooks.size()) {
				System.out.println("Please enter a number which is in the range of sequence numbers");
				return false;
			}
		}
		catch (NumberFormatException e) {
			System.out.println("Invalid sequence number.");
			return false;
		}
		return result;
	}
	
}// end class
