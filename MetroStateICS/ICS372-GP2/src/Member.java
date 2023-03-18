
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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Implements the functionality of a member of the library.
 *
 * @author Brahma Dathan and Sarnath Ramnath
 */
public class Member implements Serializable, Matchable<String> {
	private static final long serialVersionUID = 1L;
	private String name;
	private String address;
	private String phone;
	private String id;
	private static final String MEMBER_STRING = "M";
	private List<LoanableItem> itemsBorrowed = new LinkedList<>();
	private List<Hold> itemsOnHold = new LinkedList<>();
	private List<Transaction> transactions = new LinkedList<>();
	LoanableItem loanableItem;
	
	private double fine = 0.0;
	private final static double MAXLOANABLEFINE = 5.0;
	
	/**
	 * Represents a single member
	 *
	 * @param name
	 *            name of the member
	 * @param address
	 *            address of the member
	 * @param phone
	 *            phone number of the member
	 */
	public Member(String name, String address, String phone) {
		this.name = name;
		this.address = address;
		this.phone = phone;
		id = MEMBER_STRING + (MemberIdServer.instance()).getId();
	}
	
	/**
	 * Stores the item as issued to the member
	 *
	 * @param loanableItem
	 *            the item to be issued
	 * @return true iff the item could be marked as issued. always true
	 *         currently
	 */
	public boolean issue(LoanableItem loanableItem) {
		if (itemsBorrowed.add(loanableItem)) {
			transactions.add(new Transaction("Item issued ", loanableItem.getTitle()));
			return true;
		}
		return false;
	}
	
	/**
	 * Marks the item as not issued to the member
	 *
	 * @param loanableItem
	 *            the item to be returned
	 * @return true iff the item could be marked as marked as returned
	 */
	public boolean returnItem(LoanableItem loanableItem) {
		if (itemsBorrowed.remove(loanableItem)) {
			transactions.add(new Transaction("Item returned ", loanableItem.getTitle()));
			return true;
		}
		return false;
	}
	
