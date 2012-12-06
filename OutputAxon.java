/**
 * This file contains the Axon class which represents a connection between
 * two distinct neurons.
 *
 * Author: Cameron Vincent Chaparro.
 * Date: 23 November 2012
 */

/**
 * The Axon class stores information regarding two connected neurons.
 */
public class OutputAxon extends Axon {
    // The output of this output node.
    private double output;

    // Initialises everything to "zero".
    public OutputAxon() {
        super();
        output = 0;
        weight = 1;
    }

    /**
     * Initialise all instance variables to the values of the specified
     * parameters.
     * UPDATE!
     * Preconditions: Neither of the pre- or post-synaptic neurons can be null
     * if the axon is connecting an input neuron to a hidden layer, a hidden
     * layer to another hidden layer, or a hidden layer to an output layer. The
     * pre-synaptic neuron can only be null if it is connecting an input neuron.
     * And the post-synaptic neuron can only be null if it is connecting an
     * output neuron. Also, the pre- and post-synaptic neruons cannot be equal.
     * Postconditions: If no errors are detected, a new Axon object will be
     * initialsed.
     */
    public OutputAxon(Neuron preSynaptic, Neuron postSynaptic, double actionPotential, double weight) {
        super(preSynaptic, null, actionPotential, weight);
        output = 1;
    }

    /**
     * Initialise all instance variables to the values of the specified
     * parameters.
     *
     * Preconditions: Neither of the pre- or post-synaptic neurons can be null
     * if the axon is connecting an input neuron to a hidden layer, a hidden
     * layer to another hidden layer, or a hidden layer to an output layer. The
     * pre-synaptic neuron can only be null if it is connecting an input neuron.
     * And the post-synaptic neuron can only be null if it is connecting an
     * output neuron. Also, the pre- and post-synaptic neruons cannot be equal.
     * Postconditions: If no errors are detected, a new Axon object will be
     * initialsed.
     */
    public OutputAxon(Neuron preSynaptic, Neuron postSynaptic) {
        this(preSynaptic, null, 0, 1);
    }

    /**
     * Return the value returned by the output neuron.
     *
     * Preconditions: None.
     * Postconditions: The value of the output of the output neuron will be returned.
     */
    public double getPostSynaptic() {
        return (this.output);
    }

    public void sendActionPotential(double actionPotential) {
        // Send an action potential to the post-synaptic neuron.
        this.output = actionPotential;
    }
}
