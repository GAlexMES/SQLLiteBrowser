package de.szut.brennecke.SQLiteBrowser.GUI;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import de.szut.brennecke.SQLiteBrowser.DataHandling.Controller;
import de.szut.brennecke.SQLiteBrowser.DataHandling.FrameProperties;
import de.szut.brennecke.SQLiteBrowser.SQL.SQLConnection;
import de.szut.brennecke.SQLiteBrowser.SQL.SQLFileNotFoundException;

public class GUIController {
	private GUI gui;
	private static Controller controller = null;
	private FrameProperties frameProperties = new FrameProperties();

	public GUIController(Controller controller) {
		GUIController.controller = controller;
		gui = new GUI(this);
		gui.setSize(frameProperties.getMainFrameWidth(), frameProperties.getMainFrameHeight());
		gui.setLocation(frameProperties.getMainFrameLocationX(), frameProperties.getMainFrameLocationY());
		gui.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		gui.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				setFrameProperties();
				
				frameProperties.write();
			}
		});

	}

	private void setFrameProperties() {
		frameProperties.setMainFrameHeight(gui.getHeight());
		frameProperties.setMainFrameWidth(gui.getWidth());
		frameProperties.setMainFrameLocationX((int) gui.getLocation().getX());
		frameProperties.setMainFrameLocationY((int) gui.getLocation().getY());
		frameProperties.setSplitPaneLocation(0);
	}

	public void startGUI(List<String> list) {
		gui.generateEmptyGUI();
		if (!list.isEmpty() && !list.equals(null)) {
			for (String dbPath : list) {
				try {
					controller.addSQLConnection(dbPath);
				} catch (SQLFileNotFoundException e) {
					generateWrongQuerryInfoPane(e.getMessage());
				}
			}
		}
		gui.open();

	}

	public void updateGUI(ArrayList<SQLConnection> sqlConnections) {
		gui.updateGUI(sqlConnections);

	}

	public Controller getController() {
		return GUIController.controller;
	}

	public String getChosenDatabase() {
		return gui.getChosenDatabase();
	}

	public String getQuery() {
		return gui.getQuery();
	}

	public void showQuery(ResultSet rs) {
		GUIGenerator.showQuery(gui, rs);
	}

	public static void generateWrongQuerryInfoPane(String message) {
		JOptionPane.showMessageDialog(null, message);
		controller.setWrongQueryFlag(true);
	}
}
