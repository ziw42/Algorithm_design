package Project2;
// Program for 2214 INFSCI 2591 Algorithm Design, Project2
// Author: Zian Wang

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Dijkstra_Array {
    public int[][] readArray(String path, int num){
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
                    s = br.readLine();  // The first row is attribute name, it is not int.
                    while ((s = br.readLine()) != null) {
                        i = Integer.valueOf(s.split(",")[0]);
                        j = Integer.valueOf(s.split(",")[1]);
                        adjacency[i][j] = Integer.valueOf(s.split(",")[2]);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return adjacency;
    }

    public Map Dijkstra(int n, int[][] W, int startNode){
        int i;
        int vnear = 0;
        int num = 0;
        int min;
        int[] touch = new int[n - 1];   // touch[n] stands for node n+1
        int[] length = new int[n - 1];
        int[][] F = new int[n - 1][2];

        int[] swap = new int[n];
        for(int t = 0; t < n; t++){
            swap[t] = W[0][t];
            W[0][t] = W[startNode][t];
            W[startNode][t] = swap[t];
        }
        for(int t = 0; t < n; t++){
            swap[t] = W[t][0];
            W[t][0] = W[t][startNode];
            W[t][startNode] = swap[t];
        }

        for(i = 0; i < n - 1; i++){
            touch[i] = 0;
            length[i] = W[0][i + 1];
        }

        while(num < n - 1){
            min = 999999;
            for(i = 0; i < n - 1; i++){
                if(length[i] > 0 && length[i] < min){
                    min = length[i];
                    vnear = i;
                }
            }
            F[num][0] = touch[vnear];
            F[num][1] = vnear + 1;
            for(i = 0; i < n - 1; i++){
                if(length[vnear] + W[vnear + 1][i + 1] < length[i]){
                    length[i] = length[vnear] + W[vnear + 1][i + 1];
                    touch[i] = vnear + 1;
                }
            }
            length[vnear] = -1;
            num++;
        }

        for(int t = 0; t < n - 1; t++){
            if(F[t][0] == 0 || F[t][1] == 0){
                if(F[t][0] == 0)
                    F[t][0] = startNode;
                if(F[t][1] == 0)
                    F[t][1] = startNode;
            }
            else if(F[t][0] == startNode || F[t][1] == startNode){
                if(F[t][0] == startNode)
                    F[t][0] = 0;
                if(F[t][1] == startNode)
                    F[t][1] = 0;
            }
        }
        for(int t = 0; t < n - 1; t++){
            if(touch[t] == 0)
                touch[t] = startNode;
            else if(touch[t] == startNode)
                touch[t] = 0;
        }

        Map map = new HashMap();
        map.put("F", F);
        map.put("touch", touch);
        return map;
    }

    public int[] getPath(int[] touch, int numOfNodes, int startNode, int endNode, int[][] adjacency){
        int[] path = new int[numOfNodes - 1];
        int currentNode = endNode;
        int n = 1;
        int i;
        int length = 0;
        int swap;
        for(int t = 0; t < numOfNodes - 1; t++){
            path[t] = -1;
        }
        path[0] = endNode;
        if(startNode == endNode){
            int[] r = new int[2];
            r[0] = startNode;
            r[1] = 0;
            return r;
        }
        if(endNode == 0){
            length += adjacency[startNode - 1][touch[startNode - 1]];
            currentNode = touch[startNode - 1];
            path[1] = currentNode;
        }
        while(currentNode != startNode){
            if(currentNode == 0) {
                length += adjacency[currentNode][touch[startNode - 1]];
                currentNode = touch[startNode - 1];
            }
            else {
                length += adjacency[currentNode][touch[currentNode - 1]];
                currentNode = touch[currentNode - 1];
            }
            path[n] = currentNode;
            n++;
        }
        i = 0;
        int last = n;
        n--;
        while(i < n){
            swap = path[n];
            path[n] = path[i];
            path[i] = swap;
            n--;
            i++;
        }
        path[last] = length;

        return path;
    }

    public int[][] completeAdjacency(int[][] adjacency, int numOfNodes){
        for(int i = 0; i < numOfNodes; i++){
            for(int j = 0; j < numOfNodes; j++){
                if(adjacency[i][j] == 0 && i != j){
                    adjacency[i][j] = 999999999;
                }
            }
        }
        return adjacency;
    }

    public int[][] resetAdjacency(int[][] adjacency, int numOfNodes) {
        int[][] adjacencyIni = new int[numOfNodes][numOfNodes];
        for (int t1 = 0; t1 < numOfNodes; t1++) {
            for (int t2 = 0; t2 < numOfNodes; t2++) {
                adjacencyIni[t1][t2] = adjacency[t1][t2];
            }
        }

        return adjacencyIni;
    }

    public void run(String filePath, int numOfNodes){
        int[][] adjacency = new int[numOfNodes][numOfNodes];
        int[][] adjacencyIni;
        int[][] result = new int[numOfNodes - 1][2];
        int[] touch = new int[numOfNodes - 1];
        int[] path = new int[numOfNodes - 1];
        int t3;
        int t4;

        Map resultMap = new HashMap();
        adjacency = readArray(filePath, numOfNodes);
        adjacency = completeAdjacency(adjacency, numOfNodes);
        for(int t = 0; t < numOfNodes; t++){
            adjacencyIni = resetAdjacency(adjacency, numOfNodes);
            resultMap = Dijkstra(numOfNodes, adjacencyIni, t);
            touch = (int[])resultMap.get("touch");
            result = (int[][])resultMap.get("F");
            for(int t2 = 0; t2 < numOfNodes; t2++){
                int[] temp = getPath(touch, numOfNodes, t, t2, adjacency);
                if((t == 197 && t2 == 27)|| (t == 65 && t2 == 280) || (t == 187 && t2 == 68)) {
                    System.out.println("The shortest path from " + t + " to " + t2 + " is:");
                    for(t3 = 0; t3 < temp.length && temp[t3] != -1; t3++);

                    for (t4 = 0; t4 < t3 - 2; t4++) {
                        System.out.print(temp[t4] + "-> ");
                    }
                    System.out.println(temp[t4++]);
                    System.out.println("The length of the path is: " + temp[t4]);
                    System.out.println();
                }
            }
        }
    }

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

    public static void main(String[] args){
        Dijkstra_Array x = new Dijkstra_Array();
        System.out.println("Dijkstra with two-dimension array:");

        /*long start = System.nanoTime();
        String path1 = "A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File1.csv";
        int numOfNodes1 = x.getNumOfNodes(path1);
        x.run(path1,numOfNodes1);
        long end = System.nanoTime();
        System.out.println("The 1st case takes: " + (end - start) + "nano seconds");

        start = System.nanoTime();
        String path2 = "A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File2.csv";
        int numOfNodes2 = x.getNumOfNodes(path2);
        x.run(path2,numOfNodes2);
        end = System.nanoTime();
        System.out.println("The 2nd case takes: " + (end - start) + "nano seconds");

        start = System.nanoTime();*/
        String path3 = "A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File3.csv";
        int numOfNodes3 = x.getNumOfNodes(path3);
        x.run(path3,numOfNodes3);
        /*end = System.nanoTime();
        System.out.println("The 3rd case takes: " + (end - start) + "nano seconds");

        start = System.nanoTime();
        String path4 = "A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File4.csv";
        int numOfNodes4 = x.getNumOfNodes(path4);
        x.run(path4,numOfNodes4);
        end = System.nanoTime();
        System.out.println("The 4th case takes: " + (end - start) + "nano seconds");

        start = System.nanoTime();
        String path5 = "A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File5.csv";
        int numOfNodes5 = x.getNumOfNodes(path5);
        x.run(path5,numOfNodes5);
        end = System.nanoTime();
        System.out.println("The 5th case takes: " + (end - start) + "nano seconds");

        start = System.nanoTime();
        String path6 = "A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File6.csv";
        int numOfNodes6 = x.getNumOfNodes(path6);
        x.run(path6,numOfNodes6);
        end = System.nanoTime();
        System.out.println("The 6th case takes: " + (end - start) + "nano seconds");

        start = System.nanoTime();
        String path7 = "A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File7.csv";
        int numOfNodes7 = x.getNumOfNodes(path7);
        x.run(path7,numOfNodes7);
        end = System.nanoTime();
        System.out.println("The 7th case takes: " + (end - start) + "nano seconds");

        start = System.nanoTime();
        String path8 = "A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File8.csv";
        int numOfNodes8 = x.getNumOfNodes(path8);
        x.run(path8,numOfNodes8);
        end = System.nanoTime();
        System.out.println("The 8th case takes: " + (end - start) + "nano seconds");

        start = System.nanoTime();
        String path9 = "A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File9.csv";
        int numOfNodes9 = x.getNumOfNodes(path9);
        x.run(path9,numOfNodes9);
        end = System.nanoTime();
        System.out.println("The 9th case takes: " + (end - start) + "nano seconds");

        start = System.nanoTime();
        String path10 = "A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File10.csv";
        int numOfNodes10 = x.getNumOfNodes(path10);
        x.run(path10,numOfNodes10);
        end = System.nanoTime();
        System.out.println("The 10th case takes: " + (end - start) + "nano seconds");*/


    }
}
