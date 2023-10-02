import java.io.File;
import java.io.FileNotFoundException;

/**
 * This class handles the initial inputs when initating the program
 */
public class FlagHandler {

    public boolean isImported;
    
    /**
     * launch checks the command line inputs
     * 
     * @param args is the command line input as a string array
     * @return string array containing paths to scenario and log files
     */
    public String[] launch(String[] args) {

        boolean scenarioGiven = false, logGiven = false;
        // If either no flag for scenario or log was provided
        String scenarioFile = "scenarioGenerated.csv";
        String logFile = "rescuebot.log";
        
        int defaultNum = 3; // default number of scenarios if user does not specify no. scenarios to be generated

        for (int i = 0; i<args.length; i++) {
            if (args[i].equals("--help") || args[i].equals("-h")) {
                printHelp();
            }

            if (args[i].equals("--log") || args[i].equals("-l")) { 
                if ((i+1)<args.length) { // log file should proceed log flag
                    logGiven = true; // check if path give is legitimate
                    if (args[i+1].equals("--scenarios") || args[i+1].equals("-s")) {
                        // invalid input
                        printHelp();
                    }
                    else {
                        logFile = args[i+1];
                    }
                }
                else {
                    // invalid
                    printHelp();
                }
            }

            if (args[i].equals("--scenarios") || args[i].equals("-s")) {
                scenarioGiven = true; // check if path is legitimate
                if ((i+1)<args.length) { // check for scenario path
                    if (args[i+1].equals("--log") || args[i+1].equals("-l")) {
                        // invalid input
                        printHelp();
                    }
                    else {
                        // store scenario file path
                        scenarioFile = args[i+1];
                        isImported = true;
                    }
                }
                else {
                    // invalid
                    printHelp();
                }
            }

        }
        
        // if no flag pertaining to scenario was encountered, generate scenario
        if (!scenarioGiven) {
            scenarioGiven = true;
            CreateCSV createScenario = new CreateCSV();
            isImported = createScenario.writeToCSV(scenarioFile, defaultNum);
        }

        // if no flag pertaining to log was encountered, create new log file
        if (!logGiven) {
            logGiven = true;
            CreateLog generateLog = new CreateLog();
            generateLog.newLog(logFile);
            // create log
        }

        if (scenarioGiven) {
            File file = new File(scenarioFile);
            if (file.exists()) {
                // File exists
            } else {
                // File does not exist, throw FileNotFoundException
                try {
                    throw new FileNotFoundException("java.io.FileNotFoundException: could not find scenarios file.");
                } catch (FileNotFoundException e) {
                    System.err.println(e.getMessage()); // Print the error message
                    printHelp(); // Terminate the program with a non-zero status code
                }
            }
        }

        if (logGiven) {
            File file = new File(logFile);
            if (file.exists()) {
                // File exists
            } else {
                // File does not exist, throw FileNotFoundException
                try {
                    throw new FileNotFoundException("java.io.FileNotFoundException: could not find log file.");
                } catch (FileNotFoundException e) {
                    System.err.println(e.getMessage()); // Print the error message
                    printHelp(); // Terminate the program with a non-zero status code
                }
            }
        }

        String[] filenames = {scenarioFile, logFile};
        return filenames;
    }

    /**
    * Checks whether the data has been imported.
    *
    * @return {@code true} if the data has been imported, {@code false} otherwise.
    */
    public boolean checkImported() {
        return isImported;
    }

    /**
    * Prints the help message with usage instructions for the RescueBot program.
    */
    private void printHelp() {
        System.out.println("RescueBot - COMP90041 - Final Project");
        System.out.println();
        System.out.println("Usage: java RescueBot [arguments]");
        System.out.println();
        System.out.println("Arguments:");
        System.out.println("-s or --scenarios" + "\t" + "Optional: path to scenario file");
        System.out.println("-h or --help" + "\t" + "\t" +"Optional: Print Help (this message) and exit");
        System.out.println("-l or --log" + "\t" + "\t" + "Optional: path to data log file");
        System.exit(1);
    }

}
