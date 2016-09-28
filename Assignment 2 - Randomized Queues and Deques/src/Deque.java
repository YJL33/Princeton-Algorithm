/******************************************************************************
Compilation:  javac Deque.java
Execution:  java Deque input.txt
Dependencies:

The goal of this assignment is to implement elementary data structures
using arrays and linked lists, and to introduce you to generics and iterators.
A double-ended queue or deque (pronounced "deck") is a generalization of
a stack and a queue that supports adding and removing items from either the
front or the back of the data structure. 

Corner cases.
Throw a java.lang.NullPointerException
if the client attempts to add a null item;
throw a java.util.NoSuchElementException
if the client attempts to remove an item from an empty deque;
throw a java.lang.UnsupportedOperationException
if the client calls the remove() method in the iterator;
throw a java.util.NoSuchElementException
if the client calls the next() method in the iterator and no more items to return.

Performance requirements.
Your deque implementation must support each operation in constant worst-case time.
A deque containing N items must use at most 48N + 192 bytes of memory,
and use space proportional to the number of items currently in the deque.

The iterator implementation must support all operation in constant worst-case time.

******************************************************************************/

// Using double-linked-list.
// Adding: O(1)
// Removing: O(1)
// isEmpty: O(1)

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        private Item value = null;         // Generic: value can be anything
        private Node next = null;          // pointing toward tail
        private Node prev = null;          // pointing toward head
    }

    private Node head;                // head of dequeue
    private Node tail;                // tail of dequeue
    private int numOfNodes;           // size of dequeue
    
    public Deque() {
        head = null;
        tail = null;
        numOfNodes = 0;
    }                             // construct an empty deque
    
    public boolean isEmpty() {
        return numOfNodes == 0;
    }                             // is the deque empty?
    
    public int size() {
        return numOfNodes;
    }                         // return the number of items on the deque
    
    public void addFirst(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException("Nothing to add");
        Node newhead = new Node();
        newhead.value = item;
        newhead.next = head;
        newhead.prev = null;
        if (head != null) head.prev = newhead;
        head = newhead;
        numOfNodes++;
        if (tail == null) tail = head;

    }         // add the item to the front
    
    public void addLast(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException("Nothing to add");
        Node newtail = new Node();
        newtail.value = item;
        newtail.prev = tail;
        newtail.next = null;
        if (tail != null) tail.next = newtail;
        tail = newtail;
        numOfNodes++;
        if (head == null) head = tail;
    }            // add the item to the end
    
    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException("No more element!");
        Item firstval = head.value;
        if (numOfNodes == 1) {
            head = null;
            tail = null;
        }
        else {
            head = head.next;
            head.prev = null;
        }
        numOfNodes--;
        return firstval;
    }             // remove and return the item from the front
    
    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException("No more element!");
        Item lastval = tail.value;
        if (numOfNodes == 1) {
            head = null;
            tail = null;
        }
        else {
            tail = tail.prev;
            tail.next = null;
        }
        numOfNodes--;
        return lastval;
    }        // remove and return the item from the end
 
    public Iterator<Item> iterator() {
        return new DequeIter();
    }        // return an iterator over items in order from front to end
    
    private class DequeIter implements Iterator<Item> {
        private Node currentNode = head;
        @Override
        public boolean hasNext() {
            return currentNode != null;
        }
        @Override
        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException("No more element!");
            Item res = currentNode.value;
            currentNode = currentNode.next;
            return res;
        }
        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException("Unsupported operation");
        }
    }
}
