import static java.lang.System.exit;
/**
 * Institution: University of Newcastle
 * Programmer:  Ben Sutter
 * Course Code: COMP2230
 * UID: 3063467
 * Assignment 1
 * c3063467A1 Class
 * c3063467A1.java
 * Last Modified: 31/10/2016
 */
public class c3063467A1 {
    public static void main(String[] args) {
        // Check input file params
        if (args.length < 1) {
            System.out.println("Error: No input file provided. Please run again with a input param.");
            exit(1);
        }
        // Run the program
        Interface intFace = new Interface(args[0]);
        intFace.run();
    }
}
