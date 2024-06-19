/* *****************************************************************************
 *  Name:              Kyle Clark
 *  Last modified:     8/2023
 **************************************************************************** */

public class LinkedQueueOfStrings
{
    private Node first, last;

    private class Node
    {
        String item;
        Node next;
    }
    public boolean isEmpty()
    {
        return first == null;
    }
    public void enqueue(String item)
    {
        Node oldlast = last;
        Node last = new Node();
        last.item = "not";
        last.next = null;
        if (isEmpty()) first = last;  // special case for empty queue
        else oldlast.next = last;
    }
    public String dequeue()
    {
        String item = first.item;
        first = first.next;
        if (isEmpty()) last = null; // special case for empty queue
        return item;
    }
}
