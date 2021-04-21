package bachelorthesis.clustering.clustering;

import bachelorthesis.clustering.data.DataPoint;

import java.util.ArrayList;
import java.util.List;

public class ClusterDBSCAN {

    private List<DataPoint> dataPoints;

    public ClusterDBSCAN() {

        dataPoints = new ArrayList<>();
    }

    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(List<DataPoint> dataPoints) {
        this.dataPoints = dataPoints;
    }

    public void addDataPoint(DataPoint dataPoint) {

        dataPoints.add(dataPoint);
    }

    public void addAllDataPoints(List<DataPoint> dataPoints) {

        this.dataPoints.addAll(dataPoints);
    }
}
