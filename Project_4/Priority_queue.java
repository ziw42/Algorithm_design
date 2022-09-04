package Project4;


public class Priority_queue {

    public Node queueHead;
    public Node queueTail;

    /*public Node getTail() {
        if(queueHead == null){
            return null;
        }
        Node a = queueHead;
        while(a.next != null) {
            a = a.next;
        }
        return a;
    }*/

    public Priority_queue(){
        queueHead = null;
        queueTail = null;
    }

    public void insert(Node a) {
        if(queueHead == null) {
            queueHead = a;
            queueTail = a;
        }
        else {
            Node currentNode = queueHead;
            while(a.bound > currentNode.bound && currentNode.next != null) {
                currentNode = currentNode.next;
            }
            if(a.bound <= currentNode.bound) {
                a.previous = currentNode.previous;
                if (currentNode.previous != null) {
                    currentNode.previous.next = a;
                }
                a.next = currentNode;
                currentNode.previous = a;
            }
            else {
                a.previous = currentNode;
                currentNode.next = a;
                a.next = null;
                queueTail = a;
            }
            if(a.previous == null) {
                queueHead = a;
            }
        }
    }

    public Node remove() {
        if(queueHead == queueTail) {
            Node head = queueHead;
            queueHead = null;
            queueTail = null;
            return head;
        }
        else {
            Node head = queueHead;
            queueHead = queueHead.next;
            head.next.previous = null;
            head.next = null;
            return head;
        }
    }

    public boolean empty(){
        if(queueHead == null && queueTail == null) {
            return true;
        }
        else {
            return false;
        }
    }

}
