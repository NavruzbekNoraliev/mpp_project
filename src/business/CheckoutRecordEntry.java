package business;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckoutRecordEntry implements Serializable {

    private LocalDate dueDate;
    private LocalDate checkoutDate;
    private Book book;

    public CheckoutRecordEntry(LocalDate checkoutDate, LocalDate dueDate, Book bookCopy) {
        this.dueDate = dueDate;
        this.checkoutDate = checkoutDate;
        this.book = bookCopy;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public Book getBook() {
        return book;
    }
}
