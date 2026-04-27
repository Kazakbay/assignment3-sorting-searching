/**
 * Searcher handles searching operations.
 * Algorithm: Binary Search
 * Requires the input array to be sorted in ascending order.
 */
public class Searcher {

    // ---------- BINARY SEARCH ----------
    // Time complexity: O(log n)
    // Space complexity: O(1) — iterative implementation
    // Returns the index of target if found, -1 otherwise.
    public int search(int[] arr, int target) {
        if (arr == null || arr.length == 0) return -1;

        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2; // avoids integer overflow

            if (arr[mid] == target) {
                return mid; // found
            } else if (arr[mid] < target) {
                left = mid + 1; // search right half
            } else {
                right = mid - 1; // search left half
            }
        }
        return -1; // not found
    }
}
