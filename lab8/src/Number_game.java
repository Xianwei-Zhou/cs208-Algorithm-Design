import java.io.*;
import java.math.*;
import java.util.*;

public class Number_game {

    public static void main(String[] args) {
        InputStream inputStream = System.in;// new FileInputStream("C:\\Users\\wavator\\Downloads\\test.in");
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Task solver = new Task();
        solver.solve(in, out);
        out.close();
    }

    static class Operation {
        String op;
        int t;

        public Operation(String op, int t) {
            this.op = op;
            this.t = t;
        }
    }

    static Operation[] operations;

    //to find i-th bit of answer after all operations of current bit (0/1,i)
    static int findBit(int cur, int i) {
        cur = cur << i;
        for (Operation operation : operations) {
            switch (operation.op) {
                case ("AND"):
                    cur &= operation.t;
                    break;
                case ("OR"):
                    cur |= operation.t;
                    break;
                default:
                    cur ^= operation.t;
            }
        }
        cur = cur >>> i;
        if ((cur & 1) == 1) return 1;
        else return 0;
    }

    static class Task {

        public void solve(InputReader in, PrintWriter out) {
            int n = in.nextInt();
            int m = in.nextInt();
            int length = (int) Math.ceil(Math.log(m) / Math.log(2));
            int MAX_tLength = 0;
            operations = new Operation[n];
            int ans = 0;
            for (int i = 0; i < n; i++) {
                operations[i] = new Operation(in.next(), in.nextInt());
                MAX_tLength = Math.max(MAX_tLength, operations[i].t);
            }
            boolean flag = false; //to ensure the number we acquire < m
            for (int i = length - 1; i >= 0; i--) {
                int zero = findBit(0, i);
                int one = findBit(1, i);
                if (zero < one && (flag || (m >>> i & 1) == 1)) {
                    ans += one << i;
                } else {
                    ans += zero << i;
                    if ((m >>> i & 1) == 1) flag = true;
                }

            }
            int diff = (int) Math.ceil(Math.log(MAX_tLength) / Math.log(2)) - length;
            while (diff-- > 0) {
                ans += findBit(0, diff + length) << (diff + length);
            }
            System.out.println(ans);
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

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public char[] nextCharArray() {
            return next().toCharArray();
        }

        //         public boolean hasNext() {
//             try {
//                 return reader.ready();
//             } catch(IOException e) {
//                 throw new RuntimeException(e);
//             }
//         }
        public boolean hasNext() {
            try {
                String string = reader.readLine();
                if (string == null) {
                    return false;
                }
                tokenizer = new StringTokenizer(string);
                return tokenizer.hasMoreTokens();
            } catch (IOException e) {
                return false;
            }
        }

        public BigInteger nextBigInteger() {
            return new BigInteger(next());
        }

        public BigDecimal nextBigDecinal() {
            return new BigDecimal(next());
        }
    }
}