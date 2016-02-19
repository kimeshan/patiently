package uk.co.ucl.cs.gc01.pms;

import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.toedter.calendar.JDateChooser;

import uk.co.ucl.cs.gc01.pms.gui.MainGUI;

public class Utility {
	public static String passwordToString (char[] password) {
		String passwordString = "";
		for (int i=0;i<password.length;i++) passwordString+= password[i];
		return passwordString;
	}
	
	public static boolean validateFields(String[] fields) {
		boolean validated = true;
		for (int i=0;i<5;i++) {
			System.out.println("Field:"+fields[i]+"\nLength: "+fields[i].length());
			if (fields[i].length()<1) {
				validated = false;
				break;
			}
		}
		return validated;
	}
	
	//Resize table column widths
	//http://stackoverflow.com/questions/17627431/auto-resizing-the-jtable-column-widths
	public static void setColWidths(JTable table) {
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 0; column < table.getColumnCount(); column++) {
	        int width = 50; // Min width
	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width +1 , width);
	        }
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	}

	public static void setVeditComponents(HashMap<String,Object> currentPatient, 
			JTextField firstNameField, JTextField lastNameField, JDateChooser dobDate, JTextField medicalConditionField,
			JComboBox genderBox,JTextField telephoneField, JTextField emailField, JLabel proPic,
			JLabel photoOne, JLabel photoTwo, JLabel photoThree  ) {
		//EXTRACT VALUES FROM HASHMAP
		String first_name = (String) currentPatient.get("FIRST_NAME");
		String last_name = (String) currentPatient.get("LAST_NAME");
		Date dob = (Date) currentPatient.get("DOB");
		String medical_condition = (String) currentPatient.get("MEDICAL_CONDITION");
		String gender = (String) currentPatient.get("GENDER");
		String telephone = (String) currentPatient.get("TELEPHONE");
		String email = (String) currentPatient.get("EMAIL");
		String currentProfilePicture = (String)currentPatient.get("PROFILE_PICTURE");
		String photo_1 = (String) currentPatient.get("PHOTO_1");
		String photo_2 = (String) currentPatient.get("PHOTO_2");
		String photo_3 = (String) currentPatient.get("PHOTO_3");

		
		//SET VALUES TO APPROPRIATE GUI COMPONENT
		firstNameField.setText(first_name);
		lastNameField.setText(last_name);
		dobDate.setDate(dob);
		medicalConditionField.setText(medical_condition);
		System.out.println("Gender to be set:"+gender);
		genderBox.setSelectedItem(gender);
		System.out.println(genderBox.getSelectedItem());
		telephoneField.setText(telephone);
		emailField.setText(email);
		
		//Create image icons
		
		Image defaultProfilePicture = new ImageIcon(MainGUI.class.getResource("/default_profile_picture.png")).getImage();
		Image defaultMedicalPhoto = new ImageIcon(MainGUI.class.getResource("/no_image.jpg")).getImage();
		ImageIcon profilePictureImageIcon = new ImageIcon(currentProfilePicture);
		ImageIcon photoOneImageIcon = new ImageIcon(photo_1);
		ImageIcon photoTwoImageIcon = new ImageIcon(photo_2);
		ImageIcon photoThreeImageIcon = new ImageIcon(photo_3);
		
		if (currentProfilePicture==null) proPic.setIcon(new ImageIcon(defaultProfilePicture));
		else proPic.setIcon(profilePictureImageIcon);
		
		if (photo_2==null) photoOne.setIcon(new ImageIcon(defaultMedicalPhoto));
		else photoOne.setIcon(photoOneImageIcon);
		
		if (photo_3==null) photoTwo.setIcon(new ImageIcon(defaultMedicalPhoto));
		else photoTwo.setIcon(photoTwoImageIcon);
		
		if (photo_1==null) photoThree.setIcon(new ImageIcon(defaultMedicalPhoto));
		else photoThree.setIcon(photoThreeImageIcon);
		
	}
	
	//https://sites.google.com/site/teachmemrxymon/java/export-records-from-jtable-to-ms-excel
	public static boolean toExcel(JTable table, File file){
		boolean exported = false;
	    try{
	        TableModel model = table.getModel();
	        FileWriter excel = new FileWriter(file);

	        for(int i = 0; i < model.getColumnCount(); i++){
	            excel.write(model.getColumnName(i) + ",");
	        }

	        excel.write("\n");

	        for(int i=0; i< model.getRowCount(); i++) {
	        	if (model.getValueAt(i, 0)==Boolean.TRUE) {
	                for(int j=0; j < model.getColumnCount(); j++) {
		                excel.write(model.getValueAt(i,j).toString()+",");
		            }
		            excel.write("\n");
	        	}
	        }
	        excel.close();
	        exported = true;
	    }
	    catch(IOException e){ 
	    	e.printStackTrace();
	    	return exported;
	    	}
	   return exported;
	}
	
	
	
}
