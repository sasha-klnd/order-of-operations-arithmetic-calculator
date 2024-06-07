/*
 * COMP 352 AA - Data Structures and Algorithms
 * Professor Hakim Mellah
 * Written By: Sasha Klein-Charland (40281076)
 * Programming Assignment 2
 */

import java.util.EmptyStackException;

public class ArrayStack {
    
    private char[] innerStack;
    private int top;

    public ArrayStack(int capacity) {
        // User can specify the size
        innerStack = new char[capacity  ];
        top = -1;
    }

    public int size() {
        return top + 1;
    }

    public char top() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        } else {
            return innerStack[top];
        }
    }
    
    public char pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        } else {
            top--;
            return innerStack[top + 1];
        }
    }

    public void push(char c) {
        if (isFull()) {
            innerStack = expandStack(innerStack);
        }
        top++;
        innerStack[top] = c;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == innerStack.length - 1;
    }

    private char[] expandStack(char[] original) {
        // Returns a copy of the original array, with double the length

        char[] newInnerStack = new char[original.length * 2];
        for (int i = 0; i < original.length; i++) {
            newInnerStack[i] = original[i];
        }

        return newInnerStack;
    }

}
