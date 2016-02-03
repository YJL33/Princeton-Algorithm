
public class Percolation {                 // create N-by-N grid, with all sites blocked
  private boolean[][] grid;
  private boolean[][] gridstatus;
  private int depth;
  public Percolation(int k) {
    grid = new boolean[k+2][k+2];              // true = open, false = blocked
    gridstatus = new boolean[k+2][k+2];        // true = full, false = empty
    for (int x = 0; x < k + 2; x++) {
      gridstatus[0][x] = true;
    }
    depth = k;
  } 
  public void open(int i, int j) {        // open site (row i, column j) if it is not open already
    checkRange(i, j);
    if (isOpen(i, j)) return; 
    grid[i][j] = true;
    if ((i >= 1) && (i <= depth)) {         // check whether it's full
        if (gridstatus[i-1][j] || gridstatus[i][j+1] || gridstatus[i][j-1]) {
            gridstatus[i][j] = true;        // It's full because one of its neighbors is full
        }
    }
    int count = 1;
    while (count != 0) {                       // Now check whether there are status changes caused by this open
        count = 0;
        for (int y = 1; y <= depth; ++y) {     // Candidate which may change the status, and one of its neighbor is full
            for (int x = 1; x <= depth; ++x) {
                if (isOpen(y, x) && !isFull(y, x) && 
                  (gridstatus[y-1][x] || gridstatus[y+1][x] || gridstatus[y][x-1] || gridstatus[y][x+1])) { 
                      gridstatus[y][x] = true;
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
    return gridstatus[i][j];
  }
  public boolean percolates() {           // does the system percolate?
    int x = 1;
    boolean perc_or_not = false;
    while ((x <= depth) && !perc_or_not) {
        if (gridstatus[depth][x]) {
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