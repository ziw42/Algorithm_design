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

public class Problem2_AlaCarte {
    // Input: Multiplier and multiplicand: x, y.
    BigInteger AlaCarte(BigInteger x, BigInteger y){
        int sign = 1;
        if(x.compareTo(new BigInteger("0")) == -1){
            sign *= -1;
            x = x.multiply(new BigInteger("-1"));
        }
        if(y.compareTo(new BigInteger("0")) == -1){
            sign *= -1;
            y = y.multiply(new BigInteger("-1"));
        }
        BigInteger result = new BigInteger("0");
        // The while loop cannot judge whether the original (which is not divided yet) x is odd,
        // so judge it ahead of the loop.
        // If odd, add y.
        BigInteger[] t = x.divideAndRemainder(new BigInteger("2"));
        if(t[1].compareTo(new BigInteger("1")) == 0)
            result.add(y);
        while(x.compareTo(new BigInteger("1")) != 0){
            x = x.divide(new BigInteger("2"));
            y = y.multiply(new BigInteger("2"));
            // If odd, add y.
            t = x.divideAndRemainder(new BigInteger("2"));
            if(t[1].compareTo(new BigInteger("1")) == 0){
                result = result.add(y);
            }
        }
        result = result.multiply(new BigInteger(Integer.toString(sign)));
        return result;
    }

    public static void main(String[] args){
        Problem2_AlaCarte test = new Problem2_AlaCarte();
        System.out.println("AlaCarte Multiplication");
        System.out.println();
        BigInteger a = new BigInteger("7000");
        BigInteger b = new BigInteger("7294");
        System.out.println("The result of " + a + " * " + b + " = "+test.AlaCarte(a, b));
        System.out.println();
        a = new BigInteger("25");
        b = new BigInteger("5038385");
        System.out.println("The result of " + a + " * " + b + " = "+test.AlaCarte(a, b));
        System.out.println();
        a = new BigInteger("-59724");
        b = new BigInteger("783");
        System.out.println("The result of " + a + " * " + b + " = "+test.AlaCarte(a, b));
        System.out.println();
        a = new BigInteger("8516");
        b = new BigInteger("-82147953548159344");
        System.out.println("The result of " + a + " * " + b + " = "+test.AlaCarte(a, b));
        System.out.println();
        a = new BigInteger("45952456856498465985");
        b = new BigInteger("98654651986546519856");
        System.out.println("The result of " + a + " * " + b + " = "+test.AlaCarte(a, b));
        System.out.println();
        a = new BigInteger("-45952456856498465985");
        b = new BigInteger("-98654651986546519856");
        System.out.println("The result of " + a + " * " + b + " = "+test.AlaCarte(a, b));
    }
}
