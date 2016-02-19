package uk.co.ucl.cs.gc01.pms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.PreparedStatement;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

import org.omg.CORBA.portable.InputStream;

public class ImageUploader extends JFrame {
	private static final long serialVersionUID = 1L;
	public File openFileChooser(JPanel contentPane) throws IOException, BadLocationException {
		File imagePath = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setBounds(5, 5, 590, 440);
		contentPane.add(fileChooser);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Picture files", "jpg", "gif","jpeg","png");
		fileChooser.setFileFilter(filter);
		int returnVal = fileChooser.showOpenDialog(getParent());
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			String filePath = fileChooser.getSelectedFile().getAbsolutePath();
			File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
			//Copy the file to our workspace directory
			//http://stackoverflow.com/questions/25941694/copy-file-from-jfilechooser-to-new-directory
		   FileInputStream inStream = null;
	       FileOutputStream outStream = null;
	       try{
	           File dest =new File(System.getProperty("user.dir")+"/patient_photos", file.getName());
	           inStream = new FileInputStream(file);
	           outStream = new FileOutputStream(dest);
	
	           byte[] buffer = new byte[1024];
	
	           int length;
	           while ((length = inStream.read(buffer)) > 0){
	               outStream.write(buffer, 0, length);
	           		}
	
	           if (inStream != null)inStream.close();
	           if (outStream != null)outStream.close();
	           System.out.println("File Copied..");
	           imagePath = dest;
	       }
	       catch (FileNotFoundException e) {
	    	   System.out.println("No file selected");
	    	   return imagePath;
	       }
	    }
		return imagePath;
		//http://stackoverflow.com/questions/2832472/how-to-return-2-values-from-a-java-function
	}
}


