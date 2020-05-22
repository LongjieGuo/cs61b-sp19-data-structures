package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet {
    private List<Point> lst;

    public NaivePointSet(List<Point> points) {
        lst = points;
    }

    @Override
    public Point nearest(double x, double y) {
        Point target = new Point(x, y);
        Point best = lst.get(0);
        for (Point p : lst) {
            if (Point.distance(p, target) < Point.distance(best, target)) {
                best = p;
            }
        }
        return best;
    }
}
