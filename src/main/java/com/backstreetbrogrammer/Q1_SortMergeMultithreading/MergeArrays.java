package com.backstreetbrogrammer.Q1_SortMergeMultithreading;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MergeArrays implements Runnable {

    private final int[] evenArr;
    private final int[] oddArr;
    private int[] combinedArray;

    public MergeArrays(final int[] evenArr, final int[] oddArr) {
        this.evenArr = evenArr;
        this.oddArr = oddArr;
    }

    @Override
    public void run() {
        combinedArray = IntStream.concat(Arrays.stream(evenArr), Arrays.stream(oddArr)).toArray();
    }

    public int[] getCombinedArray() {
        return combinedArray;
    }
}
