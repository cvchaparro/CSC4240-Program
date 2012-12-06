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
    // Initialise everything to "zero".
    public InputNeuron() {
        super();
        activation = new Identity();
    }

    public InputNeuron(List<Axon> axons, Activation activation, double input) {
        super(axons, activation, input, 0);
    }

    @Override
    public double evaluate()
    {
    	return input;
    }
    
    @Override
    public boolean sendActionPotential() {
    	//System.out.println("Sending inputs to " + axons.size() + " neurons");
        for(Axon axon : axons) {
            axon.sendActionPotential(input);
        }
        return true;
    }

    @Override
    public void setBias(double bias) {}

    @Override
    public double getBias() {
        return 0;
    }
}

