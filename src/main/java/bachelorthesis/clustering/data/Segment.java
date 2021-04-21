package bachelorthesis.clustering.data;

public class Segment {

    private double[] mean;
    private double deviation;

    public Segment(double[] mean, double deviation) {
        this.mean = mean;
        this.deviation = deviation;
    }

    public double[] getMean() {
        return mean;
    }

    public void setMean(double[] mean) {
        this.mean = mean;
    }

    public double getDeviation() {
        return deviation;
    }

    public void setDeviation(double deviation) {
        this.deviation = deviation;
    }
}
