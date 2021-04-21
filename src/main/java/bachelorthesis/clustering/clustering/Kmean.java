package bachelorthesis.clustering.clustering;

import bachelorthesis.clustering.data.DataPoint;
import bachelorthesis.clustering.statistics.VectorMath;

import java.util.ArrayList;
import java.util.List;

public class Kmean {

    private int k;
    private int dim;
    private ClusterKMeans[] clusters;
    private List<DataPoint> dataPoints;
    private List<double[]> centroids;

    public Kmean(int k, List<DataPoint> dataPoints) {

        this.k = k;
        this.dataPoints = dataPoints;
        clusters = new ClusterKMeans[k];
        centroids = new ArrayList<>();
        dim = dataPoints.get(0).getDim();
        for (int i = 0; i < k; ++i) {
            centroids.add(new double[dim]);
            clusters[i] = new ClusterKMeans(dim);
        }
    }

    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }

    public ClusterKMeans[] getClusters() {
        return clusters;
    }

    public void setClusters(ClusterKMeans[] clusters) {
        this.clusters = clusters;
    }

    private void findCentroids() {

        for (int i = 0; i < k; ++i) {
            for (int j = 0; j < dim; ++j) {

                centroids.get(i)[j] = clusters[i].getCentroid()[j];
                //System.out.println("" + i + ": " + j + "   " + centroids.get(i)[j]);
            }
        }
    }

    public void performKmeans() {

        initialAssignment();
        int iter = 0;
        do {
            findCentroids();
            assignPointsToClosestCentroid();
            iter++;
            //System.out.println("Iteration " + iter++);
            // sometimes it does not converge
        } while(centroidsNotIdent() && iter < 100);
    }

    private void assignPointsToClosestCentroid() {

        clearClusters();
        assignPoints();
        for (ClusterKMeans cluster : clusters) {
            cluster.calculateCentroid();
        }
    }

    private void assignPoints() {

        for (DataPoint dataPoint : dataPoints) {
            int index = 0;
            double dist = calculateDistToCentroid(dataPoint, index);
            for (int i = 1; i < k; ++i) {
                double d = calculateDistToCentroid(dataPoint, i);
                if (d < dist) {

                    index = i;
                    dist = d;
                }
            }
            clusters[index].addDataPoint(dataPoint);
        }
    }

    private double calculateDistToCentroid(DataPoint dataPoint, int index) {

        double dist = 0.0;
        for (int i = 0; i < dim; ++i) {

            double diff = dataPoint.getVector()[i] - clusters[index].getCentroid()[i];
            dist += Math.pow( diff, 2.0 );
        }
        return Math.sqrt(dist);
    }

    private void clearClusters() {

        for (ClusterKMeans cluster : clusters) {
            cluster.clear();
        }
    }

    private boolean centroidsNotIdent() {

        boolean ident = true;
        for (int i = 0; i < k; ++i) {
            for (int j = 0; j < dim; ++j) {

                if (centroids.get(i)[j] != clusters[i].getCentroid()[j]) {
                    ident = false;
                    break;
                }
            }
            if (!ident) {
                break;
            }
        }
        return !ident;
    }

    private void initialAssignment() {

        for (int i = 0; i < dataPoints.size(); ++i) {

            clusters[i % k].addDataPoint(dataPoints.get(i));
        }
        for (int i = 0; i < k; ++i) {

            clusters[i].calculateCentroid();
        }
    }

    public void assignClusterIds() {

        int i = 1;
        for (ClusterKMeans cluster : getClusters()) {
            for (DataPoint dp : cluster.getDataPoints()) {
                dp.setCluster("" + i);
            }
            ++i;
        }
    }
}
