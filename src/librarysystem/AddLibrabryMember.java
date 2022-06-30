package librarysystem;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import business.Address;
import business.LibraryMember;
import dataaccess.DataAccessFacade;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import static librarysystem.Main.centerFrameOnDesktop;

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
		INSTANCE.setPreferredSize(new Dimension(630, 350));
		INSTANCE.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
		//INSTANCE.setBounds(100, 100, 450, 300);
		centerFrameOnDesktop(INSTANCE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel LabelIdLibraryMember = new JLabel("ID");
		LabelIdLibraryMember.setBounds(20, 11, 200, 14);
		contentPane.add(LabelIdLibraryMember);
		
		IdValue = new JTextField();
		IdValue.setBounds(100, 8, 200, 20);
		contentPane.add(IdValue);
		IdValue.setColumns(10);

//		First Name
		JLabel lblNewLabel = new JLabel("First Name");
		lblNewLabel.setBounds(20, 64, 200, 20);
		contentPane.add(lblNewLabel);
		
		FirstNameValue = new JTextField();
		FirstNameValue.setBounds(100, 61, 200, 20);
		contentPane.add(FirstNameValue);
		FirstNameValue.setColumns(10);

//		Last Name
		JLabel lblNewLabel_1 = new JLabel("Last Name");
		lblNewLabel_1.setBounds(320, 64, 200, 14);
		contentPane.add(lblNewLabel_1);
		
		LastNameValue = new JTextField();
		LastNameValue.setBounds(388, 61, 200, 20);
		contentPane.add(LastNameValue);
		LastNameValue.setColumns(10);

//		Street
		JLabel lblNewLabel_2 = new JLabel("Street");
		lblNewLabel_2.setBounds(20, 95, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		StreetValue = new JTextField();
		StreetValue.setBounds(100, 92, 200, 20);
		contentPane.add(StreetValue);
		StreetValue.setColumns(10);

//		Telephone
		JLabel Telephone = new JLabel("Telephone");
		Telephone.setBounds(320, 95, 200, 14);
		contentPane.add(Telephone);

		TelefoneValue = new JTextField();
		TelefoneValue.setBounds(388, 93, 200, 20);
		contentPane.add(TelefoneValue);
		TelefoneValue.setColumns(10);

//      City
		lblNewLabel_5 = new JLabel("City");
		lblNewLabel_5.setBounds(20, 127, 200, 14);
		contentPane.add(lblNewLabel_5);

		CityValue = new JTextField();
		CityValue.setBounds(100, 123, 200, 20);
		contentPane.add(CityValue);
		CityValue.setColumns(10);

//		State
		JLabel lblNewLabel_3 = new JLabel("State");
		lblNewLabel_3.setBounds(320, 127, 200, 14);
		contentPane.add(lblNewLabel_3);
		
		StateValue = new JTextField();
		StateValue.setBounds(388, 124, 200, 20);
		contentPane.add(StateValue);
		StateValue.setColumns(10);

//		ZIP
		JLabel lblNewLabel_4 = new JLabel("Zip");
		lblNewLabel_4.setBounds(20, 156, 50, 14);
		contentPane.add(lblNewLabel_4);
		
		ZipValue = new JTextField();
		ZipValue.setBounds(100, 153, 136, 20);
		contentPane.add(ZipValue);
		ZipValue.setColumns(10);
		

		
		DataAccessFacade daf = new DataAccessFacade();

		JButton saveButton = new JButton("Save");
		JButton backButton = new JButton("Back");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					validateEmptyFields();
					Address a = new Address(StreetValue.getText(), CityValue.getText(), StateValue.getText(), ZipValue.getText()   );
					LibraryMember l = new LibraryMember(IdValue.getText(), FirstNameValue.getText(), LastNameValue.getText(), TelefoneValue.getText(), a);
					daf.saveNewMember(l);
				}
				catch(Exception ex) {
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(saveButton, ex.getMessage(), "error", 1);
				}
			}
		});

		backButton.setBounds(200, 216, 106, 35);
		saveButton.setBounds(320, 216, 106, 35);
		saveButton.setForeground(Color.BLUE);
		contentPane.add(saveButton);
		contentPane.add(backButton);
		addBackButtonListener(backButton);


		exceptions = new JOptionPane();
		
		contentPane.add(exceptions);
	}


	private void addBackButtonListener(JButton button) {
		button.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
		});
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
