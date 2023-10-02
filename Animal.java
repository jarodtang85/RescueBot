/**
 * The Animal class represents an animal character in a scenario. 
 * It extends the Character class.
 */
public class Animal extends Character {

    /**
     * Constructs a new Animal object.
     * Sets the type of the character to "animal" and updates the features array.
     */
    public Animal() {
        type = "animal";
        this.type = type;
        features[0] = type;
    }

    /**
     * Prints the character's information to the console.
     * Overrides the printCharacter method from the Character class.
     */
    public void printCharacter() {
        System.out.print("- " + features[6]);
        if (features[7].equals("pet")) {
            System.out.print(" is pet");
        }
        System.out.println();
    }

    /**
     * Adds the animal's information to a string representation.
     * Overrides the addAnimal method from the Character class.
     *
     * @return A string representation of the animal's information.
     */
    public String addAnimal() {
        if (features[7].equals("pet")) {
            return features[6] + " " + features[7];
        } else {
            return features[6];
        }
    }
}

