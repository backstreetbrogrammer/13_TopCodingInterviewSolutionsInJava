package com.backstreetbrogrammer.Q12_MatchingEngine;

public class NumberUtil implements EngineConstants {

    public static int moduloPowerOfTwo(final int v, final int d) {
        return v & (d - 1);
    }

    public static int coalescePriceAndOrderLevelIndex(final int bookPriceLevelIndex, final int priceLevelOrderIndex) {
        return (bookPriceLevelIndex << 15) | (priceLevelOrderIndex);
    }

    public static int getPriceLevelOrderIndex(final int coalescedVal) {
        return coalescedVal & (MAX_ORDERS_AT_EACH_PRICE_LEVEL - 1);
    }

    public static int getBookPriceLevelIndex(final int coalescedVal) {
        return (coalescedVal >> 15) & (MAX_PRICE_LEVELS - 1);
    }

}
