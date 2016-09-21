/*
Sample client.

This client program takes the name of an input file as a command-line argument;
read the input file (in the format specified below);
prints to std-opt the line segments that your program discovers, one per line;
and draws to standard draw the line segments. 
*/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Timer;

public class Client {
  public static void main(String[] args) {


    // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
      int x = in.readInt();
      int y = in.readInt();
      points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
      p.draw();
    }
    StdDraw.show();

    
    long starttime = System.nanoTime();
    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
    long estimatedTime = System.nanoTime() - starttime;
    StdOut.println(estimatedTime);
    

    long starttime2 = System.nanoTime();
    FastCollinearPoints collinearf = new FastCollinearPoints(points);
    for (LineSegment segment : collinearf.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
    long estimatedTime2 = System.nanoTime() - starttime2;
    StdOut.println(estimatedTime2);

  }
}
