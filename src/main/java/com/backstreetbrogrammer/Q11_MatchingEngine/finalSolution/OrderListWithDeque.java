package com.backstreetbrogrammer.Q11_MatchingEngine.finalSolution;

import com.backstreetbrogrammer.Q11_MatchingEngine.Order;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class OrderListWithDeque {
    private final Deque<Order> orders = new ArrayDeque<>();
    private int size;
    private int totalVolume;

    public void addHead(final Order order) {
        orders.addFirst(order);
        totalVolume += order.getQuantity();
        size++;
    }

    public void addTail(final Order order) {
        orders.addLast(order);
        totalVolume += order.getQuantity();
        size++;
    }

    public Order getHead() {
        final Order order = orders.pollFirst();
        if (order != null) {
            totalVolume -= order.getQuantity();
            size--;
        }
        return order;
    }

    public void removeOrder(final Order order) {
        if (orders.remove(order)) {
            totalVolume -= order.getQuantity();
            size--;
        }
    }

    public Order peekHead() {
        return orders.peekFirst();
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getTotalVolume() {
        return totalVolume;
    }

    public void printList() {
        for (final Order order : orders) {
            System.out.println(order);
        }
    }
}
