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
    // The neuron that sends an action potential.
    private Neuron preSynaptic;
    // Contains the output of the neuron.
    private double postSynaptic;
    // The actual action potential sent by the pre-synaptic neuron.
    private double actionPotential;
    // The scale factor that is used to scale the action potential to give it a
    // value between 0.0 and 1.0.
    private double weight;

    // Initialises everything to "zero".
    public OutputAxon() {
        this.preSynaptic = null;
        this.postSynaptic = 0;
        this.actionPotential = 0;
        this.weight = 0;
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
        this(preSynaptic, null, 0, 0);
    }

    public Neuron getPostSynapticNeuron() {}

    /**
     * Return the value returned by the output neuron.
     *
     * Preconditions: None.
     * Postconditions: The value of the output of the output neuron will be returned.
     */
    public double getPostSynaptic() {
        return (this.postSynaptic);
    }

    public void sendActionPotential(double actionPotential) {
        // Send an action potential to the post-synaptic neuron.
        this.postSynaptic = (actionPotential * weight);
    }
}
