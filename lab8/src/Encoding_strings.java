import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Encoding_strings {
    static class Node {
        int cnt;
        boolean isChar;
        Node left;
        Node right;

        public Node(int cnt, boolean isChar) {
            this.cnt = cnt;
            this.isChar = isChar;
        }
    }

    static int ans;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int T = in.nextInt();
        for (int t = 0; t < T; t++) {
            ans = 0;
            String str = in.next();
            char[] chars = str.toCharArray();
            HashMap<Character, Integer> map = new HashMap<Character, Integer>();
            for (char aChar : chars) {
                if (map.containsKey(aChar)) {
                    map.replace(aChar, map.get(aChar) + 1);
                } else map.put(aChar, 1);
            }
            PriorityQueue<Node> queue = new PriorityQueue<>(new Comparator<Node>() {
                @Override
                public int compare(Node o1, Node o2) {
                    return o1.cnt - o2.cnt;
                }
            });
            for (char key : map.keySet()) {
                queue.offer(new Node(map.get(key), true));
            }
            if (queue.size() <= 2) {
                System.out.println(str.length());
                continue;
            }
            //Huffman Tree
            while (queue.size() != 1) {
                Node a = queue.poll();
                Node b = queue.poll();
                Node foo = new Node(a.cnt + b.cnt, false);
                foo.left = a;
                foo.right = b;
                queue.offer(foo);
            }
            bfs(queue.peek(), 0);
            System.out.println(ans);
        }
    }

    static void bfs(Node node, int layer) {
        if (node == null) return;
        if (node.isChar) ans += layer * node.cnt;
        bfs(node.left, layer + 1);
        bfs(node.right, layer + 1);
    }
}
