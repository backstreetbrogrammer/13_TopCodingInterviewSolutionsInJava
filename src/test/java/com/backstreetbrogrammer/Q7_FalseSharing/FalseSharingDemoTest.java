package com.backstreetbrogrammer.Q7_FalseSharing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;

public class FalseSharingDemoTest {

    @Test
    @DisplayName("Demonstrate False Sharing")
    void demonstrateFalseSharing() throws InterruptedException {
        final var object1 = new FalseSharingDemo();
        final var object2 = object1;

        final CountDownLatch latch = new CountDownLatch(2);
        final var thread1 = falseSharingThreadFactory(object1, true, latch);
        final var thread2 = falseSharingThreadFactory(object2, false, latch);

        thread1.start();
        thread2.start();

        latch.await();
    }

    private Thread falseSharingThreadFactory(final FalseSharingDemo object,
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
