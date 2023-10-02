import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * The preProcessing class is responsible for parsing a csv file, creating instances of
 * the classes Scenario, Location and Character, and storing these instances for future use.
 */
public class PreProcessing {
    // List to store all scenarios
    private List<Scenario> scenarios = new ArrayList<>();
    /*
    private InvalidDataFormat except1 = new InvalidDataFormat();
    private InvalidNumberFormat except2 = new InvalidNumberFormat();
    private InvalidFieldValues except3 = new InvalidFieldValues();
    */

    /**
     * The method "unpacking" parses in the provided csv file.
     * It creates instances of the classes Scenario, Location and Character based on the data in the file,
     * and stores these instances in the appropriate data structures.
     *
     * @param scenarioFile the path to the csv file to be parsed
     */
    public void unpacking(String scenarioFile, boolean isImported) {
        // Initialisation
        String line;
        String splitter = ",";
        int start = 9; // Both scenario and location are of character length 8, start at index 9 to account for :
        int columnCount = 8;

        try (BufferedReader br = new BufferedReader(new FileReader(scenarioFile))) {
            int lineCount = 0;
            Scenario scenario = null;
            Location location = null;

            while ((line = br.readLine()) != null) {
                lineCount++;
                String[] instance = line.split(splitter, -1);

                // Replace empty blocks with "null"
                for (int k = 0; k < instance.length; k++) {
                    if (instance[k].isEmpty()) {
                        instance[k] = "null";
                    }
                }

                // If line is a heading, scenario, or location, process accordingly
                if (line.contains("gender")) {
                    continue; // Stops reading this line and start reading next
                } else if (line.contains("scenario")) {
                    scenario = new Scenario();
                    scenario.setType(instance[0].substring(start).trim());
                    scenarios.add(scenario);
                } else if (line.contains("location")) {
                    location = new Location();
                    String yPos = instance[0].substring(start).split(";")[0].trim();
                    String xPos = instance[0].split(";")[1].trim();
                    location.addCoords(yPos, xPos);
                    location.addStatus(instance[0].split(";")[2].trim());

                    // Error Checks
                    if (scenario != null) {
                        scenario.addLocation(location);
                    } else {
                        System.out.println("Location found before scenario. Please check your data file.");
                    }
                } else { // Line represents a character
                Character character;

                    // Validate data format for a line
                    try {
                        if (instance.length != columnCount) {
                            throw new InvalidDataFormatException("WARNING: invalid data format in scenarios file in line " + lineCount);
                        }
                    } catch (InvalidDataFormatException e) {
                        System.err.println(e.getMessage());
                        continue; // Skips reading this line and starts reading the next one
                    }

                    // Validate number format for age
                    for (int i = 0; i < instance.length; i++) {
                        if (i == 2) {
                            try {
                                IsValidNumber check = new IsValidNumber();
                                if (!check.IsValidNumber(instance[i])) {
                                    throw new InvalidNumberFormatException("WARNING: invalid number format in scenarios file in line " + lineCount);
                                }
                            } catch (InvalidNumberFormatException e) {
                                System.err.println(e.getMessage());
                                // Assign a default value or perform additional actions
                                instance[i] = line.contains("animal") ? "8" : "38";
                            }
                        }
                    }

                    // Check for invalid characteristics
                    String[] oldInstance = Arrays.copyOf(instance, instance.length);
                    String[] currInstance = checkChar(instance);
                    boolean noChange = Arrays.equals(currInstance, oldInstance);
                    try {
                        if (!noChange) {
                            throw new InvalidCharacteristicException("WARNING: invalid characteristic in scenarios file in line " + lineCount);
                        }
                    } catch (InvalidCharacteristicException e) {
                         System.err.println(e.getMessage());
                        continue; // Skips reading this line and starts reading the next one
                    }

                    if (instance[0].equals("animal")) {
                        // Create an animal character
                        character = new Animal();
                        character = setCharacter(instance, character);
                    }
                    else {
                        // Create a human character
                        character = new Human();
                        character = setCharacter(instance, character);
                    }


                    if (location != null) {
                        location.addCharacter(character);
                    } else {
                        System.out.println("Character was found before location. Please check your data file.");
                    }
                }
            }
            if (isImported) {
                System.out.println(scenarios.size() + " scenarios imported.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * This method is a getter method to return the list of scenarios
     * 
     * @return list of scenarios
     */

    public List<Scenario> getScenarios() {
        return this.scenarios;
    }
    
    // Test
    public void printScenarios() {
        for (Scenario scenario : scenarios) {
            System.out.println(scenario);
        }
    }

    /**
     * This method checks if the given characteristics are valid or not.
     * If a characteristic is not valid, then that characteristic is assgined a default one
     *
     * @param instance the array of characteristics to be checked
     * @return the instance array after checking and adjusting the characteristics
     */
    private String[] checkChar(String[] instance) {
        String[] jobs = {"doctor", "ceo", "criminal", "homeless", "unemployed", "student", "professor", "engineer", "physicist"};
        String[] gender = {"male", "female"};
        String[] bodyT = {"average", "athletic", "overweight"};

        // Check if gender and body type are valid
        if (!Arrays.asList(gender).contains(instance[1])) {
            instance[1] = "unknown"; 
        }

        if (!Arrays.asList(bodyT).contains(instance[3])) {
            instance[3] = "unspecified";
        }

        // If character is human, check if job is valid
        if (instance[0].equals("human")) {
            if (!Arrays.asList(jobs).contains(instance[4])) {
                instance[4] = "none"; 
            }
        }
        
        return instance;
    }

    /**
     * This method sets characteristics of the character object
     *
     * @param instance the array of characteristics to be set
     * @return the Character object after setting the characteristics
     */
    private Character setCharacter(String[] instance, Character character) {
        // 0 is type
        // 1 is gender
        // 2 is age
        // 3 is body type
        // 4 is profession
        // 5 is pregnant
        // 6 is species
        // 7 is isPet
        character.setGender(instance[1]);
        character.setAge(Integer.parseInt(instance[2]));
        character.setBodyT(instance[3]);
        character.setJob(instance[4]);
        character.isPreg(instance[5]);
        character.setSpecies(instance[6]);
        character.checkPet(instance[7]);

        return character;
    }

}

