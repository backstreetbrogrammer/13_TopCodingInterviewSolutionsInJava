package com.backstreetbrogrammer.Q11_MatchingEngine.twoD;

import com.backstreetbrogrammer.Q11_MatchingEngine.Order;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

public class PriceLevelPQ {
    private final double price;
    private final PriorityQueue<Order> orders = new PriorityQueue<>(Comparator.comparing(Order::getEntryTime));
    private int totalVolume = 0;

    public PriceLevelPQ(final double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public int getTotalVolume() {
        return totalVolume;
    }

    public PriorityQueue<Order> getOrders() {
        final PriorityQueue<Order> copy = new PriorityQueue<>(Comparator.comparing(Order::getEntryTime));
        copy.addAll(orders);
        return copy;
    }

    public void addOrder(final Order order) {
        orders.add(order); // O(log n)
        totalVolume += order.getQuantity();
    }

    public void removeOrder(final Order order) {
        orders.remove(order); // O(n)
        totalVolume -= order.getQuantity();
    }

    public Order pollBestOrder() {
        final Order order = orders.poll(); // O(log n)
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
        final PriceLevelPQ that = (PriceLevelPQ) o;
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
