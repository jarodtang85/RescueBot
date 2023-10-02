import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Utility class for printing the contents of a file as ASCII text.
 */
public class PrintAscii {

    /**
     * Prints the contents of a file.
     *
     * @param filename The name of the file to print.
     */
    public void printFile(String filename) {
        try {
            // Read all lines from the file and save them in a List of Strings
            List<String> lines = Files.readAllLines(Paths.get(filename));

            // Print all lines
            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            // Handle the exception
            System.out.println("Either could not find the file or an error occurred when attempting to read it in.");
            e.printStackTrace();
        }
    }

}

