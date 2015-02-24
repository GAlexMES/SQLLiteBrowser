package de.szut.brennecke.SQLiteBrowser.DataHandling;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

/**
 * This class is used to analyse a ResultSet and to generate a TableModel.
 * 
 * @author Alexander Brennecke
 *
 */
public class ResultWorkup {

	/**
	 * This method generates a new DefaultTableModel out of a ResultSet.
	 * @param rs ResultSet which should be converted into a DefaultTableModel
	 * @return retval DefaultTableModel, which includes the values of the ResultSet
	 */
	public static DefaultTableModel getTabularDatas(ResultSet rs) {
		DefaultTableModel retval = new DefaultTableModel();
		ResultSetMetaData rsmd;
		ArrayList<ArrayList<String>> data = new ArrayList<>();
		Object[] columnNames = null;
		try {
			rsmd = rs.getMetaData();
			columnNames = new Object[rsmd.getColumnCount()];
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				String name = rsmd.getColumnName(i);
				columnNames[i - 1] = name;
			}
			
			retval.setColumnIdentifiers(columnNames);
			
			while (rs.next()) {
				data.add(new ArrayList<String>());
				String[] row = new String[rsmd.getColumnCount()];
				for (int element = 1; element <= columnNames.length; element++) {
					row[element-1]=rs.getString(element);
				}
				retval.addRow(row);
			}
		} catch (SQLException e) {
		}
		catch (NullPointerException npe){
			System.err.println("No Result-Set returnded!");
		}		
		return retval;
	}
}
