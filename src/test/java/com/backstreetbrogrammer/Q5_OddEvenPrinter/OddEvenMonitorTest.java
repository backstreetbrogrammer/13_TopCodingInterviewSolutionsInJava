package com.backstreetbrogrammer.Q5_OddEvenPrinter;

import org.junit.jupiter.api.Test;

public class OddEvenMonitorTest {

    @Test
    void testOddEvenPrinter() throws InterruptedException {
        final var monitor = new OddEvenMonitor();
        final var t1 = new Thread(new OddEvenThread(monitor, true), "OddThread");
        final var t2 = new Thread(new OddEvenThread(monitor, false), "EvenThread");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
