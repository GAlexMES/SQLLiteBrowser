package de.szut.brennecke.SQLiteBrowser.DataHandling;

import java.util.ArrayList;
import java.util.Random;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DSimple;


public class ChartDrawer {
	
	public static Chart2D generateChart(ArrayList<Double[]> values){
		Chart2D chart = new Chart2D();
		
		ITrace2D trace = new Trace2DSimple();

		chart.addTrace(trace);

		for (Double[] point:values) {
			System.out.println("X:"+ point[0] + "Y:" + point[1]);
			trace.addPoint(point[0],point[1]);
		}
		
		chart.addTrace(trace);
		
		return chart;
		
	}
	
}
