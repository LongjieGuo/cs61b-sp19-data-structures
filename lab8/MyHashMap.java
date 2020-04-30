import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.HashSet;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private int bucketNum;
    private int size;
    private double loadFactor;
    private HashSet<K> keySet;
    private Item[] buckets;

    private class Item<K, V> {
        K key;
        V value;
        Item next;

        public Item() {
            key = null;
            value = null;
            next = null;
        }

        public Item(K k, V v, Item n) {
            key = k;
            value = v;
            next = n;
        }
    }

    public MyHashMap() {
        bucketNum = 16;
        loadFactor = 0.75;
        keySet = new HashSet<>();
        buckets = new Item[bucketNum];
        initializeArray();
        size = 0;
    }

    public MyHashMap(int initialSize) {
        bucketNum = initialSize;
        loadFactor = 0.75;
        keySet = new HashSet<>();
        buckets = new Item[bucketNum];
        initializeArray();
        size = 0;
    }

    public MyHashMap(int initialSize, double loadFactor) {
        bucketNum = initialSize;
        this.loadFactor = loadFactor;
        keySet = new HashSet<>();
        buckets = new Item[bucketNum];
        initializeArray();
        size = 0;
    }

    private void initializeArray() {
        for (int i = 0; i < bucketNum; i++) {
            Item<K, V> first = new Item<>();
            buckets[i] = first;
        }
    }

    @Override
    public void clear() {
        keySet = new HashSet<>();
        buckets = new Item[bucketNum];
        initializeArray();
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        /** Returns true if this map contains a mapping for the specified key. */
        if (key == null) {
            throw new IllegalArgumentException();
        }
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        /**
         * Returns the value to which the specified key is mapped, or null if this
         * map contains no mapping for the key.
         */
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int hash = hash(key);
        Item f = buckets[hash(key)];
        while (f != null) {
            if (key.equals(f.key)) {
                return (V) f.value;
            }
            f = f.next;
        }
        return null;
    }

    @Override
    public int size() {
        /** Returns the number of key-value mappings in this map. */
        return size;
    }

    @Override
    public void put(K key, V value) {
        /**
         * Associates the specified value with the specified key in this map.
         * If the map previously contained a mapping for the key,
         * the old value is replaced.
         */
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        if ((size * 1.0 / bucketNum) >= loadFactor) {
            resize();
        }
        Item i = buckets[hash(key)];
        while (!key.equals(i.key)) {
            if (i.next == null) {
                break;
            }
            i = i.next;
        }
        if (key.equals(i.key)) {
            i.value = value;
        } else {
            Item newItem = new Item(key, value, null);
            i.next = newItem;
            size += 1;
        }
        keySet.add(key);
    }

    private void resize() {
        Item[] newBuckets = new Item[bucketNum * 2];
        bucketNum *= 2;
        for (int i = 0; i < newBuckets.length; i++) {
            Item f = new Item();
            newBuckets[i] = f;
        }
        for (Item i : buckets) {
            Item p = i.next;
            while (p != null) {
                K key = (K) p.key;
                V value = (V) p.value;
                Item it = newBuckets[hash(key)];
                while (it.next != null) {
                    it = it.next;
                }
                Item newItem = new Item(key, value, null);
                it.next = newItem;
                p = p.next;
            }
        }
        buckets = newBuckets;
    }

    private int hash(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        return (key.hashCode() & 0x7fffffff) % bucketNum;
    }

    @Override
    public Set<K> keySet() {
        /** Returns a Set view of the keys contained in this map. */
        /*
        return keySet;
         */
        HashSet<K> keys = new HashSet<>();
        for (Item i : buckets) {
            Item p = i.next;
            while (p != null) {
                keys.add((K) p.key);
                p = p.next;
            }
        }
        return keys;
    }

    @Override
    public Iterator<K> iterator() {
        /*
        return keySet.iterator();
         */
        return new MyHashMapIterator<>();
    }

    private class MyHashMapIterator<K> implements Iterator<K> {
        private int i;
        private Item it;

        public MyHashMapIterator() {
            i = 0;
            it = buckets[i];
        }

        @Override
        public boolean hasNext() {
            int j = i;
            Item it2 = it;
            while (j < buckets.length) {
                if (it2.next != null) {
                    return true;
                }
                j += 1;
                if (j != buckets.length) {
                    it2 = buckets[j];
                }
            }
            return false;
        }

        @Override
        public K next() {
            while (i < buckets.length) {
                if (it.next != null) {
                    it = it.next;
                    return (K) it.key;
                }
                i += 1;
                if (i != buckets.length) {
                    it = buckets[i];
                }
            }
            throw new NoSuchElementException();
        }
    }

    @Override
    public V remove(K key) {
        /**
         * Removes the entry for the specified key only if it is currently mapped to
         * the specified value. Not required for Lab 8. If you don't implement this,
         * throw an UnsupportedOperationException.
         */
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (!containsKey(key)) {
            return null;
        }
        if ((size * 1.0 / bucketNum) < loadFactor) {
            resizeDown();
        }
        Item i = buckets[hash(key)];
        while (!key.equals(i.next.key)) {
            i = i.next;
        }
        V returnVal = (V) i.next.value;
        i.next = i.next.next;
        size -= 1;
        keySet.remove(key);
        return returnVal;
    }

    private void resizeDown() {
        Item[] newBuckets = new Item[bucketNum / 2];
        bucketNum /= 2;
        for (int i = 0; i < newBuckets.length; i++) {
            Item f = new Item();
            newBuckets[i] = f;
        }
        for (Item i : buckets) {
            Item p = i.next;
            while (p != null) {
                K key = (K) p.key;
                V value = (V) p.value;
                Item it = newBuckets[hash(key)];
                while (it.next != null) {
                    it = it.next;
                }
                Item newItem = new Item(key, value, null);
                it.next = newItem;
                p = p.next;
            }
        }
        buckets = newBuckets;
    }

    @Override
    public V remove(K key, V value) {
        /**
         * Removes the entry for the specified key only if it is currently mapped to
         * the specified value. Not required for Lab 8. If you don't implement this,
         * throw an UnsupportedOperationException.
         */
        return remove(key);
    }
}
