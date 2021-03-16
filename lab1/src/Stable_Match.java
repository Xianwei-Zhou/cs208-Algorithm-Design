import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Stable_Match {
    public static void main(String[] args) {
        InputStream inputStream = System.in;// new FileInputStream("C:\\Users\\wavator\\Downloads\\test.in");
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Task solver = new Task();
        solver.solve(in, out);
        out.close();
    }

    static class Task {

        public void solve(InputReader in, PrintWriter out) {
            int n = in.nextInt();
            Boy[] boys = new Boy[n];
            Girl[] girls = new Girl[n];
            Queue<Boy> freeBoys = new LinkedList<>();
            HashMap<String, Girl> girlHashMap = new HashMap<>();

            String name;
            for (int i = 0; i < n; i++) {
                name = in.next();
                boys[i] = new Boy(name);
                freeBoys.offer(boys[i]);
            }
            for (int i = 0; i < n; i++) {
                name = in.next();
                girls[i] = new Girl(name);
                girlHashMap.put(name, girls[i]);
            }


            for (int i = 0; i < n; i++) {
                boys[i].liked = new String[n];
                for (int j = 0; j < n; j++) {
                    boys[i].liked[j] = in.next();
                }
            }
            for (int i = 0; i < n; i++) {
                girls[i].liked = new String[n];
                for (int j = 0; j < n; j++) {
                    girls[i].liked[j] = in.next();
                }
            }

            //begin Propose-and-reject algorithm.
            while (!freeBoys.isEmpty()) {
                Boy boy = freeBoys.peek();
                for (int i = 0; i < n; i++) {
                    if (boy.isChoosing != null) break;
                    Girl curGirl = girlHashMap.get(boy.liked[i]);
                    if (curGirl.isFree()) {
                        curGirl.isChoosing = boy;
                        boy.isChoosing = curGirl;
                        freeBoys.poll();
                    } else {
                        for (int j = 0; j < n; j++) {
                            if (curGirl.liked[j].equals(boy.name)) {
                                curGirl.isChoosing.isChoosing = null;
                                freeBoys.offer(curGirl.isChoosing);
                                curGirl.isChoosing = boy;
                                boy.isChoosing = curGirl;
                                freeBoys.poll();
                                break;
                            }
                            if (curGirl.liked[j].equals(curGirl.isChoosing.name))
                                break;
                        }
                    }
                }
            }
            for (int i = 0; i < n; i++) {
                out.println(boys[i].name + " " + boys[i].isChoosing.name);
            }
        }
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
    }
}

class Boy {
    String name;
    String[] liked;
    Girl isChoosing;

    public Boy(String name) {
        this.name = name;
    }
}

class Girl {
    String name;
    String[] liked;
    Boy isChoosing;

    public Girl(String name) {
        this.name = name;
    }

    boolean isFree() {
        return isChoosing == null;
    }
}