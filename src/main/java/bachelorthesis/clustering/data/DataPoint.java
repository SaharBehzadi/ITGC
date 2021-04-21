package bachelorthesis.clustering.data;

public class DataPoint {

    private int dim;
    private double[] vector;
    private String cluster;
    private String groundTruth;

    public DataPoint() {
        // currently only used for testing
    }

    public DataPoint(int dim, double[] vector) {

        setDim(dim);
        setVector(vector);
    }

    public DataPoint(int dim, double[] vector, String groundTruth) {

        setDim(dim);
        setVector(vector);
        setGroundTruth(groundTruth);
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getGroundTruth() {
        return groundTruth;
    }

    public void setGroundTruth(String groundTruth) {
        this.groundTruth = groundTruth;
    }

    public int getDim() {
        return dim;
    }

    public void setDim(int dim) {
        this.dim = dim;
    }

    public double[] getVector() {
        return vector;
    }

    public void setVector(double[] vector) {
        this.vector = vector;
    }
}
