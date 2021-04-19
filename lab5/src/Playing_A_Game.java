import java.io.*;
import java.util.*;

public class Playing_A_Game {

    static class Node {
        int index;
        long val;
        ArrayList<Node> children;
        //a for total investment of this node
        long a;

        public Node(int index, long val) {
            this.index = index;
            this.val = val;
            children = new ArrayList<>();
            this.a = this.val;
        }
    }

    static Node[] nodes;
    static long[][] weight;

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i, in.nextLong());
        }
        weight = new long[n][n];
        for (int i = 0; i < n - 1; i++) {
            int first = in.nextInt() - 1;
            int second = in.nextInt() - 1;
            long w = in.nextLong();
            weight[first][second] = w;
            weight[second][first] = w;
            nodes[first].children.add(nodes[second]);
            nodes[second].children.add(nodes[first]);
        }
        dfs(nodes[0], null);
        findAns(nodes[0], null);
        out.println(-ans);
        out.close();
    }

    //to get a for every node
    static void dfs(Node cur, Node parent) {
        for (Node child : cur.children) {
            if (child != parent) {
                dfs(child, cur);
                cur.a += child.a - weight[cur.index][child.index] << 1;
            }
        }
    }

    static long HP = 0;
    static long ans = 0;

    //to simulate the optimal procedure of this game (brute force)
    static void findAns(Node cur, Node parent) {
        HP += cur.val;
        if (cur.children.size() != 1) {
            cur.children.sort(new Comparator<Node>() {
                @Override
                public int compare(Node o1, Node o2) {
                    long a1 = o1.a - weight[cur.index][o1.index] << 1;
                    long a2 = o2.a - weight[cur.index][o2.index] << 1;
                    if (a1 >= 0 && a2 >= 0) {
                        if (weight[cur.index][o1.index] < weight[cur.index][o2.index]) return -1;
                        else return 1;
                    } else if (a1 >= 0) return -1;
                    else if (a2 >= 0) return 1;
                    else {
                        if (o2.a > o1.a) return 1;
                        else return -1;
                    }
                }
            });
            for (Node child : cur.children) {
                if (child != parent) {
                    HP -= weight[cur.index][child.index];
                    ans = Math.min(ans, HP);
                    findAns(child, cur);
                    HP -= weight[cur.index][child.index];
                    ans = Math.min(ans, HP);
                }
            }
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