
/**
 * Class checks if a given input is a valid number.
 */
public class IsValidNumber {

    /**
     * Checks if the given input is a valid number.
     *
     * @param input The input string to check.
     * @return true if the input is a valid number, false otherwise.
     */
    public boolean IsValidNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
