import java.util.Comparator;


/**
 * Comparator implementation for comparing Pair objects based on their ratio values and feature names.
 * The comparison is done in descending order of ratio values, and if the ratio values are equal, the comparison
 * is based on the lexicographic order of the feature names. The implementation below is heavily based on the information
 * provided in https://coderanch.com/t/405486/java/instance-variables-sort-objects.
 */
public class StatsComparator implements Comparator<Pair> {

    /**
     * Compares two Pair objects based on their ratio values and feature names.
     * The comparison is done in descending order of ratio values, and if the ratio values are equal, the comparison
     * is based on the lexicographic order of the feature names.
     *
     * @param p1 the first Pair object to compare
     * @param p2 the second Pair object to compare
     * @return a negative integer if p1 should be placed before p2,
     *         a positive integer if p1 should be placed after p2,
     *         or zero if p1 and p2 are considered equal
     */
    public int compare(Pair p1, Pair p2) {

        double ratio1 = p1.getRatio();
        double ratio2 = p2.getRatio();

        if (ratio1 > ratio2) {
            return -1;
        } else if (ratio2 > ratio1) {
            return 1;
        } else { // if both ratios are equal, then sort alphabetically
            String f1 = p1.getFeature();
            String f2 = p2.getFeature();
            int result = f1.compareTo(f2);
            if (result < 0) {
                return -1; // f1 comes before f2
            } else {
                return 1; // f1 comes after f2
            }
        }
    }
}
