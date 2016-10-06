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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    
    private SearchNode goal;
    
    private class SearchNode {
        private int moves;
        private Board board;
        private SearchNode preNode;
        
        public SearchNode(Board b) {
            moves = 0;
            preNode = null;
            board = b;
        }
    }
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        /* main search node */
        PriorityOrder po = new PriorityOrder();
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>(po);
        SearchNode sn = new SearchNode(initial);
        /* twin search node */
        PriorityOrder twinPo = new PriorityOrder();
        MinPQ<SearchNode> twinPq = new MinPQ<SearchNode>(twinPo);
        SearchNode twinSn = new SearchNode(initial.twin());
        pq.insert(sn);
        twinPq.insert(twinSn);
        
        /* delete the minimum priority node from queue until goal is reached */
        SearchNode minNode = pq.delMin();
        SearchNode twinMinNode = twinPq.delMin();
        while (!minNode.board.isGoal() && !twinMinNode.board.isGoal()) {
            for (Board b : minNode.board.neighbors()) {
                if ((minNode.preNode == null)
                        || !b.equals(minNode.preNode.board)) {
                    SearchNode node = new SearchNode(b);
                    node.moves = minNode.moves + 1;
                    node.preNode = minNode;
                    pq.insert(node);
                }
            }
            for (Board b : twinMinNode.board.neighbors()) {
                if ((minNode.preNode == null)
                        || !b.equals(twinMinNode.preNode.board)) {
                    SearchNode node = new SearchNode(b);
                    node.moves = twinMinNode.moves + 1;
                    node.preNode = twinMinNode;
                    twinPq.insert(node);
                }
            }
            minNode = pq.delMin();
            twinMinNode = twinPq.delMin();
        }
        
        if (minNode.board.isGoal())
            goal = minNode;
        else
            goal = null;
    }
    
    private class PriorityOrder implements Comparator<SearchNode> {
        public int compare(SearchNode s1, SearchNode s2) {
            int priority1 = s1.board.manhattan() + s1.moves;
            int priority2 = s2.board.manhattan() + s2.moves;
            
            if (priority1 > priority2) return 1;
            else if (priority1 < priority2) return -1;
            else return 0;
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return goal != null;
    }

    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if (goal == null) return -1;
        return goal.moves;
    }

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        if (!isSolvable())  return null;
        ArrayList<Board> bStack = new ArrayList<Board>();
        ArrayList<Board> ans = new ArrayList<Board>();
        for (SearchNode s = goal; s != null; s = s.preNode)
            bStack.add(s.board);
        
        if (bStack.size() > 0) {
            Collections.reverse(bStack);
            ans = bStack;
            return ans;
        }
        else {
            return null;
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // create initial board from file
        In in = new In(args[0]);
        int num = in.readInt();
        int[][] blocks = new int[num][num];
        for (int i = 0; i < num; i++)
            for (int j = 0; j < num; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}