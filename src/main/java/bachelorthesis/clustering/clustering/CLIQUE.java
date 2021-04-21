package bachelorthesis.clustering.clustering;

import bachelorthesis.clustering.data.DataPoint;
import bachelorthesis.clustering.grid.Cell;
import bachelorthesis.clustering.grid.Cluster;
import bachelorthesis.clustering.grid.HigherDimGrid;

import java.util.ArrayList;
import java.util.List;

public class CLIQUE {

    private HigherDimGrid grid;
    private List<DataPoint> dataPoints;
    private Cluster noise;
    private List<Cluster> clusters;

    public CLIQUE(List<DataPoint> dataPoints, int k) {
        this.dataPoints = dataPoints;
        noise = new Cluster();
        grid = new HigherDimGrid(k, dataPoints);
        clusters = new ArrayList<>();
    }

    public void performCliqueAlgorithm(int minPoints) {

        List<Cluster> clusters = grid.getClusters();
        int index = 0;
        while(index < clusters.size()) {
            Cluster cluster = clusters.get(index);
            if (countClusterPoints(cluster) < minPoints) {
                noise.mergeClusters(cluster);
                clusters.remove(cluster);
                --index;
            }
            while(cluster.getNeighbors().size() != 0) {
                Cluster merger = cluster.getNeighbors().iterator().next();
                if (merger.getN() > minPoints) {
                    grid.mergeClusters(cluster, merger);
                } else {
                    //noise.add(merger);
                    cluster.removeNeighbor(merger);
                }
            }
            //System.out.println(index);
            index++;
        }
        clusters = grid.getClusters();
        clusters.add(noise);
    }

    private int countClusterPoints(Cluster cluster) {

        int sum = 0;
        for (Cell cell : cluster.getClusterCells()) {
            sum += cell.getDataPoints().size();
        }
        return sum;
    }

    public void assignClusterIds() {

        int i = 1;
        for (Cluster cluster : grid.getClusters()) {
            for (DataPoint dp : cluster.getDataPoints()) {
                dp.setCluster("" + i);
            }
            ++i;
        }
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }

    public HigherDimGrid getGrid() {
        return grid;
    }
}
