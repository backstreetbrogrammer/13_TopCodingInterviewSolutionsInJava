package com.backstreetbrogrammer.Q4_Stack;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MyStackTest {

    private final MyStack<String> myStack = new MyStack<>();

    @Test
    @DisplayName("Test MyStack implementation")
    void testMyStack() {
        myStack.push("A");
        myStack.push("B");
        myStack.push("C");
        myStack.push("D");

        assertEquals("D", myStack.peek());
        assertEquals("D", myStack.pop());
        assertEquals("C", myStack.pop());
        assertEquals("B", myStack.peek());

        myStack.push("E");

        assertEquals("E", myStack.pop());
        assertEquals("B", myStack.peek());
    }

    @Test
    @DisplayName("Test when stack is empty")
    void testEmptyStack() {
        assertThrows(EmptyStackException.class, myStack::pop);
        assertThrows(EmptyStackException.class, myStack::peek);
    }
}
