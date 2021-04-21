package bachelorthesis.clustering;

import bachelorthesis.clustering.charts.DataChartAlternateDesign;
import bachelorthesis.clustering.clustering.CLIQUE;
import bachelorthesis.clustering.clustering.DBSCANer;
import bachelorthesis.clustering.clustering.Kmean;
import bachelorthesis.clustering.data.*;
import bachelorthesis.clustering.fileIO.FileIO;
import bachelorthesis.clustering.grid.Cluster;
import bachelorthesis.clustering.grid.HigherDimGrid;
import org.jfree.ui.ApplicationFrame;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ChartTest {

    private static DataGenerator generator;
    private static ShapeGenerator shapeGenerator;
    private static int dim = 5;
    private static int factor = 3;
    private static int groundTruthNumber;

    public static void main(String[] args) throws IOException {

        double[] mean = new double[dim];
        initMean(mean);
        List<DataPoint> dataPoints = new ArrayList<>();
        generator = new DataGenerator(dim);

        //halfmoonWithGaussian(dataPoints, mean);
        //yinYang(dataPoints, mean);
        yinYang2(dataPoints, mean);
        //doubleMoon(dataPoints, mean);
        //circlesInCircles(dataPoints, mean);
        //gaussianDifferentDensities(dataPoints, mean);
        //gaussianDifferentDensitiesThreeClusters(dataPoints, mean);
        //halfmoonAndGaussians(dataPoints, mean);

        DataChartAlternateDesign chartClustered;
        DataPartitioner partitioner = new DataPartitioner(dataPoints);
        int k = partitioner.findOptimalPartition();
        HigherDimGrid grid = new HigherDimGrid(k, dataPoints);
        /*System.out.println("Start MDL");
        //int k = 8;
        DataChartAlternateDesign chart = new DataChartAlternateDesign("Test, Ground Truth", grid, groundTruthNumber);
        chart.setDefaultCloseOperation(ApplicationFrame.DISPOSE_ON_CLOSE);
        chart.saveToJpegFile(new File("testResults/chartTest_truth.jpg"));
        System.out.println("Clustering start");
        grid.performClustering(false);
        System.out.println("Clustering end");
        int i = 1;
        for (Cluster cluster : grid.getClusters()) {
            for (DataPoint dp : cluster.getDataPoints()) {
                dp.setCluster("" + i);
            }
                ++i;
        }
        chartClustered = new DataChartAlternateDesign("Test, MDL", grid);
        chartClustered.saveToJpegFile(new File("testResults/chartTest_mdl.jpg"));
        FileIO.writeNMIFiles(grid.getDataPoints(), "testResults/mdl_nmi", ".txt");
        System.out.println("Finish MDL");*/

        System.out.println("Start Kmeans");
        Kmean kmean = new Kmean(groundTruthNumber, dataPoints);
        kmean.performKmeans();
        kmean.assignClusterIds();
        chartClustered = new DataChartAlternateDesign("Test, k - means", kmean.getClusters(), grid);
        chartClustered.saveToJpegFile(new File("testResults/chartTest_kmean.jpg"));
        FileIO.writeNMIFiles(kmean.getDataPoints(), "testResults/kmean_nmi", ".txt");
        System.out.println("Finish Kmeans");

        System.out.println("Start DBSCAN");
        DBSCANer dbscan = new DBSCANer(dataPoints);
        int kNearest = 10;
        dbscan.findKnearestNeighborDistance(kNearest, "testResults/10nearestNeighbors.jpg");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Type in Epsilon: ");
        double epsilon = Double.parseDouble(reader.readLine());
        //double epsilon = 2.5;
        dbscan.performDBSCAN(epsilon, kNearest);
        dbscan.assignClusterIds();
        chartClustered = new DataChartAlternateDesign("Test, DBSCAN", dbscan.getClusters(), grid);
        chartClustered.saveToJpegFile(new File("testResults/chartTest_dbscan_eps=" + epsilon + ".jpg"));
        FileIO.writeNMIFiles(dbscan.getDataPoints(), "testResults/dbscan_nmi", ".txt");
        System.out.println("Finish DBSCAN");

        System.out.println("Start CLIQUE");
        CLIQUE clique = new CLIQUE(dataPoints, k);
        System.out.println("Clustering start");
        clique.performCliqueAlgorithm(0);
        System.out.println("Clustering end");
        chartClustered = new DataChartAlternateDesign("Test, CLIQUE", clique.getGrid());
        chartClustered.saveToJpegFile(new File("testResults/chartTest_clique.jpg"));
        FileIO.writeNMIFiles(clique.getDataPoints(), "testResults/clique_nmi", ".txt");
        System.out.println("Finish CLIQUE");
    }

    private static void makeGaussianCluster(List<DataPoint> dataPoints, double[] mean, double mean0, double mean1, double deviation, int number, String clusterId) {

        mean[0] = mean0;
        mean[1] = mean1;

        for (int i = 0; i < number; ++i) {
            dataPoints.add(generator.generateDataPoint(mean, deviation, clusterId));
        }
    }

    private static void halfmoonAndGaussians(List<DataPoint> dataPoints, double[] mean) {

        mean[0] = 50;
        mean[1] = 50;
        shapeGenerator = new ShapeGenerator(new ArbitraryShape(dim));
        shapeGenerator.createHalfMoon(50, mean);
        List<DataPoint> shapePoints = shapeGenerator.generateShape(1000 * factor);
        for (DataPoint dp : shapePoints) {
            dp.setGroundTruth("1");
        }
        dataPoints.addAll(shapePoints);

        makeGaussianCluster(dataPoints, mean, 75, 50, 8.0, 3000, "2");
        makeGaussianCluster(dataPoints, mean, 25, 50, 4.5, 100, "3");

        groundTruthNumber = 3;
    }

    private static void gaussianDifferentDensitiesThreeClusters(List<DataPoint> dataPoints, double[] mean) {

        makeGaussianCluster(dataPoints, mean, 20.0, 50.0, 1.0, 1000, "1");
        makeGaussianCluster(dataPoints, mean, 45.0, 50.0, 5.0, 100, "2");
        makeGaussianCluster(dataPoints, mean, 25.0, 65.0, 2.2, 500, "3");
        groundTruthNumber = 3;
    }

    private static void gaussianDifferentDensities(List<DataPoint> dataPoints, double[] mean) {

        makeGaussianCluster(dataPoints, mean, 20.0, 50.0, 1.0, 1000, "1");
        makeGaussianCluster(dataPoints, mean, 40.0, 50.0, 5.0, 100, "2");
        groundTruthNumber = 2;
    }

    private static void circlesInCircles(List<DataPoint> dataPoints, double[] mean) {

        mean[0] = 50;
        mean[1] = 50;

        for (int i = 0; i < 100; ++i) {
            dataPoints.add(generator.generateDataPoint(mean, 0.3, "1"));
        }
        List<DataPoint> shapePoints;
        shapeGenerator = new ShapeGenerator(new ArbitraryShape(dim));
        shapeGenerator.createDoubleMoonLeft(mean, 30);
        shapePoints = shapeGenerator.generateShape(2000 * factor);
        for (DataPoint dp : shapePoints) {
            dp.setGroundTruth("2");
        }
        dataPoints.addAll(shapePoints);
        mean[0] = 45;
        shapeGenerator = new ShapeGenerator(new ArbitraryShape(dim));
        shapeGenerator.createDoubleMoonRight(mean, 30);
        shapePoints = shapeGenerator.generateShape(2000 * factor);
        for (DataPoint dp : shapePoints) {
            dp.setGroundTruth("2");
        }
        dataPoints.addAll(shapePoints);
        mean[0] = 50;
        shapeGenerator = new ShapeGenerator(new ArbitraryShape(dim));
        shapeGenerator.createDoubleMoonLeft(mean, 50);
        shapePoints = shapeGenerator.generateShape(2000 * factor);
        for (DataPoint dp : shapePoints) {
            dp.setGroundTruth("3");
        }
        dataPoints.addAll(shapePoints);
        mean[0] = 45;
        shapeGenerator = new ShapeGenerator(new ArbitraryShape(dim));
        shapeGenerator.createDoubleMoonRight(mean, 50);
        shapePoints = shapeGenerator.generateShape(2000 * factor);
        for (DataPoint dp : shapePoints) {
            dp.setGroundTruth("3");
        }
        dataPoints.addAll(shapePoints);
        mean[0] = 50;
        shapeGenerator = new ShapeGenerator(new ArbitraryShape(dim));
        shapeGenerator.createDoubleMoonLeft(mean, 75);
        shapePoints = shapeGenerator.generateShape(3000 * factor);
        for (DataPoint dp : shapePoints) {
            dp.setGroundTruth("4");
        }
        dataPoints.addAll(shapePoints);
        mean[0] = 45;
        shapeGenerator = new ShapeGenerator(new ArbitraryShape(dim));
        shapeGenerator.createDoubleMoonRight(mean, 75);
        shapePoints = shapeGenerator.generateShape(3000 * factor);
        for (DataPoint dp : shapePoints) {
            dp.setGroundTruth("4");
        }
        dataPoints.addAll(shapePoints);
        groundTruthNumber = 4;
    }

    private static void halfmoonWithGaussian(List<DataPoint> dataPoints, double[] mean) {

        mean[0] = 50;
        mean[1] = 50;
        shapeGenerator = new ShapeGenerator(new ArbitraryShape(dim));
        shapeGenerator.createHalfMoon(50, mean);
        List<DataPoint> shapePoints = shapeGenerator.generateShape(4000 * factor);
        for (DataPoint dp : shapePoints) {
            dp.setGroundTruth("1");
        }
        dataPoints.addAll(shapePoints);

        for (int i = 0; i < 2000 * factor; ++i) {
            dataPoints.add(generator.generateDataPoint(mean,5.0, "2"));
        }

        groundTruthNumber = 2;
    }

    private static void yinYang(List<DataPoint> dataPoints, double[] mean) {

        mean[0] = 52;
        mean[1] = 75;
        shapeGenerator = new ShapeGenerator(new ArbitraryShape(dim));
        shapeGenerator.createHalfMoon(25, mean);
        List<DataPoint> shapePoints = shapeGenerator.generateShape(4000 * factor);
        for (DataPoint dp : shapePoints) {
            dp.setGroundTruth("1");
        }
        dataPoints.addAll(shapePoints);

        for (int i = 0; i < 2000 * factor; ++i) {
            dataPoints.add(generator.generateDataPoint(mean,2.0, "2"));
        }

        mean[0] = 48;
        mean[1] = 25;
        shapeGenerator = new ShapeGenerator(new ArbitraryShape(dim));
        shapeGenerator.createDoubleMoonRight(mean, 25);
        shapePoints = shapeGenerator.generateShape(4000 * factor);
        for (DataPoint dp : shapePoints) {
            dp.setGroundTruth("1");
        }
        dataPoints.addAll(shapePoints);

        for (int i = 0; i < 2000 * factor; ++i) {
            dataPoints.add(generator.generateDataPoint(mean,2.0, "3"));
        }

        groundTruthNumber = 3;
    }

    private static void yinYang2(List<DataPoint> dataPoints, double[] mean) {

        mean[0] = 52;
        mean[1] = 75;
        shapeGenerator = new ShapeGenerator(new ArbitraryShape(dim));
        shapeGenerator.createHalfMoon(25, mean);
        List<DataPoint> shapePoints = shapeGenerator.generateShape(300 * factor);
        for (DataPoint dp : shapePoints) {
            dp.setGroundTruth("1");
        }
        dataPoints.addAll(shapePoints);

        for (int i = 0; i < 150 * factor; ++i) {
            dataPoints.add(generator.generateDataPoint(mean,2.0, "2"));
        }

        mean[0] = 48;
        mean[1] = 25;
        shapeGenerator = new ShapeGenerator(new ArbitraryShape(dim));
        shapeGenerator.createDoubleMoonRight(mean, 25);
        shapePoints = shapeGenerator.generateShape(300 * factor);
        for (DataPoint dp : shapePoints) {
            dp.setGroundTruth("1");
        }
        dataPoints.addAll(shapePoints);

        for (int i = 0; i < 600 * factor; ++i) {
            dataPoints.add(generator.generateDataPoint(mean,2.0, "3"));
        }

        groundTruthNumber = 3;
    }

    private static void doubleMoon(List<DataPoint> dataPoints, double mean[]) {

        List<DataPoint> shapePoints;
        mean[0] = 25;
        mean[1] = 65;
        shapeGenerator = new ShapeGenerator(new ArbitraryShape(dim));
        shapeGenerator.createDoubleMoonLeft(mean, 25);
        shapePoints = shapeGenerator.generateShape(3000 * factor);
        for (DataPoint dp : shapePoints) {
            dp.setGroundTruth("1");
        }
        dataPoints.addAll(shapePoints);

        mean[0] = 10.5;
        mean[1] = 40;
        shapeGenerator = new ShapeGenerator(new ArbitraryShape(dim));
        shapeGenerator.createDoubleMoonRight(mean, 25);
        shapePoints = shapeGenerator.generateShape(3000 * factor);
        for (DataPoint dp : shapePoints) {
            dp.setGroundTruth("2");
        }
        dataPoints.addAll(shapePoints);

        groundTruthNumber = 2;
    }

    private static void initMean(double[] mean) {

        for (int i = 0; i < dim; ++i) {

            mean[i] = 1;
        }
    }
}
