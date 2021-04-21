package bachelorthesis.clustering;

import bachelorthesis.clustering.charts.DataChartAlternateDesign;
import bachelorthesis.clustering.data.*;
import bachelorthesis.clustering.grid.Grid;
import org.jfree.ui.RefineryUtilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final String DIR_NAME = "results/shapes/";

    public static void main( String[] args ) {

        //createShape("halfmoon");

        //clusterData("halfmoon-simple", "Halfmoon-simple", false);
        clusterData("halfmoon", "Halfmoon", false);
        //clusterData("double-moon", "Double-moon", false);
        //clusterData("double-moon-close", "Double-moon-close", false);
        //clusterData("circle", "Circle", true);
    }

    private static void clusterData(String shapeName, String fileName, boolean debug) {

        ArbitraryShape shape = new ArbitraryShape(2);
        List<Segment> segments = new ArrayList<>();
        selectShape(shapeName, segments);
        shape.setSegments(segments);
        ShapeGenerator generator = new ShapeGenerator(shape);
        List<DataPoint> dataPoints = generator.generateShape(2000);
        DataPartitioner partitioner = new DataPartitioner(dataPoints, 100.0, 100.0);
        int k = partitioner.findOptimalPartition_old(DIR_NAME + fileName + "Results_old.txt", DIR_NAME + fileName + "Areas_old.txt");
        if (shapeName.equals("double-moon-close")) {
            k += 8;
        }
        Grid grid = new Grid(k, dataPoints, 100.0, 100.0);
        grid.setupCells();
        grid.setupClusters();
        grid.performClustering(debug);

        DataChartAlternateDesign chart = new DataChartAlternateDesign(shapeName, grid);
        chart.showChart();
        chart.saveToJpegFile(new File(DIR_NAME + fileName + ".jpg"));
    }

    private static void createShape(String shapeName) {

        ArbitraryShape shape = new ArbitraryShape(2);
        List<Segment> segments = new ArrayList<>();
        selectShape(shapeName, segments);
        shape.setSegments(segments);
        ShapeGenerator generator = new ShapeGenerator(shape);
        List<DataPoint> dataPoints = generator.generateShape(1000);
        Grid grid = new Grid(10, dataPoints, 100, 100);
        grid.setupCells();
        grid.setupClusters();

        DataChartAlternateDesign chart = new DataChartAlternateDesign(shapeName, grid);
        chart.showChart();
    }

    private static void selectShape(String shapeName, List<Segment> segments) {

        if (shapeName.equals("halfmoon-simple")) {
            createHalfmoonSimple(segments);
        } else if (shapeName.equals("circle")) {
            createCircle(segments);
        } else if (shapeName.equals("halfmoon")) {
            createHalfMoon(segments);
        } else if (shapeName.equals("double-moon")) {
            createDoubleMoon(segments);
        } else if (shapeName.equals("double-moon-close")) {
            createDoubleMoonClose(segments);
        }
    }

    private static void createDoubleMoon(List<Segment> segments) {

        double[] center = new double[2];
        center[0] = 40.0;
        center[1] = 50.0;

        double radius = 10.0;
        double x = 0.0;
        double y = radius;
        while (y > -radius) {

            x = Math.sqrt(Math.pow(radius, 2.0) - Math.pow(y, 2.0));

            segments.add(new Segment(getXandY(center, -x, y), 0.2));
            segments.add(new Segment(getXandY(center, -x, -y), 0.2));

            y -= 0.05;
        }

        center[0] = 80.0;
        center[1] = 30.0;

        radius = 10.0;
        x = 0.0;
        y = radius;
        while (y > -radius) {

            x = Math.sqrt(Math.pow(radius, 2.0) - Math.pow(y, 2.0));

            segments.add(new Segment(getXandY(center, x, y), 0.2));
            segments.add(new Segment(getXandY(center, x, -y), 0.2));

            y -= 0.05;
        }
    }

    private static void createDoubleMoonClose(List<Segment> segments) {

        double[] center = new double[2];
        center[0] = 40.0;
        center[1] = 50.0;

        double radius = 10.0;
        double x = 0.0;
        double y = radius;
        while (y > -radius) {

            x = Math.sqrt(Math.pow(radius, 2.0) - Math.pow(y, 2.0));

            segments.add(new Segment(getXandY(center, -x, y), 0.2));
            segments.add(new Segment(getXandY(center, -x, -y), 0.2));

            y -= 0.05;
        }

        center[0] = 50.0;
        center[1] = 35.0;

        radius = 10.0;
        x = 0.0;
        y = radius;
        while (y > -radius) {

            x = Math.sqrt(Math.pow(radius, 2.0) - Math.pow(y, 2.0));

            segments.add(new Segment(getXandY(center, x, y), 0.2));
            segments.add(new Segment(getXandY(center, x, -y), 0.2));

            y -= 0.05;
        }
    }

    private static void createHalfMoon(List<Segment> segments) {

        double[] center = new double[2];
        center[0] = 50.0;
        center[1] = 50.0;

        double radius = 10.0;
        double x = 0.0;
        double y = radius;
        while (y > -radius) {

            x = Math.sqrt(Math.pow(radius, 2.0) - Math.pow(y, 2.0));

            segments.add(new Segment(getXandY(center, -x, y), 0.1));
            segments.add(new Segment(getXandY(center, -x, -y), 0.1));

            y -= 0.05;
        }
    }

    private static void createCircle(List<Segment> segments) {

        double[] center = new double[2];
        center[0] = 50.0;
        center[1] = 50.0;

        double radius = 10.0;
        double x = 0.0;
        double y = 0.0;
        double phi = 0.0;
        while (phi < 90) {
        //while (x < radius) {

            //y = Math.sqrt( Math.pow(radius, 2.0) - Math.pow(x, 2.0) );
            y = Math.sin(phi) * radius;
            x = Math.cos(phi) * radius;

            segments.add(new Segment(getXandY(center, x, y), 0.1));
            segments.add(new Segment(getXandY(center, -x, y), 0.1));
            segments.add(new Segment(getXandY(center, x, -y), 0.1));
            segments.add(new Segment(getXandY(center, -x, -y), 0.1));

            //x += 0.1;
            phi += 0.9;
        }
    }

    private static double[] getXandY(double[] center, double x, double y) {

        double[] vector = new double[2];

        vector[0] = center[0] + x;
        vector[1] = center[1] + y;

        return vector;
    }

    private static void createHalfmoonSimple(List<Segment> segments) {

        double[] leftestPoint = new double[2];
        double x = 40.0;
        double y = 50.0;
        leftestPoint[0] = x;
        leftestPoint[1] = y;
        List<double[]> points = new ArrayList<>();
        points.add(leftestPoint);

        double bow = 1.000001;
        for (int i = 0; i < 20; ++i) {

            double[] point = new double[2];
            point[0] = x += (0.03 * bow);
            point[1] = y += 0.5;
            points.add(point);
            bow *= bow;
        }
        x = 40.0;
        y = 50.0;
        bow = 1.000001;
        for (int i = 0; i < 20; ++i) {

            double[] point = new double[2];
            point[0] = x += (0.03 * bow);
            point[1] = y -= 0.4;
            points.add(point);
            bow *= bow;
        }
        for (double[] point : points) {

            segments.add(new Segment(point, 0.1));
        }
    }
}
