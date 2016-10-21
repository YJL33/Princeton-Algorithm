/******************************************************************************
Compilation:  javac PointSET.java
Execution:
Dependencies:
 
Details: check http://coursera.cs.princeton.edu/algs4/assignments/kdtree.html
 
Brute-force implementation.
Write a mutable data type that represents a set of points in the unit square.
Implement the following API by using a red-black BST
(using either SET from algs4.jar or java.util.TreeSet).

Corner cases.
Throw a java.lang.NullPointerException if any argument is null.

Performance requirements.
Your implementation should support (in the worst case):
insert() and contains() -
in time proportional to the logarithm of the number of points in the set;
nearest() and range() -
in time proportional to the number of points in the set.
******************************************************************************/
import java.util.TreeSet;
import java.util.Stack;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
    private TreeSet<Point2D> tree = new TreeSet<Point2D>();
    // construct an empty set of points 
    public PointSET() {
    }
    // is the set empty?
    public boolean isEmpty() {
        return tree.isEmpty();
    }
    // number of points in the set
    public int size() {
        return tree.size();
    }
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (!tree.contains(p)) tree.add(p);
    }
    // does the set contain point p? 
    public boolean contains(Point2D p) {
        return tree.contains(p);
    }
    // draw all points to standard draw 
    public void draw() {
        for (Point2D pt : tree) pt.draw();
    }
    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> inRange = new Stack<Point2D>();
        for (Point2D pt : tree)
            if (rect.contains(pt))
                inRange.push(pt);
        return inRange;
    }
    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (tree.isEmpty()) return null;
        Point2D nearest = new Point2D(0.0, 0.0);
        double minDis = Double.POSITIVE_INFINITY;
        for (Point2D pt : tree) {
            if (!pt.equals(p)) {
                if (pt.distanceSquaredTo(p) < minDis) {
                    nearest = pt;
                    minDis = pt.distanceSquaredTo(p);
                }
            }
            else return pt;
        }
        return nearest;
    }
    // unit testing of the methods (optional) 
    public static void main(String[] args) {
        /*
        StdDraw.setCanvasSize(500, 500);
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        StdDraw.setPenRadius(0.01);
        
        PointSET pset = new PointSET();
        int N = 30;
        Point2D[] points = new Point2D[N];
        for (int i = 0; i < N; i++) {
            double x = StdRandom.uniform();
            double y = StdRandom.uniform();
            points[i] = new Point2D(x, y);
            pset.insert(points[i]);
        }
        pset.draw();
        */
        /*
        RectHV rect = new RectHV(0.3, 0.3, 0.7, 0.7);
        rect.draw();
        StdDraw.setPenColor(StdDraw.RED);
        for (Point2D pt : pset.range(rect))
            pt.draw();
        */
        /*
        StdDraw.setPenColor(StdDraw.RED);
        double x = StdRandom.uniform();
        double y = StdRandom.uniform();
        Point2D p = new Point2D(x, y);
        p.draw();
        
        StdDraw.setPenColor(StdDraw.BLUE);
        Point2D nst = pset.nearest(p);
        nst.draw();
        */
    }
}