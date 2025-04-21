package com.backstreetbrogrammer;

import java.text.DecimalFormat;
import java.util.List;

public class PrintUtils {

    private PrintUtils() {
    }

    public static void printIntArray(final int[] array) {
        if (array == null || array.length == 0) return;
        for (final int ele : array) {
            System.out.printf("%d, ", ele);
        }
        System.out.println();
    }

    public static void printIntList(final List<Integer> list) {
        if (list == null || list.size() == 0) return;
        for (final int ele : list) {
            System.out.printf("%d, ", ele);
        }
        System.out.println();
    }

    public static String withLargeIntegers(final double value) {
        final DecimalFormat df = new DecimalFormat("###,###,###");
        return df.format(value);
    }

    // withLargeIntegers(123456789) = 123,456,789
}
