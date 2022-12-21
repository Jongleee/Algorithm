package com.example.algorithm.stack;

import java.util.Stack;

public class RightParenthesis {
    boolean solution(String s) {
        StringBuilder sb = new StringBuilder(s);
        return isCorrect(sb);
    }

    private boolean isCorrect(StringBuilder s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (stack.isEmpty()) stack.push(s.charAt(i));
            else {
                if (s.charAt(i) == ')') {
                    if (stack.peek() == '(') stack.pop();
                    else {
                        stack.push(s.charAt(i));
                    }
                } else {
                    stack.push(s.charAt(i));
                }
            }
        }
        return stack.isEmpty();
    }
    boolean solution2(String s) {
        boolean answer = false;
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                count++;
            }
            if (s.charAt(i) == ')') {
                count--;
            }
            if (count < 0) {
                break;
            }
        }
        if (count == 0) {
            answer = true;
        }
        return answer;

    }
}
