package de.szut.brennecke.SQLiteBrowser.DataHandling;

import java.util.ArrayList;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DSimple;
import info.monitorenter.gui.chart.traces.painters.TracePainterLine;
import info.monitorenter.gui.chart.traces.painters.TracePainterVerticalBar;




public class ChartDrawer {
	
	public final static int LINE_CHART = 0;
	public final static int BAR_CHART = 1;
	
	
	public static Chart2D generateChart(ArrayList<Double[]> values, int chartType){
		
		Chart2D chart = new Chart2D();
		ITrace2D trace = new Trace2DSimple();
		
		switch(chartType){
		case BAR_CHART:
			trace.setTracePainter(new TracePainterVerticalBar(4, chart));
			break;
		case LINE_CHART:
		default:
			trace.setTracePainter(new TracePainterLine() );
			break;
		}

		chart.addTrace(trace);

		for (Double[] point:values) {
			trace.addPoint(point[0],point[1]);
		}
		
		chart.addTrace(trace);
		
		return chart;
	}
	
	
}
