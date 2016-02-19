package uk.co.ucl.cs.gc01.pms;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class PatientsTable {
	private static TableModel dataModel;
	
	public PatientsTable () {
		//do nothing
	}
	
	public static void updateTable (JTable table, TableModel newModel) {
		//Attach the new model to the table
		table.setModel(newModel);
		table.repaint();
	}
	
	public TableModel getTableModel() {
		return dataModel;
	}
	
	public static TableModel constructTableModel (ArrayList<HashMap<String,Object>> rows, String[] columnNames) {
		//Set all patients as unselected by default
		int numRows = rows.size();
		Boolean[] ticks = new Boolean[numRows];
		for (int i=0;i<ticks.length;i++) ticks[i] = false;
		dataModel = new AbstractTableModel() {
	          public int getColumnCount() { return 9; }
	          public int getRowCount() { return numRows;}
	          public String getColumnName(int col) {return columnNames[col];}
	          public Object getValueAt(int row, int col) { 
	        	  if (col==0) return ticks[row];
	        	  else if (col==1) return new Integer((Integer)rows.get(row).get("ID"));
	        	  else if (col==4) return (Date) rows.get(row).get("DOB");
	        	  else return new String((String)rows.get(row).get(columnNames[col]));
	        	  }
	          public Class<?> getColumnClass (int col) {
	        	  if (col==0) return Boolean.class;
	        	  else if (col==1) return Integer.class;
	        	  else return String.class;
	          }
	          public boolean isCellEditable (int row, int col) {
	        	  if (col==0) return true;
	        	  else return false;
	          }
	          //http://stackoverflow.com/questions/18125995/make-a-jcheckbox-in-a-jtable-editable
	          public void setValueAt(Object value, int row, int col){
	        	  if (col==0) ticks[row] = (Boolean) value;
	        	  fireTableCellUpdated(row,col);
	          }
	          
	      };
	  return dataModel;
	}

}
