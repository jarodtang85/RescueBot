/**
 * Represents a pair of a feature and its corresponding ratio.
 */
public class Pair {

    String feature;
    double ratio;

    /**
     * Constructs a new Pair object with the specified feature and ratio.
     *
     * @param feature The feature.
     * @param ratio The ratio.
     */
    public Pair(String feature, double ratio) {
        this.feature = feature;
        this.ratio = ratio;
    }

    /**
     * Retrieves the feature.
     *
     * @return The feature.
     */
    public String getFeature() {
        return feature;
    }

    /**
     * Retrieves the ratio.
     *
     * @return The ratio.
     */
    public double getRatio() {
        return ratio;
    }

}

