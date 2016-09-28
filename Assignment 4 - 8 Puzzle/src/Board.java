/******************************************************************************
 Compilation:  javac Board.java
 Execution:    java Board input.txt
 Dependencies: StdIn.java StdOut.java
 
 Details: check http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html
 
 Corner cases.
 You may assume that the constructor receives an n-by-n array,
 containing the n2 integers between 0 and n2 − 1,
 where 0 represents the blank square. 
 
 Performance requirements.
 Your implementation should have all Board methods in time proportional to n^2
 (or better) in the worst case.

******************************************************************************/
public class Board {
    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
                                           // (where blocks[i][j] = block in row i, column j)
    public int dimension()                 // board dimension n
    public int hamming()                   // number of blocks out of place
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    public boolean isGoal()                // is this board the goal board?
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    public boolean equals(Object y)        // does this board equal y?
    public Iterable<Board> neighbors()     // all neighboring boards
    public String toString()               // string representation of this board (in the output format specified below)

    public static void main(String[] args) // unit tests (not graded)
}