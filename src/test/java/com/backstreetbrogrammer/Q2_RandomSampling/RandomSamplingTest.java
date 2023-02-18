package com.backstreetbrogrammer.Q2_RandomSampling;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.backstreetbrogrammer.PrintUtils.printIntList;

public class RandomSamplingTest {

    private final List<Integer> data = Stream.iterate(1, n -> n + 1)
                                             .limit(10L)
                                             .collect(Collectors.toList());
    private final int sampleSize = 4;

    private final RandomSampling sampling = new RandomSampling(data, sampleSize);

    @BeforeEach
    void setUp() {
        System.out.println("Original list: ");
        printIntList(data);
        System.out.printf("----- Samples of size %d: %n", sampleSize);
    }

    @Test
    @DisplayName("Random sampling using Collections.shuffle method")
    void testRandomSamplingUsingCollectionsShuffle() {
        for (int i = 0; i < 5; i++) {
            printIntList(sampling.randomSamplingUsingShuffle(null));
        }
    }

    @Test
    @DisplayName("Random sampling using Collections.shuffle method with ThreadLocalRandom")
    void testRandomSamplingUsingCollectionsShuffleWithGivenRandom() {
        for (int i = 0; i < 5; i++) {
            printIntList(sampling.randomSamplingUsingShuffle(ThreadLocalRandom.current()));
        }
    }

    @Test
    @DisplayName("Random sampling using Collections.swap method")
    void testRandomSamplingUsingCollectionsSwap() {
        for (int i = 0; i < 5; i++) {
            printIntList(sampling.randomSamplingUsingSwap());
        }
    }

    @Test
    @DisplayName("Test Random online sampling")
    void testOnlineSampling() throws IOException, ClassNotFoundException {
        final var onlineSamplingPath = Path.of("src", "test", "resources", "onlineSampling.txt");
        try (final OutputStream os = Files.newOutputStream(onlineSamplingPath);
             final ObjectOutputStream oos = new ObjectOutputStream(os)) {
            data.forEach(i -> {
                try {
                    oos.writeObject(Integer.valueOf(i));
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            });
        }

        try (final InputStream is = Files.newInputStream(onlineSamplingPath)) {
            printIntList(sampling.randomOnlineSampling(is, 4));
        }
    }

}
