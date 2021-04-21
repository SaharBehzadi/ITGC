package bachelorthesis.clustering.charts;

import bachelorthesis.clustering.clustering.ClusterDBSCAN;
import bachelorthesis.clustering.clustering.ClusterKMeans;
import bachelorthesis.clustering.data.DataPoint;
import bachelorthesis.clustering.grid.Cell;
import bachelorthesis.clustering.grid.Cluster;
import bachelorthesis.clustering.grid.Grid;
import bachelorthesis.clustering.grid.HigherDimGrid;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class DataChartAlternateDesign extends ApplicationFrame {

    final JFreeChart chart;

    public DataChartAlternateDesign(String title, Grid grid) {

        super(title);
        final XYSeriesCollection collection = new XYSeriesCollection();
        int clusterIndex = 0;
        //System.out.println("Clusters: " + grid.getClusters().size());
        for (Cluster cluster : grid.getClusters()) {

            final XYSeries serie = new XYSeries("Cluster " + ++clusterIndex);
            for (Cell cell : cluster.getClusterCells()) {
                for (DataPoint dataPoint : cell.getDataPoints()) {

                    serie.add(dataPoint.getVector()[0], dataPoint.getVector()[1]);
                }
            }
            collection.addSeries(serie);
        }
        chart = ChartFactory.createScatterPlot(title, "x", "y", collection, PlotOrientation.VERTICAL, true, false, false);
        chart.setBackgroundPaint(Color.white);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);

        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setLowerBound(grid.getXDomain()[0]);
        domain.setUpperBound(grid.getXDomain()[1]);
        double positionX = grid.getXDomain()[0];
        double sizeX = (grid.getXDomain()[1] - grid.getXDomain()[0]) / grid.getK();
        for (int i = 0; i <= grid.getK(); ++i) {
            ValueMarker marker = new ValueMarker(positionX);
            marker.setPaint(Color.black);
            plot.addDomainMarker(marker);
            positionX += sizeX;
        }

        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setLowerBound(grid.getYDomain()[0]);
        range.setUpperBound(grid.getYDomain()[1]);

        double positionY = grid.getYDomain()[0];
        double sizeY = (grid.getYDomain()[1] - grid.getYDomain()[0]) / grid.getK();
        for (int i = 0; i <= grid.getK(); ++i) {
            ValueMarker marker = new ValueMarker(positionY);
            marker.setPaint(Color.black);
            plot.addRangeMarker(marker);
            positionY += sizeY;
        }

        //System.out.println(grid.getXDomain()[0] + " : " + grid.getXDomain()[1]);
        //System.out.println(grid.getYDomain()[0] + " : " + grid.getYDomain()[1]);

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 600));
        setContentPane(chartPanel);
    }

    public DataChartAlternateDesign(String title, HigherDimGrid grid, int groundTruthNumber) {

        super(title);
        final XYSeriesCollection collection = new XYSeriesCollection();

        for (int i = 1; i <= groundTruthNumber; ++i) {

            final XYSeries serie = new XYSeries("Cluster " + i);
            for (DataPoint dataPoint : grid.getDataPoints()) {
                double id = Double.parseDouble(dataPoint.getGroundTruth());
                if (id == i) {

                    serie.add(dataPoint.getVector()[0], dataPoint.getVector()[1]);
                }
            }
            collection.addSeries(serie);
        }

        chart = ChartFactory.createScatterPlot(title, "x", "y", collection, PlotOrientation.VERTICAL, true, false, false);
        chart.setBackgroundPaint(Color.white);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);

        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setLowerBound(grid.getDomain()[0][0]);
        domain.setUpperBound(grid.getDomain()[0][1]);
        double positionX = grid.getDomain()[0][0];
        double sizeX = (grid.getDomain()[0][1] - grid.getDomain()[0][0]) / grid.getK();
        for (int i = 0; i <= grid.getK(); ++i) {
            ValueMarker marker = new ValueMarker(positionX);
            marker.setPaint(Color.black);
            plot.addDomainMarker(marker);
            positionX += sizeX;
        }

        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setLowerBound(grid.getDomain()[1][0]);
        range.setUpperBound(grid.getDomain()[1][1]);

        double positionY = grid.getDomain()[1][0];
        double sizeY = (grid.getDomain()[1][1] - grid.getDomain()[1][0]) / grid.getK();
        for (int i = 0; i <= grid.getK(); ++i) {
            ValueMarker marker = new ValueMarker(positionY);
            marker.setPaint(Color.black);
            plot.addRangeMarker(marker);
            positionY += sizeY;
        }

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 600));
        setContentPane(chartPanel);
        setDefaultCloseOperation(ApplicationFrame.DISPOSE_ON_CLOSE);
    }

    public DataChartAlternateDesign(String title, HigherDimGrid grid) {

        super(title);
        final XYSeriesCollection collection = new XYSeriesCollection();

        int clusterIndex = 0;
        for (Cluster cluster : grid.getClusters()) {

            final XYSeries serie = new XYSeries("Cluster " + ++clusterIndex);
            for (Cell cell : cluster.getClusterCells()) {
                for (DataPoint dataPoint : cell.getDataPoints()) {

                    serie.add(dataPoint.getVector()[0], dataPoint.getVector()[1]);
                }
            }
            collection.addSeries(serie);
        }

        chart = ChartFactory.createScatterPlot(title, "x", "y", collection, PlotOrientation.VERTICAL, true, false, false);
        chart.setBackgroundPaint(Color.white);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);

        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setLowerBound(grid.getDomain()[0][0]);
        domain.setUpperBound(grid.getDomain()[0][1]);
        double positionX = grid.getDomain()[0][0];
        double sizeX = (grid.getDomain()[0][1] - grid.getDomain()[0][0]) / grid.getK();
        for (int i = 0; i <= grid.getK(); ++i) {
            ValueMarker marker = new ValueMarker(positionX);
            marker.setPaint(Color.black);
            plot.addDomainMarker(marker);
            positionX += sizeX;
        }

        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setLowerBound(grid.getDomain()[1][0]);
        range.setUpperBound(grid.getDomain()[1][1]);

        double positionY = grid.getDomain()[1][0];
        double sizeY = (grid.getDomain()[1][1] - grid.getDomain()[1][0]) / grid.getK();
        for (int i = 0; i <= grid.getK(); ++i) {
            ValueMarker marker = new ValueMarker(positionY);
            marker.setPaint(Color.black);
            plot.addRangeMarker(marker);
            positionY += sizeY;
        }

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 600));
        setContentPane(chartPanel);
    }

    public DataChartAlternateDesign(String title, List<ClusterDBSCAN> clusters, HigherDimGrid grid) {

        super(title);
        final XYSeriesCollection collection = new XYSeriesCollection();

        int clusterIndex = 0;
        for (ClusterDBSCAN cluster : clusters) {

            final XYSeries serie = new XYSeries("Cluster " + ++clusterIndex);
            for (DataPoint dataPoint : cluster.getDataPoints()) {

                serie.add(dataPoint.getVector()[0], dataPoint.getVector()[1]);
            }
            collection.addSeries(serie);
        }

        chart = ChartFactory.createScatterPlot(title, "x", "y", collection, PlotOrientation.VERTICAL, true, false, false);
        chart.setBackgroundPaint(Color.white);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);

        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setLowerBound(grid.getDomain()[0][0]);
        domain.setUpperBound(grid.getDomain()[0][1]);
        double positionX = grid.getDomain()[0][0];
        double sizeX = (grid.getDomain()[0][1] - grid.getDomain()[0][0]) / grid.getK();
        for (int i = 0; i <= grid.getK(); ++i) {
            ValueMarker marker = new ValueMarker(positionX);
            marker.setPaint(Color.black);
            plot.addDomainMarker(marker);
            positionX += sizeX;
        }

        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setLowerBound(grid.getDomain()[1][0]);
        range.setUpperBound(grid.getDomain()[1][1]);

        double positionY = grid.getDomain()[1][0];
        double sizeY = (grid.getDomain()[1][1] - grid.getDomain()[1][0]) / grid.getK();
        for (int i = 0; i <= grid.getK(); ++i) {
            ValueMarker marker = new ValueMarker(positionY);
            marker.setPaint(Color.black);
            plot.addRangeMarker(marker);
            positionY += sizeY;
        }

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 600));
        setContentPane(chartPanel);
    }

    public DataChartAlternateDesign(String title, ClusterDBSCAN[] clusters, HigherDimGrid grid) {

        super(title);
        final XYSeriesCollection collection = new XYSeriesCollection();

        final XYSeries[] series = new XYSeries[clusters.length];
        for (int i = 0; i < series.length; ++i) {

            series[i] = new XYSeries("Cluster " + (i + 1));
            for (DataPoint dataPoint : clusters[i].getDataPoints()) {

                series[i].add(dataPoint.getVector()[0], dataPoint.getVector()[1]);
            }
            collection.addSeries(series[i]);
        }

        chart = ChartFactory.createScatterPlot(title, "x", "y", collection, PlotOrientation.VERTICAL, true, false, false);
        chart.setBackgroundPaint(Color.white);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);

        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setLowerBound(grid.getDomain()[0][0]);
        domain.setUpperBound(grid.getDomain()[0][1]);
        double positionX = grid.getDomain()[0][0];
        double sizeX = (grid.getDomain()[0][1] - grid.getDomain()[0][0]) / grid.getK();
        for (int i = 0; i <= grid.getK(); ++i) {
            ValueMarker marker = new ValueMarker(positionX);
            marker.setPaint(Color.black);
            plot.addDomainMarker(marker);
            positionX += sizeX;
        }

        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setLowerBound(grid.getDomain()[1][0]);
        range.setUpperBound(grid.getDomain()[1][1]);

        double positionY = grid.getDomain()[1][0];
        double sizeY = (grid.getDomain()[1][1] - grid.getDomain()[1][0]) / grid.getK();
        for (int i = 0; i <= grid.getK(); ++i) {
            ValueMarker marker = new ValueMarker(positionY);
            marker.setPaint(Color.black);
            plot.addRangeMarker(marker);
            positionY += sizeY;
        }

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 600));
        setContentPane(chartPanel);
    }

    public JFreeChart getChart() {
        return chart;
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
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
