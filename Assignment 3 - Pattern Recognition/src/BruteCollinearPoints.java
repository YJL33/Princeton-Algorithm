/******************************************************************************
Examines 4 points at a time and checks whether they're all on the same line segment,
returning all such line segments.

To check whether the 4 points p, q, r, and s are collinear,
check whether the three slopes between p and q, between p and r,
and between p and s are all equal.

The method segments() should include each line segment having 4 points just once.
If 4 points appear on a line segment in the order p→q→r→s,
then you should include either the line segment p→s or s→p (but not both),
and you should not include subsegments such as p→r or q→r.

For simplicity,
we won't supply any input that has 5 or more collinear points.

Corner cases.
Throw a java.lang.NullPointerException either
the argument to the constructor is null or if any point in the array is null.
Throw a java.lang.IllegalArgumentException if:
the argument to the constructor contains a repeated point.

Performance requirement.
The order of growth of the running time of program should be N^4 in the worst case,
and it should use space proportional to n plus the number of line segments returned.
******************************************************************************/
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {     // finds all segments with 4 pts
        isDupe(points);
        isNull(points);

        ArrayList<LineSegment> res = new ArrayList<>();
        Point[] ptsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(ptsCopy);

        // pick combination of 4 points
        for (int p = 0; p < points.length - 3; p++) {
            for (int q = p+1; q < points.length - 2; q++) {
                for (int r = q+1; r < points.length - 1; r++) {
                    for (int s = r+1; s < points.length; s++) {
                        if (ptsCopy[p].slopeTo(ptsCopy[q]) == ptsCopy[p].slopeTo(ptsCopy[r])
                            && ptsCopy[p].slopeTo(ptsCopy[q]) == ptsCopy[p].slopeTo(ptsCopy[s]))
                        {
                            res.add(new LineSegment(ptsCopy[p], ptsCopy[s]));
                        }
                    }
                }
            }
        }

        segments = res.toArray(new LineSegment[res.size()]);

    }
    public int numberOfSegments() {             // the number of line segments
        return segments.length;
    }
    public LineSegment[] segments() {         // the line segments
        return Arrays.copyOf(segments, numberOfSegments());
    }
    private void isDupe(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Given duplicated entries.");
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
}