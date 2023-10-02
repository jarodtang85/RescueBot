import java.io.IOException;
/**
 * Custom exception class for indicating an invalid data format error.
 */
public class InvalidDataFormatException extends RuntimeException{
    /**
     * Constructs a new InvalidDataFormatException with the specified error message.
     *
     * @param message The error message.
     */
    public InvalidDataFormatException(String message) {
        super(message);
    }

}
