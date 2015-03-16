package de.szut.brennecke.SQLiteBrowser.GUI;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar{

	private GUI parentFrame;
	public MenuBar(GUI gui){
		parentFrame = gui;
		init();
	}
	
	private void init(){
		MenuListener menuListener = new MenuListener(parentFrame);

		JMenu menuFile = new JMenu("File");

		String[] menuFileItems = { "Open Database", "Show CSV in chart"};
		for (String str : menuFileItems) {
			JMenuItem menuItem = new JMenuItem(str);
			menuItem.addActionListener(menuListener);
			menuFile.add(menuItem);
		}

		this.add(menuFile);

	}

}
