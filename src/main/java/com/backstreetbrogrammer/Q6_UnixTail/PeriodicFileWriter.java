package com.backstreetbrogrammer.Q6_UnixTail;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.atomic.AtomicInteger;

public class PeriodicFileWriter implements Runnable {

    private final Path filePath;
    private final AtomicInteger counter = new AtomicInteger();

    public PeriodicFileWriter(final Path filePath) {
        this.filePath = filePath;
        try {
            Files.write(filePath, String.format("This is the header line%n").getBytes(StandardCharsets.UTF_8));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        final String str = String.format("This is the %d line.%n", counter.addAndGet(1));
        final byte[] strToBytes = str.getBytes(StandardCharsets.UTF_8);
        try {
            Files.write(filePath, strToBytes, StandardOpenOption.APPEND);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
