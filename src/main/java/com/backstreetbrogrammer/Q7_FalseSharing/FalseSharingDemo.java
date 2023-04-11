package com.backstreetbrogrammer.Q7_FalseSharing;

public class FalseSharingDemo {

    public final static class VolatileLongPadded {
        public long q1, q2, q3, q4, q5, q6;
        public volatile long value = 0L;
        public long q11, q12, q13, q14, q15, q16;

    }

    public final static class VolatileLongUnPadded {
        public volatile long value = 0L;
    }

}
