package bachelorthesis.clustering.statistics;

import java.util.ArrayList;
import java.util.List;

public class RegressionAnalyser {

    private List<Double> dataset;
    private int k;
    private double datasetMean;
    private double kMean;   // TODO this might be confusing with kmeans algorithm, maybe find a better name

    // coefficients for linear regression
    private double alpha1;
    private double alpha0;
    private double deviation;

    public RegressionAnalyser() {

        dataset = new ArrayList<>();
    }

    public void addDataPoint(double cost) {

        dataset.add(cost);
    }

    public double getDataPoint(int index) {

        return dataset.get(index);
    }

    public List<Double> getDataset() {
        return dataset;
    }

    public void setDataset(List<Double> dataset) {
        this.dataset = dataset;
    }

    public double getDeviation() {
        return deviation;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public double getAlpha1() {
        return alpha1;
    }

    public void setAlpha1(double alpha1) {
        this.alpha1 = alpha1;
    }

    public double getAlpha0() {
        return alpha0;
    }

    public void setAlpha0(double alpha0) {
        this.alpha0 = alpha0;
    }

    private void calculateDatasetMean() {

        datasetMean = 0.0;
        for (Double data : dataset) {
            datasetMean += data;
        }
        datasetMean /= k;
    }

    private void calculateKMean() {

        kMean = (1.0 + k) * k / (2.0 * k);
    }

    public void setkMean(double kMean) {
        this.kMean = kMean;
    }

    public void linearRegression() {

        calculateKMean();
        calculateDatasetMean();
        int index = 0;
        double sum = 0.0;
        double sumErrorSquares = 0.0;
        for (Double data : dataset) {
            ++index;
            alpha1 += (index - kMean) * (data - datasetMean);
            sum += Math.pow((index - kMean), 2.0);
            sumErrorSquares += Math.abs(Math.pow(data - datasetMean, 2.0));
        }
        alpha1 /= sum;
        alpha0 = datasetMean - alpha1 * kMean;
        deviation = Math.sqrt(sumErrorSquares / (index-1));
    }
}
