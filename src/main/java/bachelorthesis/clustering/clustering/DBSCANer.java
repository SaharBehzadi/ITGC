package bachelorthesis.clustering.clustering;

import bachelorthesis.clustering.charts.KdistChart;
import bachelorthesis.clustering.data.DataPoint;
import bachelorthesis.clustering.grid.Cluster;
import bachelorthesis.clustering.statistics.RegressionAnalyser;

import javax.xml.crypto.Data;
import java.io.File;
import java.util.*;

public class DBSCANer {

    private List<ClusterDBSCAN> clusters;
    private List<DataPoint> dataPoints;

    public DBSCANer(List<DataPoint> dataPoints) {

        setDataPoints(dataPoints);
        assignUnvisited();
    }

    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(List<DataPoint> dataPoints) {
        this.dataPoints = dataPoints;
    }

    public List<ClusterDBSCAN> getClusters() {
        return clusters;
    }

    public void setClusters(List<ClusterDBSCAN> clusters) {
        this.clusters = clusters;
    }

    private void assignUnvisited() {

        for (DataPoint dataPoint : dataPoints) {
            dataPoint.setCluster("-1");
        }
    }

    private void initKdist(double[] kdist) {

        for (int i = 0; i < kdist.length; ++i) {
            kdist[i] = Double.MAX_VALUE;
        }
    }

    // deprecated
    public double findEpsilon(int k) {

        double[] kdist = findKnearestNeighborDistance(k, "deprecated");
        RegressionAnalyser analyser = new RegressionAnalyser();
        for (int i = 0; i < kdist.length; ++i) {
            analyser.addDataPoint(kdist[i]);
        }
        analyser.linearRegression();
        return 0.0; // not implemented
    }

    public double[] findKnearestNeighborDistance(int k, String fileName) {

        double[] kdist = new double[dataPoints.size()];
        int index = 0;
        List<Double> distances = new ArrayList<>();
        for (DataPoint dataPoint : dataPoints) {

            distances.clear();
            for (DataPoint dp : dataPoints) {

                double distance = computeDistance(dataPoint, dp);
                int i = 0;
                for (; i < k && i < distances.size(); ++i) {
                    if (distance < distances.get(i)) {
                        distances.add(i, distance);
                        break;
                    }
                }
                if (i == distances.size()) {
                    distances.add(distance);
                }
            }
            kdist[index++] = distances.get(k);
            //System.out.println("index: " + index + " of " + dataPoints.size());
        }
        sortKdist(kdist);
        while(true) {
            try {
                KdistChart chart = new KdistChart("K distance", kdist);
                chart.showChart();
                chart.saveToJpegFile(new File(fileName));
                break;
            } catch (Exception e) {
                System.out.println("Redraw");
            }
        }

        return kdist;
    }

    private void sortKdist(double[] kdist) {

        for (int i = 0; i < kdist.length-1; ++i) {
            for (int j = i+1; j < kdist.length; ++j) {
                if (kdist[i] < kdist[j]) {
                    double temp = kdist[i];
                    kdist[i] = kdist[j];
                    kdist[j] = temp;
                }
            }
        }
    }

    public void performDBSCAN(double epsilon, int minPoints) {

        clusters = new ArrayList<>();
        clusters.add(new ClusterDBSCAN());
        int clusterID = 1;
        Set<DataPoint> queriedNeighbors;
        for (DataPoint dataPoint : dataPoints) {
            if (dataPoint.getCluster().equals("-1")) {
                queriedNeighbors = regionQuery(dataPoint, epsilon);
                // noise is marked with "1"
                if (queriedNeighbors.size() < minPoints) {
                    dataPoint.setCluster("1");
                    clusters.get(0).addDataPoint(dataPoint);
                } else {
                    clusters.add(new ClusterDBSCAN());
                    dataPoint.setCluster("" + clusterID+1);
                    clusters.get(clusterID).addDataPoint(dataPoint);
                    expandCluster(queriedNeighbors, epsilon, minPoints, clusters.get(clusterID), clusterID+1);
                    ++clusterID;
                }
            }
        }
    }

    private void expandCluster(Set<DataPoint> queriedNeighbors, double epsilon, int minPoints, ClusterDBSCAN cluster, int clusterID) {

        Set<DataPoint> collectedNeighbors = new HashSet<>();
        Set<DataPoint> neighbors;
        for (DataPoint dataPoint : queriedNeighbors) {
            if (dataPoint.getCluster().equals("-1")) {
                dataPoint.setCluster("" + clusterID);
                neighbors = regionQuery(dataPoint, epsilon);
                if (neighbors.size() >= minPoints) {
                    collectedNeighbors.addAll(neighbors);
                }
                cluster.addDataPoint(dataPoint);
            }
        }
        if (collectedNeighbors.size() > 0) {
            expandCluster(collectedNeighbors, epsilon, minPoints, cluster, clusterID);
        }
    }

    private Set<DataPoint> regionQuery(DataPoint center, double epsilon) {

        Set<DataPoint> queriedDP = new HashSet<>();
        double distance;
        for (DataPoint dataPoint : dataPoints) {

            distance = 0.0;
            for (int i = 0; i < dataPoint.getDim(); ++i) {

                double diff = dataPoint.getVector()[i] - center.getVector()[i];
                distance += Math.pow( diff, 2.0 );
            }
            if (Math.sqrt(distance) < epsilon) {
                queriedDP.add(dataPoint);
            }
        }
        return queriedDP;
    }

    private double computeDistance(DataPoint dataPoint, DataPoint center) {

        double distance = 0.0;
        for (int i = 0; i < dataPoint.getDim(); ++i) {

            double diff = dataPoint.getVector()[i] - center.getVector()[i];
            distance += Math.pow( diff, 2.0 );
        }
        return Math.sqrt(distance);
    }

    public void assignClusterIds() {

        int i = 1;
        for (ClusterDBSCAN cluster : getClusters()) {
            for (DataPoint dp : cluster.getDataPoints()) {
                dp.setCluster("" + i);
            }
            ++i;
        }
    }
}
