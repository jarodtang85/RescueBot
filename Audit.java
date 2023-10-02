import java.util.Arrays;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * The Audit class is responsible for the auditing function provided by the robot
 */
public class Audit {

    /**
    * getStats handles the statistics of the auditted data 
    */
    private HandleStats getStats = new HandleStats();

    /**
     * Checks if a history exists.
     *
     * @param historyExist Indicates whether history exists
     * @return {@code true} if history exists, {@code false} otherwise
     * @throws InvalidDataFormatException if history doesn't exist
     */
    public boolean checkHistory(boolean historyExist) {
        
        try {
            if (!historyExist) {
                throw new InvalidDataFormatException("No history found. ");
            }
        } catch (InvalidDataFormatException e) {
            System.err.print(e.getMessage());
            return false; // Skips reading this line and starts reading the next one
        }
        
        return true;
    }

    /**
     * Writes audit information to the log file.
     *
     * @param filename   The filename of the log file
     * @param scenario   The scenario object
     * @param savedLoc   The saved location
     * @param judgeType  The judge type
     */
    public void writeToLog(String filename, Scenario scenario, int savedLoc, String judgeType) {

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            
            writer.println("judgeType " + judgeType);
            writer.println("locationSaved " + savedLoc);
            
            writer.println("Scenario " + scenario.type);
            List<Location> allLocs = scenario.locations;
            for (int i = 0; i<allLocs.size(); i++) {
                Location currLoc = allLocs.get(i);
                writer.println("Location " + (i+1) + " " + currLoc.xPos + " " + currLoc.yPos);
                writer.println("status " + currLoc.status);

                for (int j = 0; j<currLoc.characters.size(); j++) {
                    Character currChar = currLoc.characters.get(j);
                    if (currChar.features[0].equals("human")) {
                        Human human = (Human) currChar;
                        writer.println("human " + human.addHuman());
                    }
                    else {
                        Animal animal = (Animal) currChar;
                        writer.println("animal " + animal.addAnimal());
                    }
                }
                if ((i+1 == allLocs.size())) {
                    // don't write separator to file
                }
                else {
                    writer.println("--");
                }

            }
            writer.println("======================================");

        } catch(IOException e) {
            System.out.println("An error occurred while writing to the log file.");
            e.printStackTrace();  
        }
    }
    /**
     * Prints the contents of the log file.
     *
     * @param filename The filename of the log file
     */
    public void printLog(String filename) {

        // Initialization
        String splitter = " ";
        String line, judge = "";
        int savedLoc = 0;
        boolean robotPresent = false, humanPresent = false;
        // Scenario variables
        String scenarioType = "";
        // Location variables
        String xPos = "", yPos = "", status;

        // Human variables
        String bodyType, job, gender, isPreg, age;

        // Animal variables
        String species, isPet;

        List<Scenario> scenariosHuman = new ArrayList<>();
        List<Location> deployedLocsHuman = new ArrayList<>();
        List<Scenario> scenariosRobot = new ArrayList<>();
        List<Location> deployedLocsRobot = new ArrayList<>();

        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            Scenario scenario = null;
            Location location = null;
            Character character = null;

            while ((line = br.readLine()) != null) {
                String[] instance = line.split(splitter, -1);

                int i = 0; // index
                if (instance.length == 1) {
                    if (instance[i].equals("======================================")) {
                        // ith scenario has been read
                        if (judge.equals("human")) {
                            humanPresent = true;
                            scenariosHuman.add(scenario);
                            deployedLocsHuman.add(scenario.locations.get(savedLoc-1));
                        }
                        else {
                            robotPresent = true;
                            scenariosRobot.add(scenario);
                            deployedLocsRobot.add(scenario.locations.get(savedLoc));
                        }
                    }
                    else { // -- spacer encountered

                    }
                }

                if (instance[i].equals("judgeType")) {
                    judge = instance[i+1];
                    continue; // read next line
                }

                if (instance[i].equals("locationSaved")) {
                    savedLoc = Integer.parseInt(instance[i+1]);
                    continue;
                }
                
                if (instance[i].equals("Scenario")) {
                    scenarioType = instance[i+1];
                    scenario = new Scenario();
                    scenario.setType(scenarioType);
                    continue;
                }
                
                if (instance[i].equals("Location")) {
                    xPos = instance[i+1];
                    yPos = instance[i+2];
                    continue;
                }

                if (instance[i].equals("status")) {
                    status = instance[i+1];
                    location = new Location();
                    location.addCoords(yPos, xPos);
                    location.addStatus(status);
                    if (scenario != null) {
                        scenario.addLocation(location);
                    } else {
                        System.out.println("Location found before scenario. Please check.");
                    }
                }
                
                if (instance[i].equals("human")) {
                    // initialize
                    character = new Human();
                    character.age = Integer.parseInt(instance[i+2]);
                    character.setGender("null");
                    character.setAgeString("null");
                    character.setAge(character.age);
                    character.setBodyT("null");
                    character.setJob("null");
                    character.isPreg("null");
                    character.setSpecies("null");
                    character.checkPet("null");

                    bodyType = instance[i+1];
                    character.setBodyT(bodyType);
                    
                    if (instance.length > 4) { // instance of human has job and potentially pregnant
                        job = instance[i+3];
                        gender = instance[i+4];
                        character.setJob(job);
                    }
                    else {
                        gender = instance[i+3];
                    }
                    
                    character.setGender(gender);

                    if (instance.length == 6) {
                        isPreg = instance[i+5];
                        character.isPreg(isPreg);
                    }

                    if (location != null) {
                        location.addCharacter(character);
                    } else {
                        System.out.println("Character was found before location. Please check.");
                    }
                    continue;

                }

                if (instance[i].equals("animal")) {
                    
                    // initialize
                    
                    character = new Animal();
                    character.setGender("null");
                    character.setAgeString("null");
                    character.setBodyT("null");
                    character.setJob("null");
                    character.isPreg("null");
                    character.setSpecies("null");
                    character.checkPet("null");

                    species = instance[i+1];

                    if (instance.length == 3) {
                        isPet = instance[i+2];
                    }
                    character.setSpecies(species);

                    if (location != null) {
                        location.addCharacter(character);
                    } else {
                        System.out.println("Character was found before location. Please check.");
                    }
                    continue;
                }

                else {
                }
            }

            if (robotPresent) {
                System.out.println("======================================");
                System.out.println("# Algorithm Audit");
                System.out.println("======================================");
                getStats.showStats(scenariosRobot, deployedLocsRobot, deployedLocsRobot.size(), true, true);
            }

            if (humanPresent) {
                System.out.println("======================================");
                System.out.println("# User Audit");
                System.out.println("======================================");
                getStats.showStats(scenariosHuman, deployedLocsHuman, deployedLocsHuman.size(), true, true);
            }

        } catch (IOException e) {
            // Handle the exception
            System.out.println("Either could not find the file or an error occurred when attempting to read it in.");
            e.printStackTrace();
        }

        System.out.print("That's all. ");
    }
}

