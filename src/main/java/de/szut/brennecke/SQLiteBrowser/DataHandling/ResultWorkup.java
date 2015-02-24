package de.szut.brennecke.SQLiteBrowser.DataHandling;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;



public class ResultWorkup {

	private static Object[][] datas;

	public static ResultData getTabularDatas(ResultSet rs) {
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
			int counter = 0;
			while (rs.next()) {
				data.add(new ArrayList<String>());
				for (int element = 1; element <= columnNames.length; element++) {
					data.get(counter).add(rs.getString(element));
				}
				counter++;
			}
		} catch (SQLException e) {
		}
		catch (NullPointerException npe){
			System.err.println("No Result-Set returnded!");
		}

		datas = new Object[data.size()][data.get(0).size()];

		for (int list = 0; list < data.size(); list++) {
			for (int element = 0; element < data.get(0).size(); element++) {
				datas[list][element] = data.get(list).get(element);
			}
		}
		return new ResultData(columnNames, datas);
	}
}
