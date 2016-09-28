/*
Subset client. 

Write a client program Subset.java that takes a command-line integer k;
reads in a sequence of N strings from standard input using StdIn.readString();
and prints out exactly k of them, uniformly at random.

Each item from the sequence can be printed out at most once.
You may assume that 0 ≦ k ≦ N, where N is the number of string on standard input.
*/
import edu.princeton.cs.algs4.StdIn;   // compile with javac-algs4, execute with java-algs4

public class Subset {
    public static void main(String[] args) {
        // RandomizedQueue in the same dir
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]); 
        while (StdIn.hasNextLine() && !StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }
        for (int i = 0; i < k; i++) {
            System.out.println(rq.dequeue());
        }
    }
}