import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;

// 0 1 knapsack problem
public class Shopping {

    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);
        int n=in.nextInt();
        int m=in.nextInt();
        int[] costs=new int[n];
        int[] values=new int[n];
        for (int i = 0; i < n; i++) {
            values[i]=in.nextInt();
        }
        for (int i = 0; i < n; i++) {
            costs[i]=in.nextInt();
        }
        int[][] ans=new int[n+1][m+1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (costs[i-1]>j){
                    ans[i][j]=ans[i-1][j];
                }else {
                    ans[i][j]=Math.max(ans[i-1][j],values[i-1]+ans[i-1][(j-costs[i-1])]);
                }
            }
        }
        out.println(ans[n][m]);
        out.close();
    }


    static class Reader
    {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader()
        {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public int nextInt() throws IOException
        {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do
            {
                ret = ret * 10 + c - '0';
            }  while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException
        {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException
        {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

    }

}