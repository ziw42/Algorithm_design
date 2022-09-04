package Algorithm_Design_Quiz13;

public class Queue {
    public Node head;
    public Node tail;

    public Queue() {
        this.head = null;
        this.tail = null;
    }

    public void enqueue(Node n) {
        if(this.empty()) {
            this.head = n;
            this.tail = n;
        }
        else {
            this.tail.next = n;
            this.tail = n;
        }
    }

    public Node dequeue() {
        Node result = this.head;
        this.head = this.head.next;
        if(this.head == null) {
            this.tail = null;
        }
        return result;
    }

    public boolean noExist(int n) {
        if(this.empty()) {
            return true;
        }
        else {
            Node temp = this.head;
            while (temp != tail) {
                if (temp.steps[0] == n) {
                    return false;
                }
                temp = temp.next;
            }
            if (temp.steps[0] == n) {
                return false;
            } else {
                return true;
            }
        }
    }

    public boolean empty() {
        if(this.head == null && this.tail == null) {
            return true;
        }
        else {
            return false;
        }
    }
}
