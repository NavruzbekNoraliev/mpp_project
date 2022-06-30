package librarysystem;

import static librarysystem.Main.centerFrameOnDesktop;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import business.CheckoutRecordEntry;
import business.ControllerInterface;
import business.SystemController;

public class LibrarySystem extends JFrame implements LibWindow {
    ControllerInterface ci = new SystemController();
    public final static LibrarySystem INSTANCE = new LibrarySystem();
    JPanel mainPanel;
    JMenuBar menuBar;
    JMenu options;
    JMenuItem login, allBookIds, allMemberIds, checkOverdue, checkoutBook, allCheckouts, checkCheckouts, addLibraryMember, addBookCopy;
    String pathToImage;
    private boolean isInitialized = false;

    private static LibWindow[] allWindows = {
            LibrarySystem.INSTANCE,
            LoginWindow.INSTANCE,
            AllMemberIdsWindow.INSTANCE,
            AddLibrabryMember.INSTANCE,
            AllBookIdsWindow.INSTANCE,
            CheckoutBooksWindow.INSTANCE,
            AllCheckoutsWindow.INSTANCE,
            CheckCheckoutsWindow.INSTANCE,
            AddBookCopy.INSTANCE
    };

    public static void hideAllWindows() {

        for (LibWindow frame : allWindows) {
            frame.setVisible(false);

        }
    }

    private LibrarySystem() {
    }

    public void init() {
        formatContentPane();
        setPathToImage();
        insertSplashImage();

        createMenus();
        // pack();
        setSize(660, 500);
        isInitialized = true;
    }

