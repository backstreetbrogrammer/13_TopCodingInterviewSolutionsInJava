package com.backstreetbrogrammer.Q8_ReadWriteLock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JReadWriteLockTest {

    private int counter = 0;

    private void incrementCounter(final JReadWriteLockI readWriteLock) throws InterruptedException {
        readWriteLock.lockWrite();
        try {
            counter++;
        } finally {
            readWriteLock.unlockWrite();
        }
    }

    private int getCounter(final JReadWriteLockI readWriteLock) throws InterruptedException {
        readWriteLock.lockRead();
        try {
            return counter;
        } finally {
            readWriteLock.unlockRead();
        }
    }

    @ParameterizedTest
    @MethodSource("getReadWriteLocks")
    @DisplayName("Test ReadWriteLock implementations")
    void testReadWriteLock(final JReadWriteLockI readWriteLock) throws InterruptedException {
        final var reader1 = getReaderThread("Reader1", readWriteLock);
        final var reader2 = getReaderThread("Reader2", readWriteLock);
        final var reader3 = getReaderThread("Reader3", readWriteLock);
        final var reader4 = getReaderThread("Reader4", readWriteLock);
        final var reader5 = getReaderThread("Reader5", readWriteLock);

        final var writer1 = getWriterThread("Writer1", readWriteLock);
        final var writer2 = getWriterThread("Writer2", readWriteLock);

        reader1.start();
        writer1.start();
        reader2.start();
        reader3.start();
        writer2.start();
        reader4.start();

        TimeUnit.SECONDS.sleep(1L);

        reader5.start();
        assertEquals(2, counter);

        System.out.println("--------------------\n");
    }

    private static Stream<Arguments> getReadWriteLocks() {
        return Stream.of(
                Arguments.of(new JReadWriteLock()),
                Arguments.of(new JReadWriteLockReadReentrant()),
                Arguments.of(new JReadWriteLockWriteReentrant()),
                Arguments.of(new JReadWriteLockReadToWriteReentrant())
                        );
    }

    private Thread getReaderThread(final String name, final JReadWriteLockI readWriteLock) {
        return new Thread(() -> {
            try {
                final int counter = getCounter(readWriteLock);
                System.out.printf("Time:[%s], Reader Thread: [%s], Counter: [%d]%n", LocalDateTime.now(),
                                  Thread.currentThread().getName(), counter);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }, name);
    }

    private Thread getWriterThread(final String name, final JReadWriteLockI readWriteLock) {
        return new Thread(() -> {
            try {
                incrementCounter(readWriteLock);
                System.out.printf("Time:[%s], Writer Thread: [%s]%n", LocalDateTime.now(),
                                  Thread.currentThread().getName());
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }, name);
    }
}
