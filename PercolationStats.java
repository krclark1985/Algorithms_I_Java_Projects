/* *****************************************************************************
 *  Name:              Kyle Clark
 *  Last modified:     8/2023
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] meanArray;
    private double trialThreshold;

    private int trialNum;

    private int openSites;
    private int size;
    private int t;

    // perform independent trials on an n by n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("n cannot be less than 1");
        }
        if (trials <= 0) {
            throw new IllegalArgumentException("trials cannot be less than 1");
        }
        size = n;
        t = trials;
        trialNum = 0;
        trialThreshold = 0.0;
        meanArray = new double[t];

        while (trialNum < t) {
            Percolation perc = new Percolation(n);
            openSites = 0;
            while (!perc.percolates()) {
                int row = StdRandom.uniformInt(n) + 1;
                int col = StdRandom.uniformInt(n) + 1;
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                    openSites++;
                }
            }
            trialThreshold = ((double) openSites / (double) (size * size));
            meanArray[trialNum] = trialThreshold;
            trialNum++;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(meanArray);
    }

    // sample std deviation of percolation threshold; if below doesn't work, may need std dev array instead using spec formula
    public double stddev() {
        return StdStats.stddev(meanArray);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double s = stddev();
        double trialRoot = Math.sqrt((double) t);
        double mean = mean();
        return mean - ((1.96 * s) / trialRoot);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double s = stddev();
        double trialRoot = Math.sqrt((double) t);
        double mean = mean();
        return mean + ((1.96 * s) / trialRoot);
    }

    // test client (2 command-lind args: n (size) and T (independent trials)

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println(
                "95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi()
                        + "]");
    }
}
