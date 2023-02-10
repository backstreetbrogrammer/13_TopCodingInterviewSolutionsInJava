package com.backstreetbrogrammer.Q2_RandomSampling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomSampling {

    public static List<Integer> randomSampling(final List<Integer> origData, final int sampleSize) {
        final List<Integer> data = new ArrayList<>(List.copyOf(origData));
        for (int i = 0; i < sampleSize; i++) {
            Collections.swap(data, i, i + ThreadLocalRandom.current().nextInt(data.size() - i));
        }
        return data.subList(0, sampleSize);
    }

}
