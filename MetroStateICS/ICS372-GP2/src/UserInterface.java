
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
 * This class implements the user interface for the Library project. The
 * commands are encoded as integers using a number of static final variables. A
 * number of utility methods exist to make it easier to parse the input.
 */
public class UserInterface {
	private static UserInterface userInterface;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Library library;
	private static final int EXIT = 0;
	private static final int ADD_MEMBER = 1;
	private static final int ADD_ITEMS = 2;
	private static final int ISSUE_ITEMS = 3;
	private static final int RETURN_ITEMS = 4;
	private static final int RENEW_ITEMS = 5;
	private static final int REMOVE_ITEMS = 6;
	private static final int PLACE_HOLD = 7;
	private static final int REMOVE_HOLD = 8;
	private static final int PROCESS_HOLD = 9;
	private static final int GET_TRANSACTIONS = 10;
	private static final int SAVE = 11;
	private static final int RETRIEVE = 12;
	private static final int PRINT_FORMATTED = 13;
	private static final int DISPLAY_OVERDUE_ITEMS = 14;
	private static final int REMOVE_MEMBER = 15;
	private static final int CHANGE_DUEDATE = 16;
	private static final int CHANGE_SECTION = 17;
	private static final int PAY_FINES = 18;
	private static final int HELP = 19;
	
	/**
	 * Made private for singleton pattern. Conditionally looks for any saved
	 * data. Otherwise, it gets a singleton Library object.
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
		System.out.println("Enter a number between 0 and 19 as explained below:");
		System.out.println(EXIT + " to Exit\n");
		System.out.println(ADD_MEMBER + " to add a member");
		System.out.println(ADD_ITEMS + " to add items");
		System.out.println(ISSUE_ITEMS + " to issue items to a  member");
		System.out.println(RETURN_ITEMS + " to return items ");
		System.out.println(RENEW_ITEMS + " to renew items ");
		System.out.println(REMOVE_ITEMS + " to remove items");
		System.out.println(PLACE_HOLD + " to place a hold on an item");
		System.out.println(REMOVE_HOLD + " to remove a hold on an item");
		System.out.println(PROCESS_HOLD + " to process holds");
		System.out.println(GET_TRANSACTIONS + " to print transactions");
		System.out.println(SAVE + " to save data");
		System.out.println(RETRIEVE + " to retrieve");
		System.out.println(PRINT_FORMATTED + " to print items formatted");
		System.out.println(DISPLAY_OVERDUE_ITEMS + " to display overdue items");
		System.out.println(REMOVE_MEMBER + " to remove member");
		System.out.println(CHANGE_DUEDATE + " to change duedate");
		System.out.println(CHANGE_SECTION + " to change item section");
		System.out.println(PAY_FINES + " to pay fines");
		System.out.println(HELP + " for help");
	}
	
	/**
	 * Method to be called for adding a member. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for adding the
	 * member.
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
	 * Method to be called for adding a loanable item. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for adding the
	 * item.
	 */
	public void addLoanableItems() {
		LoanableItem result;
		do {
			int type;
			do {
				type = getNumber("Enter " + Library.BOOK + " for book, " + Library.PERIODICAL + " for periodical, "
						+ Library.DIGITALCAMERA + " for digital camera, " + Library.DVD + " for DVD, or "
						+ Library.LAPTOP + " for laptop.");
			} while (type != Library.BOOK && type != Library.PERIODICAL && type != Library.DIGITALCAMERA
					&& type != Library.DVD && type != Library.LAPTOP);
			String title = getToken("Enter title for book, periodical, or DVD; make and model for camera or laptop");
			String author = "";
			if (type == Library.BOOK) {
				author = getToken("Enter author");
			}
			String id = getToken("Enter id");
			result = library.addLoanableItem(type, title, author, id);
			if (result != null) {
				System.out.println(result);
			}
			else {
				System.out.println("Item could not be added");
			}
			if (!yesOrNo("Add more items?")) {
				break;
			}
		} while (true);
	}
	
