import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Collections;
import java.math.RoundingMode;
import java.text.DecimalFormat;


/**
 * HandleStats prints out the statistics based on the decisions made
 */
public class HandleStats {

    private Scanner getCommand = ScannerSingleton.getScanner();
    private String command;
    // Taken from the below site
    // https://mkyong.com/java/how-to-round-double-float-value-to-2-decimal-points-in-java/
    private DecimalFormat df = new DecimalFormat("0.00");

    /**
     * Prints out statistics
     * @param scenarios
     * @param deployedLocs
     * @param runs
     * @param last true if it is the last run, false if it isn't
     * @param audit true if called by audit, false if not 
     * @return returns boolean to control classes which called HandleStats
     */

    public boolean showStats(List<Scenario> scenarios, List<Location> deployedLocs, int runs, boolean last, boolean audit) {

        List<String> uniqueFeatures = getUnique(scenarios, runs);
        List<Pair> statistics = getStats(scenarios, deployedLocs, uniqueFeatures, runs);
        // Prints Statistics Heading
        printStatsHeading(runs, audit);
        // Set round mode to up
        df.setRoundingMode(RoundingMode.UP);
        
        for (Pair feature : statistics) {
            System.out.println(feature.getFeature() + ": " + df.format((double) feature.getRatio()));
        }
        // Average age of human survivors
        System.out.println("--");
        System.out.println("average age: " + df.format((double) avgAge(deployedLocs)));

        while (!last) {
            System.out.println("Would you like to continue? (yes/no)");
            System.out.print("> ");
            command = getCommand.next().toUpperCase();
            // Consume the newline character so that showStats runs correctly
            getCommand.nextLine();
            if (command.equals("YES")) {
                
                return true;
            }
            else if (command.equals("NO")) {
                // return to menu
                return false;
            }
            else {
                System.out.print("Invalid response! ");
            }
        }
        return true; // This statement exist purely to exit the method
    }

    private void printStatsHeading(int runs, boolean audit) {
        if (!audit) {
            System.out.println("======================================");
            System.out.println("# Statistic");
            System.out.println("======================================");
        }
        System.out.println("- % SAVED AFTER " + runs + " RUNS");
    }
    
    /**
     * Gets all unique characteristics present
     * @param scenarios
     * @param runs
     * @return
     */
    
    private List<String> getUnique(List<Scenario> scenarios, int runs) {

        String[] excludeList = {"null", "unspecified", "none", "unknown"};
        List<String> uniqueFeatures = new ArrayList<>();
        for (int i = 0; i<runs; i++) {
            List<Location> allLocs = scenarios.get(i).locations;
            for (int j = 0; j<allLocs.size(); j++) {

                // Add status if it has not already been added
                if (!uniqueFeatures.contains(allLocs.get(j).status)) {
                    uniqueFeatures.add(allLocs.get(j).status);
                }
                List<Character> allChars = allLocs.get(j).characters;
                for (int k = 0; k<allChars.size(); k++) {
                    Character currChar = allChars.get(k);
                    for (int m = 0; m<currChar.features.length; m++) {

                        // Add character feature if it has not already been added
                        if (!uniqueFeatures.contains(currChar.features[m])) {
                            if (Arrays.asList(excludeList).contains(currChar.features[m])) {
                                // Do nothing
                            }
                            else {
                                uniqueFeatures.add(currChar.features[m]);
                                
                            }
                        }
                    }
                }
            }
        }
        return uniqueFeatures;

    }
    
    /**
    * Calculates the ratio of survivors to the total number of characters with a specific attribute.
    * @param scenarios the list of scenarios
    * @param deployedLocs the list of deployed locations
    * @param attribute the specific attribute
    * @return the ratio of survivors to the total number of characters with the attribute
    */
    private double getRatio(List<Scenario> scenarios, List<Location> deployedLocs, String attribute, int runs) {
        double survivorRatio;
        double survivorCount = 0, total = 0;

        // Get the total number of characters with the specified attribute
        for (int i = 0; i < runs; i++) {
            List<Location> allLocs = scenarios.get(i).locations;
            for (int j = 0; j < allLocs.size(); j++) {
                if (Arrays.asList(allLocs.get(j).status).contains(attribute)) {
                    // Increment total by the number of characters with the specified status
                    total += allLocs.get(j).characters.size();

                }
                List<Character> allChars = allLocs.get(j).characters;
                for (int k = 0; k < allChars.size(); k++) {
                    Character currChar = allChars.get(k);
                    if (Arrays.asList(currChar.features).contains(attribute)) {
                        // Increment total by 1 for each character with the specified feature
                        total += 1;
                    }
                }
            }
        }

        // Get the number of survivors with the specified attribute
        for (int j = 0; j < deployedLocs.size(); j++) {
            if (Arrays.asList(deployedLocs.get(j).status).contains(attribute)) {
                // Increment survivorCount by the number of characters with the specified status
                survivorCount += deployedLocs.get(j).characters.size();
            }
            List<Character> allChars = deployedLocs.get(j).characters;
            for (int k = 0; k < allChars.size(); k++) {
                Character currChar = allChars.get(k);
                if (Arrays.asList(currChar.features).contains(attribute)) {
                    // Increment survivorCount by 1 for each character with the specified feature
                    survivorCount += 1;
                }
            }
        }

        // Calculate the survivor ratio
        survivorRatio = survivorCount / total;
        return survivorRatio;
    }


    /**
     * Sorts statistics
     * @param scenarios
     * @param deployedLocs
     * @param uniqueFeatures
     * @param runs
     * @return sorted statistics
     */
    private List<Pair> getStats(List<Scenario> scenarios, List<Location> deployedLocs, List<String> uniqueFeatures, int runs) {
        
        // Initialisation
        List<Pair> statistics = new ArrayList<>();        
        String attribute;
        double ratio;


        // Length of uniqueFeatures and survivorRatio should be the same
        for (int i = 0; i<uniqueFeatures.size(); i++) {
            attribute = uniqueFeatures.get(i);
            ratio = getRatio(scenarios, deployedLocs, attribute, runs);
            Pair instance = new Pair(attribute, ratio);
            statistics.add(instance);
        }

        // Sorting
        Collections.sort(statistics, new StatsComparator());

        return statistics;

    }

    /**
     * Calculates avg age of human survivors
     * @param deployedLocs
     * @return average age of humann survivors
     */
    private double avgAge(List<Location> deployedLocs) {
        double totalAge = 0, n = 0;
        double avg;

        for (int j = 0; j<deployedLocs.size(); j++) {

            List<Character> allChars = deployedLocs.get(j).characters;
            for (int k = 0; k<allChars.size(); k++) {
                Character currChar = allChars.get(k);
                if (currChar.type.equals("human")) { // Only count human survivors
                    totalAge = totalAge + currChar.age;
                    n = n + 1;
                }
            }
        }

        avg = totalAge/n;
        return avg;

    }
}
