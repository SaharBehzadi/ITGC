package bachelorthesis.clustering.statistics;

public class VectorMath {

    public static double vectorMean(double[] vector) {

        double sum = 0.0;
        for (int i = 0; i < vector.length; ++i) {

            sum += vector[i];
        }
        return sum / vector.length;
    }

    public static double vectorSum(double[] vector) {

        double sum = 0.0;
        for (int i = 0; i < vector.length; ++i) {

            sum += vector[i];
        }
        return sum;
    }

    public static void scalarVectorPow(double[] vector, double scalar) {

        for (int i = 0; i < vector.length; ++i) {

            vector[i] = Math.pow(vector[i], scalar);
        }
    }

    public static void fillVectorWithZeros(double[] vector) {

        for (int i = 0; i < vector.length; ++i) {

            vector[i] = 0.0;
        }
    }

    public static void scalarVectorSqrt(double[] vector) {

        for (int i = 0; i < vector.length; ++i) {

            vector[i] = Math.sqrt(vector[i]);
        }
    }

    public static void scalarVectorDivision(double[] vector, double scalar) {

        for (int i = 0; i < vector.length; ++i) {

            vector[i] /= scalar;
        }
    }
}
