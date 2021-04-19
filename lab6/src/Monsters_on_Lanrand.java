import java.util.Arrays;
import java.io.*;

public class Monsters_on_Lanrand {

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        if (n == 1) {
            out.println(in.nextInt());
            out.close();
            return;
        }
        int[] input = new int[n];
        long sum = 0;
        for (int i = 0; i < n; i++) {
            input[i] = in.nextInt();
            sum += Math.abs(input[i]);
        }
        Arrays.sort(input);
        if (input[0] >= 0) {
            out.println(sum - 2L * input[0]);
            out.close();
            return;
        }
        if (input[n - 1] <= 0) {
            out.println(sum + 2L * input[n - 1]);
            out.close();
            return;
        }
        out.println(sum);
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
