import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


/**

* Class for creating a new log file.

*/
public class CreateLog {

    /**
    * Creates a new log file with the specified filename.

    * @param filename The name of the log file to create.
    */
    public void newLog(String filename) {

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {

        } catch(IOException e) {
            System.out.println("An error occurred while creating the log file.");
            e.printStackTrace();  
        }
    }

}

