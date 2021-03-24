import java.io.*;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Beautiful_word {

    public static void main(String[] args) {
        InputStream inputStream = System.in;// new FileInputStream("C:\\Users\\wavator\\Downloads\\test.in");
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Task solver = new Task();
        solver.solve(in, out);
        out.close();
    }

    static int length;

    static class State {
        int index;
        int val;
        boolean[] status;

        public State(int index, int val, boolean[] status) {
            this.index = index;
            this.val = val;
            this.status = status;

        }
    }

    static class Task {
        public void solve(InputReader in, PrintWriter out) {
            int T = in.nextInt();
            for (int t = 0; t < T; t++) {
                String str = in.next();
                //create a 2-D array to store all words like "nr","st","th"...
                int[][] matrix = new int[26][27];
                char tmp = 0;
                for (int i = 0; i < str.length(); i++) {
                    char ch = str.charAt(i);
                    if (ch != 'a' && ch != 'e' && ch != 'i' && ch != 'o' && ch != 'u') {
                        if (tmp != 0 && tmp != ch) {
                            matrix[tmp - 97][ch - 97]++;
                            matrix[ch - 97][tmp - 97]++;
                            matrix[tmp - 97][26]++;
                            matrix[ch - 97][26]++;
                        }
                        tmp = ch;
                    } else tmp = 0;
                }
                //dfs to traverse all situations
                ArrayList<Integer> chars = new ArrayList<>();
                for (int i = 0; i < 26; i++) {
                    if (matrix[i][26] != 0) {
                        chars.add(i);
                    }
                }
                length = chars.size();
                boolean[] status = new boolean[length];
                int max = 0;
                int val;
                Deque<State> stack = new LinkedList<>();
                stack.push(new State(0, 0, status));
                boolean[] status2 = new boolean[length];
                status2[0] = true;
                stack.push(new State(0, matrix[chars.get(0)][26], status2));

                while (!stack.isEmpty()) {
                    State temp = stack.pop();
                    if (temp.index == length - 1) {
                        max = Math.max(max, temp.val);
                        continue;
                    }
                    temp.status[temp.index + 1] = false;
                    stack.push(new State(temp.index + 1, temp.val, temp.status));

                    val = temp.val + matrix[chars.get(temp.index + 1)][26];
                    for (int i = 0; i <= temp.index; i++) {
                        if (temp.status[i])
                            val -= matrix[chars.get(temp.index + 1)][chars.get(i)] << 1;
                    }
                    boolean[] stat = new boolean[length];
                    System.arraycopy(temp.status, 0, stat, 0, temp.index + 1);
                    stat[temp.index + 1] = true;
                    stack.push(new State(temp.index + 1, val, stat));
                }
                out.println(max);
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
