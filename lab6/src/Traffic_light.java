import java.io.*;
import java.util.*;

public class Traffic_light {
    static class vertex {
        int index;
        boolean isVisited;
        ArrayList<vertex> nextVertex;
        ArrayList<Integer> weightList;
        long time;
        int a;//red time
        int b;//green time

        public vertex(int index) {
            this.index = index;
            nextVertex = new ArrayList<>();
            weightList = new ArrayList<>();
            time = Long.MAX_VALUE;
        }
    }

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int m = in.nextInt();
        vertex[] vertices = new vertex[n];
        for (int i = 0; i < n; i++) {
            vertices[i] = new vertex(i);
        }
        int u, v, w;
        for (int i = 0; i < m; i++) {
            u = in.nextInt() - 1;
            v = in.nextInt() - 1;
            w = in.nextInt();
            vertices[u].nextVertex.add(vertices[v]);
            vertices[u].weightList.add(w);
        }
        for (int i = 0; i < n; i++) {
            vertices[i].a = in.nextInt();
            vertices[i].b = in.nextInt();
        }
        vertices[0].time = 0;

        // dijkstra, which is like dfs
        PriorityQueue<vertex> queue = new PriorityQueue<>(new Comparator<vertex>() {
            @Override
            public int compare(vertex o1, vertex o2) {
                if (o1.time < o2.time) return -1;
                else if (o1.time > o2.time) return 1;
                return 0;
            }
        });
        int curIndex = 0;
        while (curIndex != n - 1) {
            vertex temp = vertices[curIndex];
            if (temp.isVisited) {
                curIndex = queue.poll().index;
                continue;
            }
            for (int i = 0; i < temp.nextVertex.size(); i++) {
                if (temp.nextVertex.get(i).isVisited) continue;
                vertex next = temp.nextVertex.get(i);
                long passedTime = (temp.time + temp.weightList.get(i)) % (next.a + next.b);
                if (passedTime < next.a) {
                    next.time = Math.min(temp.time + temp.weightList.get(i) + next.a - passedTime, next.time);
                } else {
                    next.time = Math.min(temp.time + temp.weightList.get(i), next.time);
                }
                queue.add(next);
            }
            temp.isVisited = true;
            curIndex = queue.poll().index;
        }

        out.println(vertices[n - 1].time);
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