package com.backstreetbrogrammer.Q12_MatchingEngine;

import it.unimi.dsi.fastutil.doubles.Double2IntMap;
import it.unimi.dsi.fastutil.doubles.Double2IntRBTreeMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import static com.backstreetbrogrammer.Q12_MatchingEngine.NumberUtil.moduloPowerOfTwo;

public class OrderBook implements EngineConstants {
    final Side side;
    int start = -1;
    int end = -1;
    final PriceLevel[] priceLevels = new PriceLevel[MAX_PRICE_LEVELS];
    final TreeMap<Double, Integer> priceLevelIndexMap;

    public OrderBook(Side side) {
        this.side = side;
        this.priceLevelIndexMap = new TreeMap<>();
        init();
    }

    private void init() {
        for (int i = 0; i < MAX_PRICE_LEVELS; i++) {
            priceLevels[i] = new PriceLevel(side);
        }
    }

    private void reset() {
        start = end = -1;
    }

    public List<Trade> matchOrder(int orderId, Order order) {

        if (side == Side.BUY) {
            if (order.isBuy()) return Collections.emptyList();
        } else {
            if (!order.isBuy()) return Collections.emptyList();
        }

        if (priceLevelIndexMap.isEmpty()) {
            return Collections.emptyList();
        }
        final List<Trade> trades = new ArrayList<>();
        do {
            final Double2IntMap.Entry entry = priceLevelIndexMap.double2IntEntrySet().first();
            final double price = entry.getDoubleKey();
            final int index = entry.getIntValue();
            if (!order.canExecute(price)) {
                return trades;
            }
            final PriceLevel priceLevel = priceLevels[index];
            priceLevel.matchOrder(orderId, order, trades);
            if (priceLevel.isEmpty()) {
                priceLevelIndexMap.remove(price);
            }
            if(order.isComplete()){
                return trades;
            }
        } while (!priceLevelIndexMap.isEmpty());
        return trades;
    }

    public OrderIndex.Index onNewOrder(int orderId, Order order) {
        final double price = order.getPrice();
        final int priceLevelIdx = priceLevelIndexMap.getOrDefault(price, -1);
        if (priceLevelIdx != -1) {
            return newOrder(orderId, order, priceLevelIdx);
        } else {
            if (start == -1) {
                start = 0;
                end = 0;
                int idx = start;
                priceLevelIndexMap.put(price, idx);
                return newOrder(orderId, order, idx);
            }

            final int idx = moduloPowerOfTwo(end + 1, MAX_PRICE_LEVELS);
            if (idx == start) {
                throw new RuntimeException("All available price levels are allotted ");
            }
            end = idx;
            priceLevelIndexMap.put(price, idx);
            return newOrder(orderId, order, idx);
        }
    }

    private OrderIndex.Index newOrder(int orderId, Order order, int priceLevelIdx) {
        final PriceLevel priceLevel = priceLevels[priceLevelIdx];
        final int oIdx = priceLevel.newOrder(orderId, order);
        return new OrderIndex.Index(priceLevelIdx, oIdx);
    }

}
