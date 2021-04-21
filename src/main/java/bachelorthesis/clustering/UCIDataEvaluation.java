package bachelorthesis.clustering;

import bachelorthesis.clustering.charts.DataChartAlternateDesign;
import bachelorthesis.clustering.clustering.CLIQUE;
import bachelorthesis.clustering.clustering.DBSCANer;
import bachelorthesis.clustering.clustering.HierarchicalClusterer;
import bachelorthesis.clustering.clustering.Kmean;
import bachelorthesis.clustering.data.DataPartitioner;
import bachelorthesis.clustering.data.DataPoint;
import bachelorthesis.clustering.fileIO.CsvDataReader;
import bachelorthesis.clustering.fileIO.FileIO;
import bachelorthesis.clustering.grid.Cluster;
import bachelorthesis.clustering.grid.HigherDimGrid;
import org.jfree.ui.ApplicationFrame;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class UCIDataEvaluation {

    private static int groundTruthNumber = 3;
    private static final String dirResults = "results/uciResults/";
    private static int maxK = 20;
    private static final String dirData = "results/uciData/";

    public static void main(String[] args) {

        //irisClassification("results/uciData/iris.csv", 4);
        glassClassification();
        //occupancyClassification();
        //banknoteClassification();
        //yeastClassification();
        //userKnowledgeClassification();
        //bloodDonationsClassification();
        //seedsClassification();
    }

    private static void seedsClassification() {

        maxK = 7;
        groundTruthNumber = 3;
        uciEvaluation(dirData + "seeds.csv", 7);
    }

    private static void bloodDonationsClassification() {

        maxK = 20;
        groundTruthNumber = 2;
        uciEvaluation(dirData + "blood_donations.csv", 4);
    }

    private static void userKnowledgeClassification() {

        maxK = 20;
        groundTruthNumber = 4;
        uciEvaluation(dirData + "user_knowledge.csv", 5);
    }

    private static void yeastClassification() {

        maxK = 6;
        groundTruthNumber = 10;
        uciEvaluation(dirData + "yeast.csv", 8);
    }

    private static void banknoteClassification() {

        maxK = 20;
        groundTruthNumber = 2;
        uciEvaluation(dirData + "banknote.csv", 4);
    }

    private static void occupancyClassification() {

        System.out.println("occupancy - detection");
        maxK = 20;
        groundTruthNumber = 2;
        uciEvaluation("results/uciData/occupancy.csv", 5);
    }

    private static void glassClassification() {

        System.out.println("Glass classification");
        maxK = 5;
        groundTruthNumber = 7;
        uciEvaluation("results/uciData/glass.csv", 9);
    }

    private static void uciEvaluation(String csvFile, int dim) {

        CsvDataReader csvDataReader = new CsvDataReader(csvFile, dim);
        List<DataPoint> dataPoints = csvDataReader.getDataPoints();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        /*System.out.println("Start MDL");
        DataPartitioner partitioner = new DataPartitioner(dataPoints);
        int opt = partitioner.findOptimalPartition();
        System.out.println("optimal partition: " + opt);
        //int k = 4;
        /*for (int k = 2; k <= maxK; ++k) {
            HigherDimGrid grid = new HigherDimGrid(k, dataPoints);
            //System.out.println("Clustering start");
            System.out.println("k = " + k);
            grid.performClustering(false);
            //System.out.println("Clustering end");
            int i = 1;
            for (Cluster cluster : grid.getClusters()) {
                for (DataPoint dp : cluster.getDataPoints()) {
                    dp.setCluster("" + i);
                }
                ++i;
            }
            try {
                FileIO.writeNMIFiles(grid.getDataPoints(), dirResults + "mdl_nmi" + k, ".txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Finish MDL");*/

        System.out.println("Start Kmeans");
        Kmean kmean = new Kmean(groundTruthNumber, dataPoints);
        kmean.performKmeans();
        kmean.assignClusterIds();
        try {
            FileIO.writeNMIFiles(kmean.getDataPoints(), dirResults + "kmean_nmi", ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finish Kmeans");

        /*System.out.println("Start DBSCAN");
        DBSCANer dbscan = new DBSCANer(dataPoints);
        int k = 2 * dim;
        dbscan.findKnearestNeighborDistance(k, dirResults + "KnearestNeighbors.jpg");
        System.out.println("Type in Epsilon: ");
        double epsilon = 0;
        try {
            epsilon = Double.parseDouble(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbscan.performDBSCAN(epsilon, k);
        dbscan.assignClusterIds();
        try {
            FileIO.writeNMIFiles(dbscan.getDataPoints(), dirResults + "dbscan_nmi", ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finish DBSCAN");*/

        if (dataPoints.size() > 2000) {
            System.out.println("data set to big for hierarchical clustering");
        } else {
            System.out.println("Start hierarchical clustering");
            HierarchicalClusterer hierarchicalClusterer = new HierarchicalClusterer(dataPoints);
            hierarchicalClusterer.performHierarchicalClustering(groundTruthNumber);
            hierarchicalClusterer.assignClusterIds();
            try {
                FileIO.writeNMIFiles(hierarchicalClusterer.getDataPoints(), dirResults + "hier_nmi", ".txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Finish hierarchical clustering");
        }

        System.out.println("Start CLIQUE");
        System.out.println("Type in grid - resolution:");
        int resolution = 0;
        try {
            resolution = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        CLIQUE clique = new CLIQUE(dataPoints, resolution);  // k should be the same k used for MDL
        System.out.println("Clustering start");
        clique.performCliqueAlgorithm(0);
        System.out.println("Clustering end");
        clique.assignClusterIds();
        try {
            FileIO.writeNMIFiles(clique.getDataPoints(), dirResults + "clique_nmi", ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finish CLIQUE");
    }

    private static void irisClassification(String csvFile, int dim) {

        System.out.println("Iris classification");
        CsvDataReader csvDataReader = new CsvDataReader(csvFile, dim);
        List<DataPoint> dataPoints = csvDataReader.getDataPoints();

        /*System.out.println("Start MDL");
        DataPartitioner partitioner = new DataPartitioner(dataPoints);
        int opt = partitioner.findOptimalPartition();
        System.out.println("optimal partition: " + opt);
        for (int k = 2; k <= maxK; ++k) {
            HigherDimGrid grid = new HigherDimGrid(k, dataPoints);
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
            try {
                FileIO.writeNMIFiles(grid.getDataPoints(), dirResults + "mdl_nmi" + k, ".txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Finish MDL");*/

        System.out.println("Start Kmeans");
        Kmean kmean = new Kmean(groundTruthNumber, dataPoints);
        kmean.performKmeans();
        kmean.assignClusterIds();
        try {
            FileIO.writeNMIFiles(kmean.getDataPoints(), dirResults + "kmean_nmi", ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finish Kmeans");

        System.out.println("Start DBSCAN");
        DBSCANer dbscan = new DBSCANer(dataPoints);
        int k = 8;
        dbscan.findKnearestNeighborDistance(k, dirResults + "8nearestNeighbors.jpg");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Type in Epsilon: ");
        double epsilon = 0;
        try {
            epsilon = Double.parseDouble(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbscan.performDBSCAN(epsilon, k);
        dbscan.assignClusterIds();
        try {
            FileIO.writeNMIFiles(dbscan.getDataPoints(), dirResults + "dbscan_nmi", ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finish DBSCAN");

        System.out.println("Start hierarchical clustering");
        HierarchicalClusterer hierarchicalClusterer = new HierarchicalClusterer(dataPoints);
        hierarchicalClusterer.performHierarchicalClustering(groundTruthNumber);
        hierarchicalClusterer.assignClusterIds();
        try {
            FileIO.writeNMIFiles(hierarchicalClusterer.getDataPoints(), dirResults + "hier_nmi", ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finish hierarchical clustering");

        System.out.println("Start CLIQUE");
        CLIQUE clique = new CLIQUE(dataPoints, 7);  // k should be the optimal partitioning, determined for MDL
        clique.performCliqueAlgorithm(0);
        clique.assignClusterIds();
        try {
            FileIO.writeNMIFiles(clique.getDataPoints(), dirResults + "clique_nmi", ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finish CLIQUE");
    }
}
