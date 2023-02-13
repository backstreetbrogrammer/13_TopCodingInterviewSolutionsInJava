package com.backstreetbrogrammer.Q1_SortMergeMultithreading;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

/**
 * Given an array of N size with random integers. Write a multithreaded program that performs the following operations on
 * this array:
 * <p>
 * Thread 1 sorts the even numbers
 * <p>
 * Thread 2 sorts the odd numbers
 * <p>
 * Thread 3 merge the results with even numbers in the top part of the array
 * <p>
 * Example:
 * <p>
 * Assume we have an array [2, 29, 3, 0, 11, 8, 32, 94, 9, 1, 7] of 11 elements.
 * <p>
 * Thread 1 results [0, 2, 8, 32, 94]
 * <p>
 * Thread 2 results [1, 3, 7, 9, 11, 29]
 * <p>
 * Thread 3 results [0, 2, 8, 32, 94, 1, 3, 7, 9, 11, 29]
 */
public class SortEvenAndOddNumbersAndMergeConcurrently {

    private int[] array;

    public void setArray(final int[] array) {
        this.array = array;
    }

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

            final var sortAndMerge = new SortEvenAndOddNumbersAndMergeConcurrently();
            sortAndMerge.setArray(arr);

            // create and start the threads
            sortAndMerge.multiThreadingTest();
            sortAndMerge.multiThreadingTestUsingCompletableFutures();
        }
    }

    public void multiThreadingTest() throws InterruptedException {
        if (array == null || array.length == 0) return;

        // create Runnable objects
        final var sortEvens = new SortOddsOrEvens(array, false);
        final var sortOdds = new SortOddsOrEvens(array, true);

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
        System.out.println("----------------------------\n");
    }

    public void multiThreadingTestUsingCompletableFutures() {
        if (array == null || array.length == 0) return;

        final CompletableFuture<int[]> futureThread1
                = CompletableFuture.supplyAsync(() -> Arrays.stream(array)
                                                            .filter(i -> i % 2 == 0) // evens
                                                            .sorted()
                                                            .toArray())
                                   .thenCombine(CompletableFuture.supplyAsync(
                                                        () -> Arrays.stream(array)
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

    private void print(final int[] array) {
        for (final int num : array) {
            System.out.printf("%d, ", num);
        }
        System.out.println();
    }

}
