package de.szut.brennecke.SQLiteBrowser.GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import javax.swing.JTextField;
import javax.swing.JTextPane;
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
	private static JPanel querryPane = new JPanel();

	public static GUI generateEmptyGUI(GUI mainFrame) {
		generateQueryTab(mainFrame);
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
	
	public static void generateQueryTab(final GUI mainFrame) {
		querryPane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// ////////
		// COMBOBOX
		// ////////
		databaseComboBox = new JComboBox<>();
		ArrayList<SQLConnection> sqlCons = mainFrame.getGUIController().getController().getSqlConnections();

		for (SQLConnection sqlCon : sqlCons) {
			databaseComboBox.addItem(sqlCon.getName());
		}

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;

		querryPane.add(databaseComboBox, c);

		// ////////
		// TEXTAREA
		// ////////
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1.0;
		c.gridy = 1;

		JScrollPane textScrollPane = new JScrollPane(textField);

		querryPane.add(textScrollPane, c);

		// ////////
		// LIMIT
		// ////////

		JPanel limitPane = new JPanel(new GridBagLayout());
		GridBagConstraints limitPaneC = new GridBagConstraints();
		limitPaneC.fill = GridBagConstraints.HORIZONTAL;

		// LIMIT CHECK BOX
		final JCheckBox limitCheck = new JCheckBox();
		limitPaneC.gridy = 0;
		limitPane.add(limitCheck, limitPaneC);

		// LIMIT TEXT FIELD
		JTextPane limitText = new JTextPane();
		limitText.setText("Limit");
		limitPaneC.gridx = 1;
		limitPane.add(limitText, limitPaneC);

		// LIMIT START VALUE FIELD
		JTextPane limitStartText = new JTextPane();
		limitStartText.setText("start value");
		limitPaneC.gridx = 2;
		limitPane.add(limitStartText, limitPaneC);

		// LIMIT START VALUE INPUT FIELD
		final JTextField limitStartInputField = new JTextField();
		limitStartInputField.setText("0");
		limitPaneC.gridx = 3;
		limitPaneC.ipadx = 20;
		limitPane.add(limitStartInputField, limitPaneC);
		limitPaneC.ipadx = 0;

		// LIMIT START VALUE FIELD
		JTextPane limitNumberText = new JTextPane();
		limitNumberText.setText("number of entrys ('0'||''==all)");
		limitPaneC.gridx = 4;
		limitPane.add(limitNumberText, limitPaneC);

		// LIMIT START VALUE INPUT FIELD
		final JTextField limitNumberInputField = new JTextField();
		limitNumberInputField.setText("0");
		limitPaneC.ipadx = 20;
		limitPaneC.gridx = 5;
		limitPane.add(limitNumberInputField, limitPaneC);
		limitPaneC.ipadx = 0;

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.PAGE_END;

		querryPane.add(limitPane, c);

		// ////////
		// BUTTON
		// ////////

		JButton executeButton = new JButton("execute");
		executeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (limitCheck.isSelected()) {
					int startValue = Integer.valueOf(limitStartInputField.getText());
					int numberOfValues = Integer.valueOf(limitNumberInputField.getText());
					int[] limitValues = { startValue, numberOfValues };
					mainFrame.getGUIController().getController().sendGUIQuery(limitValues);
				} else {
					mainFrame.getGUIController().getController().sendGUIQuery();
				}
			}
		});

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.PAGE_END;

		querryPane.add(executeButton, c);
	}

	public static void updateMainFrame(final GUI mainFrame) {
		ArrayList<SQLConnection> sqlCons = mainFrame.getGUIController().getController().getSqlConnections();
		updateComboBox(sqlCons);
		JSplitPane splitPane;
		// ////////
		// TABBEDPANE
		// ////////
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
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPaneTree, tabbedPane);
		mainFrame.getContentPane().removeAll();
		mainFrame.getContentPane().add(splitPane);
		mainFrame.validate();
		mainFrame.repaint();
	}

	public static String getChosenDatabase() {
		return databaseComboBox.getSelectedItem().toString();
	}
	
	private static void updateComboBox(ArrayList<SQLConnection> sqlCons){
		databaseComboBox.removeAll();
		databaseComboBox.removeAllItems();
		for(SQLConnection sqlCon : sqlCons){
			databaseComboBox.addItem(sqlCon.getName());
		}
	}
	
	public static void showQuery(GUI mainFrame, ResultSet rs) {
		scrollPaneTable = new JScrollPane(generateJTable(rs));
		newQuerryReceived = true;
		updateMainFrame(mainFrame);
	}

	public static JTextArea getTextArea() {
		return textField;
	}

}
