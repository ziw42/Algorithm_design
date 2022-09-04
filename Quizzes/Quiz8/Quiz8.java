package Algorithm_Design_Quiz8;
// Program for 2214 INFSCI 2591 Algorithm Design, Quiz8
// Author: Zian Wang

public class Quiz8 {

    // n is the number of the queens.
    // col[i] stands for which column the ith row's queen is on.
    public int n;
    public int[] col;
    public int numOfSolutions;

    // n stands for the number of the Queens.
    public Quiz8(int n){
        this.n = n;
        this.col = new int[n];
        this.numOfSolutions = 0;
    }

    // The function used to calculate the absolute value.
    // Input: int x.
    // Output: the absolute value of x.
    public int abs(int x){
        if(x >= 0)
            return x;
        else
            return -1 * x;
    }

    // The promising function.
    // Input: the index of row: i.
    // Output: whether the candidate sub-solution on ith row is promising.
    public boolean promising(int i){
        boolean p = true;
        int k = 1;

        while(p && k < i){
            if(col[i - 1] == col[k - 1] || abs(col[i - 1] - col[k - 1]) == (i - k)){
                p = false;
            }
            k++;
        }

        return p;
    }

    // Algorithm 5.1
    public void queens(int v){
        if(promising(v)){
            if(v == n){
                numOfSolutions++;
            }
            else{
                for(int j = 0; j < n; j++){
                    col[v] = j + 1;
                    queens(v + 1);
                }
            }
        }
    }

    // Reset the number of the solutions.
    public void reset(){
        this.numOfSolutions = 0;
    }

    public static void main(String args[]){
        int numOfQueens = 4;
        Quiz8 a = new Quiz8(numOfQueens);
        a.queens(0);
        System.out.println("When we have " + numOfQueens + " queens, there are " + a.numOfSolutions + " solutions.");
        System.out.println();
        a.reset();

        numOfQueens = 8;
        a = new Quiz8(numOfQueens);
        a.queens(0);
        System.out.println("When we have " + numOfQueens + " queens, there are " + a.numOfSolutions + " solutions.");
        System.out.println();
        a.reset();

        numOfQueens = 10;
        a = new Quiz8(numOfQueens);
        a.queens(0);
        System.out.println("When we have " + numOfQueens + " queens, there are " + a.numOfSolutions + " solutions.");
        System.out.println();
        a.reset();

        numOfQueens = 12;
        a = new Quiz8(numOfQueens);
        a.queens(0);
        System.out.println("When we have " + numOfQueens + " queens, there are " + a.numOfSolutions + " solutions.");
        System.out.println();
        a.reset();
    }
}
