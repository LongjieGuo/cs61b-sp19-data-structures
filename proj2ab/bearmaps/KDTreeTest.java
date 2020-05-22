package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static org.junit.Assert.assertEquals;

/* @source from Josh Hug */

public class KDTreeTest {
    private static Random r = new Random(500);

    private List<Point> generateList(int N) {
        List<Point> lst = new ArrayList<Point>();
        for (int i = 0; i < N; i++) {
            Point p = new Point(r.nextDouble(), r.nextDouble());
            lst.add(p);
        }
        return lst;
    }

    @Test
    public void testCorrectnessWithLargeRandomizedNumbers() {
        //int pointSize = 100000;
        //int querySize = 10000;
        int pointSize = 10000;
        int querySize = 1000;
        List<Point> lst = generateList(pointSize);
        KDTree kdt = new KDTree(lst);
        NaivePointSet nps = new NaivePointSet(lst);
        List<Point> queries = generateList(querySize);
        for (Point p : queries) {
            Point expected = nps.nearest(p.getX(), p.getY());
            Point actual = kdt.nearest(p.getX(), p.getY());
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testEfficiency() {
        int pointSize = 100000;
        int querySize = 10000;
        List<Point> lst = generateList(pointSize);
        NaivePointSet nps = new NaivePointSet(lst);
        KDTree kdt = new KDTree(lst);
        List<Point> queries = generateList(querySize);
        Stopwatch sw1 = new Stopwatch();
        for (Point p : queries) {
            nps.nearest(p.getX(), p.getY());
        }
        System.out.println("Time elapsed for NPS: " + sw1.elapsedTime());
        Stopwatch sw2 = new Stopwatch();
        for (Point p : queries) {
            kdt.nearest(p.getX(), p.getY());
        }
        System.out.println("Time elapsed for KDT: " + sw2.elapsedTime());
    }


}
