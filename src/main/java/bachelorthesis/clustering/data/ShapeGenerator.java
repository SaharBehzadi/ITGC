package bachelorthesis.clustering.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShapeGenerator {

    private int dim;
    private ArbitraryShape shape;
    private DataGenerator generator;

    public ShapeGenerator(ArbitraryShape shape) {
        this.shape = shape;
        dim = shape.getDim();
        generator = new DataGenerator(dim);
    }

    public List<DataPoint> generateShape(int dataSetSize) {

        List<DataPoint> dataPoints = new ArrayList<>();
        int numberSegments = shape.getSegments().size();
        int modulus;
        Segment segment;
        Random random = new Random();
        for (int i = 0; i < dataSetSize; ++i) {

            //modulus = (i + numberSegments / 2) % numberSegments;
            //segment = shape.getSegment(modulus);
            segment = shape.getSegment(random.nextInt(numberSegments));
            dataPoints.add(generator.generateDataPoint(segment.getMean(), segment.getDeviation()));
        }
        return dataPoints;
    }

    public void createDoubleMoonLeft(double[] center, double radius) {

        double x = 0.0;
        double y = radius - 0.05;
        while (y > -radius + 0.05) {

            x = Math.sqrt(Math.pow(radius, 2.0) - Math.pow(y, 2.0));

            shape.getSegments().add(new Segment(getXandY(center, -x, y), 0.2));
            shape.getSegments().add(new Segment(getXandY(center, -x, -y), 0.2));

            y -= 0.05;
        }
    }

    public void createDoubleMoonRight(double[] center, double radius) {

        double x = 0.0;
        double y = radius - 0.05;
        while (y > -radius + 0.05) {

            x = Math.sqrt(Math.pow(radius, 2.0) - Math.pow(y, 2.0));

            shape.getSegments().add(new Segment(getXandY(center, x, y), 0.2));
            shape.getSegments().add(new Segment(getXandY(center, x, -y), 0.2));

            y -= 0.05;
        }
    }

    public void createHalfMoon(double radius, double[] center) {

        double x = 0.0;
        double y = radius - 0.05;
        while (y > -radius + 0.05) {

            x = Math.sqrt(Math.pow(radius, 2.0) - Math.pow(y, 2.0));

            shape.getSegments().add(new Segment(getXandY(center, -x, y), 0.1));
            shape.getSegments().add(new Segment(getXandY(center, -x, -y), 0.1));

            y -= 0.05;
        }
    }

    public void createCircle(double radius, double[] center) {

        double x = 0.0;
        double y = 0.0;
        double phi = 0.0;
        while (phi < 90) {
            //while (x < radius) {

            //y = Math.sqrt( Math.pow(radius, 2.0) - Math.pow(x, 2.0) );
            y = Math.sin(phi) * radius;
            x = Math.cos(phi) * radius;

            shape.getSegments().add(new Segment(getXandY(center, x, y), 0.1));
            shape.getSegments().add(new Segment(getXandY(center, -x, y), 0.1));
            shape.getSegments().add(new Segment(getXandY(center, x, -y), 0.1));
            shape.getSegments().add(new Segment(getXandY(center, -x, -y), 0.1));

            //x += 0.1;
            phi += 0.9;
        }
    }

    private static double[] getXandY(double[] center, double x, double y) {

        double[] vector = new double[center.length];

        vector[0] = center[0] + x;
        vector[1] = center[1] + y;

        for (int i = 2; i < center.length; ++i) {
            vector[i] = center[i];
        }

        return vector;
    }
}
