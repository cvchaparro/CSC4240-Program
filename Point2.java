/**
 * This file contains the Point2 class which represents a 2-dimensional point
 * (or equivalently, a point in RxR space).
 *
 * Author: Cameron Vincent Chaparro.
 * Date: 1 December 2012
 */

/**
 * The Point2 class is just an immutable storage class that contains a
 * 2-dimensional point.
 */
public class Point2 {
    // X-value.
    private double x;
    // Y-value.
    private double y;

    public Point2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}