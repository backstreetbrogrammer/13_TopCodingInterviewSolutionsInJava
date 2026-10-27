package com.backstreetbrogrammer.Q13_ZeroAllocation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ZeroAllocationDemoTest {

    final ZeroAllocationDemo.BadMessageParser badParser = new ZeroAllocationDemo.BadMessageParser();
    final ZeroAllocationDemo.MutableOrderFlyweight flyweight = new ZeroAllocationDemo.MutableOrderFlyweight();

    // Simulate a raw binary wire buffer (8 bytes ID + 8 bytes price + 4 bytes qty = 20 bytes)
    final ByteBuffer rawWireBuffer = ByteBuffer.allocate(20);
    final byte[] wireData = "123456789,10525,500".getBytes(StandardCharsets.UTF_8);

    @BeforeEach
    void setUp() {
        rawWireBuffer.putLong(0, 123456789L); // Order ID
        rawWireBuffer.putLong(8, 10525L);     // Price: 105.25 scaled by 100
        rawWireBuffer.putInt(16, 500);        // Quantity
    }

    @Test
    void testBadParser() {
        for (int j = 0; j < 10; j++) {
            final long startTime = System.nanoTime();
            for (int i = 0; i < 1_000_000; i++) {
                badParser.parse(wireData);
            }
            final long endTime = System.nanoTime();
            System.out.println("[Bad Parser] Time taken: " + (endTime - startTime) / 1_000_000 + " ms");
        }
    }

    @Test
    void testZeroHeapAllocation() {
        for (int j = 0; j < 10; j++) {
            final long startTime = System.nanoTime();
            for (int i = 0; i < 1_000_000; i++) {
                // Zero-allocation approach: reuse the flyweight, read directly from buffer
                flyweight.wrap(rawWireBuffer, 0);
            }
            final long endTime = System.nanoTime();

            System.out.println("[Zero Heap] Time taken: " + (endTime - startTime) / 1_000_000 + " ms");
        }
    }
}
