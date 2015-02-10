package de.szut.brennecke.SQLiteBrowser.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIKeyListener implements ActionListener {

	private GUI gui;

	public GUIKeyListener(GUI gui) {
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		gui.getGUIController().getController().sendGUIQuery();		
	}

}
