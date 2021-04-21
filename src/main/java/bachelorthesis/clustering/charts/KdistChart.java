package bachelorthesis.clustering.charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.DomainOrder;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.io.File;
import java.io.IOException;

public class KdistChart extends ApplicationFrame {

    final JFreeChart chart;

    public KdistChart(String title, double[] kdist) {

        super(title);
        final XYSeriesCollection dataset = new XYSeriesCollection();
        final XYSeries series = new XYSeries("K dist");
        for (int i = 0; i < kdist.length; ++i) {
            series.add(i, kdist[i]);
        }
        dataset.addSeries(series);
        chart = ChartFactory.createScatterPlot(
                "K distance",
                "datapoint",
                "k distance",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        final ChartPanel chartPanel = new ChartPanel(chart);
        setContentPane(chartPanel);
    }

    public void showChart() {

        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }

    public void saveToJpegFile(File filename) {

        try {
            ChartUtilities.saveChartAsJPEG(filename, chart, 500, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
