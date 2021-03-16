import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

//bfs to find the shortest distance
public class Dividing_water {
    static Queue<State> states;
    static int sMax, nMax, mMax;
    static boolean[][][] hasVisited;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int T = in.nextInt();
        for (int t = 0; t < T; t++) {
            sMax = in.nextInt();
            nMax = in.nextInt();
            mMax = in.nextInt();
            if (sMax % 2 == 1) {
                System.out.println("impossible");
                continue;
            }
            //to make n>m
            if (mMax > nMax) {
                int tmp = mMax;
                mMax = nMax;
                nMax = tmp;
            }
            states = new LinkedList<>();
            states.offer(new State(sMax, 0, 0, 0));
            int ans;
            hasVisited = new boolean[sMax + 1][nMax + 1][mMax + 1];
            if ((ans = bfs()) != -1)
                System.out.println(ans);
            else System.out.println("impossible");
        }

    }

    static int bfs() {
        while (!states.isEmpty()) {
            State curS = states.poll();//currentState
            if (curS.n == curS.s && curS.m == 0) return curS.step;
            //n->m
            if (curS.n > 0 && curS.m < mMax) {
                if (curS.n <= mMax - curS.m && !hasVisited[curS.s][0][curS.m + curS.n]) {
                    states.offer(new State(curS.s, 0, curS.m + curS.n, curS.step + 1));
                    hasVisited[curS.s][0][curS.m + curS.n] = true;
                } else if (curS.n > mMax - curS.m && !hasVisited[curS.s][curS.n + curS.m - mMax][mMax]) {
                    states.offer(new State(curS.s, curS.n + curS.m - mMax, mMax, curS.step + 1));
                    hasVisited[curS.s][curS.n + curS.m - mMax][mMax] = true;
                }
            }
            //m->n
            if (curS.n < nMax && curS.m > 0) {
                if (curS.m <= nMax - curS.n && !hasVisited[curS.s][curS.n + curS.m][0]) {
                    states.offer(new State(curS.s, curS.n + curS.m, 0, curS.step + 1));
                    hasVisited[curS.s][curS.n + curS.m][0] = true;
                } else if (curS.m > nMax - curS.n && !hasVisited[curS.s][nMax][curS.m + curS.n - nMax]) {
                    states.offer(new State(curS.s, nMax, curS.m + curS.n - nMax, curS.step + 1));
                    hasVisited[curS.s][nMax][curS.m + curS.n - nMax] = true;
                }
            }
            //n->s
            if (curS.n > 0 && !hasVisited[curS.s + curS.n][0][curS.m]) {
                states.offer(new State(curS.s + curS.n, 0, curS.m, curS.step + 1));
                hasVisited[curS.s + curS.n][0][curS.m] = true;
            }
            //m->s
            if (curS.m > 0 && !hasVisited[curS.s + curS.m][curS.n][0]) {
                states.offer(new State(curS.s + curS.m, curS.n, 0, curS.step + 1));
                hasVisited[curS.s + curS.m][curS.n][0] = true;
            }
            //s->n
            if (curS.n < nMax) {
                if (curS.s <= nMax - curS.n && !hasVisited[0][curS.s + curS.n][curS.m]) {
                    states.offer(new State(0, curS.n + curS.s, curS.m, curS.step + 1));
                    hasVisited[0][curS.s + curS.n][curS.m] = true;
                } else if (curS.s > nMax - curS.n && !hasVisited[curS.s + curS.n - nMax][nMax][curS.m]) {
                    states.offer(new State(curS.s + curS.n - nMax, nMax, curS.m, curS.step + 1));
                    hasVisited[curS.s + curS.n - nMax][nMax][curS.m] = true;
                }
            }
            //s->m
            if (curS.m < mMax) {
                if (curS.s <= mMax - curS.m && !hasVisited[0][curS.n][curS.s + curS.m]) {
                    states.offer(new State(0, curS.n, curS.m + curS.s, curS.step + 1));
                    hasVisited[0][curS.n][curS.s + curS.m] = true;
                } else if (curS.s > mMax - curS.m && !hasVisited[curS.s + curS.m - mMax][curS.n][mMax]) {
                    states.offer(new State(curS.s + curS.m - mMax, curS.n, mMax, curS.step + 1));
                    hasVisited[curS.s + curS.m - mMax][curS.n][mMax] = true;
                }
            }
        }
        return -1;
    }
}

class State {
    int s, n, m, step;

    public State(int s, int n, int m, int step) {
        this.s = s;
        this.n = n;
        this.m = m;
        this.step = step;
    }
}

