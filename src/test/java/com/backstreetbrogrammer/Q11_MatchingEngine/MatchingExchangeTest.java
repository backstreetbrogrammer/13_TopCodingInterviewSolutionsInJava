package com.backstreetbrogrammer.Q11_MatchingEngine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class MatchingExchangeTest {
    private final OrderBookI orderBook = new OrderBookUsingList();
    private static int i = 0;

    private static void delay() {
        try {
            TimeUnit.MILLISECONDS.sleep(1L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setUp() {
        orderBook.addOrder(new Order(++i, Order.Side.SELL, 20.30, 100));
        delay();

        orderBook.addOrder(new Order(++i, Order.Side.SELL, 20.25, 100));
        delay();

        orderBook.addOrder(new Order(++i, Order.Side.SELL, 20.30, 200));
        delay();

        orderBook.addOrder(new Order(++i, Order.Side.BUY, 20.15, 100));
        delay();

        orderBook.addOrder(new Order(++i, Order.Side.BUY, 20.20, 200));
        delay();

        orderBook.addOrder(new Order(++i, Order.Side.BUY, 20.15, 200));
        delay();

        System.out.println("Current Order Book:");
        orderBook.printOrderBook();
    }

    @Test
    void testOrderMatching() {
        System.out.println("\n### Sending BUY order 250@20.35");
        orderBook.addOrder(new Order(++i, Order.Side.BUY, 20.35, 250));
        // this will trigger matching and generate trades

        System.out.println("\n### Order book after matching:");
        orderBook.printOrderBook();
    }

    @Test
    void testLevel1() {
        orderBook.printLevel1();
    }

    @Test
    void testOrderCancel() {
        System.out.println("\n### Sending BUY order 200@10");
        orderBook.addOrder(new Order(++i, Order.Side.BUY, 10, 200));

        System.out.println("\n### Order book before cancel:");
        orderBook.printOrderBook();

        System.out.println("\n### Cancelling the BUY order 200@10");
        orderBook.cancelOrder(i);

        System.out.println("\n### Order book after cancel:");
        orderBook.printOrderBook();
    }
}
