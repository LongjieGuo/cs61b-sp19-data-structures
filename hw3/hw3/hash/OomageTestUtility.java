package hw3.hash;

import java.util.List;
import java.util.HashMap;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        HashMap<Integer, Integer> buckets = new HashMap<Integer, Integer>();
        int N = 0;

        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            N += 1;
            if (!buckets.containsKey(bucketNum)) {
                buckets.put(bucketNum, 1);
            } else {
                buckets.replace(bucketNum, buckets.get(bucketNum) + 1);
            }
        }

        for (Integer b : buckets.values()) {
            if (b < N / 50 || b > N / 2.5) {
                return false;
            }
        }
        return true;
    }
}
