package com.backstreetbrogrammer.Q12_MatchingEngine;

import java.util.List;

public class Engine {
    private void matchAndInsert(OrderBook crossBook, int orderId, Order order, OrderBook orderBook, OrderIndex orderIndex) {
        final List<Trade> trades = crossBook.matchOrder(orderId, order);
        if (!trades.isEmpty()) {
            execute(orderId,order.getSide(),trades);
        }
        if (!order.isComplete()) {
            final OrderIndex.Index buyindex = orderBook.onNewOrder(orderId, order);
            orderIndex.addIndex(orderId, buyindex);
        }
    }

    private void execute(final int orderId, final Side side, final List<Trade> trades) {

    }
}
