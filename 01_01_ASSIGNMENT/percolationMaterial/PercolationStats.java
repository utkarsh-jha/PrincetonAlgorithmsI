import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;

    private final int experimentNo;
    private final double[] fractions;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        experimentNo = trials;
        fractions = new double[experimentNo];

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int rowRand = StdRandom.uniform(1, n + 1);
                int colRand = StdRandom.uniform(1, n + 1);
                p.open(rowRand, colRand);
            }
            double fraction = (double) p.numberOfOpenSites() / (n * n);
            fractions[i] = fraction;
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractions);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fractions);
    }


    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((CONFIDENCE_95 * stddev()) / Math.sqrt(experimentNo));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((CONFIDENCE_95 * stddev()) / Math.sqrt(experimentNo));
    }

    private void printResults() {
        edu.princeton.cs.algs4.StdOut.printf("%-23s = %f\n", "mean", mean());
        edu.princeton.cs.algs4.StdOut.printf("%-23s = %f\n", "stddev", stddev());
        edu.princeton.cs.algs4.StdOut
                .printf("%-23s = [%f, %f]\n", "95% confidence interval", confidenceLo(),
                        confidenceHi());
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);
        // PercolationStats ps = new PercolationStats(200, 100);
        ps.printResults();
    }


}