package uk.co.ucl.cs.gc01.pms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.sql.ResultSetMetaData;


public class Database
{
    //dbURL specified details of database to connect to, name=PMS, create=true will create the database if it doesn't exist
	private static String dbURL = "jdbc:derby:PMS;create=true";
    // jdbc Connection
    private static Connection conn = null;
    private static Statement stmt = null;
    public static void startDatabase() {
        //Connect to database
    	createConnection();
        System.out.println("Connection created");
        //Create table with schema if doesn't already exist
        createTables();
        System.out.println("Tables created");
        selectAllRows("USERS");
        selectAllRows("PATIENTS");
        //shutdown();
    }
    
    private static void createConnection()
    {
        try
        {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            //Get a connection
            conn = DriverManager.getConnection(dbURL); 
        }
        catch (Exception except)
        {
            except.printStackTrace();
        }
    }
    
    private static void createTables () {
    	try {
			stmt = conn.createStatement();
			//First create user's table
	    	stmt.execute("CREATE TABLE USERS "
	    			+ "(ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
	    			+ "USERNAME VARCHAR(50) NOT NULL PRIMARY KEY,"
	    			+ "PASSWORD VARCHAR(50) NOT NULL,"
	    			+ "ACCOUNT_TYPE VARCHAR(20) NOT NULL,"
	    			+ "UNIQUE (ID,USERNAME)"
	    			+ ")");
	    	stmt.close();
	    	//Now create patients table
	    	System.out.println(conn);
	    	
	    	
		} catch (SQLException e) {
			if(e.getErrorCode()>0) {
				System.out.println(e.getMessage()+" "+e.getErrorCode());
		        // That's OK if table already exist, do nothing
		    }
		    try {
		    	System.out.println("Creating Patients Table if doesn't exist...");
		    	stmt = conn.createStatement();
	        	stmt.execute("CREATE TABLE PATIENTS "
	        			+ "(ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
	        			+ "FIRST_NAME VARCHAR(50) NOT NULL,"
	        			+ "LAST_NAME VARCHAR(50) NOT NULL,"
	        			+ "DOB DATE NOT NULL,"
	        			+ "MEDICAL_CONDITION VARCHAR(50),"
	        			+ "GENDER VARCHAR(6) NOT NULL,"
	        			+ "TELEPHONE VARCHAR(12),"
	        			+ "EMAIL VARCHAR(50),"
	        			+ "PROFILE_PICTURE VARCHAR(500),"
	        			+ "PHOTO_1 VARCHAR(500),"
	        			+ "PHOTO_2 VARCHAR(500),"
	        			+ "PHOTO_3 VARCHAR(500),"
	        			+ "UNIQUE (ID)"
	        			+ ")");
	        	stmt.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				System.out.println(e1.getMessage()+" "+e1.getErrorCode());
				//e1.printStackTrace();
			}
		}
    	
    }
    
    public static boolean checkLogin (String username, String password) {
    	boolean loginSuccess = false;
    	try {
    		stmt = conn.createStatement();
    		stmt.execute("SELECT * FROM USERS WHERE USERNAME='"+username+"' AND PASSWORD='"+password+"'");
    		ResultSet users = stmt.getResultSet();
    		
    		ResultSetMetaData rsmd = users.getMetaData();
    	    System.out.println("querying SELECT * FROM XXX");
    	    int count=0;
    	    while (users.next()) {
    	    	count++;
    	    }
    	    if (count>0) {
    	    	System.out.println("Login successful. Count rows returned: "+count);
    	    	loginSuccess = true;
    	    }
    	    stmt.close();
    	    /*
    	    while (users.next()) {
    	        for (int i = 1; i <= columnsNumber; i++) {
    	            if (i > 1) System.out.print(",  ");
    	            String columnValue = users.getString(i);
    	            System.out.print(columnValue + " " + rsmd.getColumnName(i));
    	        }
    	        System.out.println("");
    	    }
    	    */
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    		return false;
    	}
		return loginSuccess;
    }
    
    public static boolean addUser (String username, String password,String user_role) {
    	boolean addUserSuccess = false;
    	try {
    		stmt = conn.createStatement();
    		stmt.execute("INSERT INTO USERS(USERNAME,PASSWORD,ACCOUNT_TYPE) VALUES ('"+username+"','"+password+"','user_role'"+")");
    		stmt.close();
    		addUserSuccess = true;
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    		return false;
    	}
    	return addUserSuccess;
    }
    
