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

        System.out.println("hidden layers: " + numLayers);
        for(int n = 0; n < numNeuronsPerHiddenLayer.length; n++)
        	System.out.println("num in layer " + n + ": " + numNeuronsPerHiddenLayer[n]);
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
                    for(int m = 0; m < numNeuronsPerHiddenLayer[n]; m++) {
                        currLayer.add(new HiddenNeuron());
                    }

                    // Connect the previous layer of neurons to the current
                    // layer of hidden neurons, except for the output neurons.
                    for(int m = 0; m < prevLayer.size(); m++) {
                        for(int l = 0; l < currLayer.size(); l++) {
                            Axon axon = new Axon(prevLayer.get(m), currLayer.get(l));
                            prevLayer.get(m).addAxon(axon);
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
        double currError = 1;
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
        
        inputNeurons.get(0).getAxon(0).setWeight(0.05);
        hiddenNeurons.get(0).get(0).getAxon(0).setWeight(-0.05);
        hiddenNeurons.get(0).get(0).setBias(-0.03);
        outputNeurons.get(0).setBias(0.02);
       

        //System.out.println("error " + (prevError - currError) + " " + error/400);
        while (currError >= error/400) {
            prevError = currError;
            currError = 0;
            
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

                System.out.println("Before Update");
                System.out.println("Input Neuron:");
                System.out.println("Axon Weight: " + inputNeurons.get(0).getAxon(0).getWeight());
                System.out.println("Hidden Neuron: ");
                System.out.println("Axon Weight: " + hiddenNeurons.get(0).get(0).getAxon(0).getWeight());
                System.out.println("Bias: " + hiddenNeurons.get(0).get(0).getBias());
                System.out.println("Output Neuron: ");
                System.out.println("Bias: " + outputNeurons.get(0).getBias());

                // Update the deltas for the output nodes.
                for (int i = 0; i < outputNeurons.size(); i++) {
                    double delta = (outputNeurons.get(i).getPrime() * (p.getY(i) - outputNeurons.get(i).evaluate()));
                	//System.out.println("output delta " + delta);
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
                    	//System.out.println("hidden delta " + n + " " + prime*sum);
                        n.setBias(((prime * sum) * rate) + n.getBias());
                    }
                }

                System.out.println();
                System.out.println("Input Neuron in progress:");
                System.out.println("Bias: " + inputNeurons.get(0).getBias());
                System.out.println("Axon Weight: " + inputNeurons.get(0).getAxon(0).getWeight());
                System.out.println("Input: " + inputNeurons.get(0).getInput());
                System.out.println("Evalutated: " + inputNeurons.get(0).evaluate());
                System.out.println();

                for(Neuron n : inputNeurons)
                {
                	//System.out.println("Printing inputs " + n.getAllAxons().size());
                	for(Axon axon : n.getAllAxons())
                	{
                		double prevWeight = axon.getWeight();
                		axon.setWeight(axon.getWeight() + rate * axon.getPostSynapticNeuron().getDelta() * axon.getPreSynapticNeuron().evaluate());
                		//System.out.println("Input " + n + ": " + (axon.getWeight() - prevWeight));
                	}
                }
                
                for(List<Neuron> list : hiddenNeurons)
                {
                	for(Neuron n : list)
                	{
                    	for(Axon axon : n.getAllAxons())
                    	{
                    		double prevWeight = axon.getWeight();
                    		axon.setWeight(axon.getWeight() + rate * axon.getPostSynapticNeuron().getDelta() * axon.getPreSynapticNeuron().evaluate());
                    		//System.out.println("Hidden " + n + ": " + (axon.getWeight() - prevWeight));
                    	}
                	}
                }

                System.out.println("After Update");
                System.out.println("Input Neuron:");
                System.out.println("Axon Weight: " + inputNeurons.get(0).getAxon(0).getWeight());
                System.out.println("Hidden Neuron: ");
                System.out.println("Axon Weight: " + hiddenNeurons.get(0).get(0).getAxon(0).getWeight());
                System.out.println("Bias: " + hiddenNeurons.get(0).get(0).getBias());
                System.out.println("Output Neuron: ");
                System.out.println("Bias: " + outputNeurons.get(0).getBias());
                
                // Re-reverse the list.
                Collections.reverse(hiddenNeurons);

                // Update the weights
                for(int i = 0; i < p.numY(); i++)
                {
                    double actualY = p.getY(i);
                    double predictedY = ((OutputAxon) outputNeurons.get(i).getAxon(0)).getPostSynaptic();
                    currError += Math.abs(actualY - predictedY);
                    System.out.println("actual y:    " + actualY);
                    System.out.println("predicted y: " + predictedY + "\n");
                }
            }
            
            System.out.println("Error: " + currError);

        }
    }
}