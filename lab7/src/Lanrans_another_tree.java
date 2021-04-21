import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

//MST
public class Lanrans_another_tree {

    static class Edge {
        int to;
        int cost;

        public Edge(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        //to get Fibonacci numbers (only 26 numbers are smaller than 10 000)
        int[] FNumbers = new int[27];
        FNumbers[0] = 0;
        FNumbers[1] = 1;
        for (int i = 2; i < 27; i++) {
            FNumbers[i] = FNumbers[i - 2] + FNumbers[i - 1];
        }
        int T = in.nextInt();
        while (T-- > 0) {
            int n = in.nextInt();
            int m = in.nextInt();

            ArrayList<Edge>[] edges = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                edges[i] = new ArrayList<>();
            }
            for (int i = 0; i < m; i++) {
                int u = in.nextInt() - 1;
                int v = in.nextInt() - 1;
                int w = in.nextInt();
                edges[u].add(new Edge(v, w));
                edges[v].add(new Edge(u, w));

            }
            PriorityQueue<Edge> minQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
            for (int i = 0; i < edges[0].size(); i++) {
                minQueue.offer(edges[0].get(i));
            }

            PriorityQueue<Edge> maxQueue = new PriorityQueue<>((o1, o2) -> o2.cost - o1.cost);
            for (int i = 0; i < edges[0].size(); i++) {
                maxQueue.offer(edges[0].get(i));
            }

            //Prim Algorithm
            boolean[] isVisited = new boolean[n];
            isVisited[0]=true;
            int min = PrimAlgorithm(n, edges, isVisited, minQueue);

            isVisited = new boolean[n];
            isVisited[0]=true;
            int max = PrimAlgorithm(n, edges, isVisited, maxQueue);

            if (min==-1) out.println("No");
            else {
                if (FNumbers[binarySearch(FNumbers, min, 0, 26)] <= max) out.println("Yes");
                else out.println("No");
            }
        }
        out.close();
    }

    private static int PrimAlgorithm(int n, ArrayList<Edge>[] edges, boolean[] isVisited, PriorityQueue<Edge> queue) {
        int ans = 0;
        int cnt = 0;
        while (!queue.isEmpty() && cnt != n - 1) {
            Edge edge = queue.poll();
            if (isVisited[edge.to]) continue;
            ans += edge.cost;
            cnt += 1;
            isVisited[edge.to] = true;
            for (int i = 0; i < edges[edge.to].size(); i++) {
                Edge e = edges[edge.to].get(i);
                if (!isVisited[e.to]) queue.offer(e);
            }
        }
        if (cnt!=n-1)ans=-1;
        return ans;
    }

    //to find the first number greater or equal than the given value
    static int binarySearch(int[] numbers, int val, int l, int r) {
        int m;
        int ans = -1;
        while (l <= r) {
            m = l + (r - l) / 2;
            if (numbers[m] > val) {
                ans = m;
                r = m - 1;
            } else if (numbers[m] == val) {
                return m;
            } else
                l = m + 1;
        }
        return ans;

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