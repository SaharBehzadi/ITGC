package bachelorthesis.clustering;

import bachelorthesis.clustering.data.*;
import charts.ScatterPlot;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import bachelorthesis.clustering.charts.DataChart;

public class JFreeChartTest {

	public static void main(String[] args) {
		
		menueText();
		
		int input = 0;
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in));
		
		try {
			input = Integer.parseInt(reader.readLine());
		} catch (NumberFormatException e) {
			System.out.println("Wrong Numberformat");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException occured");
			e.printStackTrace();
		}
		
		switch (input) {
			case 1:
				printScatterPlot();
				break;
			case 2:
				printHistogram();
				break;
			case 3:
				printDataPlot();
				break;
			case 4:
				printScatterPlotArbitraryShape();
		}
	}

	private static void menueText() {

		System.out.println("Choose a test");
		System.out.println("1 ... Scatterplot");
		System.out.println("2 ... Histogram");
		System.out.println("3 ... generate Test data and print them");
		System.out.println("4 ... create arbitrary shape");
		System.out.println("Press Key and Enter");
	}
	
	// thanks to: https://www.roseindia.net/tutorial/java/jfreechart/createhistogram.html
	private static void printHistogram() {
		
		double[] value = new double[1000];
		Random generator = new Random();
		for (int i = 0; i < 1000; ++i) {
			value[i] = generator.nextGaussian() + 25;
		}
		HistogramDataset dataset = new HistogramDataset();
		dataset.setType(HistogramType.FREQUENCY);
		dataset.addSeries("Histogram", value, 10);
		String plotTitle = "Histogram"; 
	    String xaxis = "number";
	    String yaxis = "value"; 
	    PlotOrientation orientation = PlotOrientation.VERTICAL; 
	    boolean show = false;
	    boolean toolTips = false;
	    boolean urls = false; 
	    JFreeChart chart = ChartFactory.createHistogram( plotTitle, xaxis, yaxis, 
	                dataset, orientation, show, toolTips, urls);
	    final ChartPanel panel = new ChartPanel(chart);
	    panel.setPreferredSize(new Dimension(640, 480));
	    final ApplicationFrame frame = new ApplicationFrame("Histogram");
	    frame.setContentPane(panel);
	    frame.pack();
	    frame.setVisible(true);
	}
	
	private static void printDataPlot() {
		
		double[] clusterCenter1 = new double[2];
		clusterCenter1[0] = 100;
		clusterCenter1[1] = 50;
		double[] clusterCenter2 = new double[2];
		clusterCenter2[0] = 300;
		clusterCenter2[1] = 550;
		double[] clusterCenter3 = new double[2];
		clusterCenter3[0] = 150;
		clusterCenter3[1] = 300;
		ArrayList<DataPoint> points = new ArrayList<>();
		
		DataGenerator generator = new DataGenerator(2);
		for (int i = 0; i < 10000; ++i) {
			points.add(generator.generateDataPoint(clusterCenter1, 1));
			points.add(generator.generateDataPoint(clusterCenter2, 5));
			points.add(generator.generateDataPoint(clusterCenter3, 10));
		}
		
		final DataChart chart = new DataChart("Test Data", points);
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
	}

	private static void printScatterPlotArbitraryShape() {

		List<DataPoint> points = new ArrayList<>();

		double[] clusterCenter1 = new double[2];
		clusterCenter1[0] = 1.5;
		clusterCenter1[1] = 1.8;
		double[] clusterCenter2 = new double[2];
		clusterCenter2[0] = 1.25;
		clusterCenter2[1] = 1.8;
		double[] clusterCenter3 = new double[2];
		clusterCenter3[0] = 1;
		clusterCenter3[1] = 1.8;
		double[] clusterCenter4 = new double[2];
		clusterCenter4[0] = 1;
		clusterCenter4[1] = 2;
		double[] clusterCenter5 = new double[2];
		clusterCenter5[0] = 1;
		clusterCenter5[1] = 2.2;
		double[] clusterCenter6 = new double[2];
		clusterCenter6[0] = 1.2;
		clusterCenter6[1] = 2.25;
		double[] clusterCenter7 = new double[2];
		clusterCenter7[0] = 1.4;
		clusterCenter7[1] = 2.3;

		ArbitraryShape shape = new ArbitraryShape(2);

		double deviation = 0.075;
		shape.addSegment(new Segment(clusterCenter1, deviation));
		shape.addSegment(new Segment(clusterCenter2, deviation));
		shape.addSegment(new Segment(clusterCenter3, deviation));
		shape.addSegment(new Segment(clusterCenter4, deviation));
		shape.addSegment(new Segment(clusterCenter5, deviation));
		shape.addSegment(new Segment(clusterCenter6, deviation));
		shape.addSegment(new Segment(clusterCenter7, deviation));

		ShapeGenerator generator = new ShapeGenerator(shape);
		points.addAll(generator.generateShape(15000));

		final DataChart chart = new DataChart("Test Data", points);
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
	}
	
	private static void printScatterPlot() {
		
		final ScatterPlot plot = new ScatterPlot("Test Plot");
		plot.pack();
		RefineryUtilities.centerFrameOnScreen(plot);
		plot.setVisible(true);
	}
}