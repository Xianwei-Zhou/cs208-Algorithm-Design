import java.util.Arrays;
import java.util.Scanner;

public class Sorting_array {
    static long count;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = in.nextInt();
        }
        count = 0;
        mergeSort(array);
        System.out.println(count);
    }

    private static int[] mergeSort(int[] array) {
        if (array.length < 2) return array;
        int p = array.length / 2;
        int[] b = Arrays.copyOfRange(array, 0, p);
        int[] c = Arrays.copyOfRange(array, p, array.length);
        return merge(mergeSort(b), mergeSort(c));
    }

    private static int[] merge(int[] b, int[] c) {
        int i = 0, j = 0;
        int length = b.length + c.length;
        int[] a = new int[length];
        for (int k = 0; k < length; k++) {
            if (j < c.length && (i >= b.length || b[i] > c[j])) {
                if (i < b.length)
                    count += b.length - i;
                a[k] = c[j];
                j += 1;
            } else {
                a[k] = b[i];
                i += 1;
            }
        }
        return a;
    }
}
