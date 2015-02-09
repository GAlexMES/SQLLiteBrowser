package de.szut.brennecke.SQLiteBrowser.GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import de.szut.brennecke.SQLiteBrowser.DataHandling.ResultData;
import de.szut.brennecke.SQLiteBrowser.DataHandling.ResultWorkup;
import de.szut.brennecke.SQLiteBrowser.SQL.SQLConnection;

public class GUIGenerator {
	private static JScrollPane scrollPaneTree;
	private static JScrollPane scrollPaneTable;
	private static JTextArea textField = new JTextArea();
	private static JTabbedPane tabbedPane = new JTabbedPane();
	private static Boolean newQuerryReceived = false;
	private static JComboBox<String> databaseComboBox;

	public static GUI generateEmptyGUI(GUI mainFrame) {
		mainFrame.setJMenuBar(generateMenu(mainFrame));
		return mainFrame;
	}

	public static void updateGUI(GUI mainFrame, ArrayList<SQLConnection> sqlConnections) {
		JTree tree = generateTree(mainFrame, sqlConnections);
		scrollPaneTree = new JScrollPane(tree);
		updateMainFrame(mainFrame);
	}

	private static JMenuBar generateMenu(GUI gui) {
		JMenuBar menuBar = new JMenuBar();
		MenuListener menuListener = new MenuListener(gui);

		JMenu menuFile = new JMenu("File");

		String[] menuFileItems = { "Open" };
		for (String str : menuFileItems) {
			JMenuItem menuItem = new JMenuItem(str);
			menuItem.addActionListener(menuListener);
			menuFile.add(menuItem);
		}

		menuBar.add(menuFile);

		return menuBar;
	}

	private static JTree generateTree(GUI mainFrame, ArrayList<SQLConnection> sqlConnections) {
		DefaultMutableTreeNode database = new DefaultMutableTreeNode("Datenbanken");
		for (SQLConnection con : sqlConnections) {
			DefaultMutableTreeNode connection = new DefaultMutableTreeNode(con.getName());
			for (String str : con.getTableNames()) {
				DefaultMutableTreeNode table = new DefaultMutableTreeNode(str);
				connection.add(table);
			}
			database.add(connection);
		}
		JTree tree = new JTree(database);
		TreeListener treeListener = new TreeListener(mainFrame);
		tree.addTreeSelectionListener(treeListener);
		tree.addMouseListener(treeListener);
		return tree;
	}

	private static JTable generateJTable(ResultSet rs) {
		ResultData resultData = ResultWorkup.getTabularDatas(rs);
		JTable table = new JTable(resultData.getData(), resultData.getColumnNames());

		return table;
	}

	public static void showQuery(GUI mainFrame, ResultSet rs) {
		scrollPaneTable = new JScrollPane(generateJTable(rs));
		newQuerryReceived = true;
		updateMainFrame(mainFrame);

	}

	public static JTextArea getTextArea() {
		return textField;
	}

	public static void updateMainFrame(final GUI mainFrame) {
		JSplitPane splitPane;
		if (scrollPaneTable != null) {
		JPanel querryPane = new JPanel();
		querryPane.setSize(scrollPaneTable.getX(), scrollPaneTable.getY());
		querryPane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

//		Dimension buttonMaxDim = new Dimension(scrollPaneTable.getWidth(), 40);

		databaseComboBox = new JComboBox<>();
//		databaseComboBox.setPreferredSize(buttonMaxDim);
		ArrayList<SQLConnection> sqlCons = mainFrame.getGUIController().getController().getSqlConnections();

		for (SQLConnection sqlCon : sqlCons) {
			databaseComboBox.addItem(sqlCon.getName());
		}

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;

		querryPane.add(databaseComboBox,c);
		
		JComboBox co = databaseComboBox;
		c.gridx = 1;
		querryPane.add(co,c);
		
		JButton executeButton = new JButton("execute");
		executeButton.setSize(scrollPaneTable.getX(), scrollPaneTable.getY());
//		executeButton.setPreferredSize(buttonMaxDim);
		executeButton.addActionListener(new GUIKeyListener(mainFrame));

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.PAGE_END;

		querryPane.add(executeButton,c);
		int selectedIndex = tabbedPane.getSelectedIndex();

		tabbedPane.removeAll();
		tabbedPane.addTab("Tabelle", scrollPaneTable);
		tabbedPane.addTab("Query", querryPane);

		if (selectedIndex == -1 || newQuerryReceived) {
			tabbedPane.setSelectedIndex(0);
			newQuerryReceived = false;
		} else {
			tabbedPane.setSelectedIndex(selectedIndex);
		}
		}
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPaneTree, tabbedPane);
		mainFrame.getContentPane().removeAll();
		mainFrame.getContentPane().add(splitPane);
		mainFrame.validate();
		mainFrame.repaint();

