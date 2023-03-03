package com.backstreetbrogrammer.Q8_ReadWriteLock;

public class JReadWriteLock implements JReadWriteLockI {

    private int readers;
    private int writers;
    private int writeRequests;

    @Override
    public synchronized void lockRead() throws InterruptedException {
        while (writers > 0 || writeRequests > 0) {
            wait();
        }
        readers++;
    }

    @Override
    public synchronized void unlockRead() {
        readers--;
        notifyAll();
    }

    @Override
    public synchronized void lockWrite() throws InterruptedException {
        writeRequests++;

        while (readers > 0 || writers > 0) {
            wait();
        }
        writeRequests--;
        writers++;
    }

    @Override
    public synchronized void unlockWrite() {
        writers--;
        notifyAll();
    }

}
