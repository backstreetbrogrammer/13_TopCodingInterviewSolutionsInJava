package com.backstreetbrogrammer.Q11_MatchingEngine;


import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class OrderBook {
    private final PriorityQueue<Order> buyOrders = new PriorityQueue<>(Comparator.comparingDouble(Order::getPrice)
                                                                                 .reversed()
                                                                                 .thenComparing(Order::getEntryTime));

    private final PriorityQueue<Order> sellOrders = new PriorityQueue<>(Comparator.comparingDouble(Order::getPrice)
                                                                                  .thenComparing(Order::getEntryTime));

    public void addOrder(final Order order) {
        if (order.side == Order.Side.BUY) {
            buyOrders.add(order);
        } else {
            sellOrders.add(order);
        }
        matchOrders();
    }

    public void cancelOrder(final Order.Side side, final int price, final int quantity) {
        final PriorityQueue<Order> orders = (side == Order.Side.BUY) ? buyOrders : sellOrders;
        final Iterator<Order> iterator = orders.iterator();

        while (iterator.hasNext()) {
            final Order order = iterator.next();
            if (order.price == price && order.quantity == quantity) {
                iterator.remove();
                System.out.println("Cancelled Order: " + order);
                break;
            }
        }
    }

    private void matchOrders() {
        while (!buyOrders.isEmpty() && !sellOrders.isEmpty() &&
                buyOrders.peek().price >= sellOrders.peek().price) {
            final Order buyOrder = buyOrders.poll();
            final Order sellOrder = sellOrders.poll();

            final int quantity = Math.min(buyOrder.quantity, sellOrder.quantity);
            buyOrder.quantity -= quantity;
            sellOrder.quantity -= quantity;

            System.out.println("Matched Order: " + quantity + " @ " + sellOrder.price);

            if (buyOrder.quantity > 0) {
                buyOrders.add(buyOrder);
            }
            if (sellOrder.quantity > 0) {
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
