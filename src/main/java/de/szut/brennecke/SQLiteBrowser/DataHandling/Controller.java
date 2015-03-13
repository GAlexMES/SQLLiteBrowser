package de.szut.brennecke.SQLiteBrowser.DataHandling;

import info.monitorenter.gui.chart.Chart2D;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import de.szut.brennecke.SQLiteBrowser.GUI.CSVViewer;
import de.szut.brennecke.SQLiteBrowser.GUI.GUIController;
import de.szut.brennecke.SQLiteBrowser.GUI.GUIGenerator;
import de.szut.brennecke.SQLiteBrowser.SQL.SQLConnection;
import de.szut.brennecke.SQLiteBrowser.SQL.SQLFileNotFoundException;

/**
 * 
 * @author Alexander Brennecke
 * 
 *         This Class is a Controller Class, which works between the
 *         GUIController and the SQLConnections. It handles all open
 *         SQLConnections and gives commands to the GUICOntroller.java
 *
 */
public class Controller {

	// INITIALISATION
	// //////////////
	DatabaseProperties dbProps = new DatabaseProperties();
	GUIController guiController = new GUIController(this);
	private String lastShowQuery = "";
	private Map<String, SQLConnection> sqlConnections = new HashMap<String, SQLConnection>();
	private Boolean wrongQueryFlag = false;

	private final int NUMBER_OF_MAX_LIMIT = 999999999;
	private final String NUMBER_OF_MAX_LIMIT_WARNING = "Da bei einem Offset ein Limit benötigt wird, wurde dies auf '999999999' gesetzt!";

	// IMPORTANT FUNCTIONS
	// ///////////////////

	/**
	 * Opens the GUI main Frame
	 */
	public void startGUI() {
		dbProps.read();
		guiController.startGUI(dbProps.getReadedDBPathes());
	}

	/**
	 * removes a SQLConnection based on it's name
	 * 
	 * @param name
	 *            of the SQLConnection
	 */
	public void removeSQLConnection(String name) {
		sqlConnections.remove(name);
		dbProps.removeDatabase(name);
		guiController.updateGUI(getSqlConnections());
	}

	/**
	 * Adds a SQLConnection to the stack
	 * 
	 * @param filePath
	 *            to the db file
	 * @throws SQLFileNotFoundException
	 *             when path is incorrect
	 */
	public void addSQLConnection(String filePath) throws SQLFileNotFoundException {
		String[] nameSplits = filePath.split("\\\\");
		String dbNameWithDot = nameSplits[nameSplits.length - 1];
		String[] dbNameSplit = dbNameWithDot.split("\\.");
		String dbName = "";
		if (dbNameSplit.length == 0) {
			dbName = dbNameWithDot;
		} else {
			dbName = dbNameSplit[0];
		}
		SQLConnection con = new SQLConnection(dbName);
		try {
			con.loadDB(filePath);
			sqlConnections.put(dbName, con);
			dbProps.addDatabasse(dbName, filePath);
			guiController.updateGUI(getSqlConnections());
		} catch (SQLFileNotFoundException e) {
			throw new SQLFileNotFoundException();
		}

	}

	/**
	 * Says the GUIController to open a table. Is usually called, when a JTree
	 * Tableobject was clicked.
	 * 
	 * @param database
	 *            name of the database, which includes the table
	 * @param table
	 *            name of the table which should be shown
	 */
	public void openTable(String database, String table) {
		SQLConnection sqlCon = sqlConnections.get(database);
		String query = "Select * from " + table;
		ResultSet rs = sqlCon.sendQuery(query);
		guiController.showQuery(rs);
		lastShowQuery = query;
	}

	/**
	 * Sends the query, which is written into the queryTextPane
	 */
	public void sendGUIQuery() {
		String query = guiController.getQuery();
		sendQuery(query);
	}

	/**
	 * the query, which is written into the queryTextPane
	 * 
	 * @param limitValues
	 *            [0] = LIMIT; [1] = OFFSET
	 */
	public void sendGUIQuery(int[] limitValues) {
		boolean showInfoPane = false;
		String startLimit = "";
		String numberOfValues = "";
		String query = guiController.getQuery();
		if (limitValues[0] > 0) {
			startLimit = " LIMIT " + limitValues[0];
		}
		if (limitValues[1] > 0 && limitValues[0] > 0) {
			numberOfValues = " OFFSET " + limitValues[1];
		} else if (limitValues[1] > 0) {
			startLimit = " LIMIT " + NUMBER_OF_MAX_LIMIT;
			numberOfValues = " OFFSET " + limitValues[1];
			showInfoPane = true;
		}
		query = query + startLimit + numberOfValues;
		System.out.println(query);
		sendQuery(query);
		if (showInfoPane) {
			GUIController.generateWrongQuerryInfoPane(NUMBER_OF_MAX_LIMIT_WARNING);
		}
	}

	/**
	 * sends a Query to the chosen database
	 * 
	 * @param query
	 *            query statement
	 */
	public void sendQuery(String query) {
		String sqlConName = guiController.getChosenDatabase();
		SQLConnection sqlCon = getSQLConnection(sqlConName);
		if (sqlCon != null) {
			ResultSet rs = sqlCon.sendQuery(query);
			if (!wrongQueryFlag) {
				if (rs == null) {
					rs = sqlCon.sendQuery(lastShowQuery);
				} else {
					lastShowQuery = query;
				}
				guiController.showQuery(rs);
			}
			wrongQueryFlag = false;
		}
	}

	public void updateChart(Chart2D chart) {
		GUIGenerator.updateChartPane(chart);
	}

	// GETTER&SETTER
	// /////////////

	public SQLConnection getSQLConnection(String name) {
		SQLConnection sqlCon = sqlConnections.get(name);
		return sqlCon;
	}

	public ArrayList<SQLConnection> getSqlConnections() {
		ArrayList<SQLConnection> sqlConnectionsList = new ArrayList<>(sqlConnections.values());
		return sqlConnectionsList;
	}

	public void setWrongQueryFlag(Boolean flag) {
		wrongQueryFlag = flag;
	}

	public Chart2D generateChart(String name, String selectedTableName, String xValueColoum, String yValueColoum) {
		String query = "select " + xValueColoum + ", " + yValueColoum + " from " + selectedTableName;
		ResultSet result = sqlConnections.get(name).sendQuery(query);
		ArrayList<Double[]> resultChartValues = ResultWorkup.getChartValues(result);
		Chart2D retval = ChartDrawer.generateChart(resultChartValues, ChartDrawer.LINE_CHART);
		return retval;
	}

	public void showCSVChart(String filePath) {
		ArrayList<String[]> values = CSVImporter.read(filePath);
		CSVViewer csvViewer = new CSVViewer();
		csvViewer.displayValues(values);
	}

}
