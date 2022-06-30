package librarysystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import business.*;
import dataaccess.DataAccessFacade;

public class CheckoutBooksWindow extends JFrame implements LibWindow {

    private JPanel mainPane;
    private JTextField memberField;
    private JTextField isbnField;
    private JTextField checkoutDateField;
    private JTextField dueDateField;

    private CheckoutBooksWindow() {
    }

    public static final CheckoutBooksWindow INSTANCE = new CheckoutBooksWindow();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;

    public JPanel getMainPanel() {
        return mainPane;
    }

    /**
     * Launch the application.
     */

    /**
     * Create the frame.
     */
    public void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        mainPane = new JPanel();
        mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainPane.setLayout(null);
        setContentPane(mainPane);

        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setBounds(17, 57, 31, -31);
        mainPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Member ID");
        lblNewLabel_1.setBounds(17, 10, 77, 16);
        mainPane.add(lblNewLabel_1);

        memberField = new JTextField();
        memberField.setBounds(135, 5, 130, 26);
        mainPane.add(memberField);
        memberField.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("ISBN Number");
        lblNewLabel_2.setBounds(17, 38, 94, 16);
        mainPane.add(lblNewLabel_2);

        isbnField = new JTextField();
        isbnField.setBounds(135, 33, 130, 26);
        mainPane.add(isbnField);
        isbnField.setColumns(10);

        JLabel lblNewLabel_3 = new JLabel("Checkout date");
        lblNewLabel_3.setBounds(17, 66, 111, 16);
        mainPane.add(lblNewLabel_3);

        checkoutDateField = new JTextField();
        checkoutDateField.setBounds(135, 61, 130, 26);
        mainPane.add(checkoutDateField);
        checkoutDateField.setColumns(10);

        JLabel lblNewLabel_4 = new JLabel("Due date");
        lblNewLabel_4.setBounds(17, 94, 61, 16);
        mainPane.add(lblNewLabel_4);

        dueDateField = new JTextField();
        dueDateField.setBounds(135, 89, 130, 26);
        mainPane.add(dueDateField);
        dueDateField.setColumns(10);

        JButton btnNewButton = new JButton("Checkout Book");
        JButton backButton = new JButton("Back");
        btnNewButton.setBounds(200, 138, 169, 35);
        backButton.setBounds(44, 138, 106, 35);
        backButton.addActionListener(e -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        });
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataAccessFacade daf = new DataAccessFacade();
                try {
                    validateEmptyFields();
                    String id = memberField.getText().trim();
                    String isbn = isbnField.getText().trim();
                    String checkout_date = checkoutDateField.getText().trim();
                    String due_date = dueDateField.getText().trim();
                    if (checkMemberAndBookExist(id, isbn, daf)) {
                        if (validateDate(checkout_date)) {
                            if (validateDate(due_date)) {
                                Book book = getBook(isbn, daf);
                                CheckOutRecord cr = new CheckOutRecord(id, new ArrayList<>() {
                                    {
                                        add(new CheckoutRecordEntry(LocalDate.of(Integer.parseInt(checkout_date.split("/")[2]),
                                                Integer.parseInt(checkout_date.split("/")[0]),
                                                Integer.parseInt(checkout_date.split("/")[1])),
                                                LocalDate.of(Integer.parseInt(due_date.split("/")[2]),
                                                        Integer.parseInt(due_date.split("/")[0]),
                                                        Integer.parseInt(due_date.split("/")[1])), book));
                                    }
                                });
                                if (daf.createCheckOut(cr)) {
                                    ci.deleteBook(book.getIsbn());
                                    LibrarySystem.hideAllWindows();
                                    LibrarySystem.INSTANCE.setVisible(true);
                                    JOptionPane.showMessageDialog(btnNewButton, "Book named " + book.getTitle() + " is successfully checked out", "Success", 1);
                                }
                            }
                        }
                    }

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(btnNewButton, ex.getMessage(), "error", 1);
                }
            }

            private Boolean validateDate(String date) {
                for (int i = 0; i < date.length(); i++) {
                    if (Character.isAlphabetic(date.charAt(i))) {
                        JOptionPane.showMessageDialog(btnNewButton, "Please enter valid date like month/day/year 12/20/2022", "error", 1);
                        return false;
                    }
                }
                if (date.length() != 10) {
                    JOptionPane.showMessageDialog(btnNewButton, "Please enter valid date like month/day/year 12/18/2022", "error", 1);
                    return false;
                }
                return true;
            }


            private boolean checkMemberAndBookExist(String id, String isbn, DataAccessFacade daf) {
                boolean memberExists = false;
                Object[] objects = daf.readMemberMap().values().toArray();
                ArrayList<LibraryMember> members = new ArrayList(Arrays.asList(objects));
                for (LibraryMember mem : members) {
                    if (id.equals(mem.getMemberId())) {
                        memberExists = true;
                        break;
                    }
                }
                if (!memberExists) {
                    JOptionPane.showMessageDialog(btnNewButton, "Member with this ID does not exist", "error", 1);
                    return false;
                } else {
                    boolean bookExists = checkBook(isbn, daf);
                    if (!bookExists) {
                        JOptionPane.showMessageDialog(btnNewButton, "Book with this ID does not exist", "error", 1);
                        return false;
                    }
                }
                return true;
            }
        });
        mainPane.add(btnNewButton);
        mainPane.add(backButton);
        //getContentPane().add(mainPane);
        isInitialized = true;
    }

    private boolean checkBook(String isbn, DataAccessFacade daf) {
        boolean bookExists = false;
        Object[] bookObjects = daf.readBooksMap().values().toArray();
        ArrayList<Book> books = new ArrayList(Arrays.asList(bookObjects));
        for (Book book : books) {
            if (isbn.equals(book.getIsbn())) {
                bookExists = true;
                break;
            }
        }
        return bookExists;
    }

    private Book getBook(String isbn, DataAccessFacade daf) {
        Book book = null;
        Object[] bookObjects = daf.readBooksMap().values().toArray();
        ArrayList<Book> books = new ArrayList(Arrays.asList(bookObjects));
        for (Book b : books) {
            if (isbn.equals(b.getIsbn())) {
                book = b;
                break;
            }
        }
        return book;
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        isInitialized = val;

    }

    public void validateEmptyFields() throws Exception {
        if (memberField.getText().isEmpty())
            throw new Exception("Member ID cannot be empty");
        if (isbnField.getText().isEmpty())
            throw new Exception("ISBN Number cannot be empty");
        if (checkoutDateField.getText().isEmpty())
            throw new Exception("Checkout Date cannot be empty");
        if (dueDateField.getText().isEmpty())
            throw new Exception("Due Date cannot be empty");

    }
}
