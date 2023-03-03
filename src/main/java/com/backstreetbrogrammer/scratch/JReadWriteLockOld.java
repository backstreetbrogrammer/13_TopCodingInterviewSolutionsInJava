package com.backstreetbrogrammer.scratch;

public class JReadWriteLockOld {

    private static final Object READ_LOCK = new Object();
    private static final Object WRITE_LOCK = new Object();

    private static String DATA = "Hello BackstreetBrogrammer!";
    private static int readCount = 0;

    public static final class Reader extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (READ_LOCK) {
                    readCount++;
                }
                System.out.printf("Reader Thread [%s] reads the DATA: [%s]%n", Thread.currentThread().getName(), DATA);
                synchronized (READ_LOCK) {
                    readCount--;
                    READ_LOCK.notify();
                }
            }
        }
    }

    public static final class Writer extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (WRITE_LOCK) {
                    boolean done = false;
                    while (!done) {
                        synchronized (READ_LOCK) {
                            if (readCount == 0) {
                                DATA = "Hello BackstreetBrogrammer! Write";
                                done = true;
                            } else {
                                // there is a Reader reading the data now
                                try {
                                    while (readCount != 0) {
                                        READ_LOCK.wait();
                                    }
                                } catch (final InterruptedException e) {
                                    System.err.println("InterruptedException in Writer wait()");
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
