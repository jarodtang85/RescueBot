import java.io.IOException;
/**
 * Custom exception class for indicating an invalid input error.
 */
public class InvalidInputException extends RuntimeException{
    /**
     * Constructs a new InvalidInputException with the specified error message.
     *
     * @param message The error message.
     */
    public InvalidInputException(String message) {
        super(message);
    }

}
