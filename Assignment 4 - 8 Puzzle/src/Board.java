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
    private int[] tile;
    private int side;               // the length of board

    public Board(int[][] blocks) {
        this(convertToOneD(blocks), blocks.length);
    }
    private Board(int[] oneD, int sz) {       // Overload => make 1D tile
        this.side = sz;
        this.tile = oneD;
    }

    private static int[] convertToOneD(int[][] twoD) {
        int[] oneD = new int[twoD.length * twoD.length];
        for (int i = 0; i < twoD.length; i++) {
            for (int j = 0; j < twoD[0].length; j++) {
                oneD[i * twoD.length + j] = twoD[i][j];
            }
        }
        return oneD;
    }

    // Custom method: get this block's neighbors
    private void getNeighbors() {
        // we don't know the number of neighbors now, so use arraylist now
        ArrayList<Board> nbs = new ArrayList<>();
        int zpos = 0;
        while (zpos+1 < side*side) {
            if (tile[zpos] == 0) {
                break;
            }
            zpos++;
        }
        if (zpos >= side) {
            int[] nb = copy(tile);
            swapGrid(nb, zpos, zpos-side);
            nbs.add(new Board(nb, side));
        }
        if (zpos < side*(side-1)) {
            int[] nb = copy(tile);
            swapGrid(nb, zpos, zpos+side);
            nbs.add(new Board(nb, side));
        }
        if (zpos % side > 0) {
            int[] nb = copy(tile);
            swapGrid(nb, zpos, zpos-1);
            nbs.add(new Board(nb, side));
        }
        if (zpos % side < side-1) {
            int[] nb = copy(tile);
            swapGrid(nb, zpos, zpos+1);
            nbs.add(new Board(nb, side));
        }
        neighbors = nbs.toArray(new Board[nbs.size()]);
    }
    // board dimension n
    public int dimension() {
        return side;
    }
    // number of block out of place
    public int hamming() {
        int count = 0;           // Here we ignore the last position
        for (int pos = 0; pos+1 < (side*side); pos++) {
            if (tile[pos] != pos+1) count++;
        }
        return count;
    }
    // sum of Manhattan distances between tile and goal
    public int manhattan() {
        int dist = 0;
        for (int goalpos = 0; goalpos+1 < (side*side); goalpos++) {
            int tilepos = 0;
            while (tilepos+1 < (side*side)) {
                if (tile[tilepos] == goalpos+1) break;
                tilepos++;
            }
            dist += Math.abs(tilepos/side - goalpos/side) +
                    Math.abs(tilepos % side - goalpos % side);
        }
        return dist;
    }
    public boolean isGoal() {
        return hamming() == 0;
    }
    private int compareTo(Board x) {
        if (manhattan() == x.manhattan()) { return 0; }
        if (manhattan() > x.manhattan()) { return 1; }
        return -1;
    }
    // custom methods: copy a 1D-array.
    private int[] copy(int[] arrayToCopy) {
        return arrayToCopy.clone();
    }
    // custom method: swap two value in a 1D-array.
    private void swapGrid(int[] tl, int pos1, int pos2) {
        int firstValue = tl[pos1];
        tl[pos1] = tl[pos2];
        tl[pos2] = firstValue;
    }
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[] twinBlocks = copy(tile);

        int p = 0;
        // pick [0][0] and [0][1]
        if (twinBlocks[p] == 0 || twinBlocks[p + 1] == 0) {
            p += side;
        }
        swapGrid(twinBlocks, p, p+1);
        return new Board(twinBlocks, side);     // convert this twin into Board
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        // simply check the dimension and each element.
        if (y == null) return false;                     // is it null?
        if (y.getClass() != getClass()) return false;    // are classes same?
        Board that = (Board) y;
        if (that.side != side) return false;             // are sizes same?

        for (int pos = 0; pos+1 < (side*side); pos++) {  // is everyone same?
            if (that.tile[pos] != tile[pos]) {
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
            res = res*prime + blocks[pos/side][pos % side];
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
                s.append(String.format("%2d ", tile[i * side + j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    public static void main(String[] args) {
    }
}
