package de.szut.brennecke.SQLiteBrowser.GUI;

import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import de.szut.brennecke.SQLiteBrowser.SQL.SQLConnection;

@SuppressWarnings("serial")
public class DatabaseTree extends JTree{
	
	private GUI parentFrame;

	public DatabaseTree(GUI gui){
		this.parentFrame = gui;
	}
	
	/**
	 * This method generates the Tree which shows the databases and tables
	 * 
	 * @param mainFrame
	 * @param sqlConnections
	 *            list with current opened databases
	 * @return a JTree with databases as sub-Elements and tables as
	 *         sub-sub-Elements
	 */
	public void update(ArrayList<SQLConnection> sqlConnections) {
		DefaultMutableTreeNode database = new DefaultMutableTreeNode("Datenbanken");
		for (SQLConnection con : sqlConnections) {
			DefaultMutableTreeNode connection = new DefaultMutableTreeNode(con.getName());
			for (String str : con.getTableNames()) {
				DefaultMutableTreeNode table = new DefaultMutableTreeNode(str);
				connection.add(table);
			}
			database.add(connection);
		}
		
		DefaultTreeModel model = (DefaultTreeModel) this.getModel();
		model.setRoot(database);
		TreeListener treeListener = new TreeListener(parentFrame);
		this.addTreeSelectionListener(treeListener);
		this.addMouseListener(treeListener);
	}
}
