/******************************************************************************
 Compilation:  javac Percolation.java
 Execution:    java Percolation input.txt
 Dependencies: StdIn.java StdOut.java
 
 Details: check http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 
 Corner cases.
 By convention, the row and column indices i and j are integers between 1 and N,
 where (1, 1) is the upper-left site:
 Throw a java.lang.IndexOutOfBoundsException if any argument to open(), isOpen(),
 or isFull() is outside its prescribed range.
 The constructor should throw a java.lang.IllegalArgumentException if N ≦ 0.
 
 Performance requirements.
 The constructor should take time proportional to N2; all methods should
 take constant time + a constant number of calls to the union-find methods union(),
 find(), connected(), and count().
******************************************************************************/
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {            // create N-by-N grid, with all sites blocked
  private boolean[] grid;
  private WeightedQuickUnionUF gridStatusT = null;
  private WeightedQuickUnionUF gridStatusB = null;
  private int depth;
  private int isPerc;

  public Percolation(int k) {
    if (k <= 0) {
         throw new java.lang.IllegalArgumentException();
    }
    grid = new boolean[ k * k + 1 ];              // true = open, false = blocked
    gridStatusT = new WeightedQuickUnionUF(k * k + 1);    // pos 0 = TOP
    gridStatusB = new WeightedQuickUnionUF(k * k + 2);    // pos 0 = TOP, -1 = BOT
    depth = k;
    isPerc = k * k + 1;
  } 

  public void open(int i, int j) {        // open site (row i, column j)
    checkRange(i, j);                     // check whether it's effective
    if (isOpen(i, j)) return;             // check whether it's already opened
    grid[reduceDim(i, j)] = true;                    // OPEN IT.

    int openNbCnt = 0;
    int[] nb = {0, 0, 0, 0};              // Neighbors

    /* 1. Check whether is there any open neighborhood around,
          if only one => just union them
       2. If more than one: check their connectivity.
          if connected => just union anyone
       3. If not, union all of them.*/
    if (i == 1) {
      gridStatusT.union(0, reduceDim(i, j));      // First row, connect with TOP
      gridStatusB.union(0, reduceDim(i, j));
    }
    if (i == depth) {
      gridStatusB.union(isPerc, reduceDim(i, j));  // Last row, connect with BOTTOM
    }

    if ((i > 1) && (grid[reduceDim(i-1, j)])) {   // Check neighbors are open or not
      nb[openNbCnt] = reduceDim(i-1, j);
      openNbCnt++;
    }
    if ((i < depth) && (grid[reduceDim(i+1, j)])) { 
      nb[openNbCnt] = reduceDim(i+1, j);
      openNbCnt++;
    }
    if ((j > 1) && (grid[reduceDim(i, j-1)])) { 
      nb[openNbCnt] = reduceDim(i, j-1);
      openNbCnt++;
    }
    if ((j < depth) && (grid[reduceDim(i, j+1)])) { 
      nb[openNbCnt] = reduceDim(i, j+1);
      openNbCnt++;
    }

    for (int num = openNbCnt-1; num >= 0; num--) {
      gridStatusT.union(reduceDim(i, j), nb[num]);
      // if (!gridStatusB.connected(reduceDim(i, j), nb[num])) {
      gridStatusB.union(reduceDim(i, j), nb[num]);
      // }
    }
  }

  private int reduceDim(int i, int j) {   // Convert the 2D array coordinate into 1D
    int pos = (depth)*(i - 1) + j;
    return pos;
  }
  public boolean isOpen(int i, int j) {   // is site (row i, column j) open?
    checkRange(i, j);
    return grid[reduceDim(i, j)];
  }
  public boolean isFull(int i, int j) {   // is site (row i, column j) full?
    checkRange(i, j);
    return gridStatusT.connected(0, ((depth)*(i - 1) + j));
  }
  public boolean percolates() {           // does the system percolate?
    return gridStatusB.connected(isPerc, 0);
    // if (isPerc) return true;
    // for (int j = 1; j <= depth; j++) {
    //   if (isOpen(depth, j) && gridStatusT.connected(0, reduceDim(depth, j))) {
    //     isPerc = true;
    //     return true;
    //   }
    // }
    // return false;
  } 
  private void checkRange(int i, int j) {
    if (i < 1 || j < 1 || i > depth || j > depth) {
      throw new IndexOutOfBoundsException();
    }
  }
  public static void main(String[] args) { } // test client (optional)
}