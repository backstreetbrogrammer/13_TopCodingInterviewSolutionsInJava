package com.backstreetbrogrammer.Q11_MatchingEngine;


import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class OrderBookUsingPQ implements OrderBookI {
    private final PriorityQueue<Order> buyOrders = new PriorityQueue<>(Comparator.comparingDouble(Order::getPrice)
                                                                                 .reversed()
                                                                                 .thenComparing(Order::getEntryTime));

    private final PriorityQueue<Order> sellOrders = new PriorityQueue<>(Comparator.comparingDouble(Order::getPrice)
                                                                                  .thenComparing(Order::getEntryTime));
    private final Map<Integer, Order> orderCache = new HashMap<>();

    @Override
    public Map<Integer, Order> getOrderCache() {
        return Map.copyOf(orderCache);
    }

    public void addOrder(final Order order) {
        // Pre-conditions
        if ((order == null) || orderCache.containsKey(order.getId())) {
            throw new IllegalArgumentException("Order is null or already exists");
        }

        addOrderAndMatch(order);
    }

    private void addOrderAndMatch(final Order order) {
        // Add the order
        orderCache.put(order.getId(), order);

        if (order.getSide() == Order.Side.BUY) {
            buyOrders.add(order); // O(log n)
        } else {
            sellOrders.add(order); // O(log n)
        }

        // Check for match
        matchOrders();
    }


    private void matchOrders() {
        // Time complexity for heap => peek() and poll(): O(1)
        while (!buyOrders.isEmpty() && !sellOrders.isEmpty() &&
                buyOrders.peek().getPrice() >= sellOrders.peek().getPrice()) {
            final Order buyOrder = buyOrders.poll(); // O(1)
            final Order sellOrder = sellOrders.poll(); // O(1)

            final int buyQty = buyOrder.getQuantity();
            final int sellQty = sellOrder.getQuantity();

            final int fillQty = Math.min(buyQty, sellQty);
            buyOrder.setQuantity(buyQty - fillQty);
            sellOrder.setQuantity(sellQty - fillQty);

            System.out.println("Matched Order: " + fillQty + " @ " + sellOrder.getPrice());

            if (buyOrder.getQuantity() > 0) {
                buyOrders.add(buyOrder); // O(log n)
            }
            if (sellOrder.getQuantity() > 0) {
                sellOrders.add(sellOrder); // O(log n)
            }
        }
    }

    @Override
    public void printOrderBook() {
        System.out.println("--------------------------");
        System.out.println("SELL Orders:");
        sellOrders.forEach(System.out::println); // O(n)
        System.out.println("\nBUY Orders:");
        buyOrders.forEach(System.out::println); // O(n)
        System.out.println("--------------------------");
    }

    @Override
    public void printLevel1() {
        System.out.println("--------------------------");

        System.out.println("BEST ASK~>");
        if (!sellOrders.isEmpty()) {
            System.out.printf("%s%n", sellOrders.peek()); // O(1)
        }

        System.out.println("--------------------------");

        System.out.println("BEST BID~>");
        if (!buyOrders.isEmpty()) {
            System.out.printf("%s%n", buyOrders.peek()); // O(1)
        }

        System.out.println("--------------------------");
    }

    @Override
    public void cancelOrder(final int orderId) {
        if (orderCache.containsKey(orderId)) {
            final Order order = orderCache.get(orderId);
            removeOrder(order);
            System.out.printf("Canceled: %s%n", order);
        } else {
            System.err.printf("Order ID not found: %d%n", orderId);
        }
    }

    @Override
    public void amendOrder(final int orderId, final int qtyNew) {
        // Pre-conditions
        if (!orderCache.containsKey(orderId) || (qtyNew < 1)) {
            throw new IllegalArgumentException("Order does not exists or incorrect qty");
        }

        final Order order = orderCache.get(orderId);
        order.setQuantity(qtyNew); // O(1)

        removeAndAddOrder(order);
        System.out.printf("Order amended: %s%n", order);
    }

    @Override
    public void amendOrder(final int orderId, final double pxNew) {
        // Pre-conditions
        if (!orderCache.containsKey(orderId) || (pxNew < 1)) {
            throw new IllegalArgumentException("Order does not exists or incorrect price");
        }

        final Order order = orderCache.get(orderId);
        order.setPrice(pxNew); // O(1)

        removeAndAddOrder(order);
        System.out.printf("Order amended: %s%n", order);
    }

    @Override
    public void amendOrder(final int orderId, final int qtyNew, final double pxNew) {
        // Pre-conditions
        if (!orderCache.containsKey(orderId) || (qtyNew < 1) || (pxNew < 1)) {
            throw new IllegalArgumentException("Order does not exists or incorrect qty / price");
        }

        final Order order = orderCache.get(orderId);
        order.setQuantity(qtyNew); // O(1)
        order.setPrice(pxNew); // O(1)

        removeAndAddOrder(order);
        System.out.printf("Order amended: %s%n", order);
    }

    private void removeAndAddOrder(final Order order) {
        removeOrder(order);
        addOrderAndMatch(order);
    }

    private void removeOrder(final Order order) {
        orderCache.remove(order.getId());
        if (order.getSide() == Order.Side.BUY) {
            buyOrders.remove(order); // O(n)
        } else {
            sellOrders.remove(order); // O(n)
        }
    }
}
