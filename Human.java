/**
 * Represents a Human character.
 * Extends the Character class.
 */
public class Human extends Character {

    /**
     * Constructs a new Human character.
     * Sets the type to "human" and initializes the features array.
     */
    public Human() {
        type = "human";
        this.type = type;
        features[0] = type;
    }

    /**
     * Prints the character's details.
     * Displays the character's features including age, gender, name, and pregnancy status (if applicable).
     */
    public void printCharacter() {
        System.out.print("- " + features[3]);
        System.out.print(" " + features[2]);
        if (features[2].equals("adult")) {
            System.out.print(" " + features[4]);
        }
        System.out.print(" " + features[1]);
        if (features[1].equals("female") && features[5].equals("pregnant")) {
            System.out.print(" pregnant");
        }
        System.out.println();
    }

    /**
     * Adds a new Human character.
     * Generates a string representation of the character's features, including age, gender, name,
     * and pregnancy status (if applicable).
     *
     * @return The string representation of the Human character's features.
     */
    public String addHuman() {
        if (features[2].equals("adult")) {
            if (features[1].equals("female") && features[5].equals("pregnant")) {
                return (features[3] + " " + age + " " + features[4] + " " + features[1] + " pregnant");
            }
            else {
                return (features[3] + " " + age + " " + features[4] + " " + features[1]);
            }
        }
        else {
            if (features[1].equals("female") && features[5].equals("pregnant")) {
                return (features[3] + " " + age + " " + features[1] + " pregnant");
            }
            else {
                return (features[3] + " " + age + " " + features[1]);
            }
        }
    }

}
