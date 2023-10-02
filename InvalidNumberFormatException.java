import java.io.IOException;
/**
 * Custom exception class for indicating an invalid number format error.
 */
public class InvalidNumberFormatException extends RuntimeException {
    /**
     * Constructs a new InvalidNumberFormatException with the specified error message.
     *
     * @param message The error message.
     */
    public InvalidNumberFormatException(String message) {
        super(message);
    }
    
}
