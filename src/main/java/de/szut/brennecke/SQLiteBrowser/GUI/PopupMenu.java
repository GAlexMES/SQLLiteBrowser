package de.szut.brennecke.SQLiteBrowser.GUI;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.szut.brennecke.SQLiteBrowser.DataHandling.Controller;

@SuppressWarnings("serial")
public class PopupMenu extends JPopupMenu {
	String nameOfSelectedDatabase = "";
	public PopupMenu(final GUI gui) {
		final JMenuItem deleteItem = new JMenuItem("Close Database!");
		deleteItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Controller controller = gui.getGUIController().getController();
				Point point = deleteItem.getParent().getLocation();
				System.out.println(point.getX());
				controller.removeSQLConnection(nameOfSelectedDatabase);
			}
		});
		add(deleteItem);
		setVisible(false);
	}

	public void setSelectedTreeName(Object name) {
		nameOfSelectedDatabase = String.valueOf(name);
	}

}
