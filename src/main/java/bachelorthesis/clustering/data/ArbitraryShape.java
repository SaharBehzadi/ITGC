package bachelorthesis.clustering.data;

import java.util.ArrayList;
import java.util.List;

public class ArbitraryShape {

    private int dim;
    private List<Segment> segments;

    public ArbitraryShape(int dim) {
        this.dim = dim;
        segments = new ArrayList<>();
    }

    public int getDim() {
        return dim;
    }

    public void setDim(int dim) {
        this.dim = dim;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    public boolean addSegment(Segment segment) {
        if (segment.getMean().length == dim) {
            return segments.add(segment);
        }
        return false;
    }

    public Segment getSegment(int index) {

        return segments.get(index);
    }

    public void clearSegments() {
        segments.clear();
    }
}
