package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private Percolation p;
    private double[] x;
    private int times;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        // perform T independent experiments on an N-by-N grid
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        x = new double[T];
        times = T;
        // perform experiments
        for (int i = 0; i < T; i++) {
            p = pf.make(N);
            x[i] = 0;
            while (!p.percolates()) {
                p.open(StdRandom.uniform(N), StdRandom.uniform(N));
            }
            x[i] = (p.numberOfOpenSites() * 1.0) / (N * N);
        }
    }

    public double mean() {
        // sample mean of percolation threshold
        return StdStats.mean(x);
    }

    public double stddev() {
        // sample standard deviation of percolation threshold
        return StdStats.stddev(x);
    }

    public double confidenceLow() {
        // low endpoint of 95% confidence interval
        return mean() - 1.96 * stddev() / Math.sqrt(times);
    }

    public double confidenceHigh() {
        // high endpoint of 95% confidence interval
        return mean() + 1.96 * stddev() / Math.sqrt(times);
    }
}
