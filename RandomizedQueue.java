/* *****************************************************************************
 *  Name:              Kyle Clark
 *  Last modified:     8/2023
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;       // queue elements
    private int n;          // number of elements on queue
    private int tail;       // index of next available slot

    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[1];
        n = 0;
        tail = 0;
    }

    // assumption: that "/" rounds the quotient down not up; so, 5 / 2 = 2 rather than 5 / 2 = 3;
    // possibly signature line could be private Item[] resize() ? then just manipulate q[] in the method? Experiment and see what works.
    private Item[] resize(Item[] a) {
        if (a.length == n) {
            Item[] copy = (Item[]) new Object[a.length * 2];
            for (int i = 0; i < n; i++) {
                copy[i] = a[i];
            }
            q = copy;
            tail = n;
        }
        else if (n <= (a.length / 4)) {
            Item[] copy = (Item[]) new Object[a.length / 2];
            for (int i = 0; i < n; i++) {
                copy[i] = a[i];
            }
            q = copy;
            tail = n;
        }
        return q;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        q[tail] = item;
        tail++;
        n++;
        resize(q);
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int random = StdRandom.uniformInt(n);
        Item item = q[random];
        q[random] = q[tail - 1];
        q[tail - 1] = null;
        tail--;
        n--;
        resize(q);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int random = StdRandom.uniformInt(n);
        Item item = q[random];
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        private int[] rando;

        public boolean hasNext() {
            return i < n;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        // consider doing the shuffling in the below method as well to remove that operation from next();
        private int[] arrayBuild(int[] a) {
            for (int j = 0; j < n; j++) {
                a[j] = j;
            }
            return a;
        }

        public Item next() {
            if (i >= n) throw new NoSuchElementException();
            if (i == 0) {
                rando = new int[n];
                arrayBuild(rando);
                StdRandom.shuffle(rando);
            }
            Item item = q[rando[i]];
            i++;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        int size = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < size; i++) queue.enqueue(i);
        StdOut.println(queue.sample());
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
    }
}
