/**
 * This file contains the PointN class which represents an N-dimensional point
 * (or equivalently, a point in R^N space).
 *
 * Author: Cameron Vincent Chaparro.
 * Date: 1 December 2012
 */

import java.util.ArrayList;
import java.util.List;

/**
 * The PointN class is just an immutable storage class that contains an
 * N-dimensional point.
 */
public class PointN {
    // X-values.
    private List<Double> x;
    // Y-values.
    private List<Double> y;

    public PointN(double[] X, double[] Y) {
        this.x = new ArrayList<Double>();
        this.y = new ArrayList<Double>();

        for (int i = 0; i < X.length; i++) {
            this.x.add(X[i]);
        }

        for (int i = 0; i < Y.length; i++) {
            this.y.add(Y[i]);
        }
    }

    public double getX(int index) {
        if (index >= 0 && index < x.size()) {
            return x.get(index);
        }

        return 0;
    }

    public double getY(int index) {
        if (index >= 0 && index < y.size()) {
            return y.get(index);
        }

        return 0;
    }

    public int numX() {
        return (x.size());
    }

    public int numY() {
        return (y.size());
    }
}