package Project1;
// Program for 2214 INFSCI 2591 Algorithm Design, Project1, Problem2, AlaCarte Multiplication
// Author: Zian Wang


import java.math.BigInteger;

public class Problem2_AlaCarte_Normal {
    // Input: Multiplier and multiplicand: x, y.
    long AlaCarte(long x, long y){
        long result = 0;
        // The while loop cannot judge whether the original (which is not divided yet) x is odd,
        // so judge it ahead of the loop.
        // If odd, add y.
        if(x % 2 == 1)
            result += y;
        while(x != 1){
            x /= 2;
            y *= 2;
            // If odd, add y.
            if(x % 2 == 1){
                result += y;
            }
        }

        return result;
    }

    public static void main(String[] args){
        Problem2_AlaCarte_Normal test = new Problem2_AlaCarte_Normal();
        System.out.println("AlaCarte Multiplication");
        System.out.println();
        long a = 7000L;
        Long b = 7294L;
        System.out.println("The result of " + a + " * " + b + " = "+test.AlaCarte(a, b));
        System.out.println();
        a = 25L;
        b = 5038385L;
        System.out.println("The result of " + a + " * " + b + " = "+test.AlaCarte(a, b));
        System.out.println();
        a = 59724L;
        b = 783L;
        System.out.println("The result of " + a + " * " + b + " = "+test.AlaCarte(a, b));
        System.out.println();
        a = 8516L;
        b = -82147953548159344L;
        System.out.println("The result of " + a + " * " + b + " = "+test.AlaCarte(a, b));
        System.out.println();
    }
}
