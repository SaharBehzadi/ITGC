package data;

import bachelorthesis.clustering.data.DataPoint;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestDataReader {

    private List<DataPoint> dataPoints;

    public TestDataReader(String csvFile) {

        dataPoints = new ArrayList<>();
        try {
            parseData(csvFile);
        } catch (IOException e) {
            System.out.println("Could not read the data, IOException");
            e.printStackTrace();
        }
    }

    private void parseData(String csvFile) throws IOException {

        String line = "";
        String splitter = ",";
        BufferedReader reader = null;

        try {

            reader = new BufferedReader(new FileReader(csvFile));
            while((line = reader.readLine()) != null) {

                String[] data = line.split(splitter);
                double[] vectors = getVectors(data);
                String groundTruth = (String)data[2];
                dataPoints.add(new DataPoint(2, vectors, groundTruth));
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

    private double[] getVectors(String[] data) {

        double[] vectors = new double[2];
        for(int i = 0; i < 2; ++i) {

            vectors[i] = Double.parseDouble(data[i]);
        }
        return vectors;
    }

    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }
}
