/******************************************************************************
Compilation:  javac Point.java
Execution:  java Point
Dependencies: none

 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.

To get started, use the data type Point.java,
which implements the constructor and the draw(), drawTo(), and toString() methods.
Your job is to add the following components.

The compareTo() method should compare points by y-coordinates,
breaking ties by x-coordinates.
Formally, the invoking point (x0, y0) is less than the argument point (x1, y1),
if and only if either y0 < y1 or if y0 = y1 and x0 < x1.

The slopeTo() method should return the slope between the invoking point (x0, y0),
and the argument point (x1, y1), which is given by the formula (y1 − y0) / (x1 − x0).
Treat the slope of a horizontal line segment as positive zero;
treat the slope of a vertical line segment as positive infinity;
treat the slope of a degenerate line segment (between a point and itself)
as negative infinity.

The slopeOrder() method should return a comparator compares its two argument points,
by the slopes they make with the invoking point (x0, y0).
Formally, the point (x1, y1) is less than the point (x2, y2),
if and only if the slope (y1−y0)/(x1−x0) is less than the slope (y2−y0)/(x2−x0).
Treat horizontal, vertical, and degenerate line segments as in the slopeTo() method.

Corner cases.
To avoid potential complications with integer overflow or floating-point precision,
you may assume that the constructor arguments x and y are each between 0 and 32,767.
******************************************************************************/
import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /*
     * Initializes a new point.
     *
     * x: the x-coordinate of the point
     * y: the y-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /*
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /*
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * that: the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /*
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * that: the other point
     * return: the slope between this point and the specified point
     */
    public double slopeTo(Point that) {        /* YOUR CODE HERE */
        if (this.x == that.x && this.y == that.y) {
            return Double.NEGATIVE_INFINITY;
        }
        else if (this.x == that.x) return Double.POSITIVE_INFINITY;
        else if (this.y == that.y) return +0.0;
        else {
            return (double) (that.y - this.y)/(that.x - this.x);
        }
    }

    /*
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * that: the other point
     * return: the value if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {            /* YOUR CODE HERE */
        if (this.y > that.y) return 1;
        else if (this.y < that.y) return -1;
        else
        if (this.x > that.x) return 1;
        else if (this.x < that.x) return -1;
        else
            return 0;
    }

    /*
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {     /* YOUR CODE HERE */
        return new Comparator<Point>() {
        @Override
        public int compare(Point point1, Point point2) {
            double slopeDiff = slopeTo(point1) - slopeTo(point2);

            // if (slopeDiff > 0) return 1;
            // else if (slopeDiff < 0) return -1;
            // else return 0;

            // val1 > val2 ? 1 : val1 < val2 ? -1 : 0
            return (slopeDiff > 0) ? 1 : (slopeDiff < 0) ? -1 : 0;
        }
        };
    }

    /*
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * return: a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /*
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}