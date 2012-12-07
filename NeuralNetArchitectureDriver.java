import java.util.ArrayList;
import java.util.List;

public class NeuralNetArchitectureDriver {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        // The architecture file.
        FileIO file = null;

        // The number of bins to use for cross validation.
        int bins = 0;
        // The learning rate to use.
        double rate = 0;
        // The error tolerance to use.
        double error = 0;
        // List of data points.
        List<PointN> points = new ArrayList<PointN>();

        // Check the command-line arguments.
        if (args.length <= 0) {
            System.out.println("Usage: java NeuralNetArchitectureDriver <architecture_file>");
            return;
        }

        // Open the file.
        try {
            file = new FileIO(args[0], FileIO.FOR_READING);

            System.out.println(args[0] + " was opened.");
        }
        catch (FileIOException fioe) {
            System.out.println(fioe);
            return;
        }

        // ----------------
        // Parse the file.
        // ----------------

        String line = "";
        String filename = "";

        try {
            // Get the filename of the file with the data points.
            line = file.readLine();

            if (line.isEmpty()) {
                return;
            }

            // Open the data file.
            FileIO data = new FileIO(line, FileIO.FOR_READING);

            FileData fData = NeuralNetDriver.parseDataFile(data);
            points = ((List<PointN>) fData.getData(2));


            // Get the number of cross-validation bins.
            line = file.readLine();
            bins = NeuralNetDriver.convertStringToInteger(line, bins + " is not a valid integer.");

            // Get the learning rate.
            line = file.readLine();
            rate = NeuralNetDriver.convertStringToDouble(line, rate + " is not a valid double.");

            // Get the error tolerance.
            line = file.readLine();
            error = NeuralNetDriver.convertStringToDouble(line, error + " is not a valid double.");
        }
        catch (FileIOException fioe) {
            System.out.println(fioe);
            return;
        }

        crossValidateWrapper(points, bins, rate, error);
    }

    public static void crossValidateWrapper(List<PointN> points, int bins, double rate, double error) {
        // Cross validation algorithm on page 710 - Figure 18.8

        // Error rate for the training set.
        double[] error = new double[2];

        // Holds the best size so far.
        int best = 0;

        for (int i = 1; i < Infinity; i++) {
            error = crossValidate(points, i, bins);

            if (Math.abs(error[0]) < error) {
                best = 
            }
        }
    }

    public static double[] crossValidate(points, size, bins) {
        System.out.println("Cross validating...");
    }
}