	/**
	 * Method to be called for issuing items. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for issuing
	 * items.
	 */
	public void issueLoanableItems() {
		LoanableItem result;
		Member thisMember;
		String memberID = getToken("Enter member id or -1 to go back");
		
		if (memberID.equals("-1")) {
			return;
		}
		
		if (library.searchMembership(memberID) == null) {
			System.out.println("No such member");
			return;
		}
		
		thisMember = (library.searchMembership(memberID));
		
		if (!thisMember.canBorrow()) {
			System.out.println("Unable to issue item -Member fine exceeds $5.00.\n" + "Current balance: $"
					+ String.format("%.2f", thisMember.getFines()));
			if (!yesOrNo("Does member wish to pay this down?")) {
				System.out.println("Member cannot borrow items before paying fines.");
				return;
			}
			else {
				do {
					String memberPaid = getToken("Enter amount of money collected to pay fines or -1 to go back:");
					if (memberPaid.equals("-1")) {
						return;
					}
					library.payFine(thisMember.getId(), Double.parseDouble(memberPaid));
					System.out.println("Current balance: $" + String.format("%.2f", thisMember.getFines()));
				} while (thisMember.getFines() > Member.getMaxLoanableFine());
			}
		}
		do {
			String itemID = getToken("Enter item id");
			result = library.issueLoanableItem(memberID, itemID);
			if (result != null) {
				System.out.println(result.getClass().getName() + " has been issued to member.");
			}
			else {
				System.out.println("Item could not be issued");
			}
			if (!yesOrNo("Issue more items?")) {
				break;
			}
		} while (true);
	}
	
	/**
	 * Method to be called for renewing items. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for renewing
	 * items.
	 */
	public void renewLoanableItems() {
		LoanableItem result;
		String memberID = getToken("Enter member id");
		if (library.searchMembership(memberID) == null) {
			System.out.println("No such member");
			return;
		}
		Iterator<LoanableItem> issuedItems = library.getLoanableItems(memberID);
		while (issuedItems.hasNext()) {
			LoanableItem item = (issuedItems.next());
			if (yesOrNo(item.getTitle())) {
				result = library.renewItem(item.getId(), memberID);
				if (result != null) {
					System.out.println(result.getTitle() + "   " + result.getDueDate());
				}
				else {
					System.out.println("Item is not renewable");
				}
			}
		}
	}
	
	/**
	 * Method to be called for returning items. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for returning
	 * items.
	 */
	public void returnLoanableItems() {
		int result;
		
		do {
			
			String itemID = getToken("Enter item id or -1 to go back");
			if (itemID.equals("-1")) {
				return;
			}
			
			result = library.returnLoanableItem(itemID);
			switch (result) {
				case Library.ITEM_NOT_FOUND:
					System.out.println("No such Item in Library");
					break;
				case Library.ITEM_NOT_ISSUED:
					System.out.println(" Item  was not checked out");
					break;
				case Library.ITEM_HAS_HOLD:
					System.out.println("Item has a hold");
					break;
				case Library.OPERATION_FAILED:
					System.out.println("Item could not be returned");
					break;
				case Library.OPERATION_COMPLETED:
					System.out.println("Item has been returned");
					break;
				case Library.ITEM_HAS_HOLD_FINE:
					System.out.println("Item has hold and fine");
					break;
				case Library.ITEM_HAS_FINE:
					System.out.println("Item has fine ");
					break;
				default:
					System.out.println("An error has occurred");
			}
			if (!yesOrNo("Return more Items?")) {
				break;
			}
		} while (true);
	}
	
