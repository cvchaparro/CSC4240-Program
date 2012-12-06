/**
 * This file contains the Activation interface which specifies the function that
 * any activation function must implement.
 *
 * Author: Cameron Vincent Chaparro.
 * Date: 1 December 2012
 */

/**
 * The Activation interface specifies what values will be needed from an activation
 * function for a Neuron in a NeuralNetwork.
 */
public interface Activation {
    /**
     * Evaluates the specified (scaled) value using the sigmoid function.
     *
     * Preconditions: The scaled value must be between 0.0 and 1.0.
     * Postconditions: The result of the scaled value evaluated with the sigmoid
     * will be returned.
     */
    public double activate(double value);

    /**
     * Evaluates the specified (scaled) value using the derivative of the
     * sigmoid function.
     *
     * Preconditions: The scaled value must be between 0.0 and 1.0.
     * Postconditions: The result of the scaled value evaluated with the sigmoid
     * functions derivative will be returned.
     */
    public double prime(double value);
}