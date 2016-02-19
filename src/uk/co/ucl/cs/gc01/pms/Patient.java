package uk.co.ucl.cs.gc01.pms;

import java.io.File;

public class Patient {
	public String first_name, last_name, dob, medical_condition, gender, telephone, email;
	public String profile_picture, photo_1, photo_2, photo_3;
	
	public Patient (String first_name, String last_name, String dob, String medical_condition, String gender, String telephone, String email, String profile_picture,String photo_one, String photo_two, String photo_three) {
		
		//Initialize variables (members)
		this.first_name = first_name;
		this.last_name = last_name;
		this.dob = dob;
		this.medical_condition = medical_condition;
		this.gender = gender;
		this.telephone = telephone;
		this.email = email;
		this.profile_picture = profile_picture;
		this.photo_1 = photo_one;
		this.photo_2 = photo_two;
		this.photo_3 = photo_three;
	}
	
	public boolean addPatientToDB () {
		return Database.addPatient(first_name, last_name, dob, medical_condition, gender, telephone, email,profile_picture, photo_1, photo_2, photo_3);	
	}
	
	public boolean updatePatient(int currentPatientID) {
		return Database.updatePatient(currentPatientID, first_name, last_name, dob, medical_condition, gender, telephone, email,profile_picture, photo_1, photo_2, photo_3);	
		
	}
}
