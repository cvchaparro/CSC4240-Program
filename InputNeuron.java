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

public class InputNeuron implements Neuron {
    // A list of Neurons that we are connected to.
    private List<Neuron> connected;
    // A list of Axons we have between other neurons and ourselves.
    private List<Axon> axons;
    // The initial input value.
    private double input;
    // The action potential to send to other neurons.
    private double output;

    // Initialise everything to "zero".
    public InputNeuron() {
        // Actually for the lists we will create them but, obviously, not put
        // anything in them yet.
        this.connected = new ArrayList<Neuron>();
        this.axons = new ArrayList<Axon>();

        // The input and output will both be zero.
        this.input = 0.0;
        this.output = 0.0;
    }

    /**
     * Initialise all instance variables to the values of the specified
     * parameters.
     *
     * Preconditions: None.
     * Postconditions: A new InputNeuron object will be initialsed.
     */
    public InputNeuron(List<Neuron> connected, List<Axon> axons, double input) {
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
        //// --- END ERROR CHECKING --- ////

        // Initialise input and output.
        this.input = input;
        this.output = 0.0;
    }

    /*
     *
     */
     public Axon connect(Neuron other) { return new Axon(); }
     public void disconnect(Neuron other) {}
     public boolean sendActionPotentialTo(Neuron postSynaptic) { return postSynaptic.receiveActionPotentialFrom(this, output); }
     public boolean receiveActionPotentialFrom(Neuron preSynaptic,
                                           double actionPotential) { return preSynaptic.sendActionPotentialTo(this); }
    public double evaluate(double oldActionPotential) { return output; }
}

