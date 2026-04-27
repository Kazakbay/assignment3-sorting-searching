/**
 * Experiment class — runs algorithm performance tests using System.nanoTime().
 */
public class Experiment {

    private final Sorter sorter;
    private final Searcher searcher;

    public Experiment() {
        this.sorter = new Sorter();
        this.searcher = new Searcher();
    }

    /**
     * Measures execution time (in nanoseconds) of a sorting algorithm.
     * @param arr  the input array (will be cloned, so the original is preserved)
     * @param type "basic" for Insertion Sort, "advanced" for Merge Sort
     * @return elapsed time in nanoseconds
     */
    public long measureSortTime(int[] arr, String type) {
        int[] copy = arr.clone(); // do not mutate the caller's array

        long start = System.nanoTime();
        if ("basic".equalsIgnoreCase(type)) {
            sorter.basicSort(copy);
        } else if ("advanced".equalsIgnoreCase(type)) {
            sorter.advancedSort(copy);
        } else {
            throw new IllegalArgumentException("Unknown sort type: " + type);
        }
        long end = System.nanoTime();

        return end - start;
    }

    /**
     * Measures execution time (in nanoseconds) of binary search on a sorted array.
     */
    public long measureSearchTime(int[] arr, int target) {
        long start = System.nanoTime();
        searcher.search(arr, target);
        long end = System.nanoTime();
        return end - start;
    }

    /**
     * Runs the full benchmarking suite across multiple sizes and input types.
     */
    public void runAllExperiments() {
        int[] sizes = {10, 100, 1000, 10000};

        System.out.println("============================================================");
        System.out.println("           SORTING & SEARCHING PERFORMANCE REPORT           ");
        System.out.println("============================================================\n");

        // --- Sorting experiments ---
        System.out.printf("%-10s %-10s %-20s %-20s%n",
                "Size", "Input", "Insertion (ns)", "Merge (ns)");
        System.out.println("------------------------------------------------------------");

        for (int size : sizes) {
            int[] randomArr = sorter.generateRandomArray(size);
            int[] sortedArr = sorter.generateSortedArray(size);

            long insertionRandom = measureSortTime(randomArr, "basic");
            long mergeRandom     = measureSortTime(randomArr, "advanced");
            long insertionSorted = measureSortTime(sortedArr, "basic");
            long mergeSorted     = measureSortTime(sortedArr, "advanced");

            System.out.printf("%-10d %-10s %-20d %-20d%n",
                    size, "random", insertionRandom, mergeRandom);
            System.out.printf("%-10d %-10s %-20d %-20d%n",
                    size, "sorted", insertionSorted, mergeSorted);
        }

        // --- Searching experiments ---
        System.out.println("\n------------------------------------------------------------");
        System.out.println("           BINARY SEARCH PERFORMANCE                        ");
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-10s %-15s %-15s%n", "Size", "Target", "Time (ns)");
        System.out.println("------------------------------------------------------------");

        for (int size : sizes) {
            int[] sortedArr = sorter.generateSortedArray(size);

            // Search for an element that exists (middle)
            int targetMiddle = size / 2;
            long timeMiddle = measureSearchTime(sortedArr, targetMiddle);

            // Search for an element that does not exist
            int targetMissing = -1;
            long timeMissing = measureSearchTime(sortedArr, targetMissing);

            System.out.printf("%-10d %-15s %-15d%n", size, "middle (found)", timeMiddle);
            System.out.printf("%-10d %-15s %-15d%n", size, "missing", timeMissing);
        }

        System.out.println("\n============================================================");
        System.out.println("Experiment complete.");
        System.out.println("============================================================");
    }
}