    private void formatContentPane() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 1));
        getContentPane().add(mainPanel);
    }

    private void setPathToImage() {
        String currDirectory = System.getProperty("user.dir");
        pathToImage = currDirectory
                // +"\\src\\librarysystem\\library.jpg";
                + "/src/librarysystem/libr.jpeg";

    }

    private void insertSplashImage() {
        ImageIcon image = new ImageIcon(pathToImage);
        mainPanel.add(new JLabel(image));
    }

    private void createMenus() {
        menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
        addMenuItems();
        setJMenuBar(menuBar);
    }

    private void addMenuItems() {
        options = new JMenu("Options");
        menuBar.add(options);
        login = new JMenuItem("Logout");
        login.addActionListener(new LoginListener());
        checkOverdue = new JMenuItem("Check Overdue");
        checkOverdue.addActionListener(new CheckOverDueListener());
        allBookIds = new JMenuItem("All Book Ids");
        allBookIds.addActionListener(new AllBookIdsListener());
        allMemberIds = new JMenuItem("All Member Ids");
        allMemberIds.addActionListener(new AllMemberIdsListener());
        checkoutBook = new JMenuItem("Checkout books");
        checkoutBook.addActionListener(new CheckoutBookListener());
        allCheckouts = new JMenuItem("All Checkouts");
        allCheckouts.addActionListener(new AllCheckoutsListener());
        checkCheckouts = new JMenuItem("Check Checkouts");
        checkCheckouts.addActionListener(new CheckCheckoutsListener());
        addLibraryMember = new JMenuItem("Add Library Member");
        addLibraryMember.addActionListener(new AddLibrabryMemberListener());

        addBookCopy = new JMenuItem("Add Book Copy");
        addBookCopy.addActionListener(new AddBookCopyListener());

        options.add(login);
        options.add(allBookIds);
        options.add(allMemberIds);
        options.add(checkOverdue);
        options.add(addLibraryMember);
        options.add(addBookCopy);
        options.add(checkoutBook);
        options.add(allCheckouts);
        options.add(checkCheckouts);
    }

    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            LoginWindow.INSTANCE.init();
            Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
            LoginWindow.INSTANCE.setVisible(true);
        }
    }

    class CheckoutBookListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            CheckoutBooksWindow.INSTANCE.init();
            Util.centerFrameOnDesktop(CheckoutBooksWindow.INSTANCE);
            CheckoutBooksWindow.INSTANCE.setVisible(true);

        }

    }

    class CheckCheckoutsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            CheckCheckoutsWindow.INSTANCE.init();
            Util.centerFrameOnDesktop(CheckCheckoutsWindow.INSTANCE);
            CheckCheckoutsWindow.INSTANCE.setBounds(100,100,600,300);
            CheckCheckoutsWindow.INSTANCE.setVisible(true);

        }

    }

    class AllCheckoutsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            AllCheckoutsWindow.INSTANCE.init();

            HashMap<String, CheckoutRecordEntry> ids = ci.allCheckouts();

            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, CheckoutRecordEntry> entry : ids.entrySet()) {
                if (!sb.toString().contains(entry.getKey())) {
                    sb.append("Member: " + entry.getKey() + "\n");
                }
                sb.append("  " + entry.getValue().getBook().getIsbn() + "  " + entry.getValue().getBook().getTitle()
                        + "  " + entry.getValue().getBook().getNumCopies() + "\n");
            }

            System.out.println(sb.toString());
            AllCheckoutsWindow.INSTANCE.setData(sb.toString());
            AllCheckoutsWindow.INSTANCE.pack();
            // AllBookIdsWindow.INSTANCE.setSize(660,500);
            Util.centerFrameOnDesktop(AllCheckoutsWindow.INSTANCE);
            AllCheckoutsWindow.INSTANCE.setVisible(true);

        }

    }

    class AllBookIdsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            AllBookIdsWindow.INSTANCE.init();
            List<String> ids = ci.allBookIds();
            Collections.sort(ids);
            StringBuilder sb = new StringBuilder();
            for (String s : ids) {
                sb.append(s + "\n");
            }
            System.out.println(sb.toString());
            AllBookIdsWindow.INSTANCE.setData(sb.toString());
            AllBookIdsWindow.INSTANCE.pack();
            Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
            AllBookIdsWindow.INSTANCE.setVisible(true);
        }
    }

    class CheckOverDueListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            CheckOverdue.INSTANCE.init();
            CheckOverdue.INSTANCE.setTitle("Check Overdue");
            CheckOverdue.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Util.centerFrameOnDesktop(CheckOverdue.INSTANCE);
            CheckOverdue.INSTANCE.setVisible(true);
        }
    }

    class AllMemberIdsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            AllMemberIdsWindow.INSTANCE.init();
            AllMemberIdsWindow.INSTANCE.pack();
            AllMemberIdsWindow.INSTANCE.setVisible(true);
            LibrarySystem.hideAllWindows();
            AllBookIdsWindow.INSTANCE.init();
            List<String> ids = ci.allMemberIds();
            Collections.sort(ids);
            StringBuilder sb = new StringBuilder();
            for (String s : ids) {
                sb.append(s + "\n");
            }
            System.out.println(sb.toString());
            AllMemberIdsWindow.INSTANCE.setData(sb.toString());
            AllMemberIdsWindow.INSTANCE.pack();
            Util.centerFrameOnDesktop(AllMemberIdsWindow.INSTANCE);
            AllMemberIdsWindow.INSTANCE.setVisible(true);
        }
    }

    class AddLibrabryMemberListener implements ActionListener {

        // @Override
        // public void actionPerformed(ActionEvent e) {
        // LibrarySystem.hideAllWindows();
        // AddLibrabryMember.INSTANCE.init();
        // AddLibrabryMember.INSTANCE.pack();
        // centerFrameOnDesktop(AddLibrabryMember.INSTANCE);
        // AddLibrabryMember.INSTANCE.setVisible(true);
        // }

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();

            AddLibrabryMember.INSTANCE.init();
            AddLibrabryMember.INSTANCE.pack();
            centerFrameOnDesktop(LibrarySystem.INSTANCE);
            AddLibrabryMember.INSTANCE.setVisible(true);
        }

    }

    class AddBookCopyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();

            AddBookCopy.INSTANCE.init();
            AddBookCopy.INSTANCE.pack();
            AddBookCopy.INSTANCE.setVisible(true);
        }

    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        isInitialized = val;

    }

}
