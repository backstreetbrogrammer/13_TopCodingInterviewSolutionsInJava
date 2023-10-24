package com.backstreetbrogrammer.Q11_FIXLogParser;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class FIXLogFileParserTest {

    @Test
    void testReadFIXLogFileUsingBufferedReader() throws IOException {
        // This is the best way to read the FIX log
        try (final BufferedReader br = Files.newBufferedReader(
                Path.of("src", "main", "resources", "fix_log2.txt"))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                count++;
            }
            assertEquals(17, count);
        }
    }

    @Test
    void testReadFIXLogFileUsingStreams() throws IOException {
        try (final Stream<String> lines = Files.lines(
                Path.of("src", "main", "resources", "fix_log2.txt"))) {
            final AtomicInteger count = new AtomicInteger();
            lines.forEach(line -> {
                System.out.println(line);
                count.getAndIncrement();
            });
            assertEquals(17, count.get());
        }
    }

    @Test
    void testReadFIXLogFileUsingFileChannel() throws IOException {
        // DON'T USE THIS
        try (final SeekableByteChannel channel = Files.newByteChannel(
                Path.of("src", "main", "resources", "fix_log2.txt"), StandardOpenOption.READ)) {
            final ByteBuffer buffer = ByteBuffer.allocate(1000);
            int count = 0;
            while (channel.read(buffer) > 0) {
                buffer.flip();
                System.out.println(new String(buffer.array()));
                count++;
                buffer.clear();
            }
            assertEquals(3, count);
        }
    }

    @Test
    void readOnlyTradeLogs() throws IOException {
        try (final BufferedReader br = Files.newBufferedReader(
                Path.of("src", "main", "resources", "fix_log2.txt"))) {
            String line;
            int count = 0;
            final String tradeLogRegex = "^(?=.*\\b35=8\\b)(?=.*\\b150=([F12])\\b).*";
            final Pattern pattern = Pattern.compile(tradeLogRegex);
            while ((line = br.readLine()) != null) {
                final Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    System.out.println(line);
                    count++;
                }
            }
            assertEquals(6, count);
        }
    }

    @Test
    void extractTagFieldValues() throws IOException {
        try (final BufferedReader br = Files.newBufferedReader(
                Path.of("src", "main", "resources", "fix_log2.txt"))) {
            final String fieldValueRegex = "\\b(\\d+)=([^\\u0001]+)";
            final Pattern pattern = Pattern.compile(fieldValueRegex);
            String line;
            while ((line = br.readLine()) != null) {
                final Matcher matcher = pattern.matcher(line);
                final Map<Integer, String> fixTagValueMap = new HashMap<>();
                while (matcher.find()) {
                    final int fixTag = Integer.parseInt(matcher.group(1));
                    final String fixTagValue = matcher.group(2);
                    fixTagValueMap.put(fixTag, fixTagValue);
                }
                System.out.println(fixTagValueMap);
                assertFalse(fixTagValueMap.isEmpty());
            }
        }
    }
}
