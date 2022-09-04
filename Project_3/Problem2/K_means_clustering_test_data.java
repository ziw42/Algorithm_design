package Project3;

import java.io.BufferedReader;
import java.io.FileReader;

import static java.lang.Math.*;

public class K_means_clustering_test_data {

    public static double[][] centroid;
    public static double[][] data;
    public static int K;

    public static double[][] readData(String path) {
        int row = 0;
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String s = null;
            s = br.readLine();  // The first row is attribute name, it is not int.
            while ((s = br.readLine()) != null) {
                row++;
            }
        }
        catch(Exception e){
            e.getMessage();
        }
        double[][] data = new double[row][2];
        try {
            int num = 0;
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String s = null;
            s = br.readLine();  // The first row is attribute name, it is not int.
            while ((s = br.readLine()) != null) {
                data[num][0] = Double.valueOf(s.split(",")[0]);
                data[num][1] = Double.valueOf(s.split(",")[1]);
                num++;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public static boolean inArray(int n, int[] array) {
        for(int t = 0; array[t] != -1; t++) {
            if (n == array[t]) {
                return true;
            }
        }
        return false;
    }

    public static void iniCentroid() {
        centroid = new double[K][2];
        int i;
        int length = data.length;
        int[] iArray = new int[K];
        for(int t = 0; t < K; t++){
            iArray[t] = -1;
        }
        for(int t2 = 0; t2 < K; t2++) {
            i = (int)(random()*length);
            if(!inArray(i, iArray)){
                iArray[t2] = i;
                centroid[t2][0] = data[i][0];
                centroid[t2][1] = data[i][1];
            }
            else {
                t2--;
            }
        }
    }

    public static double[] distance(double[] x) {
        double min = 999999999;
        int minCentroid = -1;
        double[] result = new double[2];

        for(int t = 0; t < K; t++) {
            if(((x[0] - centroid[t][0])*(x[0] - centroid[t][0]) + (x[1] - centroid[t][1])*(x[1] - centroid[t][1])) < min) {
                min = (x[0] - centroid[t][0])*(x[0] - centroid[t][0]) + (x[1] - centroid[t][1])*(x[1] - centroid[t][1]);
                minCentroid = t;
            }
        }
        result[0] = (double)minCentroid;
        result[1] = min;

        return result;
    }

    public static void moveCentroid(int[] c) {
        double[] sum = new double[2];
        int n = 0;
        int row = c.length;
        sum[0] = 0;
        sum[1] = 0;
        for(int t = 0; t < K; t++) {
            for(int t2 = 0; t2 < row; t2++) {
                if(c[t2] == t) {
                    sum[0] += data[t2][0];
                    sum[1] += data[t2][1];
                    n++;
                }
            }
            centroid[t][0] = sum[0] / n;
            centroid[t][1] = sum[1] / n;
            n = 0;
            sum[0] = 0;
            sum[1] = 0;
        }
    }

    public static boolean stop(double[][] lastCentroid) {
        for(int t = 0; t < K; t++){
            if(lastCentroid[t][0] != centroid[t][0] || lastCentroid[t][1] != centroid[t][1]) {
                return false;
            }
        }

        return true;
    }

    public static void K_means() {
        int length = data.length;
        int[] c = new int[length];
        double loss = 0;
        double[] result;
        double[][] lastCentroid = new double[K][2];
        iniCentroid();

        for(int t1 = 0; t1 < K; t1++) {
            lastCentroid[t1][0] = -999;
            lastCentroid[t1][1] = -999;
        }

        for(int t1 = 0; t1 < 10000; t1++) {
            for (int t = 0; t < length; t++) {
                result = distance(data[t]);
                c[t] = (int) (result[0]);
                loss += result[1];
            }
            loss /= length;
            System.out.println("The loss after the " + (t1 + 1) + " iteration is " + loss);
            for(int t2 = 0; t2 < K; t2++) {
                lastCentroid[t2][0] = centroid[t2][0];
                lastCentroid[t2][1] = centroid[t2][1];
            }
            moveCentroid(c);

            if(stop(lastCentroid)) {
                System.out.println("Stop at " + (t1 + 1) + " times iteration");
                t1 = 10000;
            }
        }

        // Below are all about printing.
        int num00 = 0;
        int num11 = 0;
        System.out.println();
        System.out.println("The coordinates of the 1 centroid are:");
        System.out.println(centroid[0][0] + "," + centroid[0][1]);
        System.out.println("The coordinates of the 2 centroid are:");
        System.out.println(centroid[1][0] + "," + centroid[1][1]);
        for(int t3 = 0; t3 < c.length; t3++) {
            if(c[t3] == 0) {
                num00++;
            }
            else if(c[t3] == 1) {
                num11++;
            }
        }
        System.out.println();
        System.out.println("There are " + num00 + " points assigned to the 1 centroid.");
        System.out.println("There are " + num11 + " points assigned to the 2 centroid.");
    }

    public static void main(String args[]) {
        //K_means_clustering.readData("A:/Algorithm_Design/Project3/Project3_Power_Consumption.csv");
        //K_means_clustering.readData("A:/Algorithm_Design/Project3/Project3_Test_Case.csv");
        data = K_means_clustering_test_data.readData("A:/Algorithm_Design/Project3/Project3_Test_Case.csv");
        K = 2;
        K_means_clustering_test_data.K_means();
    }
}
