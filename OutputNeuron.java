/**
 * This file contains the OutputNeuron class.
 *
 * Name: Cameron Vincent Chaparro.
 * Date: 23 November 2012
 */

import java.util.ArrayList;
import java.util.List;

public class OutputNeuron extends Neuron {
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
    public OutputNeuron(List<OutputAxon> axons, Activation activation, double input, double bias) {
        super(null, activation, input, bias);
    }

    public boolean sendActionPotential() {
        double potential = this.evaluate();
        for(Axon axon : axons) {
            ((OutputAxon) axon).sendActionPotential(potential);
        }
        return true;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public double getBias() {
        return bias;
    }
}
