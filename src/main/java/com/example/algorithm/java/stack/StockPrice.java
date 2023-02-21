package com.example.algorithm.java.stack;

import java.util.Arrays;
import java.util.Stack;

public class StockPrice {
    public static int[] solution(int[] prices) {
        int n = prices.length;
        Stack<Integer> stack = new Stack<>();
        int[] answer = new int[n];
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && prices[i] < prices[stack.peek()]) {
                int j = stack.pop();
                answer[j] = i - j;
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int j = stack.pop();
            answer[j] = n - j - 1;
        }

        return answer;
    }

    public static void main(String[] args) {
        int[] p1 = { 1, 2, 3, 2, 3 };
        System.out.println(Arrays.toString(solution(p1))); // [4, 3, 1, 1, 0]
    }
}
