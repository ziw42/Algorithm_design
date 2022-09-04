package Project1;

import java.math.BigInteger;

public class Problem2_RectangleMul_Normal {
    public void print(int[] A, int n){
        for(int t2 = 0; t2 < n; t2++){
            System.out.print(A[t2]+" ");
        }
        System.out.println();
    }
    public int tenpower(int n){
        if(n == 0)
            return 1;
        else{
            int result = 1;
            for(int t = 0; t < n; t++){
                result *= 10;
            }
            return result;
        }
    }
    public int[] split(int n){
        int[] result = new int[2];
        result[0] = n / 10;
        result[1] = n - (10 * (n / 10));
        return result;
    }
    public long RectangularMultiplication(long x, long y){
        int sign = 1;
        if(x < 0){
            sign *= -1;
            x *= -1;
        }
        if(y < 0){
            sign *= -1;
            y *= -1;
        }
        if(y > x){
            long swap;
            swap = x;
            x = y;
            y = swap;
        }
        long result = 0;
        int t = 0;
        long xt = x;
        long yt = y;
        int length1, length2;
        while(xt != 0){
            xt /= 10;
            t++;
        }
        int[] x_new = new int[t];
        length1 = t;
        while(t != 0){
            x_new[t - 1] = (int)(x / tenpower(t - 1));
            x -= tenpower(t - 1) * (x / tenpower(t - 1));
            t--;
        }
        while(yt != 0){
            yt /= 10;
            t++;
        }
        int[] y_new = new int[t];
        length2 = t;
        while(t != 0){
            y_new[t - 1] = (int)(y / tenpower(t - 1));
            y -= tenpower(t - 1) * (y / tenpower(t - 1));
            t--;
        }
        int[][] R = new int[length2][length1];
        for(int t1 = 0; t1 < length2; t1++){
            for(int t2 = 0; t2 < length1; t2++){
                R[t1][t2] = y_new[t1] * x_new[t2];
                //System.out.print(R[t1][t2]+" ");
            }
            //System.out.println();
        }
        int layernum = length1 + length2;
        int upanddown;
        int value;
        int i, j;
        int[] num;
        int layerresult = 0;
        for(int layer = 0; layer < layernum; layer++){
            if(layer < length1){
                upanddown = 1;
                i = 0;
                j = layer;
                while(i < length2 && j >= 0){
                    value = R[i][j];
                    num = split(value);
                    layerresult += num[upanddown];
                    if(upanddown == 1) {
                        upanddown = 0;
                        j--;
                    }
                    else {
                        upanddown = 1;
                        i++;
                    }
                }
            }
            else if(layer >= length1){
                i = length2 - 1;
                j = layer - length2;
                upanddown = 0;
                while(i >= 0 && j < length1){
                    value = R[i][j];
                    num = split(value);
                    layerresult += num[upanddown];
                    if(upanddown == 0){
                        upanddown = 1;
                        j++;
                    }
                    else {
                        upanddown = 0;
                        i--;
                    }
                }
            }
            result += layerresult * tenpower(layer);
            layerresult = 0;
        }
        result *= sign;
        return result;

    }
    public static void main(String[] args){
        Problem2_RectangleMul_Normal test = new Problem2_RectangleMul_Normal();
        System.out.println("AlaCarte Multiplication");
        System.out.println();
        long a = 7000L;
        Long b = 7294L;
        System.out.println("The result of " + a + " * " + b + " = "+test.RectangularMultiplication(a, b));
        System.out.println();
        a = 25L;
        b = 5038385L;
        System.out.println("The result of " + a + " * " + b + " = "+test.RectangularMultiplication(a, b));
        System.out.println();
        a = 59724L;
        b = 783L;
        System.out.println("The result of " + a + " * " + b + " = "+test.RectangularMultiplication(a, b));
        System.out.println();
        a = 8516L;
        b = -82147953548159344L;
        System.out.println("The result of " + a + " * " + b + " = "+test.RectangularMultiplication(a, b));
        System.out.println();
    }
}


