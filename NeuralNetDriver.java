/**
 * This file contains the ANNDriver class which is responsible for interfacing
 * an Artificial Neural Network and a user.
 *
 * Author: Cameron Vincent Chaparro.
 * Date: 23 November 2012
 */

public class NeuralNetDriver {
    // Used to get keyboard input from the user.
    private final static Keyboard kb = Keyboard.getKeyboard();

    /**
     * Execution starts here: The user will be prompted to enter the name of a
     * text file containing data. Also, the ANN will be set up.
     *
     * Preconditions: None.
     * Postconditions: The ANN will be created, set up, used to evaluate the
     * data specified by the user, and displayed in some form. Afterwhich, the
     * program will terminate.
     */
    public static void main(String[] args) {
        // Determines when to stop one of the input loops.
        boolean done = false;

        // Determines when to stop one of the validation loops.
        boolean valid = false;

        // The file with the data that will be analyzed by the ANN.
        FileIO file = null;

        // A prompt to the user.
        String prompt = "";

        // A string that holds the user's input.
        String usrInput = "";

        // If the user entered a parameter use it as a filename and try to open
        // the file it specifies. (Note: If the parameter is not a filename, the
        // user will be prompted for a filename.
        if (args.length >= 1) {
            // Get the file.
            file = getFileForInput(args[0]);

            if (file != null) {
                done = true;
            }
        }

        while (!done) {
            // Initialise the prompt string.
            prompt = "Enter the name of the data file whose data will be ";
            prompt += "evaluated (Q to Quit):   ";

            // Get the string input from the user.
            usrInput = getStringInput(prompt);

            // Check usrInput for the sentinel value.
            if (usrInput.equalsIgnoreCase("Q") ||
                usrInput.equalsIgnoreCase("QUIT")) {
                done = true;
            }
            // Open the specified file.
            else {
                file = getFileForInput(usrInput);
            }
        }
    }

    /**
     * Returns a string of user input from the keyboard.
     * Preconditions: None.
     * Postcondition: A string of the user's input will be returned.
     */
    public static String getStringInput(String prompt) {
      // Return the user's input
      return (kb.readString(prompt));
    }
  
    /**
     * Opens a file with the name specified through the parameter, for input.
     * Preconditions: The file must exist.
     * Postcondition: The file with the specified filename will be opened for input and
     * returned, or if it cannot be opened, null will be returned.
     */
    public static FileIO getFileForInput(String filename) {
      // Declare a FileIO object, this is the object that will be returned.
      FileIO inputFile = null;
    
      try {
        // Try to open the file for input.
        inputFile = new FileIO(filename, FileIO.FOR_READING);
      }
      catch (FileIOException fioe) {
        System.out.println("\n" + fioe + "\b, please try another file name.\n");
      }

      // Return the file, or null if it was not opened.
      return inputFile;
    }
}
