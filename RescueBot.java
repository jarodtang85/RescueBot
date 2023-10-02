import java.lang.Math;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;
import java.util.Collections;

/**
 * COMP90041, Sem1, 202d3: Final Project
 * @author Jarod Fozhun Tang
 * student id: 117592
 * student email: jarodt@student.unimelb.edu.au
 */


/** 
 * Rescue bot is the main class
 */
public class RescueBot {

    /**
     * Reads a welcome file and prompts the user for commands
     */

    private Scanner getCommand = ScannerSingleton.getScanner();
    private String command;


    public static void main(String[] args) {

        boolean isImported;
        String welcomeFile = "welcome.ascii";
        
        // Reads in command line input
        FlagHandler startup = new FlagHandler();
        String[] fileDirectories = startup.launch(args);
        String scenarioFile = fileDirectories[0];
        String logFile = fileDirectories[1];
        isImported = startup.checkImported();

        RescueBot intro = new RescueBot();
        // Prints welcome message
        PrintAscii printWelcome = new PrintAscii();
        printWelcome.printFile(welcomeFile);
        // pre processing step here
        PreProcessing organise = new PreProcessing();
        organise.unpacking(scenarioFile, isImported);
        List<Scenario> scenarios = organise.getScenarios();

        intro.commands(scenarios, fileDirectories, isImported);
        
    }

    private void commands(List<Scenario> scenarios, String[] fileDirectories, boolean isImported) {
        
        boolean historyExist = false;
        // fileDirectories[0] is scenarioFile
        // fileDirectories[1] is logFile

        System.out.println("Please enter one of the following commands to continue:");
        while (true) {
            // Print all options
            System.out.println("- judge scenarios: [judge] or [j]");
            System.out.println("- run simulations with the in-built decision algorithm: [run] or [r]");
            System.out.println("- show audit from history: [audit] or [a]");
            System.out.println("- quit the program: [quit] or [q]");
            System.out.print("> ");
            command = getCommand.next().toUpperCase();

            if (command.equals("JUDGE")  || command.equals("J")) {
                // Consume the newline character so that showStats runs correctly
                getCommand.nextLine();
                JudgeScenarios judging = new JudgeScenarios();
                historyExist = judging.getConsent(scenarios, fileDirectories[1]);
            }
            else if (command.equals("RUN")  || command.equals("R")) {
                // Consume the newline character so that showStats runs correctly
                getCommand.nextLine();
                RunSimulation sim = new RunSimulation();
                historyExist = sim.simulate(fileDirectories, isImported);
            }
            else if (command.equals("AUDIT")  || command.equals("A")) {
                Audit doAudit = new Audit();

                if (doAudit.checkHistory(historyExist)) {
                    doAudit.printLog(fileDirectories[1]);
                    returnToMenu();
                }

                else {
                    returnToMenu();
                }
            }
            else if (command.equals("QUIT")  || command.equals("Q")) {
                break;
            }
            else {
                System.out.println("Invalid command! Please enter one of the following commands to continue:");
            }
        }

    }

    private void returnToMenu() {
        while (true) {
            // Consume the newline character
            getCommand.nextLine();
            System.out.println("Press Enter to return to main menu.");
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

