import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {
    private List<Neuron> inputNeurons;
    private List<Neuron> outputNeurons;
    private List< List<Neuron> > hiddenNeurons;
    private double rate;
    private double error;
    private double scale;

    @SuppressWarnings("unchecked")
    public NeuralNetwork(FileData data, int numLayers, int[] numNeuronsPerHiddenLayer, double rate, double error) {
        boolean valid = true;
        List<Object> list = null;

        inputNeurons = new ArrayList<Neuron>();
        outputNeurons = new ArrayList<Neuron>();
        hiddenNeurons = new ArrayList< List<Neuron> >();
        List<Neuron> prevLayer = inputNeurons;

        if (data != null) {
            if (data.numItems() == 4) {
                list = data.getAllData();

                // Make sure the list is not null.
                if (list == null) {
                    list = new ArrayList<Object>();
                    list.add(-1);
                    list.add(-1);
                    list.add(-1);
                    list.add(null);
                    valid = false;
                }

                // Get the scale factor.
                scale = ((Double) list.get(0));

                // Get the number of input neurons, and add that many
                // InputNeuron objects to the inputNeurons list.
                int numInput = ((Integer) list.get(1));
                for(int n = 0; n < numInput; n++) {
                    inputNeurons.add(new InputNeuron());
                }

                // Get the number of output neurons, and add that many
                // OutputNeuron objects to the outputNeurons list.
                int numOutput = ((Integer) list.get(2));
                for(int n = 0; n < numOutput; n++) {
                    outputNeurons.add(new OutputNeuron());
                }

                // Get the number of hidden layers, and add that many lists of
                // hidden layers to the hiddenNeurons list.
                int numHiddenLayers = numLayers;
                for(int n = 0; n < numHiddenLayers; n++) {
                    List<Neuron> currLayer = new ArrayList<Neuron>();
                    for(int m = 0; m < numNeuronsPerHiddenLayer.length; m++) {
                        currLayer.add(new HiddenNeuron());
                    }

                    for(int m = 0; m < prevLayer.size(); m++) {
                        for(int l = 0; l < currLayer.size(); l++) {
                            prevLayer.get(m).connect(currLayer.get(l));
                        }
                    }
                    hiddenNeurons.add(currLayer);
                    prevLayer = currLayer;
                }

                for(int n = 0; n < prevLayer.size(); n++) {
                    for(int m = 0; m < outputNeurons.size(); m++) {
                        prevLayer.get(n).connect(outputNeurons.get(m));
                    }
                }
            }
        }
        else {
            valid = false;
        }

        this.rate = rate;
        this.error = error;

        if (valid) {
            List<PointN> points = ((List<PointN>) list.get(3));
            this.learn(points);
        }
    }

    /**
     * Performs back-propogation for a multi-layer neural network.
     *
     * Preconditions: The list of points cannot be empty.
     * Postconditions: The neural network will be able to approximate the
     * underlying function of a set of data.
     */
    public NeuralNetwork learn(List<PointN> points) {
        // The value used to adjust weights.
        double delta = 0.0;

        // Initialise all the weights to random values between -1.0 and 1.0.
        for (Neuron n : inputNeurons) {
            n.setWeight(Random.randomFloat(-1, 1));
        }
        for (Neuron n : outputNeurons) {
            n.setWeight(Random.randomFloat(-1, 1));
        }
        for (List<Neuron> l : hiddenNeurons) {
            for (Neuron n : hiddenNeurons.get(hiddenNeurons.indexOf(l))) {
                n.setWeight(Random.randomFloat(-1, 1));
            }
        }

        // Do something else...

        return this;
    }
}
