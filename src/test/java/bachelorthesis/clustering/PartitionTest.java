package bachelorthesis.clustering;

import bachelorthesis.clustering.data.DataPartitioner;
import bachelorthesis.clustering.data.DataPoint;
import data.TestDataReader;

import java.util.ArrayList;
import java.util.List;

public class PartitionTest {

    public static void main(String[] args) {

        // Before starting this program, run TestDataGenerator to produce the csv file used in this program
        //runFirstGenerationTests();
        runSecondGenerationTests();
    }

    private static void runFirstGenerationTests() {

        System.out.println("First Generation Partitioniing");
        System.out.println("*********************************\n");

        List<DataPoint> dataPoints = extractDataPointsFromFile("results/testData0.csv");
        DataPartitioner dataPartitioner = new DataPartitioner(dataPoints, 100, 100);
        dataPartitioner.findOptimalPartition_old("results/results_old.txt", "results/areas_old.txt");

        List<DataPoint> dataPoints1 = extractDataPointsFromFile("results/testData1.csv");
        DataPartitioner dataPartitioner1 = new DataPartitioner(dataPoints1, 100, 100);
        dataPartitioner1.findOptimalPartition_old("results/results1_old.txt", "results/areas1_old.txt");

        List<DataPoint> dataPoints2 = extractDataPointsFromFile("results/testData2.csv");
        DataPartitioner dataPartitioner2 = new DataPartitioner(dataPoints2, 100, 100);
        dataPartitioner2.findOptimalPartition_old("results/results2_old.txt", "results/areas2_old.txt");

        List<DataPoint> dataPoints3 = extractDataPointsFromFile("results/testData3.csv");
        DataPartitioner dataPartitioner3 = new DataPartitioner(dataPoints3, 100, 100);
        dataPartitioner3.findOptimalPartition_old("results/results3_old.txt", "results/areas3_old.txt");

        List<DataPoint> dataPoints4 = extractDataPointsFromFile("results/testData4.csv");
        DataPartitioner dataPartitioner4 = new DataPartitioner(dataPoints4, 100, 100);
        dataPartitioner4.findOptimalPartition_old("results/results4_old.txt", "results/areas4_old.txt");

        System.out.println();
    }

    private static void runSecondGenerationTests() {

        System.out.println("Second Generation Partitioniing");
        System.out.println("*********************************\n");

        List<DataPoint> dataPoints = extractDataPointsFromFile("results/testData0.csv");
        DataPartitioner dataPartitioner = new DataPartitioner(dataPoints);
        dataPartitioner.findOptimalPartition("results/results.txt", "results/areas.txt");

        List<DataPoint> dataPoints1 = extractDataPointsFromFile("results/testData1.csv");
        DataPartitioner dataPartitioner1 = new DataPartitioner(dataPoints1);
        dataPartitioner1.findOptimalPartition("results/results1.txt", "results/areas1.txt");

        List<DataPoint> dataPoints2 = extractDataPointsFromFile("results/testData2.csv");
        DataPartitioner dataPartitioner2 = new DataPartitioner(dataPoints2);
        dataPartitioner2.findOptimalPartition("results/results2.txt", "results/areas2.txt");

        List<DataPoint> dataPoints3 = extractDataPointsFromFile("results/testData3.csv");
        DataPartitioner dataPartitioner3 = new DataPartitioner(dataPoints3);
        dataPartitioner3.findOptimalPartition("results/results3.txt", "results/areas3.txt");

        List<DataPoint> dataPoints4 = extractDataPointsFromFile("results/testData4.csv");
        DataPartitioner dataPartitioner4 = new DataPartitioner(dataPoints4);
        dataPartitioner4.findOptimalPartition("results/results4.txt", "results/areas4.txt");

        System.out.println();
    }

    // useful for debugging
    public static void checkDataPoints(List<DataPoint> dataPoints) {

        System.out.println("Dataset - size: " + dataPoints.size());
        for (DataPoint dataPoint : dataPoints) {

            System.out.println("   " + dataPoint.getVector()[0] + " , " + dataPoint.getVector()[1]);
        }
    }

    public static List<DataPoint> extractDataPointsFromFile(String file) {

        TestDataReader dataReader = new TestDataReader(file);
        return dataReader.getDataPoints();
    }
}
