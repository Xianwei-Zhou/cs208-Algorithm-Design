import java.io.*;
import java.util.*;

//Union-find and Kruskal algorithm
public class Counting_on_a_tree {

    static class Edge {
        final int u;
        final int v;
        final int w;

        public Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

    static int[] fa;
    static long[] rank;
    static long[] ans;

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int m = in.nextInt();
        Edge[] edges = new Edge[n - 1];
        int maxW = 0;
        for (int i = 0; i < n - 1; i++) {
            edges[i] = new Edge(in.nextInt(), in.nextInt(), in.nextInt());
            maxW = Math.max(maxW, edges[i].w);
        }
        Arrays.sort(edges, Comparator.comparingInt(o -> o.w));
        fa = new int[n + 1];
        rank = new long[n + 1];
        ans = new long[maxW + 1];
        for (int i = 1; i <= n; i++) {
            fa[i] = i;
            rank[i] = 1;
        }
        int j = 1;
        for (Edge edge : edges) {
            while (j < edge.w) {
                ans[++j] = ans[j - 1];
            }
            ans[j] += rank[find(edge.u)] * rank[find(edge.v)];
            union(edge.u, edge.v);
        }

        while (m-- > 0) {
            out.print(ans[Math.min(maxW, in.nextInt())] + " ");
        }

        out.close();
    }

    static int find(int x) {
        if (x != fa[x])
            fa[x] = find(fa[x]);
        return fa[x];
    }

    static void union(int u, int v) {
        int fu = find(u);
        int fv = find(v);
        if (fu == fv) return;
        if (rank[fu] > rank[fv]) {
            fa[fv] = fu;
            rank[fu] += rank[fv];
        } else {
            fa[fu] = fv;
            rank[fv] += rank[fu];
        }
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