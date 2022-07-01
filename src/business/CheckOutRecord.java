package business;

import java.io.Serializable;
import java.util.ArrayList;

public class CheckOutRecord implements Serializable {


    ArrayList<CheckoutRecordEntry> books;
    private String memberID;

    public CheckOutRecord(String memberID, ArrayList<CheckoutRecordEntry> books) {

        this.memberID = memberID;
        this.books = books;
    }

    public ArrayList<CheckoutRecordEntry> getCheckoutEntries() {
        return books;
    }


    //    public Book getBook() {
//        return book;
//    }
//
//    public LocalDate getCheckOutDate() {
//        return checkOutDate;
//    }
//
//    public LocalDate getDueDate() {
//        return dueDate;
//    }

    public String getMemberID() {
        return memberID;
    }


    @Override
    public String toString() {
        return "CheckOutRecord{" +
//                "book=" + book +
//                ", checkOutDate=" + checkOutDate +
//                ", dueDate=" + dueDate +
                '}';
    }
}
