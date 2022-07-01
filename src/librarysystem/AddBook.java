package librarysystem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import business.Address;
import business.Author;
import business.Book;
import business.LibraryMember;
import dataaccess.DataAccessFacade;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Button;
import java.awt.Dimension;

public class AddBook extends JDialog implements LibWindow, FocusListener{

	private final JPanel contentPanel = new JPanel();
	private JTextField TitleValue;
	private JTextField ISBNValue;
	private JTextField AuthorValue;
	private JTextField MaxCheckoutLenghtValue;
	private JTextField NumberOfCopysValue;
	private boolean isInitialized = false;
	public static final AddBook INSTANCE = new AddBook();
	private JOptionPane exceptions = new JOptionPane();
	
	private AddBook() {
		
	}

	@Override
	public void init() {
		INSTANCE.setTitle("Add Book");
		INSTANCE.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 200, 450, 300);
		INSTANCE.setPreferredSize(new Dimension(500, 300));
		INSTANCE.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblNewLabel_1 = new JLabel("Book Title");
			lblNewLabel_1.setBounds(10, 11, 59, 14);
			contentPanel.add(lblNewLabel_1);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("ISBN");
			lblNewLabel_2.setBounds(20, 45, 36, 14);
			contentPanel.add(lblNewLabel_2);
		}
		{
			JLabel lblNewLabel_3 = new JLabel("Author");
			lblNewLabel_3.setBounds(20, 84, 49, 14);
			contentPanel.add(lblNewLabel_3);
		}
		{
			JLabel lblNewLabel_4 = new JLabel("Max Checkout Lenght");
			lblNewLabel_4.setBounds(193, 11, 123, 14);
			contentPanel.add(lblNewLabel_4);
		}
		{
			JLabel lblNewLabel_5 = new JLabel("Number of Copys");
			lblNewLabel_5.setBounds(203, 45, 104, 14);
			contentPanel.add(lblNewLabel_5);
		}
		{
			TitleValue = new JTextField();
			TitleValue.setBounds(79, 8, 96, 20);
			contentPanel.add(TitleValue);
			TitleValue.setColumns(10);
		}
		{
			ISBNValue = new JTextField();
			ISBNValue.setBounds(79, 42, 96, 20);
			contentPanel.add(ISBNValue);
			ISBNValue.setColumns(10);
		}
		{
			AuthorValue = new JTextField();
			AuthorValue.setBounds(79, 81, 96, 20);
			contentPanel.add(AuthorValue);
			AuthorValue.setColumns(10);
		}
		{
			MaxCheckoutLenghtValue = new JTextField();
			MaxCheckoutLenghtValue.setBounds(318, 11, 86, 20);
			contentPanel.add(MaxCheckoutLenghtValue);
			MaxCheckoutLenghtValue.setColumns(10);
		}
		{
			NumberOfCopysValue = new JTextField();
			NumberOfCopysValue.setBounds(317, 42, 87, 20);
			contentPanel.add(NumberOfCopysValue);
			NumberOfCopysValue.setColumns(10);
		}
		{
			Button btnSave = new Button("Save");
			btnSave.setBounds(179, 145, 70, 22);
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
	                    validateEmptyFields();
	                    List<Author> authors = new ArrayList<Author>();
	                    authors.add(new Author(AuthorValue.getText()));
	                    
	                    Book b = new Book(ISBNValue.getText(), TitleValue.getText(), Integer.parseInt(MaxCheckoutLenghtValue.getText()), authors);
	                    NumberOfCopysValue.getText();
	                    DataAccessFacade daf = new DataAccessFacade();
	                    daf.saveNewBook(b);
	                    JOptionPane.showMessageDialog(btnSave, "Book Saved!", "Success", 1);
	                    cleanFields();
	                } catch (Exception ex) {
	                    System.out.println(ex.getMessage());
	                    JOptionPane.showMessageDialog(btnSave, ex.getMessage(), "error", 1);
	                }
				}
			});
			contentPanel.add(btnSave);
			
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				addBackButtonListener(cancelButton);
				buttonPane.add(cancelButton);
			}
		}
		contentPanel.add(exceptions);
		
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isInitialized() {
		// TODO Auto-generated method stub
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		// TODO Auto-generated method stub
		isInitialized = val;
	}
	
	private void addBackButtonListener(JButton button) {
        button.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        });
    }
	
	public void validateEmptyFields() throws Exception {
        if (TitleValue.getText().isEmpty())
            throw new Exception("Title cannot be empty");
        if (ISBNValue.getText().isEmpty())
            throw new Exception("ISBN cannot be empty");
        if (AuthorValue.getText().isEmpty())
            throw new Exception("Author cannot be empty");
        if (MaxCheckoutLenghtValue.getText().isEmpty())
            throw new Exception("Max Checkout Lenght cannot be empty");
        if (NumberOfCopysValue.getText().isEmpty())
            throw new Exception("Number Of Copys cannot be empty");
       
    }
	
	 private void cleanFields() {
	    	TitleValue.setText("");
	    	ISBNValue.setText("");
	    	AuthorValue.setText("");
	    	MaxCheckoutLenghtValue.setText("");
	    	NumberOfCopysValue.setText("");
	    }

}
