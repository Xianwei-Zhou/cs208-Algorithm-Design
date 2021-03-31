import java.io.*;
import java.util.*;


public class Lanrans_tree {
    static class Node {
        int index;
        int weight;
        ArrayList<Node> children;

        public Node(int index, int weight) {
            this.index = index;
            this.weight = weight;
            children = new ArrayList<>();
        }
    }

    static int n;
    static long[] sum;
    static long[] tot;
    static long result;

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        n = in.nextInt();
        sum = new long[n];
        tot = new long[n];
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i, in.nextInt());
        }
        for (int i = 0; i < n - 1; i++) {
            int a = in.nextInt() - 1;
            int b = in.nextInt() - 1;
            nodes[a].children.add(nodes[b]);
            nodes[b].children.add(nodes[a]);
        }

        calSum0(nodes[0], null, 0);
        result = sum[0];
        dfs(nodes[0], null);
        out.println(result);
        out.close();

    }

    static void calSum0(Node node, Node parent, long deep) {
        tot[node.index] = node.weight;
        for (Node child : node.children) {
            if (child != parent) {
                calSum0(child, node, deep + 1);
                tot[node.index] += tot[child.index];
            }
        }
        sum[0] += node.weight * deep;
    }

    static void dfs(Node node, Node parent) {
        if (node.index != 0) {
            sum[node.index] = sum[parent.index] + tot[0] - tot[node.index] * 2;
            result = Math.max(sum[node.index], result);
//            System.out.println(sum[node.index]+"\t");
        }
        for (Node child : node.children) {
            if (child != parent)
                dfs(child, node);
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