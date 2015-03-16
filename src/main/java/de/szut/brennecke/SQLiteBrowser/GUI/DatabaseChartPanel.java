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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import de.szut.brennecke.SQLiteBrowser.DataHandling.ChartDrawer;
import de.szut.brennecke.SQLiteBrowser.SQL.SQLConnection;

public class DatabaseChartPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private GUI parentFrame;
	private JComboBox<String> databaseChartComboBox;
	private JComboBox<String> tableChartComboBox;
	private JComboBox<String> xValueChartComboBox;
	private JComboBox<String> yValueChartComboBox;
	private JButton showChartButton;
	private static JRadioButton lineChartButton;
	private static JRadioButton barChartButton;
	private static JRadioButton discChartButton;

	private static ButtonGroup chartViewSelection;

	private SQLConnection selectedConnection;
	private String selectedTableName;

	public DatabaseChartPanel(GUI gui) {
		parentFrame = gui;
		init();
	}

	private void init() {
		ArrayList<SQLConnection> sqlCons = parentFrame.getGUIController().getController().getSqlConnections();
		if (sqlCons != null) {
			for (SQLConnection sqlCon : sqlCons) {
				databaseChartComboBox.addItem(sqlCon.getName());
			}
		}
		generateChartPaneListeners();
		generateRadioButtons();
		this.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.2;
		c.weighty = 0.0;
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_END;

		JLabel database = new JLabel("Select Database!");
		c.gridx = 0;
		this.add(database, c);

		c.gridy = 1;
		this.add(databaseChartComboBox, c);

		JLabel table = new JLabel("Select Table!");
		c.gridy = 2;
		this.add(table, c);

		c.gridy = 3;
		this.add(tableChartComboBox, c);

		JLabel xValue = new JLabel("Select Row for xValue!");
		c.gridy = 4;
		this.add(xValue, c);

		c.gridy = 5;
		this.add(xValueChartComboBox, c);

		JLabel yValue = new JLabel("Select Row for yValue!");
		c.gridy = 6;
		this.add(yValue, c);

		c.gridy = 7;
		this.add(yValueChartComboBox, c);

		c.gridy = 8;
		this.add(lineChartButton, c);

		c.gridy = 9;
		this.add(barChartButton, c);

		c.gridy = 10;
		this.add(discChartButton, c);

		c.gridy = 11;
		this.add(showChartButton, c);
	}

	public void updateComboBoxes(ArrayList<SQLConnection> sqlCons) {
		databaseChartComboBox.removeAllItems();
		for (SQLConnection sqlCon : sqlCons) {
			databaseChartComboBox.addItem(sqlCon.getName());
		}
	}

	private void generateChartPaneListeners() {
		databaseChartComboBox = new JComboBox<String>();
		databaseChartComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (databaseChartComboBox.getItemCount() > 0) {
					String databaseName = databaseChartComboBox.getSelectedObjects()[0].toString();
					selectedConnection = parentFrame.getGUIController().getController().getSQLConnection(databaseName);
					tableChartComboBox.removeAllItems();
					ArrayList<String> tableNames = selectedConnection.getTableNames();
					for (String table : tableNames) {
						tableChartComboBox.addItem(table);
					}
				}
			}
		});

		tableChartComboBox = new JComboBox<String>();
		tableChartComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tableChartComboBox.getItemCount() > 0) {
					selectedTableName = tableChartComboBox.getSelectedObjects()[0].toString();

					xValueChartComboBox.removeAllItems();
					yValueChartComboBox.removeAllItems();
					try {
						DatabaseMetaData dmd = selectedConnection.getConnection().getMetaData();
						ResultSet result = dmd.getColumns(null, null, selectedTableName, null);
						while (result.next()) {
							String name = result.getString("COLUMN_NAME");
							xValueChartComboBox.addItem(name);
							yValueChartComboBox.addItem(name);
						}
					} catch (SQLException e1) {
					}
				}
			}
		});

		xValueChartComboBox = new JComboBox<String>();
		yValueChartComboBox = new JComboBox<String>();

		showChartButton = new JButton("Show Chart!");
		showChartButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int xValueCounter = xValueChartComboBox.getItemCount();
				int yValueCounter = yValueChartComboBox.getItemCount();
				if (xValueCounter > 0 && yValueCounter > 0) {
					String xValueColoum = xValueChartComboBox.getSelectedItem().toString();
					String yValueColoum = yValueChartComboBox.getSelectedItem().toString();
					String chartSelection = chartViewSelection.getSelection().getActionCommand();
					parentFrame.getGUIController().generateChart(selectedConnection.getName(), selectedTableName, xValueColoum, yValueColoum, Integer.valueOf(chartSelection));
				}
			}
		});
	}

	private static void generateRadioButtons() {
		lineChartButton = new JRadioButton("show as Line Chart");
		lineChartButton.setActionCommand(String.valueOf(ChartDrawer.LINE_CHART));
		lineChartButton.setSelected(true);

		barChartButton = new JRadioButton("show as Bar Chart");
		barChartButton.setActionCommand(String.valueOf(ChartDrawer.BAR_CHART));

		discChartButton = new JRadioButton("show as disc Chart");
		discChartButton.setActionCommand(String.valueOf(ChartDrawer.DISC_CHART));

		chartViewSelection = new ButtonGroup();

		chartViewSelection.add(lineChartButton);
		chartViewSelection.add(barChartButton);
		chartViewSelection.add(discChartButton);

	}

	public void updateChartPane(Chart2D chart) {
		Component[] components = this.getComponents();
		for (Component component : components) {
			if (component instanceof ChartPanel) {
				this.remove(component);
			}
		}

		ChartPanel chartPanel = new ChartPanel(chart);
		GridBagConstraints c = new GridBagConstraints();

		c.anchor = GridBagConstraints.PAGE_END;
		c.gridy = 0;
		c.gridx = 1;
		c.weightx = 0.8;
		c.weighty = 1.0;
		c.gridheight = 9;
		c.fill = GridBagConstraints.BOTH;
		this.add(chartPanel, c);
	}

}
