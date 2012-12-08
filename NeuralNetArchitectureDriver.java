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

        System.out.println("Done.");
    }

    public static void crossValidateWrapper(List<PointN> points, int bins, double rate, double error) {
        // Cross validation algorithm on page 710 - Figure 18.8

        // Error rate for the training set.
        double[] errors = new double[2];

        // Holds the best size so far.
        int best = 0;

        for (int i = 1; i <= 1; i++) {
            errors = crossValidate(points, i, bins);

            if (Math.abs(errors[0]) < error) {
                //best = 
            }
        }
    }

    public static double[] crossValidate(List<PointN> points, int size, int bins) {
        double errT = 0, errV = 0;
        List< List<PointN> > data = null;

        for (int fold = 1; fold <= 1; fold++) {//size; fold++) {
            data = partition(points, bins);

            System.out.println("\n\nTraining set...");
            for (int i = 0; i < data.get(0).size(); i++) {
                System.out.println("val: " + data.get(0).get(i));
            }
            //hypothesis = 
        }

        return new double[2];
    }

    public static List< List<PointN> > partition(List<PointN> points, int numBins) {
        // Used to determine if an index has already been taken.
        boolean taken = false;
        boolean done = false;

        // Holds a list of the used indices (prevents placing points in
        // duplicated indices).
        int[] used = new int[points.size()];
        // Holds a random index between 0 and the size of the points list.
        int index = 0;

        // A list of bins number of PointN arrays that will represent the partitions.
        List< List<PointN> > dataSets = new ArrayList< List<PointN> >();
        for (int i = 0; i < numBins; i++) {
            dataSets.add(new ArrayList<PointN>());
        }

        used[index] = -1;
        System.out.println("numBins = " + numBins);
        // Go through the list of "bins" and put random points in them.
        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < numBins; j++) {
                int count = 0;
                // Make sure the generated index has not already been put into a bin.
                while (used[index] == index || used.length == count) {
                    // Get the next randomly generated index.
                    index = Random.randomInt(0, points.size() - 1);
                    count++;

                    // If we have gone through all the elements in the list and
                    // still there is no empty index, we are done.
                    if (used.length >= count) {
                        clear(used);
                    }
                }

                // Mark index in the used array.
                used[index] = index;
                dataSets.get(j).add(points.get(index));
            }
        }
        System.out.println("dataSets.size() = " + dataSets.size());
        for (List<PointN> list : dataSets) {
            System.out.println("\nNext bin...");
            for (PointN point : list) {
                for (int x = 0; x < point.numX(); x++)
                    System.out.println("X: = " + point.getX(x));

                for (int x = 0; x < point.numY(); x++)
                    System.out.println("Y: = " + point.getY(x));
                System.out.println();
            }
        }

        return dataSets;
    }

    public static void clear(int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = -1;
        }
    }
}