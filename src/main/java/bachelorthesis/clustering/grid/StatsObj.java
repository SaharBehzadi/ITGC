package bachelorthesis.clustering.grid;

public interface StatsObj {

    /*
        Before calculating the deviation, the mean has to be calculated.
        Therefore it makes sense to calculate both in the same function.

        This interface also implies the implementation of another function:
        calculateMean(), which can be declared private.
     */

    // private void calculateMean();
    void calculateDeviationAndMean();
    double calculateCV();
}
