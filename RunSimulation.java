import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Responsible for running a simulation using a premade decision algorithm
 */
public class RunSimulation {

    private CreateCSV generateScenarios = new CreateCSV(); 
    private Scanner getCommand = ScannerSingleton.getScanner();
    private int N; // no.scenarios
    private int[] choosenLocations;
    private PreProcessing setup = new PreProcessing();
    private HandleStats runSim = new HandleStats();
    private JudgeScenarios returnToMenu = new JudgeScenarios();
    private Audit storeData = new Audit();

    /**
     * simulate asks the user how many scenarios are to be run if no scenario path has been provided and
     * provides the statistics 
     * @param fileDirectories contains list of file paths for scenario and log file
     * @param isImported true if a file path for scenario was provided, false if otherwise
     * @return always returns true so that the audit class can function as intended
     */
    public boolean simulate(String[] fileDirectories, boolean isImported) {

        String scenarioFile = fileDirectories[0];
        String logFile = fileDirectories[1];

        if (scenarioFile.equals("scenarioGenerated.csv")) {
            while (true) {
                System.out.println("How many scenarios should be run?");
                System.out.print("> ");
                try {
                    N = getCommand.nextInt();
                    // Consume the newline character
                    getCommand.nextLine();
                    // Check if input is a positive integer
                    if (N > 0) {
                        generateScenarios.writeToCSV(scenarioFile, N);
                        break;
                    } else {
                        throw new InvalidNumberFormatException("Invalid input! ");
                    }
                } catch (InvalidNumberFormatException e) {
                    System.err.print(e.getMessage());
                }
            }
            
        }

        // preprocessing step
        setup.unpacking(scenarioFile, false);
        List<Scenario> scenarios = setup.getScenarios();
        
        // Running Simulation
        choosenLocations = robotJudge(scenarios); // choosing locations based on number of humans
        List<Location> deployedLocs = new ArrayList<>();
        for (int i = 0; i<choosenLocations.length; i++) {
            storeData.writeToLog(logFile, scenarios.get(i), choosenLocations[i], "robot");
            deployedLocs.add(scenarios.get(i).locations.get(choosenLocations[i]));
        }

        runSim.showStats(scenarios, deployedLocs, scenarios.size(), true, false);
        returnToMenu.endRobot();
        
        // There will be history in audit since bot made a decision
        return true;
    }

    /**
     * picks the deployed location based on the number of human lives at a given location
     * @param scenarios list of scenarios
     * @return location array containing deployed locations
     */
    private int[] robotJudge(List<Scenario> scenarios) {
        int maxAmount = 0, currAmount = 0, currLocation = 0;
        String decidingFactor = "human";
        int[] locations = new int[scenarios.size()];

        for (int i = 0; i < scenarios.size(); i++) {
            currLocation = 0; // Reset currLocation for each scenario
            List<Location> allLocs = scenarios.get(i).locations;
            for (int j = 0; j < allLocs.size(); j++) {
                List<Character> allChars = allLocs.get(j).characters;
                // reset current amount of humans
                currAmount = 0;
                for (int k = 0; k < allChars.size(); k++) {
                    Character currChar = allChars.get(k);
                    if (Arrays.asList(currChar.features).contains(decidingFactor)) {
                        // Increment total by 1 for each character with the specified feature
                        currAmount += 1;
                    }
                }
                if (maxAmount <= currAmount) {
                    maxAmount = currAmount;
                    currLocation = j;
                }

            }
            locations[i] = currLocation;
        }

        return locations;
    }

}
