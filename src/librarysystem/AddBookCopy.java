package librarysystem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import business.Address;
import business.Book;
import business.BookCopy;
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Choice;

public class AddBookCopy extends JDialog implements LibWindow, FocusListener {

	private final JPanel contentPanel = new JPanel();
	private JTextField NumberOfCopyes;
	private boolean isInitialized = false;
	public static final AddBookCopy INSTANCE = new AddBookCopy();
	private String isbnBookSelected;
	private JOptionPane exceptions = new JOptionPane();
	
	private AddBookCopy() {
		
	}

	@Override
	public void init() {
		INSTANCE.setTitle("Add Book Copy");
		INSTANCE.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 200, 450, 300);
		INSTANCE.setPreferredSize(new Dimension(500, 300));
		INSTANCE.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JComboBox<String> DropDown = new JComboBox<String>();
			DropDown.setBounds(25, 25, 220, 20);
			DataAccess da = new DataAccessFacade();
			Collection<Book> books = da.readBooksMap().values();
			for (Book b : books) {
				DropDown.addItem(b.getIsbn());
			}
			DropDown.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	                JComboBox comboBox = (JComboBox) event.getSource();

	                Object selected = comboBox.getSelectedItem();
	                isbnBookSelected = selected.toString().trim();
	                //reload the number of copys
	                refreshNumCopyes();
                	
	            }
	        });
			contentPanel.add(DropDown);
		}

		JLabel lblNewLabel_1 = new JLabel("Number of Copyes:");
		lblNewLabel_1.setBounds(280, 11, 144, 14);
		contentPanel.add(lblNewLabel_1);

		NumberOfCopyes = new JTextField();
		NumberOfCopyes.setBounds(279, 25, 134, 20);
		contentPanel.add(NumberOfCopyes);
		NumberOfCopyes.setColumns(10);

		JButton btnAddCopy = new JButton("Add Copy");
		btnAddCopy.setBounds(278, 56, 89, 23);
		contentPanel.add(btnAddCopy);
		btnAddCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(NumberOfCopyes.getText().isEmpty())
					JOptionPane.showMessageDialog(btnAddCopy, "Need to select one record", "error", 1);
				else {
					DataAccessFacade da = new DataAccessFacade();
					da.AddCopyAndSaveToStorage(isbnBookSelected);
					refreshNumCopyes();
				}
			}
		});
		
		JLabel lblNewLabel_2 = new JLabel("Select a Book:");
		lblNewLabel_2.setBounds(25, 11, 99, 14);
		contentPanel.add(lblNewLabel_2);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Back");
				cancelButton.setActionCommand("Cancel");
				addBackButtonListener(cancelButton);
				buttonPane.add(cancelButton);
			}
		}
		contentPanel.add(exceptions);

	}
	
	private void addBackButtonListener(JButton button) {
        button.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        });
    }
	
	public void refreshNumCopyes() {
		DataAccess da = new DataAccessFacade();
		HashMap<String,Book> li = da.readBooksMap();
        int num = li.get(isbnBookSelected).getNumCopies();
    	NumberOfCopyes.setText(String.valueOf(num));
	}

	@Override
	public void focusGained(FocusEvent e) {
		List t = (List) e.getSource();
		System.out.println(t);
	}

	@Override
	public void focusLost(FocusEvent e) {
		List t = (List) e.getSource();
		System.out.println(t);

	}

	@Override
	public boolean isInitialized() {
		// TODO Auto-generated method stub
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;
	}
}
