package com.backstreetbrogrammer.Q9_UnixFind;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class JFindTest {

    private JFind finder;

    @BeforeEach
    void setUp() {
        finder = new JFind();
        final var path = Path.of("src", "test", "resources");
        finder.setDirectoryName(path.toString());
    }

    @Test
    @DisplayName("Test invalid directory")
    void testInvalidDirectory() {
        assertThrows(IllegalArgumentException.class, () -> finder.setDirectoryName(""));
    }

    @Test
    @DisplayName("Test invalid pattern")
    void testInvalidPattern() {
        assertThrows(IllegalArgumentException.class, () -> finder.setNamePattern(null));
    }

    @ParameterizedTest
    @MethodSource("getFileNamePattern")
    @DisplayName("Test Basic JFind implementation")
    void testBasicFind(final String pattern) {
        finder.setNamePattern(pattern);
        finder.findFiles();
        System.out.println("-------------------\n");
    }

    private static Stream<Arguments> getFileNamePattern() {
        return Stream.of(
                Arguments.of("*.txt"),
                Arguments.of("*.ser"),
                Arguments.of("a*.txt"),
                Arguments.of("bb*.txt"),
                Arguments.of("*Lock*.txt"),
                Arguments.of("dummy?.txt"),
                Arguments.of("d?mmy?.txt"),
                Arguments.of("dummy[12].txt")
                        );
    }
}
