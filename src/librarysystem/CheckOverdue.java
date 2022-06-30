package librarysystem;

import business.LoginException;
import business.SystemController;

import javax.swing.*;
import java.awt.*;

import static librarysystem.Main.centerFrameOnDesktop;

public class CheckOverdue extends JFrame implements LibWindow  {
    private static final long serialVersionUID = 1L;
    public static final CheckOverdue INSTANCE = new CheckOverdue();
    private boolean isInitialized = false;

    private JPanel mainPanel;
    private JPanel upperHalf;
    private JPanel middleHalf;
    private JPanel lowerHalf;
    private JPanel container;

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
    private CheckOverdue () {}

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
        //setSize(660, 500);


    }
    private void defineUpperHalf() {
        upperHalf = new JPanel();
        upperHalf.setLayout(new BorderLayout());
        defineTopPanel();
        defineMiddlePanel();
        defineLowerPanel();
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
    private void defineSearchTextPanel(){
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



    private void defineLowerPanel() {
        lowerPanel = new JPanel();
        loginButton = new JButton("Login");
        addLoginButtonListener(loginButton);
        lowerPanel.add(loginButton);
    }




    private void defineMiddlePanel() { // Define book table
        middlePanel=new JPanel();
        String data[][]={ {"101","Amit","670000","670000","670000"},
                {"102","Jai","780000","670000","670000"},
                {"101","Sachin","700000","670000","670000"}};
        String column[]={"ISBN","TITLE","COPYNUM","LIBMEM","DUE" };
        table = new JTable(data, column);
        middlePanel.add(new JScrollPane(table));
        table.setBounds(30,40,200,300);
        middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        middlePanel.add(table);

    }




    private void defineLeftTextPanel() {

        JPanel topText = new JPanel();
        JPanel bottomText = new JPanel();
        topText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
        bottomText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));

        username = new JTextField(10);
        label = new JLabel("Username");
        label.setFont(Util.makeSmallFont(label.getFont()));
        topText.add(username);
        bottomText.add(label);

        leftTextPanel = new JPanel();
        leftTextPanel.setLayout(new BorderLayout());
        leftTextPanel.add(topText,BorderLayout.NORTH);
        leftTextPanel.add(bottomText,BorderLayout.CENTER);
    }
    private void defineRightTextPanel() {

        JPanel topText = new JPanel();
        JPanel bottomText = new JPanel();
        topText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
        bottomText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));

        password = new JPasswordField(10);
        label = new JLabel("Password");
        label.setFont(Util.makeSmallFont(label.getFont()));
        topText.add(password);
        bottomText.add(label);

        rightTextPanel = new JPanel();
        rightTextPanel.setLayout(new BorderLayout());
        rightTextPanel.add(topText,BorderLayout.NORTH);
        rightTextPanel.add(bottomText,BorderLayout.CENTER);
    }

    private void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        });
    }

    private void addSearchButtonListener(JButton button) {
        button.addActionListener(evt -> {

//            JOptionPane.showMessageDialog(this,"Successful Login");
        });
    }
    private void addLoginButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            JOptionPane.showMessageDialog(this,"Successful Login");

        });
    }
}
