package Algorithm_Design_Quiz12;

public class Quiz12 {
    public static void BubbleSort(int[] S) {
        int length = S.length;
        int temp;
        boolean signal = true;

        for(int t1 = 0; signal && t1 < length; t1++) {
            signal = false;
            for(int t2 = 1; t2 < length; t2++) {
                if(S[t2 - 1] < S[t2]) {
                    temp = S[t2 - 1];
                    S[t2 - 1] = S[t2];
                    S[t2] = temp;
                    signal = true;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] a = new int[5];
        a[0] = 9;
        a[1] = 15;
        a[2] = 5;
        a[3] = 0;
        a[4] = 29;


        System.out.println("The unsorted array:");
        for(int t = 0; t < a.length; t++){
            System.out.print(a[t] +" ");
        }
        System.out.println();

        BubbleSort(a);

        System.out.println("The sorted  array:");
        for(int t = 0; t < a.length; t++){
            System.out.print(a[t] +" ");
        }
        System.out.println();
    }
}
