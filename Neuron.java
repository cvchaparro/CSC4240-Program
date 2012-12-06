/**
 * This file contains the Neuron class which has the most basic actions
 * needed by a Neuron in the Neural Network. 
 *
 * Author: Cameron Vincent Chaparro.
 * Date: 23 November 2012
 */

import java.util.ArrayList;
import java.util.List;

/**
 * The Neuron class "defines" how a neuron should behave when connecting
 * to, or disconnecting from, another neuron in the network; and how the neuron
 * should behave when sending or receiving an action potential from another
 * neuron in the network.
 */
public class Neuron {
    // The activation function that will be used to determine when to send an
    // action potential to the neurons connected to this neuron.
    private Activation activation;
    // A list of Axons we have between other neurons and ourselves.
    private List<Axon> axons;
    // The bias for the neuron.
    private double bias;
    // The initial input value.
    private double input;

    public Neuron() {
        // Actually for the list we will create it but, obviously, not put
        // anything in it yet.
        this.axons = new ArrayList<Axon>();

        // The input will be zero.
        this.bias = 0.0;
        this.input = 0.0;
    }

    /**
     * Initialise all instance variables to the values of the specified
     * parameters.
     *
     * Preconditions: None.
     * Postconditions: A new Neuron object will be initialsed.
     */
    public Neuron(List<Axon> axons, Activation activation, double input, double bias) {
        //// --- BEGIN ERROR CHECKING --- ////
        // Is the axons list equal to null?
        if (axons == null) {
            this.axons = new ArrayList<Axon>();
        }
        else {
            this.axons = axons;
        }

        // Is the activation null?
        if (activation == null) {
            this.activation = new Sigmoid();
        }
        else {
            this.activation = activation;
        }
        //// --- END ERROR CHECKING --- ////

        // Initialize input and bias.
        this.bias = bias;
        this.input = input;
    }

    /**
     * Adds an axon (a connection) to the list of axons.
     *
     * Preconditions: The axon cannot be null.
     * Postconditions: The axon will be added to the list of axons.
     */
    public void addAxon(Axon axon) {
        axons.add(axon);
    }

    /**
     * Returns the axon at the specified index.
     *
     * Preconditions: The index must be greater than 0 and less than the size of
     * the axons list (which will be the number of connections between this
     * neuron and any other neuron).
     * Postconditions: The axon at the specified index of the list will be
     * returned.
     */
    public Axon getAxon(int index) {
        if (index >= 0 && index < axons.size()) {
            return (axons.get(index));
        }
        return null;
    }

    /**
     * Returns the number of axons (connections) between the current neuron and
     * any other neurons it is connected to.
     *
     * Preconditions: None.
     * Postconditions: The number of axons in the axons list will be returned.
     */
    public int numAxons() {
        return (axons.size());
    }

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
    public boolean sendActionPotential() {
        double potential = this.evaluate();
        for(Axon axon : axons) {
            axon.sendActionPotential(potential);
        }
        return true;

    }

    /**
     * Receives an action potential (a "signal") sent by another neuron. (When
     * this function is used it means that the current neuron is the
     * post-synaptic neuron.)
     *
     * Preconditions: The neurons must already be connected to each other.
     * Postconditions: True will be returned upon completion of processing the
     * action potential. If any type of error occurs, false will be returned.
     */
    public boolean receiveActionPotential(double actionPotential) {
        this.input += actionPotential;
        return true;
    }

    /**
     * Evaluates the Neuron's action potential using an activation function.
     *
     * Preconditions: The old action potential value must be scaled to be in
     * the interval: [0, 1].
     * Postconditions: If the value is not in the interval [0, 1], the value
     * will be scaled as best as can be done with the lack of information.
     * Then a new action potential value will be returned after being evaluated
     * using the sigmoid function.
     */
    public double evaluate() {
        return (activation.activate(input));
    }

    /**
     * Sets the bias for a neuron. An important thing to remember with the bias
     * is that InputNeurons do not make use of a bias value, so for them, this
     * function does not do anything.
     *
     * Preconditions: None.
     * Postconditions: The bias value will be updated for HiddenNeurons and
     * OutputNeurons; nothing will happen for InputNeurons.
     */
    public void setBias(double bias) {
        this.bias = bias;
    }

    /**
     * Returns the bias value for a neuron. Again, it is important to note that
     * this function is not meaningful in the InputNeuron class.
     *
     * Preconditions: None.
     * Postconditions: The bias value will be returned for HiddenNeurons and
     * OutputNeurons; 0 will be returned for InputNeurons.
     */
    public double getBias() {
        return (this.bias);
    }
}
