package Project1;
// Program for 2214 INFSCI 2591 Algorithm Design, Project1, Problem2, AlaCarte Multiplication
// Author: Zian Wang

// Statement:
// My platform uses 64 bit CPU (intel Core i5-8300H), the programming language is Java, and the code is compiled on
// IntelliJ IDEA Community Edition 2020.3.
// Some numbers in the test cases, like -82147953548159344, have too many digits so the int and long type variables cannot
// handle these numbers. Therefore, I use java.math.BigInteger to handle extremely big or small number in test cases.
// The BigInteger is only used to show the extremely big and small numbers. All the functions of BigInteger I use can be
// replaced by operator + - * / % < > and = if the variables are int or long (The BigInteger cannot use these normal
// operators, they have to use functions like "BigInteger.add()", ".multiply()" to execute operation). None of them have
// other functions.
//
// Just in case using BigInteger is not allowed in this project, I also submitted normal version of AlaCarte and Rectangular,
// which don't use BigInteger. So if using java.math.BigInteger is not allowed, please grade the normal version programs.
// However, some test cases that have extremely big or small number cannot be tested in normal version because the expression
// range of int and long variables is not big enough in java.
// The normal version codes are in the folder: "Normal_version". If using BigInteger is allowed, please ignore normal
// version. Thank you! :)

import java.math.BigInteger;

public class Problem2_RectangleMul {
    // tenpower function is used to calculate 10^n.
    // Input: n.
    // Output: 10^n.
    public BigInteger tenpower(int n){
        if(n == 0)
            return new BigInteger("1");
        else{
            BigInteger result = new BigInteger("1");
            for(int t = 0; t < n; t++){
                result = result.multiply(new BigInteger("10"));
            }
            return result;
        }
    }
    // split function is used to split a double-digit number.
    // Input: The number n which is waiting to be split.
    // Output: An array, the first element is tens digit of n, the second element is units digit of n.
    public int[] split(int n){
        int[] result = new int[2];
        result[0] = n / 10;
        result[1] = n - (10 * (n / 10));
        return result;
    }
    // The primary function.
    // Input: Multiplier and multiplicand x, y.
    // Output: The product of a and b.
    public  BigInteger RectangularMultiplication(BigInteger x, BigInteger y){
        // Judge whether the product is negative.
        int sign = 1;
        if(x.compareTo(new BigInteger("0")) == -1){
            sign *= -1;
            x = x.multiply(new BigInteger("-1"));
        }
        if(y.compareTo(new BigInteger("0")) == -1){
            sign *= -1;
            y = y.multiply(new BigInteger("-1"));
        }
        // Set x is always the bigger one to make the code more clear.
        // Copy every digit of the number to the x_new and y_new array.
        if(y.compareTo(x) == 1){
            BigInteger swap;
            swap = x;
            x = y;
            y = swap;
        }
        BigInteger result = new BigInteger("0");
        int t = 0;
        BigInteger xt = x;
        BigInteger yt = y;
        int length1, length2;
        while(xt.compareTo(new BigInteger("0")) != 0){
            xt = xt.divide(new BigInteger("10"));
            t++;
        }
        int[] x_new = new int[t];
        length1 = t;
        while(t != 0){
            x_new[t - 1] = (x.divide(tenpower(t - 1))).intValue();
            x = x.subtract(tenpower(t - 1).multiply(x.divide(tenpower(t - 1))));
            t--;
        }
        while(yt.compareTo(new BigInteger("0")) != 0){
            yt = yt.divide(new BigInteger("10"));
            t++;
        }
        int[] y_new = new int[t];
        length2 = t;
        while(t != 0){
            y_new[t - 1] = (y.divide(tenpower(t - 1))).intValue();
            y = y.subtract(tenpower(t - 1).multiply(y.divide(tenpower(t - 1))));
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
        // layer means the slant row
        // upanddown stands for whether tens digit or units digit is in this layer (1 = units digit, 0 = tens digit)
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
            result = result.add(new BigInteger(Integer.toString(layerresult)).multiply(tenpower(layer)));
            layerresult = 0;
        }
        // Judge whether the result is negative or not.
        result = result.multiply(new BigInteger(Integer.toString(sign)));
        return result;

    }
    public static void main(String[] args){
        Problem2_RectangleMul test = new Problem2_RectangleMul();
        System.out.println("Rectangular Multiplication");
        System.out.println();
        BigInteger a = new BigInteger("7000");
        BigInteger b = new BigInteger("7294");
        System.out.println("The result of " + a + " * " + b + " = "+test.RectangularMultiplication(a, b));
        System.out.println();
        a = new BigInteger("25");
        b = new BigInteger("5038385");
        System.out.println("The result of " + a + " * " + b + " = "+test.RectangularMultiplication(a, b));
        System.out.println();
        a = new BigInteger("-59724");
        b = new BigInteger("783");
        System.out.println("The result of " + a + " * " + b + " = "+test.RectangularMultiplication(a, b));
        System.out.println();
        a = new BigInteger("8516");
        b = new BigInteger("-82147953548159344");
        System.out.println("The result of " + a + " * " + b + " = "+test.RectangularMultiplication(a, b));
        System.out.println();
        a = new BigInteger("45952456856498465985");
        b = new BigInteger("98654651986546519856");
        System.out.println("The result of " + a + " * " + b + " = "+test.RectangularMultiplication(a, b));
        System.out.println();
        a = new BigInteger("-45952456856498465985");
        b = new BigInteger("-98654651986546519856");
        System.out.println("The result of " + a + " * " + b + " = "+test.RectangularMultiplication(a, b));
    }
}
