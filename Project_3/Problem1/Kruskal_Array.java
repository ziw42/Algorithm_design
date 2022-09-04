package Project3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;
import java.util.Stack;

public class Kruskal_Array {
    // Function to get the num of Nodes and Edges.
    // Input: the path of the file.
    // Output: an array, the first element is the number of the nodes, the second element is the number of the edges.

    public Stack[] initial(int n){
        Stack[] result = new Stack[n];
        for(int t = 0; t < n; t++){
            result[t] = new Stack();
            result[t].push(t);
        }
        return result;
    }

    // Search every set in V and return the index in V of the set which contains target.
    public int find(Stack[] V, int n, int target){
        for(int t = 0; t < n; t++){
            if(V[t].search(target) != -1){
                return t;
            }
        }
        return -1;
    }

    public void merge(Stack[] V, int i, int j){
        while(!V[i].isEmpty()){
            V[j].push(V[i].pop());
        }
    }

    public boolean stop(Stack[] V, int n){
        boolean s = true;
        int num = 0;
        int t = 0;
        while(s && num < n){
            if(!V[num].isEmpty()){
                t++;
            }
            if(t == 2){
                s = false;
            }
            num++;
        }

        return s;
    }

    public int[][] kruskal(int n, int[][] adjacency){
        int i, j;
        int t = 0;
        int m = 0;
        for(int t1 = 0; t1 < n; t1++){
            for(int t2 = 0; t2 < n; t2++){
                if(adjacency[t1][t2] < 9999999){
                    m++;
                }
            }
        }
        m /= 2;
        int[][] E = new int[m][2];
        int[][] F = new int[n - 1][2];
        // The index of the set which contains i and j.
        int set_i;
        int set_j;
        int num = 0; // The index of edges in while loop.
        int amount = 0; // The amount of the edges in the F.
        // Set m edges in undirected graph to E.
        for(i = 0; i < n; i++){
            for(j = i + 1; j < n; j++){
                if(adjacency[i][j] < 9999999){
                    E[t][0] = i;
                    E[t][1] = j;
                    t++;
                }
            }
        }

        // Sort the edges by weight in non-decreasing order.
        int temp_1, temp_2;
        for(t = 0; t < m - 1; t++){
            for(int t2 = 0; t2 < m - 1; t2++){
                if(adjacency[E[t2][0]][E[t2][1]] > adjacency[E[t2 + 1][0]][E[t2 + 1][1]]){
                    temp_1 = E[t2][0];
                    temp_2 = E[t2][1];
                    E[t2][0] = E[t2 + 1][0];
                    E[t2][1] = E[t2 + 1][1];
                    E[t2 + 1][0] = temp_1;
                    E[t2 + 1][1] = temp_2;
                }
            }
        }
        // Initial vertices sets V. Because we now have n points, there are n sets: V[0, ..., n-1].
        Stack[] V = initial(n);

        //Set F is empty.
        for(t = 0; t < n - 1; t++){
            F[t][0] = -1;
            F[t][1] = -1;
        }

        while(!stop(V, n)){

            i = E[num][0];
            j = E[num][1];
            //System.out.println(" " +i+" "+j);
            set_i = find(V, n, i);
            set_j = find(V, n, j);
            if(set_i != set_j){
                merge(V, set_i, set_j);
                F[amount][0] = i;
                F[amount][1] = j;
                amount++;
            }
            num++;
        }
        return F;
    }

    public static void main(String[] args) {
        System.out.println("Kruskal using two-dimension array");
        String path1 = "C:/Users/王梓安/Downloads/Project3_G1_Data.csv";
        String path2 = "C:/Users/王梓安/Downloads/Project3_G2_Data.csv";
        Map a = Merge_Graph.merge(path1, path2);
        int num1 = (int)a.get("num1");
        int num2 = (int)a.get("num2");
        int numOfRepetition = (int)a.get("numOfRepetition");
        int[][] adjacency = Merge_Graph.readArray(a);
        Kruskal_Array aobject = new Kruskal_Array();
        int[][]F = aobject.kruskal(num1 + num2 - numOfRepetition, adjacency);
        int totalDistance = 0;
        System.out.println("Node:       Node:       Distance:");
        for(int i = 0; i < F.length; i++) {
            if(F[i][0] < 10)
                System.out.print(F[i][0] + "           ");
            else if(F[i][0] > 99 && F[i][0] <999)
                System.out.print(F[i][0] + "         ");
            else if(F[i][0] >= 999 && F[i][0] <9999)
                System.out.print(F[i][0] + "        ");
            else if(F[i][0] >= 9999)
                System.out.print(F[i][0] + "       ");
            else
                System.out.print(F[i][0] + "          ");
            if(F[i][1] < 10)
                System.out.print(F[i][1]+"           ");
            else if(F[i][1] > 99 && F[i][1] < 999)
                System.out.print(F[i][1]+"         ");
            else if(F[i][1] >= 999 && F[i][1] <9999)
                System.out.print(F[i][1]+"        ");
            else if(F[i][1] >= 9999)
                System.out.print(F[i][1]+"       ");
            else
                System.out.print(F[i][1]+"          ");
            System.out.print(adjacency[F[i][0]][F[i][1]]);
            totalDistance += adjacency[F[i][0]][F[i][1]];
            System.out.println();
        }
        System.out.println("TotalDistance is: " + totalDistance);
    }
}

