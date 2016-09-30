/******************************************************************************
 Compilation:  javac Board.java
 Execution:
 Dependencies:
 
 Details: check http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html
 
 Corner cases.
 You may assume that the constructor receives an n-by-n array,
 containing the n2 integers between 0 and n2 − 1,
 where 0 represents the blank square.
 
 Performance requirements.
 Your implementation should have all Board methods in time proportional to n^2
 (or better) in the worst case.

******************************************************************************/
import edu.princeton.cs.algs4.MinPQ

public class Board implements Comparable<Board> {

    private int[][] blocks;
    private int[][] goal;
    private int side;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {

        this.blocks = blocks;

        side = blocks.length;
    }
    // board dimension n
    public int dimension() {
        return side;
    }
    // number of blocks out of place
    public int hamming() {
        int count = 0;
        for (int pos=0; pos+1<(side*side); pos++) {
            if (blocks[pos/side][pos%side] != pos+1) count++;
        }
        return count
    }
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int dist = 0;
        for (int goalpos=0; goalpos+1<(side**2); goalpos++) {
            int pos1=0;
            while (pos1+1<(side*side))) {
                if (blocks[pos1/side][pos1%side] == goalpos+1) break;
                pos1++;
            }
            dist += abs(pos1/side-goalpos/side) + abs(pos1%side-goalpos%side);
        }
        return dist;
    }
    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }
    public int compareTo(Board x) {
        if (manhattan() == x.manhattan()) {
            return 0;
        }
        if (manhattan() > x.manhattan()) {
            return 1;
        }
        return -1;
    }
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        throw new NoSuchElementException("Not implement yet.");
    }
    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;

        Board that = (Board) y;
        if (this.tiles.length != that.tiles.length) return false;
        for (int i = 0; i < tiles.length; i++) {
            if (this.tiles[i].length != that.tiles[i].length) return false;
            for (int j = 0; j < tiles[i].length; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) return false;
            }
        }

        return true;
    }
    // all neighboring boards
    public Iterable<Board> neighbors() {

    }
    // string representation of this board (in the specified output format)
    public String toString() {

    }
    // unit tests (not graded)
    public static void main(String[] args) {

    }
}