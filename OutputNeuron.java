/**
 * This file contains the OutputNeuron class which is, in a sense, an equivalent
 * of a root node in a tree. Input neurons are the ones which, as the name
 * suggests, get input but the input is the original data itself.
 *
 * Name: Cameron Vincent Chaparro.
 * Date: 23 November 2012
 */

import java.util.ArrayList;
import java.util.List;

public class OutputNeuron implements Neuron {
    // The activation function that will be used to determine when to send an
    // action potential to the neurons connected to this neuron.
    private Activation activation;
    // A list of Neurons that we are connected to.
    private List<Neuron> connected;
    // A list of Axons we have between other neurons and ourselves.
    private List<Axon> axons;
    // The action potential to send to other neurons.
    private double actionPotential;
    // The bias input value.
    private double bias;
    // The initial input value.
    private double input;
    // The neuron's weight.
    private double weight;

    // Initialise everything to "zero".
    public OutputNeuron() {
        // Actually for the lists we will create them but, obviously, not put
        // anything in them yet.
        this.connected = new ArrayList<Neuron>();
        this.axons = new ArrayList<Axon>();

        // The input and output will both be zero.
        this.bias = 0.0;
        this.input = 0.0;
        this.weight = 0.0;
        this.actionPotential = 0.0;
    }

    /**
     * Initialise all instance variables to the values of the specified
     * parameters.
     *
     * Preconditions: None.
     * Postconditions: A new OutputNeuron object will be initialsed.
     */
    public OutputNeuron(List<Neuron> connected, List<Axon> axons, Activation activation, double input, double bias, double weight) {
        //// --- BEGIN ERROR CHECKING --- ////
        // Is the connected list equal to null?
        if (connected == null) {
            this.connected = new ArrayList<Neuron>();
        }
        else {
            this.connected = connected;
        }

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

        // Initialise bias, input and actionPotential.
        this.bias = bias;
        this.input = input;
        this.weight = weight;
        this.actionPotential = 0.0;
    }

    public boolean connect(Neuron other) {
        return (axons.add(new Axon(this, other, actionPotential)));
    }

    public boolean disconnect(Neuron other) {
        return (axons.remove(other));
    }

    public boolean sendActionPotentialTo() {
        double potential = this.evaluate(actionPotential);
        for(Axon axon : axons) {
            axon.sendActionPotential(potential);
        }
        return true;
    }

    public boolean receiveActionPotentialFrom(double actionPotential) {
        this.actionPotential += actionPotential;
        return true;
    }

    public double evaluate(double oldActionPotential) {
        return actionPotential;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return this.weight;
    }
}
