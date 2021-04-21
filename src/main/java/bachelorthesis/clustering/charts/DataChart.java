package bachelorthesis.clustering.charts;

import java.awt.Dimension;
import java.awt.RenderingHints;
import java.util.List;

import bachelorthesis.clustering.data.DataPoint;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.FastScatterPlot;
import org.jfree.ui.ApplicationFrame;

public class DataChart extends ApplicationFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private float[][] data;

	public DataChart(String title, List<DataPoint> dataPoints) {
		
		super(title);
		data = new float[2][dataPoints.size()];
		populateData(dataPoints);
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
	
	private void populateData(List<DataPoint> dataPoints) {
		
		int i = 0;
		for (DataPoint dataPoint : dataPoints) {
			data[0][i] = (float) dataPoint.getVector()[0];
			data[1][i] = (float) dataPoint.getVector()[1];
			++i;
		}
	}
}
