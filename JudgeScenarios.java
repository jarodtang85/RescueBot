import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

/**
 * The JudgeScenarios class contains the method to get consent 
 * from the user and 
 */
public class JudgeScenarios {

    private Scanner getCommand = ScannerSingleton.getScanner();
    private String command;
    private HandleStats getStats = new HandleStats();
    private int DIVIDER = 3;
    private Audit storeData = new Audit();

    /**
     * Prompts the user for consent to save decisions to a file and then uses a robot to begin judging
     *
     * @param scenarios The list of scenarios to be used for statistics.
     */
    public boolean getConsent(List<Scenario> scenarios, String logFile) {
        boolean consent = false;

        while (true) {
            System.out.println("Do you consent to have your decisions saved to a file? (yes/no)");
            System.out.print("> ");
            command = getCommand.next().toUpperCase();

            if (command.equals("YES")) {
                consent = true;
                break;
            } else if (command.equals("NO")) {
                break;
            } else {
                // Throw 
                try {
                    if (true) {
                        throw new InvalidInputException("Invalid response! ");
                    }
                } catch (InvalidInputException e) {
                    System.err.print(e.getMessage());
                }
            }
        }
        deployRobot(scenarios, consent, logFile);
        if (consent) {
            // there will be audit history since consent was given to record data
            return true;
        }
        else {
            // no audit history
            return false;
        }
    }
    
    /**
    * deploys robot based on choice of user
    */
    private void deployRobot(List<Scenario> scenarios, boolean consent, String logFile) {
            
        // Initialisation
        List<Location> deployedLocs = new ArrayList<>();
        int runs = 0;
        int maxRuns = scenarios.size();
        boolean continueRun = true;
        int loc;

        for (int i = 0; i<scenarios.size(); i++) {
            if (!continueRun) {
                break;
            }
            scenarios.get(i).printScenario();
            runs++;
            int locationAmount = scenarios.get(i).locations.size();
            
            while (true) {

                System.out.println("To which location should RescueBot be deployed?");
                System.out.print("> ");
                loc = getCommand.nextInt();
                // Consume the newline character so that showStats runs correctly
                getCommand.nextLine();

                if ( (loc > 0) && (loc<=locationAmount) ) {
                    // loc-1 as index starts from 1 less
                    deployedLocs.add(scenarios.get(i).locations.get(loc-1));
                    
                    if (consent) {
                        // Store the decision history of the user
                        storeData.writeToLog(logFile, scenarios.get(i), loc, "human");
                    }

                    if (runs%DIVIDER == 0) {
                        if (runs == maxRuns) {
                            // last scenario in list of scenarios
                            continueRun = getStats.showStats(scenarios, deployedLocs, runs, true, false);
                        }
                        else {
                            // ask if they want to continue
                            continueRun = getStats.showStats(scenarios, deployedLocs, runs, false, false);
                        }
                    }
                    break;
                }
                else {
                    System.out.print("Invalid response! ");
                }
            }

        }

        endRobot();

        System.out.println("Please enter one of the following commands to continue:");
    }
    
    /**
    * Ends the robot operation and waits for user input to return to the main menu.
    * The user needs to press Enter to exit the loop and return to the main menu.
    */
    public void endRobot() {
        while (true) {
            System.out.println("That's all. Press Enter to return to main menu.");
            System.out.print("> ");
            command = getCommand.nextLine();
            if (command.isEmpty()) {
                break;
            }
            else {
                System.out.print("Invalid response! ");
            }
        }
    }



}

