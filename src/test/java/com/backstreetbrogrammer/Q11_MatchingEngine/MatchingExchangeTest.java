package com.backstreetbrogrammer.Q11_MatchingEngine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MatchingExchangeTest {
    private final OrderBook orderBook = new OrderBook();
    private static int i = 0;

    @BeforeEach
    void setUp() {
        orderBook.addOrder(new Order(++i, Order.Side.SELL, 20.30, 100));
        orderBook.addOrder(new Order(++i, Order.Side.SELL, 20.25, 100));
        orderBook.addOrder(new Order(++i, Order.Side.SELL, 20.30, 200));
        orderBook.addOrder(new Order(++i, Order.Side.BUY, 20.15, 100));
        orderBook.addOrder(new Order(++i, Order.Side.BUY, 20.20, 200));
        orderBook.addOrder(new Order(++i, Order.Side.BUY, 20.15, 200));

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
}
