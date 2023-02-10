package com.backstreetbrogrammer.Q1_SortMergeMultithreading;

import java.util.Arrays;

public class SortOddsOrEvens implements Runnable {

    private final int[] arr;
    private final boolean isOdd;
    private int[] sortedArray;

    public SortOddsOrEvens(final int[] arr, final boolean isOdd) {
        this.arr = arr;
        this.isOdd = isOdd;
    }

    @Override
    public void run() {
        sortedArray = Arrays.stream(arr)
                            .filter(isOdd ?
                                            (i -> i % 2 != 0)    // filter only odd numbers
                                            : (i -> i % 2 == 0)) // filter only even numbers
                            .sorted() // sort in ascending order
                            .toArray(); // convert IntStream to int[]
    }

    public int[] getSortedArray() {
        return sortedArray;
    }
}
