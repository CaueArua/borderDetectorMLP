package br.com.arua.utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import br.com.arua.mlp.exemple.XYSeriesDemo;

public class XYGrapy extends ApplicationFrame{

	public XYGrapy(String title, String xLabel, String yLable, PlotOrientation orientation, XYSeries ... series ) {
		super(title);
		
		XYSeriesCollection data = new XYSeriesCollection();
		
		for (XYSeries xySeries : series) {
			data.addSeries(xySeries);
		}		
		
		JFreeChart chart = ChartFactory.createXYLineChart(
	       title,
	       xLabel, 
	       yLable, 
	       data,
	       orientation,
	       true,
	       true,
	       false
	   );
		
		final ChartPanel chartPanel = new ChartPanel(chart);
		   chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		   setContentPane(chartPanel);
	}
	
	
	
}
