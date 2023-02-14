package com.backstreetbrogrammer.Q2_RandomSampling;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomSampling {

    private final List<Integer> data;
    private final int sampleSize;

    public RandomSampling(final List<Integer> data, final int sampleSize) {
        this.data = data;
        this.sampleSize = sampleSize;
    }

    public List<Integer> randomSamplingUsingShuffle(final Random random) {
        if (random == null) {
            Collections.shuffle(data);
        } else {
            Collections.shuffle(data, random);
        }
        return data.subList(0, sampleSize);
    }

    public List<Integer> randomSamplingUsingSwap() {
        for (int i = 0; i < sampleSize; i++) {
            Collections.swap(data, i, i + ThreadLocalRandom.current().nextInt(data.size() - i));
        }
        return data.subList(0, sampleSize);
    }

}
