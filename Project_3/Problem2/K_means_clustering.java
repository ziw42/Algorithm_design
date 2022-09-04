package Project3;

import java.io.BufferedReader;
import java.io.FileReader;

import static java.lang.Math.random;

public class K_means_clustering {
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
        double[][] data = new double[row][7];
        try {
            int num = 0;
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String s = null;
            s = br.readLine();  // The first row is attribute name, it is not int.
            while ((s = br.readLine()) != null) {
                data[num][0] = Double.valueOf(s.split(",")[0]);
                data[num][1] = Double.valueOf(s.split(",")[1]);
                data[num][2] = Double.valueOf(s.split(",")[2]);
                data[num][3] = Double.valueOf(s.split(",")[3]);
                data[num][4] = Double.valueOf(s.split(",")[4]);
                data[num][5] = Double.valueOf(s.split(",")[5]);
                data[num][6] = Double.valueOf(s.split(",")[6]);
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
        centroid = new double[K][7];
        int i;
        int length = data.length;
        int[] iArray = new int[K];
        for(int t = 0; t < K; t++) {
            iArray[t] = -1;
        }
        for(int t2 = 0; t2 < K; t2++) {
            i = (int)(random()*length);
            if(!inArray(i, iArray)) {
                iArray[t2] = i;
                centroid[t2][0] = data[i][0];
                centroid[t2][1] = data[i][1];
                centroid[t2][2] = data[i][2];
                centroid[t2][3] = data[i][3];
                centroid[t2][4] = data[i][4];
                centroid[t2][5] = data[i][5];
                centroid[t2][6] = data[i][6];
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
        double distance = 0;


        for(int t = 0; t < K; t++) {
            distance = (x[0] - centroid[t][0])*(x[0] - centroid[t][0]) + (x[1] - centroid[t][1])*(x[1] - centroid[t][1]) + (x[2] - centroid[t][2])*(x[2] - centroid[t][2]) + (x[3] - centroid[t][3])*(x[3] - centroid[t][3]) + (x[4] - centroid[t][4])*(x[4] - centroid[t][4]) + (x[5] - centroid[t][5])*(x[5] - centroid[t][5]) + (x[6] - centroid[t][6])*(x[6] - centroid[t][6]);
            if(distance < min) {
                min = distance;
                minCentroid = t;
            }
        }
        result[0] = (double)minCentroid;
        result[1] = min;

        return result;
    }

    public static void moveCentroid(int[] c) {
        double[] sum = new double[7];
        int n = 0;
        int row = c.length;
        for(int t = 0; t < 7; t++) {
            sum[t] = 0;
        }
        for(int t = 0; t < K; t++) {
            for(int t2 = 0; t2 < row; t2++) {
                if(c[t2] == t) {
                    sum[0] += data[t2][0];
                    sum[1] += data[t2][1];
                    sum[2] += data[t2][2];
                    sum[3] += data[t2][3];
                    sum[4] += data[t2][4];
                    sum[5] += data[t2][5];
                    sum[6] += data[t2][6];
                    n++;
                }
            }
            if(n == 0) {
                System.out.println("The " + n + "th centroid is null.");
                int i = (int)(random() * row);
                for(int t4 = 0; t4 < 7; t4++) {
                    centroid[t][t4] = data[i][t4];
                    sum[t4] = 0;
                }
            }
            else {
                for (int t3 = 0; t3 < 7; t3++) {
                    centroid[t][t3] = sum[t3] / n;
                    sum[t3] = 0;
                }
            }
            n = 0;
        }
    }

    public static boolean stop(double[][] lastCentroid) {
        double totalDistance = 0;
        for(int t = 0; t < K; t++){
            for(int t1 = 0; t1 < 7; t1++){
                totalDistance += (lastCentroid[t][t1] - centroid[t][t1]) * (lastCentroid[t][t1] - centroid[t][t1]);
            }
            if (totalDistance > 0.000000001) {
                return false;
            }
            totalDistance = 0;
        }

        return true;
    }

    public static void K_means() {
        int length = data.length;
        int[] c = new int[length];
        double loss = 0;
        double[] result;
        double[][] lastCentroid = new double[K][7];
        iniCentroid();

        for(int t1 = 0; t1 < K; t1++) {
            for(int t2 = 0; t2 < 7; t2++) {
                lastCentroid[t1][t2] = -999;
            }
        }

        for(int t3 = 0; t3 < 100; t3++) {
            for (int t4 = 0; t4 < length; t4++) {
                result = distance(data[t4]);
                c[t4] = (int) (result[0]);
                loss += result[1];
            }
            loss /= length;
            for(int t5 = 0; t5 < K; t5++) {
                for(int t6 = 0; t6 < 7; t6++){
                    lastCentroid[t5][t6] = centroid[t5][t6];
                }
            }
            moveCentroid(c);

            /*if(t3 == 99){
                System.out.println("Stop after 100 times iteration");
                System.out.println("The loss after the last iteration is: " + loss);
            }*/
            System.out.println("The loss after the " + (t3 + 1) + " iteration is " + loss);
            if(stop(lastCentroid)) {
                System.out.println();
                System.out.println("Stop after " + (t3 + 1) + " times iteration");
                t3 = 10000;
            }
            loss = 0;
        }

        // Below are all about print.
        System.out.println();
        for(int t = 0; t < K; t++){
            System.out.println("The " + (t+1) + " centroid's coordinates are:");
            for(int t2 = 0; t2 < 7; t2++){
                System.out.print(centroid[t][t2] + " ");
            }
            System.out.println();
        }

        int[] count = new int[K];
        for(int t = 0; t < K; t++){
            count[t] = 0;
        }
        for(int t = 0; t < length; t++){
            count[c[t]]++;
        }
        System.out.println();
        for(int t = 0; t < K; t++) {
            System.out.println("There are " + count[t] + " points assigned to the " + (t + 1) + " centroid.");
        }
        /*int num00 = 0;
        int num11 = 0;
        for(int t = 0; t < length; t++){
            if(c[t] == 0) {
                num00++;
            }
            else if(c[t] == 1) {
                num11++;
            }
        }
        System.out.println();
        System.out.println("There are " + num00 + " rows assigned to the 1 centroid.");
        System.out.println("There are " + num11 + " rows assigned to the 2 centroid.");*/

    }

    public static void main(String args[]) {
        //K_means_clustering.readData("A:/Algorithm_Design/Project3/Project3_Power_Consumption.csv");
        //K_means_clustering.readData("A:/Algorithm_Design/Project3/Project3_Test_Case.csv");
        data = K_means_clustering.readData("A:/Algorithm_Design/Project3/Project3_Power_Consumption.csv");
        K = 2;
        K_means_clustering.K_means();
    }
}
