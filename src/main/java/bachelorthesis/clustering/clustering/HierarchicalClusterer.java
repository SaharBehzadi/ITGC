package bachelorthesis.clustering.clustering;

import bachelorthesis.clustering.data.DataPoint;
import bachelorthesis.clustering.statistics.VectorMath;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HierarchicalClusterer {

    // the k-means cluster is sufficient for this algorithm
    private Set<ClusterKMeans> clusters;
    private List<DataPoint> dataPoints;

    public HierarchicalClusterer(List<DataPoint> dataPoints) {

        setDataPoints(dataPoints);
        clusters = new HashSet<>();
        initializeClusters();
    }

    public Set<ClusterKMeans> getClusters() {
        return clusters;
    }

    public void setClusters(Set<ClusterKMeans> clusters) {
        this.clusters = clusters;
    }

    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(List<DataPoint> dataPoints) {
        this.dataPoints = dataPoints;
    }

    private void initializeClusters() {

        for (DataPoint dataPoint : dataPoints) {

            clusters.add(new ClusterKMeans(dataPoint.getDim()));
        }
        int i = 0;
        for (ClusterKMeans cluster : clusters) {

            cluster.addDataPoint(dataPoints.get(i++));
            cluster.calculateCentroid();
            /*System.out.println(cluster.getCentroid()[0]);
            System.out.println(cluster.getCentroid()[1]);*/
        }
    }

    public void performHierarchicalClustering(int numClusters) {

        double minDist = Double.MAX_VALUE;
        double dist = 0.0;
        ClusterKMeans cluster = new ClusterKMeans(dataPoints.get(0).getDim());
        ClusterKMeans merger = new ClusterKMeans(dataPoints.get(0).getDim());
        //long time = System.currentTimeMillis();
        while (clusters.size() > numClusters) {
            for (ClusterKMeans clusterCandidate : clusters) {
                for (ClusterKMeans mergerCandidate : clusters) {
                    if (clusterCandidate != mergerCandidate) {

                        dist = calculateDistance(clusterCandidate, mergerCandidate);
                        //System.out.println(dist);
                        if (dist < minDist) {

                            minDist = dist;
                            cluster = clusterCandidate;
                            //System.out.println(merger);
                            merger = mergerCandidate;
                            //System.out.println(merger);
                        }
                    }
                    /*if (System.currentTimeMillis() - time > 300000) {
                        //System.out.println("Aborted due to time, size: " + dataPoints.size());
                        break;
                    }*/
                }
                /*if (System.currentTimeMillis() - time > 300000) {
                    //System.out.println("Aborted due to time, size: " + dataPoints.size());
                    break;
                }*/
            }
            //System.out.println("Before merge: " + cluster.getDataPoints().size());
            cluster.addAllDataPoints(merger.getDataPoints());
            //System.out.println("After merge: " + cluster.getDataPoints().size());
            clusters.remove(merger);
            //System.out.println("After delete: " + cluster.getDataPoints().size());
            cluster.calculateCentroid();
            //System.out.println("Set size: " + clusters.size());
            minDist = Double.MAX_VALUE;
            // if the algorithm takes more than 3 minutes, end it
            /*if (System.currentTimeMillis() - time > 300000) {
                System.out.println("Aborted due to time, size: " + dataPoints.size());
                break;
            }*/
        }
    }

    private double calculateDistance(ClusterKMeans cluster1, ClusterKMeans cluster2) {

        double sum = 0.0;
        for (int i = 0; i < cluster1.getCentroid().length; ++i) {

            double diff = cluster1.getCentroid()[i] - cluster2.getCentroid()[i];
            sum += Math.pow( diff, 2.0 );
        }
        return Math.sqrt(sum);
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
