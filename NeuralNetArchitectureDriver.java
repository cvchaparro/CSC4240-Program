import java.util.ArrayList;
import java.util.List;

public class NeuralNetArchitectureDriver {
    private static double validationError = Double.MAX_VALUE;
    private static Architecture arch = null;

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
            //System.out.println("Usage: java NeuralNetArchitectureDriver <architecture_file>");
            return;
        }

        // Open the file.
        try {
            file = new FileIO(args[0], FileIO.FOR_READING);

            //System.out.println(args[0] + " was opened.");
        }
        catch (FileIOException fioe) {
            //System.out.println(fioe);
            return;
        }

        // ----------------
        // Parse the file.
        // ----------------

        String line = "";
        String filename = "";
        FileData fData = null;
        try {
            // Get the filename of the file with the data points.
            line = file.readLine();

            if (line.isEmpty()) {
                return;
            }

            // Open the data file.
            FileIO data = new FileIO(line, FileIO.FOR_READING);

            fData = NeuralNetDriver.parseDataFile(data);
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

        oneHiddenLayer(points, bins, rate, error, fData.scale);

        twoHiddenLayers(points, bins, rate, error, fData.scale);

        System.out.println("K-Fold Cross Validation Error: " + validationError);
        System.out.println(arch);
    }

    public static void oneHiddenLayer(List<PointN> points, int numBins, double rate, double error, double scale) {
        int[] neuronsPerLayer = new int[1];

        for (int i = 0; i < 5;) {
            neuronsPerLayer[0] = ++i;
            NeuralNetwork ann = new NeuralNetwork(points.get(0).numX(), points.get(0).numY(), points, neuronsPerLayer.length, neuronsPerLayer, rate, error, scale);
            double tmp = crossValidate(ann, points, numBins);
            if (tmp <= validationError) {
                validationError = tmp;
                arch = new Architecture(1, neuronsPerLayer);
            }
        }
    }

    public static void twoHiddenLayers(List<PointN> points, int numBins, double rate, double error, double scale) {
        int[] neuronsPerLayer = new int[2];

        for (int i = 0; i < neuronsPerLayer.length;) {
            neuronsPerLayer[0] = ++i;
            for (int j = 0; j < 5;) {
                neuronsPerLayer[1] = ++j;
                NeuralNetwork ann = new NeuralNetwork(points.get(0).numX(), points.get(0).numY(), points, neuronsPerLayer.length, neuronsPerLayer, rate, error, scale);
                double tmp = crossValidate(ann, points, numBins);
                if (tmp <= validationError) {
                    validationError = tmp;
                    arch = new Architecture(2, neuronsPerLayer);
                }
            }
        }
    }

    public static double crossValidate(NeuralNetwork ann, List<PointN> points, int numBins) {
        List< List<PointN> > partitioned = partition(points, numBins);
        List<Double> predicted = null;
        double valError = 0;

        for (int validationBin = 0; validationBin < numBins; validationBin++) {
            for (List<PointN> bin : partitioned) {
                if (partitioned.indexOf(bin) != validationBin) {
                    ann.learn(bin);
                }
            }

            List<Double> inputs = new ArrayList<Double>();
            for (int i = 0; i < partitioned.get(validationBin).size(); i++) {
                for (int j = 0; j < partitioned.get(validationBin).get(i).numX(); j++) {
                    inputs.add(partitioned.get(validationBin).get(i).getX(j));
                }
            }

            predicted = ann.evaluate(inputs);

            for (int i = 0; i < points.size(); i++) {
                if (partitioned.get(validationBin).contains(points.get(i))) {
                    for (int ptIndex = 0; ptIndex < partitioned.get(validationBin).size(); ptIndex++) {
                        for (int j = 0; j < points.get(i).numY(); j++) {
                            double actualY = points.get(i).getY(j);
                            double predictedY = partitioned.get(validationBin).get(ptIndex).getY(j);
                            valError += Math.abs(actualY - predictedY);
                        }
                    }
                }
            }
        }

        return (valError / numBins);
    }

    public static List< List<PointN> > partition(List<PointN> points, int numBins) {
        List< List<PointN> > dataSets = new ArrayList< List<PointN> >();
        List<PointN> tmpList = copy(points);

        for (int i = 0; i < numBins; i++) {
            dataSets.add(new ArrayList<PointN>());
        }

        int binIndex = 0;

        while (tmpList.size() > 0) {
            int index = Random.randomInt(0, tmpList.size() - 1);
            dataSets.get(binIndex).add(tmpList.get(index));
            binIndex++;
            tmpList.remove(index);

            if (binIndex == numBins) {
                binIndex = 0;
            }
        }

        return dataSets;
    }

    public static List<PointN> copy(List<PointN> list) {
        List<PointN> newList = new ArrayList<PointN>();

        for (int i = 0; i < list.size(); i++) {
            newList.add(list.get(i));
        }

        return newList;
    }
}