/******************************************************************************
Compilation:  javac Deque.java
Execution:    java Deque input.txt
Dependencies:

The goal of this assignment is to implement elementary data structures using arrays and linked lists,
and to introduce you to generics and iterators.
A double-ended queue or deque (pronounced "deck") is a generalization of a stack and a queue that
supports adding and removing items from either the front or the back of the data structure. 

Corner cases.
Throw a java.lang.NullPointerException if the client attempts to add a null item;
throw a java.util.NoSuchElementException if the client attempts to remove an item from an empty deque;
throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator;
throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and no more items to return.

Performance requirements.
Your deque implementation must support each deque operation in constant worst-case time.
A deque containing N items must use at most 48N + 192 bytes of memory,
and use space proportional to the number of items currently in the deque.
Additionally, the iterator implementation must support each operation (including construction) in constant worst-case time.

******************************************************************************/

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    public Deque() {                             // construct an empty deque
        public boolean isEmpty()                 // is the deque empty?
        public int size()                        // return the number of items on the deque
        public void addFirst(Item item)          // add the item to the front
        public void addLast(Item item)           // add the item to the end
        public Item removeFirst()                // remove and return the item from the front
        public Item removeLast()                 // remove and return the item from the end
        public Iterator<Item> iterator()         // return an iterator over items in order from front to end
        public static void main(String[] args)   // unit testing
    }
}