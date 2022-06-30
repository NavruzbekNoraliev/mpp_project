package librarysystem;

import business.LoginException;
import business.SystemController;

import javax.swing.*;
import java.awt.*;

import static librarysystem.Main.centerFrameOnDesktop;


public class LoginWindow extends JFrame implements LibWindow {
    public static final LoginWindow INSTANCE = new LoginWindow();
    private boolean isInitialized = false;
    private JPanel mainPanel;
    private JPanel upperHalf;
    private JPanel middleHalf;

    private JPanel topPanel;
    private JPanel usernamePanel;
    private JPanel passwordPanel;
    private JPanel loginPanel;
    private JPanel leftTextPanel;
    private JPanel rightTextPanel;

    private JTextField username;
    private JTextField password;
    private JLabel label;
    private JButton loginButton;
    private JButton logoutButton;


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
    private LoginWindow() {}

    public void init() {
        mainPanel = new JPanel();
        defineUpperHalf();
        defineMiddleHalf();
        BorderLayout bl = new BorderLayout();
        bl.setVgap(30);
        mainPanel.setLayout(bl);
        mainPanel.add(upperHalf, BorderLayout.NORTH);
        mainPanel.add(middleHalf, BorderLayout.CENTER);
        getContentPane().add(mainPanel);
        isInitialized(true);
        pack();
        setSize(330, 250);
    }

    private void defineUpperHalf() {
        upperHalf = new JPanel();
        upperHalf.setLayout(new BorderLayout());
        defineTopPanel();
        defineUsernamePanel();
        definePasswordPanel();
        upperHalf.add(topPanel, BorderLayout.NORTH);
        upperHalf.add(usernamePanel, BorderLayout.CENTER);
        upperHalf.add(passwordPanel, BorderLayout.SOUTH);
    }

    private void defineMiddleHalf() {
        middleHalf = new JPanel();
        middleHalf.setLayout(new BorderLayout());
        loginPanel = new JPanel();
        loginButton = new JButton("Log In");
        loginPanel.setPreferredSize(new Dimension(200, 30)); // set size for button
        addLoginButtonListener(loginButton);
        loginPanel.add(loginButton);
        middleHalf.add(loginPanel);
    }


    private void defineTopPanel() {
        topPanel = new JPanel();
        JPanel intPanel = new JPanel(new BorderLayout());
        intPanel.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.CENTER);
        JLabel loginLabel = new JLabel("LibMS System");
        loginLabel.setFont(new Font("Serif", Font.ITALIC,20));
        Util.adjustLabelFont(loginLabel, Color.ORANGE.darker(), true);
        intPanel.add(loginLabel, BorderLayout.CENTER);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(intPanel);
    }

    private void defineUsernamePanel() {
        usernamePanel = new JPanel();
        defineUsernameTextPanel();
        usernamePanel.add(leftTextPanel);
    }

    private void definePasswordPanel() {
        passwordPanel = new JPanel();
        definePasswordTextPanel();
        passwordPanel.add(rightTextPanel);
    }

    private void defineUsernameTextPanel() {
        JPanel topText = new JPanel();
        JPanel bottomText = new JPanel();
        topText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        bottomText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        username = new JTextField(15);
        label = new JLabel("Username");
        label.setFont(new Font("Serif", Font.ITALIC, 14));
        topText.add(username);
        bottomText.add(label);
        leftTextPanel = new JPanel();
        leftTextPanel.setLayout(new BorderLayout());
        leftTextPanel.add(topText, BorderLayout.SOUTH);
        leftTextPanel.add(bottomText, BorderLayout.CENTER);
    }

    private void definePasswordTextPanel() {
        JPanel topText = new JPanel();
        JPanel bottomText = new JPanel();
        topText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        bottomText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        password = new JPasswordField(15);
        label = new JLabel("Password");
        label.setFont(new Font("Serif", Font.ITALIC, 14));
        topText.add(password);
        bottomText.add(label);
        rightTextPanel = new JPanel();
        rightTextPanel.setLayout(new BorderLayout());
        rightTextPanel.add(topText, BorderLayout.SOUTH);
        rightTextPanel.add(bottomText, BorderLayout.CENTER);
    }


    private void addLoginButtonListener(JButton button) {
        button.addActionListener(evt -> {
            SystemController sc = new SystemController();
            String user = username.getText().trim();
            String pwd = password.getText().trim();
            try {
                sc.login(user, pwd);
                JOptionPane.showMessageDialog(this, "Welcome to LibMS\nYour status is " + sc.currentAuth, "Success!", 3);
                LibrarySystem.INSTANCE.setTitle("Welcome to LiBMS");
                LibrarySystem.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                LibrarySystem.INSTANCE.init();
                centerFrameOnDesktop(LibrarySystem.INSTANCE);
                LibrarySystem.hideAllWindows();
                LibrarySystem.INSTANCE.setVisible(true);
            } catch (LoginException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error!!!", 0);
                throw new RuntimeException(e);
            }
        });
    }


}
