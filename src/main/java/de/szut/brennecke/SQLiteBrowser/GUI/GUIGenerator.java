package de.szut.brennecke.SQLiteBrowser.GUI;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.views.ChartPanel;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
//	// INITIALISATION
//	// ///////////////
//	private final static String WRONG_LIMIT_INPUT = "Falsche Eingabe im Limit Feld. Diese wird ignoriert!";
//	private final static String WRONG_OFFSET_INPUT = "Falsche Eingabe im Offset Feld. Diese wird ignoriert!";
//
//	private static JTable resultTable;
//	private static JScrollPane scrollPaneTree;
//	private static JScrollPane scrollPaneTable;
//	private static JTextArea textField;
//	private static JTabbedPane tabbedPane;
//	private static Boolean newQuerryReceived = false;
//	private static JComboBox<String> databaseComboBox;
//	private static JPanel querryPane;
//	private static JSplitPane splitPane;
//	private static JPanel chartPane;
//	private static JComboBox<String> databaseChartComboBox;
//	private static JComboBox<String> tableChartComboBox;
//	private static JComboBox<String> xValueChartComboBox;
//	private static JComboBox<String> yValueChartComboBox;
//	private static SQLConnection selectedConnection;
//	private static JButton showChartButton;
//	private static JRadioButton lineChartButton;
//	private static JRadioButton barChartButton;
//	private static JRadioButton discChartButton;
//	private static ButtonGroup chartViewSelection;
//
//	private static String selectedTableName;
//
//	// IMPORTANT FUNCTIONS
//	// ////////////////////
//	/**
//	 * This method generates Tabs, Tables and Menus for the FIRST time only.
//	 * 
//	 * @param mainFrame
//	 * @return main Frame with menu
//	 */
//	public static GUI generateEmptyGUI(GUI mainFrame) {
//		// DEKLARATION
//		// ////////////
//		resultTable = new JTable();
//		textField = new JTextArea();
//		tabbedPane = new JTabbedPane();
//		querryPane = new JPanel();
//		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
//
//		generateQueryTab(mainFrame);
//		generateChartPane(mainFrame.getGUIController());
//		mainFrame.setJMenuBar(generateMenu(mainFrame));
//		scrollPaneTable = new JScrollPane(resultTable);
//
//		return mainFrame;
//	}
//
//	/**
//	 * This method is called when a ResultSet should be displayed. It updates
//	 * the TableModel and the main frame.
//	 * 
//	 * @param mainFrame
//	 * @param rs
//	 *            new ResultSet with new Values
//	 */
//	public static void showQuery(GUI mainFrame, ResultSet rs) {
//		newQuerryReceived = true;
//		updateTable(rs, mainFrame);
//		generateTabbedPane();
//		splitPane.setRightComponent(tabbedPane);
//		updateFrame(mainFrame);
//	}
//
//	/**
//	 * This method exchanges the TableModel with a new one
//	 * 
//	 * @param rs
//	 * @param mainFrame
//	 */
//	private static void updateTable(ResultSet rs, GUI mainFrame) {
//		DefaultTableModel resultData = null;
//		resultData = ResultWorkup.getTabularDatas(rs);
//		resultTable.setAutoCreateRowSorter(true);
//		resultTable.setModel(resultData);
//	}
//
//	/**
//	 * This method updates the tree, which shows the opened databases
//	 * 
//	 * @param mainFrame
//	 * @param sqlCons
//	 *            list of open SQLConnections
//	 */
//	public static void updateTree(GUI mainFrame, ArrayList<SQLConnection> sqlCons) {
//		JTree tree = generateTree(mainFrame, sqlCons);
//		scrollPaneTree = new JScrollPane(tree);
//		splitPane.setLeftComponent(scrollPaneTree);
//	}
//
//	/**
//	 * This method updates the hole main frame It removes and add the splitPane,
//	 * which includes all other elements
//	 * 
//	 * @param mainFrame
//	 */
//	private static void updateFrame(GUI mainFrame) {
//		mainFrame.getContentPane().removeAll();
//		int splitPaneLoc = splitPane.getDividerLocation();
//		mainFrame.getContentPane().add(splitPane);
//		splitPane.setDividerLocation(splitPaneLoc);
//		mainFrame.validate();
//		mainFrame.repaint();
//	}
//
//	/**
//	 * This method updates every component placed on the main frame. Excepted is
//	 * the data table!
//	 * 
//	 * @param mainFrame
//	 * @param sqlCons
//	 *            list with current opened databases
//	 */
//	public static void updateGUI(GUI mainFrame, ArrayList<SQLConnection> sqlCons) {
//		updateComboBox(sqlCons);
//		updateTree(mainFrame, sqlCons);
//		updateFrame(mainFrame);
//	}
//
//	/**
//	 * This method generates the menu
//	 * 
//	 * @param gui
//	 * @return a JMenuBar which can be added to the main frame
//	 */
//	private static JMenuBar generateMenu(GUI gui) {
//		JMenuBar menuBar = new JMenuBar();
//		MenuListener menuListener = new MenuListener(gui);
//
//		JMenu menuFile = new JMenu("File");
//
//		String[] menuFileItems = { "Open Database", "Show CSV in chart"};
//		for (String str : menuFileItems) {
//			JMenuItem menuItem = new JMenuItem(str);
//			menuItem.addActionListener(menuListener);
//			menuFile.add(menuItem);
//		}
//
//		menuBar.add(menuFile);
//
//		return menuBar;
//	}
//
//	/**
//	 * This method generates the Tree which shows the databases and tables
//	 * 
//	 * @param mainFrame
//	 * @param sqlConnections
//	 *            list with current opened databases
//	 * @return a JTree with databases as sub-Elements and tables as
//	 *         sub-sub-Elements
//	 */
//	private static JTree generateTree(GUI mainFrame, ArrayList<SQLConnection> sqlConnections) {
//		DefaultMutableTreeNode database = new DefaultMutableTreeNode("Datenbanken");
//		for (SQLConnection con : sqlConnections) {
//			DefaultMutableTreeNode connection = new DefaultMutableTreeNode(con.getName());
//			for (String str : con.getTableNames()) {
//				DefaultMutableTreeNode table = new DefaultMutableTreeNode(str);
//				connection.add(table);
//			}
//			database.add(connection);
//		}
//		JTree tree = new JTree(database);
//		TreeListener treeListener = new TreeListener(mainFrame);
//		tree.addTreeSelectionListener(treeListener);
//		tree.addMouseListener(treeListener);
//		return tree;
//	}
//
//	/**
//	 * This method generates the QueryTab, which will be added to the TabPane.
//	 * The QuerryTab includes: <li>ComboBox with currently opened databases</li>
//	 * <li>TextPane for the query input</li> <li>CheckBoxes and Input fields for
//	 * LIMIT and OFFSET Statements</li> <li>A execute button to execude the
//	 * query</li>
//	 * 
//	 * @param mainFrame
//	 */
//	public static void generateQueryTab(final GUI mainFrame) {
//		querryPane.setLayout(new GridBagLayout());
//		GridBagConstraints c = new GridBagConstraints();
//
//		// ////////
//		// COMBOBOX
//		// ////////
//		databaseComboBox = new JComboBox<>();
//		ArrayList<SQLConnection> sqlCons = mainFrame.getGUIController().getController().getSqlConnections();
//
//		for (SQLConnection sqlCon : sqlCons) {
//			databaseComboBox.addItem(sqlCon.getName());
//		}
//		c.fill = GridBagConstraints.HORIZONTAL;
//		c.weightx = 1.0;
//		c.weighty = 0.0;
//		c.gridx = 0;
//		c.gridy = 0;
//		c.anchor = GridBagConstraints.PAGE_START;
//
//		querryPane.add(databaseComboBox, c);
//
//		// ////////
//		// TEXTAREA
//		// ////////
//		c.fill = GridBagConstraints.BOTH;
//		c.weighty = 1.0;
//		c.gridy = 1;
//
//		JScrollPane textScrollPane = new JScrollPane(textField);
//
//		querryPane.add(textScrollPane, c);
//
//		// ////////
//		// LIMIT
//		// ////////
//
//		JPanel limitPane = new JPanel(new GridBagLayout());
//		GridBagConstraints limitPaneC = new GridBagConstraints();
//		limitPaneC.fill = GridBagConstraints.HORIZONTAL;
//
//		// LIMIT CHECK BOX
//		final JCheckBox limitCheck = new JCheckBox();
//		limitPaneC.gridy = 0;
//		limitPane.add(limitCheck, limitPaneC);
//
//		// LIMIT TEXT FIELD
//		JLabel limitText = new JLabel("Limit:");
//		limitPaneC.gridx = 1;
//		limitPane.add(limitText, limitPaneC);
//
//		// LIMIT START VALUE INPUT FIELD
//		final JTextField limitStartInputField = new JTextField();
//		limitStartInputField.setText("0");
//		limitPaneC.gridx = 2;
//		limitPaneC.ipadx = 20;
//		limitPane.add(limitStartInputField, limitPaneC);
//		limitPaneC.ipadx = 0;
//
//		// LIMIT CHECK BOX
//		final JCheckBox offsetCheck = new JCheckBox();
//		limitPaneC.gridx = 3;
//		limitPane.add(offsetCheck, limitPaneC);
//
//		// LIMIT START VALUE FIELD
//		JLabel limitNumberText = new JLabel("Offsett");
//		limitPaneC.gridx = 4;
//		limitPane.add(limitNumberText, limitPaneC);
//
//		// LIMIT START VALUE INPUT FIELD
//		final JTextField limitNumberInputField = new JTextField();
//		limitNumberInputField.setText("0");
//		limitPaneC.ipadx = 20;
//		limitPaneC.gridx = 5;
//		limitPane.add(limitNumberInputField, limitPaneC);
//		limitPaneC.ipadx = 0;
//
//		c.fill = GridBagConstraints.HORIZONTAL;
//		c.weighty = 0.0;
//		c.gridy = 2;
//		c.anchor = GridBagConstraints.PAGE_END;
//
//		querryPane.add(limitPane, c);
//
//		// ////////
//		// BUTTON
//		// ////////
//
//		JButton executeButton = new JButton("execute");
//		executeButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				int startValue = 0;
//				int numberOfValues = 0;
//
//				if (limitCheck.isSelected()) {
//					try {
//						startValue = Integer.valueOf(limitStartInputField.getText());
//					} catch (NumberFormatException nfe) {
//						GUIController.generateWrongQuerryInfoPane(WRONG_LIMIT_INPUT);
//					}
//				}
//				if (offsetCheck.isSelected()) {
//					try {
//						numberOfValues = Integer.valueOf(limitNumberInputField.getText());
//					} catch (NumberFormatException nfe) {
//						GUIController.generateWrongQuerryInfoPane(WRONG_OFFSET_INPUT);
//					}
//				}
//				int[] limitValues = { startValue, numberOfValues };
//				mainFrame.getGUIController().getController().sendGUIQuery(limitValues);
//			}
//		});
//
//		c.fill = GridBagConstraints.HORIZONTAL;
//		c.weighty = 0.0;
//		c.gridy = 3;
//		c.anchor = GridBagConstraints.PAGE_END;
//
//		querryPane.add(executeButton, c);
//	}
//
//	private static void generateChartPaneListeners(final GUIController controller) {
//		databaseChartComboBox = new JComboBox<String>();
//		databaseChartComboBox.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if (databaseChartComboBox.getItemCount() > 0) {
//					String databaseName = databaseChartComboBox.getSelectedObjects()[0].toString();
//					selectedConnection = controller.getController().getSQLConnection(databaseName);
//					tableChartComboBox.removeAllItems();
//					ArrayList<String> tableNames = selectedConnection.getTableNames();
//					for (String table : tableNames) {
//						tableChartComboBox.addItem(table);
//					}
//				}
//			}
//		});
//
//		tableChartComboBox = new JComboBox<String>();
//		tableChartComboBox.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if (tableChartComboBox.getItemCount() > 0) {
//					selectedTableName = tableChartComboBox.getSelectedObjects()[0].toString();
//
//					xValueChartComboBox.removeAllItems();
//					yValueChartComboBox.removeAllItems();
//					try {
//						DatabaseMetaData dmd = selectedConnection.getConnection().getMetaData();
//						ResultSet result = dmd.getColumns(null, null, selectedTableName, null);
//						while (result.next()) {
//							String name = result.getString("COLUMN_NAME");
//							xValueChartComboBox.addItem(name);
//							yValueChartComboBox.addItem(name);
//						}
//					} catch (SQLException e1) {
//					}
//				}
//			}
//		});
//
//		xValueChartComboBox = new JComboBox<String>();
//		yValueChartComboBox = new JComboBox<String>();
//
//		showChartButton = new JButton("Show Chart!");
//		showChartButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				int xValueCounter = xValueChartComboBox.getItemCount();
//				int yValueCounter = yValueChartComboBox.getItemCount();
//				if (xValueCounter > 0 && yValueCounter > 0) {
//					String xValueColoum = xValueChartComboBox.getSelectedItem().toString();
//					String yValueColoum = yValueChartComboBox.getSelectedItem().toString();
//					
//					String chartSelection = chartViewSelection.getSelection().getActionCommand();
//					controller.generateChart(selectedConnection.getName(), selectedTableName, xValueColoum, yValueColoum, Integer.valueOf(chartSelection));
//				}
//			}
//		});
//	}
//
//	private static void generateChartPane(GUIController controller) {
//		ArrayList<SQLConnection> sqlCons = controller.getController().getSqlConnections();
//		for (SQLConnection sqlCon : sqlCons) {
//			databaseChartComboBox.addItem(sqlCon.getName());
//		}
//		generateChartPaneListeners(controller);
//		generateRadioButtons();
//		chartPane = new JPanel();
//		chartPane.setLayout(new GridBagLayout());
//
//		GridBagConstraints c = new GridBagConstraints();
//
//		c.fill = GridBagConstraints.HORIZONTAL;
//		c.weightx = 0.2;
//		c.weighty = 0.0;
//		c.gridx = 0;
//		c.gridy = 0;
//		c.anchor = GridBagConstraints.PAGE_END;
//
//		JLabel database = new JLabel("Select Database!");
//		c.gridx = 0;
//		chartPane.add(database, c);
//
//		c.gridy = 1;
//		chartPane.add(databaseChartComboBox, c);
//
//		JLabel table = new JLabel("Select Table!");
//		c.gridy = 2;
//		chartPane.add(table, c);
//
//		c.gridy = 3;
//		chartPane.add(tableChartComboBox, c);
//
//		JLabel xValue = new JLabel("Select Row for xValue!");
//		c.gridy = 4;
//		chartPane.add(xValue, c);
//
//		c.gridy = 5;
//		chartPane.add(xValueChartComboBox, c);
//
//		JLabel yValue = new JLabel("Select Row for yValue!");
//		c.gridy = 6;
//		chartPane.add(yValue, c);
//
//		c.gridy = 7;
//		chartPane.add(yValueChartComboBox, c);
//		
//		c.gridy = 8;
//		chartPane.add(lineChartButton, c);
//		
//		c.gridy = 9;
//		chartPane.add(barChartButton, c);
//		
//		c.gridy = 10;
//		chartPane.add(discChartButton, c);
//		
//		c.gridy = 11;
//		chartPane.add(showChartButton, c);
//	}
//
//	
//	private static void generateRadioButtons() {
//		lineChartButton = new JRadioButton("show as Line Chart");
//		lineChartButton.setActionCommand(String.valueOf(ChartDrawer.LINE_CHART));
//		lineChartButton.setSelected(true);
//
//		barChartButton = new JRadioButton("show as Bar Chart");
//		barChartButton.setActionCommand(String.valueOf(ChartDrawer.BAR_CHART));
//
//		discChartButton = new JRadioButton("show as disc Chart");
//		discChartButton.setActionCommand(String.valueOf(ChartDrawer.DISC_CHART));
//
//		chartViewSelection = new ButtonGroup();
//
//		chartViewSelection.add(lineChartButton);
//		chartViewSelection.add(barChartButton);
//		chartViewSelection.add(discChartButton);
//
//	}
//	
//	
//	public static void updateChartPane(Chart2D chart) {
//		Component[] components = chartPane.getComponents();
//		for(Component component:components){
//			if(component instanceof ChartPanel){
//				chartPane.remove(component);
//			}
//		}
//		
//		ChartPanel chartPanel = new ChartPanel(chart);
//		GridBagConstraints c = new GridBagConstraints();
//		
//		c.anchor = GridBagConstraints.PAGE_END;
//		c.gridy = 0;
//		c.gridx = 1;
//		c.weightx = 0.8;
//		c.weighty = 1.0;
//		c.gridheight = 9;
//		c.fill = GridBagConstraints.BOTH;
//		chartPane.add(chartPanel, c);
//
//		generateTabbedPane();
//	}
//
//	/**
//	 * This method generates the TabbedPane, which handles the TablePane and
//	 * QueryPane.
//	 */
//	public static void generateTabbedPane() {
//		// ////////
//		// TABBEDPANE
//		// ////////
//		int selectedIndex = tabbedPane.getSelectedIndex();
//
//		tabbedPane.removeAll();
//		tabbedPane.addTab("Tabelle", scrollPaneTable);
//		tabbedPane.addTab("Query", querryPane);
//		tabbedPane.addTab("Chart", chartPane);
//
//		if (selectedIndex == -1 || newQuerryReceived) {
//			tabbedPane.setSelectedIndex(0);
//			newQuerryReceived = false;
//		} else {
//			tabbedPane.setSelectedIndex(selectedIndex);
//		}
//	}
//
//	/**
//	 * This method updates the ComboBox
//	 * 
//	 * @param sqlCons
//	 *            list with SQLConnections which will be displayed into the
//	 *            ComboBox
//	 */
//	private static void updateComboBox(ArrayList<SQLConnection> sqlCons) {
//		databaseComboBox.removeAll();
//		databaseChartComboBox.removeAll();
//		databaseComboBox.removeAllItems();
//		databaseChartComboBox.removeAllItems();
//		for (SQLConnection sqlCon : sqlCons) {
//			databaseComboBox.addItem(sqlCon.getName());
//			databaseChartComboBox.addItem(sqlCon.getName());
//		}
//		generateTabbedPane();
//		splitPane.setRightComponent(tabbedPane);
//	}
//
//	// GETTER&SETTER
//	// ////////////////////
//
//	public static String getChosenDatabase() {
//		return databaseComboBox.getSelectedItem().toString();
//	}
//
//	public static JTextArea getTextArea() {
//		return textField;
//	}
}
