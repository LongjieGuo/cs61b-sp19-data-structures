package bearmaps;

import org.junit.Test;
import static org.junit.Assert.*;
import edu.princeton.cs.algs4.Stopwatch;

public class ArrayHeapMinPQTest {

    @Test
    public void testAdd() {
        ArrayHeapMinPQ<Integer> minPQ = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> minPQN = new NaiveMinPQ<>();
        for (int i = 0; i < 1000; i++) {
            double n = Math.random();
            minPQ.add(i, n);
            minPQN.add(i, n);
        }
        int expected = minPQN.getSmallest();
        int real = minPQ.getSmallest();
        assertEquals(expected, real);
    }

    @Test
    public void testContains() {
        ArrayHeapMinPQ<Integer> minPQ = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> minPQN = new NaiveMinPQ<>();
        for (int i = 0; i < 100; i++) {
            double n = Math.random();
            minPQ.add(i, n);
            minPQN.add(i, n);
        }

        for (int i = 0; i < 100; i++) {
            assertTrue(minPQ.contains(i));
        }
        // speedContains();
    }

    @Test
    public void testGetSmallest() {
        ArrayHeapMinPQ<Integer> minPQ = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> minPQN = new NaiveMinPQ<>();
        for (int i = 0; i < 100; i++) {
            double n = Math.random();
            minPQ.add(i, n);
            minPQN.add(i, n);
            int expected = minPQN.getSmallest();
            int real = minPQ.getSmallest();
            assertEquals(expected, real);
        }
        // speedGetSmallest();
    }

    @Test
    public void testRemoveSmallest() {
        ArrayHeapMinPQ<Integer> minPQ = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> minPQN = new NaiveMinPQ<>();
        for (int i = 0; i < 100; i++) {
            minPQ.add(i, 0.01 * i);
            minPQN.add(i, 0.01 * i);
        }
        for (int i = 0; i < 40; i++) {
            minPQN.removeSmallest();
            minPQ.removeSmallest();
        }
        int expected = minPQN.getSmallest();
        int real = minPQ.getSmallest();
        assertEquals(expected, real);
        speedRemoveSmallest();
    }

    @Test
    public void testSize() {
        ArrayHeapMinPQ<Integer> minPQ = new ArrayHeapMinPQ<>();
        for (int i = 1; i < 100; i++) {
            minPQ.add(i, 1.00 / i);
            int real = minPQ.size();
            assertEquals(i, real);
        }
    }

    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ<Integer> minPQ = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> minPQN = new NaiveMinPQ<>();
        for (int i = 0; i < 100; i++) {
            double n = Math.random();
            minPQ.add(i, n);
            minPQN.add(i, n);
        }
        for (int i = 0; i < 100; i++) {
            double n = Math.random();
            minPQ.changePriority(i, n);
            minPQN.changePriority(i, n);
        }
        int expected = minPQN.getSmallest();
        int real = minPQ.getSmallest();
        assertEquals(expected, real);
    }

    public static void speedContains() {
        ArrayHeapMinPQ<Integer> minPQ = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> minPQN = new NaiveMinPQ<>();

        int j = 100000;

        for (int i = 0; i < j; i++) {
            double n = Math.random();
            minPQ.add(i, n);
        }

        Stopwatch sw1 = new Stopwatch();
        for (int i = 0; i < j; i++) {
            minPQ.contains(i);
        }
        double t1 = sw1.elapsedTime();

        for (int i = 0; i < j; i++) {
            double n = Math.random();
            minPQN.add(i, n);
        }

        Stopwatch sw2 = new Stopwatch();
        for (int i = 0; i < j; i++) {
            minPQN.contains(i);
        }
        double t2 = sw2.elapsedTime();

        System.out.println("Your solution: " + t1 +  " Naive solution: " + t2);
    }

    public static void speedGetSmallest() {
        ArrayHeapMinPQ<Integer> minPQ = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> minPQN = new NaiveMinPQ<>();

        int j = 100000;

        for (int i = 0; i < j; i++) {
            double n = Math.random();
            minPQ.add(i, n);
        }

        Stopwatch sw1 = new Stopwatch();
        for (int i = 0; i < j; i++) {
            minPQ.getSmallest();
        }
        double t1 = sw1.elapsedTime();

        for (int i = 0; i < j; i++) {
            double n = Math.random();
            minPQN.add(i, n);
        }

        Stopwatch sw2 = new Stopwatch();
        for (int i = 0; i < j; i++) {
            minPQN.getSmallest();
        }
        double t2 = sw2.elapsedTime();

        System.out.println("Your solution: " + t1 +  " Naive solution: " + t2);
    }

    public static void speedRemoveSmallest() {
        ArrayHeapMinPQ<Integer> minPQ = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> minPQN = new NaiveMinPQ<>();

        int j = 100000;

        for (int i = 0; i < j; i++) {
            double n = Math.random();
            minPQ.add(i, n);
        }

        Stopwatch sw1 = new Stopwatch();
        for (int i = 0; i < j - 1; i++) {
            minPQ.removeSmallest();
        }
        double t1 = sw1.elapsedTime();

        for (int i = 0; i < j; i++) {
            double n = Math.random();
            minPQN.add(i, n);
        }

        Stopwatch sw2 = new Stopwatch();
        for (int i = 0; i < j - 1; i++) {
            minPQN.removeSmallest();
        }
        double t2 = sw2.elapsedTime();

        System.out.println("Your solution: " + t1 +  " Naive solution: " + t2);
    }
}
