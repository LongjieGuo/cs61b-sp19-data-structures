import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Iterator;

public class TestMyIterator {
    @Test
    public void testIterator() {
        MyHashMap<Integer, Integer> hm = new MyHashMap<>();
        hm.put(1, 2);
        hm.put(2, 3);
        hm.put(3, 4);
        Iterator<Integer> iter = hm.iterator();
        assertTrue(iter.hasNext());
        int i = iter.next();
        assertEquals(1, i);
        assertTrue(iter.hasNext());
        i = iter.next();
        assertEquals(2, i);
        assertTrue(iter.hasNext());
        i = iter.next();
        assertEquals(3, i);
        assertFalse(iter.hasNext());
    }
}
