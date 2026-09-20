package daa;

import java.util.Random;

public class Sorters {
    private static final int INSERTION_SORT_CUTOFF = 15;
    private static final Random RANDOM = new Random();

    public static void insertionSort(int[] a, int low, int high, Metrics metrics) {
        for (int i = low + 1; i <= high; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= low) {
                metrics.incrementComparisons();
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    j--;
                } else {
                    break;
                }
            }
            a[j + 1] = key;
        }
    }

    public static void mergeSort(int[] a, Metrics metrics) {
        if (a == null || a.length <= 1) return;
        int[] buffer = new int[a.length];
        mergeSortInternal(a, 0, a.length - 1, buffer, metrics);
    }

    private static void mergeSortInternal(int[] a, int low, int high, int[] buffer, Metrics metrics) {
        metrics.enterRecursion();
        try {
            if (high - low <= INSERTION_SORT_CUTOFF) {
                insertionSort(a, low, high, metrics);
                return;
            }

            int mid = low + (high - low) / 2;
            mergeSortInternal(a, low, mid, buffer, metrics);
            mergeSortInternal(a, mid + 1, high, buffer, metrics);

            metrics.incrementComparisons();
            if (a[mid] <= a[mid + 1]) {
                return;
            }

            merge(a, low, mid, high, buffer, metrics);
        } finally {
            metrics.exitRecursion();
        }
    }

    private static void merge(int[] a, int low, int mid, int high, int[] buffer, Metrics metrics) {
        System.arraycopy(a, low, buffer, low, high - low + 1);

        int i = low, j = mid + 1;
        for (int k = low; k <= high; k++) {
            if (i > mid) {
                a[k] = buffer[j++];
            } else if (j > high) {
                a[k] = buffer[i++];
            } else {
                metrics.incrementComparisons();
                if (buffer[j] < buffer[i]) {
                    a[k] = buffer[j++];
                } else {
                    a[k] = buffer[i++];
                }
            }
        }
    }

    public static void quickSort(int[] a, Metrics metrics) {
        if (a == null || a.length <= 1) return;
        quickSortInternal(a, 0, a.length - 1, metrics);
    }

    private static void quickSortInternal(int[] lowArr, int low, int high, Metrics metrics) {
        while (low < high) {
            metrics.enterRecursion();
            try {
                if (high - low <= INSERTION_SORT_CUTOFF) {
                    insertionSort(lowArr, low, high, metrics);
                    break;
                }

                int pivotIndex = low + RANDOM.nextInt(high - low + 1);
                int[] ltGt = partition3Way(lowArr, low, high, pivotIndex, metrics);
                int lt = ltGt[0];
                int gt = ltGt[1];

                if (lt - low < high - gt) {
                    quickSortInternal(lowArr, low, lt - 1, metrics);
                    low = gt + 1;
                } else {
                    quickSortInternal(lowArr, gt + 1, high, metrics);
                    high = lt - 1;
                }
            } finally {
                metrics.exitRecursion();
            }
        }
    }

    private static int[] partition3Way(int[] a, int low, int high, int pivotIndex, Metrics metrics) {
        int pivot = a[pivotIndex];
        swap(a, pivotIndex, low);

        int lt = low, gt = high;
        int i = low + 1;

        while (i <= gt) {
            metrics.incrementComparisons();
            if (a[i] < pivot) {
                swap(a, lt++, i++);
            } else {
                metrics.incrementComparisons();
                if (a[i] > pivot) {
                    swap(a, i, gt--);
                } else {
                    i++;
                }
            }
        }
        return new int[]{lt, gt};
    }

    public static int quickSelect(int[] a, int k, Metrics metrics) {
        if (a == null || a.length == 0 || k < 0 || k >= a.length) {
            throw new IllegalArgumentException("Invalid input array or k out of bounds.");
        }
        int low = 0, high = a.length - 1;
        while (low < high) {
            int pivotIndex = low + RANDOM.nextInt(high - low + 1);
            int[] ltGt = partition3Way(a, low, high, pivotIndex, metrics);
            int lt = ltGt[0];
            int gt = ltGt[1];

            if (k < lt) {
                high = lt - 1;
            } else if (k > gt) {
                low = gt + 1;
            } else {
                return a[k];
            }
        }
        return a[low];
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}