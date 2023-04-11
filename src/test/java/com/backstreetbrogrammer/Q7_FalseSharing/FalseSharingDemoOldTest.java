package com.backstreetbrogrammer.Q7_FalseSharing;

import com.backstreetbrogrammer.Q7_FalseSharing.FalseSharingDemoOld.PaddedAtomicLong;
import com.backstreetbrogrammer.Q7_FalseSharing.FalseSharingDemoOld.VolatileLong;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;

public class FalseSharingDemoOldTest {

    @Test
    @DisplayName("Demonstrate False Sharing")
    void demonstrateFalseSharing() throws InterruptedException {
        final var object1 = new FalseSharingDemoOld();
        final var object2 = object1;
        // final var object2 = new FalseSharingDemo();

        final CountDownLatch latch = new CountDownLatch(2);

        falseSharingThreadFactory(object1, true, latch).start();
        falseSharingThreadFactory(object2, false, latch).start();

        latch.await();
    }

    @Test
    @DisplayName("Demonstrate False Sharing using VolatileLong")
    void demonstrateFalseSharingUsingVolatileLong() throws InterruptedException {
        final int noOfThreads = 4;
        final var volatileLongs = new VolatileLong[]{
                new VolatileLong(),
                new VolatileLong(),
                new VolatileLong(),
                new VolatileLong()
        };

        final CountDownLatch latch = new CountDownLatch(noOfThreads);

        for (int i = 0; i < noOfThreads; i++) {
            falseSharingVolatileLongsThreadFactory(volatileLongs, latch).start();
        }

        latch.await();
    }

    @Test
    @DisplayName("Demonstrate False Sharing using PaddedAtomicLong")
    void demonstrateFalseSharingUsingPaddedAtomicLong() throws InterruptedException {
        final int noOfThreads = 4;
        final var paddedAtomicLongs = new PaddedAtomicLong[]{
                new PaddedAtomicLong(),
                new PaddedAtomicLong(),
                new PaddedAtomicLong(),
                new PaddedAtomicLong()
        };

        final CountDownLatch latch = new CountDownLatch(noOfThreads);

        for (int i = 0; i < noOfThreads; i++) {
            falseSharingPaddedAtomicLongsThreadFactory(paddedAtomicLongs, latch).start();
        }

        latch.await();
    }

    private Thread falseSharingPaddedAtomicLongsThreadFactory(final PaddedAtomicLong[] paddedAtomicLongs,
                                                              final CountDownLatch latch) {
        return new Thread(() -> {
            final Instant start = Instant.now();
            for (long i = 0L; i < 1_000_000_000L; i++) {
                for (final PaddedAtomicLong paddedAtomicLong :
                        paddedAtomicLongs) {
                    paddedAtomicLong.set(i);
                }
            }
            final long timeElapsed = (Duration.between(start, Instant.now()).toMillis());
            System.out.printf("total time taken: %d ms%n%n", timeElapsed);
            latch.countDown();
        });
    }

    private Thread falseSharingVolatileLongsThreadFactory(final VolatileLong[] volatileLongs,
                                                          final CountDownLatch latch) {
        return new Thread(() -> {
            final Instant start = Instant.now();
            for (long i = 0L; i < 1_000_000_000L; i++) {
                for (final VolatileLong volatileLong :
                        volatileLongs) {
                    volatileLong.value = i;
                }
            }
            final long timeElapsed = (Duration.between(start, Instant.now()).toMillis());
            System.out.printf("total time taken: %d ms%n%n", timeElapsed);
            latch.countDown();
        });
    }

    private Thread falseSharingThreadFactory(final FalseSharingDemoOld object,
                                             final boolean isVarA,
                                             final CountDownLatch latch) {
        return new Thread(() -> {
            final Instant start = Instant.now();
            for (long i = 0L; i < 1_000_000_000L; i++) {
                if (isVarA) {
                    long a = object.getA();
                    object.setA(++a);
                } else {
                    long b = object.getB();
                    object.setB(++b);
                }
            }
            final long timeElapsed = (Duration.between(start, Instant.now()).toMillis());
            System.out.printf("total time taken: %d ms%n%n", timeElapsed);
            latch.countDown();
        });
    }
}
