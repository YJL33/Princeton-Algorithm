/******************************************************************************
Examines 4 points at a time and checks whether they're all on the same line segment,
returning all such line segments.

Given a point p, determine whether p in a set of 4 or more collinear points.

1. Think of p as the origin.
2. For each other point q, determine the slope it makes with p.
3. Sort the points according to the slopes they makes with p.
4. Check if any 3 (or more) points in the sorted order have slopes equal to p.
    If so, these points, together with p, are collinear.

Apply for each of the n points in turn yields an efficient algorithm to the problem.
Points that have equal slopes with respect to p are collinear,
and sorting brings such points together.
The algorithm is fast because the bottleneck operation is sorting. 

The method segments() should include each max line segment with 4+ pts only once.
For example, if 5 points appear on a line segment in the order p→q→r→s→t,
then do not include the subsegments p→s or q→t.

Corner cases.
Throw a java.lang.NullPointerException either:
the argument to the constructor is null or if any point in the array is null.
Throw a java.lang.IllegalArgumentException:
if the argument to the constructor contains a repeated point.

Performance requirement.
The order of growth of the running time should be n^2 log n in the worst case,
and it should use space proportional to n plus the number of line segments returned.
FastCollinearPoints should work even the input has 5 or more collinear points.
******************************************************************************/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FastCollinearPoints {

    private LineSegment[] segments;
    private ArrayList<LineSegment> res;
    private boolean isFinished = false;
    private int numOfPoints;
    
    // finds all segment with 4+ points
    public FastCollinearPoints(Point[] points) {
        isDupe(points);
        isNull(points);

        Point[] ptsCopy = Arrays.copyOf(points, points.length);
        res = new ArrayList<>();
        numOfPoints = points.length;

        // 1-3. use each point as origin, sort by the slope
        for (int p = 0; p < points.length; p++) {
            if (isFinished) {
                break;
            }
            Arrays.sort(ptsCopy, points[p].slopeOrder());
            ArrayList<Point> temp = new ArrayList<>();
            double slope = 0;
            double prev = Double.NEGATIVE_INFINITY;

            // 4. Check if 3 or more points have slopes equal.
            for (int i = 1; i < ptsCopy.length; i++) {
                slope = points[p].slopeTo(ptsCopy[i]);
                if (slope != prev) {
                    temp = checker(temp, prev, points[p]);
                }
                temp.add(ptsCopy[i]);
                prev = slope;
            }
            temp = checker(temp, slope, points[p]);
        }
        segments = res.toArray(new LineSegment[res.size()]);
    }
    public int numberOfSegments() {         // the number of line segments
        return segments.length;
    }
    public LineSegment[] segments() {     // the line segments
        return Arrays.copyOf(segments, numberOfSegments());
    }
    private void isDupe(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Given same entries.");
                }
            }
        }
    }

    private void isNull(Point[] points) {
        if (points == null) {
            throw new NullPointerException("Given Null points.");
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new NullPointerException("Null entries in given points.");
            }
        }
    }

    // check if 3 or more points have slopes equal
    private ArrayList<Point> checker(ArrayList<Point> temp, double slp, Point ori) {
        if (temp.size() >= 3) {    // Got it!
            temp.add(ori);
            Collections.sort(temp);
            Point start = temp.get(0);

            if (start.compareTo(ori) == 0) {        // Add LineSegment only when ori is first
                Point end = temp.get(temp.size() - 1);
                res.add(new LineSegment(start, end));     // seems this would be good
                if (temp.size() >= numOfPoints-2) {
                    isFinished = true;
                }
            }
        }
        return new ArrayList<Point>();
    }
}