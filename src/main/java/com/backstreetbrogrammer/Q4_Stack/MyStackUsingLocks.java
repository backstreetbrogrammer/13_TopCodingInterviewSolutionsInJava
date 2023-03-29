package com.backstreetbrogrammer.Q4_Stack;

import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyStackUsingLocks<T> implements StackI<T> {
    private final Stack<T> stack;
    private final int capacity;

    private final Lock lock;
    private final Condition stackNotEmptyCondition;
    private final Condition stackNotFullCondition;

    public MyStackUsingLocks(final int capacity) {
        this.capacity = capacity;
        stack = new Stack<>();

        lock = new ReentrantLock();
        stackNotEmptyCondition = lock.newCondition();
        stackNotFullCondition = lock.newCondition();
    }

    @Override
    public T pop() {
        try {
            lock.lock();
            while (stack.size() == 0) {
                stackNotEmptyCondition.await();
            }
            return stack.pop();
        } catch (final InterruptedException e) {
            e.printStackTrace();
        } finally {
            stackNotFullCondition.signalAll();
            lock.unlock();
        }
        return null;
    }

    @Override
    public void push(final T item) {
        try {
            lock.lock();
            while (stack.size() == capacity) {
                stackNotFullCondition.await();
            }
            stack.push(item);
            stackNotEmptyCondition.signalAll();
        } catch (final InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public T peek() {
        try {
            lock.lock();
            while (stack.size() == 0) {
                stackNotEmptyCondition.await();
            }
            return stack.peek();
        } catch (final InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }
}
