package bearmaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<PriorityNode> lst;
    private HashMap<T, Integer> map;

    private class PriorityNode {
        private T item;
        private double priority;

        public PriorityNode(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }
    }

    public int leftChild(int index) {
        if (index * 2 >= lst.size()) {
            return index;
        }
        return index * 2;
    }

    public int rightChild(int index) {
        if (index * 2 + 1 >= lst.size()) {
            return index;
        }
        return index * 2 + 1;
    }

    public int parent(int index) {
        if (index == 1) {
            return 1;
        }
        return index / 2;
    }

    public ArrayHeapMinPQ() {
        lst = new ArrayList<>();
        lst.add(null);
        map = new HashMap<>();
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        PriorityNode node = new PriorityNode(item, priority);
        lst.add(node);
        map.put(item, lst.size() - 1);
        swim(lst.size() - 1);
    }

    private void swim(int k) {
        if (lst.get(parent(k)).priority > lst.get(k).priority) {
            swap(k, parent(k));
            swim(parent(k));
        }
    }

    private void swap(int a, int b) {
        map.replace(lst.get(a).item, b);
        map.replace(lst.get(b).item, a);
        PriorityNode tmp = lst.get(a);
        lst.set(a, lst.get(b));
        lst.set(b, tmp);
    }

    @Override
    public boolean contains(T item) {
        return map.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        return lst.get(1).item;
    }

    @Override
    public T removeSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        T returnValue = getSmallest();
        swap(1, lst.size() - 1);
        lst.remove(lst.size() - 1);
        map.remove(lst.size() - 1);
        sink(1);
        return returnValue;
    }

    private void sink(int k) {
        double p = lst.get(k).priority;
        double pl = lst.get(leftChild(k)).priority;
        double pr = lst.get(rightChild(k)).priority;
        if (p > pl || p > pr) {
            if (pl <= pr) {
                swap(k, leftChild(k));
                sink(leftChild(k));
            } else {
                swap(k, rightChild(k));
                sink(rightChild(k));
            }
        }
    }

    @Override
    public int size() {
        return lst.size() - 1;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ is empty");
        }
        lst.get(map.get(item)).priority = priority;
        swim(map.get(item));
        sink(map.get(item));
    }
}
