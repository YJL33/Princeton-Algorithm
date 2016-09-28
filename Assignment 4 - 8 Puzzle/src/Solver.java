/******************************************************************************
 Compilation:  javac Solver.java
 Execution:    java Solver input.txt
 Dependencies: StdIn.java StdOut.java
 
 Details: check http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html

 Corner cases.
 The constructor should throw a java.lang.NullPointerException
 if passed a null argument.
 
 Performance requirements.
 To implement the A* algorithm,
 you must use MinPQ from algs4.jar for the priority queue(s).

******************************************************************************/
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    public boolean isSolvable()            // is the initial board solvable?
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    public static void main(String[] args) // solve a slider puzzle (given below)
}
