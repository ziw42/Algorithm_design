package Algorithm_Design_Quiz3;
// Program for 2214 INFSCI 2591 Algorithm Design, Quiz3
// Author: Zian Wang

import java.io.*;

//There are 4 primary functions in this program.
// bin is the Binomial Coefficient Using Divide-and-Conquer.
// bin2 is the Binomial Coefficient Using Dynamic Programming.
// minimum is the function used in bin(), this function returns the smaller one in two numbers.
// save is the function used to save the runtime to an excel file.
// All the units in the program is nanosecond. 1 ns = 10^-9 second.

public class Quiz3 {
    public long bin (int n, int k){
        if(k == 0 || n == k)
            return 1;
        else
            return bin(n-1, k-1) + bin(n-1, k);
    }

    public int minimum(int a, int b){
        if(a < b)
            return a;
        else
            return b;
    }
    public long bin2 (int n, int k){
        int i, j;
        long[][] B = new long[n + 1][k + 1];
        for(i = 0; i <= n; i++)
            for(j = 0; j <= minimum(i, k); j++) {
                if (j == 0 || j == i)
                    B[i][j] = 1;
                else
                    B[i][j] = B[i - 1][j - 1] + B[i - 1][j];
            }
        return B[n][k];

    }

    public void save(long[] time, int x, int n){
        try{
            OutputStream fop = new FileOutputStream("C:/Users/王梓安/IdeaProjects/Algorithm_Design/Runtime"+ x +".xlsx");
            OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");
            for(int t = 0; t < n; t++) {
                writer.append(""+time[t]);
                writer.append("\r\n");
            }
            writer.close();
            fop.close();
        }
        catch(Exception e){
            System.out.println(e.getStackTrace());
        }
    }

    public static void main(String[] args){

        Quiz3 test = new Quiz3();

        // Divide-and-Conquer, fixed n and change k.
        // I set n = 35 because if n = 40, the last run of bin takes me several minutes and wasn't finished, so I choose
        // a smaller n. The k is from 0 to 17.
        int n = 35;
        long[] time = new long[n/2+1];
        for(int t = 0; t <= n/2; t++) {
            long start = System.nanoTime();
            test.bin(n, t);
            long end = System.nanoTime();
            time[t] = end - start;
            System.out.println("Takes " + time[t] + " ns");
        }
        test.save(time,1,18);

        // Dynamic Programming, fixed n and change k.
        // I set n = 1000, and k is from 0 to 500.
        // One thing should be noticed is if n is very big, the result of bin2 will exceed the range of
        // the long variable in java, and the value will become negative. It seems that this problem will influence the
        // runtime of the algorithm.
        n = 1000;
        long[] time2 = new long[n/2+1];
        for(int t = 0; t <= n/2; t++) {
            long start = System.nanoTime();
            test.bin2(n, t);
            long end = System.nanoTime();
            time2[t] = end - start;
            System.out.println("Takes " + time2[t] + " ns");
        }
        test.save(time2,2,500);

        // Divide-and-Conquer, fixed k and change n.
        // I set k = 17 and n is from 17 to 34. Also, a bigger k will make the calculation takes too much time so I set
        // k = 17.
        int k = 17;
        long[] time3 = new long[k + 1];
        for(int t = k; t <= 2 * k; t++){
            long start = System.nanoTime();
            test.bin(t, k);
            long end = System.nanoTime();
            time3[t - 17] = end - start;
            System.out.println("Takes " + time3[t - 17] + " ns");
        }
        test.save(time3,3,18);

        // Dynamic programming, fixed k and change n.
        // I set k=500, and n is from 500 to 1000.
        k = 500;
        long[] time4 = new long[k + 1];
        for(int t = k; t <= 2 * k; t++){
            long start = System.nanoTime();
            test.bin2(t, k);
            long end = System.nanoTime();
            time4[t - 500] = end - start;
            System.out.println("Takes " + time4[t - 500] + " ns");
        }
        test.save(time4,4,501);



    }
}
