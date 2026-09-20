package daa;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class BenchmarkRunner {
    public static void main(String[] args) {
        int[] sizes = {1000, 10000, 100000, 1000000};
        String[] algos = {"MergeSort", "QuickSort"};
        String[] inputTypes = {"random", "sorted", "duplicates"};
        int runs = 5;

        try (FileWriter writer = new FileWriter("results.csv")) {
            writer.append("algorithm,input,n,time_ms,comparisons,max_depth\n");

            for (String algo : algos) {
                for (String type : inputTypes) {
                    for (int n : sizes) {
                        if (algo.equals("QuickSort") && type.equals("sorted") && n == 1000000) {
                        }

                        long[] times = new long[runs];
                        long[] comps = new long[runs];
                        int[] depths = new int[runs];

                        for (int r = 0; r < runs; r++) {
                            int[] original = generateInput(n, type);
                            Metrics metrics = new Metrics();

                            long start = System.nanoTime();
                            if (algo.equals("MergeSort")) {
                                Sorters.mergeSort(original, metrics);
                            } else {
                                Sorters.quickSort(original, metrics);
                            }
                            long end = System.nanoTime();

                            times[r] = (end - start) / 1_000_000;
                            comps[r] = metrics.getComparisons();
                            depths[r] = metrics.getMaxDepth();
                        }

                        Arrays.sort(times);
                        Arrays.sort(comps);
                        Arrays.sort(depths);
                        int mid = runs / 2;

                        writer.append(algo).append(',')
                                .append(type).append(',')
                                .append(String.valueOf(n)).append(',')
                                .append(String.valueOf(times[mid])).append(',')
                                .append(String.valueOf(comps[mid])).append(',')
                                .append(String.valueOf(depths[mid])).append('\n');

                        System.out.printf("Finished: %s | %s | n=%d\n", algo, type, n);
                    }
                }
            }
            System.out.println("Benchmark completed successfully. Results saved to results.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[] generateInput(int n, String type) {
        Random rand = new Random(42);
        int[] a = new int[n];
        switch (type) {
            case "random":
                for (int i = 0; i < n; i++) a[i] = rand.nextInt();
                break;
            case "sorted":
                for (int i = 0; i < n; i++) a[i] = i;
                break;
            case "duplicates":
                for (int i = 0; i < n; i++) a[i] = rand.nextInt(10);
                break;
        }
        return a;
    }
}