	/**
	 * Method to be called for removing items. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for removing
	 * items.
	 */
	public void removeLoanableItems() {
		int result;
		do {
			String itemID = getToken("Enter Item id");
			result = library.removeLoanableItem(itemID);
			switch (result) {
				case Library.ITEM_NOT_FOUND:
					System.out.println("No such item in Library");
					break;
				case Library.ITEM_ISSUED:
					System.out.println("Item is currently checked out");
					break;
				case Library.ITEM_HAS_HOLD:
					System.out.println("Item has a hold");
					break;
				case Library.OPERATION_FAILED:
					System.out.println("Item could not be removed");
					break;
				case Library.OPERATION_COMPLETED:
					System.out.println(" Item has been removed");
					break;
				default:
					System.out.println("An error has occurred");
			}
			if (!yesOrNo("Remove more items?")) {
				break;
			}
		} while (true);
	}
	
	/**
	 * Method to be called for placing a hold. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for placing a
	 * hold.
	 */
	public void placeHold() {
		String memberID = getToken("Enter member id");
		String itemID = getToken("Enter item id");
		int duration = getNumber("Enter duration of hold");
		int result = library.placeHold(memberID, itemID, duration);
		switch (result) {
			case Library.ITEM_NOT_FOUND:
				System.out.println("No such Item in Library");
				break;
			case Library.ITEM_NOT_ISSUED:
				System.out.println(" Item is not checked out");
				break;
			case Library.NO_SUCH_MEMBER:
				System.out.println("Not a valid member ID");
				break;
			case Library.HOLD_PLACED:
				System.out.println("A hold has been placed");
				break;
			case Library.ITEM_NOT_HOLDABLE:
				System.out.println("Item can not be held");
				break;
			default:
				System.out.println("An error has occurred");
		}
	}
	
