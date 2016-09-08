/******************************************************************************
Compilation:  javac Deque.java
Execution:  java Deque input.txt
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

// Using double-linked-list.
// Adding: O(1)
// Removing: O(1)
// isEmpty: O(1)

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
  private class Node {
    private Item value;     // Generic: value can be anything
    private Node next;      // pointing toward tail
    private Node prev;      // pointing toward head
  }
  private Node head;        // head of dequeue
  private Node tail;        // tail of dequeue
  private int size;         // size of dequeue
  public Deque() {
    head = null;
    tail = null;
    size = 0;
  }               // construct an empty deque
  public boolean isEmpty() {
    return size == 0;
  }               // is the deque empty?
  public int size() {
    return size;
  }             // return the number of items on the deque
  public void addFirst(Item item) {
    if (item == null)
      throw new java.lang.NullPointerException();
    if (isEmpty()) {
      head = new Node();
      head.value = item;
      tail = head;
      size++;
    }
    else {
      Node oldhead = head;
      head = new Node();
      head.value = item;
      head.next = oldhead;
      oldhead.prev = head;
      size++;
    }
    return;
  }     // add the item to the front
  public void addLast(Item item) {
    if (item == null)
      throw new java.lang.NullPointerException();
    if (isEmpty()) {
      head = new Node();
      head.value = item;
      tail = head;
      size++;
    }
    else {
      Node newtail = new Node();
      newtail.value = item;
      newtail.prev = tail;
      tail.next = newtail;
      tail = newtail;
      size++;
    }
    return;
  }      // add the item to the end
  public Item removeFirst() {
    if (isEmpty()) {
      throw new java.util.NoSuchElementException();
    }
    Item firstval = head.value;
    if (size == 1) {
      head.value = null;
      head.prev = null;
      head.next = null;
      tail = head;
      size--;
    }
    else {
      Node newhead = head.next;
      head.next = null;
      head.prev = null;
      head.value = null;
      size--;
      head = newhead;
    }
    return firstval;
  }       // remove and return the item from the front
  public Item removeLast() {
    if (isEmpty()) {
      throw new java.util.NoSuchElementException();
    }
    Item lastval = tail.value;
    if (size == 1) {
      head.value = null;
      head.prev = null;
      head.next = null;
      tail = head;
      size--;
    }
    else {
      Node newtail = tail.prev;
      tail.next = null;
      tail.prev = null;
      tail.value = null;
      size--;
      tail = newtail;
    }
    return lastval;
  }        // remove and return the item from the end
  public Iterator<Item> iterator() {
    return new DequeIter();
  }    // return an iterator over items in order from front to end
  private class DequeIter implements Iterator<Item> {
    public boolean hasNext() {
      return size > 1;
    }
    public Item next() {
      if (isEmpty())
        throw new java.util.NoSuchElementException();
      return removeFirst();
    }
    public void remove() {
      throw new java.lang.UnsupportedOperationException();
    }
  }
  public static void main(String[] args) {
    Deque<String> dq = new Deque<String>();
    System.out.println("is dq empty? " + dq.isEmpty());
    String a = new String();
    a = "a";
    dq.addFirst(a);
    System.out.println("addFirst(a), now is dq empty? " + dq.isEmpty());
    System.out.println("dq's head:  " + dq.head.value);
    System.out.println("dq's tail:  " + dq.tail.value);
    String b = new String();
    b = "b";
    dq.addLast(b);
    System.out.print("addLast(b), dq's head = " + dq.head.value);
    System.out.println(" and dq's tail = " + dq.tail.value);
    dq.removeFirst();
    System.out.print("removeFirst(), dq's head = " + dq.head.value);
    System.out.println(" and dq's tail = " + dq.tail.value);
    String c = new String();
    c = "c";
    dq.addLast(c);
    System.out.print("addLast(c), dq's head = " + dq.head.value);
    System.out.println(" and dq's tail = " + dq.tail.value);
    dq.removeLast();
    System.out.print("removeLast(), dq's head = " + dq.head.value);
    System.out.println(" and dq's tail = " + dq.tail.value);
    dq.addFirst(a);
    dq.addLast(c);
    System.out.println("addFirst(a), addLast(c), what's the size now?  " + dq.size);
    System.out.println("hasNext()?  " + dq.iterator().hasNext());
    System.out.println("Next()?  " + dq.iterator().next());
    System.out.println("Next()?  " + dq.iterator().next());
    System.out.println("Next()?  " + dq.iterator().next());
    System.out.println("Next()?  " + dq.iterator().next());

  }   // unit testing
}
