package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private WeightedQuickUnionUF u;
    private WeightedQuickUnionUF u2; // without bottom
    private int gridN;
    private int numberOfOpenSites;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        // create N-by-N grid, with all sites initially blocked
        grid = new boolean[N][N];
        // set the initial values to FALSE
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = false;
            }
        }
        u = new WeightedQuickUnionUF(N * N + 2);
        u2 = new WeightedQuickUnionUF(N * N + 1);
        gridN = N;
        numberOfOpenSites = 0;
    }

    public void open(int row, int col)  {
        if (row >= gridN || col >= gridN || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException();
        }
        // open the site (row, col) if it is not open already
        if (!isOpen(row, col)) {
            numberOfOpenSites += 1;
            grid[row][col] = true;
            // connect with virtual top / bottom
            if (row == 0) {
                u.union(gridN * gridN, xyTo1D(row, col));
                u2.union(gridN * gridN, xyTo1D(row, col));
            }
            if (row == gridN - 1) {
                u.union(gridN * gridN + 1, xyTo1D(row, col));
            }
            // connect with surrounding blocks
            if (row > 0 && isOpen(row - 1, col)) {
                u.union(xyTo1D(row, col), xyTo1D(row - 1, col));
                u2.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            }
            if (row < gridN - 1 && isOpen(row + 1, col)) {
                u.union(xyTo1D(row, col), xyTo1D(row + 1, col));
                u2.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            }
            if (col < gridN - 1 && isOpen(row, col + 1)) {
                u.union(xyTo1D(row, col), xyTo1D(row, col + 1));
                u2.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            }
            if (col > 0 && isOpen(row, col - 1)) {
                u.union(xyTo1D(row, col), xyTo1D(row, col - 1));
                u2.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        // is the site (row, col) open?
        if (row >= gridN || col >= gridN || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException();
        }
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        // is the site (row, col) full?
        if (row >= gridN || col >= gridN || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException();
        }
        return u2.connected(xyTo1D(row, col), gridN * gridN);
    }

    public int numberOfOpenSites() {
        // number of open sites
        return numberOfOpenSites;
    }

    public boolean percolates() {
        // does the system percolate?
        return u.connected(gridN * gridN, gridN * gridN + 1);
    }

    public static void main(String[] args) {
        // use for unit testing (not required, but keep this here for the autograder)
    }

    private int xyTo1D(int r, int c) {
        return r * gridN + c;
    }
}
