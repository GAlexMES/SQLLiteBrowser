package de.szut.brennecke.SQLiteBrowser.DataHandling;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang3.StringUtils;

/**
 * This class is used to analyse a ResultSet and to generate a TableModel.
 * 
 * @author Alexander Brennecke
 *
 */
public class ResultWorkup {

	/**
	 * This method generates a new DefaultTableModel out of a ResultSet.
	 * 
	 * @param rs
	 *            ResultSet which should be converted into a DefaultTableModel
	 * @return retval DefaultTableModel, which includes the values of the
	 *         ResultSet
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
					row[element - 1] = rs.getString(element);
				}
				retval.addRow(row);
			}
		} catch (SQLException e) {
		} catch (NullPointerException npe) {
			System.err.println("No Result-Set returnded!");
		}
		return retval;
	}

	public static ArrayList<Double[]> getChartValues(ResultSet result) {
		ArrayList<Double[]> retval = new ArrayList<>();
		try {
			while (result.next()) {
				String xValue = result.getString(1);
				String yValue = result.getString(2);
				if (isValueUseful(xValue) && isValueUseful(yValue)) {
					Double[] row = new Double[2];
					row[0] = Double.parseDouble(xValue);
					row[1] = Double.parseDouble(yValue);
					retval.add(row);
				}

			}
		} catch (NumberFormatException | SQLException e) {
			
		}

		return retval;
	}
	
	public static ArrayList<Double[]> getChartValues(JTable table, Map<String, Integer> axis) {
		ArrayList<Double[]> retval = new ArrayList<>();
		
		Set<String> axisKeys = axis.keySet();
		int rowCounter = table.getRowCount();

		for (int row = 0; row < rowCounter; row++) {
			Double[] rowValue = new Double[2];
			String xValue = (String) table.getValueAt(row, axis.get("x"));
			String yValue = (String) table.getValueAt(row, axis.get("y"));
			rowValue[0] = Double.parseDouble(xValue);
			rowValue[1] = Double.parseDouble(yValue);
			retval.add(rowValue);
		}
		
		
//		try {
//			while (result.next()) {
//				String xValue = result.getString(1);
//				String yValue = result.getString(2);
//				if (isValueUseful(xValue) && isValueUseful(yValue)) {
//					Double[] row = new Double[2];
//					row[0] = Double.parseDouble(xValue);
//					row[1] = Double.parseDouble(yValue);
//					retval.add(row);
//				}
//
//			}
//		} catch (NumberFormatException | SQLException e) {
//			
//		}

		return retval;
	}

	private static Boolean isValueUseful(String value) {
		if (isNullOrEmpty(value)) {
			if (isNumeric(value)) {
				return true;
			}
		}
		return false;
	}

	private static Boolean isNullOrEmpty(String value) {
		if (!(value == null)) {
			if (!StringUtils.isEmpty(value) && !StringUtils.isBlank(value)) {
				return true;
			}
		}
		return false;

	}

	private static Boolean isNumeric(String value) {
		try {
			Double.parseDouble(value);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
