package data;

import bachelorthesis.clustering.data.DataGenerator;
import bachelorthesis.clustering.data.DataPoint;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestDataGenerator {

    private static DataGenerator generator = new DataGenerator(2);

    public static void main(String[] args) {

        double[] mean = new double[2];
        ArrayList<DataPoint> dataPoints = new ArrayList<>();
        fillDataForTestData(dataPoints, mean);
        ArrayList<DataPoint> dataPoints1 = new ArrayList<>();
        fillDataForTestData1(dataPoints1, mean);
        ArrayList<DataPoint> dataPoints2 = new ArrayList<>();
        fillDataForTestData2(dataPoints2, mean);
        ArrayList<DataPoint> dataPoints3 = new ArrayList<>();
        fillDataForTestData3(dataPoints3, mean);
        ArrayList<DataPoint> dataPoints4 = new ArrayList<>();
        fillDataForTestData4(dataPoints4, mean);

        try {
            writeToCSV(dataPoints, "results/testData0.csv");
            System.out.println("testData0.csv written successfully");
            writeToCSV(dataPoints1, "results/testData1.csv");
            System.out.println("testData1.csv written successfully");
            writeToCSV(dataPoints2, "results/testData2.csv");
            System.out.println("testData2.csv written successfully");
            writeToCSV(dataPoints3, "results/testData3.csv");
            System.out.println("testData3.csv written successfully");
            writeToCSV(dataPoints4, "results/testData4.csv");
            System.out.println("testData4.csv written successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        // TODO removed 0s
    private static void fillDataForTestData(ArrayList<DataPoint> dataPoints, double[] mean) {

        mean[0] = 30;
        mean[1] = 70;
        for (int i = 0; i < 2000; ++i) {
            dataPoints.add(generator.generateDataPoint(mean,5.0, "1"));
        }

        mean[0] = 100;
        mean[1] = 80;
        for (int i = 0; i < 2000; ++i) {
            dataPoints.add(generator.generateDataPoint(mean,4.0, "2"));
        }
    }

    private static void fillDataForTestData1(ArrayList<DataPoint> dataPoints, double[] mean) {

        mean[0] = 15;
        mean[1] = 15;
        for (int i = 0; i < 1000; ++i) {
            dataPoints.add(generator.generateDataPoint(mean, 1.0, "1"));
        }

        mean[0] = 80;
        mean[1] = 20;
        for (int i = 0; i < 1000; ++i) {
            dataPoints.add(generator.generateDataPoint(mean,2.0, "2"));
        }
    }

    private static void fillDataForTestData2(ArrayList<DataPoint> dataPoints, double[] mean) {

        mean[0] = 15;
        mean[1] = 15;
        for (int i = 0; i < 1000; ++i) {
            dataPoints.add(generator.generateDataPoint(mean, 1.0, "1"));
        }

        mean[0] = 30;
        mean[1] = 70;
        for (int i = 0; i < 2000; ++i) {
            dataPoints.add(generator.generateDataPoint(mean,5.0, "2"));
        }

        mean[0] = 80;
        mean[1] = 20;
        for (int i = 0; i < 1000; ++i) {
            dataPoints.add(generator.generateDataPoint(mean,2.0, "3"));
        }
    }

    private static void fillDataForTestData3(ArrayList<DataPoint> dataPoints, double[] mean) {

        mean[0] = 15;
        mean[1] = 15;
        for (int i = 0; i < 1000; ++i) {
            dataPoints.add(generator.generateDataPoint(mean, 1.0, "1"));
        }

        mean[0] = 30;
        mean[1] = 70;
        for (int i = 0; i < 5000; ++i) {
            dataPoints.add(generator.generateDataPoint(mean,4.0, "2"));
        }

        mean[0] = 80;
        mean[1] = 20;
        for (int i = 0; i < 1000; ++i) {
            dataPoints.add(generator.generateDataPoint(mean,2.0, "3"));
        }

        mean[0] = 50;
        mean[1] = 50;
        for (int i = 0; i < 5000; ++i) {
            dataPoints.add(generator.generateDataPoint(mean,2.0, "4"));
        }
    }

    private static void fillDataForTestData4(ArrayList<DataPoint> dataPoints, double[] mean) {

        mean[0] = 50;
        mean[1] = 50;
        for (int i = 0; i < 500; ++i) {
            dataPoints.add(generator.generateDataPoint(mean,2.0, "1"));
        }

        mean[0] = 0;
        mean[1] = 0;
        for (int i = 0; i < 2000; ++i) {
            dataPoints.add(generator.generateDataPoint(mean,6.0, "2"));
        }
    }

    // thanks to: https://www.mkyong.com/java/how-to-export-data-to-csv-file-java/
    private static void writeToCSV(List<DataPoint> dataPoints, String csvFile) throws IOException {

        FileWriter writer = new FileWriter(csvFile);

        StringBuilder stringBuilder = new StringBuilder();
        for (DataPoint dataPoint : dataPoints) {

            boolean first = true;
            for (int i = 0; i < dataPoint.getDim(); ++i) {
                if (!first) {
                    stringBuilder.append(',');
                }
                first = false;
                stringBuilder.append(dataPoint.getVector()[i]);
            }
            stringBuilder.append(',');
            stringBuilder.append(dataPoint.getGroundTruth());
            stringBuilder.append("\n");
        }
        writer.append(stringBuilder.toString());

        writer.flush();
        writer.close();
    }
}
