
import java.io.*;

public class Slime_on_Lanrand {

    static final int INF=0x7FFFFFFF;
    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int[] weights = new int[n];
        for (int i = 0; i < n; i++) {
            weights[i] = in.nextInt();
        }
        int[] sum = new int[n+1];
        sum[1] = weights[0];//padding zero
        for (int i = 2; i <= n; i++) {
            sum[i] = sum[i - 1] + weights[i-1];
        }
        int[][] minTime = new int[n + 1][n + 1];//minTime[i][j] means from i to j, the least time to cost
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n ; j++) {
                if (i==j)minTime[i][j]=0;
                else minTime[i][j]=INF;
            }
        }
        for (int step = 1; step < n; step++) {
            for (int i = 1; i <= n-step; i++) {//start
                int k=step+i;//end
                int cost=sum[k]-sum[i-1];
                for (int j = i; j < k; j++) {//middle
                    minTime[i][k]=Math.min(minTime[i][k],minTime[i][j]+minTime[j+1][k]+cost);
                }
            }
        }
        out.println(minTime[1][n]);
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