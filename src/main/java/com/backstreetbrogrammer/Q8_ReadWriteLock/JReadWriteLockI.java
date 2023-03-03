package com.backstreetbrogrammer.Q8_ReadWriteLock;

public interface JReadWriteLockI {

    void lockRead() throws InterruptedException;

    void unlockRead();

    void lockWrite() throws InterruptedException;

    void unlockWrite();

}
