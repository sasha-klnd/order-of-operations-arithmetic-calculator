/*
 * COMP 352 AA - Data Structures and Algorithms
 * Professor Hakim Mellah
 * Written By: Sasha Klein-Charland (40281076)
 * Programming Assignment 2
 */

import java.util.EmptyStackException;

public class ArrayStack<E> {
    
    private E[] innerStack;
    private int top;

    @SuppressWarnings("unchecked")
    public ArrayStack(int capacity) {
        // User can specify the size
        innerStack = (E[]) new Object[capacity];
        top = -1;
    }

    public int size() {
        return top + 1;
    }

    public E top() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        } else {
            return innerStack[top];
        }
    }
    
    public E pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        } else {
            top--;
            return innerStack[top + 1];
        }
    }

    public void push(E element) {
        if (isFull()) {
            innerStack = expandStack(innerStack);
        }
        top++;
        innerStack[top] = element;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == innerStack.length - 1;
    }

    @SuppressWarnings("unchecked")
    private E[] expandStack(E[] original) {
        // Returns a copy of the original array, with double the length

        E[] newInnerStack = null;
        
        newInnerStack = (E[]) new Object[original.length * 2];

        for (int i = 0; i < original.length; i++) {
            newInnerStack[i] = original[i];
        }

        return newInnerStack;
    }

}
