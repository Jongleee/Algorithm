package com.example.algorithm.java.practice.dynamicProgramming;

import java.util.Deque;
import java.util.LinkedList;

public class ProperParenthesis {
    private static class Node {
        private final int left;
        private final int right;

        private Node(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }

    public static int solution(int n) {
        Deque<Node> stack = new LinkedList<>();
        stack.push(new Node(0, 0));

        int count = 0;
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            if (node.left > n || node.right > n || node.left < node.right) {
                continue;
            }
            if (node.left == n && node.right == n) {
                count++;
            } else {
                stack.push(new Node(node.left + 1, node.right));
                stack.push(new Node(node.left, node.right + 1));
            }
        }

        return count;
    }

    public static void main(String[] args) {
        System.out.println(solution(2));
        System.out.println(solution(3));
        System.out.println(solution(5));
    }

    private int count;

    public int recursive(int n) {
        count = 0;

        dfs(0, 0, n);
        return count;
    }

    public void dfs(int left, int right, int n) {
        if (left > n || right > n)
            return;
        if (left < right)
            return;
        if (left == n && right == n) {
            count++;
            return;
        }
        dfs(left + 1, right, n);
        dfs(left, right + 1, n);
    }

    public int dp(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                dp[i] += dp[i - j] * dp[j - 1];
            }
        }

        return dp[n];
    }
}
