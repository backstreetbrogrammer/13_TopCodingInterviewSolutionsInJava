package com.backstreetbrogrammer.Q4_Stack;

import java.util.concurrent.atomic.AtomicReference;

public class MyStackUsingCAS<T> implements StackI<T> {
    private final AtomicReference<StackNode<T>> top = new AtomicReference<>();

    @Override
    public T pop() {
        StackNode<T> oldHead;
        StackNode<T> newHead;
        do {
            oldHead = top.get();
            if (oldHead == null) {
                return null;
            }
            newHead = oldHead.getNext();
        } while (!top.compareAndSet(oldHead, newHead));
        return oldHead.getData();
    }

    @Override
    public void push(final T item) {
        final StackNode<T> newHead = new StackNode<>(item);
        StackNode<T> oldHead;
        do {
            oldHead = top.get();
            newHead.setNext(oldHead);
        } while (!top.compareAndSet(oldHead, newHead));
    }

    @Override
    public T peek() {
        if (top.get() == null) {
            return null;
        }
        return top.get().getData();
    }
}
