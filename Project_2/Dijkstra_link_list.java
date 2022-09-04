package Project2;
// Program for 2214 INFSCI 2591 Algorithm Design, Project2
// Author: Zian Wang

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Dijkstra_link_list {

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

    public Map Dijkstra(int n, link_list[] V, int startNode){
        int i;
        int vnear = 0;
        int num = 0;
        int min;
        int[] touch = new int[n - 1];   // touch[n] stands for node n+1
        int[] length = new int[n - 1];
        int[][] F = new int[n - 1][2];
        link_list swap = V[0];
        V[0] = V[startNode];
        V[startNode] = swap;
        /*link_list temp = V[0];
        while(temp.next != null){
            System.out.print(temp.vertex + " ");
            temp =  temp.next;
        }
        System.out.println();*/
        for(i = 0; i < n - 1; i++){
            touch[i] = 0;
            length[i] = getLength(i + 1, 0, V);
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
                int lengthA = getLength(vnear + 1, i + 1, V);
                if(length[vnear] + lengthA < length[i]){
                    length[i] = length[vnear] + lengthA;
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

    public int[] getPath(int[] touch, int numOfNodes, int startNode, int endNode, link_list[] V){
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
            length += getLength(startNode -1, touch[startNode - 1], V);
            currentNode = touch[startNode - 1];
            path[1] = currentNode;
        }
        while(currentNode != startNode){
            if(currentNode == 0) {
                length += getLength(currentNode, touch[startNode - 1], V);
                currentNode = touch[startNode - 1];
            }
            else {
                length += getLength(currentNode, touch[currentNode - 1], V);
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

    public link_list[] resetLinkList(link_list[] V, int numOfNodes){
        link_list[] result = new link_list[numOfNodes];
        link_list currentNodeV;
        link_list currentNodeR;
        for(int t = 0; t < numOfNodes; t++){
            result[t] = new link_list(-1);
            result[t].vertex = V[t].vertex;
            currentNodeV = V[t];
            currentNodeR = result[t];
            while(currentNodeV.next != null){
                currentNodeR.next = new link_list(currentNodeV.next.vertex);
                currentNodeR.weight = currentNodeV.weight;
                currentNodeV = currentNodeV.next;
                currentNodeR = currentNodeR.next;
            }
        }
        return result;
    }

    public void run(String filePath, int numOfNodes){
        link_list[] V = new link_list[numOfNodes];
        link_list[] VIni;
        int[][] result = new int[numOfNodes - 1][2];
        int[] touch = new int[numOfNodes - 1];
        int[] path = new int[numOfNodes - 1];
        int t3;
        int t4;

        Map resultMap = new HashMap();
        V = readLinkList(filePath, numOfNodes);
        for(int t = 0; t < numOfNodes; t++){
            VIni = resetLinkList(V, numOfNodes);
            resultMap = Dijkstra(numOfNodes, VIni, t);
            touch = (int[])resultMap.get("touch");
            result = (int[][])resultMap.get("F");
            for(int t2 = 0; t2 < numOfNodes; t2++){
                int[] temp = getPath(touch, numOfNodes, t, t2, V);
                if(filePath.equals("A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File3.csv")){
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
    }

    public static void main(String[] args){



        Dijkstra_link_list x = new Dijkstra_link_list();

        String path;
        int numOfNodes;
        System.out.println("Dijkstra with link list");

        /*long start = System.nanoTime();
        path = "A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File1.csv";
        numOfNodes = x.getNumOfNodes(path);
        x.readLinkList(path, numOfNodes);
        x.run(path, x.getNumOfNodes(path));
        long end = System.nanoTime();
        System.out.println("The 1st file takes: " + (end - start) + "nano seconds");

        start = System.nanoTime();
        path = "A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File2.csv";
        numOfNodes = x.getNumOfNodes(path);
        x.readLinkList(path, numOfNodes);
        x.run(path, x.getNumOfNodes(path));
        end = System.nanoTime();
        System.out.println("The 2nd file takes: " + (end - start) + "nano seconds");

        start = System.nanoTime();*/
        path = "A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File3.csv";
        numOfNodes = x.getNumOfNodes(path);
        x.readLinkList(path, numOfNodes);
        x.run(path, x.getNumOfNodes(path));
        /*end = System.nanoTime();
        System.out.println("The 3rd file takes: " + (end - start) + "nano seconds");

        start = System.nanoTime();
        path = "A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File4.csv";
        numOfNodes = x.getNumOfNodes(path);
        x.readLinkList(path, numOfNodes);
        x.run(path, x.getNumOfNodes(path));
        end = System.nanoTime();
        System.out.println("The 4th file takes: " + (end - start) + "nano seconds");


        start = System.nanoTime();
        path = "A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File5.csv";
        numOfNodes = x.getNumOfNodes(path);
        x.readLinkList(path, numOfNodes);
        x.run(path, x.getNumOfNodes(path));
        end = System.nanoTime();
        System.out.println("The 5th file takes: " + (end - start) + "nano seconds");

        start = System.nanoTime();
        path = "A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File6.csv";
        numOfNodes = x.getNumOfNodes(path);
        x.readLinkList(path, numOfNodes);
        x.run(path, x.getNumOfNodes(path));
        end = System.nanoTime();
        System.out.println("The 6th file takes: " + (end - start) + "nano seconds");

        start = System.nanoTime();
        path = "A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File7.csv";
        numOfNodes = x.getNumOfNodes(path);
        x.readLinkList(path, numOfNodes);
        x.run(path, x.getNumOfNodes(path));
        end = System.nanoTime();
        System.out.println("The 7th file takes: " + (end - start) + "nano seconds");

        start = System.nanoTime();
        path = "A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File8.csv";
        numOfNodes = x.getNumOfNodes(path);
        x.readLinkList(path, numOfNodes);
        x.run(path, x.getNumOfNodes(path));
        end = System.nanoTime();
        System.out.println("The 8th file takes: " + (end - start) + "nano seconds");

        start = System.nanoTime();
        path = "A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File9.csv";
        numOfNodes = x.getNumOfNodes(path);
        x.readLinkList(path, numOfNodes);
        x.run(path, x.getNumOfNodes(path));
        end = System.nanoTime();
        System.out.println("The 9th file takes: " + (end - start) + "nano seconds");


        start = System.nanoTime();
        path = "A:/Algorithm_Design/Project2/Project2_Input_Files/Project2_Input_File10.csv";
        numOfNodes = x.getNumOfNodes(path);
        x.readLinkList(path, numOfNodes);
        x.run(path, x.getNumOfNodes(path));
        end = System.nanoTime();
        System.out.println("The 10th file takes: " + (end - start) + "nano seconds");*/

    }
}
