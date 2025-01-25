package com.backstreetbrogrammer.Q11_MatchingEngine.attempt2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExchangeTest2 {
    private final OrderBook orderBook = new OrderBook();

    @BeforeEach
    void setUp() {
        orderBook.addOrder(new Order(Order.Side.SELL, 20.30, 100));
        orderBook.addOrder(new Order(Order.Side.SELL, 20.25, 100));
        orderBook.addOrder(new Order(Order.Side.SELL, 20.30, 200));
        orderBook.addOrder(new Order(Order.Side.BUY, 20.15, 100));
        orderBook.addOrder(new Order(Order.Side.BUY, 20.20, 200));
        orderBook.addOrder(new Order(Order.Side.BUY, 20.15, 200));

        System.out.println("Current Order Book:");
        orderBook.printOrderBook();
    }

    @Test
    void testOrderMatching() {
        System.out.println("\n### Sending BUY order 250@20.35");
        orderBook.addOrder(new Order(Order.Side.BUY, 20.35, 250));
        // this will trigger matching and generate trades

        System.out.println("\n### Order book after matching:");
        orderBook.printOrderBook();
    }

    @Test
    void testOrderCancel() {
        System.out.println("\n### Sending BUY order 200@10");
        orderBook.addOrder(new Order(Order.Side.BUY, 10, 200));

        System.out.println("\n### Order book before cancel:");
        orderBook.printOrderBook();

        System.out.println("\n### Cancelling the BUY order 200@10");
        orderBook.cancelOrder(Order.Side.BUY, 10, 200); // TODO: use orderId

        System.out.println("\n### Order book after cancel:");
        orderBook.printOrderBook();
    }
}
