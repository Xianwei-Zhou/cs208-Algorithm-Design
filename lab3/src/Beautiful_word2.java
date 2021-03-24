import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Beautiful_word2 {

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
    static int[][] matrix;
    static ArrayList<Integer> chars;
    static int max;
    static boolean[] status;

    static class Task {
        public void solve(InputReader in, PrintWriter out) {
            int T = in.nextInt();
            for (int t = 0; t < T; t++) {
                matrix = new int[26][27];
                chars = new ArrayList<>();
                String str = in.next();
                //create a 2-D array to store all words like "nr","st","th"...
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
                for (int i = 0; i < 26; i++) {
                    if (matrix[i][26] != 0) {
                        chars.add(i);
                    }
                }
                length = chars.size();
                status = new boolean[length];
                max = 0;
                dfs(0, false, 0);
                out.println(max);
            }
        }
    }

    static void dfs(int index, boolean stat, int val) {
        status[index] = stat;
        if (stat) {
            val += matrix[chars.get(index)][26];
            for (int i = 0; i < index; i++) {
                if (status[i])
                    val -= matrix[chars.get(index)][chars.get(i)] << 1;
            }
        }
        if (index == length - 1) {
            max = Math.max(max, val);
            return;
        }
        dfs(index + 1, true, val);
        dfs(index + 1, false, val);
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
