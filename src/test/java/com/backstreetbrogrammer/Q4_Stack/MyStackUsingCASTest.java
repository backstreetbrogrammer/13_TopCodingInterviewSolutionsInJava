package com.backstreetbrogrammer.Q4_Stack;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyStackUsingCASTest {

    private final MyStackUsingCAS<String> myStack = new MyStackUsingCAS<>();

    @Test
    @DisplayName("Test MyStack CAS implementation")
    void testMyStackCAS() {
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
    @DisplayName("Test MyStack CAS implementation using multiple threads")
    void testMyStackCASMultithreaded() throws InterruptedException {
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
        TimeUnit.SECONDS.sleep(1L);

        final Thread thread2 = new Thread(() -> {
            myStack.pop(); // F
            latch.countDown();
        });
        thread2.start();

        latch.await();

        assertEquals("E", myStack.peek());
        assertEquals("E", myStack.pop());

        myStack.push("G");
        assertEquals("G", myStack.pop());
        assertEquals("D", myStack.peek());
    }
}
