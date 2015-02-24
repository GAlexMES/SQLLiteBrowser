package de.szut.brennecke.SQLiteBrowser.GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import de.szut.brennecke.SQLiteBrowser.DataHandling.ResultWorkup;
import de.szut.brennecke.SQLiteBrowser.SQL.SQLConnection;

public class GUIGenerator {
	private final static String WRONG_LIMIT_INPUT = "Falsche Eingabe im Limit Feld. Diese wird ignoriert!";
	private final static String WRONG_OFFSET_INPUT = "Falsche Eingabe im Offset Feld. Diese wird ignoriert!";
	
	
	private static JTable resultTable;
	private static JScrollPane scrollPaneTree;
	private static JScrollPane scrollPaneTable;
	private static JTextArea textField;
	private static JTabbedPane tabbedPane;
	private static Boolean newQuerryReceived = false;
	private static JComboBox<String> databaseComboBox;
	private static JPanel querryPane;
	private static JSplitPane splitPane;

	public static GUI generateEmptyGUI(GUI mainFrame) {

		// Initialisation
		// //////////////

		resultTable = new JTable();
		textField = new JTextArea();
		tabbedPane = new JTabbedPane();
		querryPane = new JPanel();
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		generateQueryTab(mainFrame);
		mainFrame.setJMenuBar(generateMenu(mainFrame));
		scrollPaneTable = new JScrollPane(resultTable);

		return mainFrame;
	}

	public static void updateTree(GUI mainFrame, ArrayList<SQLConnection> sqlCons) {
		JTree tree = generateTree(mainFrame, sqlCons);
		scrollPaneTree = new JScrollPane(tree);
		splitPane.setLeftComponent(scrollPaneTree);
	}

	public static void updateTable(ArrayList<SQLConnection> sqlCons) {
		updateComboBox(sqlCons);
		generateTabbedPane();
		splitPane.setRightComponent(tabbedPane);
	}

	private static void updateFrame(GUI mainFrame) {
		mainFrame.getContentPane().removeAll();
		int splitPaneLoc = splitPane.getDividerLocation();
		mainFrame.getContentPane().add(splitPane);
		splitPane.setDividerLocation(splitPaneLoc);
		mainFrame.validate();
		mainFrame.repaint();
	}

	public static void updateGUI(GUI mainFrame, ArrayList<SQLConnection> sqlCons) {
		updateTable(sqlCons);
		updateTree(mainFrame, sqlCons);
		updateFrame(mainFrame);
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

	private static void generateJTable(ResultSet rs, GUI mainFrame) {
		DefaultTableModel resultData = null;
		resultData = ResultWorkup.getTabularDatas(rs);
		resultTable.setAutoCreateRowSorter(true);
		resultTable.setModel(resultData);
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
		JLabel limitText = new JLabel("Limit:");
		limitPaneC.gridx = 1;
		limitPane.add(limitText, limitPaneC);

		// LIMIT START VALUE INPUT FIELD
		final JTextField limitStartInputField = new JTextField();
		limitStartInputField.setText("0");
		limitPaneC.gridx = 2;
		limitPaneC.ipadx = 20;
		limitPane.add(limitStartInputField, limitPaneC);
		limitPaneC.ipadx = 0;

		// LIMIT CHECK BOX
		final JCheckBox offsetCheck = new JCheckBox();
		limitPaneC.gridx = 3;
		limitPane.add(offsetCheck, limitPaneC);

		// LIMIT START VALUE FIELD
		JLabel limitNumberText = new JLabel("Offsett");
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
				int startValue = 0;
				int numberOfValues = 0;
				
				if(limitCheck.isSelected()){
				try {
					startValue = Integer.valueOf(limitStartInputField.getText());
				} catch (NumberFormatException nfe) {
					GUIController.generateWrongQuerryInfoPane(WRONG_LIMIT_INPUT);
				}
				}
				if(offsetCheck.isSelected()){
				try {
					numberOfValues = Integer.valueOf(limitNumberInputField.getText());
				} catch (NumberFormatException nfe) {
					GUIController.generateWrongQuerryInfoPane(WRONG_OFFSET_INPUT);
				}
				}
				int[] limitValues = { startValue, numberOfValues };
				mainFrame.getGUIController().getController().sendGUIQuery(limitValues);
			}
		});

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.PAGE_END;

		querryPane.add(executeButton, c);
	}

	public static void generateTabbedPane() {
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
	}

	public static String getChosenDatabase() {
		return databaseComboBox.getSelectedItem().toString();
	}

	private static void updateComboBox(ArrayList<SQLConnection> sqlCons) {
		databaseComboBox.removeAll();
		databaseComboBox.removeAllItems();
		for (SQLConnection sqlCon : sqlCons) {
			databaseComboBox.addItem(sqlCon.getName());
		}
	}

	public static void showQuery(GUI mainFrame, ResultSet rs) {
		newQuerryReceived = true;
		scrollPaneTable = new JScrollPane(generateJTable(rs));
		JTabbedPane newTabbedPane = generateTabbedPane();
		splitPane.setRightComponent(newTabbedPane);
		updateFrame(mainFrame);
	}

	public static JTextArea getTextArea() {
		return textField;
	}

}
