package bachelorthesis.clustering.fileIO;

import bachelorthesis.clustering.data.DataPoint;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvDataReader {

    private List<DataPoint> dataPoints;
    private int dim;

    public CsvDataReader(String csvFile, int dim) {

        this.dim = dim;
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
            //int i = 0;
            while((line = reader.readLine()) != null) {

                //System.out.println(++i);
                String[] data = line.split(splitter);
                double[] vectors = getVectors(data);
                String groundTruth = (String)data[dim];
                if (groundTruth.equals("0")) {
                    groundTruth = "100";
                }
                dataPoints.add(new DataPoint(dim, vectors, groundTruth));
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

        double[] vectors = new double[dim];
        for(int i = 0; i < dim; ++i) {

            vectors[i] = Double.parseDouble(data[i]);
        }
        return vectors;
    }

    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }
}
