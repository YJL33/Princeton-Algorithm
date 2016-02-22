/******************************************************************************
 *  Compilation:  javac Deque.java
 *  Execution:    java Deque input.txt
 *  Dependencies:
 *
 *  The goal of this assignment is to implement elementary data structures using arrays and linked lists,
 *  and to introduce you to generics and iterators.
 *  A double-ended queue or deque (pronounced "deck") is a generalization of a stack and a queue that
 *  supports adding and removing items from either the front or the back of the data structure. 
 *
 *  Corner cases.
 *  Throw a java.lang.NullPointerException if the client attempts to add a null item;
 *  throw a java.util.NoSuchElementException if the client attempts to remove an item from an empty deque;
 *  throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator;
 *  throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and no more items to return.
 *
 *  Performance requirements.
 *  Your deque implementation must support each deque operation in constant worst-case time.
 *  A deque containing N items must use at most 48N + 192 bytes of memory,
 *  and use space proportional to the number of items currently in the deque.
 *  Additionally, the iterator implementation must support each operation (including construction) in constant worst-case time.
 *
 ******************************************************************************/

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

  private class Node {
    private Item item;
    private Node next;
    private Node prev;
  }

  private Node first;
  private Node last;
  private int N;

  public Deque() {
    first = null;
    last = null;
    N = 0;
  }

  public boolean isEmpty() {
    return (N == 0);
  }

  public int size() {
    return N;
  }

  public void addFirst(Item item) {
    if (item == null)
      throw new java.lang.NullPointerException();
    Node oldFirst = first;

    first = new Node();
    first.item = item;
    first.next = null;
    first.prev = null;

    if (isEmpty())
      last = first;
    else {
      first.next = oldFirst;
      if (oldFirst != null)
        oldFirst.prev = first;
    }

    N++;
  }

  public void addLast(Item item) {
    if (item == null)
      throw new java.lang.NullPointerException();
    Node oldLast = last;

    last = new Node();
    last.item = item;
    last.next = null;
    last.prev = null;

    if (isEmpty())
      first = last;
    else {
      if (oldLast != null)
        oldLast.next = last;
      last.prev = oldLast;
    }

    N++;
  }

  public Item removeFirst() {
    if (isEmpty())
      throw new java.util.NoSuchElementException();
    Item item = first.item;

    if (first.next != null)
      first.next.prev = null;
    first = first.next;
    N--;

    if (isEmpty())
      last = null;
    return item;
  }

  public Item removeLast() {
    if (isEmpty())
      throw new java.util.NoSuchElementException();
    Item item = last.item;

    if (last.prev != null)
      last.prev.next = null;
    last = last.prev;
    N--;

    if (isEmpty())
      first = null;
    return item;
  }

  public Iterator<Item> iterator() {
    return new LinkedIterator();
  }

  private class LinkedIterator implements Iterator<Item> {
    private Node current = first;

    public boolean hasNext() {
      return current != null;
    }

    public Item next() {
      if (!hasNext())
        throw new java.util.NoSuchElementException();
      Item item = current.item;
      current = current.next;
      return item;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  private Item getLast() {
    return (last != null) ? last.item : null;
  }

  private void printDeq() {
    for (Item i : this) {
      System.out.print(i + " -> ");
    }
    System.out.println("null");
  }

  public static void main(String[] args) {
  }
}
