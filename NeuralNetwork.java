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

    private final static int kfold = 10;

    private List<Neuron> inputNeurons;
    private List<Neuron> outputNeurons;
    private List< List<Neuron> > hiddenNeurons;
    private double rate;
    private double error;
    private double scale;

    public NeuralNetwork(int numInput, int numOutput, List<PointN> points, int numLayers, int[] numNeuronsPerHiddenLayer, double rate, double error, double scale) {
        List<Object> list = null;

        this.scale = scale;
        inputNeurons = new ArrayList<Neuron>();
        outputNeurons = new ArrayList<Neuron>();
        hiddenNeurons = new ArrayList< List<Neuron> >();
        List<Neuron> prevLayer = inputNeurons;


        //System.out.println("hidden layers: " + numLayers);
        for(int n = 0; n < numNeuronsPerHiddenLayer.length; n++) {
            //System.out.println("num in layer " + n + ": " + numNeuronsPerHiddenLayer[n]);
        }
        // Get the number of input neurons, and add that many
        // InputNeuron objects to the inputNeurons list.
        //for(int i = 0; i < numInput; i++) {
        for(int i = 0; i < points.get(0).numX(); i++) {
            inputNeurons.add(new InputNeuron());
        }

        // Get the number of output neurons, and add that many
        // OutputNeuron objects to the outputNeurons list.
        //for(int i = 0; i < numOutput; i++) {
        for(int i = 0; i < points.get(0).numY(); i++) {
            outputNeurons.add(new OutputNeuron());
        }

        // Set up the hidden layers.
        setNumHiddenLayers(numLayers, numNeuronsPerHiddenLayer);
        /*
          System.out.println("input size " + inputNeurons.size());
          System.out.println("output size " + hiddenNeurons.size());
          System.out.println("hidden size " + outputNeurons.size());
          System.out.println("inputNeurons.size() " + inputNeurons.size());*/
        //System.out.println("numLayer");
        //System.out.println("points.size() = " + points.size());
        //System.out.println("points.get(0).numX() = " + points.get(0).numX());
        
        setRandomWeights();
        
        for (int i = 0; i < outputNeurons.size(); i++) {
            outputNeurons.get(i).addAxon(new OutputAxon(outputNeurons.get(i), null));
        }
        
        this.rate = rate;
        this.error = error;
    }

    @SuppressWarnings("unchecked")
    public NeuralNetwork(FileData data, int numLayers, int[] numNeuronsPerHiddenLayer, double rate, double error) {
        this(((Integer) data.getData(0)), ((Integer) data.getData(1)), ((List<PointN>) data.getData(2)), numLayers, numNeuronsPerHiddenLayer, rate, error, data.scale);
    }
    
    public void setRandomWeights()
    {
        double rangeMin = -0.1;
        double rangeMax =  0.1;
        Random random = new Random();

        // Initialize all the weights to random values between -1.0 and 1.0.
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
    }
    
    public List<Double> evaluate(List<Double> input)
    {
    	List<Double> outputs = new ArrayList<Double>();
    	

    	for(Neuron n : inputNeurons)
    	{
    		n.clearInput();
    	}
    	
    	for(List<Neuron> list : hiddenNeurons)
    	{
    		for(Neuron n : list)
    		{
    			n.clearInput();
    		}
    	}
    	
    	for(Neuron n : outputNeurons)
    	{
    		n.clearInput();
    	}

        // Assign the x-values to the input layers.
        for (int i = 0; i < inputNeurons.size(); i++) {
            //System.out.println("inputNeurons.size() = " + inputNeurons.size() + ": input.size() = " + input.size());
            inputNeurons.get(i).receiveActionPotential(input.get(i));
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
            outputs.add(((OutputAxon) outputNeurons.get(i).getAxon(0)).getPostSynaptic());
        }

    	return outputs;
    }
    
    public double updateWeights(List<Double> yValues)
    {
    	double error = 0;
    	
        // Update the deltas for the output nodes.
        for (int i = 0; i < outputNeurons.size(); i++) {
            error += Math.abs(yValues.get(i) - outputNeurons.get(i).evaluate());
            double delta = (outputNeurons.get(i).getPrime() * (yValues.get(i) - outputNeurons.get(i).evaluate()));
            outputNeurons.get(i).setDelta(delta);
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
        		//System.out.println("hidden derivative " + prime);
        		//System.out.println("hidden input " + n.input);
            	//System.out.println("hidden delta " + n + " " + prime*sum);
            }
        } 
        // Re-Reverse the list
        Collections.reverse(hiddenNeurons);

        for(Neuron n : inputNeurons)
        {
        	//System.out.println("Printing inputs " + n.getAllAxons().size());
        	for(Axon axon : n.getAllAxons())
        	{
        		axon.setWeight(axon.getWeight() + rate * axon.getPostSynapticNeuron().getDelta() * axon.getPreSynapticNeuron().evaluate());
        	}
        }
        
        for(List<Neuron> list : hiddenNeurons)
        {
        	for(Neuron n : list)
        	{
            	for(Axon axon : n.getAllAxons())
            	{
            		axon.setWeight(axon.getWeight() + rate * axon.getPostSynapticNeuron().getDelta() * axon.getPreSynapticNeuron().evaluate());
            	}
        	}
        }
        
        // Update biases
        for(Neuron n : outputNeurons)
        {
            n.setBias(n.getBias() + (n.getDelta() * rate));
        }

        // Update the deltas for the hidden nodes.
        for (List<Neuron> layer : hiddenNeurons) {
            for (Neuron n : layer) {
                n.setBias(n.getBias() + (n.getDelta() * rate));
            }
        } 
        
        return error;
    }

    /**
     * Performs back-propogation for a multi-layer neural network.
     *
     * Preconditions: The list of points cannot be empty.
     * Postconditions: The neural network will be able to approximate the
     * underlying function of a set of data.
     */
    public void learn(List<PointN> points) {
        // The current errors.
        double currError = Double.MAX_VALUE;

        while (currError >= error/scale) {
            currError = 0;
            
            // Go through each point in the points list and propogate the
            // inputs forward.
            for (PointN p : points) {
            	
            	List<Double> inputs = new ArrayList<Double>();
            	for(int n = 0; n < p.numX(); n++)
            		inputs.add(p.getX(n));
            	List<Double> outputs = evaluate(inputs);
                
            	List<Double> actualOutputs = new ArrayList<Double>();
            	for(int n = 0; n < p.numY(); n++)
            		actualOutputs.add(p.getY(n));
            	updateWeights(actualOutputs);
            	
                // Update the weights
                for(int i = 0; i < outputs.size(); i++)
                {
                    double actualY = p.getY(i);
                    double predictedY = outputs.get(i);
                    currError += Math.abs(actualY - predictedY);
                }
            }
        }

        for (PointN p : points) {
            List<Double> inputs = new ArrayList<Double>();
            for(int n = 0; n < p.numX(); n++)
                inputs.add(p.getX(n));
            List<Double> outputs = evaluate(inputs);
                
            List<Double> actualOutputs = new ArrayList<Double>();
            for(int n = 0; n < p.numY(); n++)
                actualOutputs.add(p.getY(n));
            for (int i = 0; i < p.numX(); i++)
                System.out.println("input x:     " + p.getX(i));
            for (int i = 0; i < p.numY(); i++) {
                System.out.println("actual y:    " + p.getY(i));
                System.out.println("predicted y: " + outputs.get(i) + "\n");
            }
        }

        System.out.println("Error: " + currError);
    }

    public void setNumHiddenLayers(int numLayers, int[] numNeuronsPerHiddenLayer) {
        List<Neuron> prevLayer = inputNeurons;

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
    }
}