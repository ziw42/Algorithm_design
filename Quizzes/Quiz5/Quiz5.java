package Algorithm_Design_Quiz5;
// Program for 2214 INFSCI 2591 Algorithm Design, Quiz5.
// Author: Zian Wang

public class Quiz5 {

    // This function is used to return the bigger one between two inputs a and b.
    public int bigger(int a, int b) {
        if(a > b)
            return a;
        else
            return b;
    }

    // This function is used to return the biggest one in array t whose length is n.
    public int max(int[] t, int n){
        int max = 0;
        for(int i = 0; i < n; i++)
            if(t[i] > max)
                max = t[i];
        return max;
    }

    // The core part of the program:
    // Input: given list: x, the length of the given list: n.
    // Output: The maximum sum in any contiguous sublist of x.
    public int getMaximumSum(int[] x, int n) {
        // If the list is empty, the maximum sum is obviously 0.
        if(n == 0)
            return 0;

        // Sum[t] is the maximum sum of the contiguous sublist which ends in x[t].
        // Also, the sublist which gives the maximum sum, must ends in an element of x (Actually, any sublist of x ends
        // in an element of x, or it is not a sublist of x).
        // Therefore, the result we want to calculate (maximum sum in any contiguous sublist of x) is the biggest one in
        // sum[0] to sum[n].
        // So if we get all the sum[t], we can know which is the result.
        //
        // How we can calculate sum[t]?
        // There are only two possibilities: 1. sum[t] is just x[t], the other sublist ends in x[t] is smaller.
        //                                   2. sum[t] is not just x[t], and because the sublist must be contiguous,
        //                                      sum[t] must at least have x[t] and x[t - 1]. Therefore, since we must
        //                                      add x[t - 1], we should add sum[t - 1] to make sum[t] maximum.
        // So the maximum sum is the bigger one in these two possibilities.
        // Therefore, we can get a expression: sum[t] = bigger (x[t], x[t] + sum[t - 1]). It is clear that sum[0] = x[0]
        // (only x[0] is the contiguous sublist which ends in x[0]), so we can calculate every sum[t] start from x[0].
        int[] sum = new int[n];
        sum[0] = x[0];
        for(int t = 1; t < n; t++){
            // The expression.
            sum[t] = bigger(x[t], sum[t - 1] + x[t]);
        }
        // Return the biggest one in sum array.
        return max(sum, n);
    }

    public static void main(String[] args){
        Quiz5 test = new Quiz5();

        // Set the test cases.
        int[] case1 = new int[0];
        int[] case2 = new int[1];
        int[] case3 = new int[4];
        int[] case4 = new int[4];
        int[] case5 = new int[4];
        int[] case6 = new int[8];
        int[] case7 = new int[9];
        case2[0] = 1;

        case3[0] = 1;
        case3[1] = 2;
        case3[2] = 3;
        case3[3] = 4;

        case4[0] = -7;
        case4[1] = -4;
        case4[2] = -2;
        case4[3] = -8;

        case5[0] = -2;
        case5[1] = 3;
        case5[2] = 5;
        case5[3] = -7;

        case6[0] = -2;
        case6[1] = -3;
        case6[2] = 4;
        case6[3] = -1;
        case6[4] = -2;
        case6[5] = 1;
        case6[6] = 5;
        case6[7] = -3;

        case7[0] = -2;
        case7[1] = 1;
        case7[2] = -3;
        case7[3] = 4;
        case7[4] = -1;
        case7[5] = 2;
        case7[6] = 1;
        case7[7] = -5;
        case7[8] = 4;

        // Test the cases.
        System.out.println("The maximum sum in case 1 is: " + test.getMaximumSum(case1, 0));
        System.out.println("-------------------------------------------");
        System.out.println("The maximum sum in case 2 is: " + test.getMaximumSum(case2, 1));
        System.out.println("-------------------------------------------");
        System.out.println("The maximum sum in case 3 is: " + test.getMaximumSum(case3, 4));
        System.out.println("-------------------------------------------");
        System.out.println("The maximum sum in case 4 is: " + test.getMaximumSum(case4, 4));
        System.out.println("-------------------------------------------");
        System.out.println("The maximum sum in case 5 is: " + test.getMaximumSum(case5, 4));
        System.out.println("-------------------------------------------");
        System.out.println("The maximum sum in case 6 is: " + test.getMaximumSum(case6, 8));
        System.out.println("-------------------------------------------");
        System.out.println("The maximum sum in case 7 is: " + test.getMaximumSum(case7, 9));
    }
}
