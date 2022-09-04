package Algorithm_Design_Quiz1;

// Program for 2214 INFSCI 2591 Algorithm Design, Quiz1
// Author: Zian Wang
public class Quiz1 {
    public void subset(int[] set) {
        // Make sure the set contains enough elements.
        if(set.length < 3) {
            System.out.println("Error. The set must have 3 or more elements!");
            return;
        }
        // Now the core part: the part which makes and prints all subsets.
        // Let's say the target subset is {a,b,c} to distinguish between the original set and the sub set.
        // First, we assume the 1st and 2nd elements of the set are in subset and make them to "a" and "b".
        //   Then we make subsets by assign the value of "c" over all the rest of the values of the original set.
        //   This makes all subsets that contain the 1st and the 2nd element of the original set.
        // Second, we change the "b" value to the value of the 3rd element of the original set, and then again, assign the value
        //   of "c" over all the rest of the values of the original set.
        //   When the value of "b" takes all of rest values of the original set, we can change the "a" value to the 2rd value of
        //   the original set. And when the "a" value takes all the rest values of the original set. All the subset of 3 elements
        //   is made.
        // One thing that we should be aware of is that: the index of "a" in the original set is always smaller than that of "b".
        //   Also, the index of "b" in the original set is always smaller than that of "c".
        //   This is OK because we need combinations, not permutations.
        //   It is so hard to explain an algorithm in English! Pseudocode is truly much better!!!
        for(int x = 0; x < set.length - 2; x++){
            for(int y = x + 1; y < set.length - 1; y++){
                for(int z = y + 1; z < set.length; z++){
                    System.out.println(set[x] + " " + set[y] + " " + set[z]);
                    System.out.println();  // Make the output more clear.
                }
            }
        }
    }

    public static void main(String[] args) {
        // Set all the testsets.
        int[] testset1 = new int[4];
        int[] testset2 = new int[2];
        int[] testset3 = new int[8];
        for(int t = 0; t < 4; t++) {
            testset1[t] = t + 1;
        }
        testset2[0] = 7;
        testset2[1] = 3;
        testset3[0] = 4;
        testset3[1] = 1;
        testset3[2] = 7;
        testset3[3] = 4;
        testset3[4] = 3;
        testset3[5] = 9;
        testset3[6] = 1;
        testset3[7] = 5;
        //Begin test.
        Quiz1 test = new Quiz1();
        test.subset(testset1);
        System.out.println("--------------------------------------------------");
        test.subset(testset2);
        System.out.println("--------------------------------------------------");
        test.subset(testset3);
    }
}
