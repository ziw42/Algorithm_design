package Project4;

public class Node {

    public Node next;
    public Node previous;
    public int level;
    public int[] path;
    public double bound;

    public Node(){
        next = null;
        previous = null;
    }

}
