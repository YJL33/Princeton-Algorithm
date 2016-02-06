import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {                 // create N-by-N grid, with all sites blocked
  private boolean[][] grid;
  private boolean[][] gridSite;
  private WeightedQuickUnionUF gridstatus = null;
  private int depth;
  public Percolation(int k) {
    if (k <= 0) {
         throw new java.lang.IllegalArgumentException();
    }
    grid = new boolean[k+2][k+2];              // true = open, false = blocked
    gridSite = new boolean[k+2][k+2];          // true = full, false = empty
    for (int x = 0; x < k + 2; x++) {
      gridSite[0][x] = true;
    }
    gridstatus = new WeightedQuickUnionUF(k * k + 1);    // the connection status, position 0 = FULL
    depth = k;
  } 
  public void open(int i, int j) {        // open site (row i, column j) if it is not open already
    checkRange(i, j);                     // check whether it's effective
    if (isOpen(i, j)) return;             // check whether it's already opened
    grid[i][j] = true;                    // OPEN IT.

    int union_pos = (depth)*(i - 1) + j;

    if ((i >= 1) && (i <= depth)) {         // check whether it's full
        if (gridSite[i-1][j] || gridSite[i+1][j] || gridSite[i][j+1] || gridSite[i][j-1]) {
            gridSite[i][j] = true;          // It's full because one of its neighbors is full
            gridstatus.union(0, union_pos);
        }
    }
    int count = 1;
    while (count != 0) {                       // Now check whether there are status changes caused by this open
        count = 0;
        for (int y = 1; y <= depth; ++y) {     // Candidate which may change the status, and one of its neighbor is full
            for (int x = 1; x <= depth; ++x) {
                if (isOpen(y, x) && !isFull(y, x) && 
                  (gridSite[y-1][x] || gridSite[y+1][x] || gridSite[y][x-1] || gridSite[y][x+1])) { 
                      gridSite[y][x] = true;
                      gridstatus.union(0, (depth * (y - 1) + x));
                      count++;
                    }
                }
            }
        }    
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
    int x = 1;
    boolean perc_or_not = false;
    while ((x <= depth) && !perc_or_not) {
        if (isFull(depth, x)) {
            perc_or_not = true;
        }
        else {
            x++;
        }
    }
    return perc_or_not;
  } 
  private void checkRange(int i, int j) {
    if (i < 1 || j < 1 || i > depth || j > depth) {
      throw new IndexOutOfBoundsException();
    }
  }
  public static void main(String[] args) { } // test client (optional)
}