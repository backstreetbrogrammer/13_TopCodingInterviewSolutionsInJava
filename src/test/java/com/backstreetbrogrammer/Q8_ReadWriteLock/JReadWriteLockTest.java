package com.backstreetbrogrammer.Q8_ReadWriteLock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JReadWriteLockTest {

    private JReadWriteLockI readWriteLock;

    private int counter = 0;

    private void incrementCounter() throws InterruptedException {
        readWriteLock.lockWrite();
        try {
            counter++;
        } finally {
            readWriteLock.unlockWrite();
        }
    }

    private int getCounter() throws InterruptedException {
        readWriteLock.lockRead();
        try {
            return counter;
        } finally {
            readWriteLock.unlockRead();
        }
    }

    @Test
    @DisplayName("Test Basic ReadWriteLock implementation")
    void testBasicReadWriteLock() throws InterruptedException {
        readWriteLock = new JReadWriteLock();

        final var reader1 = getReaderThread("Reader1");
        final var reader2 = getReaderThread("Reader2");
        final var reader3 = getReaderThread("Reader3");

        final var writer1 = getWriterThread("Writer1");
        final var writer2 = getWriterThread("Writer2");

        reader1.start();
        writer1.start();
        reader2.start();
        writer2.start();

        TimeUnit.SECONDS.sleep(1L);

        reader3.start();
        assertEquals(2, counter);
    }

    private Thread getReaderThread(final String name) {
        return new Thread(() -> {
            try {
                final int counter = getCounter();
                System.out.printf("Time:[%s], Reader Thread: [%s], Counter: [%d]%n", LocalDateTime.now(),
                                  Thread.currentThread().getName(), counter);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }, name);
    }

    private Thread getWriterThread(final String name) {
        return new Thread(() -> {
            try {
                incrementCounter();
                System.out.printf("Time:[%s], Writer Thread: [%s]%n", LocalDateTime.now(),
                                  Thread.currentThread().getName());
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }, name);
    }
}
