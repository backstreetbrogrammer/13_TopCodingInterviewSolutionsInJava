package com.backstreetbrogrammer.Q1_SortMergeMultithreading;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class SortEvenAndOddNumbersAndMergeConcurrentlyTest {

    private final SortEvenAndOddNumbersAndMergeConcurrently sortAndMerge =
            new SortEvenAndOddNumbersAndMergeConcurrently();

    @ParameterizedTest
    @MethodSource("getInputArrays")
    @DisplayName("Sort and Merge using Thread objects")
    void sortAndMergeUsingThreads(final int[] arr) throws InterruptedException {
        sortAndMerge.setArray(arr);
        sortAndMerge.multiThreadingTest();
    }

    @ParameterizedTest
    @MethodSource("getInputArrays")
    @DisplayName("Sort and Merge using CompletableFuture")
    void sortAndMergeUsingCompletableFuture(final int[] arr) {
        sortAndMerge.setArray(arr);
        sortAndMerge.multiThreadingTestUsingCompletableFuture();
    }

    private static Stream<Arguments> getInputArrays() {
        return Stream.of(
                Arguments.of(new int[]{2, 29, 3, 0, 11, 8, 32, 94, 9, 1, 7}),
                Arguments.of(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}),
                Arguments.of(new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1}));
    }
}
