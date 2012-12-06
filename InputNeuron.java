/**
 * This file contains the InputNeuron class which is, in a sense, an equivalent
 * of a root node in a tree. Input neurons are the ones which, as the name
 * suggests, get input but the input is the original data itself.
 *
 * Name: Cameron Vincent Chaparro.
 * Date: 23 November 2012
 */

import java.util.ArrayList;
import java.util.List;

public class InputNeuron extends Neuron {
    // The activation function that will be used to determine when to send an
    // action potential to the neurons connected to this neuron.
    private Activation activation;
    // A list of Axons we have between other neurons and ourselves.
    private List<Axon> axons;
    // The initial input value.
    private double input;

    // Initialise everything to "zero".
    public InputNeuron() {
        super();
    }

    public InputNeuron(List<Axon> axons, Activation activation, double input) {
        super(axons, activation, input, 0);
    }

    @Override
    public void setBias(double bias) {}

    @Override
    public double getBias() {
        return 0;
    }
}

