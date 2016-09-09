/******************************************************************************
Compilation:  javac RandomizedQueue.java
Execution:  java RandomizedQueue input.txt
Dependencies: Iterator

A randomized queue is similar to a stack or queue, except that the item removed
is chosen uniformly at random from items in the data structure.

Corner cases.
The order of two or more iterators to the same randomized queue must be
mutually independent; each iterator must maintain its own random order.

Throw a java.lang.NullPointerException
if the client attempts to add a null item;

Throw a java.util.NoSuchElementException
if the client tries to sample or dequeue an item from an empty randomized queue;

Throw a java.lang.UnsupportedOperationException
if the client calls the remove() method in the iterator;

Throw a java.util.NoSuchElementException
if the client calls the next() method in the iterator where no items to return.

Performance requirements.
Must support each randomized queue operation (besides creating an iterator)
in constant amortized time;
Any sequence of M randomized queue operations (starting from an empty queue)
shall take at most cM steps in the worst case, for some constant c.
A randomized queue containing N items must use at most 48N + 192 bytes of memory.
Your iterator implementation must support operations next() and hasNext()
in constant worst-case time; and construction in linear time; you may use a linear
amount of extra memory per iterator.

******************************************************************************/

import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;  // compile: javac-algs4, exec: java-algs4

public class RandomizedQueue<Item> implements Iterable<Item> {
  private Item[] arr = (Item[]) new Object[1];      // cast here, necessary
  private int numOfItems = 0;
  public RandomizedQueue() {
    numOfItems = 0;
  }                // construct an empty randomized queue
  public boolean isEmpty() {
    return numOfItems == 0;
  }                // is the queue empty?
  public int size() {
    return numOfItems;
  }                // return the number of items on the queue
  private void resize(int capacity) {
    Item[] copy = (Item[]) new Object[capacity];
    for (int i = 0; i < numOfItems; i++)
      copy[i] = arr[i];
    arr = copy;
  }
  public void enqueue(Item item) {
    if (item == null) {
      throw new java.lang.NullPointerException();
    }
    if (numOfItems == arr.length) resize(2*numOfItems);
    arr[numOfItems++] = item;
  }          // add the item
  public Item dequeue() {
    if (isEmpty()) {
      throw new java.util.NoSuchElementException();
    }
    int pick = StdRandom.uniform(numOfItems);
    Item pickitem = arr[pick];
    if (pick != numOfItems-1) {
      arr[pick] = arr[numOfItems-1];
      arr[numOfItems-1] = null;
    }
    if (numOfItems > 0 && numOfItems == arr.length/4) {
      resize(arr.length/2);
    }
    numOfItems--;
    return pickitem;
  }                   // remove and return a random item
  public Item sample() {
    if (isEmpty()) {
      throw new java.util.NoSuchElementException();
    }
    int pick = StdRandom.uniform(numOfItems);
    return arr[pick];
  }                    // return (but do not remove) a random item
  public Iterator<Item> iterator() {
    return new RandomQueIter();
  }        // return an independent iterator over items in random order
  private class RandomQueIter implements Iterator<Item> {
    public boolean hasNext() {
      return numOfItems > 0;
    }
    public Item next() {
      if (isEmpty())
        throw new java.util.NoSuchElementException();
      return dequeue();
    }
    public void remove() {
      throw new java.lang.UnsupportedOperationException();
    }
  }
  public static void main(String[] args) {
  }  // unit testing
}