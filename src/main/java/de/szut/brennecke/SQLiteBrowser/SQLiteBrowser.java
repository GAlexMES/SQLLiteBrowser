package de.szut.brennecke.SQLiteBrowser;

import de.szut.brennecke.SQLiteBrowser.DataHandling.Controller;

/**
 * This class is the main class, which is called, when the software is started.
 * @author Alexander Brennecke
 *
 */
public class SQLiteBrowser {

	public static void main(String[] args) {
		Controller con = new Controller();
		con.startGUI();
	}
}
