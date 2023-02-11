package com.backstreetbrogrammer.Q4_Stack;

import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyStackUsingLocks<T> implements StackI<T> {
    private final Stack<T> stack;
    private final int capacity;

    private final Lock lock;
    private final Condition stackEmptyCondition;
    private final Condition stackFullCondition;

    public MyStackUsingLocks(final int capacity) {
        this.capacity = capacity;
        stack = new Stack<>();

        lock = new ReentrantLock();
        stackEmptyCondition = lock.newCondition();
        stackFullCondition = lock.newCondition();
    }

    @Override
    public T pop() {
        try {
            lock.lock();
            while (stack.size() == 0) {
                stackEmptyCondition.await();
            }
            final T item = stack.pop();
            stackFullCondition.signalAll();
            return item;
        } catch (final InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public void push(final T item) {
        try {
            lock.lock();
            while (stack.size() == capacity) {
                stackFullCondition.await();
            }
            stack.push(item);
            stackEmptyCondition.signalAll();
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
                stackEmptyCondition.await();
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
