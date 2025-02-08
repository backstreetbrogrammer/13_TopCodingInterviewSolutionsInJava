package com.backstreetbrogrammer.Q11_MatchingEngine;

public interface OrderBookI {

    void addOrder(final Order order);

    void printLevel1();

    void printOrderBook();

    void cancelOrder(final int orderId);

    void amendOrder(final int orderId, final int qtyNew);

    void amendOrder(final int orderId, final double pxNew);

    void amendOrder(final int orderId, final int qtyNew, final double pxNew);

}
