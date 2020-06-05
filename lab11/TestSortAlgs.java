import edu.princeton.cs.algs4.Queue;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Iterator;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<Integer> q = new Queue<>();
        q.enqueue(5);
        q.enqueue(4);
        q.enqueue(3);
        q.enqueue(2);
        q.enqueue(1);
        Queue<Integer> sorted = QuickSort.quickSort(q);
        Iterator<Integer> iter = sorted.iterator();
        int prev = iter.next();
        int current;
        while (iter.hasNext()) {
            current = iter.next();
            assertTrue(prev <= current);
            prev = current;
        }
    }

    @Test
    public void testMergeSort() {
        Queue<Integer> q = new Queue<>();
        q.enqueue(5);
        q.enqueue(4);
        q.enqueue(3);
        q.enqueue(2);
        q.enqueue(1);
        Queue<Integer> sorted = MergeSort.mergeSort(q);
        Iterator<Integer> iter = sorted.iterator();
        int prev = iter.next();
        int current;
        while (iter.hasNext()) {
            current = iter.next();
            assertTrue(prev <= current);
            prev = current;
        }
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
