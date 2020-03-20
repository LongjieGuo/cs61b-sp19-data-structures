public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T []) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
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
/*
    public ArrayDeque(ArrayDeque other) {
        items = (T []) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
        for (int i = 0; i < other.size(); i++) {
            addLast((T) other.get(i));
        }
    }*/

    private void resize(int cap) {
        T[] newList = (T []) new Object[cap];
        for (int i = 0; i < size; i++) {
            newList[i] = get(i);
        }
        items = newList;
        nextFirst = cap - 1;
        nextLast = size;
    }

    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T removed = items[plusOne(nextFirst)];
        nextFirst = plusOne(nextFirst);
        size -= 1;
        if (size < (items.length / 4)) {
            resize(items.length / 2);
        }
        return removed;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T removed = items[minusOne(nextLast)];
        nextLast = minusOne(nextLast);
        size -= 1;
        if (size < (items.length / 4)) {
            resize(items.length / 2);
        }
        return removed;
    }

    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i));
            if (i < size - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int position = nextFirst + index + 1;
        if (position >= items.length) {
            position -= items.length;
        }
        return items[position];
    }

    private int minusOne(int index) {
        if (index == 0) {
            return items.length - 1;
        }
        return index - 1;
    }

    private int plusOne(int index) {
        if (index == items.length - 1) {
            return 0;
        }
        return index + 1;
    }
/*
    public static void main(String[] args) {
        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addLast(2);
        lld1.addLast(2);
        lld1.addLast(2);
        lld1.addLast(2);
        lld1.addFirst(2);
        lld1.addFirst(2);
        lld1.addFirst(100);
        int a = lld1.removeFirst();
        ArrayDeque<Integer> lld2 = new ArrayDeque<>(lld1);
    }

 */
}
