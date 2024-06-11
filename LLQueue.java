/*
 * COMP 352 AA - Data Structures and Algorithms
 * Professor Hakim Mellah
 * Written By: Sasha Klein-Charland (40281076)
 * Programming Assignment 2
 */

import Exceptions.EmptyQueueException;

public class LLQueue<T> {

    private int size;
    private Node<T> header;
    private Node<T> trailer;
    
    private class Node<E> {

        private E element;
        private Node<T> next;
        private Node<T> prev;

        public Node(E element, Node<T> next, Node<T> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }

    }

    public LLQueue() {
        size = 0;
        header = new Node<>(null, null, null);
        trailer = new Node<>(null, null, null);

        header.next = trailer;
        trailer.prev = header;
    }

    public void enqueue(T element) {
        // Add a new node to to the end of the list

        Node<T> newNode = new Node<>(element, null, null);

        newNode.prev = trailer.prev;
        newNode.next = trailer;

        trailer.prev.next = newNode;
        trailer.prev = newNode;
        
        size++;
    }

    public T dequeue() throws EmptyQueueException {
        // Remove and return the element from the node at the front of the list, i.e. header.next

        if (isEmpty()) {
            throw new EmptyQueueException();
        }

        Node<T> temp = header.next;
        temp.next.prev = header;
        header.next = temp.next;

        size--;
        
        return (T) temp.element;
    }

    public T front() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException();
        }
        
        return (T) header.next.element;
    }
    
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void display() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException();
        }

        Node<T> cursor = header;
        T element;

        System.out.print("[");

        while (cursor.next != trailer) {
            cursor = cursor.next;
            element = (T) cursor.element;

            System.out.print(element + ", ");
        }
        System.out.println("]");
    }

}
