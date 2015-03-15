package de.szut.brennecke.SQLiteBrowser.GUI;

import info.monitorenter.gui.chart.Chart2D;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import de.szut.brennecke.SQLiteBrowser.DataHandling.Controller;
import de.szut.brennecke.SQLiteBrowser.DataHandling.FrameProperties;
import de.szut.brennecke.SQLiteBrowser.SQL.SQLConnection;
import de.szut.brennecke.SQLiteBrowser.SQL.SQLFileNotFoundException;

/**
 * This class controlls everything, that is happen on the GUI.
 * @author Alexander Brennecke
 *
 */
public class GUIController {
	//INITIALISATION
	////////////////
	private GUI gui;
	private static Controller controller = null;
	private FrameProperties frameProperties = new FrameProperties();

	//IMPORTANT FUNCTIONS
	/////////////////////
	/**
	 * 	Constructor
	 *	Gives the main frame the size properties, readed out of the props.ini
	 * @param controller This class needs to know, who his parent controller is
	 */
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

	/**
	 * This method is called, when the GUI is closed. It sets all size properties to write them to props.ini
	 */
	private void setFrameProperties() {
		frameProperties.setMainFrameHeight(gui.getHeight());
		frameProperties.setMainFrameWidth(gui.getWidth());
		frameProperties.setMainFrameLocationX((int) gui.getLocation().getX());
		frameProperties.setMainFrameLocationY((int) gui.getLocation().getY());
		frameProperties.setSplitPaneLocation(0);
	}

	
	/**
	 * This method starts the GUI and says the controller to add SQLConnection, if there are saved SQLConnections from the last Session
	 * @param list of database paths
	 */
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
	
	/**
	 * Generates a new JOptionPane
	 * @param message that should be shown in the OptionPane
	 */
	public static void generateWrongQuerryInfoPane(String message) {
		JOptionPane.showMessageDialog(null, message);
		controller.setWrongQueryFlag(true);
	}
	
	/**
	 * Updated the ComboBox for db selection with new databases
	 * @param sqlConnections
	 */
	public void updateGUI(ArrayList<SQLConnection> sqlConnections) {
		GUIGenerator.updateGUI(gui, sqlConnections);

	}

	//IMPORTANT FUNCTIONS
	/////////////////////
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

	public void generateChart(String name, String selectedTableName, String xValueColoum, String yValueColoum, int buttonType) {
		Chart2D chart = controller.generateChart(name, selectedTableName,xValueColoum, yValueColoum, buttonType);
		GUIGenerator.updateChartPane(chart);
	}
	
}
