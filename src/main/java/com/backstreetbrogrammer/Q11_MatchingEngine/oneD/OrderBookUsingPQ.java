package com.backstreetbrogrammer.Q11_MatchingEngine.oneD;


import com.backstreetbrogrammer.Q11_MatchingEngine.Order;
import com.backstreetbrogrammer.Q11_MatchingEngine.OrderBookI;
import com.backstreetbrogrammer.Q11_MatchingEngine.Side;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;


/**
 * OrderBook class to manage buy and sell orders using a priority queue.
 *
 * <p>Time complexity of various Priority Queue methods:</p>
 * <ul>
 *   <li>Enqueuing and dequeuing methods (offer, poll, remove, and add): O(log n)</li>
 *   <li>Remove(Object) and contains(Object) methods: O(n)</li>
 *   <li>Retrieval methods (peek, element, and size): O(1)</li>
 * </ul>
 */
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

    @Override
    public void addOrder(final Order order) {
        // Pre-conditions
        if ((order == null) || orderCache.containsKey(order.getId())) {
            throw new IllegalArgumentException("Order is null or already exists");
        }

        // Try to match the order first
        matchOrders(order);

        // If the order is not fully filled, add it to the order book
        if (order.getQuantity() > 0) {
            addOrderToOrderBook(order);
        }
    }

    private void addOrderToOrderBook(final Order order) {
        // Add the order
        orderCache.put(order.getId(), order);

        if (order.getSide() == Side.BUY) {
            buyOrders.add(order); // O(log n)
        } else {
            sellOrders.add(order); // O(log n)
        }
    }

    private void matchOrders(final Order order) {
        final PriorityQueue<Order> oppositeOrders = (order.getSide() == Side.BUY) ? sellOrders : buyOrders;

        while ((order.getQuantity() > 0)
                && !oppositeOrders.isEmpty()
                && isPriceMatch(order, oppositeOrders.peek())) {
            System.out.printf("Aggressive %s order, matching with %s orders%n",
                              order.getSide() == Side.BUY ? "buy" : "sell",
                              order.getSide() == Side.BUY ? "sell" : "buy");

            final Order oppositeOrder = oppositeOrders.peek(); // O(1)
            final int fillQuantity = Math.min(order.getQuantity(), oppositeOrder.getQuantity());

            System.out.printf("Matched Order: %d @ %.2f%n", fillQuantity, oppositeOrder.getPrice());

            order.setQuantity(order.getQuantity() - fillQuantity);
            oppositeOrder.setQuantity(oppositeOrder.getQuantity() - fillQuantity);

            if (oppositeOrder.getQuantity() == 0) {
                oppositeOrders.poll(); // O(log n)
            }
        }
    }


    private boolean isPriceMatch(final Order order, final Order oppositeOrder) {
        return (order.getSide() == Side.BUY && order.getPrice() >= oppositeOrder.getPrice())
                || (order.getSide() == Side.SELL && order.getPrice() <= oppositeOrder.getPrice());
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
        removeOrder(order); // O(n)
        addOrder(order); // O(log n)
    }

    private void removeOrder(final Order order) {
        orderCache.remove(order.getId()); // O(1)
        if (order.getSide() == Side.BUY) {
            buyOrders.remove(order); // O(n)
        } else {
            sellOrders.remove(order); // O(n)
        }
    }
}
