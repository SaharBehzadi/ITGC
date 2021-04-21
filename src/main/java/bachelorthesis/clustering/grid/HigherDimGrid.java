package bachelorthesis.clustering.grid;

import bachelorthesis.clustering.charts.DataChartAlternateDesign;
import bachelorthesis.clustering.data.DataPoint;
import org.jfree.ui.RefineryUtilities;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class HigherDimGrid implements StatsObj {

    private int dim;
    private int k;
    private Cell[] cells;
    private List<DataPoint> dataPoints;
    private List<Cluster> clusters;
    private double[][] domain;

    private double mean;
    private double deviation;

    public HigherDimGrid(int k, List<DataPoint> dataPoints) {

        setDim(dataPoints.get(0).getDim());
        setK(k);
        setDataPoints(dataPoints);
        cells = new Cell[calcNumberCells()];
        clusters = new ArrayList<>();
        defineDomain();
        setupCells();
        setupClusters();
    }

    public double[][] getDomain() {
        return domain;
    }

    public void setDomain(double[][] domain) {
        this.domain = domain;
    }

    public void setDataPoints(List<DataPoint> dataPoints) {
        this.dataPoints = dataPoints;
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getK() {
        return k;
    }

    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }

    public void setDim(int dim) {
        this.dim = dim;
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    private int calcNumberCells() {
        return (int) Math.pow(k, dim);
    }

    public void setupClusters() {

        Cluster[] clusterArray = new Cluster[cells.length];
        for (int i = 0; i < cells.length; ++i) {
            if (cells[i] != null & cells[i].getDataPoints().size() > 0) {

                clusterArray[i] = new Cluster(cells[i]);
            }
        }
        for (int i = 0; i < cells.length; ++i) {

            if (cells[i].getDataPoints().size() > 0) {
                clusterArray[i].setNeighbors(assignNeighbors(i, clusterArray));
                clusterArray[i].setN(dataPoints.size());
                clusters.add(clusterArray[i]);
            }
        }
    }

    private Set<Cluster> assignNeighbors(int index, Cluster[] clusterArray) {

        Set<Cluster> neighbors = new LinkedHashSet<>();

        //System.out.println("index: " + index);
        //System.out.print("neighbors:  ");
        for (int i = 0; i < dim; ++i) {

            //System.out.println("Index: " + i + "    " + Math.pow(k, i));
            //System.out.println("Length: " + clusterArray.length);
            int offset = (int) Math.pow(k, i);
            int lowerNeighborIndex = index - offset;
            int upperNeighborIndex = index + offset;
            //System.out.println("Neighbor indexes: ");
            //System.out.println(lowerNeighborIndex);
            //System.out.println(upperNeighborIndex);
            if (lowerNeighborIndex >= 0 && clusterArray[lowerNeighborIndex] != null && ((index - index % offset) % (k * offset)) != 0) {
                neighbors.add(clusterArray[lowerNeighborIndex]);
                //System.out.print(lowerNeighborIndex + "  ");
            }
            if (upperNeighborIndex < (int) Math.pow(k, dim) && clusterArray[upperNeighborIndex] != null && ((upperNeighborIndex - upperNeighborIndex % offset) % (k * offset)) != 0) {
                neighbors.add(clusterArray[upperNeighborIndex]);
                //System.out.print(upperNeighborIndex + "  ");
            }
        }
        //System.out.println();
        return neighbors;
    }

    private void defineDomain() {

        defineInitialDomain();
        for (DataPoint dataPoint : dataPoints) {
            for (int i = 0; i < dim; ++i) {

                if (dataPoint.getVector()[i] < domain[i][0]) {
                    domain[i][0] = dataPoint.getVector()[i];
                } else if (dataPoint.getVector()[i] > domain[i][1]) {
                    domain[i][1] = dataPoint.getVector()[i];
                }
            }
        }
        for (int i = 0; i < dim; ++i) {

            domain[i][0] -= 10;
            domain[i][1] += 10;
        }
    }

    private void defineInitialDomain() {

        domain = new double[dim][2];
        for (int i = 0; i < dim; ++i) {
            domain[i][0] = dataPoints.get(0).getVector()[i];
            domain[i][1] = dataPoints.get(0).getVector()[i];
        }
    }

    private void calcCellCenter(double[] cellCenter, double[] cellLength, int index) {

        for (int i = 0; i < dim; ++i) {

            int factor = index % k;
            cellCenter[i] = domain[i][0] + factor * cellLength[i] + cellLength[i] / 2;
            index = index / k;
        }
    }

    public void setupCells() {

        double[] cellLengths = new double[dim];
        for (int i = 0; i < dim; ++i) {
            cellLengths[i] = (domain[i][1] - domain[i][0]) / k;
        }
        for (int i = 0; i < cells.length; ++i) {

            double cellCenter[] = new double[dim];
            calcCellCenter(cellCenter, cellLengths, i);
            cells[i] = new Cell(new ArrayList<>(), dim, cellCenter);
        }

        assignDataPointsToCells(cellLengths);
        calculateCellsDeviationAndMean();
    }

    private void calculateCellsDeviationAndMean() {

        for (int i = 0; i < cells.length; ++i) {

            cells[i].calculateDeviationAndMean();
            cells[i].calculateMuAndSigma();
        }
    }

    private int resolveIndex(DataPoint dataPoint, double[] cellLengths) {

        int index = 0;
        for (int i = 0; i < dim; ++i) {

            int temp = (int) ((dataPoint.getVector()[i] - domain[i][0]) / cellLengths[i]);
            index += (temp * Math.pow(k, i));
        }
        return index;
    }

    private void assignDataPointsToCells(double[] cellLengths) {

        for (DataPoint dataPoint : dataPoints) {

            int index = resolveIndex(dataPoint, cellLengths);
            cells[index].getDataPoints().add(dataPoint);
        }
    }

    private void calculateMean() {

        double sum = 0.0;
        for (int i = 0; i < cells.length; ++i) {

            sum += cells[i].getMean();
        }
        mean = sum / cells.length;
    }

    @Override
    public void calculateDeviationAndMean() {

        calculateMean();
        double sum = 0.0;
        for (int i = 0; i < cells.length; ++i) {

            double partDiff = cells[i].getMean() - mean;
            sum += Math.pow(partDiff, 2.0);
        }
        deviation = Math.sqrt(sum / cells.length);
    }

    @Override
    public double calculateCV() {
        return 0;
    }

    public void performClustering(boolean debug) {

        double actualCost = 0.0;
        double costBeforeMerging = 0.0;
        int index = 0;
        List<Cluster> notConvergedClusters = new ArrayList<>();
        List<Cluster> convergedClusters = new ArrayList<>();
        do {
            //System.out.println("   cost: " + calculateCodingCost());
            getNotConvergedClusters(notConvergedClusters);
            if (notConvergedClusters.size() <= 0) {
                break;
            }
            /*
                3 strategies:
                    get a random index
                    start with a constant index (0)
                    get the index of the cluster with the most datapoints
             */
            //index = (int) (Math.random() * notConvergedClusters.size());
            index = getClusterIndexWithMostDataPoints(notConvergedClusters);

            if (debug) {
                System.out.println("Index: " + index);
            }
            mergeWithNeighbors(notConvergedClusters.get(index), convergedClusters, debug);
        } while (notConvergedClusters.size() > 0);
        //clusters = convergedClusters;
        assignClusterID();
    }

    private void mergeWithNeighbors(Cluster cluster, List<Cluster> convergedClusters, boolean debug) {

        double actualCost = 0.0;
        double costBeforeMerging = 0.0;
        List<Cluster> neighbors = new ArrayList<>();
        int neighborIndex = 0;
        int iteration = 0;
        while(true) {
            if (debug) {
                System.out.println("Iteration " + iteration);
                //System.out.println("   cost, before: " + calculateCodingCost());
                DataChartAlternateDesign chart = new DataChartAlternateDesign("Iteration " + iteration++, this);
                chart.pack();
                RefineryUtilities.centerFrameOnScreen(chart);
                chart.setVisible(true);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            neighbors.addAll(cluster.getNeighbors());
            if (neighborIndex >= neighbors.size()) {
                break;
            }
            //System.out.println("neighbors size: " + neighbors.size());
            Cluster merger = neighbors.get(neighborIndex);
            //actualCost = cluster.calculateCodingCost() + merger.calculateCodingCost();
            //actualCost = calculateCodingCost();
            actualCost = calculateMDL();
            //costBeforeMerging = cluster.calculateCodingCostBeforeMerging(merger);
            //costBeforeMerging = calculateCodingCostBeforeMerging(cluster, merger);
            costBeforeMerging = calculateMDLBeforeMerging(cluster, merger);
            if (debug) {
                System.out.println("   actual cost " + actualCost);
                System.out.println("   mergin cost " + costBeforeMerging);
            }
            /*if (Double.isNaN(actualCost)) { // just an experiment
                mergeClusters(cluster, neighbors.get(neighborIndex));
            }*/
            if (costBeforeMerging < actualCost || Double.isNaN(actualCost) || merger.getClusterCells().iterator().next().getDataPoints().size() == 1) {

                mergeClusters(cluster, neighbors.get(neighborIndex));
            } else {

                ++neighborIndex;
            }
            //System.out.println("ni: " + neighborIndex + "  ns: " + neighbors.size());
            neighbors.clear();
            if (debug) {
                //System.out.println("   cost, after: " + calculateMDL());
            }
        }
        //System.out.println("End");
        cluster.setConverged(true);
        convergedClusters.add(cluster);
    }

    private double calculateMDL() {

        double para = 0.0;
        double id = 0.0;
        double mdl = 0.0;
        double codingCost = 0.0;
        mdl += calculateCodingCost();
        //codingCost = calculateCodingCost();
        int i = 0;
        for (Cluster cluster : clusters) {
            mdl += cluster.calculateParameterCost();
            mdl += cluster.calculateIDCost();
            para += cluster.calculateParameterCost();
            id += cluster.calculateIDCost();
            //System.out.println("cluster " + i++ + "; " + cluster.calculateCodingCost());
        }
        /*System.out.println("mdl: " + mdl);
        System.out.println("cc: " + codingCost);
        System.out.println(" actual: " + para + " . " + id);*/  // TODO debug output
        return mdl;
    }

    private void assignClusterID() {

        for (int i = 0; i < clusters.size(); ++i) {

            for (DataPoint dataPoint : clusters.get(i).getDataPoints()) {
                dataPoint.setCluster("" + (i+1));
            }
        }
    }

    private int getClusterIndexWithMostDataPoints(List<Cluster> notConvergedClusters) {
        int index = 0;
        for (int i = 1; i < notConvergedClusters.size(); ++i) {
            if (notConvergedClusters.get(index).getNumberOfDataPointsInCluster() < notConvergedClusters.get(i).getNumberOfDataPointsInCluster()) {
                index = i;
            }
        }
        return index;
    }

    private void getNotConvergedClusters(List<Cluster> notConvergedClusters) {

        notConvergedClusters.clear();
        for (Cluster cluster : clusters) {
            if (!cluster.isConverged()) {

                notConvergedClusters.add(cluster);
            }
        }
    }

    public double calculateCodingCost() {

        //System.out.println("calculateCodingCost");
        double cost = 0.0;
        for (Cluster cluster : clusters) {

            //System.out.println("cells: " + cluster.getClusterCells().size());
            /*for (Cell cell : cluster.getClusterCells()) {
                //System.out.println("   " + cell.getDataPoints().size());
            }*/
            cost += cluster.calculateCodingCost();
        }
        return cost;
    }

    public double calculateMDLBeforeMerging(Cluster c, Cluster merger) {

        double para = 0.0;
        double id = 0.0;
        double mdl = 0.0;
        double cc = 0.0;
        int i = 0;
        mdl += calculateCodingCostBeforeMerging(c, merger);
        for (Cluster cluster : clusters) {

            if (!cluster.equals(c) && !cluster.equals(merger)) {
                mdl += cluster.calculateParameterCost();
                mdl += cluster.calculateIDCost();
                para += cluster.calculateParameterCost();
                id += cluster.calculateIDCost();
                //System.out.println("cc" + i++ + ": " + cluster.calculateCodingCost());
            }
        }
        //System.out.println("mdl: " + mdl);
        Cluster mergeCandidate = new Cluster();
        mergeCandidate.setN(dataPoints.size());
        mergeCandidate.getClusterCells().addAll(c.getClusterCells());
        mergeCandidate.getClusterCells().addAll(merger.getClusterCells());
        mdl += mergeCandidate.calculateParameterCost();
        mdl += mergeCandidate.calculateIDCost();
        para += mergeCandidate.calculateParameterCost();
        id += mergeCandidate.calculateIDCost();
        /*System.out.println("cc" + i++ + ": " + mergeCandidate.calculateCodingCost());
        System.out.println("mdl: " + mdl);
        System.out.println("before merge: " + para + " . " + id);*/
        return mdl;
    }

    public double calculateCodingCostBeforeMerging(Cluster c, Cluster merger) {

        //System.out.println("calculateCodingCost");
        double cost = 0.0;
        for (Cluster cluster : clusters) {

            //System.out.println("cells: " + cluster.getClusterCells().size());
            for (Cell cell : cluster.getClusterCells()) {
                //System.out.println("   " + cell.getDataPoints().size());
            }
            if (!cluster.equals(c) && !cluster.equals(merger)) {
                cost += cluster.calculateCodingCost();
            } else if (cluster.equals(c)) {
                cost += cluster.calculateCodingCostBeforeMerging(merger);
            }
        }
        return cost;
    }

    public void mergeClusters(Cluster cluster, Cluster merger) {

        cluster.mergeClusters(merger);
        for (Cluster clus : clusters) {
            clus.removeNeighbor(merger);
        }
        clusters.remove(merger);
    }
}