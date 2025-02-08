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

    }

    @Override
    public void cancelOrder(final int orderId) {

    }

    @Override
    public void amendOrder(final int orderId, final int qtyNew) {

    }

    @Override
    public void amendOrder(final int orderId, final double pxNew) {

    }

    @Override
    public void amendOrder(final int orderId, final int qtyNew, final double pxNew) {

    }
}
