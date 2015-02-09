package de.szut.brennecke.SQLiteBrowser.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import de.szut.brennecke.SQLiteBrowser.DataHandling.Controller;
import de.szut.brennecke.SQLiteBrowser.SQL.SQLFileNotFoundException;

public class MenuListener implements ActionListener {
	private GUI gui;

	public MenuListener(GUI gui) {
		this.gui = gui;
	}

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
