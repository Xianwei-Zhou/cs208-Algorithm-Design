import java.io.*;
import java.util.*;

//greedy algorithm. To make all house to hold the most number of people, or for every house, we want it holding as large amount as possible.
public class Criminals {

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        int t = in.nextInt();
        int[] people = new int[n];
        int[] house = new int[m];
        int[] cnt = new int[m];
        for (int i = 0; i < n; i++) {
            people[i] = in.nextInt();
        }
        for (int i = 0; i < m; i++) {
            house[i] = in.nextInt();
        }
        Arrays.sort(people);
        Arrays.sort(house);
        int ans = 0;
        int j = 0;//point of people
        for (int i = 0; i < m; i++) {
            //to find the nearest person and when house full, out
            while (j < n && house[i] >= people[j] - t && cnt[i] < k) {
                if (house[i] <= people[j] + t) {
                    ans++;
                    cnt[i]++;
                }
                j++;
            }
        }
        out.println(ans);
        out.close();
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
