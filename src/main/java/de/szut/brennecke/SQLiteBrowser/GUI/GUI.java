package de.szut.brennecke.SQLiteBrowser.GUI;

import java.util.ArrayList;

import javax.swing.JFrame;

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
	
	
	//IMPORTANT FUNCTIONS
	/////////////////////
	/**
	 * Constructor
	 * @param guiController This class has to know, which GUIController it is handled from.
	 */
	public GUI(GUIController guiController) {
		this.guiController=guiController;
	}
	
	/**
	 * This method calls the GUIGenerator to generate all needed awt objects
	 */
	public void generateEmptyGUI(){
		GUIGenerator.generateEmptyGUI(this);
		
	}
	
	//GETTER&SETTER
	/////////////////////
	public void open(){
		this.setVisible(true);
	}
	
	public GUIController getGUIController(){
		return this.guiController;
	}

	public String getChosenDatabase(){
		return GUIGenerator.getChosenDatabase();
	}

	public String getQuery() {
		return GUIGenerator.getTextArea().getText();
	}
    
}
