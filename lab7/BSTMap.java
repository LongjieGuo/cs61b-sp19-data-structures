import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.lang.UnsupportedOperationException;


public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root;

    private class Node {
        private K key;
        private V val;
        private Node left, right;
        private int size;

        public Node(K k, V v) {
            key = k;
            val = v;
            left = null;
            right = null;
            size = 1;
        }
    }

    public BSTMap() {
        root = null;
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        /* Returns true if this map contains a mapping for the specified key. */
        return containsKeyHelper(key, root);
    }

    private boolean containsKeyHelper(K k, Node n) {
        if (k == null) {
            throw new IllegalArgumentException();
        }
        if (n == null) {
            return false;
        }
        int cmp = k.compareTo(n.key);
        if (cmp == 0) {
            return true;
        } else if (cmp < 0) {
            // k < key
            return containsKeyHelper(k, n.left);
        } else {
            return containsKeyHelper(k, n.right);
        }
    }

    @Override
    public V get(K key) {
        /* Returns the value to which the specified key is mapped, or null if this
         * map contains no mapping for the key.
         */
        return getHelper(key, root);
    }

    private V getHelper(K k, Node n) {
        if (k == null) {
            throw new IllegalArgumentException();
        }
        if (n == null) {
            return null;
        }
        if (n.key == k) {
            return n.val;
        }
        int cmp = k.compareTo(n.key);
        if (cmp == 0) {
            return n.val;
        } else if (cmp < 0) {
            // k < key
            return getHelper(k, n.left);
        } else {
            return getHelper(k, n.right);
        }
    }

    @Override
    public int size() {
        /* Returns the number of key-value mappings in this map. */
        if (root == null) {
            return 0;
        }
        return root.size;
    };

    @Override
    public void put(K key, V value) {
        /* Associates the specified value with the specified key in this map. */
        root = putHelper(key, value, root);
    }

    private Node putHelper(K key, V value, Node node) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (node == null) {
            return new Node(key, value);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = putHelper(key, value, node.left);
        } else if (cmp > 0) {
            node.right = putHelper(key, value, node.right);
        }
        // modify the size of node
        int leftSize, rightSize;
        if (node.left == null) {
            leftSize = 0;
        } else {
            leftSize = node.left.size;
        }
        if (node.right == null) {
            rightSize = 0;
        } else {
            rightSize = node.right.size;
        }
        node.size = 1 + leftSize + rightSize;
        return node;
    }

    public void printInOrder() {

    }

    private void printHelper(Node node) {
        if (node == null) {
            return;
        }
        printHelper(node.left);
        System.out.println(node.val);
        printHelper(node.right);
    }

    // optional
    @Override
    public V remove(K key) {
        /* Removes the mapping for the specified key from this map if present.
         * Not required for Lab 8. If you don't implement this, throw an
         * UnsupportedOperationException. */
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        /* Removes the entry for the specified key only if it is currently mapped to
         * the specified value. Not required for Lab 8. If you don't implement this,
         * throw an UnsupportedOperationException.*/
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }

    @Override
    public Set<K> keySet() {
        /* Returns a Set view of the keys contained in this map. */
        throw new UnsupportedOperationException();
    }
}
