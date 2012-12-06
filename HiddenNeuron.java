/**
 * This file contains the HiddenNeuron class.
 *
 * Name: Cameron Vincent Chaparro.
 * Date: 23 November 2012
 */

import java.util.ArrayList;
import java.util.List;

public class HiddenNeuron extends Neuron {
    // Initialise everything to "zero".
    public HiddenNeuron() {
        super();
    }

    /**
     * Initialise all instance variables to the values of the specified
     * parameters.
     *
     * Preconditions: None.
     * Postconditions: A new HiddenNeuron object will be initialsed.
     */
    public HiddenNeuron(List<Axon> axons, Activation activation, double input, double bias) {
        super(axons, activation, input, bias);
    }
}
