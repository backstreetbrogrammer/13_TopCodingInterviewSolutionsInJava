package com.backstreetbrogrammer.Q7_FalseSharing;

import java.util.concurrent.atomic.AtomicLong;

public class FalseSharingDemo {

    private volatile long a;
    private volatile long b;

    public long getA() {
        return a;
    }

    public void setA(final long a) {
        this.a = a;
    }

    public long getB() {
        return b;
    }

    public void setB(final long b) {
        this.b = b;
    }

    public final static class VolatileLong {
        public volatile long value = 0L;
        //public long p1, p2, p3, p4, p5, p6;
    }

    public final static class PaddedAtomicLong extends AtomicLong {
        public volatile long p1, p2, p3, p4, p5, p6 = 7L;
    }
}
