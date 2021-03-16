import java.util.Scanner;

public class Walking_the_stairs {
    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        int T=in.nextInt();
        for (int t = 0; t < T; t++) {
            System.out.println(solve(in.nextInt(),in.nextInt()));
        }
    }
    static int solve(int n,int l){
        long[] array=new long[n];

        long tmp=1;
        array[0]=1;
        for (int i = 1; i < l; i++) {
            array[i]=(array[i-1]*2)%998244353;
            tmp=tmp+array[i];
        }

        for (int i = l; i < n; i++) {
            array[i]=tmp;
            tmp=(tmp-array[i-l]+array[i]+998244353)%998244353;
        }
        return (int)(array[n-1]%998244353);
    }

}
