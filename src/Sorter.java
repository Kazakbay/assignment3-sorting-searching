import java.util.Random;

/**
 * Sorter handles sorting operations.
 * Basic algorithm: Insertion Sort
 * Advanced algorithm: Merge Sort
 */
public class Sorter {

    // ---------- BASIC SORT: INSERTION SORT ----------
    // Time complexity: O(n^2) worst/average, O(n) best (already sorted)
    // Space complexity: O(1)
    public void basicSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            // Shift elements that are greater than key one position to the right
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    // ---------- ADVANCED SORT: MERGE SORT ----------
    // Time complexity: O(n log n) in all cases (best, average, worst)
    // Space complexity: O(n) — needs temporary arrays for merging
    public void advancedSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        mergeSort(arr, 0, arr.length - 1);
    }

    // Recursively splits the array in halves and merges them back sorted
    private void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2; // avoids integer overflow
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    // Merges two sorted subarrays arr[left..mid] and arr[mid+1..right]
    private void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];

        for (int i = 0; i < n1; i++) leftArr[i] = arr[left + i];
        for (int j = 0; j < n2; j++) rightArr[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }
        while (i < n1) arr[k++] = leftArr[i++];
        while (j < n2) arr[k++] = rightArr[j++];
    }

    // ---------- HELPERS ----------
    public void printArray(int[] arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(", ");
        }
        sb.append("]");
        System.out.println(sb.toString());
    }

    public int[] generateRandomArray(int size) {
        Random rand = new Random(42); // fixed seed for reproducible experiments
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(size * 10);
        }
        return arr;
    }

    // Generates an already-sorted array of the given size (used to test best-case behavior)
    public int[] generateSortedArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i;
        }
        return arr;
    }
}
