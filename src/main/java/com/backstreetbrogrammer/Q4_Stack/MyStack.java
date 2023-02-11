package com.backstreetbrogrammer.Q4_Stack;

import java.util.EmptyStackException;

public class MyStack<T> implements StackI<T> {
    private StackNode<T> top;

    @Override
    public T pop() {
        if (top == null) throw new EmptyStackException();
        final T item = top.getData();
        top = top.getNext();
        return item;
    }

    @Override
    public void push(final T item) {
        final StackNode<T> node = new StackNode<>(item);
        node.setNext(top);
        top = node;
    }

    @Override
    public T peek() {
        if (top == null) throw new EmptyStackException();
        return top.getData();
    }
}
