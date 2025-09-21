package com.backstreetbrogrammer.Q11_MatchingEngine.twoD;

import com.backstreetbrogrammer.Q11_MatchingEngine.Order;
import com.backstreetbrogrammer.Q11_MatchingEngine.OrderBookI;
import com.backstreetbrogrammer.Q11_MatchingEngine.Side;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class OrderBookUsingSortedMapAndListTest {
    private final OrderBookI orderBook = new OrderBookUsingSortedMapAndList();
    private int i = 0;

    private static void delay() {
        try {
            TimeUnit.MILLISECONDS.sleep(1L);
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setUp() {
        orderBook.addOrder(new Order(++i, Side.SELL, 20.30, 100));
        delay();

        orderBook.addOrder(new Order(++i, Side.SELL, 20.25, 100));
        delay();

        orderBook.addOrder(new Order(++i, Side.SELL, 20.30, 200));
        delay();

        orderBook.addOrder(new Order(++i, Side.BUY, 20.15, 100));
        delay();

        orderBook.addOrder(new Order(++i, Side.BUY, 20.20, 200));
        delay();

        orderBook.addOrder(new Order(++i, Side.BUY, 20.15, 200));
        delay();

        System.out.println("Current Order Book:");
        orderBook.printOrderBook();
    }

    @Test
    void testOrderMatching() {
        System.out.println("\n### Sending BUY order 250@20.35");
        orderBook.addOrder(new Order(++i, Side.BUY, 20.35, 250));
        // this will trigger matching and generate trades

        System.out.println("\n### Order book after matching:");
        orderBook.printOrderBook();

        final Map<Integer, Order> orderCache = orderBook.getOrderCache();
        assertNotNull(orderCache);
        assertFalse(orderCache.isEmpty());
        assertTrue(orderCache.containsKey(3));

        final Order partialFilledSellOrder = orderCache.get(3);
        assertEquals(Side.SELL, partialFilledSellOrder.getSide());
        assertEquals(150, partialFilledSellOrder.getQuantity());
        assertEquals(20.3, partialFilledSellOrder.getPrice());
    }

    @Test
    void testLevel1() {
        orderBook.printLevel1();

        final Map<Integer, Order> orderCache = orderBook.getOrderCache();
        assertNotNull(orderCache);
        assertFalse(orderCache.isEmpty());
    }

    @Test
    void testOrderCancel() {
        System.out.println("\n### Sending BUY order 200@10");
        orderBook.addOrder(new Order(++i, Side.BUY, 10, 200));

        System.out.println("\n### Order book before cancel:");
        orderBook.printOrderBook();

        System.out.println("\n### Cancelling the BUY order 200@10");
        orderBook.cancelOrder(i);

        System.out.println("\n### Order book after cancel:");
        orderBook.printOrderBook();

        final Map<Integer, Order> orderCache = orderBook.getOrderCache();
        assertNotNull(orderCache);
        assertFalse(orderCache.isEmpty());
        assertFalse(orderCache.containsKey(i));
    }

    @Test
    void testOrderAmendQty() {
        System.out.println("\n### Sending BUY order 200@10");
        orderBook.addOrder(new Order(++i, Side.BUY, 10, 200));

        System.out.println("\n### Order book before amendment:");
        orderBook.printOrderBook();

        System.out.println("\n### Amending the BUY order qty from 200 to 250");
        orderBook.amendOrder(i, 250);

        System.out.println("\n### Order book after amendment:");
        orderBook.printOrderBook();

        final Map<Integer, Order> orderCache = orderBook.getOrderCache();
        assertNotNull(orderCache);
        assertFalse(orderCache.isEmpty());
        assertTrue(orderCache.containsKey(i));
        assertEquals(250, orderCache.get(i).getQuantity());
    }

    @Test
    void testOrderAmendPrice() {
        System.out.println("\n### Sending BUY order 200@10");
        orderBook.addOrder(new Order(++i, Side.BUY, 10, 200));

        System.out.println("\n### Order book before amendment:");
        orderBook.printOrderBook();

        System.out.println("\n### Amending the BUY order px from 10 to 20");
        orderBook.amendOrder(i, 20D);

        System.out.println("\n### Order book after amendment:");
        orderBook.printOrderBook();

        final Map<Integer, Order> orderCache = orderBook.getOrderCache();
        assertNotNull(orderCache);
        assertFalse(orderCache.isEmpty());
        assertTrue(orderCache.containsKey(i));
        assertEquals(20D, orderCache.get(i).getPrice());
    }

    @Test
    void testOrderAmendQtyAndPrice() {
        System.out.println("\n### Sending BUY order 200@10");
        orderBook.addOrder(new Order(++i, Side.BUY, 10, 200));

        System.out.println("\n### Order book before amendment:");
        orderBook.printOrderBook();

        System.out.println("\n### Amending the BUY order qty from 200 to 250 and price from 10 to 20");
        orderBook.amendOrder(i, 250, 20D);

        System.out.println("\n### Order book after amendment:");
        orderBook.printOrderBook();

        final Map<Integer, Order> orderCache = orderBook.getOrderCache();
        assertNotNull(orderCache);
        assertFalse(orderCache.isEmpty());
        assertTrue(orderCache.containsKey(i));
        assertEquals(250, orderCache.get(i).getQuantity());
        assertEquals(20D, orderCache.get(i).getPrice());
    }
}
