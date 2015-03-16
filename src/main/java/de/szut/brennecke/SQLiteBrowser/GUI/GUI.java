package de.szut.brennecke.SQLiteBrowser.GUI;

import info.monitorenter.gui.chart.Chart2D;

import java.awt.Component;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import de.szut.brennecke.SQLiteBrowser.SQL.SQLConnection;

/**
 * This class is the main frame class. It is the parent of all awt object used in this software.
 * @author Alexander Brennecke
 *
 */
@SuppressWarnings("serial")
public class GUI extends JFrame{
	

	//INITIALISATION
	////////////////
	private	GUIController guiController;
	
	private QueryPanel queryPanel;
	private MenuBar menuBar;
	private DatabaseTree databaseTree;
	private DatabaseTablePanel databaseTablePanel;
	private JSplitPane mainSplit;
	private JTabbedPane tabbedPane;
	
	private Map<String, Component> tabbedComponents;
	
	//IMPORTANT FUNCTIONS
	/////////////////////
	/**
	 * Constructor
	 * @param guiController This class has to know, which GUIController it is handled from.
	 */
	public GUI(GUIController guiController) {
		this.guiController=guiController;
		init();
	}
	
	private void init(){
		menuBar = new MenuBar(this);
		databaseTree = new DatabaseTree(this);
		
		tabbedComponents = new HashMap<>();
		tabbedComponents.put("Query", new QueryPanel(this));
		tabbedComponents.put("Table", new DatabaseTablePanel(this));
		
		tabbedPane = new JTabbedPane();
		updateTabbedPane();
		
		mainSplit = new JSplitPane();
		mainSplit.setLeftComponent(databaseTree);
		mainSplit.setRightComponent(tabbedPane);
		this.setContentPane(mainSplit);
		
		this.setJMenuBar(menuBar);
		
	}
	
	/**
	 * This method calls the GUIGenerator to generate all needed awt objects
	 */
//	public void generateEmptyGUI(){
//		GUIGenerator.generateEmptyGUI(this);
//	}
	
	//GETTER&SETTER
	/////////////////////
	public void open(){
		this.setVisible(true);
	}
	
	public GUIController getGUIController(){
		return this.guiController;
	}

	public String getChosenDatabase(){
		return queryPanel.getChosenDatabase();
	}

	public String getQuery() {
		return queryPanel.getQuery();
	}

	public void showQuery(ResultSet rs) {
		((DatabaseTablePanel)tabbedComponents.get("Table")).updateTable(rs);
		updateAll();
	}

	public void updateChartPane(Chart2D chart) {
		// TODO Auto-generated method stub
		
	}

	public void updateGUI(ArrayList<SQLConnection> sqlConnections) {
		((QueryPanel)tabbedComponents.get("Query")).updateComboBoxes(sqlConnections);
		databaseTree.update(sqlConnections);
		updateAll();
	}
	
	private void updateAll(){
		updateTabbedPane();
		updateMainSplit();
		this.setContentPane(mainSplit);
		this.validate();
	}
	
	private void updateMainSplit(){
		mainSplit.setLeftComponent(databaseTree);
		mainSplit.setRightComponent(tabbedPane);
	}
	
	private void updateTabbedPane(){
		tabbedPane.removeAll();
		for(String key : tabbedComponents.keySet() ){
			tabbedPane.add(key, tabbedComponents.get(key));
		}
	}
    
}
