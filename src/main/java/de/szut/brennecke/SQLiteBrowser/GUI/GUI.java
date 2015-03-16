package de.szut.brennecke.SQLiteBrowser.GUI;

import info.monitorenter.gui.chart.Chart2D;

import java.awt.Component;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import de.szut.brennecke.SQLiteBrowser.SQL.SQLConnection;

/**
 * This class is the main frame class. It is the parent of all awt object used
 * in this software.
 * 
 * @author Alexander Brennecke
 *
 */
@SuppressWarnings("serial")
public class GUI extends JFrame {

	// INITIALISATION
	// //////////////
	private GUIController guiController;

	private MenuBar menuBar;
	private DatabaseTree databaseTree;
	private JSplitPane mainSplit;
	private JTabbedPane tabbedPane;

	private Map<String, Component> tabbedComponents;

	private final String TABLE = "Table";
	private final String QUERY = "Query";
	private final String DATABASE_CHART = "Chart";
	private final String LAST_OPENED = "Last Opened";

	// IMPORTANT FUNCTIONS
	// ///////////////////
	/**
	 * Constructor
	 * 
	 * @param guiController
	 *            This class has to know, which GUIController it is handled
	 *            from.
	 */
	public GUI(GUIController guiController) {
		this.guiController = guiController;
		init();
	}

	private void init() {
		menuBar = new MenuBar(this);
		databaseTree = new DatabaseTree(this);

		tabbedComponents = new HashMap<>();
		tabbedComponents.put(QUERY, new QueryPanel(this));
		tabbedComponents.put(TABLE, new DatabaseTablePanel(this));
		tabbedComponents.put(DATABASE_CHART, new DatabaseChartPanel(this));

		tabbedPane = new JTabbedPane();
		updateTabbedPane(TABLE);

		mainSplit = new JSplitPane();
		mainSplit.setLeftComponent(databaseTree);
		mainSplit.setRightComponent(tabbedPane);

		this.setContentPane(mainSplit);
		this.setJMenuBar(menuBar);

	}

	/**
	 * This method calls the GUIGenerator to generate all needed awt objects
	 */
	// public void generateEmptyGUI(){
	// GUIGenerator.generateEmptyGUI(this);
	// }

	// GETTER&SETTER
	// ///////////////////
	public void open() {
		this.setVisible(true);
	}

	public GUIController getGUIController() {
		return this.guiController;
	}

	public String getChosenDatabase() {
		String retval = ((QueryPanel) tabbedComponents.get("Query")).getChosenDatabase();
		return retval;
	}

	public String getQuery() {
		String retval = ((QueryPanel) tabbedComponents.get(QUERY)).getQuery();
		return retval;
	}

	public void showQuery(ResultSet rs) {
		((DatabaseTablePanel) tabbedComponents.get(TABLE)).updateTable(rs);
		updateAll(TABLE);
	}

	public void updateChartPane(Chart2D chart) {
		((DatabaseChartPanel) tabbedComponents.get(DATABASE_CHART)).updateChartPane(chart);
		updateAll(DATABASE_CHART);
	}

	public void updateGUI(ArrayList<SQLConnection> sqlConnections) {
		((QueryPanel) tabbedComponents.get(QUERY)).updateComboBoxes(sqlConnections);
		((DatabaseChartPanel) tabbedComponents.get(DATABASE_CHART)).updateComboBoxes(sqlConnections);
		databaseTree.update(sqlConnections);
		updateAll(LAST_OPENED);
	}

	private void updateAll(String openTabbed) {
		updateTabbedPane(openTabbed);
		updateMainSplit();
		this.setContentPane(mainSplit);
		this.validate();
	}

	private void updateMainSplit() {
		mainSplit.setRightComponent(tabbedPane);
		mainSplit.setLeftComponent(databaseTree);
	}

	private void updateTabbedPane(String openTab) {
		int currentOpenTab = tabbedPane.getSelectedIndex();
		Set<String> keySet = tabbedComponents.keySet();
		tabbedPane.removeAll();
		for (String key : keySet) {
			if (key.equals(TABLE)) {
				tabbedPane.add(key, ((DatabaseTablePanel) tabbedComponents.get(key)).getPane());
			} else {
				tabbedPane.add(key, tabbedComponents.get(key));
			}
		}

		switch (openTab) {
		case TABLE:
			tabbedPane.setSelectedIndex(0);
			break;
		case QUERY:
			tabbedPane.setSelectedIndex(1);
			break;
		case DATABASE_CHART:
			tabbedPane.setSelectedIndex(2);
			break;
		case LAST_OPENED:
			tabbedPane.setSelectedIndex(currentOpenTab);
			break;
		}
	}

}
