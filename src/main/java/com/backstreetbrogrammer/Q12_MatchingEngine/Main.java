package com.backstreetbrogrammer.Q12_MatchingEngine;

public class Main {
    public static void main(final String[] args) {
        final OrderBook orderBook = new OrderBook();
        orderBook.addOrder(new Order(Order.Side.SELL, 20.30, 100));
        orderBook.addOrder(new Order(Order.Side.SELL, 20.25, 100));
        orderBook.addOrder(new Order(Order.Side.SELL, 20.30, 200));
        orderBook.addOrder(new Order(Order.Side.BUY, 20.15, 100));
        orderBook.addOrder(new Order(Order.Side.BUY, 20.20, 200));
        orderBook.addOrder(new Order(Order.Side.BUY, 20.15, 200));

        //buy 250 shares at 20.35
        orderBook.addOrder(new Order(Order.Side.BUY, 20.35, 250));

        orderBook.printOrderBook();

        System.out.println("Cancelling an order");
        orderBook.addOrder(new Order(Order.Side.BUY, 10, 200));
        System.out.println("Order book before cancel:");
        orderBook.printOrderBook();

        orderBook.cancelOrder(Order.Side.BUY, 10, 200);

        System.out.println("Order book after cancel:");
        orderBook.printOrderBook();
    }
}
