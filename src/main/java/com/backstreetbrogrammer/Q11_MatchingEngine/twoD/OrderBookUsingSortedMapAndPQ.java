package com.backstreetbrogrammer.Q11_MatchingEngine.twoD;


import com.backstreetbrogrammer.Q11_MatchingEngine.Order;
import com.backstreetbrogrammer.Q11_MatchingEngine.OrderBookI;
import com.backstreetbrogrammer.Q11_MatchingEngine.Side;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class OrderBookUsingSortedMapAndPQ implements OrderBookI {
    private final Map<Integer, Order> orderCache = new HashMap<>();
    private final NavigableMap<Double, PriceLevelPQ> buyPriceLevels = new TreeMap<>();
    private final NavigableMap<Double, PriceLevelPQ> sellPriceLevels = new TreeMap<>();

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

        addOrderAndMatch(order);
    }

    private void addOrderAndMatch(final Order order) {
        // Add the order
        orderCache.put(order.getId(), order);
        final double price = order.getPrice();
        if (order.getSide() == Side.BUY) {
            buyPriceLevels.computeIfAbsent(price, pl -> new PriceLevelPQ(price)).addOrder(order);
        } else {
            sellPriceLevels.computeIfAbsent(price, pl -> new PriceLevelPQ(price)).addOrder(order);
        }

        // Check for match
        matchOrders();
    }


    private void matchOrders() {
        while (!buyPriceLevels.isEmpty() && !sellPriceLevels.isEmpty() &&
                Double.compare(buyPriceLevels.lastKey(), sellPriceLevels.firstKey()) >= 0) {
            final Map.Entry<Double, PriceLevelPQ> buyEntry = buyPriceLevels.pollLastEntry(); // O(1)
            final Map.Entry<Double, PriceLevelPQ> sellEntry = sellPriceLevels.pollFirstEntry(); // O(1)

            final PriceLevelPQ buyPriceLevel = buyEntry.getValue();
            final PriceLevelPQ sellPriceLevel = sellEntry.getValue();

            final Order buyOrder = buyPriceLevel.pollBestOrder();
            final Order sellOrder = sellPriceLevel.pollBestOrder();

            final int buyQty = buyOrder.getQuantity();
            final int sellQty = sellOrder.getQuantity();

            final int fillQty = Math.min(buyQty, sellQty);
            buyOrder.setQuantity(buyQty - fillQty);
            sellOrder.setQuantity(sellQty - fillQty);

            System.out.println("Matched Order: " + fillQty + " @ " + sellOrder.getPrice());

            if (buyOrder.getQuantity() > 0) {
                buyPriceLevel.addOrder(buyOrder);
                buyPriceLevels.put(buyEntry.getKey(), buyPriceLevel);
            }
            if (sellOrder.getQuantity() > 0) {
                sellPriceLevel.addOrder(sellOrder);
                sellPriceLevels.put(sellEntry.getKey(), sellPriceLevel);
            }
            if (!buyPriceLevel.isEmpty()) {
                buyPriceLevels.putIfAbsent(buyEntry.getKey(), buyPriceLevel);
            }
            if (!sellPriceLevel.isEmpty()) {
                sellPriceLevels.putIfAbsent(sellEntry.getKey(), sellPriceLevel);
            }
        }
    }

    @Override
    public void printLevel1() {
        System.out.println("--------------------------");

        System.out.println("BEST ASK~>");
        if (!sellPriceLevels.isEmpty()) {
            System.out.printf("%s%n", sellPriceLevels.firstEntry().getValue().getLevel1());
        }

        System.out.println("--------------------------");

        System.out.println("BEST BID~>");
        if (!buyPriceLevels.isEmpty()) {
            System.out.printf("%s%n", buyPriceLevels.lastEntry().getValue().getLevel1());
        }

        System.out.println("--------------------------");
    }

    @Override
    public void printOrderBook() {
        // Level III
        System.out.println("--------------------------");

        System.out.println("SELL Orders:");
        if (!sellPriceLevels.isEmpty()) {
            System.out.println(getPriceLevelsString(sellPriceLevels));
        }

        System.out.println("--------------------------");

        System.out.println("\nBUY Orders:");
        if (!buyPriceLevels.isEmpty()) {
            System.out.println(getPriceLevelsString(buyPriceLevels));
        }

        System.out.println("--------------------------");
    }

    private String getPriceLevelsString(final NavigableMap<Double, PriceLevelPQ> priceLevels) {
        final StringBuilder builder = new StringBuilder();
        for (final Double price : priceLevels.descendingKeySet()) {
            builder.append("\nPrice: ")
                   .append(price)
                   .append(", ");
            final PriceLevelPQ priceLevel = priceLevels.get(price);
            builder.append("TotalVolume=")
                   .append(priceLevel.getTotalVolume())
                   .append(", ")
                   .append("TotalOrders=")
                   .append(priceLevel.totalOrders())
                   .append(", ")
                   .append("Orders=");
            for (final Order order : priceLevel.getOrders()) {
                builder.append("[")
                       .append("id=")
                       .append(order.getId())
                       .append(", qty=")
                       .append(order.getQuantity())
                       .append(", entryTime=")
                       .append(order.getEntryTime())
                       .append("] ");
            }
        }
        return builder.toString();
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

    private void removeOrder(final NavigableMap<Double, PriceLevelPQ> priceLevels, final Order order) {
        final PriceLevelPQ priceLevel = priceLevels.get(order.getPrice());
        if (priceLevel != null && !priceLevel.isEmpty()) {
            priceLevel.removeOrder(order);
        }
        if (priceLevel != null && priceLevel.isEmpty()) {
            priceLevels.remove(order.getPrice());
        }
    }

    private void removeAndAddOrder(final Order order) {
        removeOrder(order);
        addOrderAndMatch(order);
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
