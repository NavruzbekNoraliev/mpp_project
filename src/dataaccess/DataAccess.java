package dataaccess;

import java.util.HashMap;
import business.Book;
import business.BookCopy;
import business.CheckOutRecord;
import business.CheckoutRecordEntry;
import business.LibraryMember;
import dataaccess.DataAccessFacade.StorageType;

public interface DataAccess {
    public HashMap<String,Book> readBooksMap();
    public HashMap<String,User> readUserMap();
    public HashMap<String, LibraryMember> readMemberMap();
    public void saveNewMember(LibraryMember member);
    public void deleteBook(String isbn);
    public HashMap<String, CheckoutRecordEntry> readCheckoutRecordMap();
    public boolean createCheckOut(CheckOutRecord checkOutRecord);
    public HashMap<String,BookCopy> readBookCopysMap();
}
