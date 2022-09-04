package Project4;
// Program for 2214 INFSCI 2591 Algorithm Design, Project4 Problem2
// Author: Zian Wang

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Problem2_Branch_and_Bound_1D {

    // Only use one-dimensional array, there is no two-dimensional array created.
    public static int[] read1DArray(String path, int num){

        // Only store the lower triangle.
        int length = (num * num - num) / 2;
        // Only one one-dimensional array is created.
        int[] adjacency = new int[length];

        for(int t = 0; t < length; t++) {
            adjacency[t] = 999999;
        }

        try {
            int i = 0;
            int j = 0;
            int index;

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
                        if(i > j) {
                            index = i * (i - 1) / 2 + j;
                            adjacency[index] = Integer.valueOf(s.split(",")[2]);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return adjacency;
    }

    public static int getLength(int i, int j, int[] adjacency) {
        if(i == j)
            return 999999;
        else {
            if(i > j) {
                return adjacency[i * (i - 1) / 2 + j];
            }
            else {
                return adjacency[j * (j - 1) / 2 + i];
            }
        }

    }

    public static boolean inPath(int[] path, int x) {
        int length = path.length;
        for(int t = 0; t < length; t++) {
            if(path[t] == x) {
                return true;
            }
        }
        return false;
    }

    public static double bound(Node node, int[] adjacency, int n) {
        int result = 0;
        int min;
        int[] path = node.path;
        int pathLength = path.length;
        for(int t = 1; t < pathLength; t++) {
            result += getLength(path[t - 1], path[t], adjacency);
        }

        for(int t = 0; t < n; t++) {
            min = 999999999;
            if(!inPath(path, t) || t == path[pathLength - 1]) {
                if(t == path[pathLength - 1]) {
                    for (int t1 = 1; t1 < n; t1++) {
                        if (!inPath(path, t1)) {
                            if (getLength(t, t1, adjacency) < min) {
                                min = getLength(t, t1, adjacency);
                            }
                        }
                    }
                }
                else {
                    for (int t1 = 0; t1 < n; t1++) {
                        if ((!inPath(path, t1) || t1 == 0) && t1 != t) {
                            if (getLength(t, t1, adjacency) < min) {
                                min = getLength(t, t1, adjacency);
                            }
                        }
                    }
                }
                result += min;
            }
        }

        return result;
    }

    public static void finalize(Node node) {
        int pathLength = node.path.length;
        int[] tempPath = new int[pathLength + 2];
        int[] exist = new int[pathLength + 1];
        int t;
        for(t = 0; t < pathLength + 1; t++) {
            exist[t] = 0;
        }
        for(t = 0; t < pathLength; t++) {
            tempPath[t] = node.path[t];
            exist[node.path[t]] = 1;
        }
        for(t = 0; t < pathLength + 1; t++) {
            if(exist[t] == 0) {
                tempPath[pathLength] = t;
                break;
            }
        }
        tempPath[pathLength + 1] = 0;
        node.path = tempPath;
    }

    public static int length(Node node, int[] adjacency) {
        int result = 0;
        int[] path = node.path;
        for(int t = 0; t < path.length - 1; t++) {
            result += getLength(path[t], path[t + 1], adjacency);
        }
        return result;
    }

    public static Map TSP (int[] adjacency, int n) {
        Priority_queue PQ = new Priority_queue();
        int[] optTour = null;
        int[] path;
        Node u;
        Node v = new Node();
        int pathLength;
        int[] tempPath;
        int t;
        v.level = 0;
        path = new int[1];
        path[0] = 0;
        v.path = path;
        v.bound = bound(v, adjacency, n);
        int minLength = 999999999;
        PQ.insert(v);
        while(!PQ.empty()) {
            v = PQ.remove();
            if(v.bound < minLength) {
                for(int i = 1; i < n ; i++) {
                    u = new Node();
                    u.level = v.level + 1;
                    if(!inPath(v.path, i)) {
                        pathLength = v.path.length;
                        tempPath = new int[pathLength + 1];
                        for(t = 0; t < pathLength; t++) {
                            tempPath[t] = v.path[t];
                        }
                        tempPath[t] = i;
                        u.path = tempPath;
                        if(u.level == n - 2) {
                            finalize(u);
                            int currentLength = length(u, adjacency);
                            if(currentLength < minLength) {
                                minLength = currentLength;
                                optTour = u.path;
                            }
                        }
                        else {
                            u.bound = bound(u, adjacency, n);
                            if(u.bound < minLength) {
                                PQ.insert(u);
                            }
                        }
                    }
                }
            }
        }
        Map map = new HashMap();
        map.put("optTour", optTour);
        map.put("minLength", minLength);

        return map;
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

    public static void main(String[] args) {
        System.out.println("Branch-and-bound algorithm uses one-dimensional array");
        String path = "A:/Algorithm_Design/Project4/Project 4_Problem 2_InputData.csv";
        int numOfNodes = getNumOfNodes(path);
        int[] adjacency = read1DArray(path, numOfNodes);

        Map result = TSP(adjacency, numOfNodes);
        int[] TSPpath = (int[])result.get("optTour");
        int minLength = (int)result.get("minLength");

        System.out.println("This is the optimal path:");
        int t;
        for(t = 0; t < TSPpath.length - 1; t++) {
            System.out.print(TSPpath[t] + " -> ");
        }
        System.out.println(TSPpath[t]);
        System.out.println();
        System.out.println("The minimum length is: " + minLength);
    }
}
