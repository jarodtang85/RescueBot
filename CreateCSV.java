import java.util.Random;
import java.util.Arrays;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Creates a csv file if there is not one provided
 */
public class CreateCSV {

    /**
     * Assists in randomly generating scenarios
     */
    private Random rand = new Random();
    
    /**

    * Writes data to a CSV file.

    * @param filename the name of the CSV file to write to

    * @param scenarioNum the number of scenarios to generate and write to the file

    @return {@code true} if the writing process was successful, {@code false} otherwise
    */

    public boolean writeToCSV(String filename, int scenarioNum) {

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Initialisation
            String scenarioLine, locationLine, characterLine;
            int locNum, charNum;
            int minLocationNum = 2;
            int maxLocationNum = 20;
            int minCharacter = 1;
            int maxCharacter = 20;

            for (int i = 0; i<scenarioNum; i++) {
                // write to file
                writer.println(scenarioLine = generateScenario());
                locNum = rand.nextInt(maxLocationNum + 1) + minLocationNum;
                for (int j = 0; j<locNum; j++) {
                    // write to file
                    writer.println(locationLine = generateLoc());
                    charNum = rand.nextInt(maxCharacter + 1) + minCharacter;
                    for (int k = 0; k<charNum; k++) {
                        writer.println(characterLine = generateChar());
                        
                    }
                }
                
            }
        } catch(IOException e) {
            System.out.println("An error occurred while creating the CSV file.");
            e.printStackTrace();  
        }
    return false;
    }

    
    private String generateScenario() {

        String newScenario;
        String[] possibleScenarios = {"flood", "bushfire", "cyclone", "earthquake"};
        newScenario = possibleScenarios[rand.nextInt(possibleScenarios.length)];
        return ("scenario:" + newScenario + ",,,,,,,");

    }

    private String generateLoc() {

        String status, yOrientation, xOrientation;
        double xpos, ypos;
        double maxLat = 90;
        double maxLong = 180;
        String[] latitude = {"N", "S"};
        String[] longitude = {"E", "W"};
        String[] statusOptions = {"trespassing", "legal"};
        xpos = maxLat * rand.nextDouble();
        ypos = maxLong * rand.nextDouble();
        yOrientation = latitude[rand.nextInt(latitude.length)];
        xOrientation = longitude[rand.nextInt(longitude.length)];
        status = statusOptions[rand.nextInt(statusOptions.length)];

        return ("location:" + String.format("%.4f", ypos) + " " + yOrientation + ";" + String.format("%.4f", xpos) + " " + xOrientation + ";" + status + ",,,,,,,");
    }

    private String generateChar() {

        String setting, characterLine;
        String[] type = {"animal", "human"};
        setting = type[rand.nextInt(type.length)];
        if (setting.equals("animal")) {
            return generateAnimal(setting);
        }
        else {
            return generateHuman(setting);
        }

    }

    private String generateHuman(String type) {
        // Initialisation
        int maxHumanAge = 100;
        int age;
        String gender, bodyT, profession, isPreg;
        String[] genderOptions = {"male", "female"};
        String[] bodyOptions = {"average", "athletic", "overweight"};
        String[] jobOptions = {"doctor", "ceo", "criminal", "homeless", "unemployed", "student", "professor", "engineer", "physicist"};
        String[] binary = {"true", "false"};

        gender = genderOptions[rand.nextInt(genderOptions.length)];
        age = rand.nextInt(maxHumanAge+1);
        bodyT = bodyOptions[rand.nextInt(bodyOptions.length)];
        profession = jobOptions[rand.nextInt(jobOptions.length)];
        if (gender.equals("female")) {
            isPreg = binary[rand.nextInt(binary.length)];
        }
        else {
            isPreg = "false";
        }
        return (type + "," + gender + "," + age + "," + bodyT + "," + profession + "," + isPreg + "," + ",");
    }

    private String generateAnimal(String type) {
        // Initialisation
        int maxAnimalAge = 20;
        int age;
        String species, isPet, gender, bodyT;
        String[] genderOptions = {"male", "female"};
        String[] bodyOptions = {"average", "athletic", "overweight"};
        String[] speciesOptions = {"cat", "koala", "dog", "wallaby", "kangaroo", "ferret", "platypus"};
        String[] allPets = {"cat", "dog", "ferret"};

        gender = genderOptions[rand.nextInt(genderOptions.length)];
        age = rand.nextInt(maxAnimalAge+1);
        bodyT = bodyOptions[rand.nextInt(bodyOptions.length)];
        species = speciesOptions[rand.nextInt(speciesOptions.length)];

        if (Arrays.asList(allPets).contains(species)) {
            isPet = "true";
        }
        else {
            isPet = "false";
        }

        return (type + "," + gender + "," + age + "," + bodyT + "," + "," + "," + species + "," + isPet);

    }


}



