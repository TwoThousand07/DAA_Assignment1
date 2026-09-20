package daa;

public class Metrics {
    private long comparisons = 0;
    private int maxDepth = 0;
    private int currentDepth = 0;

    public void reset() {
        comparisons = 0;
        maxDepth = 0;
        currentDepth = 0;
    }

    public void incrementComparisons() {
        comparisons++;
    }

    public void enterRecursion() {
        currentDepth++;
        if (currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }
    }

    public void exitRecursion() {
        currentDepth--;
    }

    public long getComparisons() {
        return comparisons;
    }

    public int getMaxDepth() {
        return maxDepth;
    }
}
