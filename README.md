# Assignment 3 — Sorting and Searching Algorithm Analysis

## A. Project Overview

This project implements and benchmarks three classical algorithms in Java to study their practical runtime behavior and compare it against their theoretical Big-O complexity.

Algorithms chosen:

| Category | Algorithm | Class |
|---|---|---|
| Basic sort | Insertion Sort | `Sorter.basicSort` |
| Advanced sort | Merge Sort | `Sorter.advancedSort` |
| Searching | Binary Search | `Searcher.search` |

The `Experiment` class runs each algorithm on arrays of different sizes (10, 100, 1000, 10000) and different shapes (random and already-sorted), measuring execution time with `System.nanoTime()`.

## B. Algorithm Descriptions

### 1. Insertion Sort (basic)

Builds the sorted array one element at a time. For each element it scans the already-sorted left part of the array from right to left, shifting larger values one position to the right until it finds the correct place to insert the current element.

- Best case: `O(n)` — array already sorted, inner loop never executes
- Average case: `O(n²)`
- Worst case: `O(n²)` — reverse-sorted input
- Space: `O(1)` — sorts in place

### 2. Merge Sort (advanced)

A divide-and-conquer algorithm. Recursively splits the array in half until each piece has one element, then merges the pieces back together in sorted order.

- Best / Average / Worst case: `O(n log n)` — guaranteed regardless of input
- Space: `O(n)` — needs auxiliary arrays during the merge step

### 3. Binary Search

Operates on a sorted array. At each step it compares the target with the middle element and discards half of the search range.

- Best case: `O(1)` — target is the middle element on the first comparison
- Average / Worst case: `O(log n)`
- Space: `O(1)` — iterative implementation

## C. Experimental Results

All times measured with `System.nanoTime()` on my machine.

### Sorting (nanoseconds)

| Size  | Input  | Insertion Sort | Merge Sort |
|-------|--------|----------------|------------|
| 10    | random | 10 600         | 15 400     |
| 10    | sorted | 900            | 5 200      |
| 100   | random | 82 600         | 118 700    |
| 100   | sorted | 5 700          | 94 800     |
| 1000  | random | 3 789 300      | 245 300    |
| 1000  | sorted | 11 100         | 150 300    |
| 10000 | random | 25 109 300     | 1 951 200  |
| 10000 | sorted | 394 100        | 604 200    |

### Binary Search (nanoseconds)

| Size  | Target          | Time  |
|-------|-----------------|-------|
| 10    | middle (found)  | 3 700 |
| 10    | missing         | 600   |
| 100   | middle (found)  | 1 800 |
| 100   | missing         | 500   |
| 1000  | middle (found)  | 1 700 |
| 1000  | missing         | 700   |
| 10000 | middle (found)  | 2 500 |
| 10000 | missing         | 900   |

### Analysis Questions

**Which sorting algorithm performed faster? Why?**
Merge Sort wins clearly on large random inputs because it scales as `O(n log n)` while Insertion Sort scales as `O(n²)`. At n = 1000 random Merge Sort is already ~15× faster (245k ns vs 3.79M ns), and at n = 10000 it is ~13× faster (1.95M ns vs 25.1M ns). On very small arrays (n = 10, 100) Insertion Sort is actually faster because it has tiny constant factors and no recursion or extra memory allocations — Merge Sort's overhead does not pay off until n gets large enough.

**How does performance change with input size?**
Insertion Sort grows quadratically. Going from n = 1000 to n = 10000 (10× more data) increased its time from 3.79M to 25.1M ns — about 6.6×, which is in the right neighborhood for `n²` once you account for cache and JIT effects. Merge Sort grew from 245k to 1.95M ns over the same range — about 8×, very close to the theoretical `n log n` factor of ~13.3.

**How does sorted vs unsorted data affect performance?**
Insertion Sort is dramatically faster on sorted data — it hits its `O(n)` best case because the inner `while` loop exits immediately on every iteration. At n = 10000, sorted input took 394k ns vs 25.1M ns for random — a ~64× speedup. Merge Sort, by contrast, only got about 3× faster on sorted vs random data at n = 10000, because it always performs the same number of splits and merges regardless of input order.

**Do the results match the expected Big-O complexity?**
Yes. Insertion Sort on random data shows the quadratic blow-up predicted by `O(n²)`. Insertion Sort on sorted data stays roughly linear, matching the `O(n)` best case. Merge Sort grows close to `n log n` on both random and sorted input. The small numbers at n = 10 and n = 100 are dominated by JIT warm-up and constant overhead, which is why Insertion Sort looks faster there even though its asymptotic class is worse.

**Which searching algorithm is more efficient? Why?**
Binary Search is more efficient than Linear Search by a wide margin. For an array of 10 000 elements, Linear Search would need up to 10 000 comparisons, while Binary Search needs at most about 14 (`log₂ 10 000 ≈ 13.3`). My measurements confirm this — search time stayed in the hundreds-to-low-thousands of nanoseconds even at n = 10000. The trade-off is that Binary Search requires the array to be sorted first.

**Why does Binary Search require a sorted array?**
The algorithm decides which half of the array to discard based on whether the middle element is smaller or larger than the target. That decision is only valid if the array is sorted — otherwise, knowing that the middle element is, say, smaller than the target tells you nothing about whether the target is to the left or to the right.

## D. Screenshots

Screenshots of program runs are in `docs/screenshots/`:

- `docs/screenshots/demo_run.png` — output of the small-array demo (sorted arrays + search result)
- `docs/screenshots/full_benchmark.png` — full output of `runAllExperiments()`

## E. Reflection

Working on this assignment made the difference between Big-O classes very tangible. On paper, `O(n²)` and `O(n log n)` are just symbols, but watching Insertion Sort take 25 milliseconds on 10 000 random elements while Merge Sort finished the same job in under 2 milliseconds made the gap concrete. What surprised me most was how well Insertion Sort competes on small or already-sorted inputs — its constant factors are so small that for n ≤ 100 it actually beats Merge Sort, and on a fully sorted array of 10 000 elements it was only ~1.5× slower than Merge Sort. That is exactly why real libraries like Java's `Arrays.sort` switch to insertion sort for small subarrays inside a more complex algorithm.

The biggest challenge was making the measurements meaningful. A single `nanoTime()` reading can be noisy because of JIT warm-up, garbage collection, and OS scheduling, so the first run of any algorithm tends to look slower than later runs. I learned to clone arrays before timing so each algorithm sees the same input, and to interpret single-run numbers with caution. Implementation-wise, getting the index arithmetic right in Merge Sort's `merge` step was the most error-prone part — off-by-one errors in the bounds are easy to make and only show up when the output is wrong.

## Project Structure

```
assignment3-sorting-searching/
├── src/
│   ├── Sorter.java
│   ├── Searcher.java
│   ├── Experiment.java
│   └── Main.java
├── docs/
│   └── screenshots/
├── README.md
└── .gitignore
```

