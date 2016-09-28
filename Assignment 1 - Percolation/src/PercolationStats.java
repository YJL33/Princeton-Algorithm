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

    // perform t independent experiments on an n-by-n grid
    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        double[] results = new double[t];
        for (int numOfExec = 0; numOfExec < t; numOfExec++) {
            Percolation perc = new Percolation(n);        // Step 1
            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, n+1);      // Step 2.1
                // System.out.printf("[row: %d,", row);      
                int col = StdRandom.uniform(1, n+1);
                // System.out.printf("col: %d]\n", col);
                perc.open(row, col);                      // Step 2.2
            }
            double openSites = 0;                         // Step 3
            for (int row = 1; row <= n; row++) {
                for (int col = 1; col <= n; col++) {
                    if (perc.isOpen(row, col)) {
                        openSites += 1;
                    }
                }
            }
            results[numOfExec] = (openSites)/(n * n);
        }
        outputs[0] = StdStats.mean(results);
        outputs[1] = StdStats.stddev(results);
        outputs[2] = StdStats.mean(results) - 1.96*StdStats.stddev(results)/(Math.sqrt(results.length)); 
        outputs[3] = StdStats.mean(results) + 1.96*StdStats.stddev(results)/(Math.sqrt(results.length));
        // System.out.printf("mean: %f, stddev: %f, conf: %f, %f\n", outputs[0], outputs[1], outputs[2], outputs[3]);
    }

    public double mean() {             // sample mean of percolation threshold
        return outputs[0];
    }
    public double stddev() {           // sample standard deviation of percolation threshold
        return outputs[1];
    }    
    public double confidenceLo() {     // low  endpoint of 95% confidence interval
        return outputs[2];
    }
    public double confidenceHi() {     // high endpoint of 95% confidence interval
        return outputs[3];
    }

    public static void main(String[] args) {  // test client (described below)
        PercolationStats percSet = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.printf("mean: %f\n", percSet.mean());
        System.out.printf("stddev: %f\n", percSet.stddev());
        System.out.printf("conf: %f, %f\n", percSet.confidenceLo(), percSet.confidenceHi());
    }
}

