package de.szut.brennecke.SQLiteBrowser.DataHandling;

import java.util.HashMap;
import java.util.Map;
import java.sql.ResultSet;
import java.util.ArrayList;

import de.szut.brennecke.SQLiteBrowser.GUI.GUIController;
import de.szut.brennecke.SQLiteBrowser.SQL.SQLConnection;

import de.szut.brennecke.SQLiteBrowser.SQL.SQLFileNotFoundException;

public class Controller {
	DatabaseProperties dbProps = new DatabaseProperties();
	GUIController guiController = new GUIController(this);
	private String lastShowQuery = "";
	private Map<String, SQLConnection> sqlConnections = new HashMap<String, SQLConnection>();
	private Boolean wrongQueryFlag = false;

	public Controller() {
	}

	public void startGUI() {
		dbProps.read();
		guiController.startGUI(dbProps.getReadedDBPathes());
	}
	
	public void removeSQLConnection(String name){
		sqlConnections.remove(name);
		dbProps.removeDatabase(name);
		guiController.updateGUI(getSqlConnections());
	}

	public void addSQLConnection(String filePath) throws SQLFileNotFoundException {
		String[] nameSplits = filePath.split("\\\\");
		String dbName = nameSplits[nameSplits.length - 1];
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

	public void openTable(String database, String table) {
		SQLConnection sqlCon = sqlConnections.get(database);
		String query = "Select * from " + table;
		ResultSet rs = sqlCon.sendQuery(query);
		guiController.showQuery(rs);
		lastShowQuery = query;
	}

	public void sendGUIQuery() {
		String query = guiController.getQuery();
		sendQuery(query);
	}

	public void setWrongQueryFlag(Boolean flag) {
		wrongQueryFlag = flag;
	}

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

	private SQLConnection getSQLConnection(String name) {
		SQLConnection sqlCon = sqlConnections.get(name);
		return sqlCon;
	}

	public ArrayList<SQLConnection> getSqlConnections() {
		ArrayList<SQLConnection> sqlConnectionsList = new ArrayList<>(sqlConnections.values());
		return sqlConnectionsList;
	}

}
