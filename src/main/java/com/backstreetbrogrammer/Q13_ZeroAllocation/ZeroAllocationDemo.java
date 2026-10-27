package com.backstreetbrogrammer.Q13_ZeroAllocation;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public final class ZeroAllocationDemo {
    // =========================================================================
    // THE ANTI-PATTERN: High Allocation Hot Path (DO NOT DO THIS)
    // =========================================================================
    public static class BadMessageParser {
        public ParsedMessage parse(final byte[] wireData) {
            // Allocates a String object on the heap
            final String text = new String(wireData, StandardCharsets.UTF_8);
            final String[] parts = text.split(","); // Allocates array + multiple String objects!

            final long orderId = Long.parseLong(parts[0]);
            final double price = Double.parseDouble(parts[1]);
            final int quantity = Integer.parseInt(parts[2]);

            // Allocates a brand new object on the heap every single message!
            return new ParsedMessage(orderId, price, quantity);
        }
    }

    // =========================================================================
    // THE ULL PATTERN: Zero Allocation Flyweight Parser
    // =========================================================================
    public static class MutableOrderFlyweight {
        private long orderId;
        private long priceScaled; // Store prices as fixed-point integers (e.g., * 100.0) to avoid doubles/floats
        private int quantity;

        // Reset state without re-allocating
        public void wrap(final ByteBuffer buffer, final int offset) {
            this.orderId = buffer.getLong(offset);
            this.priceScaled = buffer.getLong(offset + 8);
            this.quantity = buffer.getInt(offset + 16);
        }

        public long getOrderId() {
            return orderId;
        }

        public long getPriceScaled() {
            return priceScaled;
        }

        public int getQuantity() {
            return quantity;
        }
    }

    // =========================================================================
    // Demonstration & Verification
    // =========================================================================
    public static void main(final String[] args) {
        final MutableOrderFlyweight flyweight = new MutableOrderFlyweight();

        // Simulate a raw binary wire buffer (8 bytes ID + 8 bytes price + 4 bytes qty = 20 bytes)
        final ByteBuffer rawWireBuffer = ByteBuffer.allocate(20);
        rawWireBuffer.putLong(0, 123456789L); // Order ID
        rawWireBuffer.putLong(8, 10525L);     // Price: 105.25 scaled by 100
        rawWireBuffer.putInt(16, 500);        // Quantity

        // Hot-loop simulation
        final long startTime = System.nanoTime();
        for (int i = 0; i < 1_000_000; i++) {
            // Zero-allocation approach: reuse the flyweight, read directly from buffer
            flyweight.wrap(rawWireBuffer, 0);
        }
        final long endTime = System.nanoTime();

        System.out.println("Processed 1,000,000 messages with 0 heap allocations!");
        System.out.println("Time taken: " + (endTime - startTime) / 1_000_000 + " ms");
    }

    public static class ParsedMessage {
        private final long orderId;
        private final double price;
        private final int quantity;

        public ParsedMessage(final long orderId, final double price, final int quantity) {
            this.orderId = orderId;
            this.price = price;
            this.quantity = quantity;
        }
    }
}
