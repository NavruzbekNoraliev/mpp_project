package librarysystem;

import business.Book;
import business.BookCopy;
import dataaccess.DataAccessFacade;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;

public class CheckOverdue extends JFrame implements LibWindow {
    private static final long serialVersionUID = 1L;
    public static final CheckOverdue INSTANCE = new CheckOverdue();
    private boolean isInitialized = false;

    private JPanel mainPanel;
    private JPanel upperHalf;
    private JPanel middleHalf;
    private JPanel lowerHalf;

    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;
    private JPanel leftTextPanel;
    private JPanel rightTextPanel;

    private JPanel searchBookTextPanel;

    private JTextField username;
    private JTextField isbn;
    private JTextField password;
    private JLabel label;
    private Book selectedBook;
    private JButton loginButton;
    private JButton searchButton;

    private JTable table;


    public boolean isInitialized() {
        return isInitialized;
    }

    public void isInitialized(boolean val) {
        isInitialized = val;
    }

    private JTextField messageBar = new JTextField();

    public void clear() {
        messageBar.setText("");
    }

    /* This class is a singleton */
    private CheckOverdue() {
    }

    public void init() {
        mainPanel = new JPanel();
        defineUpperHalf();
        defineMiddleHalf();
        defineLowerHalf();
        BorderLayout bl = new BorderLayout();
        bl.setVgap(30);
        mainPanel.setLayout(bl);

        mainPanel.add(upperHalf, BorderLayout.NORTH);
        mainPanel.add(middleHalf, BorderLayout.CENTER);
        mainPanel.add(lowerHalf, BorderLayout.SOUTH);
        getContentPane().add(mainPanel);
        isInitialized(true);
        pack();
        setSize(380, 250);


    }

    private void defineUpperHalf() {
        upperHalf = new JPanel();
        upperHalf.setLayout(new BorderLayout());
        defineTopPanel();
        defineMiddlePanel();
        upperHalf.add(topPanel, BorderLayout.NORTH);
    }

    private void defineTopPanel() {                   // Define search text field and button
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        defineSearchBtn();
        defineSearchTextPanel();
        topPanel.add(searchBookTextPanel);
        topPanel.add(searchButton);
    }

    private void defineSearchBtn() {
        searchButton = new JButton("Search");   // Define Search Btn
        addSearchButtonListener(searchButton);       // Add Btn action listener
    }

    private void defineSearchTextPanel() {
        isbn = new JTextField(18);
        searchBookTextPanel = new JPanel();
        searchBookTextPanel.setLayout(new BorderLayout());
        searchBookTextPanel.add(isbn, BorderLayout.NORTH);
    }

    private void defineMiddleHalf() {
        middleHalf = new JPanel();
        middleHalf.setLayout(new BorderLayout());
        JSeparator s = new JSeparator();
        s.setOrientation(SwingConstants.HORIZONTAL);
        middleHalf.add(s, BorderLayout.SOUTH);
    }

    private void defineLowerHalf() {
        lowerHalf = new JPanel();
        lowerHalf.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        addBackButtonListener(backButton);
        lowerHalf.add(backButton);
    }
    
    private String[][] generateData(Book book) {
        int rowLength = book.getCopies().length;
        String[][] data = new String[rowLength][5];
        BookCopy[] bookCopies = book.getCopies();
        DataAccessFacade d = new DataAccessFacade();



        for (int i = 0; i < rowLength; i++) {
            String j = d.isBookCopyAvailable(isbn.getText().trim(), bookCopies[i].getCopyNum());
            String[] s = j.split(";");
            data[i][0] = book.getIsbn();
            data[i][1] = book.getTitle();
            data[i][2] = String.valueOf(bookCopies[i].getCopyNum());
            data[i][3] = j !=""? s[0]: "none";
            data[i][4] = j !=""? s[1]: "available";
        }
        System.out.println(Arrays.deepToString(data));
        return data;
    }

    private void popularizeTable() { // Define book table

        String data[][] = generateData(selectedBook);
        String column[] = {"ISBN", "TITLE", "COPYNUM", "LIBMEM", "DUE"};
        if (table != null) {
            table.setModel(new DefaultTableModel(data, column));
        } else {
            table = new JTable(data, column);
        }
        middlePanel.add(table.getTableHeader(), BorderLayout.PAGE_START);
        middlePanel.add(table, BorderLayout.CENTER);
    }

    private void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());
    }


    private void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        });
    }

    private void addSearchButtonListener(JButton button) {
        button.addActionListener(evt -> {
        	if(isbn.getText().isEmpty())
        		JOptionPane.showMessageDialog(button, "ISBN field cannot be empty", "error", 1);
        	else {
        		DataAccessFacade d = new DataAccessFacade();
                selectedBook = d.getBookByIsbn(isbn.getText().trim());
                popularizeTable();
                upperHalf.add(middlePanel, BorderLayout.CENTER);
                SwingUtilities.updateComponentTreeUI(mainPanel);
        	}
            
        });
    }
}
