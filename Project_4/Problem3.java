package Project4;
// Program for 2214 INFSCI 2591 Algorithm Design, Project4 Problem3
// Author: Zian Wang

public class Problem3 {

    public int n;
    public int[][] Queens;
    public int numOfSolutions;

    public Problem3(int n) {
        this.n = n;
        numOfSolutions = 0;
        Queens = new int[n][3];
        for(int t = 0; t < n; t++) {
            Queens[t][0] = -1;
            Queens[t][1] = -1;
            Queens[t][2] = -1;
        }
    }

    public int abs(int a) {
        if(a < 0) {
            return -1 * a;
        }
        return a;
    }

    public int factorial(int n) {
        int result = 1;
        for(int t = 1; t < n + 1; t++) {
            result *= t;
        }
        return result;
    }

    public boolean promising(int i) {
        boolean signal = true;
        boolean equalX;
        boolean equalY;
        boolean equalZ;
        boolean diag;
        boolean diag2;
        int k = 0;
        while(k < i && signal) {
            equalX = (Queens[k][0] == Queens[i][0]);
            equalY = (Queens[k][1] == Queens[i][1]);
            equalZ = (Queens[k][2] == Queens[i][2]);
            if((equalX && equalY) || (equalX && equalZ) || (equalY && equalZ)) {
                signal = false;
            }
            diag = ((abs(Queens[k][0] - Queens[i][0]) == abs(Queens[k][1] - Queens[i][1])) && (abs(Queens[k][0] - Queens[i][0]) == abs(Queens[k][2] - Queens[i][2])) && (abs(Queens[k][1] - Queens[i][1]) == abs(Queens[k][2] - Queens[i][2])));
            if(diag) {
                signal = false;
            }
            diag2 = (((abs(Queens[k][0] - Queens[i][0]) == abs(Queens[k][1] - Queens[i][1])) && equalZ) || ((abs(Queens[k][2] - Queens[i][2]) == abs(Queens[k][1] - Queens[i][1])) && equalX) || ((abs(Queens[k][0] - Queens[i][0]) == abs(Queens[k][2] - Queens[i][2])) && equalY));
            if(diag2) {
                signal = false;
            }
            k++;
        }
        return signal;
    }

    public void queens3D(int i) {
        if(promising(i)) {
            if(i == n - 1) {
                /*for(int t = 0; t < n; t++) {
                    System.out.println(Queens[t][0] + " " + Queens[t][1] + " " + Queens[t][2]);
                }*/
                numOfSolutions ++;
                //System.out.println("-------------------------");
            }
            else {
                for(int x = 1; x < n + 1; x++) {
                    for(int y = 1; y < n + 1; y++) {
                        for(int z = 1; z < n + 1; z++) {
                            Queens[i + 1][0] = x;
                            Queens[i + 1][1] = y;
                            Queens[i + 1][2] = z;
                            queens3D(i + 1);
                        }
                    }
                }
            }
        }
    }

    public static void run(int n) {
        Problem3 a = new Problem3(n);
        // The top level call is n = -1
        a.queens3D(-1);
        a.numOfSolutions /= a.factorial(n);
        System.out.println("n=" + n + "     There are " + a.numOfSolutions + " solutions.");
        System.out.println();
    }

    public static void main(String args[]) {
        run(2);
        run(3);
        run(4);
        run(5);
    }

}