    //METHOD TO ADD PATIENT TO THE DATABASE (PATIENTS TABLE)
    public static boolean addPatient (String first_name, String last_name, String dob, String medical_condition, String gender, String telephone, String email, String profile_picture, String photo_one, String photo_two, String photo_three) {
    	boolean addedPatient = false;
    	try {
        	conn.setAutoCommit(false);
        	
        	//http://stackoverflow.com/questions/27466361/reading-a-file-into-a-derby-database
        	PreparedStatement addPatientPS = null;
        	addPatientPS = conn.prepareStatement("INSERT INTO PATIENTS (FIRST_NAME, LAST_NAME, DOB, MEDICAL_CONDITION, GENDER,"
        			+ "TELEPHONE, EMAIL, PROFILE_PICTURE, PHOTO_1, PHOTO_2,PHOTO_3) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
        	addPatientPS.setString(1, first_name);
        	addPatientPS.setString(2, last_name);
        	addPatientPS.setString(3, dob);
        	addPatientPS.setString(4, medical_condition);
        	addPatientPS.setString(5, gender);
        	addPatientPS.setString(6, telephone);
        	addPatientPS.setString(7, email);
        	addPatientPS.setString(8, profile_picture);
        	addPatientPS.setString(9, photo_one);
        	addPatientPS.setString(10, photo_two);
        	addPatientPS.setString(11, photo_three);
        	addPatientPS.executeUpdate();
        	conn.commit();
        	
        	System.out.println("Patient committed to database");
        	addPatientPS.close();
        	addedPatient = true;
        }
    	catch (SQLException e) {
    		e.printStackTrace();
    		return false;
    	}
    	return addedPatient;
    }
    
    //METHOD TO UPDATE PATIENT
    public static boolean updatePatient (int ID, String first_name, String last_name, String dob, String medical_condition, String gender, String telephone, String email, String profile_picture, String photo_one, String photo_two, String photo_three) {
    	boolean updatedPatient = false;
    	try {
        	conn.setAutoCommit(false);
        	PreparedStatement updatePatientPS = null;
        	updatePatientPS = conn.prepareStatement("UPDATE PATIENTS SET FIRST_NAME=?, LAST_NAME=?, DOB=?, MEDICAL_CONDITION=?, GENDER=?,"
        			+ "TELEPHONE=?, EMAIL=?, PROFILE_PICTURE=?, PHOTO_1=?, PHOTO_2=?, PHOTO_3=? WHERE ID=?");
        	updatePatientPS.setString(1, first_name);
        	updatePatientPS.setString(2, last_name);
        	updatePatientPS.setString(3, dob);
        	updatePatientPS.setString(4, medical_condition);
        	updatePatientPS.setString(5, gender);
        	updatePatientPS.setString(6, telephone);
        	updatePatientPS.setString(7, email);
        	updatePatientPS.setString(8, profile_picture);
        	updatePatientPS.setString(9, photo_one);
        	updatePatientPS.setString(10, photo_two);
        	updatePatientPS.setString(11, photo_three);
        	updatePatientPS.setInt(12,  ID);
        	updatePatientPS.executeUpdate();
        	conn.commit();
        	
        	System.out.println("Patient update committed to database");
        	updatePatientPS.close();
        	updatedPatient = true;
        }
    	catch (SQLException e) {
    		e.printStackTrace();
    		return false;
    	}
    	return updatedPatient;
    }
    
    public static boolean removePhoto(int ID, String column) {
    	boolean removePhoto = false;
    	try {
        	conn.setAutoCommit(false);
        	PreparedStatement removePhotoPS = null;
        	removePhotoPS = conn.prepareStatement("UPDATE PATIENTS SET "+column+"=? WHERE ID=?");
        	removePhotoPS.setString(1, null);
        	removePhotoPS.setInt(2,  ID);
        	removePhotoPS.executeUpdate();
        	conn.commit();
        	
        	System.out.println("Photo removal committed to database");
        	removePhotoPS.close();
        	removePhoto = true;
        }
    	catch (SQLException e) {
    		e.printStackTrace();
    		return false;
    	}
    	return removePhoto;
    }
    //METHOD THAT GETS A RECORD BY ID FORM ANY TABLE IN THE DATABASE, RETURNS RESULTS AS AN ARRAY OF HASH MAPS
    public static ArrayList<HashMap<String, Object>> getRecordByID(String tableName, int ID) {
    	ArrayList<HashMap<String,Object>> results = null;
    	try {
    		stmt = conn.createStatement();
			stmt.execute("SELECT * FROM "+tableName+" WHERE ID="+ID);
			ResultSet record = stmt.getResultSet();
			results = resultsToArrayList(record, stmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return results;
    }
    
    public static boolean deletePatient (int ID) {
    	boolean patientDeleted = false;
    	try {
    		stmt = conn.createStatement();
    		stmt.execute("DELETE FROM PATIENTS WHERE ID="+ID);
    		patientDeleted = true;
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return patientDeleted;
    }
    
    public static  ArrayList<HashMap<String,Object>> searchPatients (HashMap<String,Object> searchCriteria) {
		ArrayList<HashMap<String,Object>> searchResults = null;
    	try {
    		stmt = conn.createStatement();
        	String sqlSelect = "SELECT * FROM PATIENTS";
        	Iterator it = searchCriteria.entrySet().iterator();
        	int keyCount = 0;
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                System.out.println("Key count:"+ keyCount);
                if (pair.getValue().getClass().equals(Integer.class)) {
                    if (keyCount==0) sqlSelect+= " WHERE "+pair.getKey()+"="+pair.getValue();
                    else if (keyCount>0) sqlSelect+= " AND "+pair.getKey()+"="+pair.getValue();
                }
                else {
                    if (keyCount==0) sqlSelect+= " WHERE UPPER("+pair.getKey()+")=UPPER('"+pair.getValue()+"')";
                    else if (keyCount>0) sqlSelect+= " AND UPPER("+pair.getKey()+")=UPPER('"+pair.getValue()+"')";
                }

                keyCount++;
                it.remove(); // avoids a ConcurrentModificationException
            }
            System.out.println("Executing SQL search string:"+sqlSelect);
            stmt.execute(sqlSelect);
            ResultSet searchRS = stmt.getResultSet();
			searchResults = resultsToArrayList(searchRS, stmt);
        	System.out.println("Search completed");
        }
    	catch (SQLException e) {
    		e.printStackTrace();
    		return null;
    	}
    	return searchResults;
    }
    
    //METHOD THAT RETURNS ALL RESULTS FROM A SPECIFIED TABLE
    public static ArrayList<HashMap<String,Object>> allRecords (String tableName) {
    	ArrayList<HashMap<String,Object>> results = null;
    	try {
    		stmt = conn.createStatement();
			stmt.execute("SELECT * FROM "+tableName);
			ResultSet record = stmt.getResultSet();
			results = resultsToArrayList(record, stmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return results;
    }
    
    private static ArrayList<HashMap<String,Object>> resultsToArrayList (ResultSet results, Statement stmt) {
    	ArrayList<HashMap<String,Object>> Rows = null;
		ResultSetMetaData rsmd;
		try {
			rsmd = results.getMetaData();
			//http://stackoverflow.com/questions/11824258/how-to-fetch-entire-row-as-array-of-objects-with-jdbc
			
			//Get Column names
		    int Col_Count = rsmd.getColumnCount();
		    ArrayList<String> Cols = new ArrayList<String>();
		    for (int Index=1; Index<=Col_Count; Index++) Cols.add(rsmd.getColumnName(Index));

		  //fetch out rows
		    Rows = new ArrayList<HashMap<String,Object>>();
		    while (results.next()) {
		      HashMap<String,Object> Row = new HashMap<String,Object>();
		      for (String Col_Name:Cols) {
		        Object Val = results.getObject(Col_Name);
		        Row.put(Col_Name,Val);
		      }
		      Rows.add(Row);
		    }
		    stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return Rows;
    	
    }
    
    public static void dropTable(String tableName) {
    	try {
    		stmt = conn.createStatement();
    		stmt.execute("DROP TABLE "+tableName);
    		stmt.close();
    		System.out.println("Table "+tableName+" deleted.");
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    private static void insertRow(String tableName, String username, String password )
    {
        try
        {
            stmt = conn.createStatement();
            String row = "insert into " + tableName + " values ("+ username + "," + password +")";
            System.out.print(row+"\n");
            stmt.execute(row);
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            System.out.println(sqlExcept.getErrorCode());
            if (sqlExcept.getErrorCode()==30000) {
            	System.out.println("This username already exists");
            	return;
            }
            try {
            	throw sqlExcept;
            }
            catch (SQLException e2) {
            	e2.printStackTrace();
            }
        }
    }
    
    private static void selectAllRows(String tableName)
    {
        try
        {
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("select * from " + tableName);
            ResultSetMetaData rsmd = results.getMetaData();
            int numberCols = rsmd.getColumnCount();
            for (int i=1; i<=numberCols; i++)
            {
                //print Column Names
                System.out.print(rsmd.getColumnLabel(i)+"\t\t");  
            }

            System.out.println("\n---------------------------------------------------------------------");
            
            while(results.next())
            {
                
            	for (int col=1; col<=numberCols; col++) {
            		System.out.print(results.getString(col)+"\t\t");
            	}
            	System.out.println();
            }
            results.close();
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
    
    private static void shutdown()
    {
        try
        {
            if (stmt != null)
            {
                stmt.close();
            }
            if (conn != null)
            {
                DriverManager.getConnection(dbURL + ";shutdown=true");
                conn.close();
            }           
        }
        catch (SQLException sqlExcept)
        {
            
        }

    }
}
