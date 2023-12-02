import java.util.Stack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestValue {
    static class Node {
        int pre;
        int cur;
        int nxt;

        public Node(int pre, int cur, int nxt) {
            this.pre = pre;
            this.cur = cur;
            this.nxt = nxt;
        }
    }

    public String solution(int n, int k, String[] cmd) {
        int[] pre = new int[n];
        int[] next = new int[n];
        initializeLinkedList(pre, next, n);

        Stack<Node> stack = new Stack<>();
        StringBuilder sb = new StringBuilder("O".repeat(n));

        for (String command : cmd) {
            char c = command.charAt(0);
            if (c == 'U') {
                k = moveUp(k, Integer.parseInt(command.substring(2)), pre);
            } else if (c == 'D') {
                k = moveDown(k, Integer.parseInt(command.substring(2)), next);
            } else if (c == 'C') {
                k = deleteNode(k, pre, next, stack, sb);
            } else {
                restoreNode(pre, next, stack, sb);
            }
        }
        return sb.toString();
    }

    private void initializeLinkedList(int[] pre, int[] next, int n) {
        for (int i = 0; i < n; i++) {
            pre[i] = i - 1;
            next[i] = i + 1;
        }
        next[n - 1] = -1;
    }

    private int moveUp(int k, int num, int[] pre) {
        while (num-- > 0) {
            k = pre[k];
        }
        return k;
    }

    private int moveDown(int k, int num, int[] next) {
        while (num-- > 0) {
            k = next[k];
        }
        return k;
    }

    private int deleteNode(int k, int[] pre, int[] next, Stack<Node> stack, StringBuilder sb) {
        stack.push(new Node(pre[k], k, next[k]));

        if (pre[k] != -1) {
            next[pre[k]] = next[k];
        }
        if (next[k] != -1) {
            pre[next[k]] = pre[k];
        }

        sb.setCharAt(k, 'X');

        return (next[k] != -1) ? next[k] : pre[k];
    }

    private void restoreNode(int[] pre, int[] next, Stack<Node> stack, StringBuilder sb) {
        Node node = stack.pop();
        if (node.pre != -1) {
            next[node.pre] = node.cur;
        }
        if (node.nxt != -1) {
            pre[node.nxt] = node.cur;
        }
        sb.setCharAt(node.cur, 'O');
    }

    @Test
    void 정답() {
        String[] c1 = { "D 2", "C", "U 3", "C", "D 4", "C", "U 2", "Z", "Z" };
        String[] c2 = { "D 2", "C", "U 3", "C", "D 4", "C", "U 2", "Z", "Z", "U 1", "C" };
        Assertions.assertEquals("OOOOXOOO", solution(8, 2, c1));
        Assertions.assertEquals("OOXOXOOO", solution(8, 2, c2));
    }
}
