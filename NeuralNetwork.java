/**
 * This file contains the NeuralNetwork class.
 *
 * Authors: Cameron Chaparro.
 *          Keith Manning.
 * Date: 3 December 2012
 */

import java.util.Collections;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.lang.Math;

/**
 * The NeuralNetwork class is responsible for implementing the learning algorithm.
 */
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
            if (data.numItems() == 3) {
                list = data.getAllData();

                // Make sure the list is not null.
                if (list == null) {
                    list = new ArrayList<Object>();
                    list.add(-1);
                    list.add(-1);
                    list.add(null);
                    valid = false;
                }

                // Get the number of input neurons, and add that many
                // InputNeuron objects to the inputNeurons list.
                int numInput = ((Integer) list.get(0));
                for(int n = 0; n < numInput; n++) {
                    inputNeurons.add(new InputNeuron());
                }

                // Get the number of output neurons, and add that many
                // OutputNeuron objects to the outputNeurons list.
                int numOutput = ((Integer) list.get(1));
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

                    // Connect the previous layer of neurons to the current
                    // layer of hidden neurons, except for the output neurons.
                    for(int m = 0; m < prevLayer.size(); m++) {
                        for(int l = 0; l < currLayer.size(); l++) {
                            Axon axon = new Axon(prevLayer.get(m), currLayer.get(l));
                            currLayer.get(l).addAxon(axon);
                        }
                    }

                    // Move on to the next layer.
                    hiddenNeurons.add(currLayer);
                    prevLayer = currLayer;
                }

                // Connect the last layer of hidden neurons to the output
                // neurons.
                for(int n = 0; n < prevLayer.size(); n++) {
                    for(int m = 0; m < outputNeurons.size(); m++) {
                        Axon axon = new Axon(prevLayer.get(n), outputNeurons.get(m));
                        prevLayer.get(n).addAxon(axon);
                    }
                }

                for (int i = 0; i < outputNeurons.size(); i++) {
                    outputNeurons.get(i).addAxon(new OutputAxon(outputNeurons.get(i), null));
                }
            }
        }
        else {
            valid = false;
        }

        this.rate = rate;
        this.error = error;

        if (valid) {
            List<PointN> points = ((List<PointN>) list.get(2));
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
    public void learn(List<PointN> points) {
        // The previous errors.
        double prevError = 0;
        // The current errors.
        double currError = 0;
        double rangeMin = -0.1;
        double rangeMax =  0.1;
        Random random = new Random();

        // Initialise all the weights to random values between -1.0 and 1.0.
        for (int i = 0; i < inputNeurons.size(); i++) {
            for (int j = 0; j < inputNeurons.get(i).numAxons(); j++) {
                inputNeurons.get(i).getAxon(j).setWeight(new Double(rangeMin + (rangeMax - rangeMin) * random.nextDouble()));
            }
        }
        for (int i = 0; i < hiddenNeurons.size(); i++) {
            for (int j = 0; j < hiddenNeurons.get(i).size(); j++) {
                for (int k = 0; k < hiddenNeurons.get(i).get(j).numAxons(); k++) {
                    hiddenNeurons.get(i).get(j).getAxon(k).setWeight(new Double(rangeMin + (rangeMax - rangeMin) * random.nextDouble()));
                }
            }
        }

        while (Math.abs(prevError - currError) >= error) {
            // Go through each point in the points list and propogate the
            // inputs forward.
            for (PointN p : points) {
                // Assign the x-values to the input layers.
                for (int i = 0; i < inputNeurons.size(); i++) {
                    inputNeurons.get(i).receiveActionPotential(p.getX(i));
                }

                // Send the action potential for each of the input neurons to its connected neurons.
                for (int i = 0; i < inputNeurons.size(); i++) {
                    inputNeurons.get(i).sendActionPotential();
                }

                // Assign values to the hidden layers.
                for (int i = 0; i < hiddenNeurons.size(); i++) {
                    for (int j = 0; j < hiddenNeurons.get(i).size(); j++) {
                        hiddenNeurons.get(i).get(j).sendActionPotential();
                    }
                }

                // Assign the values to the output layer.
                for (int i = 0; i < outputNeurons.size(); i++) {
                    outputNeurons.get(i).sendActionPotential();
                }

                // Update the deltas for the output nodes.
                for (int i = 0; i < outputNeurons.size(); i++) {
                    double delta = (outputNeurons.get(i).getPrime() * (p.getY(i) - outputNeurons.get(i).evaluate()));
                    outputNeurons.get(i).setDelta(delta);
                    outputNeurons.get(i).setBias(outputNeurons.get(i).getBias() + (rate * delta));
                }

                // Reverse the list so we can go from the back towards the input neurons.
                Collections.reverse(hiddenNeurons);

                // Update the deltas for the hidden nodes.
                for (List<Neuron> layer : hiddenNeurons) {
                    for (Neuron n : layer) {
                        double sum = 0;
                        for (Axon axon : n.getAllAxons()) {
                            sum += (axon.getWeight() * axon.getPostSynapticNeuron().getDelta());
                        }
                        double prime = n.getPrime();
                        n.setDelta(prime * sum);
                        n.setBias(((prime * sum) * rate) + n.getBias());
                    }
                    }

                // Re-reverse the list.
                Collections.reverse(hiddenNeurons);

                // Update the weights.
                //for (

                for(int i = 0; i < p.numY(); i++)
                {
                    double actualY = p.getY(i);
                    double predictedY = ((OutputAxon) outputNeurons.get(i).getAxon(0)).getPostSynaptic();
                    currError += Math.abs(actualY - predictedY);
                    System.out.println("actual y:    " + actualY);
                    System.out.println("predicted y: " + predictedY + "\n");
                }
            }
}
