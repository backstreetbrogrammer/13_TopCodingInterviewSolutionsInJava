package com.backstreetbrogrammer.Q6_UnixTail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

public class JTailTest {

    @ParameterizedTest
    @MethodSource("getJTailObjects")
    @DisplayName("Test tail method for a given file")
    void testTailMethodForAGivenFile(final JTail jTail) throws IOException {
        System.out.println(jTail.tail());
        System.out.println("-----------------\n");
    }

    private static Stream<Arguments> getJTailObjects() {
        final String fileName = "src\\test\\resources\\amendments.txt";
        return Stream.of(
                Arguments.of(new JTail(fileName, 2)),
                Arguments.of(new JTail(fileName, 3)),
                Arguments.of(new JTail(fileName, 5)));
    }
}
