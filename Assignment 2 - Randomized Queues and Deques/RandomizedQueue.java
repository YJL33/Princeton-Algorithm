/******************************************************************************
 *  Compilation:  javac RandomizedQueue.java
 *  Execution:    java RandomizedQueue input.txt
 *  Dependencies: Iterator
 *
 *  A randomized queue is similar to a stack or queue,
 *  except that the item removed is chosen uniformly at random from items in the data structure.
 *
 *  Corner cases.
 *  The order of two or more iterators to the same randomized queue must be mutually independent;
 *  each iterator must maintain its own random order.
 *  Throw a java.lang.NullPointerException if the client attempts to add a null item;
 *  throw a java.util.NoSuchElementException if the client tries to sample or dequeue an item from an empty randomized queue;
 *  throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator;
 *  throw a java.util.NoSuchElementException if the client calls the next() method in the iterator where no items to return.
 *
 *  Performance requirements.
 *  Must support each randomized queue operation (besides creating an iterator) in constant amortized time;
 *  Any sequence of M randomized queue operations (starting from an empty queue) shall take at most cM steps in the worst case,
 *  for some constant c. A randomized queue containing N items must use at most 48N + 192 bytes of memory.
 *  Additionally, your iterator implementation must support operations next() and hasNext() in constant worst-case time;
 *  and construction in linear time; you may (and will need to) use a linear amount of extra memory per iterator.
 *
 ******************************************************************************/

import java.util.Iterator;
//import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private Item[] queueOfItems;            // Length of qoi = number of baskets
  private int numOfItems = 0;             // Number of items
  public RandomizedQueue() {                // construct an empty randomized queue
    queueOfItems = (Item[]) new Object[1];
  }
  public boolean isEmpty() {                // is the queue empty?
    return numOfItems == 0;
  }
  public int size() {                       // return the number of items on the queue
    return numOfItems;
  }
  public void enqueue(Item item) {          // add the item
    if (item == null) {
      throw new NullPointerException("Cannot insert a null item.");
    }
    else {
      if (numOfItems == queueOfItems.length) {
        resize(2*numOfItems);             // Need more basket => double the size
      }
      queueOfItems[numOfItems++] = item;
    }
  }
  public Item dequeue() {                   // remove and return a random item
    if (numOfItems == 0) {
      throw new java.util.NoSuchElementException("Queue is empty");
    }
    else {
      int random_item = StdRandom.uniform(numOfItems);
      swap(numOfItems-1, random_item);
      Item item = queueOfItems[--numOfItems];
      queueOfItems[numOfItems] = null;
      if (numOfItems > 0 && numOfItems == queueOfItems.length/4) {
        resize(queueOfItems.length/2);             // Too many baskets => half the size
      }
      return item;
    }
  }
  private void swap(int a, int b) {       // Swap two item/basket.
    Item temp = queueOfItems[a];
    queueOfItems[a] = queueOfItems[b];
    queueOfItems[b] = temp;
    return;
   }
   
  private void resize(int capacity) {      // Adjust the size of baskets
    Item[] copy = (Item[]) new Object[capacity];
    for (int i = 0; i < numOfItems; i++) {
      copy[i] = queueOfItems[i];
    }
    queueOfItems = copy;
   }
  public Item sample() {                    // return (but do not remove) a random item
    if (numOfItems == 0) {
      throw new java.util.NoSuchElementException("Queue is empty");
    }
    else {
      int random_item = StdRandom.uniform(numOfItems);
      return queueOfItems[random_item];
    }
  }
  public Iterator<Item> iterator() {        // return an independent iterator over items in random order
    return new RandomizedQueueIterator();
  }
  private class RandomizedQueueIterator implements Iterator<Item> {
    private int i = numOfItems;
    private Item[] it;
    RandomizedQueueIterator() {
      it = (Item[]) new Object[i];
      for (int j = 0; j < i; j++) {
        it[j] = queueOfItems[j];
      }
      StdRandom.shuffle(it);
    }
    public boolean hasNext() { return i > 0; }
    public void remove() { /* not supported */
      throw new java.lang.UnsupportedOperationException("Not supported.");
    }
    public Item next() { 
        if (i == 0) {
          throw new java.util.NoSuchElementException("No more elements.");
        }
        Item ret = it[--i];
        it[i] = null;
        return ret; 
      }
   }
  public static void main(String[] args) { // unit testing
  }
}