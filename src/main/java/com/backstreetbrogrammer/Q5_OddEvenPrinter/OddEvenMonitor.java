package com.backstreetbrogrammer.Q5_OddEvenPrinter;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class OddEvenMonitor {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition toggleTurnCondition = lock.newCondition();

    private boolean turn = true;

    public void waitTurn(final boolean oldTurn) {
        try {
            lock.lock();
            while (turn != oldTurn) { // even thread will wait here for the first time
                toggleTurnCondition.await();
            }
        } catch (final InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void toggleTurn() {
        try {
            lock.lock();
            turn ^= true;
        } finally {
            toggleTurnCondition.signalAll();
            lock.unlock();
        }
    }
}
