package com.backstreetbrogrammer.Q2_RandomSampling;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static com.backstreetbrogrammer.PrintUtils.printIntList;

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

    public List<Integer> randomOnlineSampling(final InputStream is,
                                              final int sampleSize) throws IOException, ClassNotFoundException {
        final List<Integer> runningSample = new ArrayList<>(sampleSize);
        final var ois = new ObjectInputStream(is);

        // assuming at least `sampleSize` elements in the stream
        for (int i = 0; i < sampleSize; i++) {
            final Integer j = (Integer) ois.readObject();
            runningSample.add(j);
        }

        int numSeenSoFar = sampleSize;
        while (true) {
            try {
                final Integer k = (Integer) ois.readObject();
                ++numSeenSoFar;
                final int idToReplace = ThreadLocalRandom.current().nextInt(numSeenSoFar);
                if (idToReplace < sampleSize) {
                    runningSample.set(idToReplace, k);
                }
                TimeUnit.MILLISECONDS.sleep(500L);
                printIntList(runningSample);
            } catch (final EOFException e) {
                break;
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
        ois.close();
        return runningSample;
    }

}
