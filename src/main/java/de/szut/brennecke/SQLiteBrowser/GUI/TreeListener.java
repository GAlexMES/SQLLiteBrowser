package de.szut.brennecke.SQLiteBrowser.GUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

public class TreeListener implements TreeSelectionListener, MouseListener {

	private GUIController guiController;
	private PopupMenu popupMenu;
	public TreeListener(GUI gui) {
		this.guiController = gui.getGUIController();
		popupMenu = new PopupMenu(gui);
	}

	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		TreePath treePath = ((JTree) arg0.getSource()).getSelectionPath();
		if (treePath != null) {
			if (treePath.getPathCount() == 3) {
				String table = treePath.getLastPathComponent().toString();
				String database = treePath.getPath()[1].toString();
				guiController.getController().openTable(database, table);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			JTree tree = ((JTree) e.getSource());
			int row = tree.getRowForLocation(e.getX(), e.getY());
			if (row != -1) {
				tree.setSelectionRow(row);
				TreePath path = tree.getSelectionPath();
				int pathCount = path.getPathCount();
				if (pathCount == 2) {
					popupMenu.setSelectedTreeName(path.getPath()[1]);
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
