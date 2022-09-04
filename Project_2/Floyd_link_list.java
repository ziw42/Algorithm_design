package Project2;
// Program for 2214 INFSCI 2591 Algorithm Design, Project2
// Author: Zian Wang

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Floyd_link_list {
    public int getNumOfNodes(String path){
        int num = -1;
        File f = new File(path);
        try {
            if (f.exists()) {
                if (f.isFile()) {
                    FileReader fr = new FileReader(path);
                    BufferedReader br = new BufferedReader(fr);
                    String s = null;
                    s = br.readLine();  // The first row is attribute name, it is not int.
                    while ((s = br.readLine()) != null) {
                        num = Integer.valueOf(s.split(",")[0]);
                    }
                }
            }
        }
        catch(Exception e){
            e.getMessage();
        }
        num++;
        return num;
    }

    public link_list[] readLinkList(String path, int numOfNodes){
        link_list[] result = new link_list[numOfNodes];
        try {
            int node;
            int connectedNode;
            int weight;
            int currentVertex = -1;
            int num = -1;
            link_list vertex = new link_list(-1);

            File f = new File(path);
            if (f.exists()) {
                if (f.isFile()) {
                    FileReader fr = new FileReader(path);
                    BufferedReader br = new BufferedReader(fr);
                    String s = null;
                    s = br.readLine();  // The first row is attribute name, it is not int.
                    while ((s = br.readLine()) != null) {
                        node = Integer.valueOf(s.split(",")[0]);
                        connectedNode = Integer.valueOf(s.split(",")[1]);
                        weight = Integer.valueOf(s.split(",")[2]);
                        if(node != currentVertex){
                            vertex = new link_list(node);
                            currentVertex = node;
                            num++;
                            result[num] = vertex;
                        }
                        vertex.setNext(connectedNode, weight);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        /*link_list p;
        for(int t = 0; t < numOfNodes; t++){
            p = result[t];
            while(p.weight != -1){
                System.out.print(p.vertex + " ");
                p = p.next;
            }
            System.out.print(p.vertex);
            System.out.println();
        }*/
        return result;
    }

    public int getLength(int startNode, int target, link_list[] V){
        int length = 9999999;
        link_list currentNode = V[startNode];
        while(currentNode.next != null && currentNode.next.vertex != V[target].vertex){
            currentNode = currentNode.next;
        }
        if(currentNode.next != null){
            length = currentNode.weight;
        }

        return length;
    }

    public Map Floyd(int n, link_list[] V){
        int[][] P = new int[n][n];
        int[][] D = new int[n][n];
        int i, j, k;
        for(i = 0; i < n; i++)
            for(j = 0; j < n; j++)
                P[i][j] = -1;

        for(i = 0; i < n; i++)
            for (j = 0; j < n; j++)
                D[i][j] = getLength(i, j, V);

        for(i = 0; i < n; i++)
            D[i][i] = 0;

        for(k = 0; k < n; k++){
            /*System.out.println(k);*/
            for(i = 0; i < n; i++){
                for(j = 0; j < n; j++){
                    if(D[i][k] + D[k][j] < D[i][j]){
                        P[i][j] = k;
                        D[i][j] = D[i][k] + D[k][j];
                    }
                }
            }
        }

        Map map = new HashMap();
        map.put("D", D);
        map.put("P", P);

        return map;
    }

    public void getPath(int[][] P, int startNode, int endNode){
        if(P[startNode][endNode] != -1){
            getPath(P, startNode, P[startNode][endNode]);
            System.out.print(P[startNode][endNode] + "-> ");
            getPath(P, P[startNode][endNode], endNode);
        }
    }

    public void calPath(int[][] P, int startNode, int endNode){
        if(P[startNode][endNode] != -1){
            calPath(P, startNode, P[startNode][endNode]);
            /*System.out.print(P[startNode][endNode] + " ");*/
            calPath(P, P[startNode][endNode], endNode);
        }
    }

    public void printPath(int[][] P, int startNode, int endNode){
        System.out.print(startNode + "-> ");
        getPath(P, startNode, endNode);
        System.out.println(endNode);
    }

    public void run(String filePath){
        String path = filePath;
        int numOfNodes = getNumOfNodes(path);
        link_list[] V = readLinkList(path, numOfNodes);
        Map result = Floyd(numOfNodes, V);
        int[][] P = (int[][])result.get("P");
        int[][] D = (int[][])result.get("D");
        for(int t = 0; t < numOfNodes; t++){
            for(int t2 = 0; t2 < numOfNodes; t2++){
                calPath(P, t, t2);
            }
        }
    }

    public void runTest(String filePath, int[] startTestNode, int[] endTestNode){
        String path = filePath;
        int numOfNodes = getNumOfNodes(path);
        link_list[] V = readLinkList(path, numOfNodes);
        Map result = Floyd(numOfNodes, V);
        int[][] P = (int[][])result.get("P");
        int[][] D = (int[][])result.get("D");
        for(int t = 0; t < 3; t++){
            System.out.println("The path from " + startTestNode[t] + " to " + endTestNode[t] + " is:");
            printPath(P, startTestNode[t], endTestNode[t]);
            System.out.println("The length of the path is:" + D[startTestNode[t]][endTestNode[t]]);
            System.out.println();
        }
    }

    public static void main(String[] args) {

        Floyd_link_list x = new Floyd_link_list();

        System.out.println("Floyd with link list");

        int[] testStartNode = {197, 65, 187};
        int[] testEndNode = {27, 280, 68};

        x.runTest("A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File3.csv", testStartNode, testEndNode);

        /*long start = System.nanoTime();
        x.run("A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File1.csv");
        long end = System.nanoTime();
        System.out.println("The 1st case takes: " + (end - start) + " nano seconds.");

        start = System.nanoTime();
        x.run("A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File2.csv");
        end = System.nanoTime();
        System.out.println("The 2nd case takes: " + (end - start) + " nano seconds.");

        start = System.nanoTime();
        x.run("A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File3.csv");
        end = System.nanoTime();
        System.out.println("The 3rd case takes: " + (end - start) + " nano seconds.");

        start = System.nanoTime();
        x.run("A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File4.csv");
        end = System.nanoTime();
        System.out.println("The 4th case takes: " + (end - start) + " nano seconds.");

        start = System.nanoTime();
        x.run("A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File5.csv");
        end = System.nanoTime();
        System.out.println("The 5th case takes: " + (end - start) + " nano seconds.");

        start = System.nanoTime();
        x.run("A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File6.csv");
        end = System.nanoTime();
        System.out.println("The 6th case takes: " + (end - start) + " nano seconds.");

        start = System.nanoTime();
        x.run("A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File7.csv");
        end = System.nanoTime();
        System.out.println("The 7th case takes: " + (end - start) + " nano seconds.");

        start = System.nanoTime();
        x.run("A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File8.csv");
        end = System.nanoTime();
        System.out.println("The 8th case takes: " + (end - start) + " nano seconds.");

        start = System.nanoTime();
        x.run("A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File9.csv");
        end = System.nanoTime();
        System.out.println("The 9th case takes: " + (end - start) + " nano seconds.");

        start = System.nanoTime();
        x.run("A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File10.csv");
        end = System.nanoTime();
        System.out.println("The 10th case takes: " + (end - start) + " nano seconds.");*/


    }

}
