package Project4;
// Program for 2214 INFSCI 2591 Algorithm Design, Project4 Problem1
// Author: Zian Wang

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Problem1 {

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
        int[][] adjacency;
        int[][] adjacencyIni;
        int[][] result = new int[numOfNodes - 1][2];
        int[] touch;
        int[][] betweenness = new int[numOfNodes][2];
        int t3;

        Map resultMap = new HashMap();
        adjacency = readArray(filePath, numOfNodes);
        adjacency = completeAdjacency(adjacency, numOfNodes);
        for(int t = 0; t < numOfNodes; t++) {
            betweenness[t][0] = t;
            betweenness[t][1] = 0;
        }

        for(int t = 0; t < numOfNodes; t++) {
            adjacencyIni = resetAdjacency(adjacency, numOfNodes);
            resultMap = Dijkstra(numOfNodes, adjacencyIni, t);
            touch = (int[])resultMap.get("touch");
            result = (int[][])resultMap.get("F");
            for(int t2 = 0; t2 < numOfNodes - 1; t2++) {
                int[] path = getPath(touch, numOfNodes, t, t2, adjacency);
                for(t3 = 0; t3 < path.length && path[t3] != -1; t3++);
                if(path[t3 - 1] > 999999000);
                else {
                    for (int t4 = 0; t4 < t3 - 1; t4++) {
                        betweenness[path[t4]][1]++;
                    }
                }
            }
        }

        int temp1;
        int temp2;
        for(int t = 0; t < numOfNodes; t++) {
            for(int t1 = 0; t1 < numOfNodes - 1; t1++) {
                if(betweenness[t1][1] < betweenness[t1 + 1][1]) {
                    temp1 = betweenness[t1][0];
                    temp2 = betweenness[t1][1];
                    betweenness[t1][0] = betweenness[t1 + 1][0];
                    betweenness[t1][1] = betweenness[t1 + 1][1];
                    betweenness[t1 + 1][0] = temp1;
                    betweenness[t1 + 1][1] = temp2;
                }
            }
        }
        for(int t = 0; t < 20; t++) {
            System.out.println(betweenness[t][0] + "           " + betweenness[t][1]);
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
                    String s;
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
        Problem1 x = new Problem1();
        String path = "A:/Algorithm_Design/Project4/Project 4_Problem 1_InputData.csv";
        int numOfNodes = x.getNumOfNodes(path);
        System.out.println("Here are the top 20 betweenness centrality vertices in the graph in descending order:");
        System.out.println();
        System.out.println("NodeId      Betweenness centrality");
        x.run(path, numOfNodes);
    }
}
