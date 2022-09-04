package Project1;
// Program for 2214 INFSCI 2591 Algorithm Design, Project1, Problem1
// Author: Zian Wang
// This program has 4 functions:
// 1. sort() is the primary function, it is used to solve the problem1. The other 3 functions are used to support.
// 2. min() is a function used to return the smaller one of the two input.
// 3. max() is a function used to return the bigger one of the two inputs.
// 4. print() is a function used to print the input array to the console.

public class Problem1 {
    // This function is used to output the smaller one of the two inputs.
    public int min(int m, int n){
        if(m <= n)
            return m;
        else
            return n;
    }

    // This function is used to output the bigger one of the two inputs.
    public int max(int m, int n){
        if(m >= n)
            return m;
        else
            return n;
    }

    // This function is used to print the input array A to the console. The input n is the size of the array.
    public void print(int[] A, int n){
        for(int t2 = 0; t2 < n; t2++){
            System.out.print(A[t2]+" ");
        }
        System.out.println();
    }

    // The sort function.
    // Input: array A which is waiting to be sorted. m is the size of the first subarray. n is the size of the second subarray.
    // Output: The sorted array.
    public int[] sort(int[] A, int m, int n){
        // Auxiliary array Aux. Size is min(m, n).
        int[] Aux = new int[min(m, n)];
        // i and j are index for subarray 1 and subarray 2. t is a temporary index.
        int i = 0;
        int j = 0;
        int t;
        // We first copy all the value of the smaller subarray to array Aux.
        if(m > n){
            for(t = m ; t < m + n; t++){
                Aux[j] = A[t];
                j++;
            }
        }
        else{
            for(t = 0 ; t < m; t++){
                Aux[j] = A[t];
                j++;
            }
        }
        // Since we have already saved the values of the smaller subarray, we will now move the bigger subarray
        // to the beginning of the array A: A[0], ..., A[max(m, n)]. This is to make the loop easier.
        if(n > m){
            for(t = 0; t < n; t++)
                A[t] = A[t + m];
        }
        j = 0;

        // We compare the ist element of bigger subarray to the jst element of the smaller subarray, if ist is bigger
        // then put the jst to the front of the ist element, if not, we move i to the next element.
        // The loop stops when i moves to the last element of the bigger subarray (whose index will be the max(m, n) + j
        // because j also stands for the number of the elements moved from Aux to A). Or the loop will stop when the j
        // moves to the last element of the smaller array.
        // These two case mean if all the elements in the bigger array is smaller than the jst element of smaller array,
        // or all the elements in the smaller array are moved to the A, the loop will stop.
        while(i < max(m, n) + j && j < min(m, n)){
            if(A[i] > Aux[j]){
                for(t = max(m, n) + j - 1; t >= i; t--)
                    A[t + 1] = A[t];
                A[i] = Aux[j];
                i++;
                j++;
            }
            else
                i++;
        }

        // If all the elements in the smaller array are moved into A, the A is finished.
        // However, if the loop stops because all the elements in the bigger array are smaller than the jst element in
        // the smaller array at that time, we have to move the rest elements of the smaller array (Aux[j], ..., Aux[min(m, n)])
        // to the end of the array A.
        if(j < n - 1){
            for(t = j; t < min(m, n); t++){
                A[i] = Aux[t];
                i++;
            }
        }

        return A;
    }
    public static void main(String[] args){
        Problem1 test = new Problem1();
        // Making the test cases.
        int[] A= new int[3];
        A[0] = 3;
        A[1] = 7;
        A[2] = 9;
        int[] resultA = test.sort(A,0,3);
        // Print the result of the algorithm.
        System.out.println("The result of test case 1 is:");
        test.print(resultA,3);
        System.out.println();

        int[] B = new int[4];
        B[0] = 2;
        B[1] = 7;
        B[2] = 9;
        B[3] = 1;
        int[] resultB = test.sort(B,3,1);
        System.out.println("The result of test case 2 is:");
        test.print(resultB,4);
        System.out.println();

        int[] C = new int[8];
        C[0] = 1;
        C[1] = 7;
        C[2] = 10;
        C[3] = 15;
        C[4] = 3;
        C[5] = 8;
        C[6] = 12;
        C[7] = 18;
        int[] resultC = test.sort(C, 4,4);
        System.out.println("The result of test case 3 is:");
        test.print(resultC, 8);
        System.out.println();

        int[] D = new int[19];
        D[0] = 1;
        D[1] = 3;
        D[2] = 5;
        D[3] = 5;
        D[4] = 15;
        D[5] = 18;
        D[6] = 21;
        D[7] = 5;
        D[8] = 5;
        D[9] = 6;
        D[10] = 8;
        D[11] = 10;
        D[12] = 12;
        D[13] = 16;
        D[14] = 17;
        D[15] = 17;
        D[16] = 20;
        D[17] = 25;
        D[18] = 28;
        int[] resultD = test.sort(D, 7, 12);
        System.out.println("The result of test case 4 is:");
        test.print(resultD, 19);
        System.out.println();
    }
}
