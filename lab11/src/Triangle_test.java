import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import static java.lang.Math.*;
import static java.lang.Math.sin;

/*reference: https://www.cnblogs.com/kuangbin/archive/2013/07/24/3210565.html
    https://blog.csdn.net/fjssharpsword/article/details/53282918 */
public class Triangle_test {

    public static int bitReverse(int n, int bits) {
        int reversedN = n;
        int count = bits - 1;

        n >>= 1;
        while (n > 0) {
            reversedN = (reversedN << 1) | (n & 1);
            count--;
            n >>= 1;
        }

        return ((reversedN << count) & ((1 << bits) - 1));
    }

    static void fft(Complex[] buffer) {

        int bits = (int) (log(buffer.length) / log(2));
        for (int j = 1; j < buffer.length; j++) {

            int swapPos = bitReverse(j, bits);
            if (j < swapPos) {
                Complex temp = buffer[j];
                buffer[j] = buffer[swapPos];
                buffer[swapPos] = temp;
            }
        }

        for (int N = 2; N <= buffer.length; N <<= 1) {
            Complex wn = new Complex(cos(2 * PI / N), sin(2 * PI / N));
            for (int i = 0; i < buffer.length; i += N) {
                Complex w = new Complex(1, 0);
                for (int k = 0; k < N / 2; k++) {

                    int evenIndex = i + k;
                    int oddIndex = i + k + (N / 2);
                    Complex even = buffer[evenIndex];
                    Complex odd = buffer[oddIndex];

                    Complex exp = w.times(odd);

                    buffer[evenIndex] = even.plus(exp);
                    buffer[oddIndex] = even.minus(exp);
                    w = w.times(wn);
                }
            }
        }
    }

    // compute the inverse FFT of x[], assuming its length is a power of 2
    static Complex[] ifft(Complex[] x) {
        int N = x.length;

        // take conjugate
        for (int i = 0; i < N; i++) {
            x[i] = x[i].conjugate();
        }

        // compute forward FFT
        fft(x);

        // take conjugate again
        for (int i = 0; i < N; i++) {
            x[i] = x[i].conjugate();
        }

        // divide by N
        for (int i = 0; i < N; i++) {
            x[i] = x[i].scale(1.0 / N);
        }

        return x;

    }

    // compute the circular convolution of x and y
    static Complex[] cConvolve(Complex[] x, Complex[] y) {
        assert x == y;

        int N = x.length;

        // compute FFT of each sequence
        fft(x);
        fft(y);

        // point-wise multiply
        Complex[] c = new Complex[N];
        for (int i = 0; i < N; i++) {
            c[i] = x[i].times(y[i]);
        }

        // compute inverse FFT
        return ifft(c);
    }

    static Complex[] cConvolve(Complex[] x) {
        int N = x.length;

        // compute FFT of each sequence
        fft(x);

        // point-wise multiply
        for (int i = 0; i < N; i++) {
            x[i] = x[i].times(x[i]);
        }

        // compute inverse FFT
        return ifft(x);
    }


    // compute the linear convolution of x and y
    public static Complex[] convolve(Complex[] x, Complex[] y) {
        Complex ZERO = new Complex(0, 0);

        Complex[] a = new Complex[2 * x.length];
        System.arraycopy(x, 0, a, 0, x.length);
        for (int i = x.length; i < 2 * x.length; i++) a[i] = ZERO;

        Complex[] b = new Complex[2 * y.length];
        System.arraycopy(y, 0, b, 0, y.length);
        for (int i = y.length; i < 2 * y.length; i++) b[i] = ZERO;

        return cConvolve(a, b);
    }

    public static Complex[] convolve(Complex[] x) {
        Complex ZERO = new Complex(0, 0);

        Complex[] a = new Complex[2 * x.length];
        System.arraycopy(x, 0, a, 0, x.length);
        for (int i = x.length; i < 2 * x.length; i++) a[i] = ZERO;

        return cConvolve(a);
    }

    static class Complex {
        final double re;
        final double im;


        Complex(double real, double imag) {
            re = real;
            im = imag;
        }

        public Complex plus(Complex b) {
            Complex a = this;
            double real = a.re + b.re;
            double imag = a.im + b.im;
            return new Complex(real, imag);
        }

        public Complex minus(Complex b) {
            Complex a = this;
            double real = a.re - b.re;
            double imag = a.im - b.im;
            return new Complex(real, imag);
        }

        public Complex times(Complex b) {
            Complex a = this;
            double real = a.re * b.re - a.im * b.im;
            double imag = a.re * b.im + a.im * b.re;
            return new Complex(real, imag);
        }

        public Complex scale(double alpha) {
            return new Complex(alpha * re, alpha * im);
        }

        public Complex conjugate() {
            return new Complex(re, -im);
        }


    }

    static Complex[] padding(int[] ori) {

        int len = 1;
        while (len < ori.length) len <<= 1;
        Complex[] des = new Complex[len];
        for (int i = 0; i < ori.length; i++) {
            des[i] = new Complex(ori[i], 0);
        }
        for (int i = ori.length; i < len; i++) {
            des[i] = new Complex(0, 0);
        }
        return des;
    }

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = in.nextInt();
        }
        Arrays.sort(nums);

        int max = nums[n - 1];
        int[] cnt = new int[max + 1];
        for (int i = 0; i < n; i++) {
            cnt[nums[i]]++;
        }
        Complex[] fCnt = padding(cnt);

        Complex[] tmp = convolve(fCnt);

        long[] sum = new long[2 * max + 1];//the sum of two side length
        for (int i = 0; i < sum.length; i++) {
            sum[i] = (int) (tmp[i].re + 0.5);
        }

        for (int i = 0; i < n; i++) {
            sum[nums[i] * 2]--;
        }
        for (int i = 0; i < sum.length; i++) {
            sum[i] /= 2;
        }
        long[] preSum = new long[sum.length];//the sum of prefixes of array sum
        for (int i = 1; i < sum.length; i++) {
            preSum[i] = preSum[i - 1] + sum[i];
        }
        long ans = n * preSum[sum.length - 1];
        for (int i = 0; i < n; i++) {//choose nums[i] as the longest side
            //both smaller,one smaller one larger,one equal,two larger
            ans -= preSum[nums[i]] + (long) i * (n - 1 - i) + (n - 1) + (long) (n - 1 - i) * (n - i - 2) / 2;
        }
        out.println(ans);

        out.close();
    }


    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private final DataInputStream din;
        private final byte[] buffer;
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