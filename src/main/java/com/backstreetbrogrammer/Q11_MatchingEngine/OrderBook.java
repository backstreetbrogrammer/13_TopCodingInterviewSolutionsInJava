package com.backstreetbrogrammer.Q11_MatchingEngine;


import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class OrderBook {
    private final PriorityQueue<Order> buyOrders = new PriorityQueue<>(Comparator.comparingDouble(Order::getPrice)
                                                                                 .reversed()
                                                                                 .thenComparing(Order::getEntryTime));

    private final PriorityQueue<Order> sellOrders = new PriorityQueue<>(Comparator.comparingDouble(Order::getPrice)
                                                                                  .thenComparing(Order::getEntryTime));
    private final Map<Integer, Order> orderCache = new HashMap<>();

    public void addOrder(final Order order) {
        // Pre-conditions
        if ((order == null) || orderCache.containsKey(order.getId())) {
            throw new IllegalArgumentException("Order is null or already exists");
        }

        // Add the order
        orderCache.put(order.getId(), order);

        // Time complexity: O(log n)
        if (order.getSide() == Order.Side.BUY) {
            buyOrders.add(order);
        } else {
            sellOrders.add(order);
        }

        // Check for match
        matchOrders();
    }

    private void matchOrders() {
        // Time complexity for heap => peek() and poll(): O(1)
        while (!buyOrders.isEmpty() && !sellOrders.isEmpty() &&
                buyOrders.peek().getPrice() >= sellOrders.peek().getPrice()) {
            final Order buyOrder = buyOrders.poll();
            final Order sellOrder = sellOrders.poll();

            final int buyQty = buyOrder.getQuantity();
            final int sellQty = sellOrder.getQuantity();

            final int fillQty = Math.min(buyQty, sellQty);
            buyOrder.setQuantity(buyQty - fillQty);
            sellOrder.setQuantity(sellQty - fillQty);

            System.out.println("Matched Order: " + fillQty + " @ " + sellOrder.getPrice());

            if (buyOrder.getQuantity() > 0) {
                buyOrders.add(buyOrder);
            }
            if (sellOrder.getQuantity() > 0) {
                sellOrders.add(sellOrder);
            }
        }
    }

    public void printOrderBook() {
        System.out.println("--------------------------");
        System.out.println("SELL Orders:");
        sellOrders.forEach(System.out::println);
        System.out.println("\nBUY Orders:");
        buyOrders.forEach(System.out::println);
        System.out.println("--------------------------");
    }
}
