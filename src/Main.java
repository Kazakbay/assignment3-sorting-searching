/**
 * Main entry point.
 * Demonstrates Sorter, Searcher, and runs the full Experiment.
 */
public class Main {
    public static void main(String[] args) {
        Sorter sorter = new Sorter();
        Searcher searcher = new Searcher();
        Experiment experiment = new Experiment();

        // ---------- Demo: small array ----------
        System.out.println("=== DEMO ON A SMALL ARRAY (size = 10) ===\n");

        int[] demo = sorter.generateRandomArray(10);
        System.out.print("Original array:        ");
        sorter.printArray(demo);

        int[] forBasic = demo.clone();
        sorter.basicSort(forBasic);
        System.out.print("After Insertion Sort:  ");
        sorter.printArray(forBasic);

        int[] forAdvanced = demo.clone();
        sorter.advancedSort(forAdvanced);
        System.out.print("After Merge Sort:      ");
        sorter.printArray(forAdvanced);

        // ---------- Demo: search ----------
        int target = forAdvanced[5]; // pick an element that exists
        int index = searcher.search(forAdvanced, target);
        System.out.println("\nBinary Search for " + target + " -> index " + index);

        int missing = -999;
        int notFound = searcher.search(forAdvanced, missing);
        System.out.println("Binary Search for " + missing + " -> index " + notFound + " (not found)\n");

        // ---------- Run full benchmark ----------
        experiment.runAllExperiments();
    }
}
