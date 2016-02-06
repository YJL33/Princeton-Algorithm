/****************************************************
Monte Carlo simulation:
Perform a series of computational experiments
 1.Initialize all sites to be blocked.
 2.Repeat the following until the system percolates:
   Choose a site (row i, column j) uniformly at random among all blocked sites.
   Open the site (row i, column j). 
 3.The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold. 
****************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
   private double[] outputs = new double[4];

   public PercolationStats(int N, int T) {   // perform T independent experiments on an N-by-N grid
      if (N <= 0 || T <= 0) {
         throw new java.lang.IllegalArgumentException();
      }
      double[] results = new double[T];
      for (int num_of_exec = 0; num_of_exec < T; num_of_exec++) {
         Percolation perc = new Percolation(N);      // Step 1
         while (!perc.percolates()) {
            int row = StdRandom.uniform(1, N+1);      // Step 2.1
            /*debug*///System.out.printf("[row: %d,", row);     
            int col = StdRandom.uniform(1, N+1);
            /*debug*///System.out.printf("col: %d]\n", col);
            perc.open(row, col);                     // Step 2.2
         }
         double openSites = 0;                       // Step 3
         for (int row = 1; row <= N; row++) {
            for (int col = 1; col <= N; col++) {
               if (perc.isOpen(row, col)) {
                  openSites += 1;
               }
            }
         }
         results[num_of_exec] = (openSites)/(N * N);
      }
      outputs[0] = StdStats.mean(results);
      outputs[1] = StdStats.stddev(results);
      outputs[2] = StdStats.mean(results) - 1.96*StdStats.stddev(results)/(Math.sqrt(results.length)); 
      outputs[3] = StdStats.mean(results) + 1.96*StdStats.stddev(results)/(Math.sqrt(results.length));
      /*debug*/
      //System.out.printf("mean: %f, stddev: %f, conf: %f, %f\n", outputs[0], outputs[1], outputs[2], outputs[3]);
   }

   public double mean() {                    // sample mean of percolation threshold
      return outputs[0];
   }
   public double stddev() {                  // sample standard deviation of percolation threshold
      return outputs[1];
   }   
   public double confidenceLo() {            // low  endpoint of 95% confidence interval
      return outputs[2];
   }
   public double confidenceHi() {            // high endpoint of 95% confidence interval
      return outputs[3];
   }

   public static void main(String[] args) {  // test client (described below)
      PercolationStats perc_set = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
      System.out.printf("mean: %f\n", perc_set.mean());
      System.out.printf("stddev: %f\n", perc_set.stddev());
      System.out.printf("conf: %f, %f\n", perc_set.confidenceLo(), perc_set.confidenceHi());
   }
}

