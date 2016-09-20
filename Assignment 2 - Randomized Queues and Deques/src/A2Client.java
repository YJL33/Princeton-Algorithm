import java.util.Iterator;

public class A2Client<Item> {
  public static void main(String[] args) {
    System.out.println("\nPART I:");
    Deque<Integer> dq = new Deque<Integer>();
    System.out.println("is dq empty? " + dq.isEmpty());
    Integer a = 5;
    Integer b = 6;
    Integer c = 7;
    dq.addFirst(a);
    System.out.println("addFirst(a), now is dq empty? " + dq.isEmpty());
    dq.removeFirst();
    System.out.println("removeFirst()");
    dq.addLast(b);
    System.out.println("addLast(b)");
    dq.addLast(c);
    System.out.println("addLast(c)");
    dq.removeLast();
    System.out.println("removeLast()");
    System.out.println("what's the size now?  " + dq.size());
    System.out.println("hasNext()?  " + dq.iterator().hasNext());
    System.out.println("next()?  " + dq.iterator().next());
    System.out.println("what's the size now?  " + dq.size());
    dq.removeLast();
    System.out.println("removeLast()");
    System.out.println("what's the size now?  " + dq.size());

    System.out.println("is dq empty? " + dq.isEmpty());
    for (int i = 0; i<1000; i++)
      dq.addFirst(i);
    System.out.println("addFirst(i)*1000, what's dq's size now?  " + dq.size());
    for (int i = 0; i<1000; i++)
      dq.removeLast();
    System.out.println("removeLast()*1000, what's dq's size now?  " + dq.size());
    dq.addFirst(1);
    System.out.println("addFirst(1), what's dq's size now?  " + dq.size());


    System.out.println("\n\nPART II:");
    Iterator<Integer> itr = dq.iterator();
    for (int i = 0; i<1000; i++)
      dq.addFirst(i);
    System.out.println("addFirst(i)*1000, what's dq's size now?  " + dq.size());
    System.out.println("hasNext()?  " + itr.hasNext());
    System.out.println("next()?  " + itr.next());
    System.out.println("hasNext()?  " + itr.hasNext());
    for (int i = 0; i<1000; i++)
      dq.addFirst(i);
    System.out.println("addFirst(i)*1000, what's dq's size now?  " + dq.size());
    Iterator<Integer> itr2 = dq.iterator();
    System.out.println("hasNext()?  " + itr2.hasNext());
    System.out.println("next()?  " + itr2.next());
    System.out.println("hasNext()?  " + itr2.hasNext());



    System.out.println("\n\nPART III:");
    RandomizedQueue<Integer> nums = new RandomizedQueue<Integer>();
    System.out.println("is nums empty? " + nums.isEmpty());
    // System.out.println("try to dequeue: " + nums.dequeue());
    nums.enqueue(a);
    System.out.println("enqueue(5), now is nums empty? " + nums.isEmpty());
    nums.enqueue(b);
    System.out.println("enqueue(6), nums's size = " + nums.size());
    nums.enqueue(c);
    System.out.println("enqueue(7), nums's size = " + nums.size());
    int out = nums.dequeue();
    System.out.println("dequeue(): " + out + ",  nums's size = " + nums.size());
    int out2 = nums.dequeue();
    System.out.println("dequeue(): " + out2 + ",  nums's size = " + nums.size());
    nums.enqueue(3);
    nums.enqueue(4);
    System.out.println("enqueue(3), enqueue(4), now size = " + nums.size());
    Iterator<Integer> i1 = nums.iterator();
    Iterator<Integer> i2 = nums.iterator();
    System.out.println("i1 hasNext()?  " + i1.hasNext());
    System.out.println("i2 hasNext()?  " + i2.hasNext());
    System.out.println("i1's Next()?  " + i1.next());
    System.out.println("i2's Next()?  " + i2.next());
    System.out.println("i1's Next()?  " + i1.next());
    System.out.println("i2's Next()?  " + i2.next());
    System.out.println("i1's Next()?  " + i1.next());
    System.out.println("i2's Next()?  " + i2.next());
    System.out.println("hasNext()?  " + i1.hasNext());
    System.out.println("hasNext()?  " + i2.hasNext());
    a = null;
    b = null;
    c = null;
  }
}
