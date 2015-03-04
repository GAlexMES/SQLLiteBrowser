package de.szut.brennecke.SQLiteBrowser.DataHandling;

import java.util.Random;
import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DSimple;


public class ChartDrawer {
	
	public ChartDrawer(Controller controller){
		Chart2D chart = new Chart2D();
		
		ITrace2D trace = new Trace2DSimple();

		chart.addTrace(trace);

		Random random = new Random();
		for (int i = 100; i >= 0; i--) {
			trace.addPoint(i, random.nextDouble() * 10.0 + i);
		}
		
		chart.addTrace(trace);

		controller.updateChart(chart);
		
	}
	
}
