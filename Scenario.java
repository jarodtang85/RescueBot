import java.util.*;

/**
 * The Scenario class stores the type of scenario that will occur (e.g. flood, bushfire etc), and also 
 * stores the locations at which the scenarios occur at.
 */
public class Scenario {
    String type;
    List<Location> locations;

    /**
     * Constructor of Scenario
     */

    public Scenario() { // Initialise locations dynamics list for each call
        locations = new ArrayList<>(); 
    }

    /**
     * setType establishes the type of scenario for the given object
     * 
     * @param type
     */
    protected void setType(String type) {
        this.type = type;
    }
    
    /**
     * addLocation adds the locations belonging to a particular scenario to a pre-initialised
     * arraylist.
     * 
     * @param location
     */
    protected void addLocation(Location location) {  
        
        locations.add(location);
    }

    protected void printScenario() {
        System.out.println("======================================");
        System.out.println("# Scenario: " + this.type);
        System.out.println("======================================");
        for (int i = 0; i<locations.size(); i++) {
            Location currLoc = locations.get(i);
            System.out.print("[" + (i+1) + "] Location: "); // Must increment by 1 to have first location of index 1
            currLoc.printLoc(); // Calls Location

            System.out.println(locations.get(i).characters.size() + " Characters: ");

            for (int j = 0; j<currLoc.characters.size(); j++) {
                Character currChar = currLoc.characters.get(j);
                if (currChar instanceof Human) {
                    Human human = (Human) currChar;
                    human.printCharacter(); // Calls printCharacter() specific to Human
                }
                else { // Then animal object
                    Animal animal = (Animal) currChar;
                    animal.printCharacter(); // Calls printCharacter() specific to Animal
                }
            
            }
        }
    }

    
}


