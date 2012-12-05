/**
 * This file contains the Axon class which represents a connection between
 * two distinct neurons.
 *
 * Author: Cameron Vincent Chaparro.
 * Date: 23 November 2012
 */

/**
 * The Axon class stores information regarding two connected neurons, and
 * in particular, it stores information communicated between them.
 */
public class Axon {
    // The neuron that sends an action potential.
    private Neuron preSynaptic;
    // The neuron that receives an action potential.
    private Neuron postSynaptic;
    // The actual action potential sent by the pre-synaptic neuron.
    private double actionPotential;
    // The scale factor that is used to scale the action potential to give it a
    // value between 0.0 and 1.0.
    private double scale;

    // Initialises everything to "zero".
    public Axon() {
        this.preSynaptic = null;
        this.postSynaptic = null;
        this.actionPotential = 0.0;
    }

    /**
     * Initialise all instance variables to the values of the specified
     * parameters.
     *
     * Preconditions: Neither of the pre- or post-synaptic neurons can be null.
     * Also, they cannot be equal.
     * Postconditions: If no errors are detected, a new Axon object will be
     * initialsed.
     */
    public Axon(Neuron preSynaptic, Neuron postSynaptic, double actionPotential) {
        //// --- BEGIN ERROR CHECKING --- ////
        // Do either preSynaptic or postSynaptic equal null?
        if (preSynaptic == null || postSynaptic == null) {
            System.out.println("Pre-Synaptic or Post-Synaptic Neurons cannot be null.");

            return;
        }

        // Are the preSynaptic and postSynaptic neurons physically equal (i.e.
        // do the have the same memory address)?
        if (preSynaptic == postSynaptic) {
            System.out.println("Pre-Synaptic and Post-Synaptic Neurons cannot be equal.");

            return;
        }

        // Are the preSynaptic and postSynaptic neurons contentually equal
        // (i.e. do the have the same contents)?
        if (preSynaptic.equals(postSynaptic)) {
            String error = "Pre-Synaptic and Post-Synaptic Neurons cannot be";
            error += "equal.";

            System.out.println(error);

            return;
        }
        //// --- END ERROR CHECKING --- ////

        // Initialise everything.
        this.preSynaptic = preSynaptic;
        this.postSynaptic = postSynaptic;
        this.actionPotential = actionPotential;
    }

    /**
     * Return the pre-synaptic neuron.
     *
     * Preconditions: None.
     * Postconditions: The pre-synaptic neuron will be returned.
     */
    public Neuron getPreSynapticNeuron() {
        return this.preSynaptic;
    }

    /**
     * Return the post-synaptic neuron.
     *
     * Preconditions: None.
     * Postconditions: The post-synaptic neuron will be returned.
     */
    public Neuron getPostSynapticNeuron() {
        return this.postSynaptic;
    }

    /**
     * Return the action potential.
     *
     * Preconditions: None.
     * Postconditions: The action potential will be returned.
     */
    public double getActionPotential() {
        return this.actionPotential;
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
     * Sends a weighted signal to the neuron it is connected to.
     * 
     * Preconditions: None.
     * Postconditions: The neuron this axon is connected to will receive a
     * signal based on the preSynaptic neurons output and weight of the axon.
     */
    public void sendActionPotential(double potential) {
        this.getPostSynapticNeuron().receiveActionPotentialFrom(potential*scale);
    }
}

