import java.util.*;

/**
 * The Character class represents a character in a scenario.
 */
public class Character {

    String type;
    String[] features; // array to store the features
    int age;

    /**
     * Constructs a new Character object.
     * Initializes the features array.
     */
    public Character() {
        features = new String[8];
    }

    /**
     * Sets the gender of the character.
     * If the character is a human, updates the gender feature.
     *
     * @param gender The gender of the character.
     */
    public void setGender(String gender) {
        if (features[0].equals("human")) {
            features[1] = gender;
        } else {
            features[1] = "null";
        }
    }

    /**
     * Sets the age of the character.
     * If the character is a human, updates the age feature and determines the age category.
     *
     * @param age The age of the character.
     */
    public void setAge(int age) {
        if (features[0].equals("human")) {
            this.age = age;
            if (age >= 0 && age <= 4) {
                features[2] = "baby";
            } else if (age >= 5 && age <= 16) {
                features[2] = "child";
            } else if (age >= 17 && age <= 68) {
                features[2] = "adult";
            } else {
                features[2] = "senior";
            }
        } else {
            features[2] = "null";
        }
    }

    /**
     * Sets the age of the character as a string.
     *
     * @param age The age of the character as a string.
     */
    public void setAgeString(String age) {
        features[2] = age;
    }

    /**
     * Sets the body type of the character.
     * If the character is a human, updates the body type feature.
     *
     * @param bodyType The body type of the character.
     */
    public void setBodyT(String bodyType) {
        if (features[0].equals("human")) {
            features[3] = bodyType;
        } else {
            features[3] = "null";
        }
    }

    /**
     * Sets the job/profession of the character.
     * If the profession is "none", updates it as "null".
     *
     * @param profession The job/profession of the character.
     */
    public void setJob(String profession) {
        if (profession.equals("none")) {
            profession = "null";
        }
        features[4] = profession;
    }

    /**
     * Sets the pregnancy status of the character.
     * If the character is pregnant, updates the status as "pregnant".
     *
     * @param pregnant The pregnancy status of the character.
     */
    public void isPreg(String pregnant) {
        if (pregnant.equals("true")) {
            pregnant = "pregnant";
        } else {
            pregnant = "null";
        }
        features[5] = pregnant;
    }

    /**
     * Sets the species of the character.
     *
     * @param species The species of the character.
     */
    public void setSpecies(String species) {
        features[6] = species;
    }

    /**
     * Checks if the character is a pet.
     * If the character is a pet, updates the status as "pet".
     *
     * @param isPet Indicates if the character is a pet.
     */
    public void checkPet(String isPet) {
        if (isPet.equals("true")) {
            isPet = "pet";
        } else {
            isPet = "null";
        }
        features[7] = isPet;
    }
}

