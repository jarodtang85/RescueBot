import java.util.Scanner;
// below code used the template for a Singleton pattern outlined in this website: https://www.javatpoint.com/singleton-design-pattern-in-java 
/**
 * Singleton class providing a single instance of the Scanner object.
 */
public class ScannerSingleton {
   private static Scanner scanner;

   /**
    * Retrieves the instance of the Scanner object.
    *
    * @return The instance of Scanner.
    */
   public static Scanner getScanner() {
      if (scanner == null) { // checks if scanner has been initialized
         // otherwise, create new scanner object
         scanner = new Scanner(System.in);
      }

      return scanner;
   }
}