	/**
	 * Marks the item as renewed
	 *
	 * @param loanableItem
	 *            the item to be renewed
	 * @return true iff the item could be renewed
	 */
	public boolean renew(LoanableItem loanableItem) {
		for (ListIterator<LoanableItem> iterator = itemsBorrowed.listIterator(); iterator.hasNext();) {
			LoanableItem aLoanableItem = iterator.next();
			String id = aLoanableItem.getId();
			if (id.equals(loanableItem.getId())) {
				transactions.add(new Transaction("Item renewed ", loanableItem.getTitle()));
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets an iterator to the issued loanable items
	 *
	 * @return Iterator to the collection of issued items
	 */
	public Iterator<LoanableItem> getLoanableItemsIssued() {
		return (itemsBorrowed.listIterator());
	}
	
	/**
	 * Places a hold for the item
	 *
	 * @param hold
	 *            the item to be placed a hold
	 */
	public void placeHold(Hold hold) {
		transactions.add(new Transaction("Hold Placed ", hold.getLoanableItem().getTitle()));
		itemsOnHold.add(hold);
	}
	
	/**
	 * Removes a hold
	 *
	 * @param itemId
	 *            the item id for removing a hold
	 * @return true iff the hold could be removed
	 */
	public boolean removeHold(String itemId) {
		for (ListIterator<Hold> iterator = itemsOnHold.listIterator(); iterator.hasNext();) {
			Hold hold = iterator.next();
			String id = hold.getLoanableItem().getId();
			if (id.equals(itemId)) {
				transactions.add(new Transaction("Hold Removed ", hold.getLoanableItem().getTitle()));
				iterator.remove();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets an iterator to a collection of selected transactions
	 *
	 * @param date
	 *            the date for which the transactions have to be retrieved
	 * @return the iterator to the collection
	 */
	public Iterator<Transaction> getTransactions(Calendar date) {
		List<Transaction> result = new LinkedList<>();
		for (Transaction transaction : transactions) {
			if (transaction.onDate(date)) {
				result.add(transaction);
			}
		}
		return (result.iterator());
	}
	
	/**
	 * Getter for name
	 *
	 * @return member name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter for phone number
	 *
	 * @return phone number
	 */
	public String getPhone() {
		return phone;
	}
	
	/**
	 * Getter for address
	 *
	 * @return member address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Getter for id
	 *
	 * @return member id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Setter for name
	 *
	 * @param newName
	 *            member's new name
	 */
	public void setName(String newName) {
		name = newName;
	}
	
	/**
	 * Setter for address
	 *
	 * @param newName
	 *            member's new address
	 */
	public void setAddress(String newAddress) {
		address = newAddress;
	}
	
	/**
	 * Setter for phone
	 *
	 * @param newName
	 *            member's new phone
	 */
	public void setPhone(String newPhone) {
		phone = newPhone;
	}
	
	/**
	 * Checks whether the member is equal to another Member
	 *
	 * @param id
	 *            of the member who should be compared
	 * @return true iff the member ids match
	 */
	@Override
	public boolean equals(Object object) {
		Member member = (Member)object;
		return matches(member.getId());
	}
	
	/**
	 * String form of the member
	 */
	@Override
	public String toString() {
		String string = "Member name " + name + " address " + address + " id " + id + " phone " + phone;
		string += " borrowed: [";
		for (LoanableItem item : itemsBorrowed) {
			string += " " + item.getTitle();
		}
		string += "] holds: [";
		for (Hold hold : itemsOnHold) {
			string += " " + hold.getLoanableItem().getTitle();
		}
		string += "] transactions: [";
		for (Transaction transaction : transactions) {
			string += transaction;
		}
		string += "]";
		return string;
	}
	
	/**
	 * To implement the Matchable interface
	 *
	 * @param key
	 *            the member id
	 */
	@Override
	public boolean matches(String key) {
		return id.equals(key);
	}
	
	// added methods (getters)
	/**
	 * Getter for itemsBorroed
	 *
	 * @return itemsBorrowed
	 */
	public List<LoanableItem> getItemsBorrowed() {
		return itemsBorrowed;
	}
	
	/**
	 * Getter for itemsOnHold
	 *
	 * @return itemsOnHold
	 */
	public List<Hold> getItemsOnHold() {
		return itemsOnHold;
	}
	
	/**
	 * Getter for maxLoanableFine
	 *
	 * @return maxloanablefine
	 */
	public static double getMaxLoanableFine() {
		return MAXLOANABLEFINE;
	}
	
	/**
	 * Determines whether or not a member can borrow an item from the library,
	 * based on the total amount of fines the member has accumulated
	 *
	 * @param member
	 *            member
	 * @return T/F if the member can borrow items
	 */
	public boolean canBorrow() {
		return fine <= MAXLOANABLEFINE;
	}
	
	/**
	 * Getter for getFines
	 *
	 * @return fine
	 */
	public double getFines() {
		return fine;
	}
	
	/**
	 * method to pay fine
	 *
	 * @param double
	 *            payment
	 *            payment to be made
	 */
	public void payFine(double payment) {
		fine -= payment;
		if (fine < 0) {
			fine = 0;
		}
	}
	
	/**
	 * method to add fine
	 *
	 * @param fine
	 *            fine to be paid
	 * @return the amount of fine paid
	 */
	public double addFine(double fine) {
		return this.fine += fine;
		
	}
	
	/**
	 * Gets weather the member has a high value item
	 *
	 * @return T/F if member has currently borrowed a high value item
	 */
	public boolean hasHighValueItem() {
		// Logic is reserved section item or high value item
		for (LoanableItem item : itemsBorrowed) {
			// check for match
			if (item.getIsInReservedSection() || item.getIsHighValueItem()) {
				return true;
			}
		}
		
		// no match
		return false;
	}
	
}// end class
