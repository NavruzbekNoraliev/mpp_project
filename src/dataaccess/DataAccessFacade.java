package dataaccess;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import business.*;
import dataaccess.DataAccessFacade.StorageType;

public class DataAccessFacade implements DataAccess {

	enum StorageType {
		BOOKS, MEMBERS, USERS, BOOKCOPY, CHECKOUT;
	}

	public static final String OUTPUT_DIR = System.getProperty("user.dir")
			// + "\\src\\dataaccess\\storage";
			+ "/src/dataaccess/storage";
	public static final String DATE_PATTERN = "MM/dd/yyyy";

	//save LibraryMember to the file 
	public void saveNewMember(LibraryMember member) {
		HashMap<String, LibraryMember> mems = readMemberMap();
		String memberId = member.getMemberId();
		mems.put(memberId, member);
		saveToStorage(StorageType.MEMBERS, mems);
	}
	
	//save new Book to the file
	public void saveNewBook(Book book) {
		HashMap<String, Book> books = readBooksMap();
		String isbn = book.getIsbn();
		books.put(isbn, book);
		saveToStorage(StorageType.BOOKS, books);
	}
	
	//retrieve HashMap<String, Book> from file
	@SuppressWarnings("unchecked")
	public HashMap<String, Book> readBooksMap() {
		// Returns a Map with name/value pairs being
		// isbn -> Book
		return (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
	}
	
	//retrieve HashMap<String, LibraryMember> from file
	@SuppressWarnings("unchecked")
	public HashMap<String, LibraryMember> readMemberMap() {
		// Returns a Map with name/value pairs being
		// memberId -> LibraryMember
		return (HashMap<String, LibraryMember>) readFromStorage(
				StorageType.MEMBERS);
	}
	
	//verify if a book is taken by a library member
	public String isBookCopyAvailable(String isbn, int bookCopyNumber) {
		HashMap<String, Book> books = (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
		String ret = "";
		
		HashMap<String, CheckoutRecordEntry> booksCheckedOut = (HashMap<String, CheckoutRecordEntry>) readFromStorage(StorageType.CHECKOUT);
		for (Map.Entry<String, CheckoutRecordEntry> entry : booksCheckedOut.entrySet()) {
			boolean flag = false;
			if (entry.getValue().getBook().getIsbn().equals(isbn)) {
				for(int i=0; i< entry.getValue().getBook().getCopies().length; i++) {
					if(entry.getValue().getBook().getCopies()[i].getCopyNum() == bookCopyNumber
							&& !entry.getValue().getBook().getCopies()[i].isAvailable()) {
						ret = entry.getKey() +";"+ entry.getValue().getDueDate();
						flag = true;
						break;
					}
				}
				if(flag)
					break;
			}
		}

		return ret;
	}
	
	//retrieve HashMap<String, User> from file
	@SuppressWarnings("unchecked")
	public HashMap<String, User> readUserMap() {
		// Returns a Map with name/value pairs being
		// userId -> User
		return (HashMap<String, User>) readFromStorage(StorageType.USERS);
	}

	//add a copy in a book and save all the books again to the file
	public void AddCopyAndSaveToStorage(String isbn) {
		// retrieve the HashMap<String, Book>() of copy from storage
		// change the copy of the book and save again
		HashMap<String, Book> books = readBooksMap();
		Book b = books.get(isbn);
		b.addCopy();
		// change the copy from inside the book
		books.put(isbn, b);

		saveToStorage(StorageType.BOOKS, books);
	}
	//save List<Book> to the file
	static void loadBookMap(List<Book> bookList) {
		HashMap<String, Book> books = new HashMap<String, Book>();
		bookList.forEach(book -> books.put(book.getIsbn(), book));
		saveToStorage(StorageType.BOOKS, books);
	}
	//save List<User> to the file
	static void loadUserMap(List<User> userList) {
		HashMap<String, User> users = new HashMap<String, User>();
		userList.forEach(user -> users.put(user.getUsername(), user));
		saveToStorage(StorageType.USERS, users);
	}
	//save List<LibraryMember> to the file
	static void loadMemberMap(List<LibraryMember> memberList) {
		HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
		memberList.forEach(member -> members.put(member.getMemberId(), member));
		saveToStorage(StorageType.MEMBERS, members);
	}

	public Book getBookByIsbn(String isbn) {
		HashMap<String, Book> books = readBooksMap();
		return books.get(isbn);
	}

	static Object readFromStorage(StorageType type) {
		ObjectInputStream in = null;
		Object retVal = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			in = new ObjectInputStream(Files.newInputStream(path));
			retVal = in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
		return retVal;
	}

	final static class Pair<S, T> implements Serializable {

		S first;
		T second;

		Pair(S s, T t) {
			first = s;
			second = t;
		}

		@Override
		public boolean equals(Object ob) {
			if (ob == null)
				return false;
			if (this == ob)
				return true;
			if (ob.getClass() != getClass())
				return false;
			@SuppressWarnings("unchecked")
			Pair<S, T> p = (Pair<S, T>) ob;
			return p.first.equals(first) && p.second.equals(second);
		}

		@Override
		public int hashCode() {
			return first.hashCode() + 5 * second.hashCode();
		}

		@Override
		public String toString() {
			return "(" + first.toString() + ", " + second.toString() + ")";
		}

		private static final long serialVersionUID = 5399827794066637059L;
	}

	@Override
	public void deleteBook(String isbn) {
		HashMap<String, Book> books = readBooksMap();
		books.remove(isbn);
		List<Book> retval = new ArrayList<>();
		retval.addAll(books.values());
		loadBookMap(retval);
	}

	@Override
	public HashMap<String, CheckoutRecordEntry> readCheckoutRecordMap() {
		return (HashMap<String, CheckoutRecordEntry>) readFromStorage(StorageType.CHECKOUT);
	}

	@Override
	public boolean createCheckOut(CheckOutRecord checkOutRecord) {
		HashMap<String, CheckoutRecordEntry> checkOut = new HashMap();
		if (readCheckoutRecordMap() != null) {
			checkOut.putAll(readCheckoutRecordMap());
		}
		for (CheckoutRecordEntry record : checkOutRecord.getCheckoutEntries()) {
			checkOut.put(checkOutRecord.getMemberID(), record);
		}
		return saveToStorage(StorageType.CHECKOUT, checkOut);
	}

	///// load methods - these place test data into the storage area
	///// - used just once at startup

	static boolean saveToStorage(StorageType type, Object ob) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ob);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}
	}
}
