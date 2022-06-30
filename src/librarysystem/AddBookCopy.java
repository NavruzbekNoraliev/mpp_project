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

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			AddBookCopy dialog = new AddBookCopy();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
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
//			List Mylist = new List();
//			Mylist.setBounds(10, 11, 110, 208);
//			Mylist.addMouseListener(new MouseAdapter() {
//				public void mousePressed(MouseEvent me) {
//					System.out.println(me.paramString());
//				}
//			});
//			DataAccess da = new DataAccessFacade();
//			Collection<Book> books = da.readBooksMap().values();
//			for (Book b : books) {
//				Mylist.add(b.getTitle());
//			}
//			contentPanel.add(Mylist);
			
			JComboBox<String> DropDown = new JComboBox<String>();
			DropDown.setBounds(25, 25, 220, 20);
			DataAccess da = new DataAccessFacade();
			Collection<Book> books = da.readBooksMap().values();
			HashMap<String,Book> li = da.readBooksMap();
			
			for (Book b : books) {
				DropDown.addItem(b.getTitle());
			}
			DropDown.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	                JComboBox comboBox = (JComboBox) event.getSource();

	                Object selected = comboBox.getSelectedItem();
	                System.out.println(selected);
	                int num = li.get(selected).getNumCopies();
                	NumberOfCopyes.setText(String.valueOf(num));
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
		
		JLabel lblNewLabel_2 = new JLabel("Select a Book:");
		lblNewLabel_2.setBounds(25, 11, 99, 14);
		contentPanel.add(lblNewLabel_2);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

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
