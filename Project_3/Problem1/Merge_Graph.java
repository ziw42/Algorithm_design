package Project3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Merge_Graph {


    public static double[][] getCoordinates(String path1, String path2){
        int num1 = getNumOfNodes(path1);
        int num2 = getNumOfNodes(path2);
        double[][] coordinates = new double[num1 + num2][3];
        int currentNode = -1;

        try{
            FileReader fr = new FileReader(path1);
            BufferedReader br = new BufferedReader(fr);
            String s = null;
            s = br.readLine();  // The first row is attribute name, it is not int.
            while ((s = br.readLine()) != null) {
                if(Integer.valueOf(s.split(",")[0]) != currentNode){
                    currentNode = Integer.valueOf(s.split(",")[0]);
                    coordinates[currentNode][0] = currentNode;
                    coordinates[currentNode][1] = Double.valueOf(s.split(",")[3].replace("\"(",""));
                    coordinates[currentNode][2] = Double.valueOf(s.split(",")[4].replace(")\"","").replace(" ",""));
                }
            }
            fr = new FileReader(path2);
            br = new BufferedReader(fr);
            s = br.readLine();
            while ((s = br.readLine()) != null) {
                if(Integer.valueOf(s.split(",")[0]) + num1 != currentNode){
                    currentNode = Integer.valueOf(s.split(",")[0]) + num1;
                    coordinates[currentNode][0] = currentNode;
                    coordinates[currentNode][1] = Double.valueOf(s.split(",")[3].replace("\"(",""));
                    coordinates[currentNode][2] = Double.valueOf(s.split(",")[4].replace(")\"","").replace(" ",""));
                }
            }
        }
        catch(Exception e){
            e.getMessage();
        }
        return coordinates;
    }

    public static void replace(int[] node1, int[] node2, int n1, int n2, int row){
        for(int t = 0; t < row; t++){
            if(node1[t] == n2){
                node1[t] = n1;
            }
            if(node2[t] == n2){
                node2[t] = n1;
            }
        }
    }

    public static int normalize(int[] node1, int[] node2, int row){
        int t = 1;
        int t2;
        int currentIndex;
        int numOfRepetition = 0;
        int result;

        while(t < row){
            if(node1[t] < node1[t - 1]){
                numOfRepetition++;
                currentIndex = node1[t - 1];
                for(t2 = 0; t2 < row; t2++){
                    if(node1[t2] > currentIndex){
                        node1[t2]--;
                    }
                    if(node2[t2] > currentIndex){
                        node2[t2]--;
                    }
                }
            }
            t++;
        }
        result = numOfRepetition;

        return result;
    }

    public static Map merge(String path1, String path2){
        int num1 = getNumOfNodes(path1);
        int num2 = getNumOfNodes(path2);
        int row1 = getNumOfRows(path1);
        int row2 = getNumOfRows(path2);
        int[] node1 = new int[row1 + row2];
        int[] node2 = new int[row1 + row2];
        int[] distance = new int[row1 + row2];
        double[][] coordinates = getCoordinates(path1, path2);
        int t = 0;
        int numOfRepetition;
        int numOfRepeRow;
        double x;
        double y;
        Map result = new HashMap();

        try {
            FileReader fr = new FileReader(path1);
            BufferedReader br = new BufferedReader(fr);
            String s = null;
            s = br.readLine();  // The first row is attribute name, it is not int.
            while ((s = br.readLine()) != null) {
                node1[t] = Integer.valueOf(s.split(",")[0]);
                node2[t] = Integer.valueOf(s.split(",")[1]);
                distance[t] = Integer.valueOf(s.split(",")[2]);
                t++;
            }
            fr = new FileReader(path2);
            br = new BufferedReader(fr);
            s = br.readLine();
            while ((s = br.readLine()) != null) {
                node1[t] = Integer.valueOf(s.split(",")[0]) + num1;
                node2[t] = Integer.valueOf(s.split(",")[1]) + num1;
                distance[t] = Integer.valueOf(s.split(",")[2]);
                t++;
            }
        }
        catch(Exception e){
            e.getMessage();
        }

        for(int t2 = 0; t2 < num1; t2++){
            x = coordinates[t2][1];
            y = coordinates[t2][2];
            for(int t3 = num1; t3 < num1 + num2; t3++){
                if(x == coordinates[t3][1] && y == coordinates[t3][2]){
                    replace(node1, node2, (int)coordinates[t2][0], (int)coordinates[t3][0], row1 + row2);
                }
            }
        }
        numOfRepetition = normalize(node1, node2, row1 + row2);

        result.put("node1", node1);
        result.put("node2", node2);
        result.put("distance", distance);
        result.put("num1", num1);
        result.put("num2", num2);
        result.put("row1", row1);
        result.put("row2", row2);
        result.put("numOfRepetition", numOfRepetition);

        return result;
    }

    public static int getNumOfRows(String path){
        int num = 0;
        File f = new File(path);
        try {
            if (f.exists()) {
                if (f.isFile()) {
                    FileReader fr = new FileReader(path);
                    BufferedReader br = new BufferedReader(fr);
                    String s = null;
                    s = br.readLine();  // The first row is attribute name, it is not int.
                    while ((s = br.readLine()) != null) {
                        num++;
                    }
                }
            }
        }
        catch(Exception e){
            e.getMessage();
        }
        return num;
    }

    public static int getNumOfNodes(String path){
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


    public static int[][] readArray(Map nodes) {
        int num1 = (int)nodes.get("num1");
        int num2 = (int)nodes.get("num2");
        int row1 = (int)nodes.get("row1");
        int row2 = (int)nodes.get("row2");
        int numOfRepetition = (int)nodes.get("numOfRepetition");
        int[] node1 = (int[])nodes.get("node1");
        int[] node2 = (int[])nodes.get("node2");
        int[] distance = (int[])nodes.get("distance");
        int[][] adjacency = new int[num1 + num2 -numOfRepetition][num1 + num2 - numOfRepetition];
        for(int t1 = 0; t1 < num1 + num2 - numOfRepetition; t1++){
            for(int t2 = 0; t2 < num1 + num2 -numOfRepetition; t2++){
                adjacency[t1][t2] = 999999999;
            }
        }
        for(int t = 0; t < row1 + row2; t++){
            adjacency[node1[t]][node2[t]] = distance[t];
        }
        return adjacency;
    }

    public static link_list[] readLinkList(Map nodes){
        int num1 = (int)nodes.get("num1");
        int num2 = (int)nodes.get("num2");
        int row1 = (int)nodes.get("row1");
        int row2 = (int)nodes.get("row2");
        int numOfRepetition = (int)nodes.get("numOfRepetition");
        int[] node1 = (int[])nodes.get("node1");
        int[] node2 = (int[])nodes.get("node2");
        int[] distance = (int[])nodes.get("distance");
        link_list vertex = new link_list(-1);
        link_list[] adjacency = new link_list[num1 + num2 - numOfRepetition];
        for(int t = 0; t < row1 + row2; t++){
            if(adjacency[node1[t]] == null){
                adjacency[node1[t]] = new link_list(node1[t]);
                adjacency[node1[t]].weight = 999999999;
                adjacency[node1[t]].setNext(node2[t], distance[t]);
            }
            else{
                if(t > 1 && node1[t] < node1[t - 1]){
                    if(node2[t] > num1){
                        adjacency[node1[t]].setNext(node2[t], distance[t]);
                    }
                }
                else{
                    adjacency[node1[t]].setNext(node2[t], distance[t]);
                }
            }
        }

        return adjacency;
    }

    public static void main(String args[]){
        Map a = Merge_Graph.merge("C:/Users/王梓安/Downloads/Project3_G1_Data.csv", "C:/Users/王梓安/Downloads/Project3_G2_Data.csv");
        link_list[] a1 = Merge_Graph.readLinkList(a);
        /*int[][] adjacency = Merge_Graph.readArray(a);
        for(int t1 = 0; t1 < 1993; t1++){
            for(int t2 = 0; t2 < 1993; t2++){
                System.out.print(adjacency[t1][t2]+" ");
            }
            System.out.println();
        }*/
        /*Map a = Merge_Graph.merge("C:/Users/王梓安/Downloads/Project3_G1_Data.csv", "C:/Users/王梓安/Downloads/Project3_G2_Data.csv");
        int[] node1 = (int[])a.get("node1");
        int[] node2 = (int[])a.get("node2");
        for(int t = 0; t < 6076; t++){
            System.out.println(node1[t] + " " + node2[t]);
        }*/
        /*double[][] a = Merge_Graph.getCoordinates("C:/Users/王梓安/Downloads/Project3_G1_Data.csv", "C:/Users/王梓安/Downloads/Project3_G2_Data.csv");
        for(int t = 0; t < 2002; t++){
            System.out.println(a[t][0] + " " + a[t][1] + "," + a[t][2]);
        }*/
        //a.readLinkList("C:/Users/王梓安/Downloads/Project3_G1_Data.csv", "C:/Users/王梓安/Downloads/Project3_G2_Data.csv", 534, 1468);
    }
}
