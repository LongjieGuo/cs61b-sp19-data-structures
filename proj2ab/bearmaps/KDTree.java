package bearmaps;

import java.util.List;

public class KDTree implements PointSet {
    private Node root;

    private class Node {
        private Point p;
        private Node left, right;
        private char orientation;

        public Node(Point p, char o) {
            this.p = p;
            left = null;
            right = null;
            orientation = o;
        }
    }

    public KDTree(List<Point> points) {
        for (Point p : points) {
            root = insert(root, p, 'h');
        }
    }

    private Node insert(Node n, Point p, char o) {
        if (n == null) {
            return new Node(p, o);
        }
        if (n.p.equals(p)) {
            return n;
        }
        if (comparePoint(p, n.p, o) >= 0) {
            n.right = insert(n.right, p, changeOrientation(o));
        } else {
            n.left = insert(n.left, p, changeOrientation(o));
        }
        return n;
    }

    private int comparePoint(Point p1, Point p2, char o) {
        /*
        returns the value 0 if d1 is numerically equal to d2;
        a value less than 0 if d1 is numerically less than d2;
        and a value greater than 0 if d1 is numerically greater than d2.
        */
        if (o == 'h') {
            return Double.compare(p1.getX(), p2.getX());
        } else if (o == 'v') {
            return Double.compare(p1.getY(), p2.getY());
        } else {
            throw new IllegalArgumentException();
        }
    }

    private char changeOrientation(char o) {
        if (o == 'h') {
            return 'v';
        } else if (o == 'v') {
            return 'h';
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        return nearest(root, goal, root).p;
    }

    private Node nearest(Node n, Point goal, Node best) {
        if (n == null) {
            return best;
        }
        if (Point.distance(n.p, goal) < Point.distance(best.p, goal)) {
            best = n;
        }
        Node goodSide;
        Node badSide;
        if (n.orientation == 'h') {
            if (goal.getX() < n.p.getX()) {
                goodSide = n.left;
                badSide = n.right;
            }
            else {
                goodSide = n.right;
                badSide = n.left;
            }
        } else if (n.orientation == 'v'){
            if (goal.getY() < n.p.getY()) {
                goodSide = n.left;
                badSide = n.right;
            }
            else {
                goodSide = n.right;
                badSide = n.left;
            }
        } else {
            throw new IllegalArgumentException();
        }
        best = nearest(goodSide, goal, best);
        if (worthLookingTheBadSide(n, goal, best)) {
            best = nearest(badSide, goal, best);
        }
        return best;
    }

    private boolean worthLookingTheBadSide(Node n, Point goal, Node best) {
        if (n.orientation == 'h') {
            if ((n.p.getX() - goal.getX()) * (n.p.getX() - goal.getX()) >= Point.distance(goal, best.p)) {
                return false;
            } else {
                return true;
            }
        } else if (n.orientation == 'v') {
            if ((n.p.getY() - goal.getY()) * (n.p.getY() - goal.getY()) >= Point.distance(goal, best.p)) {
                return false;
            } else {
                return true;
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
    }
}
