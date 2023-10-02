import java.io.IOException;

/**
 * Custom exception class for indicating an invalid characteristic error.
 */
public class InvalidCharacteristicException extends RuntimeException {

    /**
     * Constructs a new InvalidCharacteristicException with the specified error message.
     *
     * @param message The error message.
     */
    public InvalidCharacteristicException(String message) {
        super(message);
    }

}
