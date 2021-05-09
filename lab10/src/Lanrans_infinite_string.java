import java.util.Scanner;

// f(2^n-1+k) =k+f(2^n-k) (k!=0) and f(2^n-1)=2^(n-1)

public class Lanrans_infinite_string {
    static class Tuple {
        final int n;
        final long k;

        Tuple(int n, long k) {
            this.n = n;
            this.k = k;
        }
    }

    static long[] pows;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        pows = new long[63];
        pows[0] = 1;
        for (int i = 1; i < pows.length; i++) {
            pows[i] = 2 * pows[i - 1];
        }
        while (n-- > 0) {
            long i = in.nextLong();
            long j = in.nextLong();
            System.out.println(fun(j) - fun(i - 1));
        }
    }

    // val=2^n-1+k
    static Tuple findNK(long val) {
        if (val == 0) return null;
        int n;
        return new Tuple(n = (int) (Math.log(val + 1) / Math.log(2)), val + 1 - pows[n]);
    }

    static long fun(long val) {
        Tuple tuple = findNK(val);
        if (tuple == null) return 0;
        if (tuple.k == 0) {
            return pows[tuple.n - 1];
        } else
            return tuple.k + fun(pows[tuple.n] - tuple.k);
    }
}
