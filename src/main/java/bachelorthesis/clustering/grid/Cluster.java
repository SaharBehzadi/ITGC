package bachelorthesis.clustering.grid;

import bachelorthesis.clustering.data.DataPoint;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Cluster {          // TODO clean it up

    private Set<Cell> clusterCells;
    private Set<Cluster> neighbors;
    private String name;
    private boolean converged;
    private int n;  // size of the whole dataset

    public Cluster() {

        clusterCells = new LinkedHashSet<>();
        neighbors = new LinkedHashSet<>();
        setConverged(false);
    }

    public Cluster(Cell clusterCell) {

        clusterCells = new LinkedHashSet<>();
        clusterCells.add(clusterCell);
        setConverged(false);
    }

    public Cluster(Set<Cell> clusterCells, Set<Cluster> neighbors) {

        setClusterCells(clusterCells);
        setNeighbors(neighbors);
        setConverged(false);
    }

    public Cluster(Cell clusterCell, Set<Cluster> neighbors) {

        clusterCells = new LinkedHashSet<>();
        clusterCells.add(clusterCell);
        setNeighbors(neighbors);
        setConverged(false);
    }

    public boolean isConverged() {
        return converged;
    }

    public void setConverged(boolean converged) {
        this.converged = converged;
    }

    public Set<Cell> getClusterCells() {
        return clusterCells;
    }

    public void setClusterCells(Set<Cell> clusterCells) {
        this.clusterCells = clusterCells;
    }

    public Set<Cluster> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(Set<Cluster> neighbors) {
        this.neighbors = neighbors;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DataPoint> getDataPoints() {

        List<DataPoint> dataPoints = new ArrayList<>();
        for (Cell cell : clusterCells) {
            dataPoints.addAll(cell.getDataPoints());
        }
        return dataPoints;
    }

    public boolean removeNeighbor(Cluster neighbor) {

        return neighbors.remove(neighbor);
    }

    private boolean removeNeighbors(Set<Cluster> neighbor) {

        return neighbors.removeAll(neighbor);
    }

    private boolean addNeighbors(Set<Cluster> neighbors) {

        return this.neighbors.addAll(neighbors);
    }

    public boolean addNeighbor(Cluster neighbor) {

        return neighbors.add(neighbor);
    }

    private boolean addClusterCells(Cluster cluster) {

        return clusterCells.addAll(cluster.getClusterCells());
    }

    /*private boolean addParts(Cluster cluster) {

        return parts.addAll(cluster.getParts());
    }*/

    // the merger will be removed, this has to be done outside of the Cluster.class
    public void mergeClusters(Cluster merger) {     // TODO clean it up

        //debugMerging("Neighbors before merging:", merger);

        // first the merging clusters remove themselves from the neighborhood
        merger.removeNeighbor(this);
        removeNeighbor(merger);

        // assign the neighbors
        merger.addNeighbors(getNeighbors());
        addNeighbors(merger.getNeighbors());
        //System.out.println("\nthis:");
        for (Cluster neighbor : neighbors) {
            neighbor.addNeighbor(merger);
            //System.out.println(neighbor.getName());
            //neighbor.printNeighbors();
        }
        //System.out.println("merger:");
        for (Cluster neighbor : merger.getNeighbors()) {
            neighbor.addNeighbor(this);
            //System.out.println(neighbor.getName());
            //neighbor.printNeighbors();
        }
        //System.out.println("\n");

        //merger.removeNeighbors(getParts());
        //System.out.println(getName() + " " + getParts() + "  " + this.getParts());
        /*for (Cluster c : getParts()) {
            System.out.println("parts: " + c.getName());
        }*/
        //removeNeighbors(merger.getParts());

        //merger.addNeighbors(neighbors);
        //addNeighbors(merger.getNeighbors());

        //merger.addParts(this);
        //addParts(merger);

        //System.out.println("merger, neighbors: " + merger.getNeighbors().size());
        //System.out.println("neighbors: " + getNeighbors().size());

        addClusterCells(merger);
        merger.addClusterCells(this);   // is this really necessary

        //debugMerging("Neighbors after merging:", merger);
    }

    public double calculateCodingCost() {

        List<DataPoint> dataPointList = new ArrayList<>();
        int dim = clusterCells.iterator().next().getDim();
        double[] center = new double[dim];
        int numberCells = 0;

        fillArrayWithZeros(center, dim);
        numberCells = getNumberOfCellsAndStoreInformation(this, dataPointList, center, dim);
        divideArrayByScalarInteger(center, dim, numberCells);

        Cell dataStore = new Cell(dataPointList, dim, center);
        return dataStore.calculateCodingCost();
    }

    public double calculateParameterCost() {

        double sum = 0.0;
        for (Cell cell : clusterCells) {
            sum += cell.getDataPoints().size();
        }
        //System.out.println("parameter cost: " + (clusterCells.iterator().next().getDim() * Math.log(sum) / Math.log(2.0)));
        return clusterCells.iterator().next().getDim() * Math.log(sum) / Math.log(2.0);
    }

    public double calculateIDCost() {

        double sum = 0.0;
        for (Cell cell : clusterCells) {
            sum += cell.getDataPoints().size();
        }
        //System.out.println("ID cost: " + (Math.log(n / sum)));
        return Math.log(n / sum);
    }

    public double calculateCodingCostBeforeMerging(Cluster merger) {

        List<DataPoint> dataPointList = new ArrayList<>();
        int dim = clusterCells.iterator().next().getDim();
        double[] center = new double[dim];
        int numberCells = 0;

        fillArrayWithZeros(center, dim);
        numberCells += getNumberOfCellsAndStoreInformation(this, dataPointList, center, dim);
        numberCells += getNumberOfCellsAndStoreInformation(merger, dataPointList, center, dim);
        divideArrayByScalarInteger(center, dim, numberCells);

        Cell dataStore = new Cell(dataPointList, dim, center);
        return dataStore.calculateCodingCost();
    }

    private int getNumberOfCellsAndStoreInformation(Cluster cluster, List<DataPoint> dataPointList, double[] center, int dim) {

        int numberCells = 0;
        for (Cell cell : cluster.getClusterCells()) {

            dataPointList.addAll(cell.getDataPoints());
            for (int i = 0; i < dim; ++i) {
                center[i] += cell.getCenter()[i];
            }
            numberCells++;
        }
        return numberCells;
    }

    private void divideArrayByScalarInteger(double[] array, int dim, int divisor) {

        for (int i = 0; i < dim; ++i) {
            array[i] /= divisor;
        }
    }

    private void fillArrayWithZeros(double[] array, int dim) {

        for (int i = 0; i < dim; ++i) {
            array[i] = 0.0;
        }
    }

    public int getNumberOfDataPointsInCluster() {
        int sum = 0;
        for (Cell cell : clusterCells) {
            sum += cell.getDataPoints().size();
        }
        return sum;
    }

    /*******************************
     * functions for debugging
     *******************************/

    private void debugMerging(String description, Cluster merger) {

        System.out.println(description);
        System.out.println("Cluster:  " + getName());
        printNeighbors();
        System.out.println("Merger:   " + merger.getName());
        merger.printNeighbors();
    }

    public void printNeighbors() {

        System.out.println("neighbors:    " + getName());
        for (Cluster neighbor : neighbors) {
            System.out.println("    " + neighbor.getName());
            /*for (Cluster part : neighbor.getParts()) {
                System.out.print("    " + part.getName());
            }
            System.out.println();*/
        }
    }
}
