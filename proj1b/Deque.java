public interface Deque<T> {
    void addFirst(T item);
    void addLast(T item);
    int size();
    void printDeque();
    T removeFirst();
    T removeLast();
    T get(int index);

    default boolean isEmpty() {
        if (size() == 0) {
            return true;
        }
        return false;
    }
}
