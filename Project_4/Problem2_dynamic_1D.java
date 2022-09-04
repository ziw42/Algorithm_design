package Project4;
// Program for 2214 INFSCI 2591 Algorithm Design, Project4 Problem2
// Author: Zian Wang

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Problem2_dynamic_1D {

    public static int[] read1DArray(String path, int num){

        // Only store the lower triangle.
        int length = (num * num - num) / 2;
        int[] adjacency = new int[length];

        for(int t = 0; t < length; t++) {
            adjacency[t] = 9999999;
        }

        try {
            int i = 0;
            int j = 0;
            int index;

            File f = new File(path);
            if (f.exists()) {
                if (f.isFile()) {
                    FileReader fr = new FileReader(path);
                    BufferedReader br = new BufferedReader(fr);
                    String s = null;
                    s = br.readLine();  // The first row is attribute name, it is not int.
                    while ((s = br.readLine()) != null) {
                        i = Integer.valueOf(s.split(",")[0]);
                        j = Integer.valueOf(s.split(",")[1]);
                        if(i > j) {
                            index = i * (i - 1) / 2 + j;
                            adjacency[index] = Integer.valueOf(s.split(",")[2]);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return adjacency;
    }

    public static int combNumber(int n, int k) {
        int result;
        BigInteger x = BigInteger.valueOf(1);
        BigInteger y = BigInteger.valueOf(1);
        BigInteger z = BigInteger.valueOf(1);

        for(int t = 1; t < n + 1; t++) {
            x = x.multiply(BigInteger.valueOf(t));
        }
        for(int t = 1; t < k + 1; t++){
            y = y.multiply(BigInteger.valueOf(t));
        }
        for(int t = 1; t < (n - k + 1); t++) {
            z = z.multiply(BigInteger.valueOf(t));
        }
        BigInteger resultBig = x.divide(y.multiply(z));
        result = resultBig.intValue();
        return result;
    }

    public static boolean inSet(int i, int[] set) {
        if(set[i] == 1)
            return true;
        else
            return false;
    }

    public static void moveA(int[] A, int n) {
        int s = -1;
        int num = 0;
        int t;
        for(t = 0; t < n - 1; t++) {
            if(A[t] == 1 && A[t + 1] == 0) {
                A[t] = 0;
                A[t + 1] = 1;
                s = t;
                break;
            }
        }

        for(t = 0; t < s; t++) {
            if(A[t] == 1) {
                num++;
            }
        }
        for(t = 0; t < num; t++) {
            A[t] = 1;
        }
        for(; t < s; t++) {
            A[t] = 0;
        }
    }

    public static int[] Aminusj(int[] A, int j, int n) {
        int[] result = new int[n];
        for(int t = 0; t < n; t++) {
            result[t] = A[t];
        }
        result[j] = 0;
        return result;
    }

    public static int convertSet(int[] A, int n) {
        int num;
        int result = 0;
        for(int t1 = 0; t1 < n; t1++) {
            if(A[t1] == 1) {
                num = 1;
                for(int t2 = 0; t2 < n - t1 - 1; t2++) {
                    num *= 2;
                }
                result += num;
            }
        }

        return result;
    }

    public static int getLength(int i, int j, int[] adjacency) {
        if(i == j)
            return 999999999;
        else {
            if(i > j) {
                return adjacency[i * (i - 1) / 2 + j];
            }
            else {
                return adjacency[j * (j - 1) / 2 + i];
            }
        }

    }

    public static Map TSP(int[] adjacency, int n) {
        int i;
        int j;
        int k;
        int signal1;
        boolean signal2 = true;
        int columnNumber = 1;
        for(int t = 1; t < n + 1; t++) {
            columnNumber *= 2;
        }
        columnNumber -= 1;
        int[][] D = new int[n][columnNumber];
        int[][] P = new int[n][columnNumber];

        for(i = 1; i < n; i++) {
            D[i][0] = getLength(i, 0, adjacency);
        }
        for(k = 1; k < n - 1; k++) {
            int[] A = new int[n];
            for(int t = 0; t < k; t++) {
                A[t] = 1;
            }
            int bound1 = combNumber(n, k);
            for(signal1 = 0; signal1 < bound1; signal1++) {
                if(A[0] == 1) {
                    moveA(A, n);
                    continue;
                }
                for(i = 1; i < n; i++) {
                    if(inSet(i, A)) {
                        continue;
                    }
                    int minimum = 9999999;
                    int minj = -1;
                    for(j = 0; j < n; j++) {
                        if(!inSet(j, A)) {
                            continue;
                        }
                        int temp = getLength(i, j, adjacency) + D[j][convertSet(Aminusj(A, j, n), n)];
                        if(temp < 9999){
                            System.out.print("");
                        }
                        if(temp < minimum) {
                            minimum = temp;
                            minj = j;
                        }
                    }
                    D[i][convertSet(A, n)] = minimum;
                    P[i][convertSet(A, n)] = minj;
                }
                moveA(A, n);
            }
        }
        int[] V = new int[n];
        for(int t = 0; t < n ; t++) {
            V[t] = 1;
        }
        V[0] = 0;
        int minimum = 9999999;
        int minj = -1;
        for(j = 1; j < n; j++) {
            int temp = getLength(0, j, adjacency) + D[j][convertSet(Aminusj(V, j, n), n)];
            if(temp < minimum) {
                minimum = temp;
                minj = j;
            }
        }
        V[0] = 1;
        D[0][convertSet(Aminusj(V, 0, n), n)] = minimum;
        P[0][convertSet(Aminusj(V, 0, n), n)] = minj;

        int[] path = new int[n + 1];
        int pathi = 0;
        path[n] = 0;
        int[] pathV = new int[n];
        for(int t = 0; t < n ; t++) {
            pathV[t] = 1;
        }
        pathV[0] = 0;
        path[0] = 0;
        for(int t = 0; t < n - 1; t++) {
            pathi = P[pathi][convertSet(pathV, n)];
            pathV = Aminusj(pathV, pathi, n);
            path[t + 1] = pathi;
        }

        Map map = new HashMap();
        map.put("minLength", minimum);
        map.put("path", path);

        return map;
    }

    public static int getNumOfNodes(String path){
        int num = -1;
        File f = new File(path);
        try {
            if (f.exists()) {
                if (f.isFile()) {
                    FileReader fr = new FileReader(path);
                    BufferedReader br = new BufferedReader(fr);
                    String s = null;
                    s = br.readLine();  // The first row is attribute name, it is not int.
                    while ((s = br.readLine()) != null) {
                        num = Integer.valueOf(s.split(",")[0]);
                    }
                }
            }
        }
        catch(Exception e){
            e.getMessage();
        }
        num++;
        return num;
    }

    public static void main(String args[]) {
        System.out.println("Dynamic algorithm uses one-dimensional array");
        String path = "A:/Algorithm_Design/Project4/Project 4_Problem 2_InputData.csv";
        int numOfNodes = getNumOfNodes(path);
        int[] adjacency = read1DArray(path, numOfNodes);

        Map result = TSP(adjacency, numOfNodes);
        int[] TSPpath = (int[])result.get("path");
        int minLength = (int)result.get("minLength");

        System.out.println("This is the optimal path:");
        int t;
        for(t = 0; t < TSPpath.length - 1; t++) {
            System.out.print(TSPpath[t] + " -> ");
        }
        System.out.println(TSPpath[t]);
        System.out.println();
        System.out.println("The minimum length is: " + minLength);
    }
}
