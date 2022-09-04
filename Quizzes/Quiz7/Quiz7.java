package Algorithm_Design_Quiz7;

// Program for 2214 INFSCI 2591 Algorithm Design, Quiz7
// Author: Zian Wang

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Quiz7 {
    // This program has three primary functions, readGraph() is used to read the .csv file. The getLength() function is used to
    // get the length from the start node to the end node. The prim() function is the prim algorithm.

    // This function is used to read the .csv file.
    // Input: the path of the file. The number of the nodes in the file: num.
    // Output: the link list which stores the adjacency matrix of the graph in the file.

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

    // Core part of this program: the Prim Algorithm.
    // Input: the number of the nodes: n, the link list of the graph: adjacency_list.
    // Output: the edge set which stores all the edges in the minimum-spanning tree.

    // This function does not need to input the start node because I set node 0 is always the start node which is
    // required in the instruction. If we need to set other nodes as the start node, we can just switch them in the
    // .csv file.
    public int[][] prim(int n, link_list[] adjacency_list){
        int i;
        int vnear = 0;  // Must initialize vnear a meaningless value, or will be an error later.
        // This won't cause a problem because vnear is definitely be reassigned in row 52.
        int[][] F = new int[n - 1][2];  // In each row, the first element is one end of the edge, the second element is
        // the other end of the edge.
        int nearest[] = new int[n - 1];
        int distance[] = new int[n - 1];
        int t = 0;
        int min;
        int Fi = 0; // Index of F, shows which row should the edge be added to.

        for(i = 0; i < n - 1; i++){
            nearest[i] = 1;
            distance[i] = getLength(0, i + 1, adjacency_list);
        }

        while(t < n - 1){
            min = 9999999; // As infinity in this quiz.
            for(i = 0; i < n - 1; i++){
                if(distance[i] >= 0 && distance[i] < min){
                    min = distance[i];
                    vnear = i;
                }
            }
            //  Add the edge to F.
            F[Fi][0] = nearest[vnear] - 1;
            F[Fi][1] = vnear + 1;
            distance[vnear] = -1;
            Fi++;

            for(i = 0; i < n-1; i++){
                if(getLength(i + 1, vnear + 1, adjacency_list) < distance[i]){
                    distance[i] = getLength(i + 1, vnear + 1, adjacency_list);
                    nearest[i] = vnear + 2;
                }
            }
            t++;
        }
        return F;
    }

    public static void main(String[] args) {
        Quiz7 x= new Quiz7();
        int numOfNodes = 102;
        int[][] F;
        int totalDistance = 0;
        int currerntLength;

        // Read the file to get the adjacency matrix.
        // I use absolute path here, so please change it when you run this code.
        link_list[] adjacency_list = x.readLinkList("A:/Algorithm_Design/Quiz/Quiz7/Quiz6_Input_File.csv", numOfNodes);

        // Prim algorithm, save the result (Set of edges) to int[][] F.
        // The first element in each row in F (F[n][0]) is one end of an edge, the second element (F[n][1]) is the
        // other end of the edge.
        F = x.prim(numOfNodes, adjacency_list);

        // Print the result and the total distance.
        System.out.println("Prim with link list for adjacency:");
        System.out.println("Node:       Node:       Distance:");
        for(int i = 0; i < numOfNodes - 1; i++) {
            // These if else seems a little complex because I want to print the result in a clear format,
            // so I have to print some extra space depends on the number of digits.
            if(F[i][0] < 10)
                System.out.print(F[i][0] + "           ");
            else if(F[i][0] > 99)
                System.out.print(F[i][0] + "         ");
            else
                System.out.print(F[i][0] + "          ");
            if(F[i][1] < 10)
                System.out.print(F[i][1]+"           ");
            else if(F[i][1] > 99 )
                System.out.print(F[i][1]+"         ");
            else
                System.out.print(F[i][1]+"          ");
            currerntLength = x.getLength(F[i][0], F[i][1], adjacency_list);
            System.out.print(currerntLength);
            totalDistance += currerntLength;
            System.out.println();
        }
        System.out.println("TotalDistance is: " + totalDistance);
    }
}

