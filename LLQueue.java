/*
 * COMP 352 AA - Data Structures and Algorithms
 * Professor Hakim Mellah
 * Written By: Sasha Klein-Charland (40281076)
 * Programming Assignment 2
 */

public class LLQueue<T> {

    private int size;
    private Node header;
    private Node trailer;
    
    private class Node<T> {

        private T element;
        private Node next;
        private Node prev;

        public Node(T element, Node next, Node prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }

    }

    public LLQueue() {
        size = 0;
        header = new Node(null, null, null);
        trailer = new Node(null, null, null);

        header.next = trailer;
        trailer.prev = header;
    }

    public void enqueue(T element) {
        // Add a new node to to the end of the list

        Node newNode = new Node(element, null, null);

        newNode.prev = trailer.prev;
        newNode.next = trailer;

        trailer.prev.next = newNode;
        trailer.prev = newNode;
        
        size++;
    }

    public T dequeue() {
        // Remove and return the node at the front of the list, i.e. header.next

        Node temp = header.next;
        temp.next.prev = header;
        header.next = temp.next;

        size--;
        
        return (T) temp.element;
    }

    public T front() {
        return (T) header.next.element;
    }
    
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

}
