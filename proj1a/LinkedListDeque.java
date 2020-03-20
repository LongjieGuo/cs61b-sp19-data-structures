public class LinkedListDeque<T> {
    private class StuffNode {
        private T item;
        private StuffNode next;
        private StuffNode prev;

        StuffNode(StuffNode p, T i, StuffNode n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    private int size;
    private StuffNode sentinel;

    public LinkedListDeque() {
        sentinel = new StuffNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }
/*
    public LinkedListDeque(T x) {
        sentinel = new StuffNode(null, null, null);
        sentinel.next = new StuffNode(sentinel, x, sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }

 */
/*
    public LinkedListDeque(LinkedListDeque other) {
        sentinel = new StuffNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
        StuffNode p = other.sentinel.next;
        for (int i = 0; i < other.size(); i++) {
            addLast((T) p.item);
            p = p.next;
        }
    }
*/
    public void addFirst(T item) {
        sentinel.next = new StuffNode(sentinel, item, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    public void addLast(T item) {
        sentinel.prev = new StuffNode(sentinel.prev, item, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        StuffNode p = sentinel.next;
        for (int i = 0; i < size; i++) {
            System.out.print(p.item);
            if (i < size - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        } else {
            T content = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
            return content;
        }
    }

    public T removeLast() {
        if (sentinel.next == sentinel) {
            return null;
        } else {
            T content = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
            return content;
        }
    }

    public T get(int index) {
        int position = 0;
        StuffNode p = sentinel.next;
        while (position < index) {
            p = p.next;
            position += 1;
        }
        return p.item;
    }

    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        }
        return helper(sentinel.next, 0, index);
    }

    private T helper(StuffNode p, int position, int index) {
        if (position == index) {
            return p.item;
        } else {
            return helper(p.next, position + 1, index);
        }
    }

}
