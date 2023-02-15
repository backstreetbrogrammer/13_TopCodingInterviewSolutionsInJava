package com.backstreetbrogrammer.Q2_RandomSampling;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomSamplingTest {

    private final List<Integer> data = Stream.iterate(1, n -> n + 1)
                                             .limit(10L)
                                             .collect(Collectors.toList());
    private final int sampleSize = 4;

    private final RandomSampling sampling = new RandomSampling(data, sampleSize);

    @BeforeEach
    void setUp() {
        System.out.println("Original list: " + data);
        System.out.printf("----- Samples of size %d: %n", sampleSize);
    }

    @Test
    @DisplayName("Random sampling using Collections.shuffle method")
    void testRandomSamplingUsingCollectionsShuffle() {
        for (int i = 0; i < 5; i++) {
            sampling.randomSamplingUsingShuffle(null)
                    .forEach(j -> System.out.printf("%d, ", j));
            System.out.println();
        }
    }

    @Test
    @DisplayName("Random sampling using Collections.shuffle method with ThreadLocalRandom")
    void testRandomSamplingUsingCollectionsShuffleWithGivenRandom() {
        for (int i = 0; i < 5; i++) {
            sampling.randomSamplingUsingShuffle(ThreadLocalRandom.current())
                    .forEach(j -> System.out.printf("%d, ", j));
            System.out.println();
        }
    }

    @Test
    @DisplayName("Random sampling using Collections.swap method")
    void testRandomSamplingUsingCollectionsSwap() {
        for (int i = 0; i < 5; i++) {
            sampling.randomSamplingUsingSwap()
                    .forEach(j -> System.out.printf("%d, ", j));
            System.out.println();
        }
    }

    @Test
    @DisplayName("Test Random online sampling")
    void testOnlineSampling() {
        
    }
}
