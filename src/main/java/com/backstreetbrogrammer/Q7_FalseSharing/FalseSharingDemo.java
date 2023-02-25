package com.backstreetbrogrammer.Q7_FalseSharing;

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
}
