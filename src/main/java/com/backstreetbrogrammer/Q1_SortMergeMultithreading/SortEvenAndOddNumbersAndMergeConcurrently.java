package com.backstreetbrogrammer.Q1_SortMergeMultithreading;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

public class SortEvenAndOddNumbersAndMergeConcurrently {

    public static void main(final String[] args) throws InterruptedException {
        try (final var s = new Scanner(System.in)) {
            System.out.println("Enter the input array as comma separated.");
            final var inputArrayAsString = s.nextLine().split(",");
            if (inputArrayAsString.length == 0) {
                System.out.println("Input array is incorrect!! Try re-running the program");
                System.exit(1);
            }

            final var arr = new int[inputArrayAsString.length];
            for (int i = 0; i < inputArrayAsString.length; i++) {
                final var numStr = inputArrayAsString[i];
                if (numStr != null) {
                    try {
                        arr[i] = Integer.parseInt(numStr.trim());
                    } catch (final NumberFormatException nfe) {
                        throw new RuntimeException(nfe);
                    }
                }
            }

            // create and start the threads
            multiThreadingTest(arr);
            multiThreadingTestUsingCompletableFutures(arr);
        }
    }

    private static void multiThreadingTestUsingCompletableFutures(final int[] arr) {
        if (arr == null || arr.length == 0) return;

        final CompletableFuture<int[]> futureThread1
                = CompletableFuture.supplyAsync(() -> Arrays.stream(arr)
                                                            .filter(i -> i % 2 == 0) // evens
                                                            .sorted()
                                                            .toArray())
                                   .thenCombine(CompletableFuture.supplyAsync(
                                                        () -> Arrays.stream(arr)
                                                                    .filter(i -> i % 2 != 0) // odds
                                                                    .sorted()
                                                                    .toArray()),
                                                (sortedEvens, sortedOdds) ->
                                                        IntStream.concat(Arrays.stream(sortedEvens),
                                                                         Arrays.stream(sortedOdds)).toArray());
        try {
            print(futureThread1.get());
        } catch (final InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void multiThreadingTest(final int[] arr) throws InterruptedException {
        if (arr == null || arr.length == 0) return;

        // create Runnable objects
        final var sortEvens = new SortOddsOrEvens(arr, false);
        final var sortOdds = new SortOddsOrEvens(arr, true);

        // create Thread objects from above Runnable objects
        final var thread1 = new Thread(sortEvens);
        final var thread2 = new Thread(sortOdds);

        // run the threads
        thread1.start();
        thread2.start();

        // join the threads until it completes
        thread1.join();
        thread2.join();

        // get the sorted arrays
        final var sortedEvenArray = sortEvens.getSortedArray();
        final var sortedOddArray = sortOdds.getSortedArray();

        // merge the arrays
        final var mergeArrays = new MergeArrays(sortedEvenArray, sortedOddArray);
        final var thread3 = new Thread(mergeArrays);
        thread3.start();
        thread3.join();

        // get the combined array
        final var combinedArray = mergeArrays.getCombinedArray();

        // print all the 3 arrays
        System.out.println("Sorted Even array:");
        print(sortedEvenArray);

        System.out.println("Sorted Odd array:");
        print(sortedOddArray);

        System.out.println("Sorted Combined array with Evens first and then Odds:");
        print(combinedArray);
    }

    private static class SortOddsOrEvens implements Runnable {
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

    private static class MergeArrays implements Runnable {
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

    private static void print(final int[] array) {
        for (final int num : array) {
            System.out.printf("%d, ", num);
        }
        System.out.println();
    }

}
