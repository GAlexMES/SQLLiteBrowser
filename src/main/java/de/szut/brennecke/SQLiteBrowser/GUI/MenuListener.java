package de.szut.brennecke.SQLiteBrowser.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import de.szut.brennecke.SQLiteBrowser.SQL.SQLFileNotFoundException;

/**
 * This class is a MenuListener which handles the MenuEvents.
 * @author Alexander Brennecke
 *
 */
public class MenuListener implements ActionListener {
	//INITIALISATION
	////////////////
	private GUI gui;

	//IMPORTANT FUNCTIONS
	/////////////////////
	/**
	 * Constructor
	 * @param gui
	 */
	public MenuListener(GUI gui) {
		this.gui = gui;
	}

	/**
	 * Called when a MenuItem was clicked.
	 * Chooses the correct way, depend on which MenuItem was clicked.
	 */
	public void actionPerformed(ActionEvent arg0) {
		String itemText = ((JMenuItem) arg0.getSource()).getText();
		switch (itemText) {
		case "Open":
			final JFileChooser fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				String filePath = fileChooser.getSelectedFile().getAbsolutePath();
				try {
					gui.getGUIController().getController().addSQLConnection(filePath);
				} catch (SQLFileNotFoundException e) {
					e.printStackTrace();
				}
			}
			break;
		default:
			break;
		}
	}
}
