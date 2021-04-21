package bachelorthesis.clustering;

import bachelorthesis.clustering.fileIO.FileIO;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NMIresultsCollector {

    private static List<Double> mdlResults = new ArrayList<>();
    private static List<Double> kmeansResults = new ArrayList<>();
    private static List<Double> DBSCANresults = new ArrayList<>();
    private static List<Double> hierarchicalResults = new ArrayList<>();

    private static final String directory = "results/performanceTests/results";

    public static void main(String[] args) {

        collectResultsOne();
        collectResultsTwo();
    }

    private static void writeResultsToFile(String filename, int length) {

        String file = directory + "/" + filename;
        StringBuilder builder = new StringBuilder();
        try {
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < length; ++i) {
                builder.append(i+1);
                builder.append("   ");
                builder.append(mdlResults.get(i));
                builder.append("   ");
                builder.append(kmeansResults.get(i));
                builder.append("   ");
                builder.append(DBSCANresults.get(i));
                builder.append("   ");
                builder.append(hierarchicalResults.get(i));
                builder.append("\n");
            }
            writer.append(builder);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void collectResultsTwo() {

        for (int i = 5; i <= 50; i+=5) {
            getNMIresults("near", i, 1);
        }
        writeResultsToFile("secondSerieNear.txt", 10);
        clearLists();
        for (int i = 5; i <= 50; i+=5) {
            getNMIresults("over", i, 1);
        }
        writeResultsToFile("secondSerieOver.txt", 10);
        clearLists();
        for (int i = 5; i <= 50; i+=5) {
            getNMIresults("arbitrary", i, 1);
        }
        writeResultsToFile("secondSerieArbitrary.txt", 10);
        clearLists();
        for (int i = 5; i <= 50; i+=5) {
            getNMIresults("moons", i, 1);
        }
        writeResultsToFile("secondSerieMoons.txt", 10);
        clearLists();
        for (int i = 5; i <= 50; i+=5) {
            getNMIresults("elliptical", i, 1);
        }
        writeResultsToFile("secondSerieElliptical.txt", 10);
        clearLists();
    }

    private static void getNMIresults(String type, int dim, int factor) {

        getNMIresult("/MDL", "mdl", type, dim, factor, mdlResults);
        getNMIresult("/Kmean", "kmean", type, dim, factor, kmeansResults);
        getNMIresult("/DBSCAN", "dbscan", type, dim, factor, DBSCANresults);
        getNMIresult("/hier", "hier", type, dim, factor, hierarchicalResults);
    }

    private static void clearLists() {

        mdlResults.clear();
        kmeansResults.clear();
        DBSCANresults.clear();
        hierarchicalResults.clear();
    }

    private static void collectResultsOne() {

        for (int i = 1; i <= 10; ++i) {
            getNMIresults("near", 5, i);
        }
        writeResultsToFile("firstSerieNear.txt", 10);
        clearLists();
        for (int i = 1; i <= 10; ++i) {
            getNMIresults("over", 5, i);
        }
        writeResultsToFile("firstSerieOver.txt", 10);
        clearLists();
        for (int i = 1; i <= 10; ++i) {
            getNMIresults("arbitrary", 5, i);
        }
        writeResultsToFile("firstSerieArbitrary.txt", 10);
        clearLists();
        for (int i = 1; i <= 10; ++i) {
            getNMIresults("moons", 5, i);
        }
        writeResultsToFile("firstSerieMoons.txt", 10);
        clearLists();
        for (int i = 1; i <= 10; ++i) {
            getNMIresults("elliptical", 5, i);
        }
        writeResultsToFile("firstSerieElliptical.txt", 10);
        clearLists();
    }

    private static void getNMIresult(String dir, String algorithm, String type, int dim, int factor, List<Double> resultList) {

        String fileName = directory + dir + "/results" + "/compare_";
        fileName += algorithm + "_" + type + "_dim" + dim;
        fileName += "size" + factor;
        fileName += "x_results.txt";

        String[] results;
        try {
            results = FileIO.readNMIcsvData(fileName);
            resultList.add(Double.parseDouble(results[0]));
        } catch (IOException e) {
            resultList.add(Double.NaN);
        }
    }
}
