package bachelorthesis.clustering;

import bachelorthesis.clustering.data.DataGenerator;
import bachelorthesis.clustering.data.DataPoint;

import java.util.ArrayList;
import java.util.List;

import static bachelorthesis.clustering.statistics.VectorMath.*;

public class CodingCostTest {

    public static void main(String[] args) {
        int dim = 1;
        DataGenerator generator = new DataGenerator(dim);
        double[] mean = new double[dim];
        double deviation = 1.0;

        mean[0] = 0.0;
        List<DataPoint> dataPoints = new ArrayList<>();
        fillDataPoints(generator, dataPoints, mean, deviation);
        mean[0] = 30.0;
        List<DataPoint> dataPoints1 = new ArrayList<>();
        fillDataPoints(generator, dataPoints1, mean, deviation);

        test(dataPoints, dim);
        test(dataPoints1, dim);

        dataPoints.addAll(dataPoints1);

        test(dataPoints, dim);

        System.out.println("-------------------------------");

        List<DataPoint> testData = new ArrayList<>();
        for (int i = 0; i <= 20; ++i) {
            double[] vector = new double[dim];
            vector[0] = i;
            testData.add(new DataPoint(dim, vector));
        }
        mean[0] = 0.0;
        double[] sigma = new double[dim];
        sigma[0] = 1.0;
        for (DataPoint dp : testData) {
            double cost = calculateIndividualCodingCost(dp, 0, mean, sigma);
            System.out.println(dp.getVector()[0] + " ::: " + cost);
        }
    }

    private static void fillDataPoints(DataGenerator generator, List<DataPoint> dataPoints, double[] mean, double deviation) {

        for (int i = 0; i < 2000; ++i) {

            dataPoints.add(generator.generateDataPoint(mean, deviation));
        }
    }

    private static void test(List<DataPoint> dataPoints, int dim) {

        double[] mu = new double[dim];
        double[] sigma = new double[dim];
        calculateMuAndSigma(dataPoints, mu, sigma, dim);
        double cost = calculateCodingCost(dataPoints, dim, mu, sigma);
        System.out.println("Coding cost: " + cost);
        for (int i = 0; i < dim; ++i) {
            System.out.println("   mu:    " + mu[i]);
            System.out.println("   sigma: " + sigma[i]);
        }
    }

    private static double calculateIndividualCodingCost(DataPoint dataPoint, int index, double[] mu, double[] sigma) {

        double p = probability(dataPoint, sigma, mu, index);
        return -p * Math.log(p) / Math.log(2.0);
    }

    private static double calculateCodingCost(List<DataPoint> dataPoints, int dim, double[] mu, double[] sigma) {

        double[] codingCosts = new double[dim];
        fillVectorWithZeros(codingCosts);
        for (DataPoint dataPoint : dataPoints) {
            for (int i = 0; i < dim; ++i) {

                double p = probability(dataPoint,sigma, mu, i);
                codingCosts[i] += -p * Math.log(p) / Math.log(2.0);
            }
        }
        if (dataPoints.size() <= 1) {
            return 0.0;
        }
        // first trials with the sum of vector elements
        return vectorSum(codingCosts); // TODO maybe this can be improved
    }

    private static double probability(DataPoint dataPoint, double[] sigma, double[] mu, int index) {

        double factor1 = 1.0 / (sigma[index] * Math.sqrt(2.0 * Math.PI));
        double x = Math.pow(dataPoint.getVector()[index] - mu[index], 2.0);
        double factor2 = Math.exp(-x / (2.0 * Math.pow(sigma[index], 2.0)));
        return factor1 * factor2;
    }

    private static void fillMuAndSigmaWithZeros(double[] mu, double[] sigma, int dim) {

        mu = new double[dim];
        sigma = new double[dim];
        fillVectorWithZeros(mu);
        fillVectorWithZeros(sigma);
    }

    private static void calculateMu(List<DataPoint> dataPoints, int dim, double[] mu) {

        for (DataPoint dataPoint : dataPoints) {
            for (int i = 0; i < dim; ++i) {

                mu[i] += dataPoint.getVector()[i];
            }
        }
        scalarVectorDivision(mu, dataPoints.size());
    }

    private static void calculateMuAndSigma(List<DataPoint> dataPoints, double[] mu, double[] sigma, int dim) {

        fillMuAndSigmaWithZeros(mu, sigma, dim);
        calculateMu(dataPoints, dim, mu);
        for (DataPoint dataPoint : dataPoints) {

            for (int i = 0; i < sigma.length; ++i) {

                sigma[i] += Math.pow(dataPoint.getVector()[i] - mu[i], 2.0);
            }
        }
        scalarVectorDivision(sigma, dataPoints.size());
        scalarVectorSqrt(sigma);
    }
}
