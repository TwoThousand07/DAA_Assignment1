package daa;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SortersTest {

    @Test
    void testCorrectnessMergeSort() {
        Random rand = new Random();
        for (int t = 0; t < 100; t++) {
            int n = rand.nextInt(1000);
            int[] original = rand.ints(n).toArray();
            int[] expected = original.clone();
            Arrays.sort(expected);

            Sorters.mergeSort(original, new Metrics());
            assertArrayEquals(expected, original);
        }
    }

    @Test
    void testCorrectnessQuickSort() {
        Random rand = new Random();
        for (int t = 0; t < 100; t++) {
            int n = rand.nextInt(1000);
            int[] original = rand.ints(n).toArray();
            int[] expected = original.clone();
            Arrays.sort(expected);

            Sorters.quickSort(original, new Metrics());
            assertArrayEquals(expected, original);
        }
    }

    @Test
    void testQuickSelect() {
        Random rand = new Random();
        for (int t = 0; t < 100; t++) {
            int n = 1 + rand.nextInt(500);
            int k = rand.nextInt(n);
            int[] original = rand.ints(n).toArray();
            int[] expected = original.clone();
            Arrays.sort(expected);

            int result = Sorters.quickSelect(original.clone(), k, new Metrics());
            assertEquals(expected[k], result);
        }
    }

    @Test
    void testEdgeCases() {
        int[] empty = {};
        assertDoesNotThrow(() -> Sorters.mergeSort(empty, new Metrics()));
        assertDoesNotThrow(() -> Sorters.quickSort(empty, new Metrics()));

        int[] single = {42};
        Sorters.mergeSort(single, new Metrics());
        assertArrayEquals(new int[]{42}, single);

        int[] duplicates = {5, 5, 5, 5, 5};
        int[] expected = {5, 5, 5, 5, 5};
        Sorters.quickSort(duplicates, new Metrics());
        assertArrayEquals(expected, duplicates);
    }

    @Test
    void testQuickSortDepthOnSorted() {
        int n = 100000;
        int[] sorted = new int[n];
        for (int i = 0; i < n; i++) sorted[i] = i;

        Metrics metrics = new Metrics();
        Sorters.quickSort(sorted, metrics);

        double maxAllowedDepth = 2.0 * (Math.log(n) / Math.log(2));
        assertTrue(metrics.getMaxDepth() <= maxAllowedDepth,
                "Depth " + metrics.getMaxDepth() + " exceeded limit " + maxAllowedDepth);
    }
}