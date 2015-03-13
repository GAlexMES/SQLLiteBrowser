package de.szut.brennecke.SQLiteBrowser.GUI;

import info.monitorenter.gui.chart.Chart2D;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import de.szut.brennecke.SQLiteBrowser.DataHandling.ChartDrawer;
import de.szut.brennecke.SQLiteBrowser.DataHandling.ResultWorkup;

/**
 * This Class displays a .csv file in a JTable
 * @author Alexander Brennecke
 *
 */
@SuppressWarnings("serial")
public class CSVViewer extends JFrame {

	private Map<String, Integer> axis = new HashMap<>();
	private GridBagConstraints c;
	private JTable table;
	private ArrayList<String[]> values;
	private JLabel xAxisLabel;
	private JLabel yAxisLabel;
	private JButton showChart;
	private JRadioButton lineChartButton;
	private JRadioButton barChartButton;
	private ButtonGroup chartViewSelection;
	

	public CSVViewer() {
		xAxisLabel = new JLabel();
		yAxisLabel = new JLabel();
		showChart = new JButton("Draw Chart!");
		showChart.setEnabled(false);
		c = new GridBagConstraints();
	}
	
	public void displayValues(ArrayList<String[]> values){
		this.values = values;
		initGeneration();
	}
	
	private void generateRadioButtons(){
		lineChartButton = new JRadioButton("show as Line Chart");
		lineChartButton.setActionCommand(String.valueOf(ChartDrawer.LINE_CHART));
		lineChartButton.setSelected(true);
		
		barChartButton = new JRadioButton("show as Bar Chart");
		barChartButton.setActionCommand(String.valueOf(ChartDrawer.BAR_CHART));
		chartViewSelection = new ButtonGroup();
		
		chartViewSelection.add(lineChartButton);
		chartViewSelection.add(barChartButton);
		
	}

	private void initGeneration() {

		showChart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Double[]> values = ResultWorkup.getChartValues(table, axis);
				String actionCommand = chartViewSelection.getSelection().getActionCommand();
				Chart2D chart = ChartDrawer.generateChart(values, Integer.valueOf(actionCommand));
				CSVChartViewer csvChartViewer = new CSVChartViewer();
				csvChartViewer.display(chart);
			}
		});

		defineFrame();
		generateTable();
		generateRadioButtons();
		updateSelection();
	}

	private void defineFrame() {
		this.setSize(500, 500);
		this.setLayout(new GridBagLayout());
		this.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}

	private void generateTable() {
		DefaultTableModel dtm = new DefaultTableModel();

		String[] identifiers = new String[values.get(0).length];
		dtm.setColumnIdentifiers(identifiers);

		for (String[] row : values) {
			dtm.addRow(row);
		}

		table = new JTable(dtm);
		table.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				final int coloumIndex = table.convertColumnIndexToModel(table.columnAtPoint(event.getPoint()));
				final String coloumName = table.getColumnName(coloumIndex);
				JPopupMenu popup = new JPopupMenu();
				JMenuItem xAxis = new JMenuItem("Select " + coloumName + " as X-Axis");

				xAxis.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						axis.put("x", coloumIndex);
						updateSelection();
					}
				});
				popup.add(xAxis);

				JMenuItem yAxis = new JMenuItem("Select " + coloumName + " as Y-Axis");

				yAxis.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						axis.put("y", coloumIndex);
						updateSelection();
					}
				});
				popup.add(yAxis);
				popup.show(event.getComponent(), event.getX(), event.getY());
			}
		});
		JScrollPane scroll = new JScrollPane(table);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.8;
		c.weighty = 1.0;
		c.gridheight = 7;
		c.gridx = 0;
		c.gridy = 0;

		this.getContentPane().add(scroll, c);
	}

	private void updateSelection() {
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridheight = 1;
		c.weightx = 0.2;
		c.weighty = 0.0;
		c.gridx = 1;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_END;
		
		JLabel info = new JLabel("Your selected coloums:");
		this.add(info,c);
		
		c.gridy = 1;
		
		if (axis.get("x") != null) {
			xAxisLabel.setText("X-Axis: " + table.getColumnName(axis.get("x")));
		} else {
			xAxisLabel.setText("X-Axis: Not selected!");
		}
		this.add(xAxisLabel, c);

		c.gridy = 2;

		if (axis.get("y") != null) {
			yAxisLabel.setText("Y-Axis: " + table.getColumnName(axis.get("y")));
		} else {
			yAxisLabel.setText("Y-Axis: Not selected!");
		}

		this.add(yAxisLabel, c);

		c.gridy = 3;
		JLabel chartSelectionInfo = new JLabel("Type of chart:");
		
		this.add(chartSelectionInfo,c);
		
		c.gridy = 4;
		this.add(lineChartButton,c);
		
		c.gridy = 5;
		this.add(barChartButton,c);

		c.gridy = 6;
		if (axis.get("x") != null && axis.get("y") != null) {
			showChart.setEnabled(true);
		}

		this.add(showChart, c);

		this.validate();
		this.repaint();
	}
}
