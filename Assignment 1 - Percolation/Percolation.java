/******************************************************************************
 Compilation:  javac Percolation.java
 Execution:    java Percolation input.txt
 Dependencies: StdIn.java StdOut.java
 
 Details: check http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 
 Corner cases.
 By convention, the row and column indices i and j are integers between 1 and N, where (1, 1) is the upper-left site:
 Throw a java.lang.IndexOutOfBoundsException if any argument to open(), isOpen(), or isFull() is outside its prescribed range.
 The constructor should throw a java.lang.IllegalArgumentException if N ≦ 0.
 
 Performance requirements.
 The constructor should take time proportional to N2; all methods should
 take constant time + a constant number of calls to the union-find methods union(), find(), connected(), and count().
******************************************************************************/
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {                 // create N-by-N grid, with all sites blocked
  private boolean[][] grid;
  private WeightedQuickUnionUF gridstatus = null;
  private int depth;

  public Percolation(int k) {
    if (k <= 0) {
         throw new java.lang.IllegalArgumentException();
    }
    grid = new boolean[k+2][k+2];              // true = open, false = blocked
    gridstatus = new WeightedQuickUnionUF(k * k + 2);    // the connection status, position 0 = TOP, last: percolate controller
    depth = k;
  } 

  public void open(int i, int j) {        // open site (row i, column j) if it is not open already
    checkRange(i, j);                     // check whether it's effective
    if (isOpen(i, j)) return;             // check whether it's already opened
    grid[i][j] = true;                    // OPEN IT.

    int openNbCnt = 0;
    int[] nb = {0, 0, 0, 0};              // Neighbors

    /* 1. Check whether is there any open neighborhood around, if only one => just union them
       2. If more than one: check their connectivity. if connected => just union anyone
       3. If not, union all of them.*/
    if (i == 1) {
      gridstatus.union(0, reduceDim(i, j));      // First row, it's connected with TOP (FULL)
    }
    if (i == depth) {
      gridstatus.union(reduceDim(i, j), (depth * depth + 1));
    }

    if (grid[i-1][j] && (i > 1)) {        // Check its neighborhood are open or not
      nb[openNbCnt] = reduceDim(i-1, j);
      openNbCnt++;
    }
    if (grid[i+1][j] && (i < depth)) { 
      nb[openNbCnt] = reduceDim(i+1, j);
      openNbCnt++;
    }
    if (grid[i][j-1] && (j > 1)) { 
      nb[openNbCnt] = reduceDim(i, j-1);
      openNbCnt++;
    }
    if (grid[i][j+1] && (j < depth)) { 
      nb[openNbCnt] = reduceDim(i, j+1);
      openNbCnt++;
    }

    if (openNbCnt == 1) {     // Only one neighbor => Just union them
      gridstatus.union(reduceDim(i, j), nb[0]);
    }

    if (openNbCnt >= 2) {     // Two open neighbors, check if neighbors are connected
      gridstatus.union(reduceDim(i, j), nb[0]);
      for (int num = openNbCnt-1; num >= 1; num--) {
        if (!gridstatus.connected(reduceDim(i, j), nb[num])) {
          gridstatus.union(reduceDim(i, j), nb[num]);
        }
      }
    }
  }

  private int reduceDim(int i, int j) {    // Convert the 2D array coordinate into 1D
    int pos = (depth)*(i - 1) + j;
    return pos;
  }
  public boolean isOpen(int i, int j) {   // is site (row i, column j) open?
    checkRange(i, j);
    return grid[i][j];
  }
  public boolean isFull(int i, int j) {   // is site (row i, column j) full?
    checkRange(i, j);
    return gridstatus.connected(0, ((depth)*(i - 1) + j));
  }
  public boolean percolates() {           // does the system percolate?
    return gridstatus.connected(0, (depth * depth + 1));
  } 
  private void checkRange(int i, int j) {
    if (i < 1 || j < 1 || i > depth || j > depth) {
      throw new IndexOutOfBoundsException();
    }
  }
  public static void main(String[] args) { } // test client (optional)
}