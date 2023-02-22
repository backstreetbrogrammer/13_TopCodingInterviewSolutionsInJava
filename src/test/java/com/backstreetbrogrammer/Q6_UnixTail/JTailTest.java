package com.backstreetbrogrammer.Q6_UnixTail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.file.Path;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class JTailTest {

    @ParameterizedTest
    @MethodSource("getJTailObjects")
    @DisplayName("Test tail method for a given file")
    void testTailMethodForAGivenFile(final JTail jTail) throws IOException {
        System.out.println(jTail.tail());
        System.out.println("-----------------\n");
    }

    @Test
    @DisplayName("Test tail method for a running log file")
    void testTailMethodRealtimeForRunningLogFile() throws InterruptedException, IOException {
        final var path = Path.of("src", "test", "resources", "bb.txt");
        final var jTail = new JTail(path.toString(), 1);
        jTail.setWatchDirectory(Path.of("src", "test", "resources"));

        final var fileWriter = new PeriodicFileWriter(path);
        final var scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        scheduledThreadPoolExecutor.scheduleAtFixedRate(fileWriter, 500L, 500L, TimeUnit.MILLISECONDS);
        TimeUnit.SECONDS.sleep(2L);

        jTail.tailOnline();
    }

    @Test
    @DisplayName("Test writing to a file periodically")
    void testWritingToAFilePeriodically() throws InterruptedException {
        final var path = Path.of("src", "test", "resources", "bb.txt");
        final var fileWriter = new PeriodicFileWriter(path);
        final var scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        scheduledThreadPoolExecutor.scheduleAtFixedRate(fileWriter, 500L, 500L, TimeUnit.MILLISECONDS);

        TimeUnit.SECONDS.sleep(5L);
    }

    @Test
    @DisplayName("Test writing to a file using FileLock")
    void testWritingToAFileUsingFileLock() throws IOException, InterruptedException {
        final var fileName = "src\\test\\resources\\bbLocked.txt";
        final var counter = new AtomicInteger();
        final var stream = new RandomAccessFile(fileName, "rw");
        final var channel = stream.getChannel();

        for (int i = 0; i < 10; i++) {
            FileLock lock = null;
            try {
                lock = channel.tryLock();
            } catch (final OverlappingFileLockException | IOException e) {
                stream.close();
                channel.close();
            }
            stream.writeUTF(String.format("This is the %d line.%n", counter.addAndGet(1)));
            assert lock != null;
            lock.release();

            TimeUnit.SECONDS.sleep(1L);
        }

        stream.close();
        channel.close();
    }

    private static Stream<Arguments> getJTailObjects() {
        final String fileName = "src\\test\\resources\\amendments.txt";
        return Stream.of(
                Arguments.of(new JTail(fileName, 2)),
                Arguments.of(new JTail(fileName, 3)),
                Arguments.of(new JTail(fileName, 5)));
    }
}
