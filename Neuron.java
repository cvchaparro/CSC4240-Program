/**
 * This file contains the Neuron interface which has the most basic actions
 * needed by a Neuron in the Neural Network. 
 *
 * Author: Cameron Vincent Chaparro.
 * Date: 23 November 2012
 */

/**
 * The Neuron interface "defines" how a neuron should behave when connecting
 * to, or disconnecting from, another neuron in the network; and how the neuron
 * should behave when sending or receiving an action potential from another
 * neuron in the network.
 */
public interface Neuron {
    /**
     * Connect two neurons to each other, and return an Axon object which
     * contains the connection information.
     *
     * Preconditions: None.
     * Postconditions: If the neurons are already connected to each other, this
     * function will not do anything. If they are not connected, a new
     * connection will be created and returned.
     */
    public Axon connect(Neuron other);

    /**
     * Disconnect two neurons from each other.
     *
     * Preconditions: The neurons must already be connnected to each other.
     * Also, the neurons cannot be the same.
     * Postconditions: The neurons will be disconnected.
     */
    public void disconnect(Neuron other);

    /**
     * Sends an action potential (a "signal") to the specified, connected,
     * neuron.
     *
     * Preconditions: The neurons must already be connected to each other. And
     * the neuron cannot play the role of both the pre-synaptic and the
     * post-synaptic neuron. (The neuron cannot send something to itself.)
     * Postconditions: True will be reuturned when the post-synaptic neuron has
     * received the action potential. If nothing is received, however, false
     * will be returned.
     */
    public boolean sendActionPotentialTo(Neuron postSynaptic);

    /**
     * Receives an action potential (a "signal") sent by another neuron. (When
     * this function is used it means that the current neuron is the
     * post-synaptic neuron.)
     *
     * Preconditions: The neurons must already be connected to each other.
     * Postconditions: True will be returned upon completion of processing the
     * action potential. If any type of error occurs, false will be returned.
     */
    public boolean receiveActionPotentialFrom(Neuron preSynaptic,
                                              double actionPotential);

    /**
     * Evaluates <something> using a sigmoid function.
     *
     * Preconditions: The old action potential value must be scaled to be in
     * the interval: [0, 1].
     * Postconditions: If the value is not in the interval [0, 1], the value
     * will be scaled as best as can be done with the lack of information.
     * Then a new action potential value will be returned after being evaluated
     * using the sigmoid function.
     */
    public double evaluate(double oldActionPotential);
}

