package data;

import bachelorthesis.clustering.data.ArbitraryShape;
import bachelorthesis.clustering.data.DataGenerator;
import bachelorthesis.clustering.data.DataPoint;
import bachelorthesis.clustering.data.ShapeGenerator;
import bachelorthesis.clustering.fileIO.FileIO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SecondTestDataGenerator {

    private static DataGenerator generator;
    private static ShapeGenerator shapeGenerator;

    public static void main(String[] args) {

        //generateDataSetWithNearGaussianClusters();
        //generateDataSetWithOverlappingGaussianClusters();
        //generateDataSetWithArbitraryShapes();
        //generateDateSetWithHalfMoons();
        //generateDataSetWithEllipticalClusters();
        generateDataSetYinYang();
    }

    private static void generateDataSetYinYang() {

        for (int i = 1; i <= 10; ++i) {

            generateDataSetYinYang(5, i);
        }
        for (int i = 10; i <= 50; i+=5) {

            generateDataSetYinYang(i, 1);
        }
    }

    private static void generateDataSetYinYang(int dim, int factor) {

        double[] mean = new double[dim];
        List<DataPoint> dataPoints = new ArrayList<>();
        generator = new DataGenerator(dim);
        mean[0] = 52;
        mean[1] = 75;
        shapeGenerator = new ShapeGenerator(new ArbitraryShape(dim));
        shapeGenerator.createHalfMoon(25, mean);
        List<DataPoint> shapePoints = shapeGenerator.generateShape(200 * factor);
        for (DataPoint dp : shapePoints) {
            dp.setGroundTruth("1");
        }
        dataPoints.addAll(shapePoints);

        for (int i = 0; i < 100 * factor; ++i) {
            dataPoints.add(generator.generateDataPoint(mean,2.0, "2"));
        }

        mean[0] = 48;
        mean[1] = 25;
        shapeGenerator = new ShapeGenerator(new ArbitraryShape(dim));
        shapeGenerator.createDoubleMoonRight(mean, 25);
        shapePoints = shapeGenerator.generateShape(200 * factor);
        for (DataPoint dp : shapePoints) {
            dp.setGroundTruth("1");
        }
        dataPoints.addAll(shapePoints);

        for (int i = 0; i < 500 * factor; ++i) {
            dataPoints.add(generator.generateDataPoint(mean,2.0, "3"));
        }

        writeFile(dim, factor, dataPoints, "yinyang");
    }

    private static void generateDataSetWithNearGaussianClusters() {

        for (int i = 1; i <= 10; ++i) {

            generateDataSetWithNearGaussianClusters(5, i);
        }
        for (int i = 10; i <= 50; i += 5) {

            generateDataSetWithNearGaussianClusters(i, 1);
        }
    }

    private static void generateDataSetWithNearGaussianClusters(int dim, int factor) {

        double[] mean = new double[dim];
        List<DataPoint> dataPoints = new ArrayList<>();
        generator = new DataGenerator(dim);

        for (int i = 2; i < dim; ++i) {
            mean[i] = 1;
        }

        mean[0] = 15;
        mean[1] = 15;
        for (int i = 0; i < 300 * factor; ++i) {
            dataPoints.add(generator.generateDataPoint(mean, 5.0, "1"));
        }

        mean[0] = 25;
        mean[1] = 25;
        for (int i = 0; i < 300 * factor; ++i) {
            dataPoints.add(generator.generateDataPoint(mean,4.5, "2"));
        }

        mean[0] = 80;
        mean[1] = 20;
        for (int i = 0; i < 250 * factor; ++i) {
            dataPoints.add(generator.generateDataPoint(mean,2.0, "3"));
        }

        mean[0] = 50;
        mean[1] = 50;
        for (int i = 0; i < 150 * factor; ++i) {
            dataPoints.add(generator.generateDataPoint(mean,2.0, "4"));
        }

        writeFile(dim, factor, dataPoints, "near");
    }

    private static void generateDataSetWithOverlappingGaussianClusters() {

        for (int i = 1;i <= 10; ++i) {

            generateDataSetWithOverlappingGaussianClusters(5, i);
        }
        for (int i = 10; i <= 50; i += 5) {

            generateDataSetWithOverlappingGaussianClusters(i, 1);
        }
    }

    private static void generateDataSetWithOverlappingGaussianClusters(int dim, int factor) {

        double[] mean = new double[dim];
        List<DataPoint> dataPoints = new ArrayList<>();
        generator = new DataGenerator(dim);

        for (int i = 2; i < dim; ++i) {
            mean[i] = 1;
        }

        mean[0] = 15;
        mean[1] = 15;
        for (int i = 0; i < 300 * factor; ++i) {
            dataPoints.add(generator.generateDataPoint(mean, 5.0, "1"));
        }

        mean[0] = 20;
        mean[1] = 20;
        for (int i = 0; i < 300 * factor; ++i) {
            dataPoints.add(generator.generateDataPoint(mean,5.0, "2"));
        }

        mean[0] = 80;
        mean[1] = 20;
        for (int i = 0; i < 250 * factor; ++i) {
            dataPoints.add(generator.generateDataPoint(mean,2.0, "3"));
        }

        mean[0] = 50;
        mean[1] = 50;
        for (int i = 0; i < 150 * factor; ++i) {
            dataPoints.add(generator.generateDataPoint(mean,2.0, "4"));
        }

        writeFile(dim, factor, dataPoints, "over");
    }

    private static void generateDataSetWithArbitraryShapes() {

        for (int i = 1;i <= 10; ++i) {

            generateDataSetWithArbitraryShapes(5, i);
        }
        for (int i = 10; i <= 50; i += 5) {

            generateDataSetWithArbitraryShapes(i, 1);
        }
    }

    private static void generateDataSetWithArbitraryShapes(int dim, int factor) {

        double[] mean = new double[dim];
        List<DataPoint> dataPoints = new ArrayList<>();

        for (int i = 2; i < dim; ++i) {
            mean[i] = 1;
        }

        // create a circle
        mean[0] = 15;
        mean[1] = 15;
        shapeGenerator = new ShapeGenerator(new ArbitraryShape(dim));
        shapeGenerator.createCircle(10, mean);
        List<DataPoint> shapePoints = shapeGenerator.generateShape(300 * factor);
        for (DataPoint dp : shapePoints) {
            dp.setGroundTruth("1");
        }
        dataPoints.addAll(shapePoints);

        // create a half-moon
        mean[0] = 0;
        mean[1] = 30;
        shapeGenerator = new ShapeGenerator(new ArbitraryShape(dim));
        shapeGenerator.createHalfMoon(10, mean);
        List<DataPoint> shapePoints1 = shapeGenerator.generateShape(300 * factor);
        for (DataPoint dp : shapePoints1) {
            dp.setGroundTruth("2");
        }
        dataPoints.addAll(shapePoints1);

        // create a cirvle
        mean[0] = 80;
        mean[1] = 20;
        shapeGenerator = new ShapeGenerator(new ArbitraryShape(dim));
        shapeGenerator.createCircle(10, mean);
        List<DataPoint> shapePoints2 = shapeGenerator.generateShape(200 * factor);
        for (DataPoint dp : shapePoints2) {
            dp.setGroundTruth("3");
        }
        dataPoints.addAll(shapePoints2);

        // create a half-moon
        mean[0] = 50;
        mean[1] = 50;
        shapeGenerator = new ShapeGenerator(new ArbitraryShape(dim));
        shapeGenerator.createHalfMoon(10, mean);
        List<DataPoint> shapePoints3 = shapeGenerator.generateShape(200 * factor);
        for (DataPoint dp : shapePoints3) {
            dp.setGroundTruth("4");
        }
        dataPoints.addAll(shapePoints3);

        writeFile(dim, factor, dataPoints, "arbitrary");
    }

    private static void generateDateSetWithHalfMoons() {

        for (int i = 1;i <= 10; ++i) {

            generateDataSetWithHalfMoons(5, i);
        }
        for (int i = 10; i <= 50; i += 5) {

            generateDataSetWithHalfMoons(i, 1);
        }
    }

    private static void generateDataSetWithHalfMoons(int dim, int factor) {

        double[] mean = new double[dim];
        List<DataPoint> dataPoints = new ArrayList<>();

        for (int i = 2; i < dim; ++i) {
            mean[i] = 1;
        }

        mean[0] = 40;
        mean[1] = 50;
        shapeGenerator = new ShapeGenerator(new ArbitraryShape(dim));
        shapeGenerator.createDoubleMoonLeft(mean, 10);
        List<DataPoint> shapePoints = shapeGenerator.generateShape(300 * factor);
        for (DataPoint dp : shapePoints) {
            dp.setGroundTruth("1");
        }
        dataPoints.addAll(shapePoints);

        // create a half-moon
        mean[0] = 50;
        mean[1] = 35;
        shapeGenerator = new ShapeGenerator(new ArbitraryShape(dim));
        shapeGenerator.createDoubleMoonRight(mean, 10);
        List<DataPoint> shapePoints1 = shapeGenerator.generateShape(300 * factor);
        for (DataPoint dp : shapePoints1) {
            dp.setGroundTruth("2");
        }
        dataPoints.addAll(shapePoints1);

        // create a cirvle
        mean[0] = 40;
        mean[1] = 100;
        shapeGenerator = new ShapeGenerator(new ArbitraryShape(dim));
        shapeGenerator.createDoubleMoonLeft(mean, 10);
        List<DataPoint> shapePoints2 = shapeGenerator.generateShape(200 * factor);
        for (DataPoint dp : shapePoints2) {
            dp.setGroundTruth("3");
        }
        dataPoints.addAll(shapePoints2);

        // create a half-moon
        mean[0] = 80;
        mean[1] = 80;
        shapeGenerator = new ShapeGenerator(new ArbitraryShape(dim));
        shapeGenerator.createDoubleMoonRight(mean, 10);
        List<DataPoint> shapePoints3 = shapeGenerator.generateShape(200 * factor);
        for (DataPoint dp : shapePoints3) {
            dp.setGroundTruth("4");
        }
        dataPoints.addAll(shapePoints3);

        writeFile(dim, factor, dataPoints, "moons");
    }

    private static void writeFile(int dim, int factor, List<DataPoint> dataPoints, String description) {

        String filePath = "results/performanceTests/dim" + dim + "size" + factor + description + ".csv";
        try {
            FileIO.writeToCSV(dataPoints, filePath);
            System.out.println(filePath + " successfully written");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateDataSetWithEllipticalClusters() {

        for (int i = 1; i <= 10; ++i) {

            generateDataSetWithEllipticalClusters(5, i);
        }
        for (int i = 10; i <= 50; i += 5) {

            generateDataSetWithEllipticalClusters(i, 1);
        }
    }

    private static void generateDataSetWithEllipticalClusters(int dim, int factor) {

        double[] mean = new double[dim];
        List<DataPoint> dataPoints = new ArrayList<>();
        generator = new DataGenerator(dim);

        for (int i = 2; i < dim; ++i) {
            mean[i] = 1;
        }

        mean[0] = 15;
        mean[1] = 15;
        for (int i = 0; i < 300 * factor; ++i) {
            dataPoints.add(generator.generateEllipticalDataPoint(mean, 5.0, 1.0, "1"));
        }

        mean[0] = 25;
        mean[1] = 25;
        for (int i = 0; i < 300 * factor; ++i) {
            dataPoints.add(generator.generateEllipticalDataPoint(mean,0.5, 2.0, "2"));
        }

        mean[0] = 80;
        mean[1] = 20;
        for (int i = 0; i < 250 * factor; ++i) {
            dataPoints.add(generator.generateEllipticalDataPoint(mean,2.0, 1.0, "3"));
        }

        mean[0] = 50;
        mean[1] = 50;
        for (int i = 0; i < 150 * factor; ++i) {
            dataPoints.add(generator.generateEllipticalDataPoint(mean,2.0, 2.5, "4"));
        }

        writeFile(dim, factor, dataPoints, "elliptical");
    }
}
