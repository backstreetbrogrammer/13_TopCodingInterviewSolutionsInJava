package com.backstreetbrogrammer.Q12_MatchingEngine;

public class Order {

    private Side side;
    private int quantity;
    private int filledQty;
    private double price;

    public boolean isBuy() {
        return side == Side.BUY;
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

    public boolean isComplete() {
        return quantity == filledQty;
    }
}