		// JSplitPane splitPane;
		// if (scrollPaneTable != null) {
		// Dimension buttonMaxDim = new Dimension(scrollPaneTable.getWidth(),
		// 40);
		//
		// JButton executeButton = new JButton("execute");
		// executeButton.setPreferredSize(buttonMaxDim);
		// executeButton.addActionListener(new GUIKeyListener(mainFrame));
		//
		// JScrollPane scrollPane = new JScrollPane(textField);
		// databaseComboBox = new JComboBox<>();
		// databaseComboBox.setPreferredSize(buttonMaxDim);
		// ArrayList<SQLConnection> sqlCons =
		// mainFrame.getGUIController().getController().getSqlConnections();
		//
		// for (SQLConnection sqlCon : sqlCons) {
		// databaseComboBox.addItem(sqlCon.getName());
		// }
		//
		// JSplitPane northernSplitPane = new
		// JSplitPane(JSplitPane.VERTICAL_SPLIT) {
		// private final int location = 35;
		// {
		// setDividerLocation(location);
		// }
		//
		// @Override
		// public int getDividerLocation() {
		// return location;
		// }
		//
		// @Override
		// public int getLastDividerLocation() {
		// return location;
		// }
		// };
		//
		// northernSplitPane.setTopComponent(databaseComboBox);
		// northernSplitPane.setBottomComponent(scrollPane);
		//
		// @SuppressWarnings("serial")
		// JSplitPane southernSplitPane = new
		// JSplitPane(JSplitPane.VERTICAL_SPLIT) {
		// private final int location = mainFrame.getHeight() - 150;
		// {
		// setDividerLocation(location);
		// }
		//
		// @Override
		// public int getDividerLocation() {
		// return location;
		// }
		//
		// @Override
		// public int getLastDividerLocation() {
		// return location;
		// }
		// };
		//
		// southernSplitPane.setTopComponent(northernSplitPane);
		// southernSplitPane.setBottomComponent(executeButton);
		//
		// int selectedIndex = tabbedPane.getSelectedIndex();
		//
		// tabbedPane.removeAll();
		// tabbedPane.addTab("Tabelle", scrollPaneTable);
		// tabbedPane.addTab("Query", southernSplitPane);
		//
		// if (selectedIndex == -1 || newQuerryReceived) {
		// tabbedPane.setSelectedIndex(0);
		// newQuerryReceived = false;
		// } else {
		// tabbedPane.setSelectedIndex(selectedIndex);
		// }
		// splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
		// scrollPaneTree, tabbedPane);
		//
		// } else {
		// splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
		// scrollPaneTree, null);
		// }
		// splitPane.setDividerLocation(150);
		// mainFrame.getContentPane().removeAll();
		// mainFrame.getContentPane().add(splitPane);
		// mainFrame.validate();
		// mainFrame.repaint();
	}

	public static void updateComponents() {

	}

	public static String getChosenDatabase() {
		return databaseComboBox.getSelectedItem().toString();
	}

}
