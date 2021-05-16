import java.util.Scanner;

public class Lanrans_another_infinite_string {
    static class TupleNK {
        final int n;
        final long k;

        public TupleNK(int n, long k) {
            this.n = n;
            this.k = k;
        }
    }

    static class Tuple {
        final long L;
        final long R;
        final long N;

        public Tuple(long l, long r, long n) {
            L = l;
            R = r;
            N = n;
        }
    }

    static long[] pows = new long[63];

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        pow();
        int T = in.nextInt();
        while (T-- > 0) {
            Tuple ans = fun(in.nextLong());
            System.out.println(ans.L + " " + ans.R + " " + ans.N);
        }
    }

    // val=2^n+k
    static TupleNK findNK(long val) {
        if (val == 0) return null;
        int n;
        return new TupleNK(n = (int) (Math.log(val) / Math.log(2)), val - pows[n]);
    }

    static Tuple fun(long val) {
        TupleNK tupleNK = findNK(val);
        if (tupleNK == null) return new Tuple(0, 0, 0);
        if (tupleNK.k == 0) {
            long tmp = pows[tupleNK.n] / 3;
            switch (tupleNK.n % 6) {
                case 1:
                    return new Tuple(1 + tmp, 1 + tmp, tmp);
                case 2:
                    return new Tuple(tmp, tmp + 1, tmp);
                case 3:
                    return new Tuple(tmp, tmp + 1, tmp + 1);
                case 4:
                    return new Tuple(tmp, tmp, tmp + 1);
                case 5:
                    return new Tuple(tmp + 1, tmp, tmp + 1);
                case 0:
                    return new Tuple(tmp + 1, tmp, tmp);
                default:
                    return new Tuple(0, 0, 0);
            }
        } else {
            Tuple tuple = fun(pows[tupleNK.n]);
            Tuple nTuple = fun(tupleNK.k);
            return new Tuple(tuple.L + nTuple.N, tuple.R + nTuple.L, tuple.N + nTuple.R);
        }
    }

    static void pow() {
        pows[0] = 1;
        for (int i = 1; i < pows.length; i++) {
            pows[i] = 2 * pows[i - 1];
        }
    }
}
