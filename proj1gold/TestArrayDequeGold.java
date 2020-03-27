import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void testArray() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> sad2 = new ArrayDequeSolution<>();
        String errorMessage = "";
        int size = 0;

        for (int i = 0; i < 100; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.25) {
                sad1.addLast(i);
                sad2.addLast(i);
                size += 1;
                errorMessage += "\naddLast(" + i + ")";
            } else if (numberBetweenZeroAndOne < 0.5) {
                sad1.addFirst(i);
                sad2.addFirst(i);
                size += 1;
                errorMessage += "\naddFirst(" + i + ")";
            } else if (numberBetweenZeroAndOne < 0.75) {
                if (size != 0) {
                    Integer a = sad1.removeLast();
                    Integer b = sad2.removeLast();
                    errorMessage += "\nremoveLast()";
                    assertEquals(errorMessage, a, b);
                    size -= 1;
                }
            } else {
                if (size != 0) {
                    Integer a = sad1.removeFirst();
                    Integer b = sad2.removeFirst();
                    errorMessage += "\nremoveFirst()";
                    assertEquals(errorMessage, a, b);
                    size -= 1;
                }
            }
        }

    }

}
