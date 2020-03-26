public class ArrayDeque<T> implements Deque<T>{
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

    @Override
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

    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    @Override
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

    @Override
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

    @Override
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i));
            if (i < size - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    @Override
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

}
