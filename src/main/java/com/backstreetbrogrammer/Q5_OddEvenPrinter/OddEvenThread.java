package com.backstreetbrogrammer.Q5_OddEvenPrinter;

public class OddEvenThread implements Runnable {
    private final OddEvenMonitor monitor;
    private final boolean isOdd;

    public OddEvenThread(final OddEvenMonitor monitor, final boolean isOdd) {
        this.monitor = monitor;
        this.isOdd = isOdd;
    }

    @Override
    public void run() {
        int i = 1;
        if (!isOdd) {
            i = 2;
        }
        for (; i <= 100; i += 2) {
            monitor.waitTurn(isOdd);
            System.out.println("i = " + i);
            monitor.toggleTurn();
        }
    }
}
