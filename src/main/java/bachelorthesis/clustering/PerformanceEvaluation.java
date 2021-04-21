package bachelorthesis.clustering;

import bachelorthesis.clustering.clustering.*;
import bachelorthesis.clustering.data.DataGenerator;
import bachelorthesis.clustering.data.DataPartitioner;
import bachelorthesis.clustering.data.DataPoint;
import bachelorthesis.clustering.fileIO.FileIO;
import bachelorthesis.clustering.grid.Cluster;
import bachelorthesis.clustering.grid.HigherDimGrid;
import bachelorthesis.clustering.statistics.VectorMath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PerformanceEvaluation {

    private static int startDim = 10;
    private static int endDim = 50;
    private static int steps = 10;

    public static void main(String[] args) {

        //kmeansPerformance();
        //dbscanPerformance();
        //mdlPerformance();
        //performanceEvaluation();

        //nmiPerformanceEvaluation();
        runtimePerformanceEvaluation();
    }

    private static void runtimePerformanceEvaluation() {

        for (int i = 1; i <= 10; ++i) {

            //kmeansRuntimePerformance(5, i);
            //dbscanRuntimePerformance(5, i);
            if (i > 7) {
                hierarchicalRuntimePerformance(5, i);
            }
            //mdlRuntimePerformance(5, i);
        }
        /*for (int i = 5; i <= 50; i += 5) {

            kmeansRuntimePerformance(i, 1);
            dbscanRuntimePerformance(i, 1);
            hierarchicalRuntimePerformance(i, 1);
            mdlRuntimePerformance(i, 1);
        }*/
    }

    private static void kmeansRuntimePerformance(int dim, int factor) {

        System.out.print("Kmeans : dim=" + dim + "   size=" + factor * 1000);
        kmeansRuntime(dim, factor, "yinyang");
    }

    private static void kmeansRuntime(int dim, int factor, String type) {

        List<DataPoint> dataPoints = FileIO.extractDataPointsFromFile("results/performanceTests/dim" + dim + "size" + factor + type + ".csv");
        Kmean kmean = new Kmean(10, dataPoints);
        long start = System.currentTimeMillis();
        kmean.performKmeans();
        long end = System.currentTimeMillis();
        System.out.println("   " + " time= " + (end - start));
    }

    private static void dbscanRuntimePerformance(int dim, int factor) {

        System.out.print("DBSCAN : dim=" + dim + "   size=" + factor * 1000);
        dbscanRuntime(dim, factor, "yinyang");
    }

    private static void dbscanRuntime(int dim, int factor, String type) {

        List<DataPoint> dataPoints = FileIO.extractDataPointsFromFile("results/performanceTests/dim" + dim + "size" + factor + type + ".csv");
        DBSCANer dbscan = new DBSCANer(dataPoints);
        long start = System.currentTimeMillis();
        dbscan.performDBSCAN(1, 5);
        long end = System.currentTimeMillis();
        System.out.println("   " + " time= " + (end - start));
    }

    private static void hierarchicalRuntimePerformance(int dim, int factor) {

        System.out.print("hierarchical : dim=" + dim + "   size=" + factor * 1000);
        hierRuntime(dim, factor, "yinyang");
    }

    private static void hierRuntime(int dim, int factor, String type) {

        List<DataPoint> dataPoints = FileIO.extractDataPointsFromFile("results/performanceTests/dim" + dim + "size" + factor + type + ".csv");
        HierarchicalClusterer clusterer = new HierarchicalClusterer(dataPoints);
        long start = System.currentTimeMillis();
        clusterer.performHierarchicalClustering(3);
        long end = System.currentTimeMillis();
        System.out.println("   " + " time= " + (end - start));
    }

    private static void mdlRuntimePerformance(int dim, int factor) {

        System.out.print("mdl : dim=" + dim + "   size=" + factor * 1000);
        mdlRuntime(dim, factor, "yinyang");
    }

    private static void mdlRuntime(int dim, int factor, String type) {

        try {
            List<DataPoint> dataPoints = FileIO.extractDataPointsFromFile("results/performanceTests/dim" + dim + "size" + factor + type + ".csv");
            int k = new DataPartitioner(dataPoints).findOptimalPartition("results/performanceTests/res.txt", "results/performanceTests/areas.txt");
            //int k = 10;
            HigherDimGrid grid = new HigherDimGrid(k, dataPoints);
            long start = System.currentTimeMillis();
            grid.performClustering(false);
            long end = System.currentTimeMillis();
            System.out.println("   " + " time= " + (end - start));
            try {
                FileIO.writeNMIFiles(dataPoints, "results/performanceTests/results/MDL/", "mdl_" + type + "_dim" + dim + "size" + factor + "x", ".txt");
            } catch (IOException e) {
                //e.printStackTrace();
            }
        } catch (OutOfMemoryError error) {
            //error.printStackTrace();
        }
    }


    private static void nmiPerformanceEvaluation() {

        for (int i = 1; i <= 10; ++i) {

            //kmeansNmiPerformance(5, i);
            //dbscanNmiPerformance(5, i);
            //hierarchicalNmiPerformance(5, i);
            //mdlNmiPerformance(5, i);
        }
        for (int i = 5; i <= 50; i += 5) {

            //kmeansNmiPerformance(i, 1);
            //dbscanNmiPerformance(i, 1);
            hierarchicalNmiPerformance(i, 1);
            //mdlNmiPerformance(i, 1);
        }
    }

    private static void kmeansNmiPerformance(int dim, int factor) {

        System.out.println("Kmeans : dim=" + dim + "   size=" + factor * 1000);
        kmeans(dim, factor, "near");
        kmeans(dim, factor, "over");
        kmeans(dim, factor, "arbitrary");
        kmeans(dim, factor, "moons");
        kmeans(dim, factor, "elliptical");
    }

    private static void kmeans(int dim, int factor, String type) {

        List<DataPoint> dataPoints = FileIO.extractDataPointsFromFile("results/performanceTests/dim" + dim + "size" + factor + type + ".csv");
        Kmean kmean = new Kmean(10, dataPoints);
        kmean.performKmeans();
        int i = 1;
        for (ClusterKMeans cluster : kmean.getClusters()) {
            for (DataPoint dp : cluster.getDataPoints()) {
                dp.setCluster("" + i);
            }
            ++i;
        }
        try {
            FileIO.writeNMIFiles(dataPoints, "results/performanceTests/results/Kmean/", "kmean_" + type + "_dim" + dim + "size" + factor + "x", ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void dbscanNmiPerformance(int dim, int factor) {

        System.out.println("DBSCAN : dim=" + dim + "   size=" + factor * 1000);
        dbscan(dim, factor, "near");
        dbscan(dim, factor, "over");
        dbscan(dim, factor, "arbitrary");
        dbscan(dim, factor, "moons");
        dbscan(dim, factor, "elliptical");
    }

    private static void dbscan(int dim, int factor, String type) {

        List<DataPoint> dataPoints = FileIO.extractDataPointsFromFile("results/performanceTests/dim" + dim + "size" + factor + type + ".csv");
        DBSCANer dbscan = new DBSCANer(dataPoints);
        dbscan.performDBSCAN(0.3, 10);
        int i = 1;
        for (ClusterDBSCAN cluster : dbscan.getClusters()) {
            for (DataPoint dp : cluster.getDataPoints()) {
                dp.setCluster("" + i);
            }
            ++i;
        }
        try {
            FileIO.writeNMIFiles(dataPoints, "results/performanceTests/results/DBSCAN/", "dbscan_" + type + "_dim" + dim + "size" + factor + "x", ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void hierarchicalNmiPerformance(int dim, int factor) {

        System.out.println("hierarchical : dim=" + dim + "   size=" + factor * 1000);
        hier(dim, factor, "near");
        hier(dim, factor, "over");
        hier(dim, factor, "arbitrary");
        hier(dim, factor, "moons");
        hier(dim, factor, "elliptical");
    }

    private static void hier(int dim, int factor, String type) {

        List<DataPoint> dataPoints = FileIO.extractDataPointsFromFile("results/performanceTests/dim" + dim + "size" + factor + type + ".csv");
        HierarchicalClusterer clusterer = new HierarchicalClusterer(dataPoints);
        clusterer.performHierarchicalClustering(2);
        int i = 1;
        for (ClusterDBSCAN cluster : clusterer.getClusters()) {
            for (DataPoint dp : cluster.getDataPoints()) {
                dp.setCluster("" + i);
            }
            ++i;
        }
        try {
            FileIO.writeNMIFiles(dataPoints, "results/performanceTests/results/hier/", "hier_" + type + "_dim" + dim + "size" + factor + "x", ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void mdlNmiPerformance(int dim, int factor) {

        System.out.println("mdl : dim=" + dim + "   size=" + factor * 1000);
        mdl(dim, factor, "near");
        mdl(dim, factor, "over");
        mdl(dim, factor, "arbitrary");
        mdl(dim, factor, "moons");
        mdl(dim, factor, "elliptical");
    }

    private static void mdl(int dim, int factor, String type) {

        try {
            List<DataPoint> dataPoints = FileIO.extractDataPointsFromFile("results/performanceTests/dim" + dim + "size" + factor + type + ".csv");
            int k = new DataPartitioner(dataPoints).findOptimalPartition("results/performanceTests/res.txt", "results/performanceTests/areas.txt");
            //int k = 10;
            HigherDimGrid grid = new HigherDimGrid(k, dataPoints);
            grid.performClustering(false);
            int i = 1;
            for (Cluster cluster : grid.getClusters()) {
                for (DataPoint dp : cluster.getDataPoints()) {
                    dp.setCluster("" + i);
                }
                ++i;
            }
            try {
                FileIO.writeNMIFiles(dataPoints, "results/performanceTests/results/MDL/", "mdl_" + type + "_dim" + dim + "size" + factor + "x", ".txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
        }
    }

    private static void performanceEvaluation() {

        System.out.println("Starting performance tests");

        for (int dim = startDim; dim <= endDim; dim += steps) {

            startSeries(dim);
        }

        System.out.println("Finishing performance tests");
    }

    private static void startSeries(int dim) {

        System.out.println("dim    size   Kmeans   DBSCAN   hierarchical");
        for (int size = 1000; size <= 10000; size += 1000) {

            System.out.print(dim + "   " + size);
            List<DataPoint> dataPoints = generateDatapoints(dim, size);
            Kmean kmean = new Kmean(5, dataPoints);
            long start = System.currentTimeMillis();
            kmean.performKmeans();
            long end = System.currentTimeMillis();
            long time = end - start;
            System.out.print("   " + time);

            DBSCANer dbscaNer = new DBSCANer(dataPoints);
            start = System.currentTimeMillis();
            dbscaNer.performDBSCAN(1.0, 5);
            end = System.currentTimeMillis();
            time = end - start;
            if (dim == 10 && size <= 2000) {
                System.out.print("   " + time);
            } else {
                System.out.println("   " + time);
            }

            if (dim == 10 && size <= 2000) {

                HierarchicalClusterer clusterer = new HierarchicalClusterer(dataPoints);
                start = System.currentTimeMillis();
                clusterer.performHierarchicalClustering(2);
                end = System.currentTimeMillis();
                time = end - start;
                System.out.println("   " + time);
            }
        }
    }

    private static void mdlPerformance() {

        System.out.println("Starting performance tests for MDL");

        for (int dim = startDim; dim <= endDim; dim += steps) {

            startSeriesForMDL(dim);
        }

        System.out.println("Finishing performance tests for MDL");
    }

    private static void startSeriesForMDL(int dim) {

        for (int size = 1000; size <= 10000; size += 1000) {

            List<DataPoint> dataPoints = generateDatapoints(dim, size);
            HigherDimGrid grid = new HigherDimGrid(2, dataPoints);
            long start = System.currentTimeMillis();
            grid.performClustering(false);
            long end = System.currentTimeMillis();
            long time = end - start;
            printResults(dim, size, time);
        }
    }

    private static void kmeansPerformance() {

        System.out.println("Starting performance tests for Kmeans");

        for (int dim = startDim; dim <= endDim; dim += steps) {

            startSeriesForKmean(dim);
        }

        System.out.println("Finishing performance tests for Kmeans");
    }

    private static void startSeriesForKmean(int dim) {

        for (int size = 1000; size <= 10000; size += 1000) {

            List<DataPoint> dataPoints = generateDatapoints(dim, size);
            Kmean kmean = new Kmean(5, dataPoints);
            long start = System.currentTimeMillis();
            kmean.performKmeans();
            long end = System.currentTimeMillis();
            long time = end - start;
            printResults(dim, size, time);
        }
    }

    private static void dbscanPerformance() {

        System.out.println("Starting performance tests for DBSCAN");

        for (int dim = startDim; dim <= endDim; dim += steps) {

            startSeriesForDbscan(dim);
        }

        System.out.println("Finishing performance tests for DBSCAN");
    }

    private static void startSeriesForDbscan(int dim) {

        for (int size = 1000; size <= 10000; size += 1000) {

            List<DataPoint> dataPoints = generateDatapoints(dim, size);
            DBSCANer dbscan = new DBSCANer(dataPoints);
            long start = System.currentTimeMillis();
            dbscan.performDBSCAN(2, 5);
            long end = System.currentTimeMillis();
            long time = end - start;
            printResults(dim, size, time);
        }
    }

    private static void printResults(int dim, int size, long time) {
        System.out.println(dim + "    " + size + "    " + time);
    }

    private static List<DataPoint> generateDatapoints(int dim, int size) {

        List<DataPoint> dataPoints = new ArrayList<>();
        DataGenerator generator = new DataGenerator(dim);
        double[] mean = new double[dim];
        VectorMath.fillVectorWithZeros(mean);
        for (int i = 0; i < size; ++i) {
            dataPoints.add(generator.generateDataPoint(mean, 5));
        }
        return dataPoints;
    }
}
