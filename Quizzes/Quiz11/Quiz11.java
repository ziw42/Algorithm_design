package Algorithm_Design_Quiz11;

public class Quiz11 {

    public static int W;
    public static int n;
    public static int maxprofit;
    public static int numOfVisit;
    public static int[] w;
    public static int[] p;

    public static double bound(Node u) {
        double result;
        int totWeight;
        int j;
        int k;

        if(u.weight > W) {
            return 0;
        }
        else {
            result = u.profit;
            j = u.level +1;
            totWeight = u.weight;
            while(j <= n && totWeight + w[j - 1] <= W) {
                totWeight += w[j - 1];
                result += p[j - 1];
                j++;
            }
            k = j;
            if(k <= n) {
                result += (W - totWeight) * Double.valueOf(p[k - 1]) / w[k - 1]; // In case the quotient is double.
            }
            return result;
        }
    }

    public static void knapsack() {
        Priority_queue PQ = new Priority_queue();
        Node v = new Node();
        v.level = 0;
        v.profit = 0;
        v.weight = 0;
        maxprofit = 0;
        v.bound = bound(v);
        PQ.insert(v);

        while(!PQ.empty()) {
            v = PQ.remove();
            numOfVisit++;
            if(v.bound > maxprofit) {
                Node u = new Node();
                u.level = v.level + 1;
                u.weight = v.weight + w[u.level - 1];
                u.profit = v.profit + p[u.level - 1];
                if(u.weight <= W && u.profit > maxprofit) {
                    maxprofit = u.profit;
                }
                u.bound = bound(u);
                if(u.bound > maxprofit) {
                    PQ.insert(u);
                }
                u = new Node();
                u.level = v.level + 1;
                u.weight = v.weight;
                u.profit = v.profit;
                u.bound = bound(u);
                if(u.bound > maxprofit) {
                    PQ.insert(u);
                }
            }
        }

    }
    public static void main(String args[]) {
        W = 13;
        n = 5;
        numOfVisit = 0;
        w = new int[] {2, 5, 7, 3, 1};
        p = new int[] {20, 30, 35, 12, 3};
        knapsack();

        System.out.println("All the items:");
        System.out.println("i   pi  wi  pi/wi");
        for(int t = 0; t < n; t++){
            // This if and else is to regularize the print format, making the numbers in the same column.
            if(p[t] < 10)
                System.out.println((t + 1) + "   " + p[t] + "   " + w[t] + "   " + (p[t] / w[t]));
            else
                System.out.println((t + 1) + "   " + p[t] + "  " + w[t] + "   " + (p[t] / w[t]));
        }
        System.out.println("Maximum total weight is: " + W);
        System.out.println();
        System.out.println("The max profit is: " + maxprofit);
        System.out.println("The number of nodes visited before finding the optimal solution: " + numOfVisit);
    }
}
