package Algorithm_Design_Quiz4;
// Program for 2214 INFSCI 2591 Algorithm Design, Quiz4
// Author: Zian Wang

import java.io.*;

// The code has 4 primary function:
// readGraph() is used to read the adjacency matrix from .txt file.
// isComplete() is used to judge whether the graph is complete.
// isConnected() is used to judge whether the graph is connected.
// inandoutdegree() is used to calculate the in degree, out degree and total degree of each node in the graph.

public class Quiz4 {

    // Input: Path of the file, and the size of the matrix.
    // Output: The adjacency matrix of the graph.
    public int[][] readGraph(String path, int num) {
        int[][] adjacency = new int[num][num];
        try {
            int i = 0;
            int j = 0;
            File f = new File(path);
            if (f.exists()) {
                if (f.isFile()) {
                    FileReader fr = new FileReader(path);
                    BufferedReader br = new BufferedReader(fr);
                    String s = null;
                    while ((s = br.readLine()) != null) {
                        for(j = 0; j < num; j++){
                            adjacency[i][j] = Integer.parseInt(s.split(" ")[j]);
                        }
                        i++;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return adjacency;
    }

    // Input: The adjacency matrix of the graph, the size of the matrix.
    // Output: A boolean variable that stands for whether the graph is complete, true for complete, false for not complete.
    public boolean isComplete(int[][] adjacency, int num){
        boolean result = true;
        for(int i = 0; i < num; i++){
            for(int j = 0; j < num; j++){
                if(i == j);
                else if(adjacency[i][j] == 0)
                    result = false;
            }
        }

        return result;
    }

    // Input: The adjacency matrix of the graph, the size of the matrix.
    // Output: A boolean variable that stands for whether the graph is connected, true for connected, false for not connected.
    public boolean isConnected(int[][] adjacency, int num) {
        boolean result = true;
        int[][] TC = new int[num][num];
        for (int t1 = 0; t1 < num; t1++) {
            for (int t2 = 0; t2 < num; t2++) {
                TC[t1][t2] = adjacency[t1][t2];
            }
        }
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                if (TC[j][i] > 0) {
                    for (int t = 0; t < num; t++) {
                        TC[j][t] += TC[i][t];
                    }
                }
            }
        }
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                if (i == j) ;
                else if (TC[i][j] == 0)
                    result = false;
            }
        }
        return result;
    }

    // Input: The adjacency matrix of the graph, the size of the matrix.
    // Output: An array: result[i][0] for the ist node's out degree, result[i][1] for the ist node's in degree,
    //          result[i][2] for the ist node's total degree
    public int[][] inandoutdegree(int[][] adjacency, int num) {
        int[][] result = new int[num][3];
        int outdegree = 0;
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                if (adjacency[i][j] != 0) {
                    outdegree++;
                }
            }
            result[i][0] = outdegree;
            outdegree = 0;
        }
        int indegree = 0;
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                if (adjacency[j][i] != 0) {
                    indegree++;
                }
            }
            result[i][1] = indegree;
            indegree = 0;
        }
        for (int t = 0; t < num; t++)
            result[t][2] = result[t][0] + result[t][1];
        return result;
    }


    public static void main (String[] args){
        // I nearly ran out of the time so the test cases and the print of the result may looks a litle bit messy.
        Quiz4 test = new Quiz4();
        int[][] case1 = test.readGraph("C:/Users/王梓安/IdeaProjects/Algorithm_Design/src/Algorithm_Design_Quiz4/Case1.txt", 5);
        int[][] case2 = test.readGraph("C:/Users/王梓安/IdeaProjects/Algorithm_Design/src/Algorithm_Design_Quiz4/Case2.txt", 4);
        int[][] case3 = test.readGraph("C:/Users/王梓安/IdeaProjects/Algorithm_Design/src/Algorithm_Design_Quiz4/Case3.txt", 5);
        int[][] case4 = test.readGraph("C:/Users/王梓安/IdeaProjects/Algorithm_Design/src/Algorithm_Design_Quiz4/Case4.txt", 5);
        int[][] degree;
        String[] letter = new String[10];
        letter[0] = "A";
        letter[1] = "B";
        letter[2] = "C";
        letter[3] = "D";
        letter[4] = "E";
        letter[5] = "F";
        letter[6] = "G";
        letter[7] = "H";
        letter[8] = "I";
        letter[9] = "J";

        System.out.println("Case1:");
        if(test.isComplete(case1, 5))
            System.out.println("Case1 is Complete");
        else
            System.out.println("Case1 is Not Complete");
        if(test.isConnected(case1, 5))
            System.out.println("Case1 is Connected");
        else
            System.out.println("Case1 is Not Connected");
        degree = test.inandoutdegree(case1, 5);
        for(int t = 0; t < 5; t++){
            System.out.println("The node " + letter[t] + "'s outdegree is "+ degree[t][0]);
            System.out.println("The node " + letter[t] + "'s indegree is "+ degree[t][1]);
            System.out.println("The node " + letter[t] + "'s totaldegree is "+ degree[t][2]);
        }
        System.out.println("------------------------------------------------------------------------------------");

        System.out.println("Case2:");
        if(test.isComplete(case2, 4))
            System.out.println("Case2 is Complete");
        else
            System.out.println("Case2 is Not Complete");
        if(test.isConnected(case2, 4))
            System.out.println("Case2 is Connected");
        else
            System.out.println("Case2 is Not Connected");
        degree = test.inandoutdegree(case2, 4);
        for(int t = 0; t < 4; t++){
            System.out.println("The node " + letter[t] + "'s outdegree is "+ degree[t][0]);
            System.out.println("The node " + letter[t] + "'s indegree is "+ degree[t][1]);
            System.out.println("The node " + letter[t] + "'s totaldegree is "+ degree[t][2]);
        }
        System.out.println("------------------------------------------------------------------------------------");

        System.out.println("Case3:");
        if(test.isComplete(case3, 5))
            System.out.println("Case3 is Complete");
        else
            System.out.println("Case3 is Not Complete");
        if(test.isConnected(case3, 5))
            System.out.println("Case3 is Connected");
        else
            System.out.println("Case3 is Not Connected");
        degree = test.inandoutdegree(case3, 5);
        for(int t = 0; t < 5; t++){
            System.out.println("The node " + letter[t] + "'s outdegree is "+ degree[t][0]);
            System.out.println("The node " + letter[t] + "'s indegree is "+ degree[t][1]);
            System.out.println("The node " + letter[t] + "'s totaldegree is "+ degree[t][2]);
        }
        System.out.println("------------------------------------------------------------------------------------");

        System.out.println("Case4:");
        if(test.isComplete(case4, 5))
            System.out.println("Case4 is Complete");
        else
            System.out.println("Case4 is Not Complete");
        if(test.isConnected(case4, 5))
            System.out.println("Case4 is Connected");
        else
            System.out.println("Case4 is Not Connected");
        degree = test.inandoutdegree(case4, 5);
        for(int t = 0; t < 5; t++){
            System.out.println("The node " + letter[t] + "'s outdegree is "+ degree[t][0]);
            System.out.println("The node " + letter[t] + "'s indegree is "+ degree[t][1]);
            System.out.println("The node " + letter[t] + "'s totaldegree is "+ degree[t][2]);
        }
        System.out.println("------------------------------------------------------------------------------------");

    }

}
