import java.io.*;
import java.util.*;


public class Lanrans_mahjong_survey {
    static class Lanran {
        int u;
        int v;
        int vCnt;

        public Lanran(int u, int v, int vCnt) {
            this.u = u;
            this.v = v;
            this.vCnt = vCnt;
        }
    }

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        Lanran[] lanrans = new Lanran[n];
        for (int i = 0; i < n; i++) {
            lanrans[i] = new Lanran(in.nextInt(), in.nextInt(), i);
        }
        Arrays.sort(lanrans, (o1, o2) -> {
            if (o1.u == o2.u) return o1.v - o2.v;
            return o1.u - o2.u;
        });
        //the problem equals to finding how many j > i && u[j]<u[i] for every element of array u
        //or how many right numbers are smaller than the current, or to find every inversion pairs of array u
        int[] u=new int[n];
        for (int i = 0; i < n; i++) {
            u[i]=lanrans[n-1-i].vCnt;
        }
        //System.out.println(Arrays.toString(u));
        cnt=new int[n];
        mergeSort(u);
        int[] ans=new int[n];
        //System.out.println(Arrays.toString(cnt));
        for (int i = 0; i < n; i++) {
            ans[cnt[i]]++;
        }
        for (int i = 0; i < n; i++) {
            out.println(ans[i]);
        }
        out.close();
    }
    static int[] cnt;
    private static int[] mergeSort(int[] array) {
        if (array.length < 2) return array;
        int p = array.length / 2;
        int[] b = Arrays.copyOfRange(array, 0, p);
        int[] c = Arrays.copyOfRange(array, p, array.length);
        return merge(mergeSort(b), mergeSort(c));
    }

    private static int[] merge(int[] b, int[] c) {
        int tmp=0;
        int i = 0, j = 0;
        int length = b.length + c.length;
        int[] a = new int[length];
        for (int k = 0; k < length; k++) {
            if (j < c.length && (i >= b.length || b[i] > c[j])) {
                a[k] = c[j];
                tmp++;
                j += 1;
            } else {
                cnt[b[i]] += tmp;
                a[k] = b[i];
                i += 1;
            }
        }
        return a;
    }


    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

    }

}
