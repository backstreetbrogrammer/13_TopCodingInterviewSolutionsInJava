package com.backstreetbrogrammer.Q11_MatchingEngine;


import java.util.*;

public class OrderBookUsingList implements OrderBookI {
    private final LinkedList<Order> buyOrders = new LinkedList<>();

    private final LinkedList<Order> sellOrders = new LinkedList<>();
    private final Map<Integer, Order> orderCache = new HashMap<>();

    @Override
    public void addOrder(final Order order) {
        // Pre-conditions
        if ((order == null) || orderCache.containsKey(order.getId())) {
            throw new IllegalArgumentException("Order is null or already exists");
        }

        // Add the order
        orderCache.put(order.getId(), order);

        if (order.getSide() == Order.Side.BUY) {
            buyOrders.addLast(order); // O(1)
            buyOrders.sort(Comparator.comparingDouble(Order::getPrice)
                                     .reversed()
                                     .thenComparing(Order::getEntryTime)); // O(n * logn)
        } else {
            sellOrders.addLast(order); // O(1)
            sellOrders.sort(Comparator.comparingDouble(Order::getPrice)
                                      .thenComparing(Order::getEntryTime)); // O(n * logn)
        }

        // Check for match
        matchOrders();
    }


    private void matchOrders() {
        while (!buyOrders.isEmpty() && !sellOrders.isEmpty() &&
                buyOrders.getFirst().getPrice() >= sellOrders.getFirst().getPrice()) {
            final Order buyOrder = buyOrders.getFirst();   // O(1)
            final Order sellOrder = sellOrders.getFirst(); // O(1)

            final int buyOrderQuantity = buyOrder.getQuantity();
            final int sellOrderQuantity = sellOrder.getQuantity();
            final int fillQuantity = Math.min(buyOrderQuantity, sellOrderQuantity);

            System.out.println("Matched Order: " + fillQuantity + " @ " + sellOrder.getPrice());

            buyOrder.setQuantity(buyOrderQuantity - fillQuantity);
            sellOrder.setQuantity(sellOrderQuantity - fillQuantity);

            if (buyOrder.getQuantity() == 0) buyOrders.removeFirst();   // O(1)
            if (sellOrder.getQuantity() == 0) sellOrders.removeFirst(); // O(1)
        }
    }

    @Override
    public void printOrderBook() {
        System.out.println("--------------------------");
        System.out.println("SELL Orders:");
        reverseList(sellOrders).forEach(System.out::println);
        System.out.println("\nBUY Orders:");
        buyOrders.forEach(System.out::println);
        System.out.println("--------------------------");
    }

    private List<Order> reverseList(final List<Order> orders) {
        final List<Order> result = new ArrayList<>(orders);
        Collections.reverse(result);
        return result;
    }

    @Override
    public void printLevel1() {
        System.out.println("--------------------------");

        System.out.println("BEST ASK~>");
        if (!sellOrders.isEmpty()) {
            System.out.printf("%s%n", sellOrders.getFirst());
        }

        System.out.println("--------------------------");

        System.out.println("BEST BID~>");
        if (!buyOrders.isEmpty()) {
            System.out.printf("%s%n", buyOrders.getFirst());
        }

        System.out.println("--------------------------");
    }

    @Override
    public void cancelOrder(final int orderId) {
        final Order order = orderCache.remove(orderId);
        if (order != null) {
            if (order.getSide() == Order.Side.BUY) {
                buyOrders.remove(order); // O(n)
            } else {
                sellOrders.remove(order); // O(n)
            }
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
        order.setQuantity(qtyNew);

        System.out.printf("Order amended: %s%n", order);
    }

    @Override
    public void amendOrder(final int orderId, final double pxNew) {

    }

    @Override
    public void amendOrder(final int orderId, final int qtyNew, final double pxNew) {

    }
}
