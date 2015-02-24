package de.szut.brennecke.SQLiteBrowser.GUI;

import java.util.ArrayList;

import javax.swing.JFrame;

import de.szut.brennecke.SQLiteBrowser.SQL.SQLConnection;

@SuppressWarnings("serial")
public class GUI extends JFrame{

	private	GUIController guiController;
	
	public GUI(GUIController guiController) {
		this.guiController=guiController;
	}
	
	public void generateEmptyGUI(){
		GUIGenerator.generateEmptyGUI(this);
		
	}
	
	public void open(){
		this.setVisible(true);
	}
	
	public GUIController getGUIController(){
		return this.guiController;
	}

	public void updateGUI(ArrayList<SQLConnection> sqlConnections) {
		GUIGenerator.updateGUI(this, sqlConnections);
	}

	public String getChosenDatabase(){
		return GUIGenerator.getChosenDatabase();
	}

	public String getQuery() {
		return GUIGenerator.getTextArea().getText();
	}
    
}
