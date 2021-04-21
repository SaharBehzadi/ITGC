package charts;

import java.awt.Dimension;
import java.awt.RenderingHints;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.FastScatterPlot;
import org.jfree.ui.ApplicationFrame;

// thanks to: http://www.java2s.com/Code/Java/Chart/JFreeChartFastScatterPlotDemo.htm
public class ScatterPlot extends ApplicationFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int COUNT = 5000;
	
	private float[][] data = new float[2][COUNT];
	
	public ScatterPlot(final String title) {
		
		super(title);
		populateData();
		final NumberAxis domainAxis = new NumberAxis("X");
		domainAxis.setAutoRangeIncludesZero(false);
		final NumberAxis rangeAxis = new NumberAxis("Y");
		rangeAxis.setAutoRangeIncludesZero(false);
		final FastScatterPlot plot = new FastScatterPlot(data, domainAxis, rangeAxis);
		final JFreeChart chart = new JFreeChart("Scatter Plot", plot);
		
		chart.getRenderingHints().put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		final ChartPanel panel = new ChartPanel(chart, true);
		panel.setPreferredSize(new Dimension(500, 270));
		panel.setMinimumDrawHeight(10);
		panel.setMaximumDrawHeight(2000);
		panel.setMinimumDrawWidth(20);
		panel.setMaximumDrawWidth(2000);
		
		setContentPane(panel);
	}
	
	private void populateData() {
		
		for (int i = 0; i < data[0].length; ++i) {
			final float x = (float) i + 1000;
			data[0][i] = x;
			data[1][i] = 1000 + (float) Math.random() * COUNT;
		}
	}
}