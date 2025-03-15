package com.backstreetbrogrammer.Q11_MatchingEngine;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order {

    private final int id;

    private final Side side;
    private final LocalDateTime entryTime;
    private double price;
    private int quantity;

    public Order(final int id, final Side side, final double price, final int quantity) {
        this.id = id;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
        this.entryTime = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public Side getSide() {
        return side;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Order order = (Order) o;
        return id == order.id && Double.compare(price, order.price) == 0 && quantity == order.quantity && side == order.side && Objects.equals(entryTime, order.entryTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, side, entryTime, price, quantity);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", side=" + side +
                ", entryTime=" + entryTime +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
