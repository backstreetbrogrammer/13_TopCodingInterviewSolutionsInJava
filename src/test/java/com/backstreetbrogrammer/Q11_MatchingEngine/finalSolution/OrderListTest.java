package com.backstreetbrogrammer.Q11_MatchingEngine.finalSolution;

import com.backstreetbrogrammer.Q11_MatchingEngine.Order;
import com.backstreetbrogrammer.Q11_MatchingEngine.Side;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class OrderListTest {

    private OrderList orderList;

    @BeforeEach
    void setUp() {
        orderList = new OrderList();
    }

    @Test
    void testAddHead() {
        final Order order1 = new Order(1, Side.BUY, 50.5, 100);
        final Order order2 = new Order(2, Side.SELL, 51.0, 200);

        orderList.addHead(order1);
        orderList.addHead(order2);

        assertEquals(2, orderList.getSize());
        assertEquals(300, orderList.getTotalVolume());
        assertEquals(order2, orderList.peekHead());
    }

    @Test
    void testAddTail() {
        final Order order1 = new Order(1, Side.BUY, 50.5, 100);
        final Order order2 = new Order(2, Side.SELL, 51.0, 200);

        orderList.addTail(order1);
        orderList.addTail(order2);

        assertEquals(2, orderList.getSize());
        assertEquals(300, orderList.getTotalVolume());
        assertEquals(order1, orderList.peekHead());
    }

    @Test
    void testGetHead() {
        final Order order1 = new Order(1, Side.BUY, 50.5, 100);
        final Order order2 = new Order(2, Side.SELL, 51.0, 200);

        orderList.addHead(order1);
        orderList.addHead(order2);

        final Order headOrder = orderList.getHead();
        assertEquals(order2, headOrder);
        assertEquals(1, orderList.getSize());
        assertEquals(100, orderList.getTotalVolume());
    }

    @Test
    void testPeekHead() {
        final Order order1 = new Order(1, Side.BUY, 50.5, 100);

        orderList.addHead(order1);

        final Order headOrder = orderList.peekHead();
        assertEquals(order1, headOrder);
        assertEquals(1, orderList.getSize());
        assertEquals(100, orderList.getTotalVolume());
    }

    @Test
    void testIsEmpty() {
        assertTrue(orderList.isEmpty());

        final Order order1 = new Order(1, Side.BUY, 50.5, 100);
        orderList.addHead(order1);

        assertFalse(orderList.isEmpty());
    }

    @Test
    void testGetSize() {
        assertEquals(0, orderList.getSize());

        final Order order1 = new Order(1, Side.BUY, 50.5, 100);
        orderList.addHead(order1);

        assertEquals(1, orderList.getSize());
    }

    @Test
    void testGetTotalVolume() {
        assertEquals(0, orderList.getTotalVolume());

        final Order order1 = new Order(1, Side.BUY, 50.5, 100);
        orderList.addHead(order1);

        assertEquals(100, orderList.getTotalVolume());
    }

    @Test
    void testPrintList() {
        final Order order1 = new Order(1, Side.BUY, 50.5, 100);
        final Order order2 = new Order(2, Side.SELL, 51.0, 200);

        orderList.addHead(order1);
        orderList.addHead(order2);

        // Capture the printed output
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        orderList.printList();

        // Restore the original System.out
        System.setOut(System.out);

        // Verify the printed output
        final String expectedOutput = order2.toString() + System.lineSeparator() +
                order1.toString() + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }
}