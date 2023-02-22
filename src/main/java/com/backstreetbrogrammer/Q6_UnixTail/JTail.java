package com.backstreetbrogrammer.Q6_UnixTail;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.*;

public class JTail {

    private final String fileName;
    private final int noOfLines;

    private Path watchDirectory;

    public JTail(final String fileName, final int noOfLines) {
        if (fileName == null || fileName.isEmpty() || noOfLines <= 0) {
            throw new IllegalStateException(String.format("INVALID [filename:%s], [noOfLines:%d]", fileName,
                                                          noOfLines));
        }
        this.fileName = fileName;
        this.noOfLines = noOfLines;
    }

    public void setWatchDirectory(final Path watchDirectory) {
        this.watchDirectory = watchDirectory;
    }

    public void tailOnline() throws IOException, InterruptedException {
        if (watchDirectory == null) return;
        final var watchService = FileSystems.getDefault().newWatchService();
        watchDirectory.register(
                watchService,
                StandardWatchEventKinds.ENTRY_MODIFY);
        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (final WatchEvent<?> event : key.pollEvents()) {
                System.out.printf("%s", tail());
            }
            key.reset();
        }
    }

    public String tail() throws IOException {
        try (final var filePtr = new RandomAccessFile(fileName, "r")) {
            final long fileSize = filePtr.length();
            filePtr.seek(fileSize - 1);

            long newLineCount = 0L;
            final var lastNLines = new StringBuilder();

            // read file in reverse and look for '\n'
            for (long i = fileSize - 1; i != -1; i--) {
                filePtr.seek(i);
                final int readByte = filePtr.readByte();
                final char c = (char) readByte;
                if ((c == '\n') || (System.lineSeparator()
                                          .equals(String.valueOf(c)))) {
                    ++newLineCount;
                    if (newLineCount > noOfLines) {
                        break;
                    }
                }
                lastNLines.append(c);
            }

            lastNLines.reverse();
            return lastNLines.toString();
        }
    }
}
