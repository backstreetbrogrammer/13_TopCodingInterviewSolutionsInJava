package com.backstreetbrogrammer.Q7_FalseSharing;

import com.backstreetbrogrammer.Q7_FalseSharing.FalseSharingDemo.VolatileLongPadded;
import com.backstreetbrogrammer.Q7_FalseSharing.FalseSharingDemo.VolatileLongUnPadded;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

public class FalseSharingDemoTest {

    private VolatileLongPadded[] paddedLongs;
    private VolatileLongUnPadded[] unPaddedLongs;

    private final int numOfThreads = 4;
    private final long iterations = 100_000_000L;

    @BeforeEach
    void setUp() {
        paddedLongs = new VolatileLongPadded[]{
                new VolatileLongPadded(),
                new VolatileLongPadded(),
                new VolatileLongPadded(),
                new VolatileLongPadded()
        };

        unPaddedLongs = new VolatileLongUnPadded[]{
                new VolatileLongUnPadded(),
                new VolatileLongUnPadded(),
                new VolatileLongUnPadded(),
                new VolatileLongUnPadded()
        };
    }

    @Test
    @DisplayName("Demonstrate False Sharing")
    void demonstrateFalseSharing() throws InterruptedException {
        for (int i = 1; i <= numOfThreads; i++) {
            final Thread[] threads = new Thread[i];
            for (int j = 0; j < threads.length; j++) {
                threads[j] = new Thread(createUnpaddedRunnable(j));
            }
            final Instant start = Instant.now();
            for (final Thread t : threads) {
                t.start();
            }
            for (final Thread t : threads) {
                t.join();
            }
            final long timeElapsed = (Duration.between(start, Instant.now()).toMillis());
            System.out.printf("[UNPADDED] No of Threads=[%d], total time taken: %d ms%n%n", i, timeElapsed);
        }
        System.out.println("------------------------------");
    }

    @Test
    @DisplayName("Demonstrate fixing False Sharing by padding")
    void demonstrateFixingFalseSharingByPadding() throws InterruptedException {
        for (int i = 1; i <= numOfThreads; i++) {
            final Thread[] threads = new Thread[i];
            for (int j = 0; j < threads.length; j++) {
                threads[j] = new Thread(createPaddedRunnable(j));
            }
            final Instant start = Instant.now();
            for (final Thread t : threads) {
                t.start();
            }
            for (final Thread t : threads) {
                t.join();
            }
            final long timeElapsed = (Duration.between(start, Instant.now()).toMillis());
            System.out.printf("[PADDED] No of Threads=[%d], total time taken: %d ms%n%n", i, timeElapsed);
        }
        System.out.println("------------------------------");
    }

    private Runnable createUnpaddedRunnable(final int k) {
        return () -> {
            long i = iterations + 1;
            while (0 != --i) {
                unPaddedLongs[k].value = i;
            }
        };
    }

    private Runnable createPaddedRunnable(final int k) {
        return () -> {
            long i = iterations + 1;
            while (0 != --i) {
                paddedLongs[k].value = i;
            }
        };
    }

}
