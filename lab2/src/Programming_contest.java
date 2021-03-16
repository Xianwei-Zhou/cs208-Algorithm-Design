import java.io.*;
import java.util.*;

public class Programming_contest {

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int T = in.nextInt();
        for (int t = 0; t < T; t++) {
            int n = in.nextInt();

            //remove duplicate and sort (reverse).
            List<Integer> difficulties0 = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                difficulties0.add(in.nextInt());
            }
            List<Integer> difficulties = new ArrayList<>(new HashSet<>(difficulties0));
            Collections.sort(difficulties);
            Collections.reverse(difficulties);

            int first = difficulties.get(0);
            int cnt = 0;
            if (first % 30 == 0) {
                for (Integer difficulty : difficulties) {
                    if (difficulty * 2 == first || difficulty * 3 == first || difficulty * 5 == first) {
                        cnt++;
                    }
                    if (difficulty * 5 < first) break;
                }
            }
            int sum1 = 0;
            int sum2 = first;
            if (cnt == 3) sum1 = first / 30 * 31;
            cnt = 0;
            int tmp = 0;
            for (Integer difficulty : difficulties) {
                if (cnt == 2) break;
                if (first % difficulty != 0 && (tmp == 0 || tmp % difficulty != 0)) {
                    tmp = difficulty;
                    sum2 += difficulty;
                    cnt += 1;
                }
            }
            out.println(Math.max(sum1, sum2));
        }


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

        public Reader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
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

        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

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

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }

}