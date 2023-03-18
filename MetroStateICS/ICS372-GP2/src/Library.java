
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Implementation of the Library system. It acts as a Facade for all operations.
 *
 * @author mh6624pa
 */
public class Library implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int ITEM_NOT_FOUND = 1;
	public static final int ITEM_NOT_ISSUED = 2;
	public static final int ITEM_HAS_HOLD = 3;
	public static final int ITEM_ISSUED = 4;
	public static final int HOLD_PLACED = 5;
	public static final int NO_HOLD_FOUND = 6;
	public static final int OPERATION_COMPLETED = 7;
	public static final int OPERATION_FAILED = 8;
	public static final int NO_SUCH_MEMBER = 9;
	
	public static final int BOOK = 1;
	public static final int PERIODICAL = 2;
	public static final int DIGITALCAMERA = 3;
	public static final int DVD = 4;
	public static final int LAPTOP = 5;
	public static final int ITEM_HAS_HOLD_FINE = 9;
	public static final int ITEM_HAS_FINE = 4;
	public static final int ITEM_NOT_HOLDABLE = 10;
	private Catalog catalog;
	private MemberList memberList;
	private static Library library;
	
	/**
	 * Private for the singleton pattern Creates the catalog and member
	 * collection objects
	 */
	private Library() {
		catalog = Catalog.instance();
		memberList = MemberList.instance();
	}
	
	/**
	 * Supports the singleton pattern
	 *
	 * @return the singleton object
	 */
	public static Library instance() {
		if (library == null) {
			MemberIdServer.instance(); // instantiate all singletons
			return (library = new Library());
		}
		else {
			return library;
		}
	}
	
	/**
	 * Organizes the operations for adding a loanable item
	 *
	 * @param type
	 *            the type of item to be created
	 * @param title
	 *            item title
	 * @param author
	 *            author name (for applicable items
	 * @param id
	 *            item id
	 * @return the LoanableItem object created
	 */
	public LoanableItem addLoanableItem(int type, String title, String author, String id) {
		LoanableItem item = LoanableItemFactory.instance().createLoanableItem(type, title, author, id);
		if (catalog.insertLoanableItem(item)) {
			return (item);
		}
		return null;
	}
	
	/**
	 * Organizes the operations for adding a member
	 *
	 * @param name
	 *            member name
	 * @param address
	 *            member address
	 * @param phone
	 *            member phone
	 * @return the Member object created
	 */
	public Member addMember(String name, String address, String phone) {
		Member member = new Member(name, address, phone);
		if (memberList.insertMember(member)) {
			return (member);
		}
		return null;
	}
	
	/**
	 * Organizes the placing of a hold
	 *
	 * @param memberId
	 *            member's id
	 * @param itemId
	 *            item's id
	 * @param duration
	 *            for how long the hold should be valid in days
	 * @return indication on the outcome
	 */
	public int placeHold(String memberId, String itemId, int duration) {
		LoanableItem item = catalog.search(itemId);
		if (item == null) {
			return (ITEM_NOT_FOUND);
		}
		if (item.getBorrower() == null) {
			return (ITEM_NOT_ISSUED);
		}
		if (item.getIsInReservedSection()) {
			return (ITEM_NOT_HOLDABLE);
		}
		Member member = memberList.search(memberId);
		if (member == null) {
			return (NO_SUCH_MEMBER);
		}
		Hold hold = new Hold(member, item, duration);
		item.placeHold(hold);
		member.placeHold(hold);
		return (HOLD_PLACED);
	}
	
	/**
	 * Searches for a given member
	 *
	 * @param memberId
	 *            id of the member
	 * @return true iff the member is in the member list collection
	 */
	public Member searchMembership(String memberId) {
		return memberList.search(memberId);
	}
	
	/**
	 * Processes holds for a single item
	 *
	 * @param itemId
	 *            id of the item
	 * @return the member who should be notified
	 */
	public Member processHold(String itemId) {
		LoanableItem item = catalog.search(itemId);
		if (item == null) {
			return (null);
		}
		Hold hold = item.getNextHold();
		if (hold == null) {
			return (null);
		}
		hold.getMember().removeHold(itemId);
		hold.getLoanableItem().removeHold(hold.getMember().getId());
		return (hold.getMember());
	}
	
	/**
	 * Removes a hold for a specific item and member combination
	 *
	 * @param memberId
	 *            id of the member
	 * @param itemId
	 *            item id
	 * @return result of the operation
	 */
	public int removeHold(String memberId, String itemId) {
		Member member = memberList.search(memberId);
		if (member == null) {
			return (NO_SUCH_MEMBER);
		}
		LoanableItem item = catalog.search(itemId);
		if (item == null) {
			return (ITEM_NOT_FOUND);
		}
		return member.removeHold(itemId) && item.removeHold(memberId) ? OPERATION_COMPLETED : NO_HOLD_FOUND;
	}
	
	/**
	 * Organizes the issuing of an item
	 *
	 * @param memberId
	 *            member id
	 * @param itemId
	 *            item id
	 * @return the item issued
	 */
	public LoanableItem issueLoanableItem(String memberId, String itemId) {
		LoanableItem item = catalog.search(itemId);
		if (item == null) {
			return (null);
		}
		if (item.getBorrower() != null) {
			return (null);
		}
		Member member = memberList.search(memberId);
		if (member == null) {
			return (null);
		}
		if (!(item.issue(member) && member.issue(item))) {
			return null;
		}
		return (item);
	}
	
	/**
	 * Renews an item
	 *
	 * @param itemId
	 *            id of the item to be renewed
	 * @param memberId
	 *            member id
	 * @return the item renewed
	 */
	public LoanableItem renewItem(String itemId, String memberId) {
		LoanableItem loanableItem = catalog.search(itemId);
		if (loanableItem == null) {
			return (null);
		}
		Member member = memberList.search(memberId);
		if (member == null) {
			return (null);
		}
		if ((loanableItem.renew(member) && member.renew(loanableItem))) {
			return (loanableItem);
		}
		return (null);
	}
	
	/**
	 * Returns an iterator to the loanable items issued to a member
	 *
	 * @param memberId
	 *            member id
	 * @return iterator to the collection
	 */
	public Iterator<LoanableItem> getLoanableItems(String memberId) {
		Member member = memberList.search(memberId);
		if (member == null) {
			return (null);
		}
		else {
			return (member.getLoanableItemsIssued());
		}
	}
	
	/**
	 * Find loanable items (book) idea; implement a type filter
	 *
	 * @return Collection of Loanable Items
	 */
	public List<LoanableItem> getLoanableItemsFiltered(/* int type */) {
		List<LoanableItem> items = new ArrayList<>();
		
		// loop through catalog
		for (Iterator<LoanableItem> itemIterator = catalog.iterator(); itemIterator.hasNext();) {
			LoanableItem item = itemIterator.next();
			
			// match book types (idea: implement filter)
			if (item instanceof Book) {
				items.add(item);
			}
		}
		
		// give found loanable items
		return items;
	}
	
	/**
	 * Removes a specific item from the catalog
	 *
	 * @param itemId
	 *            id of the item
	 * @return a code representing the outcome
	 */
	public int removeLoanableItem(String itemId) {
		LoanableItem item = catalog.search(itemId);
		if (item == null) {
			return (ITEM_NOT_FOUND);
		}
		if (item.hasHold()) {
			return (ITEM_HAS_HOLD);
		}
		if (item.getBorrower() != null) {
			return (ITEM_ISSUED);
		}
		if (catalog.removeLoanableItem(itemId)) {
			return (OPERATION_COMPLETED);
		}
		return (OPERATION_FAILED);
	}
	
	/**
	 * Returns a single loanable item to the library
	 *
	 * @param itemId
	 *            id of the item to be returned
	 * @return a code representing the outcome
	 */
	public int returnLoanableItem(String itemId) {
		LoanableItem loanableItem = catalog.search(itemId);
		if (loanableItem == null) {
			return (ITEM_NOT_FOUND);
		}
		// compute fines
		double fine = loanableItem.borrowedBy.addFine(loanableItem.getFineAmount());
		
		Member member = loanableItem.returnItem();
		if (member == null) {
			return (ITEM_NOT_ISSUED);
		}
		
		// final steps
		if (!(member.returnItem(loanableItem))) {
			return (OPERATION_FAILED);
		}
		
		if (fine > 0.0) {
			if (loanableItem.hasHold()) {
				return (ITEM_HAS_HOLD_FINE);
			}
			else {
				return (ITEM_HAS_FINE);
			}
		}
		
		if (loanableItem.hasHold()) {
			return (ITEM_HAS_HOLD);
		}
		
		return (OPERATION_COMPLETED);
	}
	
	/**
	 * Returns an iterator to the transactions for a specific member on a
	 * certain date
	 *
	 * @param memberId
	 *            member id
	 * @param date
	 *            date of issue
	 * @return iterator to the collection
	 */
	public Iterator<Transaction> getTransactions(String memberId, Calendar date) {
		Member member = memberList.search(memberId);
		if (member == null) {
			return (null);
		}
		return member.getTransactions(date);
	}
	
	public void processLoanableItems(LoanableItemVisitor visitor) {
		for (Iterator<LoanableItem> itemIterator = catalog.iterator(); itemIterator.hasNext();) {
			itemIterator.next().accept(visitor);
		}
	}
	
	/**
	 * Retrieves a deserialized version of the library from disk
	 *
	 * @return a Library object
	 */
	public static Library retrieve() {
		try {
			FileInputStream file = new FileInputStream("LibraryData");
			ObjectInputStream input = new ObjectInputStream(file);
			input.readObject();
			MemberIdServer.retrieve(input);
			return library;
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}
		catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Serializes the Library object
	 *
	 * @return true iff the data could be saved
	 */
	public static boolean save() {
		ObjectOutputStream output = null;
		try {
			FileOutputStream file = new FileOutputStream("LibraryData");
			output = new ObjectOutputStream(file);
			output.writeObject(library);
			output.writeObject(MemberIdServer.instance());
			output.close();
			return true;
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Writes the object to the output stream
	 *
	 * @param output
	 *            the stream to be written to
	 */
	private void writeObject(java.io.ObjectOutputStream output) throws IOException {
		output.defaultWriteObject();
		output.writeObject(library);
	}
	
	/**
	 * Reads the object from a given stream
	 *
	 * @param input
	 *            the stream to be read
	 */
	private void readObject(java.io.ObjectInputStream input) throws IOException, ClassNotFoundException {
		input.defaultReadObject();
		if (library == null) {
			library = (Library)input.readObject();
		}
		else {
			input.readObject();
		}
	}
	
	/**
	 * String form of the library
	 */
	@Override
	public String toString() {
		return catalog + "\n" + memberList;
	}
	
	// added methods
	/**
	 * show list of all members that have no dues and have not
	 * borrowed any loanable items
	 *
	 * @return a list of all member that have no dues and
	 *         have not borrowed any items
	 */
	public List<Member> displayRemovableMembers() {
		List<Member> listOfRemovableMembers = new ArrayList<>();
		
		for (Iterator<Member> memberIterator = MemberList.instance().iterator(); memberIterator.hasNext();) {
			Member member = memberIterator.next();
			
			if (member.getItemsBorrowed().isEmpty() && member.getFines() == 0) {
				listOfRemovableMembers.add(member);
				System.out.println("Member id: " + member.getId());
			}
		}
		return listOfRemovableMembers;
	}
	
	/**
	 * method will remove any holds member has
	 * before permanently removing the member
	 *
	 * @param memberID
	 *            the member id
	 */
	public void removeMember(String memberID) {
		MemberList listOfMembers = MemberList.instance();
		Member member = memberList.search(memberID);
		List<Hold> listOfHolds = member.getItemsOnHold();
		
		if (!listOfHolds.isEmpty()) {
			for (Hold aItemOnHold : listOfHolds) {
				String itemID = aItemOnHold.getLoanableItem().getId();
				removeHold(memberID, itemID);
			}
		}
		listOfMembers.removeMember(memberID);
		System.out.println("Member Succesfully deleted");
	}
	
	/**
	 * method will change the due date of a checked out
	 * item
	 *
	 * @param itemId
	 *            the item id
	 * @param dueDate
	 *            the due date of item
	 */
	public int changeDueDate(String itemId, Calendar dueDate) {
		LoanableItem item = catalog.search(itemId);
		if (item == null) {
			return (ITEM_NOT_FOUND);
		}
		item.setDueDate(dueDate);
		return (OPERATION_COMPLETED);
	}
	
	/**
	 * method will obtain member fines
	 *
	 * @param memberID
	 *            the member id
	 */
	public double getMemberFines(String memberID) {
		Member member = searchMembership(memberID);
		if (member == null) {
			return -1;
		}
		return member.getFines();
	}
	
	/**
	 * method will obtain amount owed by member and
	 * pay fine
	 *
	 * @param memberID
	 *            the member id
	 * @param payment
	 *            the amount of payment made
	 */
	public double payFine(String memberID, double payment) {
		Member member = memberList.search(memberID);
		if (member == null) {
			return -1;
		}
		double totalOwed = member.getFines();
		if (totalOwed == 0 || totalOwed < payment) {
			return -1;
		}
		member.payFine(payment);
		return member.getFines();
	}
	
}// end class
