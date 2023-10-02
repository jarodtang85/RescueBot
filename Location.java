import java.util.*;

/**
 * Represents a location in the context of the application.
 */
public class Location {
    String yPos;
    String xPos;
    String status; // legal or illegal
    List<Character> characters;

    /**
     * Constructs a new Location object.
     * Initializes the list of characters associated with the location.
     */
    public Location() {
        characters = new ArrayList<>();
    }

    /**
     * Sets the status of the location.
     *
     * @param status The status of the location (legal or illegal).
     */
    public void addStatus(String status) {
        this.status = status;
    }

    /**
     * Sets the coordinates of the location.
     *
     * @param yPos The y-coordinate of the location.
     * @param xPos The x-coordinate of the location.
     */
    public void addCoords(String yPos, String xPos) {
        this.yPos = yPos;
        this.xPos = xPos;
    }

    /**
     * Adds a character to the location.
     *
     * @param character The character to add.
     */
    public void addCharacter(Character character) {
        characters.add(character);
    }

    /**
     * Prints the location details.
     * Displays the coordinates and trespassing status of the location.
     */
    public void printLoc() {
        System.out.println(yPos + ", " + xPos);
        System.out.print("Trespassing: ");
        if (status.equals("trespassing")) {
            System.out.println("yes");
        } else {
            System.out.println("no");
        }
    }

}

