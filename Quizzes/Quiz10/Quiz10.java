package Algorithm_Design_Quiz10;

public class Quiz10 {

    public static int W;
    public static int n;
    public static int[] p;
    public static int[] w;
    public static int maxprofit;
    public static boolean[] include;
    public static boolean[] bestSet;

    public static void knapsack(int i, int profit, int weight) {
        if(weight <= W && profit > maxprofit) {
            maxprofit = profit;
            for(int t = 0; t < n; t++) {
                bestSet[t] = include[t];
            }
        }

        if(promising(i, weight, profit)) {
            include[i + 1] = true;
            knapsack(i + 1, profit + p[i + 1], weight + w[i + 1]);
            include[i + 1] = false;
            knapsack(i + 1, profit, weight);
        }
    }

    public static boolean promising(int i, int weight, int profit) {
        int j;
        int k;
        int totweight;
        double bound;

        if(weight >= W){
            return false;
        }
        else {
            j = i + 1;
            bound = profit;
            totweight = weight;
            while(j < n && totweight + w[j] <= W) {
                totweight += w[j];
                bound += p[j];
                j++;
            }
            k = j;
            if(k < n) {
                bound += (W - totweight) * (p[k] / w[k]);
            }
            return bound > maxprofit;
        }
    }

    public static void main(String args[]) {
        // Initialize all the variables.
        W = 9;
        n = 5;
        p = new int[n];
        w = new int[n];

        p[0] = 20;
        p[1] = 30;
        p[2] = 35;
        p[3] = 12;
        p[4] = 3;

        w[0] = 2;
        w[1] = 5;
        w[2] = 7;
        w[3] = 3;
        w[4] = 1;

        maxprofit = 0;
        include = new boolean[n];
        bestSet=  new boolean[n];

        // Here I use -1 because in java the first index in the array is 0, so -1 is the root.
        knapsack(-1, 0, 0);

        // All below are all about print.
        // List all the items.
        System.out.println("All the items:");
        System.out.println("i   pi  wi  pi/wi");
        for(int t = 0; t < n; t++){
            // This if and else is to regularize the print format, making the numbers in the same column.
            if(p[t] < 10)
                System.out.println((t + 1) + "   " + p[t] + "   " + w[t] + "   " + (p[t] / w[t]));
            else
                System.out.println((t + 1) + "   " + p[t] + "  " + w[t] + "   " + (p[t] / w[t]));
        }
        System.out.println("Total weight is: " + W);
        System.out.println();

        // The results:
        System.out.print("These items are included: ");
        for(int t = 0; t < n; t++) {
            if(bestSet[t]){
                System.out.print((t + 1) + " ");
            }
        }
        System.out.println();
        System.out.println("The maximum profit is " + maxprofit);
    }
}
