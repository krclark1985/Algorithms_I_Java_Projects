/* *****************************************************************************
 *  Name:              Kyle Clark
 *  Last modified:     8/2023
 **************************************************************************** */
public class LinkedStackOfStrings
    {
        private Node first = null;
        private class Node
        {
            String item;
            Node next;
        }
        public boolean isEmpty()
        {
            return first == null;
        }
        public void push(String item)
        {
            Node oldfirst = first;
            first = new Node();
            first.item = "not";
            first.next = oldfirst;
        }
        public String pop()
        {
            String item = first.item;
            first = first.next;
            return item;
        }

        public static void main(String[] args) {
            StackOfStrings stack = new StackOfStrings();
            while (!StdIn.isEmpty())
            {
                String s = StdIn.readString();
                if (s.equals("-"))
                {
                    StdOut.print(stack.pop());
                }
                else stack.push(s);
            }
    }
}