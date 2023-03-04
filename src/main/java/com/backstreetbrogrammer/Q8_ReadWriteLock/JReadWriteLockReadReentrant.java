package com.backstreetbrogrammer.Q8_ReadWriteLock;

import java.util.HashMap;
import java.util.Map;

public class JReadWriteLockReadReentrant implements JReadWriteLockI {

    private final Map<Thread, Integer> readingThreads = new HashMap<>();
    private int writers;
    private int writeRequests;

    @Override
    public synchronized void lockRead() throws InterruptedException {
        final var callingThread = Thread.currentThread();
        while (!canGrantReadAccess(callingThread)) {
            wait();
        }
        readingThreads.merge(callingThread, 1, Integer::sum);
    }

    @Override
    public synchronized void unlockRead() {
        final var callingThread = Thread.currentThread();
        final int accessCount = readingThreads.get(callingThread);
        if (accessCount == 1) {
            readingThreads.remove(callingThread);
        } else {
            readingThreads.put(callingThread, accessCount - 1);
        }
        notifyAll();
    }

    @Override
    public synchronized void lockWrite() throws InterruptedException {
        writeRequests++;
        final var readers = readingThreads.get(Thread.currentThread());
        while (((readers != null) && (readers > 0)) || writers > 0) {
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

    private boolean canGrantReadAccess(final Thread callingThread) {
        if (writers > 0) return false;
        if (readingThreads.containsKey(callingThread)) return true;
        return writeRequests <= 0;
    }

}
