package com.backstreetbrogrammer.Q12_FIXLogParser;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
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
    @Disabled
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

    @ParameterizedTest
    @MethodSource("getFixLogLine")
    void extractTagFieldValuesWithPipeSeparator(final String line, final String fieldExtractorRegex) {
        final Pattern pattern = Pattern.compile(fieldExtractorRegex);
        final Matcher matcher = pattern.matcher(line);
        final Map<Integer, String> fixTagValueMap = new HashMap<>();
        while (matcher.find()) {
            final int tag = Integer.parseInt(matcher.group(1));
            final String tagValue = matcher.group(2).strip();
            fixTagValueMap.put(tag, tagValue);
        }
        System.out.println(fixTagValueMap);
        assertFalse(fixTagValueMap.isEmpty());
    }

    @Test
    void testEfficientWriteToALargeFile() throws IOException {
        final Path writeFilePath = Path.of("src", "main", "resources", "fix_log3.txt");
        final Path readFilePath = Path.of("src", "main", "resources", "fix_log1.txt");
        try (
                final RandomAccessFile randomAccessFile = new RandomAccessFile(writeFilePath.toFile(), "rws");
                final FileChannel fileChannel = randomAccessFile.getChannel()
        ) {
            try (final BufferedReader fileReader = Files.newBufferedReader(readFilePath)) {
                String line;
                while ((line = fileReader.readLine()) != null) {
                    line += System.lineSeparator();
                    final byte[] lineInBytes = line.getBytes(StandardCharsets.UTF_8);
                    final ByteBuffer buffer = ByteBuffer.allocate(lineInBytes.length);
                    buffer.put(lineInBytes);
                    buffer.flip();

                    final int bytesWritten = fileChannel.write(buffer);
                    assertTrue(bytesWritten > 0);
                }
            }
        }
    }

    @Test
    void testEasiestWayToWriteToAFile() throws IOException {
        final Path writeFilePath = Path.of("src", "main", "resources", "fix_log3.txt");
        final Path readFilePath = Path.of("src", "main", "resources", "fix_log1.txt");
        try (
                final BufferedReader reader = Files.newBufferedReader(readFilePath);
                final BufferedWriter writer = Files.newBufferedWriter(writeFilePath)
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    private static Stream<Arguments> getFixLogLine() {
        return Stream.of(
                Arguments.of("8=FIX.4.2 | 9=178 | 35=8 | 49=PHLX | 56=PERS | 52=20071123-05:30:00.000 | 11=ATOMNOCCC9990900 | 20=3 | 150=E | 39=E | 55=MSFT | 167=CS | 54=1 | 38=15 | 40=2 | 44=15 | 58=PHLX EQUITY TESTING | 59=0 | 47=C | 32=0 | 31=0 | 151=15 | 14=0 | 6=0 | 10=128 |",
                             "\\b(\\d+)=([^\\u007c]+)"),
                Arguments.of("8=FIX.4.2   9=178   35=8   49=PHLX   56=PERS   52=20071123-05:30:00.000   11=ATOMNOCCC9990900   20=3   150=E   39=E   55=MSFT   167=CS   54=1   38=15   40=2   44=15   58=PHLX EQUITY TESTING   59=0   47=C   32=0   31=0   151=15   14=0   6=0   10=128  ",
                             "\\b(\\d+)=([^\\s]+)")
                        );
    }
}
