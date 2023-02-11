package com.backstreetbrogrammer.Q4_Stack;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyStackUsingLocksTest {

    private final MyStackUsingLocks<String> myStack = new MyStackUsingLocks<>(5);

    @Test
    @DisplayName("Test MyStack Locks implementation")
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
    @DisplayName("Test MyStack Locks implementation using multiple threads")
    void testMyStackMultithreaded() throws InterruptedException {
        final var latch = new CountDownLatch(2);
        final Thread thread1 = new Thread(() -> {
            myStack.push("A");
            myStack.push("B");
            myStack.push("C");
            myStack.push("D");
            myStack.push("E");
            myStack.push("F");
            latch.countDown();
        });

        thread1.start();

        final Thread thread2 = new Thread(() -> {
            myStack.pop();
            latch.countDown();
        });

        thread2.start();

        latch.await();

        assertEquals("F", myStack.peek());
        assertEquals("F", myStack.pop());

        myStack.push("G");

        assertEquals("G", myStack.pop());
        assertEquals("D", myStack.peek());
    }
}
