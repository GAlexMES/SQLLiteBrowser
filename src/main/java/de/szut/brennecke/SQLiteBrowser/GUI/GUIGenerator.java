package de.szut.brennecke.SQLiteBrowser.GUI;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.views.ChartPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import de.szut.brennecke.SQLiteBrowser.DataHandling.ChartDrawer;
import de.szut.brennecke.SQLiteBrowser.DataHandling.ResultWorkup;
import de.szut.brennecke.SQLiteBrowser.SQL.SQLConnection;

/**
 * This class generates every GUI object (without the main Frame) which is used
 * for this software.
 * 
 * @author Alexander Brennecke
 *
 */
public class GUIGenerator {
	// INITIALISATION
	// ///////////////
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
	private static JScrollPane scrollPaneChart;

	// IMPORTANT FUNCTIONS
	//////////////////////
	/**
	 * This method generates Tabs, Tables and Menus for the FIRST time only.
	 * 
	 * @param mainFrame
	 * @return main Frame with menu
	 */
	public static GUI generateEmptyGUI(GUI mainFrame) {
		// DEKLARATION
		//////////////
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

	/**
	 * This method is called when a ResultSet should be displayed. It updates
	 * the TableModel and the main frame.
	 * 
	 * @param mainFrame
	 * @param rs
	 *            new ResultSet with new Values
	 */
	public static void showQuery(GUI mainFrame, ResultSet rs) {
		newQuerryReceived = true;
		updateTable(rs, mainFrame);
		generateTabbedPane();
		splitPane.setRightComponent(tabbedPane);
		updateFrame(mainFrame);
	}
	
	/**
	 * This method exchanges the TableModel with a new one
	 * @param rs
	 * @param mainFrame
	 */
	private static void updateTable(ResultSet rs, GUI mainFrame) {
		DefaultTableModel resultData = null;
		resultData = ResultWorkup.getTabularDatas(rs);
		resultTable.setAutoCreateRowSorter(true);
		resultTable.setModel(resultData);
	}

	/**
	 * This method updates the tree, which shows the opened databases
	 * 
	 * @param mainFrame
	 * @param sqlCons
	 *            list of open SQLConnections
	 */
	public static void updateTree(GUI mainFrame, ArrayList<SQLConnection> sqlCons) {
		JTree tree = generateTree(mainFrame, sqlCons);
		scrollPaneTree = new JScrollPane(tree);
		splitPane.setLeftComponent(scrollPaneTree);
	}

	/**
	 * This method updates the hole main frame
	 * It removes and add the splitPane, which includes all other elements
	 * @param mainFrame
	 */
	private static void updateFrame(GUI mainFrame) {
		mainFrame.getContentPane().removeAll();
		int splitPaneLoc = splitPane.getDividerLocation();
		mainFrame.getContentPane().add(splitPane);
		splitPane.setDividerLocation(splitPaneLoc);
		mainFrame.validate();
		mainFrame.repaint();
	}

	/**
	 * This method updates every component placed on the main frame. Excepted is the data table!
	 * @param mainFrame
	 * @param sqlCons list with current opened databases
	 */
	public static void updateGUI(GUI mainFrame, ArrayList<SQLConnection> sqlCons) {
		updateComboBox(sqlCons);
		updateTree(mainFrame, sqlCons);
		updateFrame(mainFrame);
	}

	/**
	 * This method generates the menu
	 * @param gui
	 * @return a JMenuBar which can be added to the main frame
	 */
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

	/**
	 * This method generates the Tree which shows the databases and tables
	 * @param mainFrame
	 * @param sqlConnections list with current opened databases
	 * @return a JTree with databases as sub-Elements and tables as sub-sub-Elements
	 */
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

	/**
	 * 	This method generates the QueryTab, which will be added to the TabPane.
	 * The QuerryTab includes:
	 * 	<li> ComboBox with currently opened databases </li>
	 * 	<li> TextPane for the query input</li>
	 * 	<li> CheckBoxes and Input fields for LIMIT and OFFSET Statements </li>
	 * 	<li> A execute button to execude the query </li>
	 * @param mainFrame
	 */
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
				ChartDrawer chart = new ChartDrawer(mainFrame.getGUIController().getController());
				int startValue = 0;
				int numberOfValues = 0;

				if (limitCheck.isSelected()) {
					try {
						startValue = Integer.valueOf(limitStartInputField.getText());
					} catch (NumberFormatException nfe) {
						GUIController.generateWrongQuerryInfoPane(WRONG_LIMIT_INPUT);
					}
				}
				if (offsetCheck.isSelected()) {
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
	
	public static void generateChartPane(Chart2D chart){
		ChartPanel chartPanel = new ChartPanel(chart);
		scrollPaneChart = new JScrollPane(chartPanel);
		generateTabbedPane();
	}
	
	/**
	 * This method generates the TabbedPane, which handles the TablePane and QueryPane.
	 */
	public static void generateTabbedPane() {
		// ////////
		// TABBEDPANE
		// ////////
		int selectedIndex = tabbedPane.getSelectedIndex();

		tabbedPane.removeAll();
		tabbedPane.addTab("Tabelle", scrollPaneTable);
		tabbedPane.addTab("Query", querryPane);
		tabbedPane.addTab("Chart", scrollPaneChart);

		if (selectedIndex == -1 || newQuerryReceived) {
			tabbedPane.setSelectedIndex(0);
			newQuerryReceived = false;
		} else {
			tabbedPane.setSelectedIndex(selectedIndex);
		}
	}

	/**
	 * This method updates the ComboBox
	 * @param sqlCons list with SQLConnections which will be displayed into the ComboBox
	 */
	private static void updateComboBox(ArrayList<SQLConnection> sqlCons) {
		databaseComboBox.removeAll();
		databaseComboBox.removeAllItems();
		for (SQLConnection sqlCon : sqlCons) {
			databaseComboBox.addItem(sqlCon.getName());
		}
		generateTabbedPane();
		splitPane.setRightComponent(tabbedPane);
	}

	// GETTER&SETTER
	//////////////////////
	
	public static String getChosenDatabase() {
		return databaseComboBox.getSelectedItem().toString();
	}

	public static JTextArea getTextArea() {
		return textField;
	}

}
