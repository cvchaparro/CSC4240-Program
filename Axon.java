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
public class Axon {
    // The neuron that sends an action potential.
    protected Neuron preSynaptic;
    // The neuron that receives an action potential.
    protected Neuron postSynaptic;
    // The actual action potential sent by the pre-synaptic neuron.
    protected double actionPotential;
    // The scale factor that is used to scale the action potential to give it a
    // value between 0.0 and 1.0.
    protected double weight;

    // Initialises everything to "zero".
    public Axon() {
        this.preSynaptic = null;
        this.postSynaptic = null;
        this.actionPotential = 0;
        this.weight = 0;
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
    public Axon(Neuron preSynaptic, Neuron postSynaptic, double actionPotential, double weight) {
        //// --- BEGIN ERROR CHECKING --- ////
        // Is the pre-synaptic neuron null?
        if (preSynaptic == null) {
            try {
                // Try and cast the post-synaptic neuron as an InputNeuron. If
                // a ClassCastException is thrown, then it is not an input
                // neuron and we should exit.
                if (postSynaptic != null) {
                    InputNeuron n = ((InputNeuron) preSynaptic);
                }
                else {
                    throw new ClassCastException();
                }
            }
            catch (ClassCastException cce) {
                System.out.println("Pre-Synaptic neurons cannot be null for HiddenNeurons or OutputNeurons.");
                return;
            }
        }
        // Is the post-synaptic neuron null?
        if (postSynaptic == null) {
            try {
                // Try and cast the post-synaptic neuron as an OutputNeuron. If
                // a ClassCastException is thrown, then it is not an output
                // neuron and we should exit.
                OutputNeuron n = ((OutputNeuron) postSynaptic);
            }
            catch (ClassCastException cce) {
                System.out.println("Post-Synaptic neurons cannot be null for InputNeurons or HiddenNeurons.");
                return;
            }
        }

        // Are the preSynaptic and postSynaptic neurons physically equal (i.e.
        // do the have the same memory address)?
        if (preSynaptic == postSynaptic) {
            System.out.println("Pre-Synaptic and Post-Synaptic Neurons cannot be equal.");
            return;
        }
        //// --- END ERROR CHECKING --- ////

        // Initialize everything.
        this.preSynaptic = preSynaptic;
        this.postSynaptic = postSynaptic;
        this.actionPotential = actionPotential;
        this.weight = weight;
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
    public Axon(Neuron preSynaptic, Neuron postSynaptic) {
        this(preSynaptic, postSynaptic, 0, 0);
    }

    /**
     * Return the pre-synaptic neuron.
     *
     * Preconditions: None.
     * Postconditions: The pre-synaptic neuron will be returned.
     */
    public Neuron getPreSynapticNeuron() {
        return (this.preSynaptic);
    }

    /**
     * Return the post-synaptic neuron.
     *
     * Preconditions: None.
     * Postconditions: The post-synaptic neuron will be returned.
     */
    public Neuron getPostSynapticNeuron() {
        return (this.postSynaptic);
    }

    /**
     * Return the action potential.
     *
     * Preconditions: None.
     * Postconditions: The action potential will be returned.
     */
    public double getActionPotential() {
        return (this.actionPotential);
    }

    /**
     * Return the weight associated with this axon.
     *
     * Preconditions: None.
     * Postconditions: The weight associated with this axon will be returned.
     */
    public double getWeight() {
        return (this.weight);
    }

    /**
     * Set the value of the action potential. (This is used during the back
     * propogation process.)
     *
     * Preconditions: None.
     * Postconditions: The action potential of the current connection between
     * the pre- and post-synaptic neurons will be updated.
     */
    public void updateActionPotential(double newActionPotential) {
        // Set the new action potential.
        this.actionPotential = newActionPotential;
    }

    /**
     * Set the value of weight. (This is used during the learning process.)
     *
     * Preconditions: None.
     * Postconditions: The weight will be updated.
     */
    public void setWeight(double newWeight) {
        // Set the new weight.
        this.weight = newWeight;
    }

    /**
     * Sends a weighted signal to the neuron it is connected to.
     * 
     * Preconditions: PostSynaptic cannot be null.
     * Postconditions: The neuron this axon is connected to will receive a
     * signal based on the preSynaptic neurons output and weight of the axon.
     */
    public void sendActionPotential(double actionPotential) {
        // Send an action potential to the post-synaptic neuron.
        if (postSynaptic != null) {
            this.postSynaptic.receiveActionPotential(actionPotential * weight);
        }
    }
}

