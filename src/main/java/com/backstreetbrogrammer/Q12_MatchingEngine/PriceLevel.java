package com.backstreetbrogrammer.Q12_MatchingEngine;

import static com.backstreetbrogrammer.Q12_MatchingEngine.NumberUtil.moduloPowerOfTwo;

public class PriceLevel implements EngineConstants {

    final Side side;
    double price;
    int totalOpenOrders = 0;
    int totalCancelOrders = 0;
    int totalExecuteOrders = 0;
    int start = -1;
    int end = -1;
    int[][] orderQuantity = new int[MAX_ORDERS_AT_EACH_PRICE_LEVEL][2]; // orderId and quantity


    public PriceLevel(final Side side) {
        this.side = side;
    }

    public boolean isEmpty() {
        return totalOpenOrders == 0;
    }


    private void reset(final double price) {
        this.price = price;
        this.totalOpenOrders = this.totalCancelOrders = this.totalExecuteOrders = 0;
        this.start = this.end = -1;
    }


    private void makeInvalid(final int oIdx) {
        orderQuantity[oIdx][0] = -1;
        orderQuantity[oIdx][1] = -1;
        totalOpenOrders--;
    }

    private boolean isInvalid(final int idx) {
        return orderQuantity[idx][0] <= 0 && orderQuantity[idx][1] <= 0;
    }

    public double getPrice() {
        return price;
    }

    public int newOrder(int orderId, Order order) {
        if (totalOpenOrders == 0) {
            reset(order.getPrice());
        }
        if (start == -1) {
            start = 0;
            int idx = start;
            insertOrder(orderId, order, idx);
            return idx;
        }

        final int idx = moduloPowerOfTwo(end + 1, MAX_ORDERS_AT_EACH_PRICE_LEVEL);
        if (idx == start) {
            throw new RuntimeException("All available entries at price level is allotted ");
        }
        insertOrder(orderId, order, idx);
        return idx;
    }

    private void insertOrder(int orderId, Order order, int idx) {
        orderQuantity[idx][0] = orderId;
        orderQuantity[idx][1] = order.getQuantity();
        end = idx;
        totalOpenOrders++;
    }
}
