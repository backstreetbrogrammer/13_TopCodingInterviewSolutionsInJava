package com.backstreetbrogrammer.Q11_MatchingEngine.finalSolution;

import com.backstreetbrogrammer.Q11_MatchingEngine.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderList {
    private static class Node {
        Order order;
        Node prev;
        Node next;

        Node(final Order order) {
            this.order = order;
        }
    }

    private Node head;
    private Node tail;
    private int size;
    private int totalVolume;

    public void addHead(final Order order) {
        final Node newNode = new Node(order);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        totalVolume += order.getQuantity();
        size++;
    }

    public void addTail(final Order order) {
        final Node newNode = new Node(order);
        if (tail == null) {
            head = tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        totalVolume += order.getQuantity();
        size++;
    }

    public Order getHead() {
        if (head == null) {
            return null; // List is empty
        }
        final Order order = head.order;
        head = head.next;
        if (head != null) {
            head.prev = null;
        } else {
            tail = null; // List becomes empty
        }
        totalVolume -= order.getQuantity();
        size--;
        return order;
    }

    public void removeOrder(final Order order) {
        if (order == null || head == null) {
            return; // No order to remove
        }

        Node current = head;
        while (current != null) {
            if (current.order.equals(order)) {
                removeNode(current); // Reuse the removeNode method
                return;
            }
            current = current.next;
        }
    }

    private void removeNode(final Node node) {
        if (node == null) {
            return;
        }

        // Update the total volume and size
        totalVolume -= node.order.getQuantity();
        size--;

        // If the node is the head
        if (node == head) {
            head = head.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null; // List becomes empty
            }
        }
        // If the node is the tail
        else if (node == tail) {
            tail = tail.prev;
            if (tail != null) {
                tail.next = null;
            } else {
                head = null; // List becomes empty
            }
        }
        // If the node is in the middle
        else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    public Order peekHead() {
        return (head != null) ? head.order : null;
    }

    public List<Order> getOrders() {
        final List<Order> orders = new ArrayList<>();
        Node current = head;
        while (current != null) {
            orders.add(current.order);
            current = current.next;
        }
        return orders;
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
        Node current = head;
        while (current != null) {
            System.out.println(current.order);
            current = current.next;
        }
    }
}
