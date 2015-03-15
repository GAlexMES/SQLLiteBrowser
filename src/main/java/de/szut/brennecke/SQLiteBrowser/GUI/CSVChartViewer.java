package de.szut.brennecke.SQLiteBrowser.GUI;

import java.awt.Dimension;
import java.awt.Toolkit;
import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.views.ChartPanel;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class CSVChartViewer extends JFrame {
	public CSVChartViewer() {
		this.setVisible(true);
		this.setSize(500, 500);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}

	public void display(Chart2D chart) {
		ChartPanel chartPanel = new ChartPanel(chart);
		this.add(chartPanel);
	}
}