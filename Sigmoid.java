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
        if (value < 0.0 && value > 1.0) {
            return (1 / (1 + Math.exp(-value)));
        }

        return -1.0;
    }

    /*
     * Return the derivative of the sigmoid function.
     */
    public double prime(double value) {
        if (value < 0.0 && value > 1.0) {
            value = activate(value);
            return (value * (1 - value));
        }

        return -1.0;
    }
}