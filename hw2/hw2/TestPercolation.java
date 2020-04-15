package hw2;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestPercolation {

    @Test
    public void testFull() {
        Percolation p = new Percolation(5);
        p.open(3, 4);
        p.open(2, 4);
        p.open(2, 2);
        p.open(2, 3);
        p.open(0, 2);
        p.open(1, 2);
        assertTrue(p.isFull(3, 4));
        assertFalse(p.isFull(4, 0));
    }

    @Test
    public void testPercolation() {
        Percolation p = new Percolation(5);
        p.open(3, 4);
        p.open(2, 4);
        p.open(2, 2);
        p.open(2, 3);
        assertFalse(p.percolates());
        p.open(0, 2);
        p.open(1, 2);
        p.open(4, 4);
        assertTrue(p.percolates());
    }

    @Test
    public void testBackwash() {
        Percolation p = new Percolation(3);
        p.open(0, 0);
        p.open(1, 0);
        p.open(2, 0);
        p.open(1, 2);
        p.open(2, 2);
        assertFalse(p.isFull(2, 2));
        assertTrue(p.percolates());
    }

    @Test
    public void testPercolationStat() {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats ps = new PercolationStats(20, 100, pf);
        System.out.println(ps.mean());
        System.out.println(ps.stddev());
        System.out.println(ps.confidenceLow());
        System.out.println(ps.confidenceHigh());
    }
}
