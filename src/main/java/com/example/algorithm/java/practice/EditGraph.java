package com.example.algorithm.java.practice;

import java.util.Stack;

public class EditGraph {
    public static void main(String[] args) {
        System.out.println(solution(8, 2, new String[]{"D 2","C","U 3","C","D 4","C","U 2","Z","Z"}));
    }
    public static String solution(int n, int k, String[] cmd) {
        int[] pre = new int[n];
        int[] next = new int[n];
        for(int i = 0; i < n; i++) {
            pre[i] = i - 1;
            next[i] = i + 1;
        }
        next[n - 1] = -1;
        
        Stack<Node> stack = new Stack<>();
        StringBuilder sb = new StringBuilder("O".repeat(n));
        for(int i = 0; i < cmd.length; i++) {
            char c = cmd[i].charAt(0);
            if(c == 'U') {
                int num = Integer.parseInt(cmd[i].substring(2));
                while(num-- > 0) {
                    k=pre[k];
                }
            } else if(c == 'D') {
                int num = Integer.parseInt(cmd[i].substring(2));
                while(num-- > 0) {
                    k=next[k];
                }
            } else if(c == 'C') {
                k = findC(k, pre, next, stack, sb);
            } else {
                findO(pre, next, stack, sb);
            }
        }
        return sb.toString();
    }
    private static void findO(int[] pre, int[] next, Stack<Node> stack, StringBuilder sb) {
        Node node = stack.pop();
        if(node.pre != -1) next[node.pre] = node.cur;
        if(node.nxt != -1) pre[node.nxt] = node.cur;
        sb.setCharAt(node.cur, 'O');
    }
    private static int findC(int k, int[] pre, int[] next, Stack<Node> stack, StringBuilder sb) {
        stack.push(new Node(pre[k], k, next[k]));
        if(pre[k] != -1) next[pre[k]] = next[k]; 
        if(next[k] != -1) pre[next[k]] = pre[k];
        sb.setCharAt(k, 'X');
        
        if(next[k] != -1) k = next[k];
        else k = pre[k];
        return k;
    }
    
    public static class Node{
        int pre;
        int cur;
        int nxt;
        
        public Node(int pre, int cur, int nxt) {
            this.pre = pre;
            this.cur = cur;
            this.nxt = nxt;
        }
    }
}
