import java.lang.Math;

public class Sigmoid implements Activation {
    /*
     * The constructor does nothing special.
     */
    public Sigmoid() {}

    /*
     * Use the sigmoid function to evaluate value.
     */
    public double activate(double value) {
        return (1 / (1 + Math.exp(-value)));
    }

    /*
     * Return the derivative of the sigmoid function.
     */
    public double prime(double value) {
        value = activate(value);
        return (value * (1 - value));
    }
}