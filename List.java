/* *****************************************************************************
 *  Name:              Kyle Clark
 *  Last modified:     8/2023
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class List<Item> implements Iterable<Item> {

    private int n;
    private int count;

    private Node first;

    private Node last;

    private class Node {
        Item item;
        Node next;
    }

    public List() {
        first = null;
        n = 0;
        count = 0;
    }

    // is the list empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items in the list
    public int size() {
        return n;
    }


    public void push(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        else if (n == 0) {
            first = new Node();
            first.item = item;
            first.next = null;
            n++;
        }
        else if (n == 1) {
            last = first;
            first = new Node();
            first.item = item;
            first.next = last;
            n++;
        }
        else {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
            n++;
        }
    }

    public Item popLast() {
        if (isEmpty()) throw new NoSuchElementException("list is empty");
        else if (n == 1) {
            Item item = first.item;
            first.item = null;
            first.next = null;
            n--;
            return item;
        }
        else {
            Item item = last.item;
            n--;
            int i = 1;
            for (Node x = first; i <= n; x = x.next, i++) {
                if (i == n) {
                    last = x;
                    last.next = null;
                }
            }
            return item;
        }
    }


    public Item peekMid() {
        if (isEmpty()) throw new NoSuchElementException("list is empty");
        else if (n == 1) {
            return first.item;
        }
        else if (n % 2 == 1) {
            Item item = null;
            int i = 1;
            int mid = (n / 2) + 1;

            // iterate through list until reaching the mid node
            for (Node x = first; x != null; x = x.next, i++) {
                if (i == mid) {
                    item = x.item;
                }
            }
            return item;
        }
        else {
            Item item = null;
            Node x = new Node();
            int i = 1;
            int mid = n / 2;

            // iterate through list until reaching the mid node
            for (x = first; x != null; x = x.next, i++) {
                if (i == mid) {
                    item = x.item;
                }
            }
            return item;
        }
    }


    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class LinkedIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        List<Integer> list = new List<Integer>();
        int size = Integer.parseInt(args[0]);
        for (int i = 0; i < size; i++) {
            list.push(i);
        }
        StdOut.println(list.peekMid());
        while (list.size() > (size / 50000)) {
            list.popLast();
        }
        for (int a : list) {
            StdOut.print(a + " ");
        }
        StdOut.println();
        StdOut.println(list.peekMid());
    }
}