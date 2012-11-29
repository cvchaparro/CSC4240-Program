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

        // The neural network that will be trained.
        NeuralNetwork ann = null;
        
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
                usrInput = args[0];
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

                if (file != null) {
                    done = true;
                }
            }
        }

        if (file != null) {
            // Confirm that the file was opened.
            System.out.println("\n" + usrInput + " was opened.");
        }

        // Process the file.
        ann = processFile(file);

        if (ann != null) {
            System.out.println("\nNetwork created.\n");
        }
        else {
            System.out.println("\nThere are errors in the input file, network could not be created.\n");
        }
    }

    /**
     * Returns a NeuralNetwork setup with the information gathered from the
     * file object that is passed in.
     * Preconditions: The file object must not be null. And the file must be
     * formatted as follows:
     * filename_with_data.ext
     * number_of_hidden_layers
     * number_of_nodes_in_hidden_layer_1
     * number_of_nodes_in_hidden_layer_2
     * number_of_nodes_in_hidden_layer_3
     *        ...
     * number_of_nodes_in_hidden_layer_n (where n is the number of hidden layers)
     * learning_rate
     * error_tolerance
     *
     * Postconditions: A new NeuralNetwork object will be returned.
     */
    public static NeuralNetwork processFile(FileIO file) {
        // 
        if (file == null) {
            // Do not continue.
            return null;
        }
        else {
            // The learning rate.
            double rate = 0.0;

            // The error tolerance.
            double error = 0.0;

            // Holds the number of hidden layers.
            int numHiddenLayers = 0;

            // An array with the number of nodes in numHiddenLayers number of
            // hidden layers.
            int[] numNodes;

            // Holds the value of the strings read from the file.
            String line = null;

            // Get the name of the file with the data in it.
            line = file.readLine();

            // Open the data file.
            FileIO data = getFileForInput(line);

            // If the file could not be opened, let the user know and quit.
            if (data == null) {
                System.out.println("\n" + line + " could not be opened.");
                return null;
            }

            // Get the number of hidden layers, or nodes-per-hidden layer.
            line = file.readLine();

            // Convert the value to a number.
            try {
                numHiddenLayers = Integer.parseInt(line);

                if (numHiddenLayers < 1) {
                    System.out.println("\nThere must be at least one hidden layer.");
                    return null;
                }

                // Create the numNodes array.
                numNodes = new int[numHiddenLayers];
            }
            catch (NumberFormatException nfe) {
                System.out.println("\n" + line + " is not a valid integer.");
                return null;
            }

            // Get numHiddenLayers number of nodes-per-layer.
            for (int i = 0; i < numHiddenLayers; i++) {
                // Get the number of nodes in the i-th hidden layer.
                line = file.readLine();

                // Convert the value to an integer.
                try {
                    numNodes[i] = Integer.parseInt(line);

                    if (numNodes[i] < 1) {
                        System.out.println("\nThere must be at least one node in a hidden layer.");
                        return null;
                    }
                }
                catch (NumberFormatException nfe) {
                    System.out.println("\n" + line + " is not a valid integer.");
                    return null;
                }
            }

            // Get the learning rate.
            line = file.readLine();

            // Convert the value to a double.
            try {
                if (line != null) {
                    rate = Double.parseDouble(line);
                }
                else {
                    throw new NumberFormatException(line);
                }

                if (rate < 0) {
                    System.out.println("\nThe learning rate provided (" + rate + ") is invalid.");
                    return null;
                }
            }
            catch (NumberFormatException nfe) {
                System.out.println("\n" + line + " is not a valid double.");
                return null;
            }
            
            // Get the learning rate.
            line = file.readLine();

            // Convert the value to a double.
            try {
                if (line != null) {
                    error = Double.parseDouble(line);
                }
                else {
                    throw new NumberFormatException(line);
                }

                if (error < 0) {
                    System.out.println("\nThe learning rate provided (" + rate + ") is invalid.");
                    return null;
                }
            }
            catch (NumberFormatException nfe) {
                System.out.println("\n" + line + " is not a valid double.");
                return null;
            }

            return new NeuralNetwork(data, numHiddenLayers, numNodes, rate, error);
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
