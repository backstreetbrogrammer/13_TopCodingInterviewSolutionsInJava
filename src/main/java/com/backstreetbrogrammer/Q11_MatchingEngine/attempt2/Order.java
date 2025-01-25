package com.backstreetbrogrammer.Q11_MatchingEngine.attempt2;

import java.time.LocalDateTime;

public class Order {
    enum Side { BUY, SELL }
    Side side;
    double price;
    int quantity;
    LocalDateTime entryTime;

    Order(final Side side, final double price, final int quantity) {
        this.side = side;
        this.price = price;
        this.quantity = quantity;
        this.entryTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Order{" +
                "side=" + side +
                ", price=" + price +
                ", quantity=" + quantity +
                ", entryTime=" + entryTime +
                '}';
    }

    public Side getSide() {
        return side;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }
}
