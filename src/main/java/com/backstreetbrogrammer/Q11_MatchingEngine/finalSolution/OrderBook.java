package com.backstreetbrogrammer.Q11_MatchingEngine.finalSolution;


import com.backstreetbrogrammer.Q11_MatchingEngine.Order;
import com.backstreetbrogrammer.Q11_MatchingEngine.OrderBookI;
import com.backstreetbrogrammer.Q11_MatchingEngine.Side;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class OrderBook implements OrderBookI {

    // max-heap for buy orders (the highest price first)
    private final PriorityQueue<Double> buyPriceLevels = new PriorityQueue<>(Comparator.reverseOrder());

    // min-heap for sell orders (the lowest price first)
    private final PriorityQueue<Double> sellPriceLevels = new PriorityQueue<>();

    private final Map<Double, OrderList> ordersAtPrice = new HashMap<>();


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
        final double price = order.getPrice();
        if (order.getSide() == Side.BUY) {
            buyPriceLevels.add(price); // O(log n)
        } else {
            sellPriceLevels.add(price); // O(log n)
        }
        ordersAtPrice.computeIfAbsent(price, p -> new OrderList()).addTail(order);
    }

    private boolean isPriceMatch(final Order order, final PriorityQueue<Double> oppositeSidePriceLevel) {
        if (oppositeSidePriceLevel == null || oppositeSidePriceLevel.peek() == null) {
            return false; // No price level to match
        }
        final double topPrice = oppositeSidePriceLevel.peek(); // Safe unboxing
        return (order.getSide() == Side.BUY && order.getPrice() >= topPrice)
                || (order.getSide() == Side.SELL && order.getPrice() <= topPrice);
    }


    private void matchOrders(final Order order) {
        final PriorityQueue<Double> oppositeSidePriceLevel =
                (order.getSide() == Side.BUY) ? sellPriceLevels : buyPriceLevels;

        while (isPriceMatch(order, oppositeSidePriceLevel)) {
            final double matchPrice = oppositeSidePriceLevel.peek();
            final OrderList oppositeOrders = ordersAtPrice.get(matchPrice);
            if (oppositeOrders == null || oppositeOrders.isEmpty()) {
                oppositeSidePriceLevel.poll(); // Remove empty price level
                continue;
            }

            final Order oppositeOrder = oppositeOrders.getHead();
            final int fillQty = Math.min(order.getQuantity(), oppositeOrder.getQuantity());
            order.setQuantity(order.getQuantity() - fillQty);
            oppositeOrder.setQuantity(oppositeOrder.getQuantity() - fillQty);

            System.out.printf("Matched Order: %d @ %.2f%n", fillQty, matchPrice);

            if (oppositeOrder.getQuantity() > 0) {
                oppositeOrders.addHead(oppositeOrder); // Re-add to the front
            } else {
                orderCache.remove(oppositeOrder.getId()); // Remove fully filled order from cache
            }

            if (oppositeOrders.isEmpty()) {
                ordersAtPrice.remove(matchPrice);
                oppositeSidePriceLevel.poll(); // Remove empty price level
            }

            if (order.getQuantity() == 0) {
                orderCache.remove(order.getId()); // Remove fully filled order from cache
                break; // Order fully filled
            }
        }

    }

    @Override
    public void printLevel1() {
        System.out.println("--------------------------");

        System.out.println("BEST ASK~>");
        if (!sellPriceLevels.isEmpty()) {
            final double bestAskPrice = sellPriceLevels.peek();
            System.out.printf("%.2f%n", bestAskPrice);
            final OrderList ordersAtBestAsk = ordersAtPrice.get(bestAskPrice);
            if (ordersAtBestAsk != null) {
                System.out.printf("TotalVolume=%d, TotalOrders=%d%n",
                                  ordersAtBestAsk.getTotalVolume(),
                                  ordersAtBestAsk.getSize());
            }
        }

        System.out.println("--------------------------");

        System.out.println("BEST BID~>");
        if (!buyPriceLevels.isEmpty()) {
            final double bestBidPrice = buyPriceLevels.peek();
            System.out.printf("%.2f%n", bestBidPrice);
            final OrderList ordersAtBestBid = ordersAtPrice.get(bestBidPrice);
            if (ordersAtBestBid != null) {
                System.out.printf("TotalVolume=%d, TotalOrders=%d%n",
                                  ordersAtBestBid.getTotalVolume(),
                                  ordersAtBestBid.getSize());
            }
        }

        System.out.println("--------------------------");
    }

    @Override
    public void printOrderBook() {
        System.out.println("--------------------------");

        System.out.println("SELL Orders:");
        if (!sellPriceLevels.isEmpty()) {
            for (final Double price : sellPriceLevels) {
                final OrderList ordersAtPriceLevel = ordersAtPrice.get(price);
                if (ordersAtPriceLevel != null) {
                    System.out.printf("Price: %.2f, TotalVolume: %d, TotalOrders: %d%n",
                                      price, ordersAtPriceLevel.getTotalVolume(), ordersAtPriceLevel.getSize());
                    for (final Order order : ordersAtPriceLevel.getOrders()) {
                        System.out.printf("  OrderID: %d, Qty: %d, EntryTime: %s%n",
                                          order.getId(), order.getQuantity(), order.getEntryTime());
                    }
                }
            }
        }

        System.out.println("--------------------------");

        System.out.println("BUY Orders:");
        if (!buyPriceLevels.isEmpty()) {
            for (final Double price : buyPriceLevels) {
                final OrderList ordersAtPriceLevel = ordersAtPrice.get(price);
                if (ordersAtPriceLevel != null) {
                    System.out.printf("Price: %.2f, TotalVolume: %d, TotalOrders: %d%n",
                                      price, ordersAtPriceLevel.getTotalVolume(), ordersAtPriceLevel.getSize());
                    for (final Order order : ordersAtPriceLevel.getOrders()) {
                        System.out.printf("  OrderID: %d, Qty: %d, EntryTime: %s%n",
                                          order.getId(), order.getQuantity(), order.getEntryTime());
                    }
                }
            }
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
            System.out.printf("Order ID not found: %d%n", orderId);
        }
    }

    private void removeOrder(final Order order) {
        orderCache.remove(order.getId());
        if (order.getSide() == Side.BUY) {
            removeOrder(buyPriceLevels, order);
        } else {
            removeOrder(sellPriceLevels, order);
        }
    }

    private void removeOrder(final PriorityQueue<Double> priceLevels, final Order order) {
        final double price = order.getPrice();
        final OrderList ordersAtPriceLevel = ordersAtPrice.get(price); // O(1)

        if (ordersAtPriceLevel != null) {
            ordersAtPriceLevel.removeOrder(order); // Remove the order from the list
            if (ordersAtPriceLevel.isEmpty()) {
                ordersAtPrice.remove(price); // Remove the price level if no orders remain
                priceLevels.remove(price); // Remove the price from the priority queue
            }
        }
    }

    private void removeAndAddOrder(final Order order) {
        removeOrder(order);
        addOrderToOrderBook(order);
    }

    @Override
    public void amendOrder(final int orderId, final int qtyNew) {
        // Pre-conditions
        if (!orderCache.containsKey(orderId) || qtyNew <= 0) {
            throw new IllegalArgumentException("Order does not exist or new qty is wrong");
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
}
