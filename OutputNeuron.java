/**
 * This file contains the OutputNeuron class.
 *
 * Name: Cameron Vincent Chaparro.
 * Date: 23 November 2012
 */

import java.util.ArrayList;
import java.util.List;

public class OutputNeuron extends Neuron {
    // The activation function that will be used to determine when to send an
    // action potential to the neurons connected to this neuron.
    private Activation activation;
    // A list of Axons we have between other neurons and ourselves.
    private List<Axon> axons;
    // The bias input value.
    private double bias;
    // The initial input value.
    private double input;

    // Initialise everything to "zero".
    public OutputNeuron() {
        super();
    }

    /**
     * Initialise all instance variables to the values of the specified
     * parameters.
     *
     * Preconditions: None.
     * Postconditions: A new OutputNeuron object will be initialsed.
     */
    public OutputNeuron(List<Axon> axons, Activation activation, double input, double bias) {
        super(axons, activation, input, bias);
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public double getBias() {
        return bias;
    }
}
