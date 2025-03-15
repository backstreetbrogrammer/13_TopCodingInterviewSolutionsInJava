package com.backstreetbrogrammer.Q11_MatchingEngine.twoD;

import com.backstreetbrogrammer.Q11_MatchingEngine.Order;

import java.util.LinkedList;
import java.util.Objects;

public class PriceLevelList {
    private final double price;
    private final LinkedList<Order> orders = new LinkedList<>();
    private int totalVolume = 0;

    public PriceLevelList(final double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public int getTotalVolume() {
        return totalVolume;
    }

    public LinkedList<Order> getOrders() {
        return new LinkedList<>(orders);
    }

    public void addOrder(final Order order) {
        orders.addLast(order); // O(1)
        totalVolume += order.getQuantity();
    }

    public void removeOrder(final Order order) {
        orders.remove(order); // O(n)
        totalVolume -= order.getQuantity();
    }

    public Order pollBestOrder() {
        final Order order = orders.removeFirst(); // O(1)
        totalVolume -= order.getQuantity();
        return order;
    }

    public String getLevel1() {
        return String.format("BestPrice: [%.2f], TotalVolume: [%d], TotalOrders: [%d]%n", price, totalVolume,
                             orders.size());
    }

    public boolean isEmpty() {
        return orders.isEmpty();
    }

    public int totalOrders() {
        return orders.size();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PriceLevelList that = (PriceLevelList) o;
        return Double.compare(price, that.price) == 0 && totalVolume == that.totalVolume && Objects.equals(orders, that.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, orders, totalVolume);
    }

    @Override
    public String toString() {
        return "PriceLevel{" +
                "price=" + price +
                ", orders=" + orders +
                ", totalVolume=" + totalVolume +
                '}';
    }
}
