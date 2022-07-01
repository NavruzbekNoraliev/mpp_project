package librarysystem;

import business.Book;
import business.CheckoutRecordEntry;
import business.ControllerInterface;
import business.SystemController;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CheckCheckoutsWindow extends JFrame implements LibWindow {
    private static final long serialVersionUID = 1L;
    public static final CheckCheckoutsWindow INSTANCE = new CheckCheckoutsWindow();
    private boolean isInitialized = false;
    ControllerInterface ci = new SystemController();
    private JPanel mainPanel;
    private JPanel upperHalf;
    private JPanel middleHalf;
    private JPanel lowerHalf;

    private JPanel topPanel;
    private JPanel middlePanel;

    private JPanel searchBookTextPanel;

    private JTextField searchField;
    private JButton searchButton;

    private JTable table;
    String data[][];


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
    private CheckCheckoutsWindow() {
    }

    public void init() {
        mainPanel = new JPanel();
        defineUpperHalf();
        defineMiddleHalf();
        defineLowerHalf();
        BorderLayout bl = new BorderLayout();
        mainPanel.setBounds(100, 100, 450, 300);
        bl.setVgap(30);
        mainPanel.setLayout(bl);

        mainPanel.add(upperHalf, BorderLayout.NORTH);
        mainPanel.add(middleHalf, BorderLayout.CENTER);
        mainPanel.add(lowerHalf, BorderLayout.SOUTH);
        getContentPane().add(mainPanel);
        isInitialized(true);
        pack();
        //setSize(660, 500);


    }

    private void defineUpperHalf() {
        upperHalf = new JPanel();
        upperHalf.setLayout(new BorderLayout());
        defineTopPanel();
        defineMiddlePanel();
        upperHalf.add(topPanel, BorderLayout.NORTH);
        upperHalf.add(middlePanel, BorderLayout.CENTER);
//        upperHalf.add(lowerPanel, BorderLayout.SOUTH);
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
        searchField = new JTextField(18);
        searchBookTextPanel = new JPanel();
        JLabel jLabel = new JLabel("Member ID");
        searchBookTextPanel.setLayout(new BorderLayout());
        searchBookTextPanel.add(searchField, BorderLayout.EAST);
        searchBookTextPanel.add(jLabel, BorderLayout.WEST);
    }

    private void defineMiddleHalf() {
        middleHalf = new JPanel();
        middleHalf.setLayout(new BorderLayout());
        JSeparator s = new JSeparator();
        s.setOrientation(SwingConstants.HORIZONTAL);
        //middleHalf.add(Box.createRigidArea(new Dimension(0,50)));
        middleHalf.add(s, BorderLayout.SOUTH);

    }

    private void defineLowerHalf() {
        lowerHalf = new JPanel();
        lowerHalf.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        addBackButtonListener(backButton);
        lowerHalf.add(backButton);
    }


    private void defineMiddlePanel() { // Define book table
        middlePanel = new JPanel();
//        data = {{"101", "Amit", "670000", "670000", "670000"},
//                {"102", "Jai", "780000", "670000", "670000"},
//                {"101", "Sachin", "700000", "670000", "670000"}};
        String column[] = {"ISBN", "TITLE", "COPYNUM", "LIBMEM", "DUE"};
//        if (data != null) {
//            table = new JTable(data, column);
//            middlePanel.add(new JScrollPane(table));
//            table.setBounds(30, 40, 600, 300);
//            middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
//            middlePanel.add(table);
//        }

    }


    private void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        });
    }

    private void addSearchButtonListener(JButton button) {
        button.addActionListener(evt -> {

            String memberId = searchField.getText();
            if (memberId.isEmpty()) {
                JOptionPane.showMessageDialog(button, "Member ID cannot be empty", "Error", 1);
            } else {
                HashMap<String, CheckoutRecordEntry> ids = ci.allCheckouts();
                if (ids.containsKey(memberId)) {
                    Book book = ids.get(memberId).getBook();
                    Object[][] data = {{book.getIsbn(), book.getTitle(), book.getNumCopies(), memberId, ids.get(memberId).getDueDate()}};
                    String column[] = {"ISBN", "TITLE", "COPYNUM", "LIBMEM", "DUE"};
                    if (table == null) {
                        table = new JTable(data, column);
                        middlePanel.add(new JScrollPane(table));
                        table.setBounds(30, 40, 600, 300);
                        table.getColumnModel().getColumn(0).setPreferredWidth(300);
                        middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                        middlePanel.add(table);
                        mainPanel.add(middleHalf, BorderLayout.CENTER);
                    }
                    table.setModel(new TableModel() {
                        @Override
                        public int getRowCount() {
                            return 1;
                        }

                        @Override
                        public int getColumnCount() {
                            return 5;
                        }

                        @Override
                        public String getColumnName(int columnIndex) {
                            return column[columnIndex];
                        }

                        @Override
                        public Class<?> getColumnClass(int columnIndex) {
                            return Object.class;
                        }

                        @Override
                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                            return false;
                        }

                        @Override
                        public Object getValueAt(int rowIndex, int columnIndex) {
                            return data[rowIndex][columnIndex];
                        }

                        @Override
                        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                            data[rowIndex][columnIndex] = aValue;
                        }

                        @Override
                        public void addTableModelListener(TableModelListener l) {

                        }

                        @Override
                        public void removeTableModelListener(TableModelListener l) {

                        }
                    });
                    SwingUtilities.updateComponentTreeUI(CheckCheckoutsWindow.INSTANCE);
                    System.out.println("added");
                }else{
                    JOptionPane.showMessageDialog(button, "This member did not get any book from our library", "Info", 1);
                }
            }
        });
    }
}
