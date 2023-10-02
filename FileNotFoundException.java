import java.io.IOException;

/**
 * Custom exception class for indicating a file not found error.
 */
public class FileNotFoundException extends RuntimeException {

    /**
     * Constructs a new FileNotFoundException with the specified error message.
     *
     * @param message The error message.
     */
    public FileNotFoundException(String message) {
        super(message);
    }

}
