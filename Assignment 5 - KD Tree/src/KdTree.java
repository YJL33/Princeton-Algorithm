/******************************************************************************
 Compilation:  javac KdTree.java
 Execution:
 Dependencies:
 
 Details: check http://coursera.cs.princeton.edu/algs4/assignments/kdtree.html

2d-tree implementation.
Write a mutable data type KdTree.java that uses a 2d-tree to implement the same API
(but replace PointSET with KdTree).
A 2d-tree is a generalization of a BST to two-dimensional keys.
The idea is to build a BST with points in the nodes,
using the points' x- and y-coordinates as keys in strictly alternating sequence.

Search and insert.
The algorithms for search and insert are similar to those for BSTs,
but at the root we use the x-coordinate
(if the point to be inserted has a smaller x-coordinate than the point at the root,
go left; otherwise go right);
then at the next level, we use the y-coordinate
(if the point to be inserted has a smaller y-coordinate than the point in the node,
go left; otherwise go right);
then at the next level the x-coordinate, and so forth.

Draw.
A 2d-tree divides the unit square in a simple way:
all the points to the left of the root go in the left subtree;
all those to the right go in the right subtree; and so forth, recursively.

Your draw() method should draw all of the points to standard draw in black,
and the subdivisions in red (for vertical splits) and blue (for horizontal splits).
This method need not be efficient—it is primarily for debugging. 

The prime advantage of a 2d-tree over a BST is that
it supports efficient implementation of range search and nearest neighbor search.

Each node corresponds to an axis-aligned rectangle in the unit square,
which encloses all of the points in its subtree.
The root corresponds to the unit square;
the left and right children of the root corresponds to the two rectangles split,
by the x-coordinate of the point at the root; and so forth.

Range search.
To find all points contained in a given query rectangle,
start at the root and recursively search for points in both subtrees:
If the query rectangle does not intersect the rectangle corresponding to a node,
there is no need to explore that node (or its subtrees).
A subtree is searched only if it may contain a point in the query rectangle.

Nearest neighbor search.
To find a closest point to a given query point,
start at the root and recursively search in both subtrees:
If the closest point discovered so far is closer than
the distance between the query point and the rectangle corresponding to a node,
there is no need to explore that node (or its subtrees).
A node is searched only if it may contain a point closer than the best one so far.
The effectiveness of the pruning rule depends on quickly finding a nearby point.
Organize your recursion so that when there are two possible subtrees to go down,
you always choose the subtree that is on the same side of the splitting line
as the query point as the first subtree to explore—the closest point found,
while exploring the first subtree may enable pruning of the second subtree.

******************************************************************************/
import java.util.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {

    private static double minDis;
    private static Node result;

    private int number = 0;
    private Node root;             // root of 2D Tree

    // construct an empty set of points 
    public KdTree() {
    }
    // custom class Node
    private static class Node {
        private Point2D p;   // the point
        private RectHV rect; // the axis-aligned rectangle corresponding to the node
        private Node lb;     // the left/bottom subtree
        private Node rt;     // the right/top subtree
        private boolean splt;
        public Node(Point2D p) {
            this.p = p;
            this.lb = null;
            this.rt = null;
        }
    }
    // is the set empty? 
    public boolean isEmpty() {
        return number == 0;
    }
    // number of points in the set 
    public int size() {
        return number;
    }
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D pt) { 
        double minX = 0.0;
        double minY = 0.0;
        double maxX = 1.0;
        double maxY = 1.0;
        root = insert(root, pt, false, minX, minY, maxX, maxY);
    }
    
    private Node insert(Node nd, Point2D pt, boolean xy, double x1, double y1, double x2, double y2) {
        double cmp;
        boolean orien = !xy;
        if (nd == null) {
            number++;
            Node newNode = new Node(pt);
            newNode.splt = orien;
            newNode.rect = new RectHV(x1, y1, x2, y2);
            return newNode;
        }
        if (nd.p.equals(pt)) return nd;
        if (nd.splt) { // split over x
            cmp = nd.p.x() - pt.x();
        } else {       // split over y
            cmp = nd.p.y() - pt.y();
        }
        if (nd.splt && cmp > 0) {
            x2 = nd.p.x();
            nd.lb = insert(nd.lb, pt, orien, x1, y1, x2, y2);
        } else if (!nd.splt && cmp > 0) {
            y2 = nd.p.y();
            nd.lb = insert(nd.lb, pt, orien, x1, y1, x2, y2);
        } else if (nd.splt && cmp <= 0) {
            x1 = nd.p.x();
            nd.rt = insert(nd.rt, pt, orien, x1, y1, x2, y2);
        } else if (!nd.splt && cmp <= 0) {
            y1 = nd.p.y();
            nd.rt = insert(nd.rt, pt, orien, x1, y1, x2, y2);
        }
        return nd;
    }

    // does the set contain point p? 
    public boolean contains(Point2D pt) {
        // StdOut.println("Start search: ");
        Node nd = root;
        double cmp;
        while (nd != null) {
            // StdOut.println(nd.p.toString() + " " + nd.splt);
            if (nd.p.equals(pt)) return true;
            if (nd.splt) {
                cmp = nd.p.x() - pt.x();
            } else {
                cmp = nd.p.y() - pt.y();
            }
            if (cmp > 0) {
                nd = nd.lb;
            } else {
                nd = nd.rt;
            }
        }
        return false;
    }

    // draw all points to standard draw 
    public void draw() {
        RectHV iniRec = new RectHV(0.0, 0.0, 1.0, 1.0);
        iniRec.draw();
        root = draw(root);
    }
    private Node draw(Node nd) {
        if (nd == null) return nd;
        drawLine(nd);
        // StdOut.println(nd.p.toString());
        nd.lb = draw(nd.lb);
        nd.rt = draw(nd.rt);
        return nd;
    }
    private void drawLine(Node node)
    {
        if (node.splt) {
            StdDraw.setPenColor(StdDraw.RED);
            double yMax = node.rect.ymax();
            double yMin = node.rect.ymin();
            StdDraw.line(node.p.x(), yMin, node.p.x(), yMax);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            double xMax = node.rect.xmax();
            double xMin = node.rect.xmin();
            StdDraw.line(xMin, node.p.y(), xMax, node.p.y());
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        node.p.draw();
    }
    // all points that are inside the rectangle 
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> stk = new Stack<Point2D>();
        root = range(root, rect, stk);
        return stk;
    }
    
    private Node range(Node node, RectHV rectangle, Stack<Point2D> stack) {
        if (node == null) return node;
        if (!rectangle.intersects(node.rect)) return node;
        if (rectangle.contains(node.p)) stack.push(node.p);
        node.lb = range(node.lb, rectangle, stack);
        node.rt = range(node.rt, rectangle, stack);
        return node;
    }
    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) { 
        // StdOut.println("Call function:");
        result = null;
        minDis = Double.POSITIVE_INFINITY;
        root = nearest(p, root);
        if (result == null) return null;
        minDis = Double.POSITIVE_INFINITY;
        return result.p;
    }
    
    private Node nearest(Point2D pt, Node node) {
        // StdOut.println("Find the nearest point:");
        if (node == null) return node;
        // StdOut.println("this node: " + node.p.toString());
        double newDis = node.p.distanceSquaredTo(pt);
        // StdOut.println("Distance for this node: " + newDis + " Nearest distance now: " + minDis);
        if (newDis <= minDis) {
            // StdOut.println("Update: ");
            result = node;
            minDis = newDis;
            // StdOut.println("Nearest point: " + result.p.toString() + " Nearest distance: " + minDis);
        }
        if (node.lb == null && node.rt == null) {
            // StdOut.println("Leaf Node: " + node.p.toString());
            return node;
        } else if (node.lb != null && node.rt == null) {
            if (minDis >= node.lb.rect.distanceSquaredTo(pt))
                node.lb = nearest(pt, node.lb);
        } else if (node.lb == null && node.rt != null) {
            if (minDis >= node.rt.rect.distanceSquaredTo(pt))
                node.rt = nearest(pt, node.rt);
        } else if (node.lb != null && node.rt != null) {
            if (node.lb.rect.contains(pt)) {
                node.lb = nearest(pt, node.lb);
                if (minDis >= node.rt.rect.distanceSquaredTo(pt))
                    node.rt = nearest(pt, node.rt);
            } else if (node.rt.rect.contains(pt)) {
                node.rt = nearest(pt, node.rt);
                if (minDis >= node.lb.rect.distanceSquaredTo(pt))
                {
                    node.lb = nearest(pt, node.lb);
                }
            } else {
                if (minDis >= node.lb.rect.distanceSquaredTo(pt))
                    node.lb = nearest(pt, node.lb);
                if (minDis >= node.rt.rect.distanceSquaredTo(pt))
                    node.rt = nearest(pt, node.rt);
            }
        }
        return node;
    }
    // unit testing of the methods (optional) 
    public static void main(String[] args) {
    }
}