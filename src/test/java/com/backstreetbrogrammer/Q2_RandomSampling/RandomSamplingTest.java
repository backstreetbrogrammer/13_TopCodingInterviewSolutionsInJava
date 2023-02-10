package com.backstreetbrogrammer.Q2_RandomSampling;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomSamplingTest {

    @Test
    void testRandomSampling() {
        final var data = Stream.iterate(1, n -> n + 1)
                               .limit(10L)
                               .collect(Collectors.toList());
        final var sampleSize = 4;
        System.out.println("Original list: " + data);
        System.out.printf("----- Samples of size %d: %n", sampleSize);
        for (int i = 0; i < 5; i++) {
            RandomSampling.randomSampling(data, sampleSize)
                          .forEach(j -> System.out.printf("%d, ", j));
            System.out.println();
        }
    }
}
