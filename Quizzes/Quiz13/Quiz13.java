package Algorithm_Design_Quiz13;

import static java.lang.Math.floor;

public class Quiz13 {


    public static boolean complete(Node v, int n) {
        int length = 0;
        for(int t = 0; t < v.steps.length; t++) {
            length += v.steps[t];
        }
        if(length == n) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean exceed(Node v, int n) {
        int length = 0;
        for(int t = 0; t < v.steps.length; t++) {
            length += v.steps[t];
        }
        if(length > n) {
            return true;
        }
        else {
            return false;
        }
    }

    public static void robotStep(int n) {
        Queue Q = new Queue();
        Node u;
        Node v = new Node();
        v.steps = new int[1];
        v.steps[0] = 1;
        Q.enqueue(v);
        while(!Q.empty()) {
            v = Q.dequeue();
            if(complete(v, n)) {
                System.out.print("{");
                for(int t = 0; t < v.steps.length - 1; t++) {
                    System.out.print(v.steps[t] + ", ");
                }
                System.out.print(v.steps[v.steps.length - 1]);
                System.out.print("}");
                System.out.println();
            }

            else if(exceed(v, n)) {
                if(v.steps[0] < 3) {
                    int temp = v.steps[0] + 1;
                    if(Q.noExist(temp)) {
                        u = new Node();
                        u.steps = new int[1];
                        u.steps[0] = temp;
                        Q.enqueue(u);
                    }
                }
            }

            else {
                int tempLength = v.steps.length + 1;
                for(int t = v.steps[v.steps.length - 1]; t <= 3; t++) {
                    u = new Node();
                    u.steps = new int[tempLength];
                    for(int t2 = 0; t2 < tempLength - 1; t2++) {
                        u.steps[t2] = v.steps[t2];
                    }
                    u.steps[tempLength - 1] = t;
                    Q.enqueue(u);
                }
            }
        }
    }

    public static void main(String args[]) {
        for(int t = 1; t <= 5; t++) {
            System.out.println("n = " + t);
            robotStep(t);
            System.out.println("------------------------------------");
        }
    }
}
