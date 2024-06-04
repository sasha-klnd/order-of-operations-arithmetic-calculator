/*
 * COMP 352 AA - Data Structures and Algorithms
 * Professor Hakim Mellah
 * Written By: Sasha Klein-Charland (40281076)
 * Programming Assignment 2
 */

public class LinkedList {
    
    private int size;

    private static class Node<T> {
        T element;
        int index;
        Node<T> next;
        Node<T> prev;

        public Node(T element, int index, Node<T> next, Node<T> prev) {
            this.element = element;
            this.index = index;
            this.next = next;
            this.prev = prev;
        }

        public Node() {
            this(null, 0, null, null);
        }

        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }

        

    }
    
}
