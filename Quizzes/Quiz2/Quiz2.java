package Algorithm_Design_Quiz2;

// Program for 2214 INFSCI 2591 Algorithm Design, Quiz2
// Author: Zian Wang
public class Quiz2 {

    // I put the matrix in the parameter list because if I set the matrix globally, it will be hard to test all the case
    // in one time. I have to quit the program and reset the matrix every time.
    //
    // Actually, I want to make a recursive code first because this is the Quiz of Divide-and-Conquer. However, the book
    // says the iterative code is more efficient and the question mentioned write an efficient algorithm. So I write an
    // iterative one instead.
    //
    // The logic of the code is we compare the most left bottom value to the target, if it is equal, then record the
    // index and return, if not: if the value is bigger, we compare the upper one, if the value is smaller, we compare
    // the righter one, when we find the target, we record the index. Because the matrix is sorted, so this method can
    // always make the value to the correct direction (bigger or smaller), and if in this direction the target is not
    // founded, the target is not in the matrix.
    //
    // Input: the matrix, and the number we want to find: target.
    // Output: If found, return the index of the target in the matrix. If not, return -1, -1 (impossible value).
    public int[] matrix_search(int[][] matrix, int target){
        int length = matrix[0].length;
        int width = matrix.length;
        int[] location = new int[2];  // Location is the index of the target if it is in the matrix. If it is not,
                                      // the index will be -1, -1.
        location[0] = -1;
        location[1] = -1;
        int x = 0;    // x is the index of the column, and y is the index of the row. (Like coordinate system.)
        int y = width - 1;
        while(x < length && y >= 0){  // Core part of the algorithm, iterative.
            if(matrix[y][x] > target){
                y--;
            }
            else if(matrix[y][x] < target){
                x++;
            }
            else if(matrix[y][x] == target){
                location[0] = y;
                location[1] = x;
                break;
            }
        }

        return location;

    }

    public static void main(String[] args) {
        // Make test cases.
        int[][] test1 = new int[3][3];
        int num = 1;
        for(int t1 = 0; t1 < 3; t1++){
            for(int t2 = 0; t2 < 3; t2++){
                test1[t1][t2] = num;
                num++;
            }
        }
        int[][] test2 = new int[3][7];
        test2[0][0] = 2;
        test2[0][1] = 4;
        test2[0][2] = 9;
        test2[0][3] = 14;
        test2[0][4] = 14;
        test2[0][5] = 15;
        test2[0][6] = 18;
        test2[1][0] = 21;
        test2[1][1] = 27;
        test2[1][2] = 31;
        test2[1][3] = 35;
        test2[1][4] = 42;
        test2[1][5] = 45;
        test2[1][6] = 50;
        test2[2][0] = 54;
        test2[2][1] = 59;
        test2[2][2] = 64;
        test2[2][3] = 69;
        test2[2][4] = 82;
        test2[2][5] = 84;
        test2[2][6] = 84;

        int[][] test3 = new int[5][8];
        test3[0][0] = 3;
        test3[0][1] = 15;
        test3[0][2] = 21;
        test3[0][3] = 24;
        test3[0][4] = 83;
        test3[0][5] = 87;
        test3[0][6] = 88;
        test3[0][7] = 93;
        test3[1][0] = 178;
        test3[1][1] = 319;
        test3[1][2] = 541;
        test3[1][3] = 669;
        test3[1][4] = 770;
        test3[1][5] = 793;
        test3[1][6] = 848;
        test3[1][7] = 970;
        test3[2][0] = 1439;
        test3[2][1] = 1546;
        test3[2][2] = 1853;
        test3[2][3] = 2124;
        test3[2][4] = 2234;
        test3[2][5] = 2459;
        test3[2][6] = 2518;
        test3[2][7] = 2978;
        test3[3][0] = 3111;
        test3[3][1] = 3406;
        test3[3][2] = 3490;
        test3[3][3] = 3669;
        test3[3][4] = 3796;
        test3[3][5] = 3936;
        test3[3][6] = 4112;
        test3[3][7] = 4776;
        test3[4][0] = 5277;
        test3[4][1] = 5667;
        test3[4][2] = 6067;
        test3[4][3] = 6555;
        test3[4][4] = 7890;
        test3[4][5] = 8056;
        test3[4][6] = 8234;
        test3[4][7] = 9968;



        Quiz2 a = new Quiz2();
        int[] location1 = new int[2];
        int[] location2 = new int[2];
        int[] location3 = new int[2];
        location1 = a.matrix_search(test1, 8);
        location2 = a.matrix_search(test2, 45);
        location3 = a.matrix_search(test3, 2356);
        // Print the result.
        System.out.println("The result of test1 is: " + location1[0] + " , " + location1[1] + " .");
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println();
        System.out.println("The result of test2 is: " + location2[0] + " , " + location2[1] + " .");
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println();
        System.out.println("The result of test3 is: " + location3[0] + " , " + location3[1] + " .");
        System.out.println();
        System.out.println("Because the result of the case3 is -1 -1, this means the target is not in the matrix");
    }
}
