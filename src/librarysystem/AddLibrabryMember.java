package librarysystem;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.CardLayout;
import java.awt.Dimension;


import business.Address;
import business.LibraryMember;
import dataaccess.DataAccessFacade;

import javax.swing.JTextField;
import java.awt.Window.Type;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddLibrabryMember extends JFrame implements LibWindow{

	private JPanel contentPane;
	private JTextField IdValue;
	private JTextField FirstNameValue;
	private JTextField LastNameValue;
	private JTextField StreetValue;
	private JTextField StateValue;
	private JTextField ZipValue;
	private JTextField TelefoneValue;
	private boolean isInitialized = false;
	public static final AddLibrabryMember INSTANCE = new AddLibrabryMember();
	private JLabel lblNewLabel_5;
	private JTextField CityValue;
	private JOptionPane exceptions;

	
	private AddLibrabryMember() {
		
	}

	@Override
	public void init() {
		
		INSTANCE.setTitle("Add Library Member");
		INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		INSTANCE.setPreferredSize(new Dimension(500, 300));
		INSTANCE.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
		//INSTANCE.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel LabelIdLibraryMember = new JLabel("ID");
		LabelIdLibraryMember.setBounds(38, 11, 18, 14);
		contentPane.add(LabelIdLibraryMember);
		
		IdValue = new JTextField();
		IdValue.setBounds(76, 8, 86, 20);
		contentPane.add(IdValue);
		IdValue.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("First Name");
		lblNewLabel.setBounds(10, 64, 61, 14);
		contentPane.add(lblNewLabel);
		
		FirstNameValue = new JTextField();
		FirstNameValue.setBounds(76, 61, 136, 20);
		contentPane.add(FirstNameValue);
		FirstNameValue.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Last Name");
		lblNewLabel_1.setBounds(222, 64, 61, 14);
		contentPane.add(lblNewLabel_1);
		
		LastNameValue = new JTextField();
		LastNameValue.setBounds(288, 61, 136, 20);
		contentPane.add(LastNameValue);
		LastNameValue.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Street");
		lblNewLabel_2.setBounds(20, 95, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		StreetValue = new JTextField();
		StreetValue.setBounds(76, 92, 136, 20);
		contentPane.add(StreetValue);
		StreetValue.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("State");
		lblNewLabel_3.setBounds(242, 127, 37, 14);
		contentPane.add(lblNewLabel_3);
		
		StateValue = new JTextField();
		StateValue.setBounds(288, 124, 136, 20);
		contentPane.add(StateValue);
		StateValue.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Zip");
		lblNewLabel_4.setBounds(47, 156, 24, 14);
		contentPane.add(lblNewLabel_4);
		
		ZipValue = new JTextField();
		ZipValue.setBounds(76, 153, 136, 20);
		contentPane.add(ZipValue);
		ZipValue.setColumns(10);
		
		JLabel Telefone = new JLabel("Telefone");
		Telefone.setBounds(232, 95, 51, 14);
		contentPane.add(Telefone);
		
		TelefoneValue = new JTextField();
		TelefoneValue.setBounds(288, 93, 136, 20);
		contentPane.add(TelefoneValue);
		TelefoneValue.setColumns(10);
		
		DataAccessFacade daf = new DataAccessFacade();
		
		Button button = new Button("Save");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					validateEmptyFields();
					Address a = new Address(StreetValue.getText(), CityValue.getText(), StateValue.getText(), ZipValue.getText()   );
					LibraryMember l = new LibraryMember(IdValue.getText(), FirstNameValue.getText(), LastNameValue.getText(), TelefoneValue.getText(), a);
					daf.saveNewMember(l);
				}
				catch(Exception ex) {
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(button, ex.getMessage(), "error", 1);
				}
			}
		});
		button.setBounds(167, 216, 106, 35);
		contentPane.add(button);
		
		lblNewLabel_5 = new JLabel("City");
		lblNewLabel_5.setBounds(34, 127, 37, 14);
		contentPane.add(lblNewLabel_5);
		
		CityValue = new JTextField();
		CityValue.setBounds(76, 123, 136, 20);
		contentPane.add(CityValue);
		CityValue.setColumns(10);
		exceptions = new JOptionPane();
		
		contentPane.add(exceptions);
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
	
	public void validateEmptyFields() throws Exception{
		if(IdValue.getText().isEmpty())
			throw new Exception("ID cannot be empty");
		if(FirstNameValue.getText().isEmpty())
			throw new Exception("First Name cannot be empty");
		if(LastNameValue.getText().isEmpty())
			throw new Exception("Last Name cannot be empty");
		if(StreetValue.getText().isEmpty())
			throw new Exception("Street cannot be empty");
		if(TelefoneValue.getText().isEmpty())
			throw new Exception("Telefone cannot be empty");
		if(CityValue.getText().isEmpty())
			throw new Exception("City cannot be empty");
		if(StateValue.getText().isEmpty())
			throw new Exception("State cannot be empty");
		if(ZipValue.getText().isEmpty())
			throw new Exception("Zip cannot be empty");
	}
	
}
