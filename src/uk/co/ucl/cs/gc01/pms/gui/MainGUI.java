package uk.co.ucl.cs.gc01.pms.gui;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.BadLocationException;

import org.omg.CORBA.portable.InputStream;

import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import java.awt.Choice;
import com.toedter.calendar.JDateChooser;

import uk.co.ucl.cs.gc01.pms.Database;
import uk.co.ucl.cs.gc01.pms.ForcedListSelectionModel;
import uk.co.ucl.cs.gc01.pms.ImageUploader;
import uk.co.ucl.cs.gc01.pms.Patient;
import uk.co.ucl.cs.gc01.pms.PatientlyImage;
import uk.co.ucl.cs.gc01.pms.PatientsTable;
import uk.co.ucl.cs.gc01.pms.Utility;

import javax.swing.JTable;
import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.border.LineBorder;
import javax.swing.UIManager;
import javax.swing.JPopupMenu;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.border.EtchedBorder;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTabbedPane;
import javax.swing.JPasswordField;

public class MainGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	//Login panel variables
	private JTextField textFieldLoginUsername;
	private JPasswordField passwordFieldLogin;
	private JTextField textFieldRegisterUsername;
	private JPasswordField passwordFieldRegister;
	private JPasswordField passwordFieldRegisterConfirm;

	
	//Main Panel variables
	private JTextField textFieldSearchFName;
	private JTextField textFieldSearchLName;
	private JTextField textFieldSearchIDNumber;
	private JTextField textFieldSearchMedCondition;
	private JTextField textFieldSearchTel;
	private JTextField textFieldSearchEmail;
	private JTable table;
	String [] columnNames = {"Export","ID","FIRST_NAME","LAST_NAME","DOB","MEDICAL_CONDITION","GENDER","TELEPHONE","EMAIL"};
	int currentPatientID;
	
	//Create Patient panel variables
	private JTextField textFieldAddPatientFirstName;
	private JTextField textFieldAddPatientLastName;
	private JTextField textFieldAddPatientMedCondition;
	private JTextField textFieldAddPatientTelephone;
	private JTextField textFieldAddPatientEmail;
	
	
	private String proPictureString =null;
	private String photoOneString = null;
	private String photoTwoString = null;
	private String photoThreeString =null;
	
	//Vedit panel variables
	private String veditProPictureString =null;
	private String veditPhotoOneString = null;
	private String veditPhotoTwoString = null;
	private String veditPhotoThreeString =null;
	
	private JTextField textFieldVeditFirstName;
	private JTextField textFieldVeditLastName;
	private JTextField textFieldVeditMedicaCondition;
	private JTextField textFieldVeditTelephone;
	private JTextField textFieldVeditEmail;
	
	private JDateChooser dateChooserVeditDOB;
	private JComboBox comboBoxVeditGender;
	private JLabel labelMedicalPhotoOneImage;
	private JLabel labelMedicalPhotoTwoImage;
	private JLabel labelMedicalPhotoThreeImage;
	private JLabel labelFileSelectedVeditProPicture;
	private JLabel profilePictureImageLabel;
	private JLabel labelFileSelectedPhoto1;
	private JLabel labelFileSelectedPhoto2;
	private JLabel labelFileSelectedPhoto3;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Start database, connect to it and create tables if doesn't already exist
					Database.startDatabase();					
					MainGUI frame = new MainGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 926, 600);
		
		//Create content pane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		//Create panels
		
		JPanel loginPanel = new JPanel();
		contentPane.add(loginPanel, "name_499002879047843");
		loginPanel.setLayout(null);
		loginPanel.setVisible(true);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		contentPane.add(mainPanel, "name_499033465829162");
		mainPanel.setLayout(null);
		mainPanel.setVisible(false);
		
		JPanel createPatientPanel = new JPanel();
		createPatientPanel.setBackground(Color.WHITE);
		contentPane.add(createPatientPanel, "name_556100927798396");
		createPatientPanel.setLayout(null);
		createPatientPanel.setVisible(false);
		
		JPanel veditPatientPanel = new JPanel();
		contentPane.add(veditPatientPanel, "name_645403010009629");
		veditPatientPanel.setVisible(false);
		
		JLabel loginLogoLabel = new JLabel("LOGO");
		Image loginLogoLabelImg = new ImageIcon(this.getClass().getResource("/mainLogo.png")).getImage();
		loginLogoLabel.setIcon(new ImageIcon(loginLogoLabelImg));
		loginLogoLabel.setBounds(6, 6, 200, 61);
		loginPanel.add(loginLogoLabel);
		
		//ADD A PANEL
		JPanel panelLoginRegisterTabs = new JPanel();
		panelLoginRegisterTabs.setBackground(Color.GRAY);
		panelLoginRegisterTabs.setBounds(83, 160, 765, 350);
		loginPanel.add(panelLoginRegisterTabs);
		panelLoginRegisterTabs.setLayout(null);
		
		//ADD A WELCOME LABEL
		JLabel lblLoginRegisterWelcome = new JLabel("Welcome");
		lblLoginRegisterWelcome.setBounds(339, 9, 94, 37);
		lblLoginRegisterWelcome.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblLoginRegisterWelcome.setForeground(Color.WHITE);
		panelLoginRegisterTabs.add(lblLoginRegisterWelcome);
		
		//ADD A TABBED PANE FOR LOGIN AND REGISTER TABS
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(102, 58, 570, 241);
		panelLoginRegisterTabs.add(tabbedPane);
		
		//ADD A LOGIN TAB
		JPanel loginTabbedPanel = new JPanel();
		tabbedPane.addTab("Login", null, loginTabbedPanel, null);
		loginTabbedPanel.setLayout(null);
		
		//USERNAME LABEL FOR LOGIN TAB
		JLabel lblLoginUsername = new JLabel("Username");
		lblLoginUsername.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblLoginUsername.setBounds(96, 29, 111, 31);
		loginTabbedPanel.add(lblLoginUsername);
		
		//PASSWORD LABEL FOR LOGIN TAB
		JLabel lblLoginPassword = new JLabel("Password");
		lblLoginPassword.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblLoginPassword.setBounds(96, 72, 111, 40);
		loginTabbedPanel.add(lblLoginPassword);
		
		//TEXT FIELD FOR USERNAME IN LOGIN TAB
		textFieldLoginUsername = new JTextField();
		textFieldLoginUsername.setBounds(238, 27, 187, 40);
		loginTabbedPanel.add(textFieldLoginUsername);
		textFieldLoginUsername.setColumns(10);
		
		//PASSWORD FIELD FOR PASSWORD IN LOGIN TAB
		passwordFieldLogin = new JPasswordField();
		passwordFieldLogin.setBounds(238, 75, 187, 40);
		loginTabbedPanel.add(passwordFieldLogin);
		
		//STATUS LABEL FOR LOGIN TAB, ERRORS TO BE DISPLAYED HERE IF LOGIN FAILS
		JLabel lblLoginStatus = new JLabel("");
		lblLoginStatus.setForeground(Color.RED);
		lblLoginStatus.setBounds(97, 148, 277, 16);
		loginTabbedPanel.add(lblLoginStatus);
		
		//OK BUTTON TO LOGIN
		JButton btnOkLogin = new JButton("Login");
		btnOkLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = textFieldLoginUsername.getText();
				char[] charPassword = passwordFieldLogin.getPassword();
				String password = "";
				for (int i=0;i<charPassword.length;i++) {
					password+=charPassword[i];
				}
				System.gc();
				boolean loginResult = Database.checkLogin(username, password);
				if (!loginResult) lblLoginStatus.setText("Invalid login details.");
				else {
					//LOGIN SUCCESSFUL, SET THE MAIN PANEL OF VISIBLE AND HIDE THE LOGIN PANEL
					loginPanel.setVisible(false);
					mainPanel.setVisible(true);
				}
			}
		});
		btnOkLogin.setBounds(414, 143, 117, 29);
		loginTabbedPanel.add(btnOkLogin);
		
		//Create register tab 
		//Create panel
		JPanel registerTabbedPanel = new JPanel();
		//Create tab
		tabbedPane.addTab("Register", null, registerTabbedPanel, null);
		registerTabbedPanel.setLayout(null);
		
		//Adde username label to registration panel
		JLabel lblRegisterUsername = new JLabel("Username");
		lblRegisterUsername.setBounds(55, 29, 111, 31);
		registerTabbedPanel.add(lblRegisterUsername);
		lblRegisterUsername.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		
		//Add user name text field to registration panel
		textFieldRegisterUsername = new JTextField();
		textFieldRegisterUsername.setBounds(238, 27, 187, 40);
		textFieldRegisterUsername.setColumns(10);
		registerTabbedPanel.add(textFieldRegisterUsername);
		
		//Add password label to register panel
		JLabel lblRegisterPassword = new JLabel("Password");
		lblRegisterPassword.setBounds(55, 72, 111, 40);
		lblRegisterPassword.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		registerTabbedPanel.add(lblRegisterPassword);

		//Add password field to register panel
		passwordFieldRegister = new JPasswordField();
		passwordFieldRegister.setBounds(238, 75, 187, 40);
		registerTabbedPanel.add(passwordFieldRegister);
		
		//Add password confirmation label to register panel
		JLabel lblRegisterConfirmPassword = new JLabel("Confirm password");
		lblRegisterConfirmPassword.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblRegisterConfirmPassword.setBounds(55, 118, 216, 40);
		registerTabbedPanel.add(lblRegisterConfirmPassword);
		
		//Add password confirm field to registration field 
		passwordFieldRegisterConfirm = new JPasswordField();
		passwordFieldRegisterConfirm.setBounds(238, 117, 187, 40);
		registerTabbedPanel.add(passwordFieldRegisterConfirm);
	
		JLabel lblStatusRegister = new JLabel("");
		lblStatusRegister.setForeground(UIManager.getColor("Button.select"));
		lblStatusRegister.setBounds(55, 165, 363, 16);
		registerTabbedPanel.add(lblStatusRegister);
		
		//OK button to registration panel
		JButton btnOKRegister = new JButton("Register");
		btnOKRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Check if password fields match
				String password = Utility.passwordToString(passwordFieldRegister.getPassword());
				String confirmPassword = Utility.passwordToString(passwordFieldRegisterConfirm.getPassword());
				if (password.equals(confirmPassword)) {
					//Try adding the user to the USERS table
					boolean addUserResult = Database.addUser(textFieldRegisterUsername.getText(), password, "user_role");
					if (addUserResult) {
						lblStatusRegister.setForeground(Color.BLUE);
						lblStatusRegister.setText("Registration successful.");
					}
					else {
						lblStatusRegister.setForeground(UIManager.getColor("Button.select"));
						lblStatusRegister.setText("User name already exists.");
					}
				
					
				}
				else {
					//Display an error message to the user that password does not match
					lblStatusRegister.setForeground(UIManager.getColor("Button.select"));
					lblStatusRegister.setText("Passwords do not match. Try again.");
					
				}
			}
		});
		btnOKRegister.setBounds(426, 160, 117, 29);
		registerTabbedPanel.add(btnOKRegister);
		
		JButton btnExitPatiently = new JButton("EXIT");
		btnExitPatiently.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showConfirmDialog(
						loginPanel,
					    "Are you sure you want to exit Patiently?",
					    "Confirm EXIT",
					    JOptionPane.YES_NO_OPTION);
				if (n==0) System.exit(0);
			}
		});
		btnExitPatiently.setBounds(390, 518, 143, 44);
		loginPanel.add(btnExitPatiently);
		
		//Add GUI components and functionality to mainPanel
		JLabel logoLabel = new JLabel("LOGO");
		Image img = new ImageIcon(this.getClass().getResource("/mainLogo.png")).getImage();
		logoLabel.setIcon(new ImageIcon(img));
		logoLabel.setBounds(6, 6, 200, 61);
		mainPanel.add(logoLabel);
		
		//Panel for advanced search
		JPanel panel = new JPanel();
		panel.setBounds(218, 6, 692, 125);
		mainPanel.add(panel);
		panel.setLayout(null);
		
		//Label for ADVANCED SEARCH
		JLabel lblSearchByField = new JLabel("Advanced search");
		lblSearchByField.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblSearchByField.setBounds(6, 6, 130, 16);
		panel.add(lblSearchByField);
		
		//LABEL FOR SEARCH FIRST NAME
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(6, 34, 87, 16);
		panel.add(lblFirstName);
		
		//TEXT FIELD FOR SEARCH FIRST NAME
		textFieldSearchFName = new JTextField();
		textFieldSearchFName.setBounds(94, 29, 151, 26);
		panel.add(textFieldSearchFName);
		textFieldSearchFName.setColumns(10);
		
		//LABEL FOR SEARCH LAST NAME
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(6, 62, 77, 16);
		panel.add(lblLastName);

		//TEXT FIELD FOR SEARCH LAST NAME
		textFieldSearchLName = new JTextField();
		textFieldSearchLName.setBounds(94, 57, 151, 26);
		panel.add(textFieldSearchLName);
		textFieldSearchLName.setColumns(10);

		//LABEL FOR SEARCH DATA OF BIRTH 
		JLabel lblDateOfBirth = new JLabel("Date of Birth");
		lblDateOfBirth.setBounds(6, 90, 87, 16);
		panel.add(lblDateOfBirth);
		
		//DATE CHOOSER FOR SEARCH DATE OF BIRTH
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(94, 86, 151, 26);
		panel.add(dateChooser);

		//LABLE FOR SEARCH ID NUMBER
		JLabel lblIdNumber = new JLabel("ID Number");
		lblIdNumber.setBounds(257, 34, 111, 16);
		panel.add(lblIdNumber);
		
		//TEXT FIELD FOR SEARCH ID NUMBER
		textFieldSearchIDNumber = new JTextField();
		textFieldSearchIDNumber.setBounds(380, 29, 130, 26);
		panel.add(textFieldSearchIDNumber);
		textFieldSearchIDNumber.setColumns(10);
		
		//LABEL FOR SEARCH MEDICAL CONDITION
		JLabel lblMedicalCondition = new JLabel("Medical condition");
		lblMedicalCondition.setBounds(257, 62, 121, 16);
		panel.add(lblMedicalCondition);
		
		//TEXT FIELD FOR SEARCH MEDICAL CONDITION
		textFieldSearchMedCondition = new JTextField();
		textFieldSearchMedCondition.setBounds(380, 57, 130, 26);
		panel.add(textFieldSearchMedCondition);
		textFieldSearchMedCondition.setColumns(10);
		
		//LABEL FOR SEARCH GENDER
		JLabel lblGender = new JLabel("Gender");
		lblGender.setBounds(257, 90, 61, 16);
		panel.add(lblGender);
		
		//JCOMBOBOX FOR SEARCH GENDER
		JComboBox comboBoxSearchGender = new JComboBox();
		comboBoxSearchGender.setModel(new DefaultComboBoxModel(new String[] {"","Male", "Female", "Other"}));
		comboBoxSearchGender.setBounds(380, 86, 130, 27);
		panel.add(comboBoxSearchGender);
		
		//LABEL FOR SEARCH TELEPHONE
		JLabel lblTel = new JLabel("Tel");
		lblTel.setBounds(522, 34, 61, 16);
		panel.add(lblTel);
		
		//TEXT FIELD FOR SEARCH TELEPHONE
		textFieldSearchTel = new JTextField();
		textFieldSearchTel.setBounds(556, 29, 130, 26);
		panel.add(textFieldSearchTel);
		textFieldSearchTel.setColumns(10);
		
		//LABEL FOR SEARCH EMAIL
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(522, 62, 61, 16);
		panel.add(lblEmail);
		
		//TEXT FIELD FOR SEARCH EMAIL
		textFieldSearchEmail = new JTextField();
		textFieldSearchEmail.setBounds(556, 57, 130, 26);
		panel.add(textFieldSearchEmail);
		textFieldSearchEmail.setColumns(10);
		
		//SEARCH BUTTON
		JButton searchButton = new JButton("");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Only search for fields that are not null
				HashMap<String,Object> searchCriteriaToUse = new HashMap<String,Object>();
				if (textFieldSearchFName.getText().length()>0) searchCriteriaToUse.put("FIRST_NAME", new String(textFieldSearchFName.getText()));
				if (textFieldSearchLName.getText().length()>0) searchCriteriaToUse.put("LAST_NAME", new String(textFieldSearchLName.getText()));
				String searchDate = String.format("%1$tY-%1$tm-%1$td", dateChooser.getDate());
				if (searchDate.length()>0 && searchDate.indexOf("null")==-1) searchCriteriaToUse.put("DOB", searchDate);
				if (textFieldSearchIDNumber.getText().length()>0) searchCriteriaToUse.put("ID", Integer.parseInt(textFieldSearchIDNumber.getText()));
				if (textFieldSearchMedCondition.getText().length()>0) searchCriteriaToUse.put("MEDICAL_CONDITION", textFieldSearchMedCondition.getText());
				if (textFieldSearchTel.getText().length()>0) searchCriteriaToUse.put("TELEPHONE", textFieldSearchTel.getText());
				if (((String)(comboBoxSearchGender.getSelectedItem())).length()>0) searchCriteriaToUse.put("GENDER", ((String)(comboBoxSearchGender.getSelectedItem())));
				if (textFieldSearchEmail.getText().length()>0) searchCriteriaToUse.put("EMAIL", textFieldSearchEmail.getText());
				ArrayList<HashMap<String,Object>> searchResults = Database.searchPatients(searchCriteriaToUse);
				//Construct table model
				TableModel newModelFromSearch = PatientsTable.constructTableModel(searchResults, columnNames);
				PatientsTable.updateTable(table, newModelFromSearch);
			}
		});
		Image searchImg = new ImageIcon(this.getClass().getResource("/search.png")).getImage();
		searchButton.setIcon(new ImageIcon(searchImg));
		searchButton.setBounds(514, 85, 172, 29);
		panel.add(searchButton);
		

	
		//createPatientPanel should open when addPatient Button Clicked
		JButton addPatientButton = new JButton("");
		addPatientButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(false);
				createPatientPanel.setVisible(true);
			}
		});
		
		Image addPatientImg = new ImageIcon(this.getClass().getResource("/addPatient.png")).getImage();
		addPatientButton.setIcon(new ImageIcon(addPatientImg));
		addPatientButton.setBounds(6, 73, 208, 61);
		mainPanel.add(addPatientButton);
		
		table = new JTable();
		table.setSelectionModel(new ForcedListSelectionModel());
		table.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		table.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		table.setBackground(UIManager.getColor("CheckBox.background"));
		table.setBounds(6, 146, 904, 358);
		
		//Get all records from Patients database
		ArrayList<HashMap<String,Object>> allPatients = Database.allRecords("PATIENTS");
		TableModel dataModel = PatientsTable.constructTableModel(allPatients, columnNames);
	    table.setEnabled(true);
		table.setModel(dataModel);
		table.setShowGrid(true);
		table.setAutoscrolls(true);
		Utility.setColWidths(table);
		
		JTableHeader header = table.getTableHeader();
	    header.setBackground(Color.darkGray);
	    header.setForeground(Color.white);
		
		mainPanel.add(table);
		
		JScrollPane js=new JScrollPane(table);
		js.setBounds(6, 146, 904, 358);
		js.setVisible(true);
		mainPanel.add(js);
		
		//ADD BUTTON TO VIEW OR EDIT SELECTED PATIENT (SELECTED ROW)
		JButton btnView = new JButton("VIEW/EDIT PATIENT");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				currentPatientID = (int) table.getValueAt(selectedRow,1);
				ArrayList<HashMap<String, Object>> results = Database.getRecordByID("PATIENTS", currentPatientID);
				HashMap<String,Object> currentPatient= results.get(0);
				//Set the components in the panel with the selected patient's details
				Utility.setVeditComponents(currentPatient, textFieldVeditFirstName, textFieldVeditLastName, 
						dateChooserVeditDOB, textFieldVeditMedicaCondition, comboBoxVeditGender,
						textFieldVeditTelephone, textFieldVeditEmail, profilePictureImageLabel,
						labelMedicalPhotoOneImage, labelMedicalPhotoTwoImage, labelMedicalPhotoThreeImage);
				//Repaint the panel 
				veditPatientPanel.revalidate();
				veditPatientPanel.setVisible(true);
				mainPanel.setVisible(false);
			}
		});
		btnView.setBounds(6, 507, 218, 55);
		mainPanel.add(btnView);
		
		JButton deletePatientSelectedButton = new JButton("DELETE PATIENT");
		deletePatientSelectedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				currentPatientID = (int)table.getValueAt(selectedRow, 1);
				int n = JOptionPane.showConfirmDialog(
						mainPanel,
					    "Are you sure you want to delete patient: "+(String)table.getValueAt(selectedRow, 2)+" "+(String)table.getValueAt(selectedRow, 3)+"?",
					    "Confirm Delete Patient",
					    JOptionPane.YES_NO_OPTION);
				if (n==0) {
					if (Database.deletePatient(currentPatientID)) {
						//Patient deleted, now refresh the table
						ArrayList<HashMap<String,Object>> allPatients = Database.allRecords("PATIENTS");
						TableModel dataModelAfterDeletion = PatientsTable.constructTableModel(allPatients, columnNames);
						PatientsTable.updateTable(table, dataModelAfterDeletion);
						JOptionPane.showMessageDialog(mainPanel, "Patient deleted.");
					}
				}
			}
		});
		deletePatientSelectedButton.setBounds(227, 509, 162, 53);
		mainPanel.add(deletePatientSelectedButton);
		
		//IMPORT BUTTON
		JButton btnImport = new JButton("IMPORT");
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean importSuccess = false;
				File importFile = null;
				JFileChooser csvFileChooser = new JFileChooser();
				csvFileChooser.setBounds(5, 5, 590, 440);
				mainPanel.add(csvFileChooser);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Comma separated values files", "csv");
				csvFileChooser.setFileFilter(filter);
				int returnVal = csvFileChooser.showOpenDialog(mainPanel);
				
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					boolean currentImportSuccess = false;
					String csvFilePath = csvFileChooser.getSelectedFile().getAbsolutePath();
					File csvFile = new File(csvFileChooser.getSelectedFile().getAbsolutePath());
					
					//Iterate through the file
					//http://codereview.stackexchange.com/questions/10681/java-function-to-read-a-csv-file
					String line = null;
					BufferedReader stream = null;
					int recordsImported = 0;
	
				    try {
				        try {
							stream = new BufferedReader(new FileReader(csvFile));
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				        while ((line = stream.readLine()) != null) {
				            String[] splitted = line.split(",");
				            if (splitted.length==7) {
				            	//Create the patient object
				            	Patient importCurrentPatient = new Patient (splitted[0],splitted[1],splitted[2],splitted[3],
				            			splitted[4],splitted[5],splitted[6],null,null,null,null);
				            	//Add the patient to the database
				            	currentImportSuccess = importCurrentPatient.addPatientToDB();
				            	if (currentImportSuccess) recordsImported++;
				            }
				            else {
				            	continue;
				            }
				        }
						ArrayList<HashMap<String,Object>> allPatients = Database.allRecords("PATIENTS");
						TableModel dataModelAfterDeletion = PatientsTable.constructTableModel(allPatients, columnNames);
						PatientsTable.updateTable(table, dataModelAfterDeletion);
				        JOptionPane.showMessageDialog(mainPanel, recordsImported+" patients were successfully imported.");
				        
				        
				    } catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
				        if (stream != null)
							try {
								stream.close();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
				    }
				    
			    }
			}
		});
		btnImport.setBounds(401, 508, 117, 53);
		mainPanel.add(btnImport);
		
		//EXPORT BUTTON
		JButton btnExport = new JButton("EXPORT");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Create a comma separated value file
				File exportFile = new File("exportedPatients"+Calendar.getInstance().get(Calendar.MILLISECOND)+".csv");
				//Export the selected rows
				boolean exportSuccess = Utility.toExcel(table, exportFile);
				//Output success message
				if (exportSuccess) JOptionPane.showMessageDialog(mainPanel, "Data successfully exported to:"+exportFile.getAbsolutePath());
				else JOptionPane.showMessageDialog(mainPanel, "Export failed");
			}
		});
		btnExport.setBounds(522, 508, 117, 53);
		mainPanel.add(btnExport);
		
		JButton btnLogOut = new JButton("LOG OUT");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(false);
				loginPanel.setVisible(true);
			}
		});
		btnLogOut.setBounds(793, 503, 117, 59);
		mainPanel.add(btnLogOut);

		//Add components and functionality to main panel
		
		JLabel logoLabel2 = new JLabel("LOGO");
		logoLabel2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				createPatientPanel.setVisible(false);
				mainPanel.setVisible(true);
			}
		});
		
		//ADD LOGO
		Image logo = new ImageIcon(this.getClass().getResource("/mainLogo.png")).getImage();
		logoLabel2.setIcon(new ImageIcon(logo));
		logoLabel2.setBounds(6, 6, 200, 61);
		createPatientPanel.add(logoLabel2);
		
		//ADD PANEL WHERE THE INPUT FORM WILL BE CONTAINED IN
		JPanel panelCreatePatientForm = new JPanel();
		panelCreatePatientForm.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelCreatePatientForm.setBackground(UIManager.getColor("Button.background"));
		panelCreatePatientForm.setBounds(35, 79, 854, 466);
		createPatientPanel.add(panelCreatePatientForm);
		panelCreatePatientForm.setLayout(null);
		
		
		//LABEL FOR FIRST NAME FIELD IN PATIENT CREATION FORM
		JLabel lblAddPatientFirstName = new JLabel("First Name*");
		lblAddPatientFirstName.setForeground(UIManager.getColor("Button.light"));
		lblAddPatientFirstName.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblAddPatientFirstName.setBounds(22, 29, 140, 32);
		panelCreatePatientForm.add(lblAddPatientFirstName);
		
		//FIRST NAME TEXT FIELD IN PATIENT CREATION FORM
		textFieldAddPatientFirstName = new JTextField();
		textFieldAddPatientFirstName.setForeground(Color.DARK_GRAY);
		textFieldAddPatientFirstName.setFont(new Font("Lucida Grande", Font.PLAIN, 19));
		textFieldAddPatientFirstName.setBounds(162, 27, 237, 37);
		panelCreatePatientForm.add(textFieldAddPatientFirstName);
		textFieldAddPatientFirstName.setColumns(10);
		
		//LABEL FOR LAST NAME FIELD IN PATIENT CREATION FORM
		JLabel labelAddPatientLastName = new JLabel("Last Name*");
		labelAddPatientLastName.setForeground(UIManager.getColor("Button.light"));
		labelAddPatientLastName.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		labelAddPatientLastName.setBounds(447, 29, 140, 32);
		panelCreatePatientForm.add(labelAddPatientLastName);
		
		//LAST NAME TEXT FIELD IN PATIENT CREATION FORM
		textFieldAddPatientLastName = new JTextField();
		textFieldAddPatientLastName.setForeground(Color.DARK_GRAY);
		textFieldAddPatientLastName.setFont(new Font("Lucida Grande", Font.PLAIN, 19));
		textFieldAddPatientLastName.setColumns(10);
		textFieldAddPatientLastName.setBounds(573, 27, 237, 37);
		panelCreatePatientForm.add(textFieldAddPatientLastName);
		
		//LABEL FOR DOB IN PATIENT CREATION FORM
		JLabel lblAddPatientDOB = new JLabel("Date of birth*");
		lblAddPatientDOB.setForeground(UIManager.getColor("Button.light"));
		lblAddPatientDOB.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblAddPatientDOB.setBounds(22, 87, 131, 32);
		panelCreatePatientForm.add(lblAddPatientDOB);
		
		
		//DATE CHOOSER FOR DOB IN PATIENT CREATION FORM
		JDateChooser dateChooserAddPatientDOB = new JDateChooser();
		dateChooserAddPatientDOB.setBounds(162, 87, 237, 32);
		panelCreatePatientForm.add(dateChooserAddPatientDOB);
		
		//LABLE FOR GENDER IN PATIENT CREATION FORM
		JLabel lblAddPatientGender = new JLabel("Gender*");
		lblAddPatientGender.setForeground(UIManager.getColor("Button.light"));
		lblAddPatientGender.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblAddPatientGender.setBounds(447, 87, 140, 32);
		panelCreatePatientForm.add(lblAddPatientGender);
		
		//COMBO BOX FOR GENDER IN PATIENT CREATION FORM
		JComboBox comboBoxAddPatientGender = new JComboBox();
		comboBoxAddPatientGender.setModel(new DefaultComboBoxModel(new String[] {"Male", "Female", "Other"}));
		comboBoxAddPatientGender.setBounds(573, 87, 237, 32);
		panelCreatePatientForm.add(comboBoxAddPatientGender);
		
		//LABEL FOR MEDICAL CONDITION IN PATIENT CREATION FORM
		JLabel lblAddPatientMedCondition = new JLabel("Med Condition");
		lblAddPatientMedCondition.setForeground(UIManager.getColor("Button.light"));
		lblAddPatientMedCondition.setFont(new Font("Lucida Grande", Font.PLAIN, 19));
		lblAddPatientMedCondition.setBounds(22, 209, 140, 32);
		panelCreatePatientForm.add(lblAddPatientMedCondition);
		
		//TEXT FIELD FOR MEDICAL CONDITION IN PATIENT CREATION FORM
		textFieldAddPatientMedCondition = new JTextField();
		textFieldAddPatientMedCondition.setBounds(162, 205, 648, 37);
		panelCreatePatientForm.add(textFieldAddPatientMedCondition);
		textFieldAddPatientMedCondition.setForeground(Color.DARK_GRAY);
		textFieldAddPatientMedCondition.setFont(new Font("Lucida Grande", Font.PLAIN, 19));
		textFieldAddPatientMedCondition.setColumns(10);

		//LABEL FOR TELEPHONE IN PATIENT CREATION FORM
		JLabel lblAddPatientTelephone = new JLabel("Telephone");
		lblAddPatientTelephone.setForeground(UIManager.getColor("Button.light"));
		lblAddPatientTelephone.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblAddPatientTelephone.setBounds(22, 151, 140, 32);
		panelCreatePatientForm.add(lblAddPatientTelephone);
		
		//TEXT FIELD FOR TELEPHONE IN PATIENT CREATION FORM
		textFieldAddPatientTelephone = new JTextField();
		textFieldAddPatientTelephone.setForeground(Color.DARK_GRAY);
		textFieldAddPatientTelephone.setFont(new Font("Lucida Grande", Font.PLAIN, 19));
		textFieldAddPatientTelephone.setColumns(10);
		textFieldAddPatientTelephone.setBounds(162, 146, 237, 37);
		panelCreatePatientForm.add(textFieldAddPatientTelephone);
		
		//LABEL FOR EMAIL IN PATIENT CREATION FORM
		JLabel lblAddPatientEmail = new JLabel("Email");
		lblAddPatientEmail.setForeground(UIManager.getColor("Button.light"));
		lblAddPatientEmail.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblAddPatientEmail.setBounds(447, 148, 140, 32);
		panelCreatePatientForm.add(lblAddPatientEmail);
		
		//TEXT FIELD FOR EMAIL IN PATIENT CREATION FORM
		textFieldAddPatientEmail = new JTextField();
		textFieldAddPatientEmail.setForeground(Color.DARK_GRAY);
		textFieldAddPatientEmail.setFont(new Font("Lucida Grande", Font.PLAIN, 19));
		textFieldAddPatientEmail.setColumns(10);
		textFieldAddPatientEmail.setBounds(573, 143, 237, 37);
		panelCreatePatientForm.add(textFieldAddPatientEmail);
		
		//LABEL FOR UPLOADING PROFILE
		JLabel lblAddPatientProfilePicture = new JLabel("Upload profile picture");
		lblAddPatientProfilePicture.setBounds(22, 266, 230, 32);
		panelCreatePatientForm.add(lblAddPatientProfilePicture);
		lblAddPatientProfilePicture.setForeground(UIManager.getColor("Button.light"));
		lblAddPatientProfilePicture.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		
		//LABEL SHOWING PROFILE PICTURE/FILE SELECTED FROM IMAGE CHOOSER
		JLabel lblAddPatientProfilePicSelected = new JLabel("No file selected");
		lblAddPatientProfilePicSelected.setForeground(UIManager.getColor("Button.select"));
		lblAddPatientProfilePicSelected.setBounds(22, 358, 181, 16);
		panelCreatePatientForm.add(lblAddPatientProfilePicSelected);
		
		//Instantiate imageUploader object
		ImageUploader GetImage = new ImageUploader();

		//BUTTON TO CHOOSE PROFILE PICTURE
		JButton btnAddPatientChooseProfilePicture = new JButton("Choose picture");
		btnAddPatientChooseProfilePicture.setBounds(22, 302, 206, 51);
		btnAddPatientChooseProfilePicture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					File proPicture = GetImage.openFileChooser(createPatientPanel);
					lblAddPatientProfilePicSelected.setText(proPicture.getName());
					proPictureString = proPicture.getAbsolutePath();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panelCreatePatientForm.add(btnAddPatientChooseProfilePicture);
		

		
		//LABEL TO UPLOAD PHOTOS DESCRIBING MEDICAL CONDITION
		JLabel lblUploadPhotosDescribing = new JLabel("Upload photos describing medical condition");
		lblUploadPhotosDescribing.setForeground(UIManager.getColor("Button.light"));
		lblUploadPhotosDescribing.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblUploadPhotosDescribing.setBounds(276, 266, 526, 32);
		panelCreatePatientForm.add(lblUploadPhotosDescribing);
		
		//LABEL SHOWING PHOTO 1 SELECTED
		JLabel labelAddPatientPhotoOneSelected = new JLabel("No file selected");
		labelAddPatientPhotoOneSelected.setForeground(UIManager.getColor("Button.select"));
		labelAddPatientPhotoOneSelected.setBounds(276, 358, 155, 16);
		panelCreatePatientForm.add(labelAddPatientPhotoOneSelected);
		
		//BUTTON TO CHOOSE FIRST PHOTO
		JButton btnAddPatientPhotoOne = new JButton("Choose photo 1");
		btnAddPatientPhotoOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					File photoOne = GetImage.openFileChooser(createPatientPanel);
					labelAddPatientPhotoOneSelected.setText(photoOne.getName());
					photoOneString = photoOne.getAbsolutePath();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAddPatientPhotoOne.setBounds(270, 302, 164, 51);
		panelCreatePatientForm.add(btnAddPatientPhotoOne);
		

		
		//LABEL SHOWING PHOTO 2 SELECTED
		JLabel labelAddPatientPhotoTwoSelected = new JLabel("No file selected");
		labelAddPatientPhotoTwoSelected.setForeground(UIManager.getColor("Button.select"));
		labelAddPatientPhotoTwoSelected.setBounds(447, 358, 155, 16);
		panelCreatePatientForm.add(labelAddPatientPhotoTwoSelected);
		
		//BUTTON TO CHOOSE SECOND PHOTO
		JButton btnAddPatientPhotoTwo = new JButton("Choose photo 2");
		btnAddPatientPhotoTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					File photoTwo = GetImage.openFileChooser(createPatientPanel);
					labelAddPatientPhotoTwoSelected.setText(photoTwo.getName());
					photoTwoString = photoTwo.getAbsolutePath();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAddPatientPhotoTwo.setBounds(444, 302, 164, 51);
		panelCreatePatientForm.add(btnAddPatientPhotoTwo);
		
		//LABEL SHOWING PHOTO 3 SELECTED
		JLabel labelAddPatientPhotoThreeSelected = new JLabel("No file selected");
		labelAddPatientPhotoThreeSelected.setForeground(UIManager.getColor("Button.select"));
		labelAddPatientPhotoThreeSelected.setBounds(620, 358, 155, 16);
		panelCreatePatientForm.add(labelAddPatientPhotoThreeSelected);

		//BUTTON TO CHOOSE THIRD PHOTO
		JButton btnAddPatientPhotoThree = new JButton("Choose photo 3");
		btnAddPatientPhotoThree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					File photoThree = GetImage.openFileChooser(createPatientPanel);
					labelAddPatientPhotoThreeSelected.setText(photoThree.getName());
					photoThreeString = photoThree.getAbsolutePath();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAddPatientPhotoThree.setBounds(620, 302, 164, 51);
		panelCreatePatientForm.add(btnAddPatientPhotoThree);
		

		//ADD PATIENT SUBMIT BUTTON
		JButton btnAddPatientSubmit = new JButton("ADD PATIENT");
		btnAddPatientSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Validate form fields
				String [] createPatientFields = new String[7];
				createPatientFields[0] = textFieldAddPatientFirstName.getText();
				createPatientFields[1] = textFieldAddPatientLastName.getText();
				
				//http://stackoverflow.com/questions/6760690/can-not-retrieve-date-from-jdatechooser-in-java
				//http://db.apache.org/derby/docs/10.2/ref/rrefsqlj27620.html
				createPatientFields[2] = String.format("%1$tY-%1$tm-%1$td", dateChooserAddPatientDOB.getDate());
				createPatientFields[3] = textFieldAddPatientMedCondition.getText();
				System.out.println("Date as string:"+createPatientFields[2]);
				createPatientFields[4] = (String) comboBoxAddPatientGender.getSelectedItem();
				System.out.println("Gender extracted:"+createPatientFields[4]);
				createPatientFields[5] = textFieldAddPatientTelephone.getText();
				createPatientFields[6] = textFieldAddPatientEmail.getText();
			
				boolean validated = Utility.validateFields(createPatientFields);
				if (validated) {
					System.out.println("Fields good");
					//Create patient object
					Patient newPatient = new Patient(createPatientFields[0],createPatientFields[1], createPatientFields[2], createPatientFields[3], createPatientFields[4], createPatientFields[5], createPatientFields[6], proPictureString,photoOneString ,photoTwoString,photoThreeString);
					if (newPatient.addPatientToDB()) {
						JOptionPane.showMessageDialog(panelCreatePatientForm, "Patient successfully added");
						ArrayList<HashMap<String,Object>> allPatients = Database.allRecords("PATIENTS");
						TableModel dataModel = PatientsTable.constructTableModel(allPatients, columnNames);
						PatientsTable.updateTable(table, dataModel);
					}
				}
				else {
					JOptionPane.showMessageDialog(panelCreatePatientForm, "Invalid input","Add Patient Error",JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		});
		
		
		btnAddPatientSubmit.setBackground(Color.WHITE);
		btnAddPatientSubmit.setForeground(Color.DARK_GRAY);
		btnAddPatientSubmit.setBounds(22, 398, 788, 49);
		panelCreatePatientForm.add(btnAddPatientSubmit);
		
		JButton btnNewButton_3 = new JButton("BACK");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(true);
				createPatientPanel.setVisible(false);
				
			}
		});
		btnNewButton_3.setBounds(783, 7, 106, 61);
		createPatientPanel.add(btnNewButton_3);
		
		//Add Components to View/Edit Patient Panel
		JLabel logoLabelVedit = new JLabel("");
		logoLabelVedit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				veditPatientPanel.setVisible(false);
				mainPanel.setVisible(true);
			}
		});

		//ADD LOGO
		Image viewEditPatientLogo = new ImageIcon(this.getClass().getResource("/mainLogo.png")).getImage();
		veditPatientPanel.setLayout(null);
		logoLabelVedit.setIcon(new ImageIcon(viewEditPatientLogo));
		logoLabelVedit.setBounds(0, 0, 200, 61);
		veditPatientPanel.add(logoLabelVedit);
		
		//ADD PANEL FOR PROFILE PICTURE
		JPanel profilePicturePanel = new JPanel();
		profilePicturePanel.setBounds(745, 6, 165, 268);
		veditPatientPanel.add(profilePicturePanel);
		profilePicturePanel.setLayout(null);		
		
		//ADD LABEL SHOWING PROFILE PHOTO
		//https://docs.oracle.com/javase/7/docs/api/javax/swing/ImageIcon.html
			
		profilePictureImageLabel = new JLabel("NO PHOTO");
		profilePictureImageLabel.setBounds(6, 6, 150, 200);
		profilePicturePanel.add(profilePictureImageLabel);
		
		//REMOVE PROFILE PICTURE BUTTON
		JButton removeProfilePictureButton = new JButton("");
		removeProfilePictureButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Database.removePhoto(currentPatientID,"PROFILE_PICTURE");
				ArrayList<HashMap<String, Object>> results = Database.getRecordByID("PATIENTS", currentPatientID);
				HashMap<String,Object> currentPatientHashMap= results.get(0);
				//Set the components in the panel with the selected patient's details
				veditProPictureString = null;
				Utility.setVeditComponents(currentPatientHashMap, textFieldVeditFirstName, textFieldVeditLastName, 
						dateChooserVeditDOB, textFieldVeditMedicaCondition, comboBoxVeditGender,
						textFieldVeditTelephone, textFieldVeditEmail, profilePictureImageLabel,
						labelMedicalPhotoOneImage, labelMedicalPhotoTwoImage, labelMedicalPhotoThreeImage);
				//Repaint the panel 
				veditPatientPanel.revalidate();
			} 
		});
		Image removeProfilePictureImage = new ImageIcon(this.getClass().getResource("/remove_button.png")).getImage();
		removeProfilePictureButton.setIcon(new ImageIcon(removeProfilePictureImage));
		removeProfilePictureButton.setBounds(82, 206, 24, 24);
		profilePicturePanel.add(removeProfilePictureButton);
		
		
		//CHANGE PROFILE PHOTO
		JButton editProfilePictureButton = new JButton("");
		editProfilePictureButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					File veditProPicture = GetImage.openFileChooser(veditPatientPanel);
					labelFileSelectedVeditProPicture.setText(veditProPicture.getName());
					veditProPictureString = veditProPicture.getAbsolutePath();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		Image editProfilePictureImage = new ImageIcon(this.getClass().getResource("/edit_button.png")).getImage();
		editProfilePictureButton.setBounds(55, 206, 24, 24);
		profilePicturePanel.add(editProfilePictureButton);
		editProfilePictureButton.setIcon(new ImageIcon(editProfilePictureImage));
		
		//LABEL SHOWING PROFILE PICTURE SELECTED
		labelFileSelectedVeditProPicture = new JLabel("No file selected");
		labelFileSelectedVeditProPicture.setBounds(16, 242, 140, 16);
		profilePicturePanel.add(labelFileSelectedVeditProPicture);
		labelFileSelectedVeditProPicture.setForeground(UIManager.getColor("Button.select"));
		
		//VEDIT: FIRST NAME LABEL
		JLabel labelVeditFirstName = new JLabel("First Name*");
		labelVeditFirstName.setForeground(SystemColor.controlHighlight);
		labelVeditFirstName.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		labelVeditFirstName.setBounds(17, 89, 140, 32);
		veditPatientPanel.add(labelVeditFirstName);
		
		//VEDIT: FIRST NAME TEXT FIELD
		textFieldVeditFirstName = new JTextField();
		textFieldVeditFirstName.setForeground(Color.DARK_GRAY);
		textFieldVeditFirstName.setFont(new Font("Lucida Grande", Font.PLAIN, 19));
		textFieldVeditFirstName.setColumns(10);
		textFieldVeditFirstName.setBounds(163, 87, 211, 37);
		veditPatientPanel.add(textFieldVeditFirstName);
		
		//VEDIT: LAST NAME LABEL
		JLabel labelVeditLastName = new JLabel("Last Name*");
		labelVeditLastName.setForeground(SystemColor.controlHighlight);
		labelVeditLastName.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		labelVeditLastName.setBounds(383, 89, 117, 32);
		veditPatientPanel.add(labelVeditLastName);
		
		//VEDIT: LAST NAME TEXT FIELD
		textFieldVeditLastName = new JTextField();
		textFieldVeditLastName.setForeground(Color.DARK_GRAY);
		textFieldVeditLastName.setFont(new Font("Lucida Grande", Font.PLAIN, 19));
		textFieldVeditLastName.setColumns(10);
		textFieldVeditLastName.setBounds(504, 87, 237, 37);
		veditPatientPanel.add(textFieldVeditLastName);
		
		//VEDIT: DOB LABLE
		JLabel labelVeditDOB = new JLabel("Date of birth*");
		labelVeditDOB.setForeground(SystemColor.controlHighlight);
		labelVeditDOB.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		labelVeditDOB.setBounds(17, 126, 131, 32);
		veditPatientPanel.add(labelVeditDOB);
		
		//VEDIT: DOB CHOOSER 
		dateChooserVeditDOB = new JDateChooser();
		dateChooserVeditDOB.setBounds(163, 126, 211, 32);
		veditPatientPanel.add(dateChooserVeditDOB);
		
		//VEDIT: GENDER LABEL
		JLabel labelVeditGender = new JLabel("Gender*");
		labelVeditGender.setForeground(SystemColor.controlHighlight);
		labelVeditGender.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		labelVeditGender.setBounds(383, 126, 140, 32);
		veditPatientPanel.add(labelVeditGender);
		
		//VEDIT: GENDER COMBO BOX
		comboBoxVeditGender = new JComboBox();
		comboBoxVeditGender.setModel(new DefaultComboBoxModel(new String[] {"Male", "Female", "Other"}));
		comboBoxVeditGender.setBounds(504, 126, 237, 32);
		veditPatientPanel.add(comboBoxVeditGender);
		
		//VEDIT: MED CONDITION LABEL
		JLabel labelVeditMedicalCondition = new JLabel("Med Condition");
		labelVeditMedicalCondition.setForeground(SystemColor.controlHighlight);
		labelVeditMedicalCondition.setFont(new Font("Lucida Grande", Font.PLAIN, 19));
		labelVeditMedicalCondition.setBounds(17, 201, 140, 32);
		veditPatientPanel.add(labelVeditMedicalCondition);
		
		//VEDIT: MEDICAL CONDITION TEXT FIELD
		textFieldVeditMedicaCondition = new JTextField();
		textFieldVeditMedicaCondition.setForeground(Color.DARK_GRAY);
		textFieldVeditMedicaCondition.setFont(new Font("Lucida Grande", Font.PLAIN, 19));
		textFieldVeditMedicaCondition.setColumns(10);
		textFieldVeditMedicaCondition.setBounds(163, 199, 420, 37);
		veditPatientPanel.add(textFieldVeditMedicaCondition);
		
		//VEDIT: TELEPHONE LABEL
		JLabel labelVeditTelephone = new JLabel("Telephone");
		labelVeditTelephone.setForeground(SystemColor.controlHighlight);
		labelVeditTelephone.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		labelVeditTelephone.setBounds(17, 165, 140, 32);
		veditPatientPanel.add(labelVeditTelephone);
		
		//VEDIT: TELEPHONE TEXT FIELD
		textFieldVeditTelephone = new JTextField();
		textFieldVeditTelephone.setForeground(Color.DARK_GRAY);
		textFieldVeditTelephone.setFont(new Font("Lucida Grande", Font.PLAIN, 19));
		textFieldVeditTelephone.setColumns(10);
		textFieldVeditTelephone.setBounds(163, 163, 211, 37);
		veditPatientPanel.add(textFieldVeditTelephone);
		
		//VEDIT: EMAIL LABEL
		JLabel labelVeditEmail = new JLabel("Email");
		labelVeditEmail.setForeground(SystemColor.controlHighlight);
		labelVeditEmail.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		labelVeditEmail.setBounds(383, 160, 117, 37);
		veditPatientPanel.add(labelVeditEmail);
		
		//VEDIT: EMAIL TEXT FIELD
		textFieldVeditEmail = new JTextField();
		textFieldVeditEmail.setForeground(Color.DARK_GRAY);
		textFieldVeditEmail.setFont(new Font("Lucida Grande", Font.PLAIN, 19));
		textFieldVeditEmail.setColumns(10);
		textFieldVeditEmail.setBounds(504, 160, 237, 37);
		veditPatientPanel.add(textFieldVeditEmail);
		
		//VEDIT: PHOOTS DESCRIBING MED CONDITION LABEL
		JLabel labelVeditPhotosMedCondition = new JLabel("Photos describing medical condition");
		labelVeditPhotosMedCondition.setForeground(SystemColor.controlHighlight);
		labelVeditPhotosMedCondition.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		labelVeditPhotosMedCondition.setBounds(17, 245, 526, 32);
		veditPatientPanel.add(labelVeditPhotosMedCondition);
		
		//VEDIT: PANEL SHOWING MED PHOTOS
		JPanel medicalPhotosPanel = new JPanel();
		medicalPhotosPanel.setBounds(17, 281, 724, 281);
		veditPatientPanel.add(medicalPhotosPanel);
		medicalPhotosPanel.setLayout(null);
		
		//VEDIT: LABEL FOR PHOTO 1
		labelMedicalPhotoOneImage = new JLabel("");
		labelMedicalPhotoOneImage.setBounds(6, 6, 230, 230);
		medicalPhotosPanel.add(labelMedicalPhotoOneImage);
		
		//VEDIT: LABEL FOR PHOTO 2
		labelMedicalPhotoTwoImage = new JLabel("");
		labelMedicalPhotoTwoImage.setBounds(248, 6, 230, 230);
		medicalPhotosPanel.add(labelMedicalPhotoTwoImage);
		
		//VEDIT: LABEL FOR PHOTO 3
		labelMedicalPhotoThreeImage = new JLabel("");
		labelMedicalPhotoThreeImage.setBounds(490, 6, 230, 230);
		medicalPhotosPanel.add(labelMedicalPhotoThreeImage);
		
		//VEDIT: REMOVE PHOTO 1 BUTTON
		JButton buttonRemovePhoto1 = new JButton("");
		buttonRemovePhoto1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Database.removePhoto(currentPatientID,"PHOTO_1");
				ArrayList<HashMap<String, Object>> results = Database.getRecordByID("PATIENTS", currentPatientID);
				HashMap<String,Object> currentPatientHashMap= results.get(0);
				veditPhotoOneString = null;
				//Set the components in the panel with the selected patient's details
				Utility.setVeditComponents(currentPatientHashMap, textFieldVeditFirstName, textFieldVeditLastName, 
						dateChooserVeditDOB, textFieldVeditMedicaCondition, comboBoxVeditGender,
						textFieldVeditTelephone, textFieldVeditEmail, profilePictureImageLabel,
						labelMedicalPhotoOneImage, labelMedicalPhotoTwoImage, labelMedicalPhotoThreeImage);
				//Repaint the panel 
				veditPatientPanel.revalidate();
			}
		});
		buttonRemovePhoto1.setIcon(new ImageIcon(removeProfilePictureImage));
		buttonRemovePhoto1.setBounds(212, 248, 24, 24);
		medicalPhotosPanel.add(buttonRemovePhoto1);
		
		
		//VEDIT: EDIT PHOTO 1 BUTTON
		ImageUploader getNewPhoto1 = new ImageUploader();
		JButton buttonEditPhoto1 = new JButton("");
		buttonEditPhoto1.setIcon(new ImageIcon(editProfilePictureImage));
		buttonEditPhoto1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					File veditPhoto = getNewPhoto1.openFileChooser(veditPatientPanel);
					labelFileSelectedPhoto1.setText(veditPhoto.getName());
					veditPhotoOneString = veditPhoto.getAbsolutePath();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonEditPhoto1.setBounds(185, 248, 24, 24);
		medicalPhotosPanel.add(buttonEditPhoto1);
		
		//VEDIT: LABEL PHOTO 1 SELECTED
		labelFileSelectedPhoto1 = new JLabel("No file selected");
		labelFileSelectedPhoto1.setBounds(16, 248, 166, 24);
		medicalPhotosPanel.add(labelFileSelectedPhoto1);
		labelFileSelectedPhoto1.setForeground(UIManager.getColor("Button.select"));
		
		//VEDIT: REMOVE PHOTO 2 BUTTON
		JButton buttonRemovePhoto2 = new JButton("");
		buttonRemovePhoto2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Database.removePhoto(currentPatientID,"PHOTO_2");
				ArrayList<HashMap<String, Object>> results = Database.getRecordByID("PATIENTS", currentPatientID);
				HashMap<String,Object> currentPatientHashMap= results.get(0);
				veditPhotoTwoString = null;
				//Set the components in the panel with the selected patient's details
				Utility.setVeditComponents(currentPatientHashMap, textFieldVeditFirstName, textFieldVeditLastName, 
						dateChooserVeditDOB, textFieldVeditMedicaCondition, comboBoxVeditGender,
						textFieldVeditTelephone, textFieldVeditEmail, profilePictureImageLabel,
						labelMedicalPhotoOneImage, labelMedicalPhotoTwoImage, labelMedicalPhotoThreeImage);
				//Repaint the panel 
				veditPatientPanel.revalidate();
			}
		});
		buttonRemovePhoto2.setIcon(new ImageIcon(removeProfilePictureImage));
		buttonRemovePhoto2.setBounds(454, 248, 24, 24);
		medicalPhotosPanel.add(buttonRemovePhoto2);
		
		//VEDIT: EDIT PHOTO 2 BUTTON
		ImageUploader getNewPhotoTwo = new ImageUploader();
		JButton buttonEditPhoto2 = new JButton("");
		buttonEditPhoto2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					File veditPhoto = getNewPhotoTwo.openFileChooser(veditPatientPanel);
					labelFileSelectedPhoto2.setText(veditPhoto.getName());
					veditPhotoTwoString = veditPhoto.getAbsolutePath();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonEditPhoto2.setIcon(new ImageIcon(editProfilePictureImage));
		buttonEditPhoto2.setBounds(427, 248, 24, 24);
		medicalPhotosPanel.add(buttonEditPhoto2);

		//VEDIT: LABEL PHOTO 2 SELECTED
		labelFileSelectedPhoto2 = new JLabel("No file selected");
		labelFileSelectedPhoto2.setBounds(248, 248, 171, 24);
		medicalPhotosPanel.add(labelFileSelectedPhoto2);
		labelFileSelectedPhoto2.setForeground(UIManager.getColor("Button.select"));
		
		//VEDIT: REMOVE PHOTO 3 BUTTON
		JButton buttonRemovePhoto3 = new JButton("");
		buttonRemovePhoto3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Database.removePhoto(currentPatientID,"PHOTO_3");
				ArrayList<HashMap<String, Object>> results = Database.getRecordByID("PATIENTS", currentPatientID);
				HashMap<String,Object> currentPatientHashMap= results.get(0);
				//Set the components in the panel with the selected patient's details
				veditPhotoThreeString = null;
				Utility.setVeditComponents(currentPatientHashMap, textFieldVeditFirstName, textFieldVeditLastName, 
						dateChooserVeditDOB, textFieldVeditMedicaCondition, comboBoxVeditGender,
						textFieldVeditTelephone, textFieldVeditEmail, profilePictureImageLabel,
						labelMedicalPhotoOneImage, labelMedicalPhotoTwoImage, labelMedicalPhotoThreeImage);
				//Repaint the panel 
				veditPatientPanel.revalidate();
			}
		});
		buttonRemovePhoto3.setIcon(new ImageIcon(removeProfilePictureImage));
		buttonRemovePhoto3.setBounds(694, 248, 24, 24);
		medicalPhotosPanel.add(buttonRemovePhoto3);
		
		
		//VEDIT: EDIT PHOTO 3 BUTTON
		ImageUploader getNewPhoto3 = new ImageUploader();
		JButton buttonEditPhoto3 = new JButton("");
		buttonEditPhoto3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					File veditPhoto = getNewPhoto3.openFileChooser(veditPatientPanel);
					labelFileSelectedPhoto3.setText(veditPhoto.getName());
					veditPhotoThreeString = veditPhoto.getAbsolutePath();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonEditPhoto3.setIcon(new ImageIcon(editProfilePictureImage));
		buttonEditPhoto3.setBounds(667, 248, 24, 24);
		medicalPhotosPanel.add(buttonEditPhoto3);
		
		//VEDIT: LABEL PHOTO 3 SELECTED
		labelFileSelectedPhoto3 = new JLabel("No file selected");
		labelFileSelectedPhoto3.setBounds(490, 248, 155, 16);
		medicalPhotosPanel.add(labelFileSelectedPhoto3);
		labelFileSelectedPhoto3.setForeground(UIManager.getColor("Button.select"));
		

		
		JButton buttonVeditSave = new JButton("SAVE");
		buttonVeditSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Validate form fields
				String [] editPatientFields = new String[7];
				
				editPatientFields[0] = textFieldVeditFirstName.getText();
				editPatientFields[1] = textFieldVeditLastName.getText();
				//http://stackoverflow.com/questions/6760690/can-not-retrieve-date-from-jdatechooser-in-java
				//http://db.apache.org/derby/docs/10.2/ref/rrefsqlj27620.html
				editPatientFields[2] = String.format("%1$tY-%1$tm-%1$td", dateChooserVeditDOB.getDate());
				editPatientFields[3] = textFieldVeditMedicaCondition.getText();
				editPatientFields[4] = (String) comboBoxVeditGender.getSelectedItem();
				editPatientFields[5] = textFieldVeditTelephone.getText();
				editPatientFields[6] = textFieldVeditEmail.getText();
			
				boolean validated = Utility.validateFields(editPatientFields);
				if (validated) {
					System.out.println("Fields good");
					//Create patient object
					Patient currentPatient = new Patient(editPatientFields[0],editPatientFields[1], editPatientFields[2], editPatientFields[3], editPatientFields[4], editPatientFields[5], editPatientFields[6], veditProPictureString,veditPhotoOneString ,veditPhotoTwoString,veditPhotoThreeString);
					if (currentPatient.updatePatient(currentPatientID)) {
						JOptionPane.showMessageDialog(veditPatientPanel, "Patient updated");
						
						//Redraw the veditPanel
						ArrayList<HashMap<String, Object>> results = Database.getRecordByID("PATIENTS", currentPatientID);
						HashMap<String,Object> currentPatientHashMap= results.get(0);
						//Set the components in the panel with the selected patient's details
						Utility.setVeditComponents(currentPatientHashMap, textFieldVeditFirstName, textFieldVeditLastName, 
								dateChooserVeditDOB, textFieldVeditMedicaCondition, comboBoxVeditGender,
								textFieldVeditTelephone, textFieldVeditEmail, profilePictureImageLabel,
								labelMedicalPhotoOneImage, labelMedicalPhotoTwoImage, labelMedicalPhotoThreeImage);
						//Repaint the panel 
						veditPatientPanel.revalidate();
						
						//Also regenerate the table so changes will be seen when user goes back to table view
						ArrayList<HashMap<String,Object>> allPatients = Database.allRecords("PATIENTS");
						TableModel dataModel = PatientsTable.constructTableModel(allPatients, columnNames);
						PatientsTable.updateTable(table, dataModel);
					}
				}
				else {
					JOptionPane.showMessageDialog(panelCreatePatientForm, "Invalid input","Add Patient Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		buttonVeditSave.setBounds(745, 286, 165, 66);
		veditPatientPanel.add(buttonVeditSave);
		
		JButton btnNewButton = new JButton("CLOSE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(true);
				veditPatientPanel.setVisible(false);
			}
		});
		btnNewButton.setBounds(745, 358, 165, 61);
		veditPatientPanel.add(btnNewButton);
		
		JButton btnNewButton_2 = new JButton("MORE INFO");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String medicalCondition = (String)table.getValueAt(table.getSelectedRow(), 5);
				medicalCondition = medicalCondition.replace(" ", "+");
				URI uri = null;
				try {
					uri = new URI("https://en.wikipedia.org/w/index.php?search="+medicalCondition);
				} catch (URISyntaxException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					Desktop.getDesktop().browse(uri);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_2.setBounds(595, 201, 142, 37);
		veditPatientPanel.add(btnNewButton_2);
	}
}
