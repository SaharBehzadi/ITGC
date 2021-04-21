package bachelorthesis.clustering.fileIO;

import bachelorthesis.clustering.data.DataPoint;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileIO {

    public static String[] readNMIcsvData(String filename) throws IOException {

        String[] data = null;
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String nmiResult = reader.readLine();
        data = nmiResult.split(",");
        return data;
    }

    public static void writeNMIFiles(List<DataPoint> dataPoints, String directory, String fileName, int index, String ending) throws IOException {

        writeNMIFiles(dataPoints, directory + fileName + index, ending);
    }

    public static void writeNMIFiles(List<DataPoint> dataPoints, String directory, String fileName, String ending) throws IOException {

        writeNMIFiles(dataPoints, directory + fileName, ending);
    }

    public static void writeNMIFiles(List<DataPoint> dataPoints, String fullFileName, String ending) throws IOException {

        FileWriter writerGroundTruth = new FileWriter(fullFileName + "_truth" + ending);
        FileWriter writerCluster = new FileWriter(fullFileName + "_results" + ending);

        StringBuilder stringBuilderGroundTruth = new StringBuilder();
        StringBuilder stringBuilderCluster = new StringBuilder();

        for (DataPoint dataPoint : dataPoints) {

            stringBuilderGroundTruth.append(dataPoint.getGroundTruth() + " ");
            stringBuilderCluster.append(dataPoint.getCluster() + " ");
        }

        writerGroundTruth.append(stringBuilderGroundTruth);
        writerCluster.append(stringBuilderCluster);

        writerGroundTruth.flush();
        writerCluster.flush();
        writerGroundTruth.close();
        writerCluster.close();
    }

    // thanks to: https://www.mkyong.com/java/how-to-export-data-to-csv-file-java/
    public static void writeToCSV(List<DataPoint> dataPoints, String csvFile) throws IOException {

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

    private static List<DataPoint> dataPoints;

    public static List<DataPoint> extractDataPointsFromFile(String file) {

        dataPoints = new ArrayList<>();
        try {
            parseData(file);
        } catch (IOException e) {
            System.out.println("Could not read the data, IOException");
            e.printStackTrace();
        }
        return dataPoints;
    }

    private static void parseData(String csvFile) throws IOException {

        String line = "";
        String splitter = ",";
        BufferedReader reader = null;

        try {

            reader = new BufferedReader(new FileReader(csvFile));
            while((line = reader.readLine()) != null) {

                String[] data = line.split(splitter);
                double[] vectors = getVectors(data);
                String groundTruth = (String)data[data.length-1];
                //System.out.println("data.length   " + data.length);
                dataPoints.add(new DataPoint(data.length-1, vectors, groundTruth));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static double[] getVectors(String[] data) {

        double[] vectors = new double[data.length-1];
        for(int i = 0; i < data.length-1; ++i) {

            vectors[i] = Double.parseDouble(data[i]);
        }
        return vectors;
    }
}