	/**
	 * Method to be called for removing a holds. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for removing a
	 * hold.
	 */
	public void removeHold() {
		String memberID = getToken("Enter member id");
		String itemID = getToken("Enter item id");
		int result = library.removeHold(memberID, itemID);
		switch (result) {
			case Library.ITEM_NOT_FOUND:
				System.out.println("No such Item in Library");
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
	 * Method to be called for processing items. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for processing
	 * items.
	 */
	public void processHolds() {
		Member result;
		do {
			String itemID = getToken("Enter item id");
			result = library.processHold(itemID);
			if (result != null) {
				System.out.println(result);
			}
			else {
				System.out.println("No valid holds left");
			}
			if (!yesOrNo("Process more items?")) {
				break;
			}
		} while (true);
	}
	
	/**
	 * Method to be called for displaying transactions. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for displaying
	 * transactions.
	 */
	public void getTransactions() {
		Iterator<Transaction> result;
		String memberID = getToken("Enter member id");
		Calendar date = getDate("Please enter the date for which you want records as mm/dd/yy");
		result = library.getTransactions(memberID, date);
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
	 * Method to be called for saving the Library object. Uses the appropriate
	 * Library method for saving.
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
	 * Method to be called for retrieving saved data. Uses the appropriate
	 * Library method for retrieval.
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
	 * Prints the items in a unique format for each type of item.
	 */
	public void printFormatted() {
		library.processLoanableItems(PrintFormat.instance());
	}
	
	/**
	 * Orchestrates the whole process. Calls the appropriate method for the
	 * different functionalties.
	 */
	public void process() {
		int command;
		help();
		while ((command = getCommand()) != EXIT) {
			switch (command) {
				case ADD_MEMBER:
					addMember();
					break;
				case ADD_ITEMS:
					addLoanableItems();
					break;
				case ISSUE_ITEMS:
					issueLoanableItems();
					break;
				case RETURN_ITEMS:
					returnLoanableItems();
					break;
				case REMOVE_ITEMS:
					removeLoanableItems();
					break;
				case RENEW_ITEMS:
					renewLoanableItems();
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
				case PRINT_FORMATTED:
					printFormatted();
					break;
				case DISPLAY_OVERDUE_ITEMS:
					displayOverdueItems();
					break;
				case REMOVE_MEMBER:
					removeMember();
					break;
				case CHANGE_DUEDATE:
					changeDueDate();
					break;
				case CHANGE_SECTION:
					changeSection();
					break;
				case PAY_FINES:
					payFines();
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
	
	// added methods
	
	/**
	 * Method to be called for displaying all items that are
	 * overdue
	 */
	public void displayOverdueItems() {
		library.processLoanableItems(OverdueVisitor.instance());
		if (OverdueVisitor.instance().getCount() == 0) {
			System.out.println("There are currently no overdue items in the library");
		}
	}
	
	/**
	 * Method to be called for removing a member. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for removing a
	 * member.
	 */
	public void removeMember() {
		String memberID;
		
		List<Member> arrayOfMembers = library.displayRemovableMembers();
		
		if (arrayOfMembers.isEmpty()) {
			System.out.println("No removable members to display");
			return;
		}
		else {
			memberID = getToken("Enter member id or -1 to go back: ");
			
			if (memberID.equals("-1")) {
				return;
			}
			if (library.searchMembership(memberID) == null) {
				System.out.println("No such member");
				return;
			}
			
			library.removeMember(memberID);
		}
	}
	
	/**
	 * Change the section the item is stored in
	 * ex: from normal to reserved
	 */
	private void changeSection() {
		// show items
		while (true) {
			List<LoanableItem> items = library.getLoanableItemsFiltered();
			int counter = 0;
			
			// display setup
			System.out.println("Enter line number of desired book to move");
			System.out.println("-1: back");
			
			// loop through collection
			for (LoanableItem item : items) {
				String section = item.getIsInReservedSection() ? "Reserved" : "Normal";
				System.out.println((counter++) + ": [" + item.getId() + "] " + item.getTitle() + " (" + section + ")");
			}
			
			// validate range
			int index = getNumber("Book: ");
			if (index < -1 || index >= items.size()) {
				System.out.println("Invalid selection...");
				System.out.println("--------------------");
				continue;
			}
			else if (index == -1) {
				// exit condition
				break;
			}
			else {
				// switch item
				System.out.println("Book moved to reserved section");
				LoanableItem selectedItem = items.get(index);
				selectedItem.setIsInReservedSection(!selectedItem.getIsInReservedSection());
			}
			
			// check to process more items
			if (!yesOrNo("Process more items?")) {
				break;
			}
		}
	}
	
	/**
	 * Method to be called for changing the due date for a loanable item.
	 * Prompts the user for the appropriate values and uses the appropriate
	 * Library method for changing due date.
	 */
	public void changeDueDate() {
		int result;
		do {
			String itemId = getToken("Enter item id");
			Calendar date = getDate("Please enter new due date as mm/dd/yy");
			result = library.changeDueDate(itemId, date);
			if (result == Library.ITEM_NOT_FOUND) {
				System.out.println("No such item in the library");
			}
			else {
				System.out.println("Due date succesfully changed");
			}
			if (!yesOrNo("Change due date for more items?")) {
				break;
			}
		} while (true);
	}
	
	/**
	 * Method to be called for paying a member fine. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for paying a
	 * member fine
	 */
	public void payFines() {
		String memberID = getToken("Enter member id or -1 to go back: ");
		
		if (memberID.equals("-1")) {
			return;
		}
		
		double fines = library.getMemberFines(memberID);
		if (fines == -1) {
			System.out.println("Member does not exist.");
			return;
		}
		if (fines < 0.01) {
			System.out.println("Member has no fines to pay.");
			return;
		}
		System.out.printf("Total fines: $%.2f \n", fines);
		double payment;
		try {
			payment = Double.parseDouble(getToken("Enter payment amount: "));
		}
		catch (NumberFormatException e) {
			System.out.println("Invalid input.");
			return;
		}
		double result = library.payFine(memberID, payment);
		if (result == -1) {
			System.out.println("Fine was not paid.");
		}
		else {
			System.out.printf("Paid $" + String.format("%.2f", payment) + "; remaining balance is $%.2f \n", result);
		}
	}
	
}// end class
