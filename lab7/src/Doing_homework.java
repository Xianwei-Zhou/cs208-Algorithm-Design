import java.io.*;
import java.util.*;

public class Doing_homework {

    static class Task {
        int s;
        int t;
        int w;

        public Task(int s, int t, int w) {
            this.s = s;
            this.t = t;
            this.w = w;
        }
    }

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        Task[] tasks = new Task[n];
        for (int i = 0; i < n; i++) {
            tasks[i] = new Task(in.nextInt(), in.nextInt(), in.nextInt());
        }

        //to find discrete time points we need
        timeList = new ArrayList<>();
        Arrays.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o1.s - o2.s;
            }
        });
        int tmp = 0;
        for (int i = 0; i < n; i++) {
            tmp = Math.max(tasks[i].s, tmp + 1);
            timeList.add(tmp);
        }

        //to match discrete tasks with discrete time points linearly by greedy algorithm
        long ans = 0;
        chosenTasks = new HashSet<>();
        Arrays.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o2.w - o1.w;
            }
        });
        timeTaskMap = new HashMap<>();
        Task task;
        for (int i = 0; i < n; i++) {
            task = tasks[i];
            find(0,task);
        }

        for (Task t : chosenTasks) {
            ans += t.w;
        }
        out.println(ans);
        out.close();
    }

    static HashSet<Task> chosenTasks;
    static HashMap<Integer, Task> timeTaskMap;
    static ArrayList<Integer> timeList;

    //find time to do task from time t
    static boolean find(int t, Task task) {
        if (timeList.get(t) > task.t) {
            return false;
        }
        if (timeTaskMap.get(t) == null) {
            chosenTasks.add(task);
            timeTaskMap.put(t, task);
            return true;
        }
        Task task1=timeTaskMap.get(t);
        //greedy algorithm (t smaller->better)
        if (task.t>task1.t){
            return find(t+1,task);
        }else {
            if (find(t+1,task1)){
                chosenTasks.add(task);
                timeTaskMap.put(t,task);
                return true;
            }else return false;
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