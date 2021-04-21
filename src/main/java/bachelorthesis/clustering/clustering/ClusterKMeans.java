package bachelorthesis.clustering.clustering;

import bachelorthesis.clustering.data.DataPoint;
import bachelorthesis.clustering.statistics.VectorMath;

public class ClusterKMeans extends ClusterDBSCAN {

    private double[] centroid;

    public ClusterKMeans(int dim) {

        super();
        centroid = new double[dim];
    }

    public double[] getCentroid() {
        return centroid;
    }

    public void setCentroid(double[] centroid) {
        this.centroid = centroid;
    }

    public void calculateCentroid() {

        VectorMath.fillVectorWithZeros(centroid);
        for (DataPoint dataPoint : getDataPoints()) {
            for (int i = 0; i < centroid.length; ++i) {

                centroid[i] += dataPoint.getVector()[i];
            }
        }
        VectorMath.scalarVectorDivision(centroid, getDataPoints().size());
    }

    public void clear() {
        getDataPoints().clear();
    }
}
