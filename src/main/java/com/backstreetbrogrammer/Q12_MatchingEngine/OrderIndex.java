package com.backstreetbrogrammer.Q12_MatchingEngine;

import java.util.HashMap;
import java.util.Map;

public class OrderIndex implements EngineConstants{

    final Map<Integer, Integer> orderIndex = new HashMap<>(MAX_ORDERS_IN_BOOK / 4);

    public void addIndex(int orderId, Index idx) {
        orderIndex.put(orderId, idx.coalesceValue());
    }

    public Index getIndex(int orderId) {
        final int val = orderIndex.get(orderId);
        return new Index(val);
    }

    public Index removeIndex(int orderId) {
        final int val = orderIndex.remove(orderId);
        return new Index(val);
    }

    public static class Index {
        int bookPriceLevelIndex;
        int priceLevelOrderIndex;

        public Index(int pricelevelIndex, int oIdx) {
            this.bookPriceLevelIndex = pricelevelIndex;
            this.priceLevelOrderIndex = oIdx;
        }

        public Index(int val) {
            this.bookPriceLevelIndex = NumberUtil.getBookPriceLevelIndex(val);
            this.priceLevelOrderIndex = NumberUtil.getPriceLevelOrderIndex(val);
        }

        public int coalesceValue() {
            return NumberUtil.coalescePriceAndOrderLevelIndex(bookPriceLevelIndex, priceLevelOrderIndex);
        }
    }
}
