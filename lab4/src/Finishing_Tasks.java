import java.io.*;
import java.util.*;

//通过入度表进行拓扑排序，每次找到入度为0的入队（都在队列里的元素顺序任意），队列非空时将队首元素出队并且其子节点入度均-1，然后将入度为0的子节点入队
public class Finishing_Tasks {
    static class Node {
        int val;
        ArrayList<Node> children;

        public Node(int val) {
            this.val = val;
            children = new ArrayList<>();
        }
    }

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();

        Node[] nodes = new Node[n];
        int[] degrees = new int[n];

        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i + 1);
        }
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            nodes[a - 1].children.add(nodes[b - 1]);
            degrees[b - 1]++;
        }
        PriorityQueue<Node> heap = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.val - o2.val;
            }
        });
        for (int i = 0; i < n; i++) {
            if (degrees[i] == 0)
                heap.add(nodes[i]);
        }
        int[] sortedNumbers = new int[n];
        int j = 0;
        int cnt=0;
        while (!heap.isEmpty()) {
            Node node = heap.poll();
            cnt++;
            sortedNumbers[j++] = node.val;
            for (int i = 0; i < node.children.size(); i++) {
                if (--degrees[node.children.get(i).val - 1] == 0) {
                    heap.add(node.children.get(i));
                }
            }
        }
        if (cnt==n) {
            for (int i = 0; i < n; i++) {
                out.print(sortedNumbers[i] + " ");
            }
        }else out.println("impossible");
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
