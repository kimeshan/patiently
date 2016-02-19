package uk.co.ucl.cs.gc01.pms;

import java.io.File;

public class PatientlyImage {
	private String filePath;
	private File fileChosen;
	public PatientlyImage(String filePath, File fileChosen) {
		this.filePath = filePath;
		this.fileChosen = fileChosen;
	}
	
	public String getPath() {
		return filePath;
	}
	
	public File getFile() {
		return fileChosen;
	}

}
