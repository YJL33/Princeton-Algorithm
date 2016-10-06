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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board {

    private Board[] neighbors;      // a 1D array that stores neighbor boards
    private int[][] blocks;         // a 2D array describes current board
    private int side;               // the length of board

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.blocks = blocks;
        this.side = blocks.length;
    }
    // Custom method: get this block's neighbors
    private void getNeighbors() {
        // we don't know the number of neighbors now, so use arraylist now
        ArrayList<Board> nbs = new ArrayList<>();
        int zpos = 0;
        while (zpos+1 < side*side) {
            if (blocks[zpos/side][zpos%side] == 0) {
                break;
            }
            zpos++;
        }
        if (zpos/side > 0) {
            int[][] nb = copy(blocks);
            swapGrid(nb, zpos/side, zpos%side, (zpos/side)-1, zpos%side);
            nbs.add(new Board(nb));
        }
        if (zpos/side < side-1) {
            int[][] nb = copy(blocks);
            swapGrid(nb, zpos/side, zpos%side, (zpos/side)+1, zpos%side);
            nbs.add(new Board(nb));
        }
        if (zpos%side > 0) {
            int[][] nb = copy(blocks);
            swapGrid(nb, zpos/side, zpos%side, (zpos/side), (zpos%side)-1);
            nbs.add(new Board(nb));
        }
        if (zpos%side < side-1) {
            int[][] nb = copy(blocks);
            swapGrid(nb, zpos/side, zpos%side, (zpos/side), (zpos%side)+1);
            nbs.add(new Board(nb));
        }
        neighbors = nbs.toArray(new Board[nbs.size()]);
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
        return count;
    }
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int dist = 0;
        for (int goalpos=0; goalpos+1<(side*side); goalpos++) {
            int pos1=0;
            while (pos1+1<(side*side)) {
                if (blocks[pos1/side][pos1%side] == goalpos+1) break;
                pos1++;
            }
            dist += Math.abs(pos1/side-goalpos/side) + Math.abs(pos1%side-goalpos%side);
        }
        return dist;
    }
    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }
    private int compareTo(Board x) {
        if (manhattan() == x.manhattan()) {
            return 0;
        }
        if (manhattan() > x.manhattan()) {
            return 1;
        }
        return -1;
    }

    // custom methods: copy a 2D-array.
    private int[][] copy(int[][] arrayToCopy) {
        int[][] copy = new int[arrayToCopy.length][];
        for (int r = 0; r < arrayToCopy.length; r++) {
            copy[r] = arrayToCopy[r].clone();
        }
        return copy;
    }
    // custom method: swap two value in a 2D-array.
    private void swapGrid(int[][] blocks,
        int iFirstGrid, int jFirstGrid, int iSecondGrid, int jSecondGrid) {
        int firstValue = blocks[iFirstGrid][jFirstGrid];
        blocks[iFirstGrid][jFirstGrid] = blocks[iSecondGrid][jSecondGrid];
        blocks[iSecondGrid][jSecondGrid] = firstValue;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] twinBlocks = copy(blocks);

        int i = 0;
        int j = 0;
        // pick [0][0] and [0][1]
        if (twinBlocks[i][j] == 0 || twinBlocks[i][j + 1] == 0) {
            i++;
        }
        swapGrid(twinBlocks, i, j, i, j + 1);
        return new Board(twinBlocks);       // convert this twin into Board
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        // simply check the dimension and each element.
        if (y.getClass() != getClass()) return false;   // is class same?
        Board that = (Board) y;
        if (that.blocks.length != side) return false;             // is height same?
        if (that.blocks[0].length != side) return false;          // is width same?

        for (int pos=0; pos+1<(side*side); pos++) {       // is everyone same?
            if (that.blocks[pos/side][pos%side] != blocks[pos/side][pos%side]) {
                return false;
            }
        }
        return true;
    }
    /*
    @Override
    private int hashCode() {
        final int prime = 2;
        int res = 1;
        for (int pos=0; pos+1<side*side; pos++) {
            res = res*prime + blocks[pos/side][pos%side];
        }
        return res;
    }
    */
    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                if (neighbors == null) {
                    getNeighbors();
                }
                return new NeighborsIterator();
            }
        };
    }

    // Custom method: generate iterable neighbors()
    private class NeighborsIterator implements Iterator<Board> {
        private int index = 0;
        @Override
        public boolean hasNext() {
            return index < neighbors.length;
        }
        @Override
        public Board next() {
            if (hasNext()) {
                Board bd = neighbors[index++];
                return bd;
            }
            else {
                throw new NoSuchElementException("No next neighbor");
            }
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported");
        }
    }

    // string representation of this board (in the specified output format)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(side + "\n");
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    // unit tests (not graded)
    public static void main(String[] args) {

    }
}