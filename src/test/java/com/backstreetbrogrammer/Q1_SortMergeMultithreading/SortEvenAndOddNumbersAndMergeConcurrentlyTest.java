package com.backstreetbrogrammer.Q1_SortMergeMultithreading;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SortEvenAndOddNumbersAndMergeConcurrentlyTest {

    private final int[] arr = new int[]{2, 29, 3, 0, 11, 8, 32, 94, 9, 1, 7};
    private final SortEvenAndOddNumbersAndMergeConcurrently sortAndMerge =
            new SortEvenAndOddNumbersAndMergeConcurrently(arr);

    @Test
    @DisplayName("Sort and Merge using Thread objects")
    void sortAndMergeUsingThreads() throws InterruptedException {
        sortAndMerge.multiThreadingTest();
    }

    @Test
    @DisplayName("Sort and Merge using CompletableFutures")
    void sortAndMergeUsingCompletableFutures() {
        sortAndMerge.multiThreadingTestUsingCompletableFutures();
    }


}
