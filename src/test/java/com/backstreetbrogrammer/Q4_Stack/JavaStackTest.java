package com.backstreetbrogrammer.Q4_Stack;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JavaStackTest {

    private final Stack<String> javaStack = new Stack<>();

    @Test
    @DisplayName("Test Java Stack implementation")
    void testJavaStack() {
        javaStack.push("A");
        javaStack.push("B");
        javaStack.push("C");
        javaStack.push("D");

        assertEquals("D", javaStack.peek());
        assertEquals("D", javaStack.pop());
        assertEquals("C", javaStack.pop());
        assertEquals("B", javaStack.peek());

        javaStack.push("E");

        assertEquals("E", javaStack.pop());
        assertEquals("B", javaStack.peek());
    }

    @Test
    @DisplayName("Test when stack is empty")
    void testEmptyStack() {
        assertThrows(EmptyStackException.class, javaStack::pop);
        assertThrows(EmptyStackException.class, javaStack::peek);
    }
}
